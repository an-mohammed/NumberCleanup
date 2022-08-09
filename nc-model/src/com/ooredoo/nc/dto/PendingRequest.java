package com.ooredoo.nc.dto;

import java.io.Serializable;

public class PendingRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long requestId;
	private Long actionId;
	private String actionDesc;
	private Long status;
	
	public Long getRequestId() {
		return requestId;
	}
	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}
	public Long getActionId() {
		return actionId;
	}
	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}
	public String getActionDesc() {
		return actionDesc;
	}
	public void setActionDesc(String actionDesc) {
		this.actionDesc = actionDesc;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}

}
