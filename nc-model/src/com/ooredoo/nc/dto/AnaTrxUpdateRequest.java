package com.ooredoo.nc.dto;

public class AnaTrxUpdateRequest {

	private String trxId;
	private String trxDetails;
	private String trxStatus;
	private String siebelOrder;
	
	public String getTrxId() {
		return trxId;
	}
	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}
	public String getTrxDetails() {
		return trxDetails;
	}
	public void setTrxDetails(String trxDetails) {
		this.trxDetails = trxDetails;
	}
	public String getTrxStatus() {
		return trxStatus;
	}
	public void setTrxStatus(String trxStatus) {
		this.trxStatus = trxStatus;
	}
	public String getSiebelOrder() {
		return siebelOrder;
	}
	public void setSiebelOrder(String siebelOrder) {
		this.siebelOrder = siebelOrder;
	}
}
