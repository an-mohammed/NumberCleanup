package com.ooredoo.nc.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ESMProfileDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private String msisdn;
	private String subscriberType;
	private String userSegment;
	private Boolean isParent;
	private Boolean isFmc;
	private String language;
	private Boolean isConverged;
	private String activationDate;
	private List<ESMOfferDetails> activeOffers;
	private List<ESMOfferDetails> availableOffers;
	
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getSubscriberType() {
		return subscriberType;
	}
	public void setSubscriberType(String subscriberType) {
		this.subscriberType = subscriberType;
	}
	public String getUserSegment() {
		return userSegment;
	}
	public void setUserSegment(String userSegment) {
		this.userSegment = userSegment;
	}
	public Boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}
	public Boolean getIsFmc() {
		return isFmc;
	}
	public void setIsFmc(Boolean isFmc) {
		this.isFmc = isFmc;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Boolean getIsConverged() {
		return isConverged;
	}
	public void setIsConverged(Boolean isConverged) {
		this.isConverged = isConverged;
	}
	public String getActivationDate() {
		return activationDate;
	}
	public void setActivationDate(String activationDate) {
		this.activationDate = activationDate;
	}
	public List<ESMOfferDetails> getActiveOffers() {
		if(null == activeOffers) {
			activeOffers = new ArrayList<ESMOfferDetails>();
		}
		return activeOffers;
	}
	public void setActiveOffers(List<ESMOfferDetails> activeOffers) {
		this.activeOffers = activeOffers;
	}
	public List<ESMOfferDetails> getAvailableOffers() {
		if(null == availableOffers) {
			availableOffers = new ArrayList<ESMOfferDetails>();
		}
		
		return availableOffers;
	}
	public void setAvailableOffers(List<ESMOfferDetails> availableOffers) {
		this.availableOffers = availableOffers;
	}
}
