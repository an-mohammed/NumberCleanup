package com.ooredoo.nc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * The persistent class for the NUMBER_POOL database table.
 * 
 */
@Entity
@Table(name="NUMBER_POOL")
@NamedQuery(name="NumberPool.findAll", query="SELECT n FROM NumberPool n")
@JsonIgnoreProperties({"numberAsgnmtHistory","ncGrpMsisdns"})
@JsonInclude(Include.NON_EMPTY)
public class NumberPool implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="NUMBER_POOL_ID_GEN", sequenceName="NUMBER_POOL_SEQ", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="NUMBER_POOL_ID_GEN")
	@Column(updatable=false)
	private Long id;

	@Column(name="CREATED_BY")
	private Long createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_DATE")
	private Date createdDate;

	private Boolean enabled;

	private String imsi;

	@Column(name="MOD_BY")
	private Long modBy;

	@Temporal(TemporalType.DATE)
	@Column(name="MOD_DATE")
	private Date modDate;

	private String msisdn;

	@Column(name="SIM_NO")
	private String simNo;
	
	@Column(name="CUR_LOC")
	private String curErpLoc;
	
	@Column(name="CUR_NMS_POOL")
	private String curNmsPool;
	
	@Column(name="PH_SIM")
	private Boolean phSim;

	//bi-directional many-to-one association to NumberAsgnmtHistory
	@ManyToOne
	@JoinColumn(name="ASSMT_ID")
	private NumberAsgnmtHistory numberAsgnmtHistory;
	
	//bi-directional many-to-one association to NcGrpMsisdn
	@OneToMany(mappedBy="numberPool", fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<NcGrpMsisdn> ncGrpMsisdns;
	
	public boolean getNumberAssigned() {
		if(null != numberAsgnmtHistory && null != numberAsgnmtHistory.getId()) {
			return true;
		} else {
			return false;
		}
	}

	public NumberPool() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getImsi() {
		return this.imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public Long getModBy() {
		return this.modBy;
	}

	public void setModBy(Long modBy) {
		this.modBy = modBy;
	}

	public Date getModDate() {
		return this.modDate;
	}

	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}

	public String getMsisdn() {
		return this.msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getSimNo() {
		return this.simNo;
	}

	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}

	public NumberAsgnmtHistory getNumberAsgnmtHistory() {
		return this.numberAsgnmtHistory;
	}

	public void setNumberAsgnmtHistory(NumberAsgnmtHistory numberAsgnmtHistory) {
		this.numberAsgnmtHistory = numberAsgnmtHistory;
	}

	public String getCurErpLoc() {
		return curErpLoc;
	}

	public void setCurErpLoc(String curErpLoc) {
		this.curErpLoc = curErpLoc;
	}

	public String getCurNmsPool() {
		return curNmsPool;
	}

	public void setCurNmsPool(String curNmsPool) {
		this.curNmsPool = curNmsPool;
	}

	public List<NcGrpMsisdn> getNcGrpMsisdns() {
		return ncGrpMsisdns;
	}

	public void setNcGrpMsisdns(List<NcGrpMsisdn> ncGrpMsisdns) {
		this.ncGrpMsisdns = ncGrpMsisdns;
	}

	public Boolean getPhSim() {
		return phSim;
	}

	public void setPhSim(Boolean phSim) {
		this.phSim = phSim;
	}
}