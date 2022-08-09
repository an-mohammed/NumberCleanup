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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ooredoo.nc.utility.PasswordUtility;

import io.swagger.annotations.ApiModelProperty;


/**
 * The persistent class for the APP_USERS database table.
 * 
 */
@Entity
@Table(name="APP_USERS")
@NamedQuery(name="AppUser.findAll", query="SELECT a FROM AppUser a")
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(value= {"salt"})
public class AppUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USERS_ID_GENERATOR", sequenceName="USER_SEQ", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USERS_ID_GENERATOR")
	@Column(unique=true, nullable=false)
	private Long id;

	private String comments;

	@Column(name="CONTACT_NO")
	private String contactNo;

	private String createdby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createddate;

	private String email;

	private String firstname;

	@Column(name="IS_ACC_DEFAULT_PSWD")
	private Boolean isAccDefaultPswd;

	@Column(name="IS_ACC_LOCKED")
	private Boolean isAccLocked;

	@Column(name="IS_ENABLED")
	private Boolean isEnabled;

	@Column(name="IS_SERVICE_USER")
	private Boolean isServiceUser;

	private String lastname;

	private Boolean ldapauthentication;

	private String modifiedby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modifieddate;

	private String salt;

	@Column(name="U_NAME")
	private String uName;

	@Column(name="U_PWD")
	private String uPwd;
	
	//bi-directional many-to-many association to Role
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="USER_ROLES"
		, joinColumns={
			@JoinColumn(name="USER_ID", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="ROLE_ID", nullable=false)
			}
		)
	private List<Role> roles;

	//bi-directional many-to-one association to NumberAsgnmtHistory
	@OneToMany(mappedBy="appUser", fetch=FetchType.LAZY)
	//@Fetch(value = FetchMode.SUBSELECT)
	private List<NumberAsgnmtHistory> numberAsgnmtHistories;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
		name="NC_USER_GRP"
		, joinColumns={
			@JoinColumn(name="USER_ID", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="GRP_ID", nullable=false)
			}
		)
	//@Fetch(value = FetchMode.SUBSELECT)
	private List<NcAppUserGrp> ncUserGrps;
	

	public AppUser() {
	}
	
	public boolean validateCredential(String providedPassword) {
		return PasswordUtility.verifyUserPassword(providedPassword, getUPwd(), getSalt());
	}
	
	public void encryptAndSetPassword() {
		this.salt = PasswordUtility.getSalt(30);
		this.uPwd  = PasswordUtility.generateSecurePassword(getUPwd(), getSalt());
	}
	
	public void encryptAndSetNewPassword(String newPassword) {
		this.salt = PasswordUtility.getSalt(30);
		this.uPwd  = PasswordUtility.generateSecurePassword(newPassword, getSalt());
	}
	
	public boolean hasPrivilege(String priv) {
		boolean hasPriv = false;
	
		for(Role r : getRoles()) {
			if(null != r.getPrivileges()) {
				for(Privilege p : r.getPrivileges()) {
					if(p.getPName().equals(priv)) {
						hasPriv = true;
						return hasPriv;
					}
				}
			} 
		}
		
		return hasPriv;
	}
	
	@ApiModelProperty(notes = "Ignored", hidden=true)
	public boolean isSuperUser() {
		boolean isSuperUser = false;
		
		if(null != roles) {
			for(Role er : roles) {
				if(er.getIsSuperUserRole()) {
					isSuperUser = true;
					break;
				}
			}
		}
		
		return isSuperUser;
	}
	
	@ApiModelProperty(notes = "Ignored", hidden=true)
	public boolean isAdminUser() {
		boolean isAdminUser = false;
		
		if(null != getRoles()) {
			for(Role er : getRoles()) {
				if(er.getIsAdminRole()) {
					isAdminUser = true;
					break;
				}
			}
		}
		
		return isAdminUser;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getContactNo() {
		return this.contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public Boolean getIsAccDefaultPswd() {
		return this.isAccDefaultPswd;
	}

	public void setIsAccDefaultPswd(Boolean isAccDefaultPswd) {
		this.isAccDefaultPswd = isAccDefaultPswd;
	}

	public Boolean getIsAccLocked() {
		return this.isAccLocked;
	}

	public void setIsAccLocked(Boolean isAccLocked) {
		this.isAccLocked = isAccLocked;
	}

	public Boolean getIsEnabled() {
		return this.isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Boolean getIsServiceUser() {
		return this.isServiceUser;
	}

	public void setIsServiceUser(Boolean isServiceUser) {
		this.isServiceUser = isServiceUser;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Boolean getLdapauthentication() {
		return this.ldapauthentication;
	}

	public void setLdapauthentication(Boolean ldapauthentication) {
		this.ldapauthentication = ldapauthentication;
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

	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getUName() {
		return this.uName;
	}

	public void setUName(String uName) {
		this.uName = uName;
	}

	public String getUPwd() {
		return this.uPwd;
	}

	public void setUPwd(String uPwd) {
		this.uPwd = uPwd;
	}
	
	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public List<NumberAsgnmtHistory> getNumberAsgnmtHistories() {
		return this.numberAsgnmtHistories;
	}

	public void setNumberAsgnmtHistories(List<NumberAsgnmtHistory> numberAsgnmtHistories) {
		this.numberAsgnmtHistories = numberAsgnmtHistories;
	}

	public NumberAsgnmtHistory addNumberAsgnmtHistory(NumberAsgnmtHistory numberAsgnmtHistory) {
		getNumberAsgnmtHistories().add(numberAsgnmtHistory);
		numberAsgnmtHistory.setAppUser(this);

		return numberAsgnmtHistory;
	}

	public NumberAsgnmtHistory removeNumberAsgnmtHistory(NumberAsgnmtHistory numberAsgnmtHistory) {
		getNumberAsgnmtHistories().remove(numberAsgnmtHistory);
		numberAsgnmtHistory.setAppUser(null);

		return numberAsgnmtHistory;
	}
	
	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getuPwd() {
		return uPwd;
	}

	public void setuPwd(String uPwd) {
		this.uPwd = uPwd;
	}

	public List<NcAppUserGrp> getNcUserGrps() {
		return ncUserGrps;
	}

	public void setNcUserGrps(List<NcAppUserGrp> ncUserGrps) {
		this.ncUserGrps = ncUserGrps;
	}

/*	public List<NcUserGrp> getNcUserGrps() {
		return ncUserGrps;
	}

	public void setNcUserGrps(List<NcUserGrp> ncUserGrps) {
		this.ncUserGrps = ncUserGrps;
	}*/


}