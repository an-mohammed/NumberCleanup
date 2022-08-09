package com.ooredoo.nc.gui.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.gui.configuration.SessionHandler;
import com.ooredoo.nc.model.Privilege;
import com.ooredoo.nc.model.Role;
import com.ooredoo.nc.utility.ApplicationConstants;

@Named(value="bo")
@ViewScoped
public class BackOfficeTask extends ManagedBean implements Serializable, ApplicationConstants {

	private static final long serialVersionUID = 1L;
	private String currentPassword = null;
	private String newPassword = null;
	private String confirmNewPassword = null;
	private String username = null;
	private Map<String, String> allRoleNames = null;
	private String selectedRoleName = null;
	private List<String> availablePrivNames = null;
	private List<String> availablePrivNamesNr = null;
	private Map<String, String> allPrivNames = null;
	private String selectedPrivName = null;
	private Role selectedRole = null;
	private Role newRole = null;
	private String newPriv = null;
	private String newPrivDesc = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(BackOfficeTask.class);
	
	public void resetFields() {
		this.currentPassword = null;
		this.newPassword = null;
		this.confirmNewPassword = null;
	}
	
	/**
	 * Method to reset password for self
	 */
	/*public void resetPasswordForSelf() {
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
				
				getUserService().resetPassword(userP, AuditLogActions.R_S_P.getAction(), getActionUser());
				
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
			LOGGER.error("Error Occurred while resetting password", e);
			
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
	
	*//**
	 * Method to reset password for others
	 *//*
	public void resetPasswordForOther() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			UserPrinciple userP = new UserPrinciple();
			userP.setUsername(username);
			userP.setUpdateDefaultPassword(false);
			userP.setResetToDefaultPassword(true);
			
			getUserService().resetPassword(userP, AuditLogActions.R_O_P.getAction(), getActionUser());
			
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Success",
							"Password successfully updated with default password"));
			
		} catch(ApplicationException e) {
			
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while resetting password for : " + username, e);
			
		} catch (Exception e) {
			
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
	
	*/
	
	@PostConstruct
	public void init() {
		initManageRole();
		initCreateRole();
	}
	/**
	 * Initiate Role Management dialogue
	 */
	public void initManageRole() {
		 FacesContext context = FacesContext.getCurrentInstance();
		 
		try {
			allRoleNames = new HashMap<String, String>();
			allPrivNames = new HashMap<String, String>();
			
			for(Role r : getRoleService().getAllRoles(null, null)) {
				
				if(r.getIsServiceRole()) {
					continue;
				} else {
					allRoleNames.put(r.getName(), r.getDescription());
				}
			}
			
			for(Privilege p : getRoleService().getAllPrivlege(null, null)) {
				allPrivNames.put(p.getPName(), p.getDescription());
			}
			
		} catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while initiating manage role", e);
			
		} catch(Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
			
		}
	}
	
	public void closeManageRoleD() {
		if(null != allRoleNames) {
			allRoleNames = null;
		}
		
		if(null != allPrivNames ) {
			allPrivNames = null;
		}
		
		selectedRoleName = null;
		availablePrivNames = null;
		selectedPrivName = null;
		selectedRole = null;
	}
	
	public void closeCreatePrivD() {
		newPriv = null;
		newPrivDesc = null;
	}
	
	/**
	 * Method to create new privilege
	 */
	public void createNewPriv() {
		FacesContext context = FacesContext.getCurrentInstance();
		 
		try {
			Privilege p = new Privilege();
			p.setPName(newPriv);
			p.setDescription(newPrivDesc);
			p.setCreatedby(SessionHandler.getLoggerInUserInfo().getId());
			getRoleService().createPrivilege(p, null, null);
			
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Error",
							"Privelege successfully created"));
			
		}  catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while changing role", e);
		} catch(Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
			
		}finally {
			newPriv = null;
			newPrivDesc = null;
			
			try {
				for(Privilege p : getRoleService().getAllPrivlege(null, null)) {
					allPrivNames.put(p.getPName(), p.getDescription());
				}
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
	
	/**
	 * Method to handle event of changing role
	 */
	public void onRoleChange() {
		 FacesContext context = FacesContext.getCurrentInstance();
		 
		try {
			selectedRole = getRoleService().getRoleDetails(selectedRoleName, null, null);
			availablePrivNames = new ArrayList<String>();
			
			if(null != selectedRole && null != selectedRole.getPrivileges())
			for(Privilege p : selectedRole.getPrivileges()) {
				availablePrivNames.add(p.getPName());
			}
			
		}  catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while changing role", e);
			
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
	 * Method to update role definition
	 */
	public void updateRole() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			
			if(null == availablePrivNames || availablePrivNames.isEmpty()) {
				throw new ApplicationException("AUI.003");
			} 
			
			List<Privilege> updatedPrivList = new ArrayList<Privilege>();
			Privilege newP = null;
			
			for(String p : availablePrivNames) {
				newP = new Privilege();
				newP.setPName(p);
				updatedPrivList.add(newP);
			}
			
			if(null != selectedRole.getPrivileges()) {
				selectedRole.getPrivileges().clear();
			}
			
			selectedRole.setPrivileges(updatedPrivList);
			selectedRole.setModifiedby(SessionHandler.getLoggerInUserInfo().getUName());
			
			getRoleService().updateRole(selectedRole);
			
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Success",
							"Role updated successfully with new authorities"));
			
		}  catch(ApplicationException e) {
			
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while updating role", e);
			
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
	 * Method to initiate role creation definition
	 */
	public void initCreateRole() {
		 FacesContext context = FacesContext.getCurrentInstance();
		 
			try {
				newRole = new Role();
				allPrivNames = new HashMap<String, String>();
				
				for(Privilege p : getRoleService().getAllPrivlege(null, null)) {
					allPrivNames.put(p.getPName(), p.getDescription());
				}
				
			} catch(ApplicationException e) {
				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error",
								e.getErrorMessage()));
				LOGGER.error("Error Occurred while initiating new role creation", e);
				
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
	 * Method to handle new role creation
	 */
	public void createNewRole() {
		 FacesContext context = FacesContext.getCurrentInstance();
		 List<Privilege> pList = new ArrayList<Privilege>();
		 Privilege p = null;
		 
		try {
			
			for(String privName : availablePrivNamesNr) {
				p= new Privilege();
				p.setPName(privName);
				pList.add(p);
			}
			
			newRole.setCreatedby(SessionHandler.getLoggerInUserInfo().getUName());
			newRole.setCreateddate(new Date());
			newRole.setIsServiceRole(false);
			newRole.setPrivileges(pList);
			
			getRoleService().createNewRole(newRole);
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Success",
							"Role " + newRole.getName() + " created successfully with authorities"));
			
		} catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while creating new role", e);
		} catch(Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
		} finally {
			try {
				newRole = null;
				if (null != availablePrivNamesNr) {
					availablePrivNamesNr.clear();
				}
				
				allRoleNames.clear();
				
				for(Role r : getRoleService().getAllRoles(null, null)) {
					
					if(r.getIsServiceRole()) {
						continue;
					} else {
						allRoleNames.put(r.getName(), r.getDescription());
					}
				}
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
	
	public void closeCreateRoleD() {
		newRole = null;
		allPrivNames = null;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Role getSelectedRole() {
		return selectedRole;
	}

	public void setSelectedRole(Role selectedRole) {
		this.selectedRole = selectedRole;
	}

	public Map<String, String> getAllRoleNames() {
		return allRoleNames;
	}

	public void setAllRoleNames(Map<String, String> allRoleNames) {
		this.allRoleNames = allRoleNames;
	}

	public String getSelectedRoleName() {
		return selectedRoleName;
	}

	public void setSelectedRoleName(String selectedRoleName) {
		this.selectedRoleName = selectedRoleName;
	}

	public Map<String, String> getAllPrivNames() {
		return allPrivNames;
	}

	public void setAllPrivNames(Map<String, String> allPrivNames) {
		this.allPrivNames = allPrivNames;
	}

	public String getSelectedPrivName() {
		return selectedPrivName;
	}

	public void setSelectedPrivName(String selectedPrivName) {
		this.selectedPrivName = selectedPrivName;
	}

	public List<String> getAvailablePrivNames() {
		return availablePrivNames;
	}

	public void setAvailablePrivNames(List<String> availablePrivNames) {
		this.availablePrivNames = availablePrivNames;
	}

	public Role getNewRole() {
		return newRole;
	}

	public void setNewRole(Role newRole) {
		this.newRole = newRole;
	}

	public String getNewPriv() {
		return newPriv;
	}

	public void setNewPriv(String newPriv) {
		this.newPriv = newPriv;
	}

	public String getNewPrivDesc() {
		return newPrivDesc;
	}

	public void setNewPrivDesc(String newPrivDesc) {
		this.newPrivDesc = newPrivDesc;
	}

	public List<String> getAvailablePrivNamesNr() {
		return availablePrivNamesNr;
	}

	public void setAvailablePrivNamesNr(List<String> availablePrivNamesNr) {
		this.availablePrivNamesNr = availablePrivNamesNr;
	}
}
