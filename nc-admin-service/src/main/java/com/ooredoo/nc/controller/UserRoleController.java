package com.ooredoo.nc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.model.AppUser;
import com.ooredoo.nc.model.Privilege;
import com.ooredoo.nc.model.Role;
import com.ooredoo.nc.signature.ServiceSignatureI;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = {"${service.userrolemanagement.base-url}"})
public class UserRoleController extends Controller implements ServiceSignatureI<Role> {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleController.class);
	@Override
	@RolesAllowed({"ROLE_CAS"})
	public List<Role> findAll() throws ApplicationException {
		return getRepository().getRoleRepo().findAllByIsServiceRole(false);
	}

	@Override
	public List<Role> findAllValid() throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@RolesAllowed({"ROLE_CAS"})
	public Role findByPublicId(String publicId, HttpServletRequest req) throws ApplicationException {
		Optional<Role> r = getRepository().getRoleRepo().findByName(publicId);
		
		if(r.isPresent()) {
			return r.get();
		} else {
			throw new ApplicationException("CAS.019", new Object[] {publicId});
		}
	}

	@Override
	public Role findByPrivateId(Long privateId, HttpServletRequest req) throws ApplicationException {
		Optional<Role> r = getRepository().getRoleRepo().findById(privateId);
		
		if(r.isPresent()) {
			return r.get();
		} else {
			throw new ApplicationException("CAS.020", new Object[] {privateId});
		}
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
	@RolesAllowed({"ROLE_CAS"})
	public Role create(Role reqRole, HttpServletRequest req) throws ApplicationException {

		if(null != reqRole) {
			
			//validate(reqRole);
			
			if(null == reqRole.getDescription() || reqRole.getDescription().isEmpty()) {
				reqRole.setDescription(reqRole.getName());
			}
			
			reqRole.setName("ROLE_" + reqRole.getName());
			
			if(null != reqRole.getPrivileges() && !reqRole.getPrivileges().isEmpty()) {
				List<Privilege> pList = new ArrayList<Privilege>();
				
				for(Privilege p : reqRole.getPrivileges()) {
					Optional<Privilege> priv = getRepository().getPrivRepo().findByPName(p.getPName());
					
					if(priv.isPresent()) {
						pList.add(priv.get());
					} else {
						throw new ApplicationException("CAS.023", new Object[] {p.getPName()});
					}
				}
				
				reqRole.getPrivileges().clear();
				reqRole.setPrivileges(pList);
				
				if(null != reqRole.getCreatedby() && !reqRole.getCreatedby().isEmpty()) {
					Optional<AppUser> u = getRepository().getUserRepo().findByUName(reqRole.getCreatedby());
					
					if(u.isPresent()) {
						AppUser createdByUser = u.get();
						
						if(createdByUser.getIsEnabled() && !createdByUser.getIsAccLocked()) {
							
							
							//if(createdByUser.isSuperUser()) {
								reqRole.setCreatedby(String.valueOf(createdByUser.getId()));
								reqRole.setCreateddate(new Date());
							/*} else {
								throw new ApplicationException("CAS.018", new Object[] {reqRole.getCreatedby()});
							}*/
						} else {
							throw new ApplicationException("CAS.015");
						}
					} else {
						throw new ApplicationException("CAS.018", new Object[] {reqRole.getCreatedby()});
					}
				} else {
					throw new ApplicationException("CAS.008", new Object[] {"User", "Created By"});
				}
				
			} else {
				throw new ApplicationException("CAS.024");
			}
			
			/*if(reqRole.getIsAdminRole() && getRepository().getRoleRepo().findByIsAdminRole(true).isPresent()) {
				throw new ApplicationException("CAS.029");
			}
			
			if(reqRole.getIsSuperUserRole() && getRepository().getRoleRepo().findByIsSuperUserRole(true).isPresent()) {
				throw new ApplicationException("CAS.030");
			}*/

			getRepository().getRoleRepo().save(reqRole);
			
			return reqRole;
		} else {
			throw new ApplicationException("CAS.021");
		}
	}

	@Override
	public Role deleteByPrivateId(Long privateId, String deletedBy, HttpServletRequest req)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Role deleteByPublicId(String publicId, String deletedBy, HttpServletRequest req)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Role invalidateByPrivateId(Long privateId, String deletedBy, HttpServletRequest req)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Role invalidateByByPublicId(String publicId, String deletedBy, HttpServletRequest req)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Secured(value = { "ROLE_CAS" })
	@PutMapping(value="/update")
	@ApiOperation(value = "This operation is responsible to update a role definition", response = Role.class
	,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public Role update(@RequestBody(required=true) Role reqRole, HttpServletRequest req) throws ApplicationException {
		Role updatedRole = null;
		
		if(null != reqRole) {
			Optional<AppUser> u = null;
			LOGGER.info("Modified By:{}", reqRole.getModifiedby());
			
			if(null != reqRole.getModifiedby() && !reqRole.getModifiedby().isEmpty()) {
				
				u = getRepository().getUserRepo().findByUName(reqRole.getModifiedby());
				
			} else {
				throw new ApplicationException("CAS.008", new Object[] {"Role", "Modified By"});
			}
			
			AppUser updatedByUser = null;
			
			if(u.isPresent()) {
				updatedByUser = u.get();
				
				if(updatedByUser.getIsEnabled() && !updatedByUser.getIsAccLocked()) {
					
					/*if(!updatedByUser.isSuperUser()) {
						
						throw new ApplicationException("CAS.018", new Object[] {reqRole.getModifiedby()});
					}*/ 
					
				} else {
					throw new ApplicationException("CAS.015");
				}
				
			} else {
				throw new ApplicationException("CAS.018", new Object[] {reqRole.getModifiedby()});
			}
			
			Optional<Role> existingRoleT = getRepository().getRoleRepo().findById(reqRole.getId());
			
			if(existingRoleT.isPresent()) {
				updatedRole = existingRoleT.get();
				
				if(null != reqRole.getName() && !reqRole.getName().isEmpty() && !updatedRole.getName().equals(reqRole.getName())) {
					
					if(!getRepository().getRoleRepo().existsByName(reqRole.getName())) {
						updatedRole.setName(reqRole.getName());
					} else {
						throw new ApplicationException("CAS.022", new Object[] {reqRole.getName()});
					}
				}
				
				List<Privilege> newPrivList = new ArrayList<Privilege>();
				
				for(Privilege p : reqRole.getPrivileges()) {
					Optional<Privilege> ePrivO = getRepository().getPrivRepo().findByPName(p.getPName()); 
					
					if(ePrivO.isPresent()) {
						newPrivList.add(ePrivO.get());
					} else {
						throw new ApplicationException("CAS.019");
					}
					
				}
				
				if(null != updatedRole.getPrivileges()) {
					updatedRole.getPrivileges().clear();
				}
				
				updatedRole.setPrivileges(newPrivList);
				updatedRole.setModifiedby(String.valueOf(updatedByUser.getId()));
				updatedRole.setModifieddate(new Date());
				
				if(null != reqRole.getDescription() && !reqRole.getDescription().isEmpty()) {
					updatedRole.setDescription(reqRole.getDescription());
				}
				
				getRepository().getRoleRepo().save(updatedRole);
			} else {
				throw new ApplicationException("CAS.020");
			}
		} else {
			throw new ApplicationException("CAS.021");
		}
		
		return updatedRole;
	}

}
