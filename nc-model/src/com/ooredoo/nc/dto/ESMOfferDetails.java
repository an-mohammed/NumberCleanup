package com.ooredoo.nc.dto;

import java.io.Serializable;

public class ESMOfferDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private String offerId;
	private String offerName;
	private String offerType;
	private String offerCategory;
	private String purchaseDate;
	private Boolean isOpOffer;
	
	public String getOfferId() {
		return offerId;
	}
	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}
	public String getOfferName() {
		return offerName;
	}
	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}
	public String getOfferType() {
		return offerType;
	}
	public void setOfferType(String offerType) {
		this.offerType = offerType;
	}
	public String getOfferCategory() {
		return offerCategory;
	}
	public void setOfferCategory(String offerCategory) {
		this.offerCategory = offerCategory;
	}
	public String getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public Boolean getIsOpOffer() {
		return isOpOffer;
	}
	public void setIsOpOffer(Boolean isOpOffer) {
		this.isOpOffer = isOpOffer;
	}
}
