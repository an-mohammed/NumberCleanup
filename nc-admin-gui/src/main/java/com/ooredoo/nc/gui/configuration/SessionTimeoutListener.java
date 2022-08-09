package com.ooredoo.nc.gui.configuration;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionTimeoutListener implements PhaseListener {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(SessionTimeoutListener.class);
	
	private String getLoginPath() {
        return "/login.xhtml";
    }

	@Override
	public void afterPhase(PhaseEvent arg0) {

	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
		 final FacesContext facesContext = FacesContext.getCurrentInstance();
	 
	        final HttpServletRequest request = HttpServletRequest.class.cast(facesContext.getExternalContext().getRequest());
	        if (request.getDispatcherType() == DispatcherType.FORWARD && getLoginPath().equals(request.getServletPath())) {
	            final String redirect = facesContext.getExternalContext().getRequestContextPath() + request.getServletPath();
	            try {
	                facesContext.getExternalContext().redirect(redirect);
	            } catch (final IOException e) {
	            	LOGGER.error("Error Occurred", e);
	            }
	        }
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
