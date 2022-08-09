package com.ooredoo.nc.dto;

import java.util.Date;

public class NodalCleanupDetails {
	
	private String node = null;
	private String request = null;
	private String response = null;
	private String responseCode = null;
	private String status = null;
	private String execLog = null;
	private Date execStart = null;
	private Date execEnd = null;
	
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getExecLog() {
		return execLog;
	}
	public void setExecLog(String execLog) {
		this.execLog = execLog;
	}
	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
	public Date getExecStart() {
		return execStart;
	}
	public void setExecStart(Date execStart) {
		this.execStart = execStart;
	}
	public Date getExecEnd() {
		return execEnd;
	}
	public void setExecEnd(Date execEnd) {
		this.execEnd = execEnd;
	}
}
