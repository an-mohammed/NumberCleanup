package com.ooredoo.nc.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ooredoo.nc.dto.BSCSProfileDetails;
import com.ooredoo.nc.dto.DatabaseConfigDetails;
import com.ooredoo.nc.dto.NodalCleanupDetails;
import com.ooredoo.nc.dto.SubscriberProfileDetails;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.service.BSCSCleanupService;
import com.ooredoo.nc.service.ConfigService;

@RestController
@RequestMapping(value = {"${service.bscsmanagement.base-url}"})
public class BscsCleanupController extends Controller{

	@Autowired
	private BSCSCleanupService service;
	
	@Autowired
	private ConfigService configService;
	private static final Logger LOGGER = LoggerFactory.getLogger(BscsCleanupController.class);

	/**
	 * 
	 * @param profile
	 * @param req
	 * @return
	 * @throws ApplicationException
	 */
	@Secured(value = { "ROLE_CAS" })
	@PostMapping(value="/cleanup", consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public NodalCleanupDetails cleanup(@RequestBody(required=true) SubscriberProfileDetails profile,  HttpServletRequest req) throws ApplicationException {
		LOGGER.info("Cleaning up BSCS Node");
		return service.deleteSubscriberFromBscs(profile);
	}
	
	/**
	 * 
	 * @param msisdn
	 * @param req
	 * @return
	 * @throws ApplicationException
	 */
	@Secured(value = { "ROLE_CAS" })
	@GetMapping(value="/getProfile", produces = MediaType.APPLICATION_JSON_VALUE)
	public BSCSProfileDetails getProfile(@RequestParam(required=true, name="msisdn") String msisdn,  HttpServletRequest req) throws ApplicationException {
		LOGGER.info("Extracting BSCS Profile for MSISDN : {}", msisdn);
		return service.extractBscsProfile(msisdn);
	}
	
	@Secured(value = { "ROLE_CAS" })
	@GetMapping(value="/getDBConfig", produces = MediaType.APPLICATION_JSON_VALUE)
	public DatabaseConfigDetails getDBConfig(HttpServletRequest req) throws ApplicationException {
		LOGGER.info("Extracting BSCS DB COnfig for BSCS");
		return configService.getDbConfig();
	}
	
	@Secured(value = { "ROLE_CAS" })
	@GetMapping(value="/validationprofile", produces = MediaType.APPLICATION_JSON_VALUE)
	public BSCSProfileDetails getValidationProfile(@RequestParam(required=true, name="msisdn") String msisdn,  HttpServletRequest req) throws ApplicationException {
		LOGGER.info("Extracting BSCS Profile for MSISDN : {}", msisdn);
		return service.extractBscsProfileForValidation(msisdn);
	}
}
