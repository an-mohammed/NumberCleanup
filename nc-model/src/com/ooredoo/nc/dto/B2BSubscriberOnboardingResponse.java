package com.ooredoo.nc.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class B2BSubscriberOnboardingResponse {

	private String msisdn;
	private String civilId;
	private String promoId;
	private String imsi;
	private String simNo;
	private String nmsPool;
	private String erpLocation;
	private String nmsDealer;
	private String status;
	private String siebelRowId;
	private String siebelOrderId;
	
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getCivilId() {
		return civilId;
	}
	public void setCivilId(String civilId) {
		this.civilId = civilId;
	}
	public String getPromoId() {
		return promoId;
	}
	public void setPromoId(String promoId) {
		this.promoId = promoId;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getSimNo() {
		return simNo;
	}
	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}
	public String getNmsPool() {
		return nmsPool;
	}
	public void setNmsPool(String nmsPool) {
		this.nmsPool = nmsPool;
	}
	public String getErpLocation() {
		return erpLocation;
	}
	public void setErpLocation(String erpLocation) {
		this.erpLocation = erpLocation;
	}
	public String getNmsDealer() {
		return nmsDealer;
	}
	public void setNmsDealer(String nmsDealer) {
		this.nmsDealer = nmsDealer;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSiebelRowId() {
		return siebelRowId;
	}
	public void setSiebelRowId(String siebelRowId) {
		this.siebelRowId = siebelRowId;
	}
	public String getSiebelOrderId() {
		return siebelOrderId;
	}
	public void setSiebelOrderId(String siebelOrderId) {
		this.siebelOrderId = siebelOrderId;
	}
}
