package com.ooredoo.nc.gui.managedbean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.exception.ApplicationRestException;
import com.ooredoo.nc.gui.configuration.SessionHandler;
import com.ooredoo.nc.model.AppUser;
import com.ooredoo.nc.model.Role;
import com.ooredoo.nc.utility.ApplicationUtility;

@Named(value="sum")
@ViewScoped
public class ServiceUserManagement extends ManagedBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceUserManagement.class);
	private boolean includeInvalid = false;
	private String name;
	private String username;
	private String email;
	private String mobile;
	private List<AppUser> userList;
	private List<AppUser> filteredUserList;
	private AppUser selectedUser;
	private boolean disabled = true;
	private AppUser newServiceUser;
	private String newUserRole;
	private Map<String, String> allValidRoleList;
	
	public void close() {
		includeInvalid = false;
		name = null;
		username = null;
		email = null;
		mobile = null;
		newServiceUser = new AppUser();
	}
	
	public void onRowSelect(SelectEvent selectEvent) {
		disabled = false;
	}
	
	public void onRowUnselect(UnselectEvent selectEvent) {
		disabled = true;
	}
	
	@PostConstruct
	public void init() {
		this.newServiceUser = new AppUser();
		newServiceUser.setLdapauthentication(false);
		
	}
	
	public String back() {
		return "backOffice";
	}
	
	public void onRowEdit(RowEditEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			AppUser u = (AppUser) event.getObject();
			u.setModifiedby(SessionHandler.getLoggerInUserInfo().getUName());
			getUserService().updateServiceUser(u);
			
	        FacesMessage msg = new FacesMessage("Service user information successfully saved.");
		    FacesContext.getCurrentInstance().addMessage(null, msg);
		        
		}  catch(ApplicationException e) {
			FacesContext.getCurrentInstance().validationFailed();
			
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			
			LOGGER.error("Error Occurred while saving Service User Info. Cause", e);
			
		} catch(Exception e) {
			FacesContext.getCurrentInstance().validationFailed();
			
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
			
		}
    }
	
	public void searchUser() {
		FacesContext context = FacesContext.getCurrentInstance();
		 
		AppUser currentUser = SessionHandler.getLoggerInUserInfo();
		 
		 
		try {
			String actionComments = "Searching user for name :" + name + " and username:" + username 
					+ " and email:" + email + " and mobile:" + mobile + " and includeInvalid:" + includeInvalid;
			LOGGER.info(actionComments);
			
			userList = getUserService().searchServiceUser(username, name, mobile, email, includeInvalid);
			
			if(null == userList || userList.isEmpty()) {
				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Success",
								ApplicationUtility.getMessage("AUI.006", null)));
				
			} else {
				Iterator<AppUser> it = userList.iterator();
				
				 if(currentUser.isSuperUser()) {
					 
				 }else if(currentUser.isAdminUser()) {
					while(it.hasNext()) {
						AppUser u = it.next();
						
						for(Role r : u.getRoles()) {
							if(r.getIsSuperUserRole()) {
								it.remove();
							}
						}
					}
				} else {
					while(it.hasNext()) {
						AppUser u = it.next();
						
						for(Role r : u.getRoles()) {
							if(r.getIsSuperUserRole() || r.getIsAdminRole()) {
								it.remove();
							}
						}
					}
				}
			}
			
			LOGGER.info("Response List size : " + (null != userList ? userList.size() : "0"));
			
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
	
	/**
	 * 
	 */
	public void addNewServiceUser() {
	FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			
			List<Role> roleList = null;
			Role newRole = null;
			
			if(null != newUserRole && !newUserRole.isEmpty()) {
				roleList = new ArrayList<Role>();
				newRole = new Role();
				newRole.setName(newUserRole);
				roleList.add(newRole);
				newServiceUser.setRoles(roleList);
			}
			
			newServiceUser.setCreatedby(SessionHandler.getLoggerInUserInfo().getUName());
			newServiceUser.setLdapauthentication(false);
			
			AppUser newU = getUserService().createServiceUser(newServiceUser);
			
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Success",
							"User created. Password :" + newU.getUPwd() + " " + newU.getComments()));
			
		} catch (ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while creating service user", e);
		} catch (Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
		}
	}
	
	public List<AppUser> getUserList() {
		return userList;
	}

	public void setUserList(List<AppUser> userList) {
		this.userList = userList;
	}

	public List<AppUser> getFilteredUserList() {
		return filteredUserList;
	}

	public void setFilteredUserList(List<AppUser> filteredUserList) {
		this.filteredUserList = filteredUserList;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public boolean isIncludeInvalid() {
		return includeInvalid;
	}
	public void setIncludeInvalid(boolean includeInvalid) {
		this.includeInvalid = includeInvalid;
	}

	public AppUser getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(AppUser selectedUser) {
		this.selectedUser = selectedUser;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public AppUser getNewServiceUser() {
		return newServiceUser;
	}

	public void setNewServiceUser(AppUser newServiceUser) {
		this.newServiceUser = newServiceUser;
	}

	public Map<String, String> getAllValidRoleList() {
		FacesContext context = FacesContext.getCurrentInstance();
		allValidRoleList = new HashMap<String, String>();
		
		try {
			for (Entry<String, String> r : getUserService().getAllServiceRoles().entrySet()) {
				allValidRoleList.put(r.getValue(), r.getKey());
			}
		} catch(ApplicationRestException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while extracting all service roles", e);
			
		} catch(Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
		}
		
		return allValidRoleList;
	}

	public String getNewUserRole() {
		return newUserRole;
	}

	public void setNewUserRole(String newUserRole) {
		this.newUserRole = newUserRole;
	}
}
