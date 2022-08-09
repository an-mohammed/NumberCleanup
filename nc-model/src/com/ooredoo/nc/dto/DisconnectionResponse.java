package com.ooredoo.nc.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DisconnectionResponse {
	private List<DisconnectionOrderDetails> disconnectionOrderDetails;
	private String status;

	public List<DisconnectionOrderDetails> getDisconnectionOrderDetails() {
		return disconnectionOrderDetails;
	}

	public void setDisconnectionOrderDetails(List<DisconnectionOrderDetails> disconnectionOrderDetails) {
		this.disconnectionOrderDetails = disconnectionOrderDetails;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
