package com.ooredoo.nc.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the BULK_ACTIVATION_DETAILS database table.
 * 
 */
@Entity
@Table(name="BULK_ACTIVATION_DETAILS")
@NamedQuery(name="BulkActivationDetail.findAll", query="SELECT b FROM BulkActivationDetail b")
public class BulkActivationDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="BULK_ACTIVATION_DETAILS_ID_GENERATOR", sequenceName="BULK_ACTIVATION_SEQ", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BULK_ACTIVATION_DETAILS_ID_GENERATOR")
	private Long id;

	@Column(name="BATCH_ID")
	private Long batchId;

	@Column(name="CL_ERP_LOCATION")
	private String clErpLocation;

	@Column(name="CL_NMS_POOL")
	private String clNmsPool;

	@Column(name="CO_TYPE")
	private String coType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="END_TIME")
	private Date endTime;

	@Column(name="ERROR_DETAILS")
	private String errorDetails;

	private String imsi;

	private String msisdn;
	
	@Column(name="CIVIL_ID")
	private String civilId;

	@Column(name="NMS_DEALER")
	private String nmsDealer;

	@Column(name="PROMO_NAME")
	private String promoName;
	
	@Column(name="PROMO_ID")
	private String promoId;

	@Column(name="SIM_NO")
	private String simNo;

	@Column(name="SPC_ID")
	private String spcId;

	@Column(name="SPC_TYPE")
	private String spcType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="START_TIME")
	private Date startTime;

	private String status;

	@Column(name="SUBS_TYPE")
	private String subsType;

	private String username;

	public BulkActivationDetail() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBatchId() {
		return this.batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public String getClErpLocation() {
		return this.clErpLocation;
	}

	public void setClErpLocation(String clErpLocation) {
		this.clErpLocation = clErpLocation;
	}

	public String getClNmsPool() {
		return this.clNmsPool;
	}

	public void setClNmsPool(String clNmsPool) {
		this.clNmsPool = clNmsPool;
	}

	public String getCoType() {
		return this.coType;
	}

	public void setCoType(String coType) {
		this.coType = coType;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getErrorDetails() {
		return this.errorDetails;
	}

	public void setErrorDetails(String errorDetails) {
		this.errorDetails = errorDetails;
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

	public String getNmsDealer() {
		return this.nmsDealer;
	}

	public void setNmsDealer(String nmsDealer) {
		this.nmsDealer = nmsDealer;
	}

	public String getPromoId() {
		return this.promoId;
	}

	public void setPromoId(String promoId) {
		this.promoId = promoId;
	}

	public String getSimNo() {
		return this.simNo;
	}

	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}

	public String getSpcId() {
		return this.spcId;
	}

	public void setSpcId(String spcId) {
		this.spcId = spcId;
	}

	public String getSpcType() {
		return this.spcType;
	}

	public void setSpcType(String spcType) {
		this.spcType = spcType;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubsType() {
		return this.subsType;
	}

	public void setSubsType(String subsType) {
		this.subsType = subsType;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCivilId() {
		return civilId;
	}

	public void setCivilId(String civilId) {
		this.civilId = civilId;
	}

	public String getPromoName() {
		return promoName;
	}

	public void setPromoName(String promoName) {
		this.promoName = promoName;
	}

}