package com.ooredoo.nc.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SubscriberProfileCleanupResponse {

	private String requestUsername;
	private Date requestDate;
	private Date responseDate;
	private String cleanupStatus;
	private List<SubscriberProfileDetails> profiles;
	private Map<String, CleanupDetails> cleanupDetails;
	
	public String getRequestUsername() {
		return requestUsername;
	}
	public void setRequestUsername(String requestUsername) {
		this.requestUsername = requestUsername;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public List<SubscriberProfileDetails> getProfiles() {
		if(null == this.profiles) {
			this.profiles = new ArrayList<SubscriberProfileDetails>();
		}
		return profiles;
	}
	public void setProfiles(List<SubscriberProfileDetails> profiles) {
		this.profiles = profiles;
	}
	public Date getResponseDate() {
		return responseDate;
	}
	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}
	public Map<String, CleanupDetails> getCleanupDetails() {
		return cleanupDetails;
	}
	public void setCleanupDetails(Map<String, CleanupDetails> cleanupDetails) {
		this.cleanupDetails = cleanupDetails;
	}
	public String getCleanupStatus() {
		return cleanupStatus;
	}
	public void setCleanupStatus(String cleanupStatus) {
		this.cleanupStatus = cleanupStatus;
	}
}
