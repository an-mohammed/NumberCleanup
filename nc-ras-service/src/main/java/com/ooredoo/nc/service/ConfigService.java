package com.ooredoo.nc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ooredoo.nc.dto.DatabaseConfigDetails;
import com.ooredoo.nc.signature.DatabaseConfig;
import com.ooredoo.nc.utility.ApplicationConstants;

@Service
public class ConfigService implements DatabaseConfig, ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigService.class);
	
	@Value("${spring.datasource.url}")
	private String dbUrl;
	
	@Value("${spring.datasource.username}")
	private String user;
	
	@Value("${spring.datasource.password}")
	private String password;
	
	@Override
	public DatabaseConfigDetails getDbConfig() {
		LOGGER.info("Extracting database config for node : {}", "RAS");
		DatabaseConfigDetails dbConfig = new DatabaseConfigDetails();
		
		String[] splittedUrl = dbUrl.split(SPLITTER);
		String[] dbDetails = splittedUrl[1].split(":");
		
		dbConfig.setDbHost(dbDetails[0]);
		String s2 = dbDetails[1];
		
		if(s2.contains(SLASH)) {
			String[] sp1 = s2.split(SLASH);
			dbConfig.setDbPort(sp1[0]);
			dbConfig.setDbServicename(sp1[1]);
			
		} else {
			dbConfig.setDbPort(dbDetails[1]);
			dbConfig.setDbServicename(dbDetails[2]);
		}
		dbConfig.setUser(this.user);
		dbConfig.setPassword(this.password);
		return dbConfig;
	}
	
	
}
