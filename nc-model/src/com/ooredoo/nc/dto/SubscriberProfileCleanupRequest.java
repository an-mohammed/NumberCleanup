package com.ooredoo.nc.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SubscriberProfileCleanupRequest {

	private String requestUsername;
	private Date requestDate;
	private List<SubscriberProfileDetails> profiles;
	private List<String> nodes;
	
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
	public List<String> getNodes() {
		if(null == this.nodes) {
			this.nodes = new ArrayList<String>();
		}
		return nodes;
	}
	public void setNodes(List<String> nodes) {
		this.nodes = nodes;
	}
	
	@Override
	public String toString() {
		return "SubscriberProfileCleanupRequest [requestUsername=" + requestUsername + ", requestDate=" + requestDate
				+ ", profiles=" + profiles + ", nodes=" + nodes + "]";
	}
}
