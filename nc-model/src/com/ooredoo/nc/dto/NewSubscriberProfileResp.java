package com.ooredoo.nc.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewSubscriberProfileResp implements Serializable{

	private static final long serialVersionUID = 5881570240104663367L;
	private String spcId;
	private String spcNum;
	private String respCode;
	private String message;
	
	public String getSpcId() {
		return spcId;
	}
	public void setSpcId(String spcId) {
		this.spcId = spcId;
	}
	public String getSpcNum() {
		return spcNum;
	}
	public void setSpcNum(String spcNum) {
		this.spcNum = spcNum;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
