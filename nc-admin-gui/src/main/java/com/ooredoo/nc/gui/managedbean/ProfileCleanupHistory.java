package com.ooredoo.nc.gui.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.model.CleanupLogMaster;
import com.ooredoo.nc.utility.ApplicationConstants;

@Named(value="pch")
@ViewScoped
public class ProfileCleanupHistory extends ManagedBean  implements Serializable, ApplicationConstants {

	private static final long serialVersionUID = 1L;
	private List<CleanupLogMaster> hist;
	private String msisdn;
	private static final Logger LOGGER = LoggerFactory.getLogger(ProfileCleanupHistory.class);
	
	@PostConstruct
	public void init() {
	}
	
	public void searchHist() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			if(null != msisdn && !msisdn.isEmpty()) {
				
				if(msisdn.length() == 8) {
					msisdn = MSISDN_PREFIX + msisdn;
					
				} else if(msisdn.length() == 11) {
					if(!msisdn.startsWith(MSISDN_PREFIX)) {
						throw new ApplicationException("Invalid MSISDN", true);
					}
				} else {
					throw new ApplicationException("Invalid MSISDN", true);
				}
				
				hist = getProfileService().showHist(msisdn);
			} else {
				throw new ApplicationException("Please provide MSISDN to search", true);
			}
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

	public List<CleanupLogMaster> getHist() {
		return hist;
	}

	public void setHist(List<CleanupLogMaster> hist) {
		this.hist = hist;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
}
