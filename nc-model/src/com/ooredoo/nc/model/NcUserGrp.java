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
 * The persistent class for the NC_USER_GRP database table.
 * 
 */
@Entity
@Table(name="NC_USER_GRP")
@NamedQuery(name="NcUserGrp.findAll", query="SELECT n FROM NcUserGrp n")
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties({"appUser"})
public class NcUserGrp implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private NcUserGrpPK id;

	//bi-directional many-to-one association to AppUser
	@ManyToOne
	@JoinColumn(name="USER_ID", insertable=false, updatable=false)
	private AppUser appUser;

	//bi-directional many-to-one association to NcAppUserGrp
	@ManyToOne
	@JoinColumn(name="GRP_ID", insertable=false, updatable=false)
	private NcAppUserGrp ncAppUserGrp;

	public NcUserGrp() {
	}

	public NcUserGrpPK getId() {
		return this.id;
	}

	public void setId(NcUserGrpPK id) {
		this.id = id;
	}

	public AppUser getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public NcAppUserGrp getNcAppUserGrp() {
		return this.ncAppUserGrp;
	}

	public void setNcAppUserGrp(NcAppUserGrp ncAppUserGrp) {
		this.ncAppUserGrp = ncAppUserGrp;
	}

}