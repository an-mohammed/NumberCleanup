package com.ooredoo.nc.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ooredoo.nc.dto.UserPrinciple;
import com.ooredoo.nc.dto.UserSearchCriteria;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.model.AppUser;
import com.ooredoo.nc.model.Role;
import com.ooredoo.nc.signature.UserServiceSignatureI;
import com.ooredoo.nc.signature.ValidateI;
import com.ooredoo.nc.utility.ApplicationConstants;
import com.ooredoo.nc.utility.ApplicationUtility;
import com.ooredoo.nc.utility.CryptoConverter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = {"${service.serviceuser.base-url}"})
@Api(value="ServiceUserController", description="This API is responsible to handle operations for ServiceUser", protocols="http", tags="Service-AppUser-Service")
public class ServiceUserController extends Controller implements UserServiceSignatureI<AppUser>, ValidateI<AppUser>, ApplicationConstants {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping(value="/getServiceRoleNames", produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String, String> getServiceRoleMap() {
		Map<String, String> resp = new HashMap<String, String>();
		List<Role> serviceR = super.getServiceRoles();
		
		for(Role r : serviceR) {
			resp.put(r.getName(), r.getDescription());
		}
		return resp;
	}

	@Override
	@ApiOperation(value = "This operation is responsible to extract all Service AppUser definition", response = AppUser.class
	,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE, responseContainer="List")
	public List<AppUser> findAll() throws ApplicationException {
		return getRepository().getUserRepo().findByRolesNameIn(getServiceRoleNames());
	}

	@Override
	@ApiOperation(value = "This operation is responsible to extract all valid service AppUser definition", response = AppUser.class
	,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE, responseContainer="List")
	public List<AppUser> findAllValid() throws ApplicationException {
		return getRepository().getUserRepo().findByIsEnabledEqualsAndRolesNameIn(true, getServiceRoleNames());
	}

	@Override
	@ApiOperation(value = "This operation is not supported", response = AppUser.class
	,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public AppUser findByPublicId(String publicId, HttpServletRequest req) throws ApplicationException {
		throw new ApplicationException("CAS.999");
	}

	@Override
	@ApiOperation(value = "This operation is not supported", response = AppUser.class
	,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public AppUser findByPrivateId(Long privateId, HttpServletRequest req) throws ApplicationException {
		throw new ApplicationException("CAS.999");
	}

	@Override
	@ApiOperation(value = "This operation is not supported", response = AppUser.class
	,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public boolean exists(String publicId, HttpServletRequest req) throws ApplicationException {
		throw new ApplicationException("CAS.999");
	}

	@Override
	@ApiOperation(value = "This operation is not supported", response = AppUser.class
	,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public boolean exists(Long privateId, HttpServletRequest req) throws ApplicationException {
		throw new ApplicationException("CAS.999");
	}

	@Override
	@ApiOperation(value = "This operation is responsible to create a service AppUser definition", response = AppUser.class
	,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public AppUser create(AppUser reqUser, HttpServletRequest req) throws ApplicationException {
		AppUser respUser = null;
		
		if(null != reqUser) {
			validate(reqUser);
			
			if(getRepository().getUserRepo().existsByUName(reqUser.getUName())) {
				throw new ApplicationException("CAS.009", new Object[] {reqUser.getUName()});
			}
			
			if(getRepository().getUserRepo().existsByEmail(reqUser.getEmail())) {
				throw new ApplicationException("CAS.010", new Object[] {reqUser.getEmail()});
			}
			
			if(getRepository().getUserRepo().existsByContactNo(reqUser.getContactNo())) {
				throw new ApplicationException("CAS.011", new Object[] {reqUser.getContactNo()});
			}
			
			reqUser.setComments("Number cleanup Service AppUser");
			
			respUser = new AppUser();
			respUser.setUName(reqUser.getUName());
			respUser.setUPwd("ENC(" + CryptoConverter.getEncryptedValue(reqUser.getUPwd()) + ")");
			respUser.setComments("Please use the generated password as it is for the AppUser credential");
			
			reqUser.setFirstname("N/A");
			reqUser.setLastname("N/A");
			reqUser.setUPwd(passwordEncoder.encode(reqUser.getUPwd()));
			reqUser.setLdapauthentication(false);
			reqUser.setIsAccDefaultPswd(false);
			reqUser.setIsEnabled(true);
			reqUser.setIsAccLocked(false);
			reqUser.setSalt(ApplicationUtility.getFormattedDate(DF_YYMMDDHHMMSS, new Date()));
			reqUser.setContactNo(ApplicationUtility.getFormattedDate(DF_YYMMDDHHMMSS, new Date()));
			reqUser.setEmail(ApplicationUtility.getFormattedDate(DF_YYMMDDHHMMSS, new Date()));
			reqUser.setIsServiceUser(true);
			
			if(null != reqUser.getCreatedby() && !reqUser.getCreatedby().isEmpty()) {
				Optional<AppUser> u = getRepository().getUserRepo().findByUName(reqUser.getCreatedby());
				
				if(u.isPresent()) {
					AppUser createdByUser = u.get();
					
					if(createdByUser.getIsEnabled() && !createdByUser.getIsAccLocked()) {
						
						
						if(createdByUser.isSuperUser() || createdByUser.isAdminUser()) {
							reqUser.setCreatedby(reqUser.getCreatedby());
							reqUser.setCreateddate(new Date());
						} else {
							throw new ApplicationException("CAS.018", new Object[] {reqUser.getCreatedby()});
						}
					} else {
						throw new ApplicationException("CAS.015");
					}
				} else {
					throw new ApplicationException("CAS.018", new Object[] {reqUser.getCreatedby()});
				}
			} else {
				throw new ApplicationException("CAS.008", new Object[] {"AppUser", "Created By"});
			}
			
			if(null == reqUser.getRoles() || reqUser.getRoles().isEmpty()) {
				throw new ApplicationException("CAS.012");
			} else {
				Role reqRole = reqUser.getRoles().get(0);
				List<Role> roles = new ArrayList<Role>();
				
				Optional<Role> rO = getRepository().getRoleRepo().findByName(reqRole.getName());
				
				if(rO.isPresent()) {
					roles.add(rO.get());
					reqUser.setRoles(roles);
				} else {
					throw new ApplicationException("CAS.019", new Object[] {reqRole.getName()});
				}
			}
			
			getRepository().getUserRepo().save(reqUser);
			
		} else {
			throw new ApplicationException("CAS.007");
		}
		
		return respUser;
	}

	@Override
	@ApiOperation(value = "This operation is not supported", response = AppUser.class
	,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public AppUser deleteByPrivateId(Long privateId, String deletedBy, HttpServletRequest req) throws ApplicationException {
		throw new ApplicationException("CAS.999");
	}

	@Override
	@ApiOperation(value = "This operation is not supported", response = AppUser.class
	,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public AppUser deleteByPublicId(String publicId, String deletedBy, HttpServletRequest req) throws ApplicationException {
		throw new ApplicationException("CAS.999");
	}

	@Override
	@ApiOperation(value = "This operation is not supported", response = AppUser.class
	,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public AppUser invalidateByPrivateId(Long privateId, String deletedBy, HttpServletRequest req) throws ApplicationException {
		throw new ApplicationException("CAS.999");
	}

	@Override
	@ApiOperation(value = "This operation is responsible to invalidate a service AppUser", response = AppUser.class
	,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public AppUser invalidateByByPublicId(String publicId, String deletedBy, HttpServletRequest req) throws ApplicationException {
		throw new ApplicationException("CAS.999");
	}

	@Override
	public void validate(AppUser AppUser) throws ApplicationException {
		if(AppUser == null) {
			throw new ApplicationException("CAS.007");
		}
		
		if(AppUser.getUName() == null || AppUser.getUName().isEmpty()) {
			throw new ApplicationException("CAS.008", new Object[] {"AppUser", "Username"});
		}
		
		if(AppUser.getContactNo() == null || AppUser.getContactNo().isEmpty()) {
			throw new ApplicationException("CAS.008", new Object[] {"AppUser", "Contact No."});
		}
		
		if(AppUser.getEmail() == null || AppUser.getEmail().isEmpty()) {
			throw new ApplicationException("CAS.008", new Object[] {"AppUser", "e-Mail"});
		}
		
		if(AppUser.getFirstname() == null || AppUser.getFirstname().isEmpty()) {
			throw new ApplicationException("CAS.008", new Object[] {"AppUser", "First Name"});
		}
		
		if(AppUser.getLastname() == null || AppUser.getLastname().isEmpty()) {
			throw new ApplicationException("CAS.008", new Object[] {"AppUser", "Last Name"});
		}
		
		if(null == AppUser.getRoles() || AppUser.getRoles().isEmpty()) {
			throw new ApplicationException("CAS.012");
		}
	}

	@Override
	@ApiOperation(value = "This operation is not supported", response = AppUser.class
	,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public AppUser authenticateUser(UserPrinciple userPrinciple, HttpServletRequest req) throws ApplicationException {
		throw new ApplicationException("CAS.999");
	}

	@Override
	@ApiOperation(value = "This operation is not supported", response = AppUser.class
	,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public AppUser updateCredential(UserPrinciple userPrinciple, HttpServletRequest req) throws ApplicationException {
		throw new ApplicationException("CAS.999");
	}

	@Override
	@ApiOperation(value = "This operation is not supported", response = AppUser.class
	,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public AppUser manageRole(UserPrinciple userPrinciple, HttpServletRequest req) throws ApplicationException {
		throw new ApplicationException("CAS.999");
	}

	@Override
	@ApiOperation(value = "This operation is not supported", response = AppUser.class
	,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public AppUser updateUser(AppUser user, HttpServletRequest req) throws ApplicationException {
		AppUser resp = null;
		
		if(null != user.getModifiedby() && !user.getModifiedby().isEmpty()) {
			Optional<AppUser> modByUO = getRepository().getUserRepo().findByUNameAndIsEnabledEquals(user.getModifiedby(), true);
			
			if(modByUO.isPresent()) {
				AppUser modBy = modByUO.get();
				
				if(modBy.isSuperUser() || modBy.isAdminUser()) {
					Optional<AppUser> uO = getRepository().getUserRepo().findByUName(user.getUName());
					
					if(uO.isPresent()) {
						AppUser u = uO.get();
						u.setFirstname(user.getFirstname());
						u.setLastname(user.getLastname());
						
						Optional<AppUser> uMailO = getRepository().getUserRepo().findByEmail(user.getEmail());
						if(!uMailO.isPresent() || uMailO.get().getUName().equals(u.getUName())) {
							u.setEmail(user.getEmail());
						} else {
							throw new ApplicationException("CAS.010", new Object[] {user.getEmail()});
						}
						
						Optional<AppUser> uContactO = getRepository().getUserRepo().findByContactNo(user.getContactNo());
						if(!uContactO.isPresent() || uContactO.get().getUName().equals(u.getUName())) {
							u.setContactNo(user.getContactNo());
						} else {
							throw new ApplicationException("CAS.011", new Object[] {user.getContactNo()});
						}
						
						u.setIsEnabled(user.getIsEnabled());
						u.setModifiedby(user.getModifiedby());
						u.setModifieddate(new Date());
						
						resp = getRepository().getUserRepo().save(u);
						
					} else {
						throw new ApplicationException("CAS.002", new Object[] {user.getUName()});
					}
				} else {
					throw new ApplicationException("CAS.018", new Object[] {modBy.getUName()});
				}
			} else {
				throw new ApplicationException("CAS.002", new Object[] {user.getModifiedby()});
			}
		} else {
			throw new ApplicationException("CAS.008", new Object[] {"Service AppUser" , "Modified By"});
		}
		
		return resp;
	}

	@Override
	@ApiOperation(value = "This operation is responsible to search an service AppUser based on username. It can also extract disabled AppUser", response = AppUser.class
	,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE
	, responseContainer="List")
	public List<AppUser> searchUser(@RequestBody UserSearchCriteria userSearchCriteria, HttpServletRequest req) throws ApplicationException {
		//validateRequestHeader(req);

		List<Role> serviceRoles = getRepository().getRoleRepo().findByNameIn(getServiceRoleNames());
		List<Long> rIds = new ArrayList<Long>();
		
		for(Role r : serviceRoles) {
			rIds.add(r.getId());
		}
		
		List<AppUser> uList = getRepository().getUserRepo().findAll(new Specification<AppUser>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<AppUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();
				
				Join<AppUser, Role> regions = root.join("roles");
				predicates.add(regions.in(rIds));
				query.distinct(true);
				
				if (null != userSearchCriteria.getName() && !userSearchCriteria.getName().isEmpty()) {
					predicates.add(cb.like(cb.upper(root.get("firstname")), "%" + userSearchCriteria.getName().toUpperCase() + "%"));
				}

				if (null != userSearchCriteria.getUsername() && !userSearchCriteria.getUsername().isEmpty()) {
					predicates.add(cb.equal(root.get("uName"), userSearchCriteria.getUsername()));
				}

				if (null != userSearchCriteria.getMobile() && !userSearchCriteria.getMobile().isEmpty()) {
					predicates.add(cb.equal(root.get("contactNo"), userSearchCriteria.getMobile()));
				}
				
				if (null != userSearchCriteria.getEmail() && !userSearchCriteria.getEmail().isEmpty()) {
					predicates.add(cb.equal(root.get("email"), userSearchCriteria.getEmail()));
				}
				
				if(!userSearchCriteria.isIncludeInvalid()) {
					predicates.add(cb.equal(root.get("isEnabled"), new BigDecimal(1)));
				}
				
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		});
		
		return uList;
	}

}
