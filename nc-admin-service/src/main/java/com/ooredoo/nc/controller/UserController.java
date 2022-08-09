package com.ooredoo.nc.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ooredoo.nc.config.LdapAuthnticationContext;
import com.ooredoo.nc.dto.UserPrinciple;
import com.ooredoo.nc.dto.UserSearchCriteria;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.model.AppUser;
import com.ooredoo.nc.model.NcAppUserGrp;
import com.ooredoo.nc.model.Role;
import com.ooredoo.nc.signature.UserServiceSignatureI;
import com.ooredoo.nc.utility.ApplicationConstants;
import com.ooredoo.nc.utility.ApplicationUtility;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = {"${service.usermanagement.base-url}"})
@Api(value="UserController", description="This API is responsible to handle operations for User entity", protocols="http", tags="User Service")
public class UserController extends Controller implements UserServiceSignatureI<AppUser>, ApplicationConstants {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private LdapAuthnticationContext ldapAuthContext;

	@Override
	public List<AppUser> findAll() throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AppUser> findAllValid() throws ApplicationException {
		return getRepository().getUserRepo().findAllByIsEnabled(true);
	}

	@Override
	public AppUser findByPublicId(String publicId, HttpServletRequest req) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AppUser findByPrivateId(Long privateId, HttpServletRequest req) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(String publicId, HttpServletRequest req) throws ApplicationException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exists(Long privateId, HttpServletRequest req) throws ApplicationException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AppUser create(AppUser user, HttpServletRequest req) throws ApplicationException {
		AppUser createdUser = null;
		LOGGER.debug("Creating new user for Number Cleanup Administration");
		
		if(getRepository().getUserRepo().existsByUName(user.getUName())) {
			LOGGER.error(ApplicationUtility.getMessage("CAS.009", new Object[] {user.getUName()}));
			throw new ApplicationException("CAS.009", new Object[] {user.getUName()});
		}
		
		if(getRepository().getUserRepo().existsByEmail(user.getEmail())) {
			LOGGER.error(ApplicationUtility.getMessage("CAS.010", new Object[] {user.getEmail()}));
			throw new ApplicationException("CAS.010", new Object[] {user.getEmail()});
		}
		
		if(getRepository().getUserRepo().existsByContactNo(user.getContactNo())) {
			LOGGER.error(ApplicationUtility.getMessage("CAS.011", new Object[] {user.getContactNo()}));
			throw new ApplicationException("CAS.011", new Object[] {user.getContactNo()});
		}
		
		if(user.getRoles().isEmpty()) {
			LOGGER.error(ApplicationUtility.getMessage("CAS.012", null));
			throw new ApplicationException("CAS.012");
		} else {
			
			List<Role> roles = new ArrayList<Role>();
			
			for(Role r : user.getRoles()) {
				Optional<Role> existingRole = getRepository().getRoleRepo().findByName(r.getName());
				
				if(existingRole.isPresent()) {
					roles.add(existingRole.get());
				} else {
					LOGGER.error(ApplicationUtility.getMessage("CAS.013", new Object[] {r.getName()}));
					throw new ApplicationException("CAS.013", new Object[] {r.getName()});
				}
			}
			user.getRoles().clear();
			user.setRoles(roles);
		}
		
		user.setIsEnabled(true);
		user.setIsAccLocked(false);
		
		if(user.getLdapauthentication()) {
			user.setLdapauthentication(true);
			user.setIsAccDefaultPswd(false);
		} else {
			user.setLdapauthentication(false);
			user.setIsAccDefaultPswd(true);
		}
		
		user.setCreateddate(new Date());
		//String pwd = RandomStringUtils.random(8, true, true);
		
		user.setUPwd(DEFAULT_PASSWORD);
		user.setComments(user.getComments());
		user.encryptAndSetPassword();
		user.setIsServiceUser(false);
		
		if(null != user.getNcUserGrps() && !user.getNcUserGrps().isEmpty()) {
			List<NcAppUserGrp> newGrpList = new ArrayList<NcAppUserGrp>();
			
			for(NcAppUserGrp g : user.getNcUserGrps()) {
				Optional<NcAppUserGrp> n = getRepository().getUserGroupRepo().findByGrpName(g.getGrpName());
				
				if(n.isPresent()) {
					newGrpList.add(n.get());
				} else {
					throw new ApplicationException("CAS.051", new Object[] {g.getGrpName()});
				}
			}
			
			user.getNcUserGrps().clear();
			user.setNcUserGrps(newGrpList);
			
		} /*else {
			throw new ApplicationException("CAS.052");
		}*/
		
		createdUser = getRepository().getUserRepo().save(user);
		
		return createdUser;
	}

	@Override
	public AppUser deleteByPrivateId(Long privateId, String deletedBy, HttpServletRequest req)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AppUser deleteByPublicId(String publicId, String deletedBy, HttpServletRequest req)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AppUser invalidateByPrivateId(Long privateId, String deletedBy, HttpServletRequest req)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AppUser invalidateByByPublicId(String publicId, String deletedBy, HttpServletRequest req)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	//@Secured(value = { "ROLE_CAS" })
	public AppUser authenticateUser(UserPrinciple user, HttpServletRequest req) throws ApplicationException {
		Optional<AppUser> requestedUser = null;
		
		if(user == null) {
			throw new ApplicationException("CAS.005");
			
		} else {
			
			String serverPort = environment.getProperty("server.port");
			LOGGER.info("Service CAS serving at port :" + serverPort + " with channel -" + req.getHeader("CHANNEL"));
			
			if(user.getUsername() != null && !user.getUsername().isEmpty() 
					&& user.getCurrentPassword() != null && !user.getCurrentPassword().isEmpty()) {
				
				requestedUser = getRepository().getUserRepo().findByUNameAndIsEnabledEqualsAndIsAccLockedEquals(user.getUsername(), true, false);
				//requestedUser = getRepository().getUserRepo().getUserDetailsForAuthentication(user.getUsername(), true, false);
				
				if(!requestedUser.isPresent()) {
					throw new ApplicationException("CAS.015");
				}
				
				//List<Role> userRoles = getRepository().getRoleRepo().getRolesForUser(requestedUser.get().getId());
				//requestedUser.get().setRoles(userRoles);
				
				if(requestedUser.get().getLdapauthentication()) {
					//LDAP Authentication to be implemented
					
					DirContext ctx = null;
					try {
						
						if(!Boolean.valueOf(ldapAuthContext.getBypassLdapAuthForTesting())) {
							Hashtable<String, String> env = ldapAuthContext.getInitialLdapContext(user.getUsername(), user.getCurrentPassword());
							ctx = new InitialDirContext(env);
						} else {
							LOGGER.info("Please note LDAP authntication is working on courtesy mode. actual validation is not happening using Ldap Auth Context.");
						}
						
						//Audit Log Entry
						//validateActionLogActionEvent(req, new Object[] {user.getUsername()});
						return requestedUser.get();
						
					} catch(NamingException ne) {
						LOGGER.error("Unable to Login to LDAP. Cause :" + ne.getMessage() + " Detail Explanation : " + ne.getExplanation(), ne);
						throw new ApplicationException("CAS.016", ne);
						
					} catch(Exception e) {
						LOGGER.error("Unable to Login to LDAP. Cause :" + e.getMessage() + " Detail Explanation : " + e.getMessage());
						throw new ApplicationException("CAS.016", e);
						
					} finally {
						if(null != ctx) {
							try {ctx.close();} catch(Exception e) {LOGGER.error(e.getMessage(), e);}
						}
					}
				} else {
					if(requestedUser.get().validateCredential(user.getCurrentPassword())) {
						return requestedUser.get();
						
					} else {
						throw new ApplicationException("CAS.006");
					}
				}
			} else {
				throw new ApplicationException("CAS.005");
			}
		}	
	}

	@Override
	@Secured(value = { "ROLE_CAS" })
	public AppUser updateCredential(UserPrinciple userPrinciple, HttpServletRequest req) throws ApplicationException {
		AppUser u = null;
		Optional<AppUser> existingUser = getRepository().getUserRepo().findByUName(userPrinciple.getUsername());
		
		if(existingUser.isPresent()) {
			u = existingUser.get();
			
			if(u.getLdapauthentication()) {
				throw new ApplicationException("CAS.017");
			}
			
			if(userPrinciple.getResetToDefaultPassword()) {
				u.encryptAndSetNewPassword(DEFAULT_PASSWORD);
				u.setIsAccDefaultPswd(true);
				
				getRepository().getUserRepo().save(u);
				//validateActionLogActionEvent(req, new Object[] {userPrinciple.getUsername()});
				
			} else {
				if(null != userPrinciple.getCurrentPassword() && !userPrinciple.getCurrentPassword().isEmpty() 
						&& u.validateCredential(userPrinciple.getCurrentPassword())) {
					u.encryptAndSetNewPassword(userPrinciple.getNewPassword());
					
					if(userPrinciple.getUpdateDefaultPassword()) {
						u.setIsAccDefaultPswd(false);
					}
					
					getRepository().getUserRepo().save(u);
					//validateActionLogActionEvent(req, new Object[] {userPrinciple.getUsername()});
				} else {
					throw new ApplicationException("CAS.014");
				}
			}
		} else {
			throw new ApplicationException("CAS.002", new Object[] {userPrinciple.getUsername()});
		}
		
		return u;
	}

	@Override
	public AppUser manageRole(UserPrinciple userPrinciple, HttpServletRequest req) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Secured(value = { "ROLE_CAS" })
	public AppUser updateUser(AppUser user, HttpServletRequest req) throws ApplicationException {
		AppUser updatedUser = null;
		
		
		if(getRepository().getUserRepo().existsByUName(user.getUName())) {
			AppUser existingUser = null;
			
			Optional<AppUser> existingUserO = getRepository().getUserRepo().findByUName(user.getUName());
			
			if(existingUserO.isPresent()) {
				existingUser = existingUserO.get();
			}
			
			if(null != user.getFirstname() && !user.getFirstname().isEmpty()) {
				existingUser.setFirstname(user.getFirstname());
			}
			
			if(null != user.getLastname() && !user.getLastname().isEmpty()) {
				existingUser.setLastname(user.getLastname());
			}
			
			if(null != user.getContactNo() && !user.getContactNo().isEmpty() && !user.getContactNo().equals(existingUser.getContactNo())) {
				if(!getRepository().getUserRepo().existsByContactNo(user.getContactNo())) {
					existingUser.setContactNo(user.getContactNo());
				} else {
					throw new ApplicationException("CAS.011", new Object[] {user.getContactNo()});
				}
			}
			
			if(null != user.getEmail() && !user.getEmail().isEmpty() && !user.getEmail().equals(existingUser.getEmail())) {
				if(!getRepository().getUserRepo().existsByEmail(user.getEmail())) {
					existingUser.setEmail(user.getEmail());
				} else {
					throw new ApplicationException("CAS.010", new Object[] {user.getEmail()});
				}
			}
			
			if(null != user.getIsAccLocked()) {
				existingUser.setIsAccLocked(user.getIsAccLocked());
			}
			
			if(null != user.getIsEnabled()) {
				existingUser.setIsEnabled(user.getIsEnabled());
			}	
			
			if(null != user.getLdapauthentication()) {
				
				if(!existingUser.getLdapauthentication() && user.getLdapauthentication()) {
					existingUser.setLdapauthentication(user.getLdapauthentication());
					existingUser.setIsAccDefaultPswd(false);
					
				} else if(existingUser.getLdapauthentication() && !user.getLdapauthentication()) {
					existingUser.setIsAccDefaultPswd(true);
					existingUser.setUPwd(DEFAULT_PASSWORD);
					existingUser.encryptAndSetPassword();
				}
				
				existingUser.setLdapauthentication(user.getLdapauthentication());
			}
			
			if(null != user.getModifiedby() && !user.getModifiedby().isEmpty()) {
				existingUser.setModifiedby(user.getModifiedby());
				existingUser.setModifieddate(new Date());
			} else {
				throw new ApplicationException("CAS.008", new Object[] {"User", "Modified By"});
			}
			
			if(null != user.getComments() && !user.getComments().isEmpty()) {
				existingUser.setComments(user.getComments());
			}
			
			if(null != user.getRoles() && !user.getRoles().isEmpty()) {
				List<Role> newRoleList = new ArrayList<>();
				
				for(Role r : user.getRoles()) {
					Optional<Role> role = getRepository().getRoleRepo().findByName(r.getName());
					
					if(role.isPresent()) {
						newRoleList.add(role.get());
					} else {
						throw new ApplicationException("CAS.013", new Object[] {r.getName()});
					}
				}
				
				existingUser.getRoles().clear();
				existingUser.setRoles(newRoleList);
			}
			
			if(null != user.getNcUserGrps() && !user.getNcUserGrps().isEmpty()) {
				List<NcAppUserGrp> newGrpList = new ArrayList<NcAppUserGrp>();
				
				for(NcAppUserGrp g : user.getNcUserGrps()) {
					Optional<NcAppUserGrp> n = getRepository().getUserGroupRepo().findByGrpName(g.getGrpName());
					
					if(n.isPresent()) {
						newGrpList.add(n.get());
					} else {
						throw new ApplicationException("CAS.051", new Object[] {g.getGrpName()});
					}
				}
				
				existingUser.getNcUserGrps().clear();
				existingUser.setNcUserGrps(newGrpList);
				
			}
			
			
			updatedUser = getRepository().getUserRepo().save(existingUser);
			
			//Audit Log Entry
			//validateActionLogActionEvent(req, new Object[] {updatedUser.getName()});
			
		} else {
			throw new ApplicationException("CAS.002", new Object[] {user.getUName()});
		}
		
		return updatedUser;
	}

	@Override
	@Secured(value = { "ROLE_CAS" })
	public List<AppUser> searchUser(UserSearchCriteria userSearchCriteria, HttpServletRequest req) throws ApplicationException {
		List<Role> serviceRoles = getRepository().getRoleRepo().findByNameNotIn(getServiceRoleNames());
		List<Long> rIds = new ArrayList<Long>();
		
		for(Role r : serviceRoles) {
			rIds.add(r.getId());
		}
		
		LOGGER.info("Searching User");
		List<AppUser> uList = getRepository().getUserRepo().findAll(new Specification<AppUser>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<AppUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<>();
				
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
		}, new Sort(Direction.ASC, "firstname"));
		
		return uList;
	}
}
