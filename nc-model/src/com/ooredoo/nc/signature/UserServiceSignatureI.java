package com.ooredoo.nc.signature;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ooredoo.nc.dto.UserPrinciple;
import com.ooredoo.nc.dto.UserSearchCriteria;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.model.AppUser;
import com.ooredoo.nc.utility.ApplicationConstants;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

public interface UserServiceSignatureI<T> extends ServiceSignatureI<T> {
	
	/**
	 * 
	 * @param userPrinciple
	 * @param req
	 * @return {@link T}
	 * @throws ApplicationException
	 */
	@PostMapping(value={"/authenticateUser"}, consumes=MediaType.APPLICATION_JSON_VALUE	, produces= MediaType.APPLICATION_JSON_VALUE, name="Authenticate User")
	@ApiImplicitParams({
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_ACTION_USER, value = "Username of the User performing the operation", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_AUDIT_EVENT, value = "Event Action that is being performed", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_CHANNEL, value = "Name of the channel request is being sent", paramType = "header", required=true)
	})
	public @ResponseBody T authenticateUser(@RequestBody(required=true) UserPrinciple userPrinciple, HttpServletRequest req) throws ApplicationException;
	
	/**
	 * 
	 * @param userPrinciple
	 * @param req
	 * @return {@link T}
	 * @throws ApplicationException
	 */
	@PutMapping(value={"/updateCredential"}, consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_ACTION_USER, value = "Username of the User performing the operation", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_AUDIT_EVENT, value = "Event Action that is being performed", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_CHANNEL, value = "Name of the channel request is being sent", paramType = "header", required=true)
	})
	public @ResponseBody T updateCredential(@RequestBody UserPrinciple userPrinciple,  HttpServletRequest req) throws ApplicationException;
	
	/**
	 * 
	 * @param userPrinciple
	 * @param req
	 * @return {@link T}
	 * @throws ApplicationException
	 */
	@PutMapping(value={"/updateAuthority"}, consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_ACTION_USER, value = "Username of the User performing the operation", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_AUDIT_EVENT, value = "Event Action that is being performed", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_CHANNEL, value = "Name of the channel request is being sent", paramType = "header", required=true)
	})
	public @ResponseBody T manageRole(@RequestBody UserPrinciple userPrinciple, HttpServletRequest req) throws ApplicationException;
	
	/**
	 * 
	 * @param user
	 * @param req
	 * @return {@link T}
	 * @throws ApplicationException
	 */
	@PutMapping(value={"/update"}, consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_ACTION_USER, value = "Username of the User performing the operation", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_AUDIT_EVENT, value = "Event Action that is being performed", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_CHANNEL, value = "Name of the channel request is being sent", paramType = "header", required=true)
	})
	public @ResponseBody T updateUser(@RequestBody(required=true) AppUser user, HttpServletRequest req) throws ApplicationException;
	
	/**
	 * 
	 * @param userSearchCriteria
	 * @param req
	 * @return
	 * @throws ApplicationException
	 */
	@PostMapping(value={"/searchUser"}, consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_ACTION_USER, value = "Username of the User performing the operation", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_AUDIT_EVENT, value = "Event Action that is being performed", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_CHANNEL, value = "Name of the channel request is being sent", paramType = "header", required=true)
	})
	public @ResponseBody List<AppUser> searchUser(@RequestBody UserSearchCriteria userSearchCriteria, HttpServletRequest req) throws ApplicationException;
}
