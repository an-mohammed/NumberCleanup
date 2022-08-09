package com.ooredoo.nc.dto;

import java.io.Serializable;
import java.util.Date;

public class Occ implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long snCode;
	private String description;
	private Date createdDate;
	private Double amount;
	private String feeType;
	
	public Long getSnCode() {
		return snCode;
	}
	public void setSnCode(Long snCode) {
		this.snCode = snCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

}
