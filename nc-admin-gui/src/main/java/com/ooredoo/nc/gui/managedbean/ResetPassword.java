package com.ooredoo.nc.gui.managedbean;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ooredoo.nc.dto.UserPrinciple;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.gui.configuration.SessionHandler;
import com.ooredoo.nc.model.AppUser;
import com.ooredoo.nc.utility.ApplicationConstants;

@Named(value="rp")
@ViewScoped
public class ResetPassword extends ManagedBean implements Serializable, ApplicationConstants {

	private static final long serialVersionUID = 1L;
	private String currentPassword = null;
	private String newPassword = null;
	private String confirmNewPassword = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(ResetPassword.class);
	
	public void resetFields() {
		this.currentPassword = null;
		this.newPassword = null;
		this.confirmNewPassword = null;
	}
	
	public void resetPassword() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			if(newPassword.equals(confirmNewPassword)) {
				AppUser u = SessionHandler.getLoggerInUserInfo();
				
				UserPrinciple userP = new UserPrinciple();
				userP.setUsername(u.getUName());
				userP.setCurrentPassword(currentPassword);
				userP.setNewPassword(newPassword);
				userP.setUpdateDefaultPassword(true);
				userP.setResetToDefaultPassword(false);
				
				getUserService().resetPassword(userP);
				
				
				context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath() + "/logout");	
				
			} else {
				throw new ApplicationException("AUI.002");
			}
			
			
		} catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while logging into from application", e);
			
		} catch (IOException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
			
		} finally {
			resetFields();
		}
	}
	
	public void changePassword() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		LOGGER.info("Password details -----" + newPassword  + ":" + confirmNewPassword);
		try {
			if(newPassword.equals(confirmNewPassword)) {
				AppUser u = SessionHandler.getLoggerInUserInfo();
				
				if(u.getLdapauthentication()) {
					throw new ApplicationException("User credential is validated against LDAP. System can not update the password", true);
				}
				
				UserPrinciple userP = new UserPrinciple();
				userP.setUsername(u.getUName());
				userP.setCurrentPassword(currentPassword);
				userP.setNewPassword(newPassword);
				userP.setUpdateDefaultPassword(true);
				userP.setResetToDefaultPassword(false);
				
				getUserService().resetPassword(userP);
				
				resetFields();
				context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath() + "/logout");	
				
			} else {
				throw new ApplicationException("AUI.002");
			}
			
			
		} catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while logging into from application", e);
			
		} catch (IOException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
			
		} 
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}
}
