package com.ooredoo.nc.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DisconnectionRequest {

	private String username;
	private List<String> msisdnList;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<String> getMsisdnList() {
		return msisdnList;
	}
	public void setMsisdnList(List<String> msisdnList) {
		this.msisdnList = msisdnList;
	}
}
