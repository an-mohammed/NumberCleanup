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
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * The persistent class for the PRIVILEGE database table.
 * 
 */
@Entity
@NamedQuery(name="Privilege.findAll", query="SELECT p FROM Privilege p")
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties("roles")
public class Privilege implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PRIVILEGE_ID_GENERATOR", sequenceName="PRIVILEGE_SEQ", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PRIVILEGE_ID_GENERATOR")
	@Column(unique=true, nullable=false)
	private Long id;

	private Long createdby;

	private Date createddate;

	private String description;

	private Date moddate;

	private Long modifiedby;

	@Column(name="P_NAME")
	private String pName;

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	//bi-directional many-to-many association to Role
	@ManyToMany(mappedBy="privileges", fetch=FetchType.EAGER)
	private List<Role> roles;

	public Privilege() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(Long createdby) {
		this.createdby = createdby;
	}

	public Date getCreateddate() {
		return this.createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getModdate() {
		return this.moddate;
	}

	public void setModdate(Date moddate) {
		this.moddate = moddate;
	}

	public Long getModifiedby() {
		return this.modifiedby;
	}

	public void setModifiedby(Long modifiedby) {
		this.modifiedby = modifiedby;
	}

	public String getPName() {
		return this.pName;
	}

	public void setPName(String pName) {
		this.pName = pName;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}