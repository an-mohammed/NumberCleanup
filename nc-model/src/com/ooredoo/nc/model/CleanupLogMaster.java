package com.ooredoo.nc.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the CLEANUP_LOG_MASTER database table.
 * 
 */
@Entity
@Table(name="CLEANUP_LOG_MASTER")
@NamedQuery(name="CleanupLogMaster.findAll", query="SELECT c FROM CleanupLogMaster c")
public class CleanupLogMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LOG_MSTR_ID_GENERATOR", sequenceName="DASHBOARD_LOGS_SEQ", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LOG_MSTR_ID_GENERATOR")
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(name="ERP_LOC")
	private String erpLoc;

	private String imsi;

	private String msisdn;

	@Column(name="NMS_POOL")
	private String nmsPool;

	@Column(name="NMS_PRICE")
	private String nmsPrice;

	@Column(name="SIM_NO")
	private String simNo;
	
	private String bscs;

	private String cs;

	private String erp;

	private String esm;

	@Temporal(TemporalType.DATE)
	@Column(name="EXEC_DATE")
	private Date execDate;
	
	@Column(name="EXEC_USER", nullable=false)
	private String execUser;

	private String hlr;

	private String nms;

	private String pcrf;

	private String ras;

	private String spr;

	public CleanupLogMaster() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getErpLoc() {
		return this.erpLoc;
	}

	public void setErpLoc(String erpLoc) {
		this.erpLoc = erpLoc;
	}

	public String getImsi() {
		return this.imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getMsisdn() {
		return this.msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getNmsPool() {
		return this.nmsPool;
	}

	public void setNmsPool(String nmsPool) {
		this.nmsPool = nmsPool;
	}

	public String getNmsPrice() {
		return this.nmsPrice;
	}

	public void setNmsPrice(String nmsPrice) {
		this.nmsPrice = nmsPrice;
	}

	public String getSimNo() {
		return this.simNo;
	}

	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}

	public String getBscs() {
		return bscs;
	}

	public void setBscs(String bscs) {
		this.bscs = bscs;
	}

	public String getCs() {
		return cs;
	}

	public void setCs(String cs) {
		this.cs = cs;
	}

	public String getErp() {
		return erp;
	}

	public void setErp(String erp) {
		this.erp = erp;
	}

	public String getEsm() {
		return esm;
	}

	public void setEsm(String esm) {
		this.esm = esm;
	}

	public Date getExecDate() {
		return execDate;
	}

	public void setExecDate(Date execDate) {
		this.execDate = execDate;
	}

	public String getHlr() {
		return hlr;
	}

	public void setHlr(String hlr) {
		this.hlr = hlr;
	}

	public String getNms() {
		return nms;
	}

	public void setNms(String nms) {
		this.nms = nms;
	}

	public String getPcrf() {
		return pcrf;
	}

	public void setPcrf(String pcrf) {
		this.pcrf = pcrf;
	}

	public String getRas() {
		return ras;
	}

	public void setRas(String ras) {
		this.ras = ras;
	}

	public String getSpr() {
		return spr;
	}

	public void setSpr(String spr) {
		this.spr = spr;
	}

	public String getExecUser() {
		return execUser;
	}

	public void setExecUser(String execUser) {
		this.execUser = execUser;
	}

}