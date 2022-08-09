package com.ooredoo.nc.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CustomerProfile implements Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal customerId;
	private BigDecimal contractId;
	private String contractStatus;
	private String customerType;
	private String ratePlanType;
	private BigDecimal ratePlanCode;
	private String language;
	private String civilId;
	private String iddFlag;
	private String barringStatus;
	private boolean paymentResp;
	private String promoSegment;
	private String siebelAccountCustCode;
	private String laCustCode;
	private String custCode;
	private boolean isHybrid;
	private boolean isDigital;
	private boolean prepaidXpressDevice;
	private String coreOffer;
	private String coreOfferPrice;
	private String activationDate;
	private String nojoomMemberId;
	private String nojoomMemberPoint;
	
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public BigDecimal getContractId() {
		return contractId;
	}
	public void setContractId(BigDecimal contractId) {
		this.contractId = contractId;
	}
	public String getContractStatus() {
		return contractStatus;
	}
	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getRatePlanType() {
		return ratePlanType;
	}
	public void setRatePlanType(String ratePlanType) {
		this.ratePlanType = ratePlanType;
	}
	public BigDecimal getRatePlanCode() {
		return ratePlanCode;
	}
	public void setRatePlanCode(BigDecimal ratePlanCode) {
		this.ratePlanCode = ratePlanCode;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCivilId() {
		return civilId;
	}
	public void setCivilId(String civilId) {
		this.civilId = civilId;
	}
	public String getIddFlag() {
		return iddFlag;
	}
	public void setIddFlag(String iddFlag) {
		this.iddFlag = iddFlag;
	}
	public String getBarringStatus() {
		return barringStatus;
	}
	public void setBarringStatus(String barringStatus) {
		this.barringStatus = barringStatus;
	}
	public boolean getPaymentResp() {
		return paymentResp;
	}
	public void setPaymentResp(boolean paymentResp) {
		this.paymentResp = paymentResp;
	}
	public String getPromoSegment() {
		return promoSegment;
	}
	public void setPromoSegment(String promoSegment) {
		this.promoSegment = promoSegment;
	}
	public String getSiebelAccountCustCode() {
		return siebelAccountCustCode;
	}
	public void setSiebelAccountCustCode(String siebelAccountCustCode) {
		this.siebelAccountCustCode = siebelAccountCustCode;
	}
	public String getLaCustCode() {
		return laCustCode;
	}
	public void setLaCustCode(String laCustCode) {
		this.laCustCode = laCustCode;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public boolean getIsHybrid() {
		return isHybrid;
	}
	public void setIsHybrid(boolean isHybrid) {
		this.isHybrid = isHybrid;
	}
	public boolean getIsDigital() {
		return isDigital;
	}
	public void setIsDigital(boolean isDigital) {
		this.isDigital = isDigital;
	}
	public boolean getPrepaidXpressDevice() {
		return prepaidXpressDevice;
	}
	public void setPrepaidXpressDevice(boolean prepaidXpressDevice) {
		this.prepaidXpressDevice = prepaidXpressDevice;
	}
	public String getCoreOffer() {
		return coreOffer;
	}
	public void setCoreOffer(String coreOffer) {
		this.coreOffer = coreOffer;
	}
	public String getCoreOfferPrice() {
		return coreOfferPrice;
	}
	public void setCoreOfferPrice(String coreOfferPrice) {
		this.coreOfferPrice = coreOfferPrice;
	}
	public String getActivationDate() {
		return activationDate;
	}
	public void setActivationDate(String activationDate) {
		this.activationDate = activationDate;
	}
	public String getNojoomMemberId() {
		return nojoomMemberId;
	}
	public void setNojoomMemberId(String nojoomMemberId) {
		this.nojoomMemberId = nojoomMemberId;
	}
	public String getNojoomMemberPoint() {
		return nojoomMemberPoint;
	}
	public void setNojoomMemberPoint(String nojoomMemberPoint) {
		this.nojoomMemberPoint = nojoomMemberPoint;
	}	
}
