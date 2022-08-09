package com.ooredoo.nc.gui.managedbean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ooredoo.nc.dto.SubscriberProfileCleanupRequest;
import com.ooredoo.nc.dto.SubscriberProfileCleanupResponse;
import com.ooredoo.nc.dto.SubscriberProfileDetails;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.gui.configuration.SessionHandler;
import com.ooredoo.nc.model.AppUser;
import com.ooredoo.nc.model.NumberAsgnmtHistory;
import com.ooredoo.nc.model.NumberPool;
import com.ooredoo.nc.utility.ApplicationConstants;

@Named(value="am")
@ViewScoped
public class AssignmentManagement extends ManagedBean implements ApplicationConstants {
	private static final Logger LOGGER = LoggerFactory.getLogger(AssignmentManagement.class);
	private List<SelectItem> allAssignmentChoice;
	private List<NumberAsgnmtHistory> allAssignment;
	private NumberAsgnmtHistory selectedAssignment;
	private NumberAsgnmtHistory newAssignment;
	private boolean disabled = true;
	private Long selectedAssignmentId;
	private Map<String, String> subscriberTypes;
	private List<SubscriberProfileDetails> subscriberProfiles;
	private List<SubscriberProfileDetails> selectedProfilesForCleanup;
	private Map<String, String> allNodes;
	private SubscriberProfileCleanupRequest cleanReq;
	private SubscriberProfileCleanupResponse cleanRes;
	private Map<String, String> allErpLocations;
	private List<SubscriberProfileDetails> subscriberProfilesUg;
	private List<SubscriberProfileDetails> selectedProfilesForCleanupUg;
	
	
	@PostConstruct
	public void init() {
		getAllActiveAssignment();
		getAvailableNumbersToUserGroup();
		
		//if(null != this.allAssignmentChoice && !this.allAssignmentChoice.isEmpty()) {
			subscriberTypes = new HashMap<String, String>();
			subscriberTypes.put("Prepaid", "PP");
			subscriberTypes.put("Postpaid", "PO");
			
			getErpLocations();
			
			getAllSystems();
			
			cleanReq = new SubscriberProfileCleanupRequest();
			
			for(Entry<String, String> i : allNodes.entrySet()) {
				cleanReq.getNodes().add(i.getValue());
			}
		//}
	}
	
	public void refreshA() {
		getAllActiveAssignment();
	}
	
	public void refreshG() {
		getAvailableNumbersToUserGroup();
	}

	public void onRowSelect(SelectEvent selectEvent) {
		disabled = false;
	}
	
	public void onRowUnselect(UnselectEvent selectEvent) {
		disabled = true;
	}
	
	public void onAssignmentChange() {
		LOGGER.info("Selected assignment : {}", selectedAssignmentId);
		SubscriberProfileDetails p = null;
		
		for(NumberAsgnmtHistory h : this.allAssignment) {
			if(selectedAssignmentId.equals(h.getId())) {
				selectedAssignment = h;

				if(null != selectedAssignment && null != selectedAssignment.getNumberPools() && !selectedAssignment.getNumberPools().isEmpty()) {
					subscriberProfiles = new ArrayList<SubscriberProfileDetails>();
					
					for(NumberPool np : selectedAssignment.getNumberPools()) {
						p = new SubscriberProfileDetails();
						p.setImsi(np.getImsi());
						p.setMsisdn(np.getMsisdn());
						p.setSimNo(np.getSimNo());
						p.setSelectedErpLocation(np.getCurErpLoc());
						p.setSelecedNmsPool(np.getCurNmsPool());
						
						subscriberProfiles.add(p);
					}
				}
				break;
			}
		}
		
		LOGGER.info("Found assigned numbers for assignment id - {}", this.selectedAssignment.getDescription());
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
	
	public Map<String, String> getAllSystems() {
		String allSystems = getExternalConfigValue("service.cleanup.availabe.system");
		String[] splitString = allSystems.split(COMMA);
		
		allNodes = new HashMap<String, String>();
		
		for(String loc : splitString) {
			allNodes.put(loc, loc);
		}
		
		return allNodes;
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
	
	public Map<String, String> getErpLocations() {
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
	
	public void finishAssignment() {
		FacesContext context = FacesContext.getCurrentInstance();
		AppUser currentUser = SessionHandler.getLoggerInUserInfo();
		
		if(null != this.selectedAssignment) {
			
			LOGGER.info("Selected assignment : {}", selectedAssignmentId);
			try {
				selectedAssignment.setModBy(currentUser.getId());
				
				getNumberPoolSerivce().finishedAssignment(selectedAssignment);
				getAllActiveAssignment();
				
				LOGGER.info("Marking assignment as completed for assignment : {}", this.selectedAssignment.getDescription());
				
				this.selectedAssignment = null;
				
				if(null != this.selectedProfilesForCleanup) {
					this.selectedProfilesForCleanup.clear();
				}
				
				if(null != this.subscriberProfiles) {
					this.subscriberProfiles.clear();
				}
				
				this.cleanRes = null;
				
				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Success",
								"Assignment successfully marked finished"));
				
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
	}
	
	public void getAllActiveAssignment() {
		List<SelectItem> aItem = null;
		FacesContext context = FacesContext.getCurrentInstance();
		AppUser currentUser = SessionHandler.getLoggerInUserInfo();
		try {
			this.allAssignment = getNumberPoolSerivce().getAssignmentsForUser(currentUser.getId());
			
			if(null != this.allAssignment && !this.allAssignment.isEmpty()) {
				aItem = new ArrayList<SelectItem>();
				
				for(NumberAsgnmtHistory a : this.allAssignment) {
					if(a.getStatus().equals("A")) {
						aItem.add(new SelectItem(a.getId(), a.getDescription()));
					}
				}
				
				this.allAssignmentChoice = aItem;
				
			}
			
			if(null == this.allAssignmentChoice || this.allAssignmentChoice.isEmpty()) {
				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Great",
								"You have no active assignment"));
			}
			
			LOGGER.info("Extracted all assignment from pool");
			
		} catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while extracting all assignment. Cause", e);
			
		} catch(Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
		}
	}
	
	public void getAvailableNumbersToUserGroup() {
		FacesContext context = FacesContext.getCurrentInstance();
		AppUser currentUser = SessionHandler.getLoggerInUserInfo();
		
		try {
			List<NumberPool> npForUserGrp = getNumberPoolSerivce().getAvailableNumbersToUserGroup(currentUser.getId());
			
			if(null != npForUserGrp && !npForUserGrp.isEmpty()) {
				subscriberProfilesUg = new ArrayList<SubscriberProfileDetails>();
				SubscriberProfileDetails p = null;
				
				for(NumberPool np : npForUserGrp) {
					p = new SubscriberProfileDetails();
					p.setImsi(np.getImsi());
					p.setMsisdn(np.getMsisdn());
					p.setSimNo(np.getSimNo());
					p.setSelectedErpLocation(np.getCurErpLoc());
					p.setSelecedNmsPool(np.getCurNmsPool());
					
					subscriberProfilesUg.add(p);
				}
			}
			
			LOGGER.info("Extracted all assignment from pool");
			
		} catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while extracting all assignment. Cause", e);
			
		} catch(Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
		}
	}
	
	public void cleanupProfile() {
		LOGGER.info("Cleaning up for subscriber profile.");
		cleanReq.setRequestDate(new Date());
		cleanReq.setRequestUsername(getActionUser());
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			if(null != this.selectedProfilesForCleanup && !this.selectedProfilesForCleanup.isEmpty()) {
				if(null != this.cleanReq.getNodes() && !this.cleanReq.getNodes().isEmpty()) {
					for(SubscriberProfileDetails s : this.selectedProfilesForCleanup) {
						if(null == s.getSubscriberType() || s.getSubscriberType().isEmpty()) {
							throw new ApplicationException("Subscriber type not selected for MSISDN : " + s.getMsisdn(), true);
						}
						
						if(null == s.getSelectedErpLocation() || s.getSelectedErpLocation().isEmpty()) {
							throw new ApplicationException("ERP Location not selected for MSISDN : " + s.getMsisdn(), true);
						}
						
						if(null == s.getSelecedNmsPool() || s.getSelecedNmsPool().isEmpty()) {
							throw new ApplicationException("NMS Pool not selected for MSISDN : " + s.getMsisdn(), true);
						}
						
						if(null == s.getSelectedDealer() || s.getSelectedDealer().isEmpty()) {
							throw new ApplicationException("Dealer not selected for MSISDN : " + s.getMsisdn(), true);
						}
					}
						
					cleanReq.setProfiles(selectedProfilesForCleanup);
					cleanReq.setRequestDate(new Date());
					cleanReq.setRequestUsername(getActionUser());
					
					LOGGER.info("Cleanup request :  " + cleanReq.toString());
					
					cleanRes = getProfileService().cleanup(cleanReq);
					
				} else {
					throw new ApplicationException("No system/node selected for cleanup", true);
				}
			} else {
				throw new ApplicationException("No subscriber profile selected for cleanup", true);
			}
		} catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while extracting all assignment. Cause", e);
			
		} catch(Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
		}
	}
	
	public void cleanupProfileUg() {
		LOGGER.info("Cleaning up for subscriber profile assigned to user group.");
		cleanReq.setRequestDate(new Date());
		cleanReq.setRequestUsername(getActionUser());
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			if(null != this.selectedProfilesForCleanupUg && !this.selectedProfilesForCleanupUg.isEmpty()) {
				if(null != this.cleanReq.getNodes() && !this.cleanReq.getNodes().isEmpty()) {
					for(SubscriberProfileDetails s : this.selectedProfilesForCleanupUg) {
						if(null == s.getSubscriberType() || s.getSubscriberType().isEmpty()) {
							throw new ApplicationException("Subscriber type not selected for MSISDN : " + s.getMsisdn(), true);
						}
						
						if(null == s.getSelectedErpLocation() || s.getSelectedErpLocation().isEmpty()) {
							throw new ApplicationException("ERP Location not selected for MSISDN : " + s.getMsisdn(), true);
						}
						
						if(null == s.getSelecedNmsPool() || s.getSelecedNmsPool().isEmpty()) {
							throw new ApplicationException("NMS Pool not selected for MSISDN : " + s.getMsisdn(), true);
						}
						
						if(null == s.getSelectedDealer() || s.getSelectedDealer().isEmpty()) {
							throw new ApplicationException("Dealer not selected for MSISDN : " + s.getMsisdn(), true);
						}
					}
					
					cleanReq.setProfiles(selectedProfilesForCleanupUg);
					cleanReq.setRequestDate(new Date());
					cleanReq.setRequestUsername(getActionUser());
					
					LOGGER.info("Cleanup request :  " + cleanReq.toString());
					
					cleanRes = getProfileService().cleanup(cleanReq);
					
				} else {
					throw new ApplicationException("No system/node selected for cleanup", true);
				}
			} else {
				throw new ApplicationException("No subscriber profile selected for cleanup", true);
			}
		} catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while extracting all assignment. Cause", e);
			
		} catch(Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
		}
	}
	
	public void onTabChange() {
		this.cleanRes = null;
		this.selectedProfilesForCleanup = null;
		this.selectedProfilesForCleanupUg = null;
		this.selectedAssignmentId = null;
		this.selectedAssignment = null;
		
		init();
	}
	
	public void reset() {
		this.cleanReq = new SubscriberProfileCleanupRequest();
		
		if(null != this.cleanRes.getCleanupDetails()) {
			this.cleanRes.getCleanupDetails().clear();
		}
		
		if(null != this.cleanRes.getProfiles()) {
			this.cleanRes.getProfiles().clear();
		}
	}

	public void resetG() {
		this.cleanReq = new SubscriberProfileCleanupRequest();
		
		if(null != this.cleanRes.getCleanupDetails()) {
			this.cleanRes.getCleanupDetails().clear();
		}
		
		if(null != this.cleanRes.getProfiles()) {
			this.cleanRes.getProfiles().clear();
		}
	}

	public NumberAsgnmtHistory getSelectedAssignment() {
		return selectedAssignment;
	}

	public void setSelectedAssignment(NumberAsgnmtHistory selectedAssignment) {
		this.selectedAssignment = selectedAssignment;
	}

	public NumberAsgnmtHistory getNewAssignment() {
		return newAssignment;
	}

	public void setNewAssignment(NumberAsgnmtHistory newAssignment) {
		this.newAssignment = newAssignment;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public Long getSelectedAssignmentId() {
		return selectedAssignmentId;
	}

	public void setSelectedAssignmentId(Long selectedAssignmentId) {
		this.selectedAssignmentId = selectedAssignmentId;
	}

	public List<SelectItem> getAllAssignmentChoice() {
		return allAssignmentChoice;
	}

	public void setAllAssignmentChoice(List<SelectItem> allAssignmentChoice) {
		this.allAssignmentChoice = allAssignmentChoice;
	}

	public Map<String, String> getSubscriberTypes() {
		return subscriberTypes;
	}

	public void setSubscriberTypes(Map<String, String> subscriberTypes) {
		this.subscriberTypes = subscriberTypes;
	}

	public List<SubscriberProfileDetails> getSubscriberProfiles() {
		return subscriberProfiles;
	}

	public void setSubscriberProfiles(List<SubscriberProfileDetails> subscriberProfiles) {
		this.subscriberProfiles = subscriberProfiles;
	}

	public List<SubscriberProfileDetails> getSelectedProfilesForCleanup() {
		return selectedProfilesForCleanup;
	}

	public void setSelectedProfilesForCleanup(List<SubscriberProfileDetails> selectedProfilesForCleanup) {
		this.selectedProfilesForCleanup = selectedProfilesForCleanup;
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

	public SubscriberProfileCleanupResponse getCleanRes() {
		return cleanRes;
	}

	public void setCleanRes(SubscriberProfileCleanupResponse cleanRes) {
		this.cleanRes = cleanRes;
	}

	public Map<String, String> getAllErpLocations() {
		return allErpLocations;
	}

	public void setAllErpLocations(Map<String, String> allErpLocations) {
		this.allErpLocations = allErpLocations;
	}

	public List<SubscriberProfileDetails> getSubscriberProfilesUg() {
		return subscriberProfilesUg;
	}

	public void setSubscriberProfilesUg(List<SubscriberProfileDetails> subscriberProfilesUg) {
		this.subscriberProfilesUg = subscriberProfilesUg;
	}

	public List<SubscriberProfileDetails> getSelectedProfilesForCleanupUg() {
		return selectedProfilesForCleanupUg;
	}

	public void setSelectedProfilesForCleanupUg(List<SubscriberProfileDetails> selectedProfilesForCleanupUg) {
		this.selectedProfilesForCleanupUg = selectedProfilesForCleanupUg;
	}
}
