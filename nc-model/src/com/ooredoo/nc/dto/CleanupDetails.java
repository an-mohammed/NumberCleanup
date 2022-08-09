package com.ooredoo.nc.dto;

import java.util.List;

import com.ooredoo.nc.utility.ApplicationConstants;

public class CleanupDetails implements ApplicationConstants {
	private String status = null;
	private String styleClass = null;
	private List<NodalCleanupDetails> cleanupDetails = null;
	
	public String getStatus() {
		status = SUCCESS_LEBEL;
		if(null != cleanupDetails && !cleanupDetails.isEmpty()) {
			
			for(NodalCleanupDetails e : cleanupDetails) {
				if(e.getStatus().equals(SUCCESS_LEBEL)) {
					continue;
				} else {
					status = ERROR_LABEL;
					break;
				}
			}
		}
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStyleClass() {
		if(getStatus().equals(SUCCESS_LEBEL)) {
			styleClass = "green";
		} else {
			styleClass = "red";
		}
		return styleClass;
	}
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	public List<NodalCleanupDetails> getCleanupDetails() {
		return cleanupDetails;
	}
	public void setCleanupDetails(List<NodalCleanupDetails> cleanupDetails) {
		this.cleanupDetails = cleanupDetails;
	}
	
	
}
