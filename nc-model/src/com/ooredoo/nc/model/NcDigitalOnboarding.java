package com.ooredoo.nc.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the NC_DIGITAL_ONBOARDING database table.
 * 
 */
@Entity
@Table(name="NC_DIGITAL_ONBOARDING")
@NamedQuery(name="NcDigitalOnboarding.findAll", query="SELECT n FROM NcDigitalOnboarding n")
public class NcDigitalOnboarding implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="NC_DIGITAL_ONBOARDING_ID_GENERATOR", sequenceName="DIGITAL_ONBOARDING_SEQ", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="NC_DIGITAL_ONBOARDING_ID_GENERATOR")
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(name="BSCS_IMSI", length=40)
	private String bscsImsi;

	@Column(name="BSCS_SIM_NO", length=40)
	private String bscsSimNo;

	@Column(name="CLEANUP_STATUS", length = 1)
	private String cleanupStatus;

	@Column(name="ONBOARDING_STATUS", length = 1)
	private String onboardingStatus;
	
	@Column(name="TRX_DETAILS", length = 1)
	private String trxDetails;

	@Column(name="LOCAL_IMSI", nullable=false, length=40)
	private String localImsi;

	@Column(name="LOCAL_SIM", nullable=false, length=40)
	private String localSim;

	@Column(nullable=false, length=20)
	private String msisdn;

	@Temporal(TemporalType.DATE)
	@Column(name="TRX_DATE", nullable=false)
	private Date trxDate;
	
	@Column(name="TRX_ID", nullable=false, length=50)
	private String trxId;
	
	@Column(name="USERNAME", nullable=false, length=50)
	private String username;

	public NcDigitalOnboarding() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBscsImsi() {
		return this.bscsImsi;
	}

	public void setBscsImsi(String bscsImsi) {
		this.bscsImsi = bscsImsi;
	}

	public String getBscsSimNo() {
		return this.bscsSimNo;
	}

	public void setBscsSimNo(String bscsSimNo) {
		this.bscsSimNo = bscsSimNo;
	}

	public String getLocalImsi() {
		return this.localImsi;
	}

	public void setLocalImsi(String localImsi) {
		this.localImsi = localImsi;
	}

	public String getLocalSim() {
		return this.localSim;
	}

	public void setLocalSim(String localSim) {
		this.localSim = localSim;
	}

	public String getMsisdn() {
		return this.msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public Date getTrxDate() {
		return this.trxDate;
	}

	public void setTrxDate(Date trxDate) {
		this.trxDate = trxDate;
	}

	public String getTrxDetails() {
		return trxDetails;
	}

	public void setTrxDetails(String trxDetails) {
		this.trxDetails = trxDetails;
	}

	public String getCleanupStatus() {
		return cleanupStatus;
	}

	public void setCleanupStatus(String cleanupStatus) {
		this.cleanupStatus = cleanupStatus;
	}

	public String getOnboardingStatus() {
		return onboardingStatus;
	}

	public void setOnboardingStatus(String onboardingStatus) {
		this.onboardingStatus = onboardingStatus;
	}

	public String getTrxId() {
		return trxId;
	}

	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}