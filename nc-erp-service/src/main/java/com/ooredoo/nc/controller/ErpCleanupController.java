package com.ooredoo.nc.controller;

import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ooredoo.nc.dto.DatabaseConfigDetails;
import com.ooredoo.nc.dto.NodalCleanupDetails;
import com.ooredoo.nc.dto.SubscriberProfileDetails;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.service.ConfigService;
import com.ooredoo.nc.service.ERPCleanupService;

@RestController
@RequestMapping(value = {"${service.erpmanagement.base-url}"})
public class ErpCleanupController extends Controller{

	@Autowired
	private ERPCleanupService service;
	
	@Autowired
	private ConfigService configService;
	private static final Logger LOGGER = LoggerFactory.getLogger(ErpCleanupController.class);
	
	@Secured(value = { "ROLE_CAS" })
	@GetMapping(value="/findAllLocations", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String, String> getAllErpLocations() throws ApplicationException{
		
		Map<String, String> locations = service.getAllLocations();
		/*Map<String, String> locations = new HashMap<String, String>();
		LOGGER.info("Locations : {}", locations);
		
		if(locations.isEmpty()) {
			locations.put("POS - Airport", "POS - Airport");
			locations.put("Corporate", "Corporate");
		}*/
		return locations;
	}
	
	@Secured(value = { "ROLE_CAS" })
	@PostMapping(value="/cleanup", consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public NodalCleanupDetails cleanup(@RequestBody(required=true) SubscriberProfileDetails profile,  HttpServletRequest req) throws ApplicationException {
		return service.deleteSubscriberFromErp(profile);
	}
	
	@Secured(value = { "ROLE_CAS" })
	@GetMapping(value="/getDBConfig", produces = MediaType.APPLICATION_JSON_VALUE)
	public DatabaseConfigDetails getDBConfig(HttpServletRequest req) throws ApplicationException {
		LOGGER.info("Extracting BSCS DB Config for ERP");
		return configService.getDbConfig();
	}
}
