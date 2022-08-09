package com.ooredoo.nc.dto;

import java.io.Serializable;

public class ActiveSubscription implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long snCode;
	private String desc;
	private Double accessFee;
	
	public Long getSnCode() {
		return snCode;
	}
	public void setSnCode(Long snCode) {
		this.snCode = snCode;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Double getAccessFee() {
		return accessFee;
	}
	public void setAccessFee(Double accessFee) {
		this.accessFee = accessFee;
	}

}
