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
import org.springframework.web.bind.annotation.RestController;

import com.ooredoo.nc.dto.DatabaseConfigDetails;
import com.ooredoo.nc.dto.NodalCleanupDetails;
import com.ooredoo.nc.dto.SubscriberProfileDetails;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.service.ConfigService;
import com.ooredoo.nc.service.NmsCleanupService;

@RestController
@RequestMapping(value = {"${service.nmsmanagement.base-url}"})
public class NmsCleanupController extends Controller{

	@Autowired
	private NmsCleanupService service;
	
	@Autowired
	private ConfigService configService;
	private static final Logger LOGGER = LoggerFactory.getLogger(NmsCleanupController.class);
	
	@Secured(value = { "ROLE_CAS" })
	@PostMapping(value="/cleanup", consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public NodalCleanupDetails cleanup(@RequestBody(required=true) SubscriberProfileDetails profile,  HttpServletRequest req) throws ApplicationException {
		LOGGER.info("Cleaning up NMS node");
		return service.deleteSubscriberFromNms(profile);
	}
	
	@Secured(value = { "ROLE_CAS" })
	@GetMapping(value="/getDBConfig", produces = MediaType.APPLICATION_JSON_VALUE)
	public DatabaseConfigDetails getDBConfig(HttpServletRequest req) throws ApplicationException {
		LOGGER.info("Extracting BSCS DB COnfig for NMS");
		return configService.getDbConfig();
	}
}
