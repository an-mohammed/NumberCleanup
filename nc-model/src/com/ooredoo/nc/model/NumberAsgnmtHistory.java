package com.ooredoo.nc.model;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * The persistent class for the NUMBER_ASGNMT_HISTORY database table.
 * 
 */
@Entity
@Table(name="NUMBER_ASGNMT_HISTORY")
@JsonIgnoreProperties("appUser")
@JsonInclude(Include.NON_EMPTY)
@NamedQuery(name="NumberAsgnmtHistory.findAll", query="SELECT n FROM NumberAsgnmtHistory n")
public class NumberAsgnmtHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(updatable=false)
	@SequenceGenerator(name="NUMBER_ASSIGNMENT_GEN", sequenceName="NUMBER_ASSIGNMENT_SEQ", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="NUMBER_ASSIGNMENT_GEN")
	private Long id;

	@Column(name="CREATED_BY")
	private Long createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_ON")
	private Date createdOn;

	private String description;

	@Temporal(TemporalType.DATE)
	@Column(name="END_DATE")
	private Date endDate;

	@Column(name="MOD_BY")
	private Long modBy;

	@Temporal(TemporalType.DATE)
	@Column(name="MOD_ON")
	private Date modOn;

	private String reason;

	@Temporal(TemporalType.DATE)
	@Column(name="START_DATE")
	private Date startDate;

	private String status;

	//bi-directional many-to-one association to AppUser
	@ManyToOne
	@JoinColumn(name="ASSIGNED_TO", nullable=true, updatable=true)
	private AppUser appUser;

	//bi-directional many-to-one association to NumberPool
	@OneToMany(mappedBy="numberAsgnmtHistory", fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<NumberPool> numberPools;
	
	@Transient
	private List<String> selectedMsisdns;
	
	@Transient
	private String assignedToUser;

	public NumberAsgnmtHistory() {
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

	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getModBy() {
		return this.modBy;
	}

	public void setModBy(Long modBy) {
		this.modBy = modBy;
	}

	public Date getModOn() {
		return this.modOn;
	}

	public void setModOn(Date modOn) {
		this.modOn = modOn;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public AppUser getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public List<NumberPool> getNumberPools() {
		return this.numberPools;
	}

	public void setNumberPools(List<NumberPool> numberPools) {
		if(null != numberPools) {
			for(NumberPool np : numberPools) {
				np.setNumberAsgnmtHistory(this);
			}
		}
		this.numberPools = numberPools;
	}

	public NumberPool addNumberPool(NumberPool numberPool) {
		getNumberPools().add(numberPool);
		numberPool.setNumberAsgnmtHistory(this);

		return numberPool;
	}

	public NumberPool removeNumberPool(NumberPool numberPool) {
		getNumberPools().remove(numberPool);
		numberPool.setNumberAsgnmtHistory(null);

		return numberPool;
	}

	public String getAssignedToUser() {
		return assignedToUser;
	}

	public void setAssignedToUser(String assignedToUser) {
		this.assignedToUser = assignedToUser;
	}

	public List<String> getSelectedMsisdns() {
		this.selectedMsisdns = new ArrayList<String>();
		
		if(null != this.numberPools) {
			for(NumberPool np : this.numberPools) {
				this.selectedMsisdns.add(np.getMsisdn());
			}
		}
		
		return this.selectedMsisdns;
	}

	public void setSelectedMsisdns(List<String> selectedMsisdns) {
		this.selectedMsisdns = selectedMsisdns;
	}

}