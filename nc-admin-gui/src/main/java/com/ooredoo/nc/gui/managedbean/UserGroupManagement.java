package com.ooredoo.nc.gui.managedbean;

import java.util.ArrayList;
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

import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.gui.configuration.SessionHandler;
import com.ooredoo.nc.model.AppUser;
import com.ooredoo.nc.model.NcAppUserGrp;
import com.ooredoo.nc.model.NumberPool;

@Named(value="ugm")
@ViewScoped
public class UserGroupManagement extends ManagedBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserGroupManagement.class);
	private List<NcAppUserGrp> groupList;
	private List<NcAppUserGrp> filteredGroupList;
	private NcAppUserGrp selectedGroupList;
	private NcAppUserGrp newGroup;
	private boolean disabled = true;
	private List<NumberPool> allNumbers;
	private List<AppUser> allUsers;
	private List<String> alocatedNumbersToNewGroup;
	private List<String> alocatedUsersToNewGroup;
	private List<String> curNumbersToGroup;
	private List<String> curUsersToGroup;
	

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		this.newGroup = new NcAppUserGrp();
		this.alocatedNumbersToNewGroup = new ArrayList<String>();
		
		try {
			this.groupList = getUserGrpService().getAllGroups();
			
		} catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while getting user groups. Cause", e);
			
		} catch(Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
		}
	}
	
	public String closeMug() {
		selectedGroupList = null;
		groupList = null;
		filteredGroupList= null;
		
		return "manageUserGroup";
	}

	public void onRowSelect(SelectEvent selectEvent) {
		disabled = false;
	}
	
	public void onRowUnselect(UnselectEvent selectEvent) {
		disabled = true;
	}
	
	public List<NumberPool> getAllNumbersFromPool() {
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
		
		return allNumbers;
	}
	
	public List<AppUser> getAllUsers() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			allUsers = getUserService().searchUser(null, null, null, null, true);
			LOGGER.info("Extracted all users");
			
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
		
		return allUsers;
	}
	
	public void createNewGroup() {
		FacesContext context = FacesContext.getCurrentInstance();
		AppUser currentUser = SessionHandler.getLoggerInUserInfo();
		
		try {
			
			if(null == this.newGroup.getGrpName() || this.newGroup.getGrpName().isEmpty()) {
				throw new ApplicationException("No group code specified", true);
			}
			
			if(null == this.newGroup.getGrpDesc() || this.newGroup.getGrpDesc().isEmpty()) {
				throw new ApplicationException("No group name specified", true);
			}
			
			/*if(null == this.alocatedNumbersToNewGroup || this.alocatedNumbersToNewGroup.isEmpty()) {
				throw new ApplicationException("No Numbers selected for the group", true);
			}*/
			
			if(null == this.alocatedUsersToNewGroup || this.alocatedUsersToNewGroup.isEmpty()) {
				throw new ApplicationException("No users selected for the group", true);
			}
			
			NumberPool np = null;
			List<NumberPool> npList = new ArrayList<NumberPool>();
			
			for(String selectedNo : this.alocatedNumbersToNewGroup) {
				np = new NumberPool();
				np.setMsisdn(selectedNo);
				npList.add(np);
			}
			
			newGroup.setAllocatedNumbers(npList);
			
			AppUser u = null;
			List<AppUser> uList = new ArrayList<AppUser>();
			
			for(String selectedU : this.alocatedUsersToNewGroup) {
				u = new AppUser();
				u.setuName(selectedU);
				u.setuName(selectedU);
				uList.add(u);
			}
			
			newGroup.setNcUserGrps(uList);
			
			newGroup.setCreatedBy(currentUser.getId());
			newGroup = getUserGrpService().createGroup(newGroup);
			
			this.groupList = getUserGrpService().getAllGroups();
			LOGGER.info("New User Group created successfully");
			
			this.alocatedNumbersToNewGroup.clear();
			this.alocatedUsersToNewGroup.clear();
			this.newGroup = new NcAppUserGrp();
			
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Success",
							"New User Group created successfully"));
			
		} catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while creating new user group. Cause", e);
			
		} catch(Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
		}
	}
	
	public void updateGroup() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			
			if(null == this.selectedGroupList.getGrpDesc() || this.selectedGroupList.getGrpDesc().isEmpty()) {
				throw new ApplicationException("No group name specified", true);
			}
			
			if(null == this.curNumbersToGroup || this.curNumbersToGroup.isEmpty()) {
				throw new ApplicationException("No Numbers selected for the group", true);
			}
			
			if(null == this.curUsersToGroup || this.curUsersToGroup.isEmpty()) {
				throw new ApplicationException("No users selected for the group", true);
			}
			
			NumberPool np = null;
			List<NumberPool> npList = new ArrayList<NumberPool>();
			
			for(String selectedNo : this.curNumbersToGroup) {
				np = new NumberPool();
				np.setMsisdn(selectedNo);
				npList.add(np);
			}
			
			if(null != selectedGroupList.getAllocatedNumbers() && !selectedGroupList.getAllocatedNumbers().isEmpty()) {
				selectedGroupList.getAllocatedNumbers().clear();
			}
			selectedGroupList.setAllocatedNumbers(npList);
			
			AppUser u = null;
			List<AppUser> uList = new ArrayList<AppUser>();
			
			for(String selectedU : this.curUsersToGroup) {
				u = new AppUser();
				u.setuName(selectedU);
				u.setuName(selectedU);
				uList.add(u);
			}
			
			if(null != selectedGroupList.getNcUserGrps() && !selectedGroupList.getNcUserGrps().isEmpty()) {
				selectedGroupList.getNcUserGrps().clear();
			}
			
			selectedGroupList.setNcUserGrps(uList);
			
			selectedGroupList = getUserGrpService().updateGroup(selectedGroupList);
			
			this.groupList = getUserGrpService().getAllGroups();
			LOGGER.info("New User Group updated successfully");
			
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Success",
							"New User Group updated successfully"));
			
		} catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while creating new user group. Cause", e);
			
		} catch(Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
		}
	}
	
	public List<NcAppUserGrp> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<NcAppUserGrp> groupList) {
		this.groupList = groupList;
	}

	public List<NcAppUserGrp> getFilteredGroupList() {
		return filteredGroupList;
	}

	public void setFilteredGroupList(List<NcAppUserGrp> filteredGroupList) {
		this.filteredGroupList = filteredGroupList;
	}

	public NcAppUserGrp getSelectedGroupList() {
		return selectedGroupList;
	}

	public void setSelectedGroupList(NcAppUserGrp selectedGroupList) {
		this.selectedGroupList = selectedGroupList;
	}

	public NcAppUserGrp getNewGroup() {
		return newGroup;
	}

	public void setNewGroup(NcAppUserGrp newGroup) {
		this.newGroup = newGroup;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public List<NumberPool> getAllNumbers() {
		return allNumbers;
	}

	public void setAllNumbers(List<NumberPool> allNumbers) {
		this.allNumbers = allNumbers;
	}

	public List<String> getAlocatedNumbersToNewGroup() {
		return alocatedNumbersToNewGroup;
	}

	public void setAlocatedNumbersToNewGroup(List<String> alocatedNumbersToNewGroup) {
		this.alocatedNumbersToNewGroup = alocatedNumbersToNewGroup;
	}

	public void setAllUsers(List<AppUser> allUsers) {
		this.allUsers = allUsers;
	}

	public List<String> getAlocatedUsersToNewGroup() {
		return alocatedUsersToNewGroup;
	}

	public void setAlocatedUsersToNewGroup(List<String> alocatedUsersToNewGroup) {
		this.alocatedUsersToNewGroup = alocatedUsersToNewGroup;
	}

	public List<String> getCurNumbersToGroup() {
		
		if(null != this.selectedGroupList && null != this.selectedGroupList.getAllocatedNumbers() && !this.selectedGroupList.getAllocatedNumbers().isEmpty()) {
			if(null != curNumbersToGroup) {
				this.curNumbersToGroup.clear();
			} else {
				this.curNumbersToGroup = new ArrayList<String>();
			}
			
			for(NumberPool np : this.selectedGroupList.getAllocatedNumbers()) {
				curNumbersToGroup.add(np.getMsisdn());
			}
		}
		return curNumbersToGroup;
	}

	public void setCurNumbersToGroup(List<String> curNumbersToGroup) {
		
		this.curNumbersToGroup = curNumbersToGroup;
	}

	public List<String> getCurUsersToGroup() {
		
		if(null != this.selectedGroupList && null != this.selectedGroupList.getGroupMembers() && !this.selectedGroupList.getGroupMembers().isEmpty()) {
			if(null != curUsersToGroup) {
				this.curUsersToGroup.clear();
			} else {
				this.curUsersToGroup = new ArrayList<String>();
			}
			
			for(AppUser a : this.selectedGroupList.getGroupMembers()) {
				curUsersToGroup.add(a.getUName());
			}
		}
		
		return curUsersToGroup;
	}

	public void setCurUsersToGroup(List<String> curUsersToGroup) {
		this.curUsersToGroup = curUsersToGroup;
	}
}
