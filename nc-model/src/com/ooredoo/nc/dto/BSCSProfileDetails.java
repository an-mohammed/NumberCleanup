package com.ooredoo.nc.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BSCSProfileDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long customerId;
	private Long contractId;
	private String customerStatus;
	private String contractStatus;
	private String custCode;
	private Date csActivated;
	private Double csCurbalance;
	private Date coInstalled;
	private String paymentResp;
	private String lang;
	private Date lbcDate;
	private Long sncode;
	private Long ratePlanCode;
	private String rateplanDes;
	private List<ActiveSubscription> activeSubs;
	private List<Occ> occs;
	private List<PendingRequest> pRequests;
	private String simNo;
	private String imsi;
	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getContractId() {
		return contractId;
	}
	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
	public String getCustomerStatus() {
		return customerStatus;
	}
	public void setCustomerStatus(String customerStatus) {
		this.customerStatus = customerStatus;
	}
	public String getContractStatus() {
		return contractStatus;
	}
	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public Date getCsActivated() {
		return csActivated;
	}
	public void setCsActivated(Date csActivated) {
		this.csActivated = csActivated;
	}
	public Double getCsCurbalance() {
		return csCurbalance;
	}
	public void setCsCurbalance(Double csCurbalance) {
		this.csCurbalance = csCurbalance;
	}
	public Date getCoInstalled() {
		return coInstalled;
	}
	public void setCoInstalled(Date coInstalled) {
		this.coInstalled = coInstalled;
	}
	public String getPaymentResp() {
		return paymentResp;
	}
	public void setPaymentResp(String paymentResp) {
		this.paymentResp = paymentResp;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public Date getLbcDate() {
		return lbcDate;
	}
	public void setLbcDate(Date lbcDate) {
		this.lbcDate = lbcDate;
	}
	public Long getSncode() {
		return sncode;
	}
	public void setSncode(Long sncode) {
		this.sncode = sncode;
	}
	public Long getRatePlanCode() {
		return ratePlanCode;
	}
	public void setRatePlanCode(Long ratePlanCode) {
		this.ratePlanCode = ratePlanCode;
	}
	public String getRateplanDes() {
		return rateplanDes;
	}
	public void setRateplanDes(String rateplanDes) {
		this.rateplanDes = rateplanDes;
	}
	
	public List<ActiveSubscription> getActiveSubs() {
		if(activeSubs == null) {
			activeSubs = new ArrayList<ActiveSubscription>();
		}
		
		return activeSubs;
	}
	
	public void setActiveSubs(List<ActiveSubscription> activeSubs) {
		this.activeSubs = activeSubs;
	}
	
	public List<Occ> getOccs() {
		if(occs == null) {
			occs = new ArrayList<Occ>();
		}
		
		return occs;
	}
	
	public void setOccs(List<Occ> occs) {
		this.occs = occs;
	}
	
	public List<PendingRequest> getpRequests() {
		if(pRequests == null) {
			pRequests = new ArrayList<PendingRequest>();
		}
		
		return pRequests;
	}
	public void setpRequests(List<PendingRequest> pRequests) {
		this.pRequests = pRequests;
	}
	public String getSimNo() {
		return simNo;
	}
	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	
	
	

}
