package com.ooredoo.nc.gui.managedbean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.gui.configuration.SessionHandler;
import com.ooredoo.nc.model.AppUser;
import com.ooredoo.nc.model.NumberAsgnmtHistory;
import com.ooredoo.nc.model.NumberPool;
import com.ooredoo.nc.utility.ApplicationConstants;

@Named(value="ai")
@ViewScoped
public class AssignmentInventory extends ManagedBean implements ApplicationConstants {
	private static final Logger LOGGER = LoggerFactory.getLogger(AssignmentInventory.class);
	private List<NumberAsgnmtHistory> allAssignments;
	private NumberAsgnmtHistory selectedAsmt;
	private NumberAsgnmtHistory newAsmt;
	private boolean disabled = true;
	private String selectedUsername;
	private String msisdn;
	private boolean isUserChanged = false;
	private List<String> selectedMsisdn;
	private List<String> currentMsisdn;
	
	public void searchAssignment() {
		FacesContext context = FacesContext.getCurrentInstance();
		LOGGER.info("Searching assignment with msisdn :{}", msisdn);
		
		try {
			
			if(null != this.msisdn && !this.msisdn.isEmpty()) {
				if(this.msisdn.length() == 8) {
					this.msisdn = MSISDN_PREFIX + this.msisdn;
					
				} else if(this.msisdn.length() == 11) {
					if(!this.msisdn.startsWith(MSISDN_PREFIX)) {
						throw new ApplicationException("Invalid MSISDN.", true);
					}
				} else {
					throw new ApplicationException("Invalid MSISDN.", true);
				}
				
				allAssignments = getNumberPoolSerivce().searchAssignment(msisdn);
				
			} else {
				getAllAssignmentFromPool();
				
			}
		} catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while searching assignment. Cause", e);
			
		} catch(Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
		}
	}
	
	
	@PostConstruct
	public void init() {
		getAllAssignmentFromPool();
		newAsmt = new NumberAsgnmtHistory();
	}

	public void onRowSelect(SelectEvent selectEvent) {
		disabled = false;
	}
	
	public void onRowUnselect(UnselectEvent selectEvent) {
		disabled = true;
	}
	
	public void finishAssignment() {
		FacesContext context = FacesContext.getCurrentInstance();
		AppUser currentUser = SessionHandler.getLoggerInUserInfo();
		
		if(null != this.selectedAsmt) {
			
			LOGGER.info("Selected assignment : {}", selectedAsmt.getDescription());
			try {
				selectedAsmt.setModBy(currentUser.getId());
				
				getNumberPoolSerivce().finishedAssignment(selectedAsmt);
				getAllAssignmentFromPool();
				
				LOGGER.info("Marking assignment as completed for assignment : {}", this.selectedAsmt.getDescription());
				
				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Success",
								"Assignment successfully marked finished"));
				
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
	}
	
	public void getAllAssignmentFromPool() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			allAssignments = getNumberPoolSerivce().getAllAssignments();
			
			LOGGER.info("Extracted all assignment from pool");
			
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
	
	public Map<String, String> getAllUserMap() {
		Map<String, String> allUserM = null;
		List<AppUser> aUs = getAllAvailableUser();
		
		if(null != aUs && !aUs.isEmpty()) {
			allUserM = new HashMap<String, String>();
			
			for(AppUser au : aUs) {
				allUserM.put(au.getUName(), au.getFirstname() + " " + au.getLastname());
			}
		}
		
		return allUserM;
	}
	
	public void onUserChange() {
		this.isUserChanged = true;
	}
	
	private List<AppUser> getAllAvailableUser() {
		List<AppUser> aUser = null;
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			aUser = getUserService().getAllValidUser();
			
			ListIterator<AppUser> li = aUser.listIterator();
			
			while(li.hasNext()) {
				AppUser u = li.next();
				
				if(u.getIsServiceUser() || u.isSuperUser()) {
					li.remove();
				}
			}
			
		}  catch(ApplicationException e) {
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
		
		return aUser;
	}
	
	public Map<String, String> getAllAvailableNumbers() {
		Map<String, String> aNumbers = null;
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			List<NumberPool> aNumbersList = getNumberPoolSerivce().getAllAvailableNumbers();
			
			if(null != aNumbersList && !aNumbersList.isEmpty()) {
				aNumbers = new HashMap<String, String>();
				
				for(NumberPool np : aNumbersList) {
					aNumbers.put(np.getMsisdn(), np.getMsisdn());
				}
			}
			
		}  catch(ApplicationException e) {
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
		
		return aNumbers;
	}
	
	public Map<String, String> getAllAvailableNumbersForAsmt() {
		Map<String, String> aNumbers = null;
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			List<NumberPool> aNumbersList = getNumberPoolSerivce().getAllAvailableNumbers();
			
			if(null != aNumbersList && !aNumbersList.isEmpty()) {
				aNumbers = new HashMap<String, String>();
				
				for(NumberPool np : aNumbersList) {
					aNumbers.put(np.getMsisdn(), np.getMsisdn());
				}
			}
			
			if(null != selectedAsmt) {
				if(aNumbers == null) {
					aNumbers = new HashMap<String, String>();
				}
				
				for(NumberPool np : selectedAsmt.getNumberPools()) {
					aNumbers.put(np.getMsisdn(), np.getMsisdn());
				}
			}
			
		}  catch(ApplicationException e) {
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
		
		return aNumbers;
	}
	
	public void updateAssignment() {
		FacesContext context = FacesContext.getCurrentInstance();
		AppUser currentUser = SessionHandler.getLoggerInUserInfo();
		
		try {
			if(null != this.selectedAsmt) {
				selectedAsmt.setModBy(currentUser.getId());
				
				if(this.isUserChanged) {
					selectedAsmt.setAssignedToUser(this.selectedUsername);
				} else {
					selectedAsmt.setAssignedToUser(null);
				}
				
				
				selectedAsmt.getNumberPools().clear();
				NumberPool r = null;
				
				for(String msisdn : currentMsisdn) {
					r = new NumberPool();
					r.setMsisdn(msisdn);
					selectedAsmt.getNumberPools().add(r);
				}
				
				selectedAsmt = getNumberPoolSerivce().updateAssignment(selectedAsmt);
				getAllAssignmentFromPool();
				
				LOGGER.info("Updated selected assignment");
				currentMsisdn.clear();
				
				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Success",
								"Assignment successfully updated"));
				
			} else {
				throw new ApplicationException("No assignment selected", true);
			}
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
	
	public void createAssignment() {
		FacesContext context = FacesContext.getCurrentInstance();
		AppUser currentUser = SessionHandler.getLoggerInUserInfo();
		
		try {
			if(null != this.newAsmt) {
				newAsmt.setCreatedBy(currentUser.getId());
				
				if(null == this.selectedUsername || this.selectedUsername.isEmpty()) {
					throw new ApplicationException("No user selected for assignment", true);
				} else {
					newAsmt.setAssignedToUser(selectedUsername);
				}
				
				if(null == this.newAsmt.getNumberPools()) {
					List<NumberPool> assignedNumberPol = new ArrayList<NumberPool>();
					this.newAsmt.setNumberPools(assignedNumberPol);
					
					NumberPool np = null;
					
					for(String msisdn : selectedMsisdn) {
						np = new NumberPool();
						np.setMsisdn(msisdn);
						
						this.newAsmt.getNumberPools().add(np);
					}
				} else {
					newAsmt.getNumberPools().clear();
				}
				
				LOGGER.info("Created new assignment");
				
				getNumberPoolSerivce().createNewAssignment(this.newAsmt);
				
				this.newAsmt = new NumberAsgnmtHistory();
				
				getAllAssignmentFromPool();
				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Success",
								"Assignment successfully created"));
				
			} else {
				throw new ApplicationException("No assignment selected", true);
			}
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
	

	public boolean getDisabled() {
		return disabled;
	}


	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}


	public List<NumberAsgnmtHistory> getAllAssignments() {
		return allAssignments;
	}


	public void setAllAssignments(List<NumberAsgnmtHistory> allAssignments) {
		this.allAssignments = allAssignments;
	}


	public NumberAsgnmtHistory getSelectedAsmt() {
		return selectedAsmt;
	}


	public void setSelectedAsmt(NumberAsgnmtHistory selectedAsmt) {
		this.selectedAsmt = selectedAsmt;
	}


	public NumberAsgnmtHistory getNewAsmt() {
		return newAsmt;
	}


	public void setNewAsmt(NumberAsgnmtHistory newAsmt) {
		this.newAsmt = newAsmt;
	}


	public String getSelectedUsername() {
		return selectedUsername;
	}


	public void setSelectedUsername(String selectedUsername) {
		this.selectedUsername = selectedUsername;
	}

	public List<String> getSelectedMsisdn() {
		return selectedMsisdn;
	}

	public void setSelectedMsisdn(List<String> selectedMsisdn) {
		this.selectedMsisdn = selectedMsisdn;
	}


	public String getMsisdn() {
		return msisdn;
	}


	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public void initEditD() {
		if(null != selectedAsmt) {
			getCurrentMsisdnList();
		}
	}

	public List<String> getCurrentMsisdnList() {
		if(null == currentMsisdn || currentMsisdn.isEmpty()) {
			currentMsisdn = new ArrayList<String>();
		} else {
			currentMsisdn.clear();
		}

		if(null != selectedAsmt) {
			for(NumberPool r : selectedAsmt.getNumberPools()) {
				LOGGER.info("Current Msisdn : {}", r.getMsisdn());
				currentMsisdn.add(r.getMsisdn());
			}
		}
		
		return currentMsisdn;
	}
	
	public List<String> getCurrentMsisdn() {
		return currentMsisdn;
	}


	public void setCurrentMsisdn(List<String> currentMsisdn) {
		this.currentMsisdn = currentMsisdn;
	}

}
