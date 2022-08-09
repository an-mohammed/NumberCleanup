package com.ooredoo.nc.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ooredoo.nc.dto.CleanupSystemConfig;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.service.ConfigService;
import com.ooredoo.nc.utility.ApplicationConstants;

@RestController
@RequestMapping(value = {"${service.config.base-url}"})
public class ConfigController extends Controller implements ApplicationConstants {
	
	@Autowired
	private ConfigService configService;
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigController.class);
	
	@GetMapping(value={"/getSystemConfig"}, produces= MediaType.APPLICATION_JSON_VALUE, name="Add new Number To Number Pool")
	public @ResponseBody CleanupSystemConfig getSystemConfig(HttpServletRequest servlet) throws ApplicationException {
		LOGGER.info("Invoked getAll service");
		CleanupSystemConfig resp = configService.getConfiguration();
		return resp;
	}
}
