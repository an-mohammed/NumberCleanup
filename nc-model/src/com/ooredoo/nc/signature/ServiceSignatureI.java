package com.ooredoo.nc.signature;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.utility.ApplicationConstants;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

public interface ServiceSignatureI<T> extends ApplicationConstants {

	/**
	 * 
	 * @return {@link List<T}
	 * @throws ApplicationException
	 */
	@GetMapping(value="/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_ACTION_USER, value = "Username of the User performing the operation", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_AUDIT_EVENT, value = "Event Action that is being performed", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_CHANNEL, value = "Name of the channel request is being sent", paramType = "header", required=true)
	})
	public List<T> findAll() throws ApplicationException;
	
	/**
	 * 
	 * @return {@link List<T>}
	 * @throws ApplicationException
	 */
	@GetMapping(value="/findAllValid", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_ACTION_USER, value = "Username of the User performing the operation", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_AUDIT_EVENT, value = "Event Action that is being performed", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_CHANNEL, value = "Name of the channel request is being sent", paramType = "header", required=true)
	})
	public List<T> findAllValid() throws ApplicationException;
	
	/**
	 * 
	 * @param publicId
	 * @param req
	 * @return {@link T}
	 * @throws ApplicationException
	 */
	@GetMapping(value="/findByPublicId", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_ACTION_USER, value = "Username of the User performing the operation", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_AUDIT_EVENT, value = "Event Action that is being performed", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_CHANNEL, value = "Name of the channel request is being sent", paramType = "header", required=true)
	})
	public T findByPublicId(@RequestParam(name="publicId", required=true) String publicId,  HttpServletRequest req) throws ApplicationException;
	
	/**
	 * 
	 * @param privateId
	 * @param req
	 * @return {@link T}
	 * @throws ApplicationException
	 */
	@GetMapping(value="/findByPrivateId", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_ACTION_USER, value = "Username of the User performing the operation", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_AUDIT_EVENT, value = "Event Action that is being performed", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_CHANNEL, value = "Name of the channel request is being sent", paramType = "header", required=true)
	})
	public T findByPrivateId(@RequestParam(name="privateId", required=true) Long privateId,   HttpServletRequest req) throws ApplicationException;
	
	/**
	 * 
	 * @param publicId
	 * @param req
	 * @return {@link Boolean}
	 * @throws ApplicationException
	 */
	@GetMapping(value="/existsPublicId", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_ACTION_USER, value = "Username of the User performing the operation", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_AUDIT_EVENT, value = "Event Action that is being performed", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_CHANNEL, value = "Name of the channel request is being sent", paramType = "header", required=true)
	})
	public @ResponseBody boolean exists(@RequestParam(name="publicId", required=true) String publicId,  HttpServletRequest req) throws ApplicationException;
	
	/**
	 * 
	 * @param privateId
	 * @param req
	 * @return {@link Boolean}
	 * @throws ApplicationException
	 */
	@GetMapping(value="/existsPrivateId", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_ACTION_USER, value = "Username of the User performing the operation", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_AUDIT_EVENT, value = "Event Action that is being performed", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_CHANNEL, value = "Name of the channel request is being sent", paramType = "header", required=true)
	})
	public boolean exists(@RequestParam(name="privateId", required=true) Long privateId,  HttpServletRequest req) throws ApplicationException;
	
	/**
	 * 
	 * @param t
	 * @param req
	 * @return {@link T}
	 * @throws ApplicationException
	 */
	@PostMapping(value="/persist", consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_ACTION_USER, value = "Username of the User performing the operation", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_AUDIT_EVENT, value = "Event Action that is being performed", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_CHANNEL, value = "Name of the channel request is being sent", paramType = "header", required=true)
	})
	public T create(@RequestBody T t,  HttpServletRequest req) throws ApplicationException;
	
	/**
	 * 
	 * @param privateId
	 * @param deletedBy
	 * @param req
	 * @return {@link T}
	 * @throws ApplicationException
	 */
	@DeleteMapping(value="/deleteByPrivateId", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_ACTION_USER, value = "Username of the User performing the operation", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_AUDIT_EVENT, value = "Event Action that is being performed", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_CHANNEL, value = "Name of the channel request is being sent", paramType = "header", required=true)
	})
	public T deleteByPrivateId(@RequestParam(name="privateId", required=true) Long privateId, @RequestParam(name="deletedBy", required=true) String deletedBy,  HttpServletRequest req) throws ApplicationException;
	
	/**
	 * 
	 * @param publicId
	 * @param deletedBy
	 * @param req
	 * @return {@link T}
	 * @throws ApplicationException
	 */
	@DeleteMapping(value="/deleteByPublicId", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_ACTION_USER, value = "Username of the User performing the operation", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_AUDIT_EVENT, value = "Event Action that is being performed", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_CHANNEL, value = "Name of the channel request is being sent", paramType = "header", required=true)
	})
	public T deleteByPublicId(@RequestParam(name="publicId", required=true) String publicId, @RequestParam(name="deletedBy", required=true) String deletedBy,  HttpServletRequest req) throws ApplicationException;
	
	/**
	 * 
	 * @param privateId
	 * @param deletedBy
	 * @param req
	 * @return {@link T}
	 * @throws ApplicationException
	 */
	@DeleteMapping(value="/invalidateByPrivateId", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_ACTION_USER, value = "Username of the User performing the operation", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_AUDIT_EVENT, value = "Event Action that is being performed", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_CHANNEL, value = "Name of the channel request is being sent", paramType = "header", required=true)
	})
	public T invalidateByPrivateId(@RequestParam(name="privateId", required=true) Long privateId, @RequestParam(name="deletedBy", required=true) String deletedBy,  HttpServletRequest req) throws ApplicationException;
	
	/**
	 * 
	 * @param publicId
	 * @param deletedBy
	 * @param req
	 * @return {@link T}
	 * @throws ApplicationException
	 */
	@DeleteMapping(value="/invalidateByByPublicId", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_ACTION_USER, value = "Username of the User performing the operation", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_AUDIT_EVENT, value = "Event Action that is being performed", paramType = "header", required=true),
        @ApiImplicitParam(name = ApplicationConstants.HEADER_KEY_CHANNEL, value = "Name of the channel request is being sent", paramType = "header", required=true)
	})
	public T invalidateByByPublicId(@RequestParam(name="publicId", required=true) String publicId, @RequestParam(name="deletedBy", required=true) String deletedBy,   HttpServletRequest req) throws ApplicationException;
}
