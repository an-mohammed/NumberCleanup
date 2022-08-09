package com.ooredoo.nc.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * The persistent class for the NC_GRP_MSISDN database table.
 * 
 */
@Entity
@Table(name="NC_GRP_MSISDN")
@NamedQuery(name="NcGrpMsisdn.findAll", query="SELECT n FROM NcGrpMsisdn n")
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties({"ncAppUserGrp","numberPool"})
public class NcGrpMsisdn implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private NcGrpMsisdnPK id;

	//bi-directional many-to-one association to NcAppUserGrp
	@ManyToOne
	@JoinColumn(name="GRP_ID", insertable=false, updatable=false)
	private NcAppUserGrp ncAppUserGrp;

	//bi-directional many-to-one association to NumberPool
	@ManyToOne
	@JoinColumn(name="NP_ID", insertable=false, updatable=false)
	private NumberPool numberPool;

	public NcGrpMsisdn() {
	}

	public NcGrpMsisdnPK getId() {
		return this.id;
	}

	public void setId(NcGrpMsisdnPK id) {
		this.id = id;
	}

	public NcAppUserGrp getNcAppUserGrp() {
		return this.ncAppUserGrp;
	}

	public void setNcAppUserGrp(NcAppUserGrp ncAppUserGrp) {
		this.ncAppUserGrp = ncAppUserGrp;
	}

	public NumberPool getNumberPool() {
		return this.numberPool;
	}

	public void setNumberPool(NumberPool numberPool) {
		this.numberPool = numberPool;
	}

}