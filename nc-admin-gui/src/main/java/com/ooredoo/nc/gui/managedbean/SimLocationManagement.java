package com.ooredoo.nc.gui.managedbean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ooredoo.nc.dto.SubscriberProfileCleanupRequest;
import com.ooredoo.nc.dto.SubscriberProfileCleanupResponse;
import com.ooredoo.nc.dto.SubscriberProfileDetails;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.gui.configuration.SessionHandler;
import com.ooredoo.nc.model.AppUser;
import com.ooredoo.nc.model.NumberPool;
import com.ooredoo.nc.utility.ApplicationConstants;

@Named(value="slm")
@ViewScoped
public class SimLocationManagement extends ManagedBean implements ApplicationConstants {
	private static final Logger LOGGER = LoggerFactory.getLogger(SimLocationManagement.class);
	private SubscriberProfileCleanupRequest cleanReq;
	private SubscriberProfileCleanupResponse cleanRes;
	private Map<String, String> allErpLocations;
	private List<SubscriberProfileDetails> subscriberProfilesUg;
	private List<SubscriberProfileDetails> selectedProfilesForCleanupUg;
	
	
	@PostConstruct
	public void init() {
		cleanReq = new SubscriberProfileCleanupRequest();
		getAvailableNumbersToUser();
		getErpLocations();
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
	
	public void getAvailableNumbersToUser() {
		FacesContext context = FacesContext.getCurrentInstance();
		AppUser currentUser = SessionHandler.getLoggerInUserInfo();
		
		try {
			List<NumberPool> npForUserGrp = getNumberPoolSerivce().getAllActiveNumbersForUser(currentUser.getId());
			
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
	
	public void refresh() {
		getAvailableNumbersToUser();
	}
	
	public void resetLocation() {
		LOGGER.info("Cleaning up for subscriber profile assigned to user group.");
		cleanReq.setRequestDate(new Date());
		cleanReq.setRequestUsername(getActionUser());
		List<String> nodes = new ArrayList<String>();
		nodes.add("ERP");
		cleanReq.setNodes(nodes);
		
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			if(null != this.selectedProfilesForCleanupUg && !this.selectedProfilesForCleanupUg.isEmpty()) {
				if(null != this.cleanReq.getNodes() && !this.cleanReq.getNodes().isEmpty()) {
					for(SubscriberProfileDetails s : this.selectedProfilesForCleanupUg) {
						if(null == s.getSelectedErpLocation() || s.getSelectedErpLocation().isEmpty()) {
							throw new ApplicationException("ERP Location not selected for MSISDN : " + s.getMsisdn(), true);
						}
						
						cleanReq.setProfiles(selectedProfilesForCleanupUg);
						cleanReq.setRequestDate(new Date());
						cleanReq.setRequestUsername(getActionUser());
						
						LOGGER.info("Cleanup request :  " + cleanReq.toString());
						
						cleanRes = getProfileService().cleanup(cleanReq);
					}
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
	
	public void reset() {
		this.cleanReq = new SubscriberProfileCleanupRequest();
		
		if(null != this.cleanRes.getCleanupDetails()) {
			this.cleanRes.getCleanupDetails().clear();
		}
		
		if(null != this.cleanRes.getProfiles()) {
			this.cleanRes.getProfiles().clear();
		}
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
