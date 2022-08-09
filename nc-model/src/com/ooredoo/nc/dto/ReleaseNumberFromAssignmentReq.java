package com.ooredoo.nc.dto;

import java.io.Serializable;

public class ReleaseNumberFromAssignmentReq implements Serializable {

	private static final long serialVersionUID = 1L;
	private String msisdn;
	private Long modBy;
	
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public Long getModBy() {
		return modBy;
	}
	public void setModBy(Long modBy) {
		this.modBy = modBy;
	}
	
}
