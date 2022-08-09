package com.ooredoo.nc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import com.ooredoo.nc.dto.CleanupSystemConfig;
import com.ooredoo.nc.dto.DatabaseConfigDetails;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.signature.DatabaseConfig;
import com.ooredoo.nc.utility.ApplicationConstants;

@Service
public class ConfigService implements DatabaseConfig, ApplicationConstants  {
	
	@Autowired
	private BSCSCleanupService bscsCleanupService;
	
	@Autowired
	private RASCleanupService rasCleanupService;
	
	@Autowired
	private NMSCleanupService nmsCleanupService;
	
	@Autowired
	private ERPCleanupService erpCleanupService;
	
	@Value("${spring.datasource.url}")
	private String dbUrl;
	
	@Value("${spring.datasource.username}")
	private String user;
	
	@Value("${spring.datasource.password}")
	private String password;
	
	@Autowired
	private ReloadableResourceBundleMessageSource externalConfig;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigService.class);

	@Override
	public DatabaseConfigDetails getDbConfig() {
		LOGGER.info("Extracting database config for node : {}", "Cleanup Schema");
		DatabaseConfigDetails dbConfig = new DatabaseConfigDetails();
		
		String[] splittedUrl = dbUrl.split(SPLITTER);
		String[] dbDetails = splittedUrl[1].split(":");
		
		dbConfig.setDbHost(dbDetails[0]);
		dbConfig.setDbPort(dbDetails[1]);
		dbConfig.setDbServicename(dbDetails[2]);
		dbConfig.setUser(this.user);
		dbConfig.setPassword(this.password);
		return dbConfig;
	}
	
	public CleanupSystemConfig getConfiguration() {
		CleanupSystemConfig systemConfig = new CleanupSystemConfig();
		
		//try {
			Map<String, DatabaseConfigDetails> dbConfigs = new HashMap<String, DatabaseConfigDetails>();
			DatabaseConfigDetails bscsDB = null;
			DatabaseConfigDetails nmsDB = null;
			DatabaseConfigDetails rasDB = null;
			DatabaseConfigDetails erpDB = null;
			
			try {
				bscsDB = bscsCleanupService.getDBConfig();
				dbConfigs.put("BSCS", bscsDB);
				
			} catch(ApplicationException ae) {
				LOGGER.error("Error", ae);
			} catch(Exception ae) {
				LOGGER.error("Error", ae);
			}
			
			try {
				nmsDB = nmsCleanupService.getDBConfig();
				dbConfigs.put("NMS", nmsDB);
				
			} catch(ApplicationException ae) {
				LOGGER.error("Error", ae);
			} catch(Exception ae) {
				LOGGER.error("Error", ae);
			}
			
			try {
				rasDB = rasCleanupService.getDBConfig();
				dbConfigs.put("RAS", rasDB);
				
			} catch(ApplicationException ae) {
				LOGGER.error("Error", ae);
			} catch(Exception ae) {
				LOGGER.error("Error", ae);
			}
			
			try {
				erpDB = erpCleanupService.getDBConfig();
				dbConfigs.put("ERP", erpDB);
				
			} catch(ApplicationException ae) {
				LOGGER.error("Error", ae);
				
			} catch(Exception ae) {
				LOGGER.error("Error", ae);
			}
			
			systemConfig.setDbConfigs(dbConfigs);
			
			 List<String> esmServiceUrl = new ArrayList<String>();
			 esmServiceUrl.add(externalConfig.getMessage("service.cleanup.esm.url", null, Locale.ENGLISH));
			 systemConfig.setEsmServiceUrl(esmServiceUrl);
			 
			 List<String> pcrfServiceUrl = new ArrayList<String>();
			 pcrfServiceUrl.add(externalConfig.getMessage("service.cleanup.pcrf.url", null, Locale.ENGLISH));
			 systemConfig.setPcrfServiceUrl(pcrfServiceUrl);
			 
			 
			 List<String> hlrServiceUrl = new ArrayList<String>();
			 hlrServiceUrl.add(externalConfig.getMessage("service.cleanup.hlr.hss.url", null, Locale.ENGLISH));
			 hlrServiceUrl.add(externalConfig.getMessage("service.cleanup.hlr.rmvdnaptrrec.url", null, Locale.ENGLISH));
			 systemConfig.setHlrServiceUrl(hlrServiceUrl);
			 
			 List<String> csServiceUrl = new ArrayList<String>();
			 csServiceUrl.add(externalConfig.getMessage("service.cleanup.cs.url", null, Locale.ENGLISH));
			 systemConfig.setCsServiceUrl(csServiceUrl);
			 
			 List<String> sprServiceUrl = new ArrayList<String>();
			 sprServiceUrl.add(externalConfig.getMessage("service.cleanup.spr.url", null, Locale.ENGLISH));
			 systemConfig.setSprServiceUrl(sprServiceUrl);
			
		/*} catch(ApplicationException ae) {
			LOGGER.error("Error occurred while extracting system config. Cause {}", ae);
		}*/
		
		return systemConfig;
	}
}
