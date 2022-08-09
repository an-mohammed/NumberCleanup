package com.ooredoo.nc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.ooredoo.nc.utility.ApplicationConstants;


@Component
public class Controller implements ApplicationConstants {
	
	@Autowired
	Environment environment;
	
	@Autowired
	private ReloadableResourceBundleMessageSource externalConfig;
	
	public ReloadableResourceBundleMessageSource getExternalConfig() {
		return externalConfig;
	}

	public void setExternalConfig(ReloadableResourceBundleMessageSource externalConfig) {
		this.externalConfig = externalConfig;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

}
