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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
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
 * The persistent class for the NC_APP_USER_GRP database table.
 * 
 */
@Entity
@Table(name="NC_APP_USER_GRP")
@NamedQuery(name="NcAppUserGrp.findAll", query="SELECT n FROM NcAppUserGrp n")
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties({"ncUserGrps"})
public class NcAppUserGrp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GRP_ID_GENERATOR", sequenceName="GROUP_SEQ", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GRP_ID_GENERATOR")
	private Long id;

	@Column(name="CREATED_BY")
	private Long createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_ON")
	private Date createdOn;

	@Column(name="GRP_DESC")
	private String grpDesc;

	@Column(name="GRP_NAME")
	private String grpName;

	//bi-directional many-to-one association to NcGrpMsisdn
	/*@OneToMany(mappedBy="ncAppUserGrp", fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<NcGrpMsisdn> ncGrpMsisdns;*/
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="NC_GRP_MSISDN"
		, joinColumns={
			@JoinColumn(name="GRP_ID", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="NP_ID", nullable=false)
			}
		)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<NumberPool> allocatedNumbers;

	//bi-directional many-to-one association to NcUserGrp
	/*@OneToMany(mappedBy="ncAppUserGrp", fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<NcUserGrp> ncUserGrps;
*/
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="NC_USER_GRP"
		, joinColumns={
			@JoinColumn(name="GRP_ID", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="USER_ID", nullable=false)
			}
		)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<AppUser> ncUserGrps;
	
	@Transient
	private List<AppUser> groupMembers;
	
	public NcAppUserGrp() {
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

	public String getGrpDesc() {
		return this.grpDesc;
	}

	public void setGrpDesc(String grpDesc) {
		this.grpDesc = grpDesc;
	}

	public String getGrpName() {
		return this.grpName;
	}

	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}

/*	public List<NcGrpMsisdn> getNcGrpMsisdns() {
		return this.ncGrpMsisdns;
	}

	public void setNcGrpMsisdns(List<NcGrpMsisdn> ncGrpMsisdns) {
		this.ncGrpMsisdns = ncGrpMsisdns;
	}*/

/*	public NcGrpMsisdn addNcGrpMsisdn(NcGrpMsisdn ncGrpMsisdn) {
		getNcGrpMsisdns().add(ncGrpMsisdn);
		ncGrpMsisdn.setNcAppUserGrp(this);

		return ncGrpMsisdn;
	}

	public NcGrpMsisdn removeNcGrpMsisdn(NcGrpMsisdn ncGrpMsisdn) {
		getNcGrpMsisdns().remove(ncGrpMsisdn);
		ncGrpMsisdn.setNcAppUserGrp(null);

		return ncGrpMsisdn;
	}*/

/*	public List<NcUserGrp> getNcUserGrps() {
		return this.ncUserGrps;
	}

	public void setNcUserGrps(List<NcUserGrp> ncUserGrps) {
		this.ncUserGrps = ncUserGrps;
	}*/

/*	public NcUserGrp addNcUserGrp(NcUserGrp ncUserGrp) {
		getNcUserGrps().add(ncUserGrp);
		ncUserGrp.setNcAppUserGrp(this);

		return ncUserGrp;
	}

	public NcUserGrp removeNcUserGrp(NcUserGrp ncUserGrp) {
		getNcUserGrps().remove(ncUserGrp);
		ncUserGrp.setNcAppUserGrp(null);

		return ncUserGrp;
	}*/

	public List<NumberPool> getAllocatedNumbers() {
		return allocatedNumbers;
	}

	public void setAllocatedNumbers(List<NumberPool> allocatedNumbers) {
		this.allocatedNumbers = allocatedNumbers;
	}

	public List<AppUser> getGroupMembers() {
		if(null != this.ncUserGrps) {
			groupMembers = new ArrayList<AppUser>();
			AppUser m = null;
			for(AppUser a : ncUserGrps) {
				m = new AppUser();
				m.setId(a.getId());
				m.setUName(a.getUName());
				m.setuName(a.getuName());
				m.setFirstname(a.getFirstname());
				m.setLastname(a.getLastname());
				groupMembers.add(m);
			}
		}
		return groupMembers;
	}

	public void setGroupMembers(List<AppUser> groupMembers) {
		this.groupMembers = groupMembers;
	}

	public List<AppUser> getNcUserGrps() {
		return ncUserGrps;
	}

	public void setNcUserGrps(List<AppUser> ncUserGrps) {
		this.ncUserGrps = ncUserGrps;
	}
}