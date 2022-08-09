package com.ooredoo.nc.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.ooredoo.nc.model.Role;
import com.ooredoo.nc.repo.CompositeRepository;
import com.ooredoo.nc.utility.ApplicationConstants;


@Component
public class Controller implements ApplicationConstants {
	
	@Autowired
	Environment environment;
	
	@Autowired
	private CompositeRepository repository;
	
	@Autowired
	private ReloadableResourceBundleMessageSource externalConfig;
	private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
	
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

	public CompositeRepository getRepository() {
		return repository;
	}

	public void setRepository(CompositeRepository repository) {
		this.repository = repository;
	}
	
	public List<Role> getServiceRoles() {
	
		LOGGER.debug("extracting the service roles");
		List<Role> serviceRoles = new ArrayList<Role>();
		serviceRoles = getRepository().getRoleRepo().findByNameIn(getServiceRoleNames());
		
		return serviceRoles;
	}

	public List<String> getServiceRoleNames() {
		List<String> serviceRoleName = new ArrayList<String>();
		serviceRoleName.add("ROLE_CAS");
		
		return serviceRoleName;
	}
}
