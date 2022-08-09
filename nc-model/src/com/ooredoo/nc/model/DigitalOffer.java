package com.ooredoo.nc.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the DIGITAL_OFFERS database table.
 * 
 */
@Entity
@Table(name="DIGITAL_OFFERS")
public class DigitalOffer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="OFFER_ID", unique=true, nullable=false)
	private Long offerId;

	@Column(name="ACC_GROUP", nullable=false, length=20)
	private String accGroup;

	@Column(nullable=false, precision=1)
	private Boolean enabled;

	@Column(name="OFFER_DESC", nullable=false, length=1000)
	private String offerDesc;

	@Column(name="OFFER_NAME", nullable=false, length=100)
	private String offerName;

	public DigitalOffer() {
	}

	public Long getOfferId() {
		return this.offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public String getAccGroup() {
		return this.accGroup;
	}

	public void setAccGroup(String accGroup) {
		this.accGroup = accGroup;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getOfferDesc() {
		return this.offerDesc;
	}

	public void setOfferDesc(String offerDesc) {
		this.offerDesc = offerDesc;
	}

	public String getOfferName() {
		return this.offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

}