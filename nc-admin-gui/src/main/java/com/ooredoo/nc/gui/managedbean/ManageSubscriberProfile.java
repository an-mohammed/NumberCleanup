package com.ooredoo.nc.gui.managedbean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ooredoo.nc.dto.SubscriberProfileManagementResponse;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.utility.ApplicationConstants;

@Named(value="msp")
@ViewScoped
public class ManageSubscriberProfile extends ManagedBean  implements Serializable, ApplicationConstants {

	private static final long serialVersionUID = 1L;
	private String msisdn;
	private boolean searchComplete = false;
	private String dashboardUrl;
	private SubscriberProfileManagementResponse profile;
	private static final Logger LOGGER = LoggerFactory.getLogger(ManageSubscriberProfile.class);
	
	@PostConstruct
	public void init() {
		this.msisdn = null;
		this.searchComplete = false;
		this.dashboardUrl = getExternalConfigValue("esm.dashboard.url");
	}
	
	
	/**
	 * 
	 */
	public void searchProfile() {
		LOGGER.info("Searching for subscriber profile with MSISDN - {}");
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			if(null == this.msisdn || this.msisdn.trim().isEmpty()) {
				throw new ApplicationException("Missing MSISDN", true);
				
			} else {
				if(this.msisdn.length() == 8) {
					this.msisdn = MSISDN_PREFIX + this.msisdn;
					
				} else if(this.msisdn.length() == 11) {
					if(!this.msisdn.startsWith(MSISDN_PREFIX)) {
						throw new ApplicationException("Invalid MSISDN", true);
					}
				} else {
					throw new ApplicationException("Invalid MSISDN", true);
					
				}
			}
			this.searchComplete = true;
			this.profile = getProfileService().searchProfile(msisdn);
			
						
		} catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Unable to search subscriber profile. Cause", e);
			
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
		msisdn = null;
		this.searchComplete = false;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public SubscriberProfileManagementResponse getProfile() {
		return profile;
	}

	public void setProfile(SubscriberProfileManagementResponse profile) {
		this.profile = profile;
	}


	public boolean getSearchComplete() {
		return searchComplete;
	}


	public void setSearchComplete(boolean searchComplete) {
		this.searchComplete = searchComplete;
	}


	public String getDashboardUrl() {
		return dashboardUrl;
	}


	public void setDashboardUrl(String dashboardUrl) {
		this.dashboardUrl = dashboardUrl;
	}
}
