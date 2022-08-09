package com.ooredoo.nc.dto;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ooredoo.nc.exception.ApplicationException;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewSubscriberProfile implements Serializable{

	private static final long serialVersionUID = 5881570240104663367L;
	private String salutation;
	private String firstName;
	private String lastName;
	private String email;
	private String msisdn;
	private String simNo;
	private String idType;
	private String idValue;
	private String idExpirationDate;
	private String visaType;
	private String transactionNo;
	private String dealerId;
	private String promoId;
	private String language;
	private String nationality;
	private String subscriberType;
	
	public void validate() throws ApplicationException {
		if(null == salutation || salutation.isEmpty()) {
			throw new ApplicationException("Missing new subscriber salutation", true);
		}
		
		if(null == firstName || firstName.isEmpty()) {
			throw new ApplicationException("Missing new subscriber first name", true);
		}
		
		if(null == lastName || lastName.isEmpty()) {
			throw new ApplicationException("Missing new subscriber last name", true);
		}
		
		if(null == email || email.isEmpty()) {
			throw new ApplicationException("Missing new subscriber e-Mail", true);
		}
		
		if(null == msisdn || msisdn.isEmpty()) {
			throw new ApplicationException("Missing new subscriber mobile number", true);
		} else {
			if(msisdn.length() != 8) {
				throw new ApplicationException("Invalid mobile number provided. Must be 8 digit", true);
			}
		}
		
		/*
		 * if(null == idType || idType.isEmpty()) { throw new
		 * ApplicationException("Missing new subscriber identity type", true); }
		 */
		
		if(null == idValue || idValue.isEmpty()) {
			throw new ApplicationException("Missing new subscriber ID number", true);
		}
		
		if(null == idExpirationDate || idExpirationDate.isEmpty()) {
			throw new ApplicationException("Missing new subscriber ID Expiration Date", true);
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			sdf.setLenient(false);
			try {
				sdf.parse(idExpirationDate);
			} catch (ParseException e) {
				throw new ApplicationException("Invalid date for ID expiration. Must be in DD/MM/YYYY format", true);
			}
		}
		
		if(null == visaType || visaType.isEmpty()) {
			//throw new ApplicationException("Missing new subscriber visa type", true);
			this.visaType="18";
		}
		
		if(null == transactionNo || transactionNo.isEmpty()) {
			throw new ApplicationException("Missing transaction number to initiate subscriber onboarding", true);
		}
		
		if(null == dealerId || dealerId.isEmpty()) {
			throw new ApplicationException("Missing subscriber onboarding dealer Id", true);
		}
		
		if(null == promoId || promoId.isEmpty()) {
			throw new ApplicationException("Missing new subscriber promotion ID", true);
		}
		
		if(null == language || language.isEmpty()) {
			throw new ApplicationException("Missing new subscriber language", true);
		} else {
			if(!language.equals("Arabic") && !language.equals("English")) {
				throw new ApplicationException("Invalid language. Valid languages are English/Arabic", true);
			}
		}
		
		if(null == nationality || nationality.isEmpty()) {
			throw new ApplicationException("Missing new subscriber nationality", true);
		}
		
		if(null == subscriberType || subscriberType.isEmpty()) {
			throw new ApplicationException("Missing new subscriber type", true);
		} else {
			if(!subscriberType.equals("PREPAID") && !subscriberType.equals("POSTPAID")) {
				throw new ApplicationException("Invalid subscriber type. Valid values are PREPAID/POSTPAID", true);
			}
		}
	}
	
	public String getSalutation() {
		return salutation;
	}
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdValue() {
		return idValue;
	}
	public void setIdValue(String idValue) {
		this.idValue = idValue;
	}
	public String getIdExpirationDate() {
		return idExpirationDate;
	}
	public void setIdExpirationDate(String idExpirationDate) {
		this.idExpirationDate = idExpirationDate;
	}
	public String getVisaType() {
		return visaType;
	}
	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}
	public String getTransactionNo() {
		return transactionNo;
	}
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}
	public String getDealerId() {
		return dealerId;
	}
	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}
	public String getPromoId() {
		return promoId;
	}
	public void setPromoId(String promoId) {
		this.promoId = promoId;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getSubscriberType() {
		return subscriberType;
	}

	public void setSubscriberType(String subscriberType) {
		this.subscriberType = subscriberType;
	}

	public String getSimNo() {
		return simNo;
	}

	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}

}
