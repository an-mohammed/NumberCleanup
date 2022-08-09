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

import com.ooredoo.nc.dto.DisconnectionRequest;
import com.ooredoo.nc.dto.DisconnectionResponse;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.gui.configuration.SessionHandler;
import com.ooredoo.nc.model.AppUser;
import com.ooredoo.nc.utility.ApplicationConstants;

@Named(value="dc")
@ViewScoped
public class Disconnection extends ManagedBean  implements Serializable, ApplicationConstants {

	private static final long serialVersionUID = 1L;
	private List<String> selectedMsisdn;
	private List<String> availableMsisdnForUser;
	private DisconnectionResponse discResp;
	private boolean execStatus = false;
	private static final Logger LOGGER = LoggerFactory.getLogger(Disconnection.class);
	
	@PostConstruct
	public void init() {
		getAvailableNumbersToUser();
	}
	
	public void getAvailableNumbersToUser() {
		FacesContext context = FacesContext.getCurrentInstance();
		AppUser currentUser = SessionHandler.getLoggerInUserInfo();
		
		try {
			String username = currentUser.getUName();
			if(null == username || username.isEmpty()) {
				username = currentUser.getuName();
			}
			
			this.availableMsisdnForUser = getNumberPoolSerivce().getAllActiveNumbersForUsername(username);
			
			LOGGER.info("Extracted all available mobile numbers from pool for user - {}", username);
			
		} catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while extracting all msisdns. Cause", e);
			
		} catch(Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
		}
	}
	
	public void submit() {
		FacesContext context = FacesContext.getCurrentInstance();
		AppUser currentUser = SessionHandler.getLoggerInUserInfo();
		
		try {
			String username = currentUser.getUName();
			
			DisconnectionRequest req = new DisconnectionRequest();
			req.setUsername(username);
			req.setMsisdnList(this.selectedMsisdn);
			this.execStatus = true;
			this.discResp = getConfigService().disconnection(req);
			
			if(null != this.discResp) {
				if(OK_LABEL.equals(this.discResp.getStatus())) {
					context.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Success",
									"Disconnection request successfully posted"));
				} else {
					context.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Error",
									this.discResp.getStatus()));
				}
			} else {
				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error",
								"No response received from service"));
			}
			
		} catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while extracting all msisdns. Cause", e);
			
		} catch(Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
			
		} finally {
			if(null != this.selectedMsisdn) {
				this.selectedMsisdn.clear();
			}
		}
	}
	
	public List<String> getSelectedMsisdn() {
		return selectedMsisdn;
	}
	public void setSelectedMsisdn(List<String> selectedMsisdn) {
		this.selectedMsisdn = selectedMsisdn;
	}
	public List<String> getAvailableMsisdnForUser() {
		return availableMsisdnForUser;
	}
	public void setAvailableMsisdnForUser(List<String> availableMsisdnForUser) {
		this.availableMsisdnForUser = availableMsisdnForUser;
	}
	public DisconnectionResponse getDiscResp() {
		return discResp;
	}
	public void setDiscResp(DisconnectionResponse discResp) {
		this.discResp = discResp;
	}
	public boolean getExecStatus() {
		return execStatus;
	}
	public void setExecStatus(boolean execStatus) {
		this.execStatus = execStatus;
	}
}
