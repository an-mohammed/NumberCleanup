package com.ooredoo.nc.dto;

import java.util.List;

public class StatusRequest {

	private List<ProfileStatus> profileStatus;

	public List<ProfileStatus> getProfileStatus() {
		return profileStatus;
	}

	public void setProfileStatus(List<ProfileStatus> profileStatus) {
		this.profileStatus = profileStatus;
	}
}
