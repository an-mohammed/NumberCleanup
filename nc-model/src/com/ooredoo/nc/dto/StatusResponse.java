package com.ooredoo.nc.dto;

import java.util.List;

public class StatusResponse {

	private String errorCode;
	private String errorMessage;
	private List<ProfileStatus> profileStatus;

	public List<ProfileStatus> getProfileStatus() {
		return profileStatus;
	}

	public void setProfileStatus(List<ProfileStatus> profileStatus) {
		this.profileStatus = profileStatus;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
