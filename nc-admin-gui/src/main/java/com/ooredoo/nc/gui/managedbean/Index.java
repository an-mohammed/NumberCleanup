package com.ooredoo.nc.gui.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.annotation.SessionScope;

import com.ooredoo.nc.dto.CleanupSystemConfig;
import com.ooredoo.nc.exception.ApplicationRestException;
import com.ooredoo.nc.gui.configuration.SessionHandler;
import com.ooredoo.nc.model.AppUser;
import com.ooredoo.nc.utility.ApplicationConstants;


@Named(value="index")
@SessionScope
public class Index extends ManagedBean implements Serializable, ApplicationConstants {

	private static final long serialVersionUID = 1L;
	private Locale locale = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(Index.class);
	private String userFullName = null;
	private String usernameForOnboarding = null;
	private Long userId = null;
	private CleanupSystemConfig sc;
	
	
	@PostConstruct
	public void init() {
		locale = (Locale) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LOCALE_LANG");
		
		if(null == locale) {
			LOGGER.info("No Locale set from context. Setting English as default");
			locale = ENGLISH;
		}
		
		AppUser u = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		userFullName = u.getFirstname() + " " + u.getLastname();
		usernameForOnboarding = u.getUName();
		userId = u.getId();
		
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsernameForOnboarding() {
		return usernameForOnboarding;
	}

	public void setUsernameForOnboarding(String usernameForOnboarding) {
		this.usernameForOnboarding = usernameForOnboarding;
	}

	public Integer getYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	
	
	public String getUserFullName() {
		return userFullName;
	}


	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}


	public String routeHome() {
		String page = null;
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(null == SecurityContextHolder.getContext().getAuthentication() || null == SessionHandler.getLoggerInUserInfo()) {
			try {
				LOGGER.info("As no session found logging out from active session");
				
				context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath() + "/logout");
			} catch(IOException e) {
				page = "home";
				LOGGER.error("Unable to logout from home page");
			}
		} else {
			LOGGER.info("Routing to Home page");
			page = "home";
		}
		
		return page;
	}
	
	public void logout() {
		FacesContext context = FacesContext.getCurrentInstance();
		String actionComment = null;
		
		try {
			AppUser u = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			LOGGER.info("Logging out from application for user :" + u.getUName() + " at -" + new Date());
			
			
			context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath() + "/logout");	
			
		} catch(IOException e) {
			actionComment = "Logout failed for User :" + SessionHandler.getLoggerInUserInfo().getUName();
			LOGGER.error("Error Occurred while logging out from application");
			
		}	finally {
			AppUser loggedInu = SessionHandler.getLoggerInUserInfo();
			
			if(null != loggedInu) {
				actionComment = "User :" + loggedInu.getUName() + " successfully logged out from application";
			} else {
				actionComment = "User successfully logged out from application";
			}
			
			LOGGER.info("Action comment : {}", actionComment);
			//logAuditEvent(AuditLogActions.LOGOUT.getAction(), actionComment);
		}	
	}
	
	public void onIdle() {
		LOGGER.info("Maximum Inactive threshold limit reached.");
		SessionHandler.removeFromSession(S_CREDENTIAL);
		SessionHandler.removeFromSession(S_TRANSACTION_ID);
	}
	
	public void onActive() {
		LOGGER.info("Maximum Inactive threshold limit reached. Logging out user");
		logout();
	}
	
	public void setEnglish() {
	    locale = ENGLISH;
	    updateViewLocale();
	 }
	
	private void updateViewLocale() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("LOCALE_LANG", locale);
	    FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
	}
	
	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	public void getSystemConfig() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			sc = getConfigService().getSystemConfig();
			
		} catch(ApplicationRestException e) {
			context.addMessage(null, new FacesMessage("Error", "Fatal Error. Error occurred while extracting system config"));
			LOGGER.error("Error Occurred while extracting system config");
		}
	}

	public CleanupSystemConfig getSc() {
		return sc;
	}

	public void setSc(CleanupSystemConfig sc) {
		this.sc = sc;
	}
}
