package com.ooredoo.nc.dto;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class UserPrinciple implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(notes = "Username of the user which needs to be authenticated")
	private String username;
	 
	@ApiModelProperty(notes = "Current password of the user") 
	private String currentPassword;
	
	@ApiModelProperty(notes = "The new password user want to update with")
	private String newPassword;
	
	@ApiModelProperty(notes = "Flag to identify whether user wants to update his default password")
	private Boolean updateDefaultPassword;
	
	@ApiModelProperty(notes = "Flag to identify whether user password needs to be rest to default")
	private Boolean resetToDefaultPassword;
	
	@ApiModelProperty(notes = "List of the roles that user need to assigned")
	private List<String> roles;
	
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public Boolean getUpdateDefaultPassword() {
		return updateDefaultPassword;
	}
	public void setUpdateDefaultPassword(Boolean updateDefaultPassword) {
		this.updateDefaultPassword = updateDefaultPassword;
	}
	public Boolean getResetToDefaultPassword() {
		return resetToDefaultPassword;
	}
	public void setResetToDefaultPassword(Boolean resetToDefaultPassword) {
		this.resetToDefaultPassword = resetToDefaultPassword;
	}
	
}
