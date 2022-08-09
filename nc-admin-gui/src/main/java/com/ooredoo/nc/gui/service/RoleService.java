package com.ooredoo.nc.gui.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.exception.ApplicationRestException;
import com.ooredoo.nc.model.Privilege;
import com.ooredoo.nc.model.Role;
import com.ooredoo.nc.utility.ApplicationConstants;


@Component("roleSerivce")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RoleService extends ServiceConsumer implements Serializable, ApplicationConstants {
	
	private static final long serialVersionUID = 1L;
	
	public Role getRoleDetails(String roleName, String actionEvent, String actionUser) throws ApplicationRestException  {
		Role role = null;
		HttpEntity<Role> responseRole = null;
		
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
		requestParams.add("publicId", roleName);
		
		Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, responseRole, null, HttpMethod.GET,  new ParameterizedTypeReference<Role>() {
		}, getFindRoleByNameUrl(), requestParams);
		
		if(null != o) {
			role = (Role) o;
		}
		
		return role;
	}
	
	@SuppressWarnings("unchecked")
	public List<Role> getAllRoles(String actionEvent, String actionUser) throws ApplicationRestException  {
		List<Role> allRole = null;
		HttpEntity<List<Role>> responseRole = null;
		
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
		requestParams.add("findAll", "true");
		
		
		Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, responseRole, null, HttpMethod.GET,  new ParameterizedTypeReference<List<Role>>() {
		}, getFindAllRolesUrl(), requestParams);
		
		if(null != o) {
			allRole = (List<Role>) o;
		}
		
		return allRole;
	}
	
	@SuppressWarnings("unchecked")
	public List<Privilege> getAllPrivlege(String actionEvent, String actionUser) throws ApplicationRestException  {
		List<Privilege> allPriv = null;
		HttpEntity<List<Privilege>> responseRole = null;
		
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
		requestParams.add("findAll", "true");
		
		
		Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, responseRole, null, HttpMethod.GET,  new ParameterizedTypeReference<List<Privilege>>() {
		}, getFindAllPrivUrl(), requestParams);
		
		if(null != o) {
			allPriv = (List<Privilege>) o;
		}
		
		return allPriv;
	}
	
	public Privilege getPrivilege(String privilegeName, String actionEvent, String actionUser) throws ApplicationException{
		Privilege privilege = null;
		HttpEntity<Privilege> responsePrivilege = null;
		
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
		requestParams.add("publicId", privilegeName);
		
		Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, responsePrivilege, null, HttpMethod.GET,  new ParameterizedTypeReference<Privilege>() {
		}, getFindPrivByName(), requestParams);
		
		if(null != o) {
			privilege = (Privilege) o;
		}
		
		return privilege;
	}
	
	public Privilege createPrivilege(Privilege reqPrivilege, String actionEvent, String actionUser) throws ApplicationException{
		Privilege privilege = null;
		HttpEntity<Privilege> responsePrivilegeDetails = null;
		
		try {
			Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, responsePrivilegeDetails, reqPrivilege, HttpMethod.POST,  new ParameterizedTypeReference<Role>() {
    		}, getCreatePrivUrl(), null);
			
			if(null != o && o instanceof Privilege) {
				privilege = (Privilege) o;
			}
		} catch(ApplicationRestException e) {
			throw e;
			
		}
		
		return privilege;
	}
	
	public Role updateRole(Role updatedRole) throws ApplicationRestException {
		Role responseRole = null;
		HttpEntity<Role> responseUserDetails = null;
		
		try {
			Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, responseUserDetails, updatedRole, HttpMethod.PUT,  new ParameterizedTypeReference<Role>() {
    		}, getUpdateRoleUrl(), null);
			
			if(null != o && o instanceof Role) {
				responseRole = (Role) o;
			}
		} catch(ApplicationRestException e) {
			throw e;
			
		}
		
		return responseRole;
	}
	
	public Role createNewRole(Role updatedRole) throws ApplicationRestException {
		Role responseRole = null;
		HttpEntity<Role> responseUserDetails = null;
		
		try {
			Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, responseUserDetails, updatedRole, HttpMethod.POST,  new ParameterizedTypeReference<Role>() {
    		}, getCreateRoleUrl(), null);
			
			if(null != o && o instanceof Role) {
				responseRole = (Role) o;
			}
		} catch(ApplicationRestException e) {
			throw e;
			
		}
		
		return responseRole;
	}
}
