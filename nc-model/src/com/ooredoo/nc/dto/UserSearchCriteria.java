package com.ooredoo.nc.dto;

import java.io.Serializable;

public class UserSearchCriteria implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean includeInvalid = false;
	private String name;
	private String username;
	private String email;
	private String mobile;
	
	public boolean isIncludeInvalid() {
		return includeInvalid;
	}
	public void setIncludeInvalid(boolean includeInvalid) {
		this.includeInvalid = includeInvalid;
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

}
