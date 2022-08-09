package com.ooredoo.nc.gui.managedbean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import com.ooredoo.nc.exception.ApplicationRestException;
import com.ooredoo.nc.gui.configuration.SessionHandler;
import com.ooredoo.nc.model.AppUser;
import com.ooredoo.nc.model.NcAppUserGrp;
import com.ooredoo.nc.model.Role;
import com.ooredoo.nc.utility.ApplicationUtility;

@Named(value="um")
@ViewScoped
public class UserManagement extends ManagedBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserManagement.class);
	private boolean includeInvalid = false;
	private String name;
	private String username;
	private String email;
	private String mobile;
	private List<AppUser> userList;
	private List<AppUser> filteredUserList;
	private AppUser selectedUser;
	private boolean disabled = true;
	private List<String> currentRoleList;
	private List<String> currentGrpList;
	private List<String> newUserRoleList;
	private List<String> newUserGrpList;
	private AppUser newUser;
	private Map<String, String> allValidRoleList;
	private Map<String, String> allValidGrpList;
	private Boolean adminUser;
	private Boolean superUser;
	private Boolean disableAdminUser = false;
	private Boolean disableSuperUser = false;
	private Boolean disableRole = false;
	private Boolean disableLdapAuth = false;
	
	public String closeMu() {
		includeInvalid = false;
		name = null;
		username = null;
		email = null;
		mobile = null;
		newUserRoleList = null;
		newUser = new AppUser();
		allValidRoleList = null;
		adminUser = false;
		superUser = false;
		disableAdminUser = false;
		disableSuperUser = false;
		disableRole = false;
		
		return "manageUser";
	}
	
	public String closeCu() {
		includeInvalid = false;
		name = null;
		username = null;
		email = null;
		mobile = null;
		newUserRoleList = null;
		newUser = new AppUser();
		allValidRoleList = null;
		adminUser = false;
		superUser = false;
		disableAdminUser = false;
		disableSuperUser = false;
		disableRole = false;
		
		return "createUser";
	}
	
	public void onSelectingAdmin() {
		if(adminUser) {
			disableSuperUser = true;
			disableRole = true;
			disableLdapAuth = true;
		} else {
			disableSuperUser = false;
			disableRole = false;
			disableLdapAuth = false;
		}
	}
	
	public void onSelectingSU() {
		if(superUser) {
			disableAdminUser = true;
			disableRole = true;
			disableLdapAuth = true;
		} else {
			disableAdminUser = false;
			disableRole = false;
			disableLdapAuth = false;
		}
	}
	
	
	public void onRowSelect(SelectEvent selectEvent) {
		disabled = false;
	}
	
	public void onRowUnselect(UnselectEvent selectEvent) {
		disabled = true;
	}
	
	@PostConstruct
	public void init() {
		this.newUser = new AppUser();
		superUser = false;
		adminUser = false;
	}
	
	public void searchUser() {
		FacesContext context = FacesContext.getCurrentInstance();
		AppUser currentUser = SessionHandler.getLoggerInUserInfo();
		 
		try {
			String actionComments = "Searching user for name :" + name + " and username:" + username 
					+ " and email:" + email + " and mobile:" + mobile + " and includeInvalid:" + includeInvalid;
			LOGGER.info(actionComments);
			
			userList = getUserService().searchUser(username, name, mobile, email, includeInvalid);
			
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
	public void addNewUser() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			
			List<Role> roleList = new ArrayList<Role>();
			Role newRole = null;
			
			List<NcAppUserGrp> grpList = new ArrayList<NcAppUserGrp>();
			NcAppUserGrp grp = null;
			
			if(superUser) {
				for (Role r : getRoleService().getAllRoles(null, null)) {
					if(r.getIsSuperUserRole()) {
						newRole = new Role();
						newRole.setName(r.getName());
						roleList.add(newRole);
						break;
					} 
				}
			} else if(adminUser) {
				for (Role r : getRoleService().getAllRoles(null, null)) {
					if(r.getIsAdminRole()) {
						newRole = new Role();
						newRole.setName(r.getName());
						roleList.add(newRole);
						break;
					} 
				}
			} else {
				if(null == newUserRoleList || newUserRoleList.isEmpty()) {
					throw new ApplicationException("AUI.001");
					
				} else {
					for (String r : newUserRoleList) {
						newRole = new Role();
						newRole.setName(r);
						roleList.add(newRole);
					}
				}
			}
			
			newUser.setRoles(roleList);
			
			if(null != newUserGrpList && !newUserGrpList.isEmpty()) {
				for (String r : newUserGrpList) {
					grp = new NcAppUserGrp();
					grp.setGrpName(r);
					grpList.add(grp);
				}
				
				newUser.setNcUserGrps(grpList);
			}
			
			
			newUser.setCreatedby(SessionHandler.getLoggerInUserInfo().getUName());
			
			if(superUser || adminUser) {
				newUser.setLdapauthentication(false);
			}
			
			getUserService().createUser(newUser);
			
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Success",
							"User successfully created with default password"));
			
			this.newUser = new AppUser();
			superUser = false;
			adminUser = false;
			disableAdminUser = false;
			disableSuperUser = false;
			disableRole = false;
			disableLdapAuth = false;
			
		} catch (ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while logging into from application", e);
		} catch (Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
		}
	}
	
	public void initEditD() {
		if(null != selectedUser) {
			if(selectedUser.isSuperUser()) {
				superUser = true;
				onSelectingSU();
				
			} else if(selectedUser.isAdminUser()) {
				adminUser = true;
				onSelectingAdmin();
			}
			
			getCurrentRoleList();
		}
	}
	
	public void updateUser() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			
			selectedUser.getRoles().clear();
			
			if(superUser) {
				selectedUser.getRoles().clear();
				for(Role role : getRoleService().getAllRoles(null, null)) {
					if(role.getIsSuperUserRole()) {
						Role r = getRoleService().getRoleDetails(role.getName(), null, null);
						selectedUser.getRoles().add(r);
					}
				}
				selectedUser.setLdapauthentication(false);
				
			} else if(adminUser) {
				selectedUser.getRoles().clear();
				
				for(Role role : getRoleService().getAllRoles(null, null)) {
					if(role.getIsAdminRole()) {
						Role r = getRoleService().getRoleDetails(role.getName(), null, null);
						selectedUser.getRoles().add(r);
					}
				}
				selectedUser.setLdapauthentication(false);
				
			} else {
				selectedUser.getRoles().clear();
				
				for(String roleName : currentRoleList) {
					Role r = getRoleService().getRoleDetails(roleName, null, null);
					selectedUser.getRoles().add(r);
				}
				//selectedUser.setLdapauthentication(true);
			}
			
			if(null != selectedUser.getNcUserGrps()) {
				selectedUser.getNcUserGrps().clear();
			} else {
				List<NcAppUserGrp> ncUserGrps = new ArrayList<NcAppUserGrp>();
				selectedUser.setNcUserGrps(ncUserGrps);
			}
			
			if(null != currentGrpList && !currentGrpList.isEmpty()) {
				for(String grpName : currentGrpList) {
					LOGGER.info("Processing group : {}", grpName);
					NcAppUserGrp g = getUserGrpService().getGroupDetails(grpName);
					selectedUser.getNcUserGrps().add(g);
				}
			}
			
			selectedUser.setModifiedby(SessionHandler.getLoggerInUserInfo().getUName());
			selectedUser.setModifieddate(new Date());
			
			getUserService().updateUser(selectedUser);
			
			if(null != userList) {
				userList.clear();
			}
			
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Success",
							"User successfully updated with new values"));
			LOGGER.info("User successfully updated");
		} catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while logging into from application", e);
			
		} finally {
			if(null != currentRoleList) {
				currentRoleList.clear();
			}			
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

	public void setCurrentRoleList(List<String> currentRoleList) {
		this.currentRoleList = currentRoleList;
	}
	
	public Map<String, String> getAllValidRoleList() {
		FacesContext context = FacesContext.getCurrentInstance();
		allValidRoleList = new HashMap<String, String>();
		
		try {
			for (Role r : getRoleService().getAllRoles(null, null)) {
				if(r.getName().equals("ROLE_CAS")) {
					continue;
				} else if (r.getIsSuperUserRole()) {
					continue;
				} else if (r.getIsAdminRole()) {
					continue;
				} else {
					allValidRoleList.put(r.getName(), r.getDescription());
				}
			}
		} catch(ApplicationRestException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while logging into from application", e);
			
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
	
	public Map<String, String> getAllValidGroupList() {
		FacesContext context = FacesContext.getCurrentInstance();
		allValidGrpList = new HashMap<String, String>();
		
		try {
			for (NcAppUserGrp g : getUserGrpService().getAllGroups()) {
				allValidGrpList.put(g.getGrpName(), g.getGrpDesc());
			}
		} catch(ApplicationRestException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while extracting all groups", e);
			
		} catch(Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
			getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
			
		}
		
		return allValidGrpList;
	}
	
	public void setAllValidRoleList(Map<String, String> allValidRoleList) {
		this.allValidRoleList = allValidRoleList;
	}

	public AppUser getNewUser() {
		return newUser;
	}

	public void setNewUser(AppUser newUser) {
		this.newUser = newUser;
	}

	public List<String> getNewUserRoleList() {
		return newUserRoleList;
	}

	public void setNewUserRoleList(List<String> newUserRoleList) {
		this.newUserRoleList = newUserRoleList;
	}

	public Boolean getDisableAdminUser() {
		return disableAdminUser;
	}

	public void setDisableAdminUser(Boolean disableAdminUser) {
		this.disableAdminUser = disableAdminUser;
	}

	public Boolean getDisableSuperUser() {
		return disableSuperUser;
	}

	public void setDisableSuperUser(Boolean disableSuperUser) {
		this.disableSuperUser = disableSuperUser;
	}

	public Boolean getDisableRole() {
		return disableRole;
	}

	public void setDisableRole(Boolean disableRole) {
		this.disableRole = disableRole;
	}

	public List<String> getCurrentRoleList() {
		if(null == currentRoleList || currentRoleList.isEmpty()) {
			currentRoleList = new ArrayList<String>();
			
			if(null != selectedUser) {
				for(Role r : selectedUser.getRoles()) {
					currentRoleList.add(r.getName());
				}
			}
		}
		
		return currentRoleList;
	}

	public Boolean getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(Boolean adminUser) {
		this.adminUser = adminUser;
	}

	public Boolean getSuperUser() {
		return superUser;
	}

	public void setSuperUser(Boolean superUser) {
		this.superUser = superUser;
	}

	public Boolean getDisableLdapAuth() {
		return disableLdapAuth;
	}

	public void setDisableLdapAuth(Boolean disableLdapAuth) {
		this.disableLdapAuth = disableLdapAuth;
	}

	public Map<String, String> getAllValidGrpList() {
		return allValidGrpList;
	}

	public void setAllValidGrpList(Map<String, String> allValidGrpList) {
		this.allValidGrpList = allValidGrpList;
	}

	public List<String> getNewUserGrpList() {
		return newUserGrpList;
	}

	public void setNewUserGrpList(List<String> newUserGrpList) {
		this.newUserGrpList = newUserGrpList;
	}

	public List<String> getCurrentGrpList() throws ApplicationRestException {
		if(null == currentGrpList || currentGrpList.isEmpty()) {
			currentGrpList = new ArrayList<String>();
			
			if(null != selectedUser) {
				List<String> grpList = getUserGrpService().getAllGroupsForUser(String.valueOf(selectedUser.getId()));
				
				if(null != grpList && !grpList.isEmpty()) {
					currentGrpList.addAll(grpList);
				}
				
				/*
				for(NcAppUserGrp r : selectedUser.getNcUserGrps()) {
					currentGrpList.add(r.getGrpName());
				}
				*/
			}
		}
		
		return currentGrpList;
	}

	public void setCurrentGrpList(List<String> currentGrpList) {
		this.currentGrpList = currentGrpList;
	}
}
