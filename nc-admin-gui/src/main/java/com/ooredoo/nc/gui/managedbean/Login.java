package com.ooredoo.nc.gui.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ooredoo.nc.gui.auth.UIAuthenticationProvider;
import com.ooredoo.nc.model.AppUser;
import com.ooredoo.nc.utility.ApplicationConstants;

@Named(value="login")
@ViewScoped
public class Login extends UIAuthenticationProvider implements Serializable, ApplicationConstants {

	private static final long serialVersionUID = 1L;
	private String username = null;
	private String password = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(Login.class);
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void resetFields() {
		this.username = null;
		this.password = null;
	}
	
	public Integer getYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	
	public void validateAndLogin() {
		FacesContext context = FacesContext.getCurrentInstance();
		String actionComment = null;
		
		try {
			Authentication authentication = authenticate(new UsernamePasswordAuthenticationToken(this.username, this.password));
	        
			AppUser u = (AppUser) authentication.getPrincipal();
			String username = u.getUName();
			
			if(u.getIsAccDefaultPswd()) {
				SecurityContextHolder.getContext().setAuthentication(authentication);
				actionComment = "User " + username + " successfully authenticated at " + new Date() + " with default Password. Redirecting to reset password page";
				LOGGER.info(actionComment);
				context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath() + RESET_PASSWORD_PAGE);
				
			} else {
				SecurityContextHolder.getContext().setAuthentication(authentication);
				actionComment = "User " + username + " successfully logged into Number cleanup system at " + new Date().toString();
				LOGGER.info(actionComment + ". Redirecting to home page");
				context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath() + MANAGE_TASK_PAGE);
				
			}
		} catch(AuthenticationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getMessage()));
			LOGGER.error("Error Occurred while logging into from application", e);
			
			actionComment = "User :" + username + " login failed";
		} catch (IOException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getMessage("AUI.004")));
			LOGGER.error("Error Occurred while logging into from application", e);
			
			actionComment = "User :" + username + " login failed";
		} catch (Exception e) {
			actionComment = "User :" + username + " login failed";
			if(null != e.getMessage() && e.getMessage().startsWith("No instances available for")) {
				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error",
								getMessage("AUI.005")));
			} else {
				context.addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error",
						getMessage("AUI.004")));
			}
			
			LOGGER.error("Error Occurred while logging into from application", e);
		} finally {
			resetFields();
		}
	}
}
