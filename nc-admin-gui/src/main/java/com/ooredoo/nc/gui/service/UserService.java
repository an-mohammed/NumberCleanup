package com.ooredoo.nc.gui.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.ooredoo.nc.dto.UserPrinciple;
import com.ooredoo.nc.dto.UserSearchCriteria;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.exception.ApplicationRestException;
import com.ooredoo.nc.gui.configuration.SessionHandler;
import com.ooredoo.nc.model.AppUser;
import com.ooredoo.nc.model.Role;
import com.ooredoo.nc.utility.ApplicationConstants;


@Component("userSerivce")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserService extends ServiceConsumer implements Serializable, ApplicationConstants {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return {@link UsernamePasswordAuthenticationToken}
	 * @throws AuthenticationException
	 */
	public UsernamePasswordAuthenticationToken authenticate(String username, String password) throws AuthenticationException {
		UserPrinciple principal = null;
		GrantedAuthority grantedAuthority = null;
		AppUser responseuser = null;
		HttpEntity<AppUser> responseUserDetails = null;
		
        final List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
        
        try {
        	principal = new UserPrinciple();
        	principal.setUsername(username);
        	principal.setCurrentPassword(password);
        	
        	Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, responseUserDetails, principal, HttpMethod.POST,  new ParameterizedTypeReference<AppUser>() {
    		}, getAuthenticateUserUrl(), null);
        	
        	if(null != o) {
        		responseuser = (AppUser) o;
        		SessionHandler.putUserCredentialToSession(responseuser);
        		SessionHandler.putRoleAuthToSession(responseuser);
            	
        		for(Role role : responseuser.getRoles()) {
        			grantedAuthority = new SimpleGrantedAuthority(role.getName());
                    grantedAuths.add(grantedAuthority);
        		}
        				 
        		if(!responseuser.getIsEnabled()) {
        			throw new ApplicationException("AUI.006");
        		}
        		
        		if(responseuser.getIsAccLocked()) {
        			throw new ApplicationException("AUI.007");
        		}
        		
                return new UsernamePasswordAuthenticationToken(responseuser, password, grantedAuths);
                
            }else {
            	throw new ApplicationException("Fatal");
            } 
        } catch(ApplicationRestException e) {
        	throw new BadCredentialsException(e.getErrorMessage());
        } catch(ApplicationException e) {
        	throw new BadCredentialsException(e.getErrorMessage());
        }
	}
	
	public AppUser resetPassword(UserPrinciple userP) throws ApplicationException {
		AppUser updatedUser = null;
		HttpEntity<AppUser> responseUserDetails = null;
		
		try {
			
			Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, responseUserDetails, userP, HttpMethod.PUT,  new ParameterizedTypeReference<AppUser>() {
    		}, getResetUserPasswordUrl(), null);
			
			if(null != o && o instanceof AppUser) {
				updatedUser = (AppUser) o;
			}
		} catch(ApplicationRestException e) {
			throw new ApplicationException(e);
			
		}
		
		return updatedUser;
	}
	
	public AppUser createUser(AppUser newUser) throws ApplicationRestException {
		AppUser responseUser = null;
		HttpEntity<AppUser> responseUserDetails = null;
		
		try {
			Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, responseUserDetails, newUser, HttpMethod.POST,  new ParameterizedTypeReference<AppUser>() {
    		}, getCreateUserUrl(), null);
			
			if(null != o && o instanceof AppUser) {
				responseUser = (AppUser) o;
			}
		} catch(ApplicationRestException e) {
			throw e;
			
		}
		
		return responseUser;
	}
	
	@SuppressWarnings("unchecked")
	public List<AppUser> searchUser(String username, String firstName, String mobile, String email, boolean includeDisabled) throws ApplicationException {
		List<AppUser> responseList = null;
		UserSearchCriteria usc = new UserSearchCriteria();
		
		usc.setEmail(email);
		usc.setMobile(mobile);
		usc.setName(firstName);
		usc.setUsername(username);
		usc.setIncludeInvalid(includeDisabled);
		
		HttpEntity<List<AppUser>> responseUserDetails = null;
		
		try {
			Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, responseUserDetails, usc, HttpMethod.POST,  new ParameterizedTypeReference<List<AppUser>>() {
    		}, getSearchUserUrl(), null);
			
			if(null != o && o instanceof List<?>) {
				responseList = (List<AppUser>) o;
			}
		} catch(ApplicationRestException e) {
			throw new ApplicationException(e);
			
		}
		
		return responseList;
	}
	
	public AppUser updateUser(AppUser u) throws ApplicationException {
		AppUser updatedUser = null;
		HttpEntity<List<AppUser>> responseUserDetails = null;
		
		try {
			
			Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, responseUserDetails, u, HttpMethod.PUT,  new ParameterizedTypeReference<AppUser>() {
    		}, getUpdateUserUrl(), null);
			
			if(null != o && o instanceof AppUser) {
				updatedUser = (AppUser) o;
			}
		} catch(ApplicationRestException e) {
			throw new ApplicationException(e);
			
		}
		
		return updatedUser;
	}
	
	public AppUser createServiceUser(AppUser newUser) throws ApplicationRestException {
		AppUser responseUser = null;
		HttpEntity<AppUser> responseUserDetails = null;
		
		try {
			Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, responseUserDetails, newUser, HttpMethod.POST,  new ParameterizedTypeReference<AppUser>() {
    		}, getCreateServiceUserUrl(), null);
			
			if(null != o && o instanceof AppUser) {
				responseUser = (AppUser) o;
			}
		} catch(ApplicationRestException e) {
			throw e;
			
		}
		
		return responseUser;
	}
	
	public AppUser updateServiceUser(AppUser u) throws ApplicationException {
		AppUser updatedUser = null;
		HttpEntity<List<AppUser>> responseUserDetails = null;
		
		try {
			
			Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, responseUserDetails, u, HttpMethod.PUT,  new ParameterizedTypeReference<AppUser>() {
    		}, getUpdateServiceUserUrl(), null);
			
			if(null != o && o instanceof AppUser) {
				updatedUser = (AppUser) o;
			}
		} catch(ApplicationRestException e) {
			throw new ApplicationException(e);
			
		}
		
		return updatedUser;
	}
	
	@SuppressWarnings("unchecked")
	public List<AppUser> searchServiceUser(String username, String firstName, String mobile, String email, boolean includeDisabled) throws ApplicationException {
		List<AppUser> responseList = null;
		UserSearchCriteria usc = new UserSearchCriteria();
		
		usc.setEmail(email);
		usc.setMobile(mobile);
		usc.setName(firstName);
		usc.setUsername(username);
		usc.setIncludeInvalid(includeDisabled);
		
		HttpEntity<List<AppUser>> responseUserDetails = null;
		
		try {
			Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, responseUserDetails, usc, HttpMethod.POST,  new ParameterizedTypeReference<List<AppUser>>() {
    		}, getSearchServiceUserUrl(), null);
			
			if(null != o && o instanceof List<?>) {
				responseList = (List<AppUser>) o;
			}
		} catch(ApplicationRestException e) {
			throw new ApplicationException(e);
			
		}
		
		return responseList;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getAllServiceRoles() throws ApplicationRestException  {
		Map<String, String> allRole = null;
		HttpEntity<Map<String, String>> responseRole = null;
		
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
		requestParams.add("findAll", "true");
		
		
		Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, responseRole, null, HttpMethod.GET,  new ParameterizedTypeReference<Map<String, String>>() {
		}, getFindAllServiceRoleUrl(), requestParams);
		
		if(null != o) {
			allRole = (Map<String, String>) o;
		}
		
		return allRole;
	}
	
	@SuppressWarnings("unchecked")
	public List<AppUser> getAllValidUser() throws ApplicationRestException  {
		List<AppUser> allUser = null;
		HttpEntity<List<AppUser>> responseUser = null;
		
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
		requestParams.add("findAll", "true");
		
		
		Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, responseUser, null, HttpMethod.GET,  new ParameterizedTypeReference<List<AppUser>>() {
		}, getFindAllValidUserUrl(), requestParams);
		
		if(null != o) {
			allUser = (List<AppUser>) o;
		}
		
		return allUser;
	}
	
}
