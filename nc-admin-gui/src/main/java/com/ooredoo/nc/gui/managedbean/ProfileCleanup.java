package com.ooredoo.nc.gui.managedbean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ooredoo.nc.dto.SubscriberProfileCleanupRequest;
import com.ooredoo.nc.dto.SubscriberProfileCleanupResponse;
import com.ooredoo.nc.dto.SubscriberProfileDetails;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.model.NumberPool;
import com.ooredoo.nc.utility.ApplicationConstants;

@Named(value="pc")
@ViewScoped
public class ProfileCleanup extends ManagedBean  implements Serializable, ApplicationConstants {

	private static final long serialVersionUID = 1L;
	private SubscriberProfileCleanupRequest cleanReq;
	private SubscriberProfileCleanupResponse cleanRes;
	private SubscriberProfileDetails profileDetails;
	private Map<String, String> allNodes;
	private Map<String, String> subscriberTypes;
	private List<String> modes;
	private String mode;
	private boolean bulkFlag;
	private String errorStyle = "red-button";
	private String successStyle = "green-button";
	private StreamedContent templateFile;
	private static final Logger LOGGER = LoggerFactory.getLogger(ProfileCleanup.class);
	
	@PostConstruct
	public void init() {
		profileDetails = new SubscriberProfileDetails();
		cleanReq = new SubscriberProfileCleanupRequest();
		
		allNodes = getAllSystems();
		
		for(Entry<String, String> i : allNodes.entrySet()) {
			cleanReq.getNodes().add(i.getValue());
		}
		
		bulkFlag = false;
		modes = new ArrayList<String>();
		modes.add(MODE_SINGLE);
		modes.add(MODE_BULK);
		
		subscriberTypes = new HashMap<String, String>();
		subscriberTypes.put("Prepaid", "PP");
		subscriberTypes.put("Postpaid", "PO");
		
		
	}
	
	public Map<String, String> getAllDealer() {
		String nmsLocationString = getExternalConfigValue("service.cleanup.nms.dealer");
		String[] splitString = nmsLocationString.split(COMMA);
		
		Map<String, String> allLocations = new HashMap<String, String>();
		
		for(String loc : splitString) {
			String[] sArray = loc.split(HYPHEN);
			allLocations.put(sArray[0], sArray[1]);
		}
		
		return allLocations;
	}
	
	public void manageBulkFlag() {
		if(mode.equals("Single")) {
			bulkFlag = false;
		} else {
			bulkFlag = true;
		}
	}
	
	public void getFile() {
		templateFile = new DefaultStreamedContent(FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/BulkCleanupTemplate.csv"), "file/csv", "Bulk_Cleanup_Template.csv");
	}
	
	/**
	 * 
	 */
	public void searchNumber() {
		try {
			if(null != this.profileDetails && null != this.profileDetails.getMsisdn() && !this.profileDetails.getMsisdn().isEmpty()) {
				
				if(null == profileDetails.getMsisdn() || profileDetails.getMsisdn().trim().isEmpty()) {
					throw new ApplicationException("Missing MSISDN", true);
					
				} else {
					if(profileDetails.getMsisdn().length() == 8) {
						profileDetails.setMsisdn(MSISDN_PREFIX + profileDetails.getMsisdn());
						
					} else if(profileDetails.getMsisdn().length() == 11) {
						if(!profileDetails.getMsisdn().startsWith(MSISDN_PREFIX)) {
							throw new ApplicationException("Invalid MSISDN", true);
							
						}
					} else {
						throw new ApplicationException("Invalid MSISDN", true);
						
					}
				}
				
				NumberPool np = getNumberPoolSerivce().getNumberDetails(this.profileDetails.getMsisdn());
				
				if(null != np) {
					this.profileDetails.setImsi(np.getImsi());
					this.profileDetails.setSimNo(np.getSimNo());
					this.profileDetails.setSelecedNmsPool(np.getCurNmsPool());
					this.profileDetails.setSelectedErpLocation(np.getCurErpLoc());
				}
			}
		} catch(ApplicationException e) {
			LOGGER.error("Error Occurred while searching number. Cause", e);			
		} catch(Exception e) {
			LOGGER.error("Fatal exception occurred. Cause", e);
		}
	}
	
	public void cleanupProfile() {
		LOGGER.info("Cleaning up for subscriber profile.");
		cleanReq.setRequestDate(new Date());
		cleanReq.setRequestUsername(getActionUser());
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			
			if(null == this.cleanReq.getNodes() || this.cleanReq.getNodes().isEmpty()) {
				throw new ApplicationException("Please select atleast one system to execute cleanup", true);
			}
			
			if(null != mode && mode.equals("Single")) {
				
				if(null == profileDetails.getMsisdn() || profileDetails.getMsisdn().trim().isEmpty()) {
					throw new ApplicationException("Missing MSISDN", true);
					
				} else {
					if(profileDetails.getMsisdn().length() == 8) {
						profileDetails.setMsisdn(MSISDN_PREFIX + profileDetails.getMsisdn());
						
					} else if(profileDetails.getMsisdn().length() == 11) {
						if(!profileDetails.getMsisdn().startsWith(MSISDN_PREFIX)) {
							throw new ApplicationException("Invalid MSISDN", true);
							
						}
					} else {
						throw new ApplicationException("Invalid MSISDN", true);
						
					}
				}
				
				if(null == profileDetails.getImsi() || profileDetails.getImsi().trim().isEmpty()) {
					throw new ApplicationException("Missing IMSI", true);
				}
				
				if(null == profileDetails.getSimNo() || profileDetails.getSimNo().trim().isEmpty()) {
					throw new ApplicationException("Missing SIM Number", true);
				} else {
					if(!profileDetails.getSimNo().startsWith(SIMNO_PREFIX)) {
						throw new ApplicationException("Invalid SIM No", true);
					}
				}
				
				if(null == profileDetails.getSelectedErpLocation() || profileDetails.getSelectedErpLocation().isEmpty()) {
					throw new ApplicationException("Missing ERP Location", true);
				}
				
				if(null == profileDetails.getSubscriberType() || profileDetails.getSubscriberType().isEmpty()) {
					throw new ApplicationException("Missing Subscriber type", true);
				}
				
				cleanReq.getProfiles().clear();
				cleanReq.getProfiles().add(profileDetails);
				
				LOGGER.info("Cleanup request for single mode :  " + cleanReq.toString());
				
				cleanRes = getProfileService().cleanup(cleanReq);
				
			} else if(null != mode && mode.equals("Bulk")) {
				
				LOGGER.info("Cleanup request for bulk mode :  " + cleanReq.toString());
				
				cleanRes = getProfileService().cleanup(cleanReq);
			}
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, SUCCESS_LEBEL, "Profile cleanup execution complete."));
			
		} catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while searching user. Cause", e);
			
		} catch(Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
		}
	}
	
	public void reset() {
		bulkFlag = false;
		profileDetails = new SubscriberProfileDetails();
		cleanReq = new SubscriberProfileCleanupRequest();
		cleanRes.getCleanupDetails().clear();
		cleanRes.getProfiles().clear();
		this.mode = "Single";
	}
	
	public Map<String, String> getAllSystems() {
		String nmsLocationString = getExternalConfigValue("service.cleanup.availabe.system");
		String[] splitString = nmsLocationString.split(COMMA);
		
		allNodes = new HashMap<String, String>();
		
		for(String loc : splitString) {
			allNodes.put(loc, loc);
		}
		
		return allNodes;
	}
	
	public Map<String, String> getAllNmsPools() {
		String nmsLocationString = getExternalConfigValue("service.cleanup.nms.pool");
		String[] splitString = nmsLocationString.split(COMMA);
		
		Map<String, String> allLocations = new HashMap<String, String>();
		
		for(String loc : splitString) {
			allLocations.put(loc, loc);
		}
		
		return allLocations;
	}
	
	public Map<String, String> getAllErpLocations() {
		Map<String, String> allErpLocations = null;
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			allErpLocations = getProfileService().getAllErpLocations();
			
		} catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while searching user. Cause", e);
			
		} catch(Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
		}
		
		return allErpLocations;
	}
	
	/**
	 * 
	 * @param record
	 * @param profiles
	 * @throws Exception
	 */
	private void parseRecord(String record, List<SubscriberProfileDetails> profiles) throws ApplicationException {
		
		SubscriberProfileDetails profile = null;
		
		String[] columns = record.split(COMMA);
		
		if(columns.length == 8 && validateColumn(columns[0]) && validateColumn(columns[1]) 
				&& validateColumn(columns[2]) && validateColumn(columns[5]) && validateColumn(columns[6])) {
			if(columns[0].trim().startsWith(MSISDN_PREFIX)) {
				if(columns[1].trim().startsWith(SIMNO_PREFIX)) {
					if(columns[6].trim().equals("PP") || columns[6].trim().equals("PO")) {
						profile = new SubscriberProfileDetails();
						profile.setMsisdn(columns[0]);
						profile.setSimNo(columns[1]);
						profile.setImsi(columns[2]);
						profile.setSelecedNmsPool(null != columns[3] && !columns[3].isEmpty() ? columns[3] : null);
						profile.setPrice(null != columns[4] && !columns[4].isEmpty() ? Double.parseDouble(columns[4]) : null);
						profile.setSelectedErpLocation(columns[5]);
						profile.setSubscriberType(columns[6]);
						profile.setSelectedDealer(columns[7]);
						profiles.add(profile);
					} else {
						throw new ApplicationException("Subscriber type should be PP/PO. Error value is : " + columns[6], true);
					}
				} else {
					throw new ApplicationException("Sim No. Must Start with " + SIMNO_PREFIX +".SIM No - " + columns[1], true);
				}
			} else {
				throw new ApplicationException("MSISDN Must Start with " + MSISDN_PREFIX +".MSISDN-" + columns[0], true);
			}
		} else {
			throw new ApplicationException("Records must be in format withouto blank/space- <MSISDN>,<SIM NO>,<IMSI>,<NMS Pool>,<NMS Price>,<ERP Location>,<Subscriber Type>,<Dealer ID>", true);
		}
	}
	
	/**
	 * 
	 * @param column
	 * @return
	 */
	private boolean validateColumn(String column) {
		if(null == column || column.trim().isEmpty()) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 
	 * @param event
	 */
	public void handleFileUpload(FileUploadEvent event) {
		LOGGER.info("Invoking file Upload Method");
		
        if(event.getFile() != null && event.getFile().getSize() > 0) {
        	BufferedReader br = null;
        	String line = null;
        	
        	try {
        		if(null != this.cleanReq.getProfiles()) {
        			this.cleanReq.getProfiles().clear();
        		}
        		
        		br = new BufferedReader(new InputStreamReader(event.getFile().getInputstream(), "utf-8"));
        		
        		while((line = br.readLine()) != null) {
        			parseRecord(line, this.cleanReq.getProfiles());
        		}
        		
        		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, SUCCESS_LEBEL, event.getFile().getFileName() + " is successfully uploaded."));
        	
        	} catch(IOException e) {
        		LOGGER.error("Error Occurred while uploading and parsing file", e);
        		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, ERROR_LABEL, getFatalErrorMessage()));
        		
        	} catch(ApplicationException ae) {
        		LOGGER.error("Error Occurred while uploading and parsing file", ae);
        		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, ERROR_LABEL, ae.getErrorMessage()));
        		
        	} catch(Exception e) {
        		LOGGER.error("Error Occurred while uploading and parsing file", e);
        		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, ERROR_LABEL, getFatalErrorMessage()));
        		
        	}finally {
        		try {
	        		event.getFile().getInputstream().close();
	        		if(null != br) {
	        			try {br.close();} catch(Exception e) {
	        				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, ERROR_LABEL, getFatalErrorMessage()));
	        			}
	        		}
	        		
        		} catch(Exception e) {
        			LOGGER.error("Error Occurred while uploading and parsing file", e);
            		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,ERROR_LABEL, getFatalErrorMessage()));
        		}
        	}
            
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ERROR_LABEL, INVALID_FILE));
        }
    }
	
	public String close() {
		return "home";
	}

	public Map<String, String> getAllNodes() {
		return allNodes;
	}

	public void setAllNodes(Map<String, String> allNodes) {
		this.allNodes = allNodes;
	}

	public SubscriberProfileCleanupRequest getCleanReq() {
		return cleanReq;
	}

	public void setCleanReq(SubscriberProfileCleanupRequest cleanReq) {
		this.cleanReq = cleanReq;
	}

	public SubscriberProfileDetails getProfileDetails() {
		return profileDetails;
	}

	public void setProfileDetails(SubscriberProfileDetails profileDetails) {
		this.profileDetails = profileDetails;
	}

	public boolean getBulkFlag() {
		return bulkFlag;
	}

	public void setBulkFlag(boolean bulkFlag) {
		this.bulkFlag = bulkFlag;
	}

	public List<String> getModes() {
		return modes;
	}

	public void setModes(List<String> modes) {
		this.modes = modes;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public SubscriberProfileCleanupResponse getCleanRes() {
		return cleanRes;
	}

	public void setCleanRes(SubscriberProfileCleanupResponse cleanRes) {
		this.cleanRes = cleanRes;
	}

	public String getErrorStyle() {
		return errorStyle;
	}

	public void setErrorStyle(String errorStyle) {
		this.errorStyle = errorStyle;
	}

	public String getSuccessStyle() {
		return successStyle;
	}

	public void setSuccessStyle(String successStyle) {
		this.successStyle = successStyle;
	}

	public Map<String, String> getSubscriberTypes() {
		return subscriberTypes;
	}

	public void setSubscriberTypes(Map<String, String> subscriberTypes) {
		this.subscriberTypes = subscriberTypes;
	}

	public StreamedContent getTemplateFile() {
		return templateFile;
	}

	public void setTemplateFile(StreamedContent templateFile) {
		this.templateFile = templateFile;
	}
}
