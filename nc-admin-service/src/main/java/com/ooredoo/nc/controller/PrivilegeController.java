package com.ooredoo.nc.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.model.AppUser;
import com.ooredoo.nc.model.Privilege;
import com.ooredoo.nc.signature.ServiceSignatureI;
import com.ooredoo.nc.signature.ValidateI;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = {"${service.privmanagement.base-url}"})
@Api(value="PrivilegeController", description="This API is responsible to handle operations for Privilege entity", basePath="/priv", protocols="http", tags="Privilege-Service")
public class PrivilegeController extends Controller implements ServiceSignatureI<Privilege>, ValidateI<Privilege> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PrivilegeController.class);

	/*@Autowired
	private CompositeRepository repository;*/
	
	@Override
	@Secured(value = { "ROLE_CAS" })
	@ApiOperation(value = "This operation is responsible to find all Roles", response = Privilege.class
	,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE	, responseContainer="List")
	public List<Privilege> findAll() throws ApplicationException {
		return getRepository().getPrivRepo().findAllByOrderByIdAsc();
	}

	@Override
	@Secured(value = { "ROLE_CAS" })
	@ApiOperation(value = "This operation is responsible to find all valid privilege", response = Privilege.class
	,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE	)
	public List<Privilege> findAllValid() throws ApplicationException {
		throw new ApplicationException("CAS.999");
	}

	@Override
	@Secured(value = { "ROLE_CAS" })
	@ApiOperation(value = "This operation is responsible to find a privilege by its name", response = Privilege.class
	,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE	)
	public Privilege findByPublicId(String publicId, HttpServletRequest req) throws ApplicationException {
		Optional<Privilege> r = getRepository().getPrivRepo().findByPName(publicId);
		
		if(r.isPresent()) {
			return r.get();
		} else {
			throw new ApplicationException("CAS.019", new Object[] {publicId});
		}
	}

	@Override
	@Secured(value = { "ROLE_CAS" })
	@ApiOperation(value = "This operation is responsible to find a privilege by its Id", response = Privilege.class
	,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE	)
	public Privilege findByPrivateId(Long privateId, HttpServletRequest req) throws ApplicationException {
		Optional<Privilege> r = getRepository().getPrivRepo().findById(privateId);
		
		if(r.isPresent()) {
			return r.get();
		} else {
			throw new ApplicationException("CAS.020", new Object[] {privateId});
		}
	}

	@Override
	@Secured(value = { "ROLE_CAS" })
	@ApiOperation(value = "This operation is responsible to to chech existance of a privilege by its name", response = Privilege.class
	,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE	)
	public boolean exists(String publicId, HttpServletRequest req) throws ApplicationException {
		throw new ApplicationException("CAS.999");
	}

	@Override
	@Secured(value = { "ROLE_CAS" })
	@ApiOperation(value = "This operation is responsible to to chech existance of a privilege by its id", response = Privilege.class
	,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE	)
	public boolean exists(Long privateId, HttpServletRequest req) throws ApplicationException {
		throw new ApplicationException("CAS.999");
	}

	@Override
	@Secured(value = { "ROLE_CAS" })
	@ApiOperation(value = "This operation is responsible to create a new privilege definition", response = Privilege.class
	,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE	)
	public Privilege create(Privilege reqPrivilege, HttpServletRequest req) throws ApplicationException {
		
		if(null != reqPrivilege) {
			
			validate(reqPrivilege);
			
			if(null == reqPrivilege.getDescription() || reqPrivilege.getDescription().isEmpty()) {
				reqPrivilege.setDescription(reqPrivilege.getPName());
			}
			
			reqPrivilege.setCreateddate(new Date());

			Privilege response =  getRepository().getPrivRepo().save(reqPrivilege);
			
			LOGGER.info("Privilege " + reqPrivilege.getPName() + " is successfully created");
			
			return response;
			
		} else {
			throw new ApplicationException("CAS.021");
		}
	}
	
	@Override
	@Secured(value = { "ROLE_CAS" })
	@ApiOperation(value = "This operation is responsible to delete a privilege by its id", response = Privilege.class
	,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE	)
	public Privilege deleteByPrivateId(Long privateId, String deletedBy, HttpServletRequest req)
			throws ApplicationException {
		throw new ApplicationException("CAS.999");
	}

	@Override
	@Secured(value = { "ROLE_CAS" })
	@ApiOperation(value = "This operation is responsible to delete a privilege by its name", response = Privilege.class
	,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE	)
	public Privilege deleteByPublicId(String publicId, String deletedBy, HttpServletRequest req)
			throws ApplicationException {
		
		Privilege existingRole = null;
		
		if(null != publicId && !publicId.isEmpty()) {
			Optional<AppUser> u = getRepository().getUserRepo().findByUName(deletedBy);
			
			if(u.isPresent()) {
				AppUser createdByUser = u.get();
				
				if(createdByUser.getIsEnabled() && !createdByUser.getIsAccLocked()) {
					
					
					//if(!createdByUser.isSuperUser()) {
					//	throw new ApplicationException("CAS.018", new Object[] {deletedBy});
					//} else {
						Optional<Privilege> ePriv = getRepository().getPrivRepo().findByPName(publicId);
						
						if(ePriv.isPresent()) {
							existingRole = ePriv.get();
							getRepository().getPrivRepo().delete(ePriv.get());
						} else {
							throw new ApplicationException("CAS.019", new Object[] {publicId});
						}
					//}
					
				} else {
					throw new ApplicationException("CAS.015");
				}
				
			} else {
				throw new ApplicationException("CAS.018", new Object[] {deletedBy});
			}
		}
		return existingRole;
	}

	@Override
	@Secured(value = { "ROLE_CAS" })
	@ApiOperation(value = "This operation is responsible to invalidate a privilege by its id", response = Privilege.class
	,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE	)
	public Privilege invalidateByPrivateId(Long privateId, String deletedBy, HttpServletRequest req)
			throws ApplicationException {
		throw new ApplicationException("CAS.999");
	}

	@Override
	@Secured(value = { "ROLE_CAS" })
	@ApiOperation(value = "This operation is responsible to invalidate a privilege by its name", response = Privilege.class
	,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE	)
	public Privilege invalidateByByPublicId(String publicId, String deletedBy, HttpServletRequest req)
			throws ApplicationException {
		throw new ApplicationException("CAS.999");
	}

	@Override
	public void validate(Privilege r) throws ApplicationException {
		if(null == r.getPName() || r.getPName().isEmpty()) {
			throw new ApplicationException("CAS.008", new Object[] {"Role", "Name"});
		}
		
		if(null == r.getCreatedby()) {
			throw new ApplicationException("CAS.008", new Object[] {"Role", "Created By"});
		}
		
		if(getRepository().getPrivRepo().existsByPName(r.getPName())) {
			throw new ApplicationException("CAS.022", new Object[] {r.getPName()});
		}
		
		Optional<AppUser> u = getRepository().getUserRepo().findById(r.getCreatedby());
		
		if(u.isPresent()) {
			AppUser createdByUser = u.get();
			
			if(createdByUser.getIsEnabled() && !createdByUser.getIsAccLocked()) {
				/*if(!createdByUser.isSuperUser()) {
					throw new ApplicationException("CAS.018", new Object[] {r.getCreatedby()});
				}*/ 
				
			} else {
				throw new ApplicationException("CAS.015");
			}
			
		} else {
			throw new ApplicationException("CAS.018", new Object[] {r.getCreatedby()});
		}
	}

}
