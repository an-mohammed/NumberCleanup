package com.ooredoo.nc.dto;

import java.util.Map;

public class SubscriberProfileManagementResponse {

	private BSCSProfileDetails bscsProfile;
	private ESMProfileDetails esmProfile;
	private CustomerProfile customerProfile;
	private HLRProfile hlrProfile;
	private boolean customerProfileFound;
	private boolean bscsProfileFound;
	private boolean esmProfileFound;
	private boolean hlrProfileFound;
	private Map<String, String> rasProfile;
	private Map<String, String> csProfile;
	private Map<String, String> erpProfile;
	private Map<String, String> nmsProfile;
	private Map<String, String> pcrfProfile;
	
	public BSCSProfileDetails getBscsProfile() {
		return bscsProfile;
	}

	public void setBscsProfile(BSCSProfileDetails bscsProfile) {
		this.bscsProfile = bscsProfile;
	}

	public ESMProfileDetails getEsmProfile() {
		return esmProfile;
	}

	public void setEsmProfile(ESMProfileDetails esmProfile) {
		this.esmProfile = esmProfile;
	}

	public CustomerProfile getCustomerProfile() {
		return customerProfile;
	}

	public void setCustomerProfile(CustomerProfile customerProfile) {
		this.customerProfile = customerProfile;
	}

	public boolean getCustomerProfileFound() {
		if(null != getCustomerProfile()) {
			this.customerProfileFound = true;
		} else {
			this.customerProfileFound = false;
		}
		
		return this.customerProfileFound;
	}

	public void setCustomerProfileFound(boolean customerProfileFound) {
		this.customerProfileFound = customerProfileFound;
	}

	public boolean getBscsProfileFound() {
		if(null != getBscsProfile()) {
			this.bscsProfileFound = true;
		} else {
			this.bscsProfileFound = false;
		}
		
		return this.bscsProfileFound;
	}

	public void setBscsProfileFound(boolean bscsProfileFound) {
		this.bscsProfileFound = bscsProfileFound;
	}

	public boolean getEsmProfileFound() {
		if(null != getEsmProfile()) {
			this.esmProfileFound = true;
		} else {
			this.esmProfileFound = false;
		}
		
		return this.esmProfileFound;
	}

	public void setEsmProfileFound(boolean esmProfileFound) {
		this.esmProfileFound = esmProfileFound;
	}

	public HLRProfile getHlrProfile() {
		return hlrProfile;
	}

	public void setHlrProfile(HLRProfile hlrProfile) {
		this.hlrProfile = hlrProfile;
	}

	public boolean getHlrProfileFound() {
		if(null != getHlrProfile()) {
			this.hlrProfileFound = true;
		} else {
			this.hlrProfileFound = false;
		}
		
		return this.hlrProfileFound;
	}

	public void setHlrProfileFound(boolean hlrProfileFound) {
		this.hlrProfileFound = hlrProfileFound;
	}

	public Map<String, String> getRasProfile() {
		return rasProfile;
	}

	public void setRasProfile(Map<String, String> rasProfile) {
		this.rasProfile = rasProfile;
	}

	public Map<String, String> getCsProfile() {
		return csProfile;
	}

	public void setCsProfile(Map<String, String> csProfile) {
		this.csProfile = csProfile;
	}

	public Map<String, String> getErpProfile() {
		return erpProfile;
	}

	public void setErpProfile(Map<String, String> erpProfile) {
		this.erpProfile = erpProfile;
	}

	public Map<String, String> getNmsProfile() {
		return nmsProfile;
	}

	public void setNmsProfile(Map<String, String> nmsProfile) {
		this.nmsProfile = nmsProfile;
	}

	public Map<String, String> getPcrfProfile() {
		return pcrfProfile;
	}

	public void setPcrfProfile(Map<String, String> pcrfProfile) {
		this.pcrfProfile = pcrfProfile;
	}
}
