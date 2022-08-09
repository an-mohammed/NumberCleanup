package com.ooredoo.nc.dto;

import com.ooredoo.nc.utility.ApplicationConstants;

public class SubscriberProfileDetails implements ApplicationConstants {
	private String msisdn = null;
	private String msisdnWithoutPrefix = null;
	private String simNo = null;
	private String imsi = null;
	private String selecedNmsPool = null;
	private String selectedErpLocation = null;
	private Double price = null;
	private String subscriberType = null;
	private String selectedDealer = null;
	
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
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
	public String getSelecedNmsPool() {
		return selecedNmsPool;
	}
	public void setSelecedNmsPool(String selecedNmsPool) {
		this.selecedNmsPool = selecedNmsPool;
	}
	public String getSelectedErpLocation() {
		return selectedErpLocation;
	}
	public void setSelectedErpLocation(String selectedErpLocation) {
		this.selectedErpLocation = selectedErpLocation;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public String getMsisdnWithoutPrefix() {
		msisdnWithoutPrefix = msisdn.replaceFirst(MSISDN_PREFIX, ""); 
		return msisdnWithoutPrefix;
	}
	public void setMsisdnWithoutPrefix(String msisdnWithoutPrefix) {
		this.msisdnWithoutPrefix = msisdnWithoutPrefix;
	}
	public String getSubscriberType() {
		return subscriberType;
	}
	public void setSubscriberType(String subscriberType) {
		this.subscriberType = subscriberType;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SubscriberProfileDetails [msisdn=");
		builder.append(msisdn);
		builder.append(", msisdnWithoutPrefix=");
		builder.append(msisdnWithoutPrefix);
		builder.append(", simNo=");
		builder.append(simNo);
		builder.append(", imsi=");
		builder.append(imsi);
		builder.append(", selecedNmsPool=");
		builder.append(selecedNmsPool);
		builder.append(", selectedErpLocation=");
		builder.append(selectedErpLocation);
		builder.append(", price=");
		builder.append(price);
		builder.append(", subscriberType=");
		builder.append(subscriberType);
		builder.append("]");
		return builder.toString();
	}
	public String getSelectedDealer() {
		return selectedDealer;
	}
	public void setSelectedDealer(String selectedDealer) {
		this.selectedDealer = selectedDealer;
	}
}
