package com.ooredoo.nc.gui.managedbean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ooredoo.nc.dto.ReleaseNumberFromAssignmentReq;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.gui.configuration.SessionHandler;
import com.ooredoo.nc.model.AppUser;
import com.ooredoo.nc.model.NumberPool;

@Named(value="nm")
@ViewScoped
public class NumberManagement extends ManagedBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(NumberManagement.class);
	private List<NumberPool> allNumbers;
	private NumberPool selectedNumber;
	private NumberPool newNumber;
	private boolean disabled = true;
	
	@PostConstruct
	public void init() {
		getAllNumbersFromPool();
		newNumber = new NumberPool();
	}
	

	public void onRowSelect(SelectEvent selectEvent) {
		disabled = false;
	}
	
	public void onRowUnselect(UnselectEvent selectEvent) {
		disabled = true;
	}
	
	public void getAllNumbersFromPool() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			allNumbers = getNumberPoolSerivce().getAllNumbers();
			LOGGER.info("Extracted all number from pool");
		} catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while extracting all numbers. Cause", e);
			
		} catch(Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
		}
	}
	
	public void updateNumber() {
		FacesContext context = FacesContext.getCurrentInstance();
		AppUser currentUser = SessionHandler.getLoggerInUserInfo();
		
		try {
			selectedNumber.setModBy(currentUser.getId());
			selectedNumber = getNumberPoolSerivce().updateNumber(selectedNumber);
			LOGGER.info("Number Updated successfully");
			
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Success",
							"Number successfully updated"));
			
		} catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while extracting all numbers. Cause", e);
			
		} catch(Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
		}
	}
	
	public void releaseNumberFromAssignment() {
		FacesContext context = FacesContext.getCurrentInstance();
		AppUser currentUser = SessionHandler.getLoggerInUserInfo();
		
		try {
			ReleaseNumberFromAssignmentReq req = new ReleaseNumberFromAssignmentReq();
			req.setMsisdn(this.selectedNumber.getMsisdn());
			req.setModBy(currentUser.getId());
			
			selectedNumber = getNumberPoolSerivce().updateNumber(req);
			LOGGER.info("Number Updated successfully");
			
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Success",
							"Number successfully released from assignment"));
			
		} catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while releasibg number from assignment. Cause", e);
			
		} catch(Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
		}
	}
	
	public void addNumber() {
		FacesContext context = FacesContext.getCurrentInstance();
		AppUser currentUser = SessionHandler.getLoggerInUserInfo();
		
		try {
			newNumber.setCreatedBy(currentUser.getId());
			selectedNumber = getNumberPoolSerivce().addNumber(newNumber);
			LOGGER.info("New Number successfully added to pool");
			
			getAllNumbersFromPool();
			
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Success",
							"Number successfully added to pool"));
			this.newNumber = new NumberPool();
			
		} catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while extracting all numbers. Cause", e);
			
		} catch(Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
		}
	}

	public void setAllNumbers(List<NumberPool> allNumbers) {
		this.allNumbers = allNumbers;
	}

	public List<NumberPool> getAllNumbers() {
		return allNumbers;
	}

	public NumberPool getSelectedNumber() {
		return selectedNumber;
	}

	public void setSelectedNumber(NumberPool selectedNumber) {
		this.selectedNumber = selectedNumber;
	}


	public boolean getDisabled() {
		return disabled;
	}


	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}


	public NumberPool getNewNumber() {
		return newNumber;
	}


	public void setNewNumber(NumberPool newNumber) {
		this.newNumber = newNumber;
	}
}
