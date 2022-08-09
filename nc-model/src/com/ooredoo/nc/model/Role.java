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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * The persistent class for the "ROLES" database table.
 * 
 */
@Entity
@Table(name="ROLES")
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties("appUsers")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ROLES_ID_GENERATOR", sequenceName="ROLE_SEQ", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ROLES_ID_GENERATOR")
	@Column(unique=true, nullable=false)
	private Long id;

	private String createdby;

	private Date createddate;

	private String description;

	private String modifiedby;

	private Date modifieddate;

	@Column(name="R_NAME")
	private String name;
	
	@Column(name="IS_SERVICE_ROLE")
	private Boolean isServiceRole;
	
	@Column(name="ADMIN_ROLE", nullable=false)
	private Boolean isAdminRole = false;
	
	@Column(name="SU_ROLE", nullable=false)
	private Boolean isSuperUserRole = false;

	//bi-directional many-to-many association to AppUser
	@ManyToMany(mappedBy="roles")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<AppUser> appUsers;

	//bi-directional many-to-many association to Privilege
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="ROLE_PRIVILEGE"
		, joinColumns={
			@JoinColumn(name="ROLE_ID", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="PRIVILEGE_ID", nullable=false)
			}
		)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Privilege> privileges;

	public Role() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(String createdby) {
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

	public String getModifiedby() {
		return this.modifiedby;
	}

	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}

	public Date getModifieddate() {
		return this.modifieddate;
	}

	public void setModifieddate(Date modifieddate) {
		this.modifieddate = modifieddate;
	}

	public List<AppUser> getAppUsers() {
		return this.appUsers;
	}

	public void setAppUsers(List<AppUser> appUsers) {
		this.appUsers = appUsers;
	}

	public List<Privilege> getPrivileges() {
		return this.privileges;
	}

	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}

	public Boolean getIsServiceRole() {
		return isServiceRole;
	}

	public void setIsServiceRole(Boolean isServiceRole) {
		this.isServiceRole = isServiceRole;
	}
	
	public Boolean getIsAdminRole() {
		return isAdminRole;
	}

	public void setIsAdminRole(Boolean isAdminRole) {
		this.isAdminRole = isAdminRole;
	}

	public Boolean getIsSuperUserRole() {
		return isSuperUserRole;
	}

	public void setIsSuperUserRole(Boolean isSuperUserRole) {
		this.isSuperUserRole = isSuperUserRole;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}