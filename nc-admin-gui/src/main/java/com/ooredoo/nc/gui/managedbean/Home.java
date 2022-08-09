package com.ooredoo.nc.gui.managedbean;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ooredoo.nc.gui.configuration.SessionHandler;

@Named(value="home")
@ViewScoped
public class Home {
	private static final Logger LOGGER = LoggerFactory.getLogger(Home.class);
	
	public String userManagement() {
		LOGGER.info("User Management URL Invoked");
		return routePage("userManagement");
	}
	
	public String almManagement() {
		LOGGER.info("Audit Log Management URL Invoked");
		return routePage("almManagement");
	}
	
	public String backOffice() {
		LOGGER.info("Backoffice Management URL Invoked");
		return routePage("backOffice");
	}
	
	public String customerManagement() {
		LOGGER.info("Customer Management URL Invoked");
		return routePage("customerManagement");
	}
	
	public String jobManagement() {
		LOGGER.info("Batch Job Management URL Invoked");
		return routePage("jobManagement");
	}
	
	public String reportManagement() {
		LOGGER.info("Report Management URL Invoked");
		return routePage("reportManagement");
	}
	
	private String routePage(String page) {
		String rPage = null;
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(null == SecurityContextHolder.getContext() || null == SecurityContextHolder.getContext().getAuthentication() || null == SessionHandler.getLoggerInUserInfo()) {
			try {
				LOGGER.info("As no session credential found so logging out from active session and redirecting to login page");
				context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath() + "/logout");
				
			} catch(IOException e) {
				LOGGER.error("Unable to logout from home page. Cause:", e);
				rPage = "home";
			}
		} else {
			LOGGER.info("Application context and security context is valid. Redirect it to page - {}", page);
			rPage = page;
		}
		
		return rPage;	
	}
}
