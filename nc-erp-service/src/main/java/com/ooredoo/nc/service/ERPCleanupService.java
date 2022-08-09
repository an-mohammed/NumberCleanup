package com.ooredoo.nc.service;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import com.ooredoo.nc.dao.ERPCleanupDao;
import com.ooredoo.nc.dto.NodalCleanupDetails;
import com.ooredoo.nc.dto.SubscriberProfileDetails;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.utility.ApplicationConstants;

@Service
public class ERPCleanupService implements ApplicationConstants {

	@Autowired
	private ERPCleanupDao erpCleanupDao;
		
	private static final Logger LOGGER = LoggerFactory.getLogger(ERPCleanupService.class);
	
	public Map<String, String> getAllLocations() throws ApplicationException{
		Map<String, String> locations = erpCleanupDao.getErpLocations();
		
		return locations;
	}
	public NodalCleanupDetails deleteSubscriberFromErp(SubscriberProfileDetails profile) {
		NodalCleanupDetails cleanupDetails = new NodalCleanupDetails();
		
		try {
			cleanupDetails.setNode(ERP);
			cleanupDetails.setExecStart(new Date());
			LOGGER.info("deleteSubscriberFromErp location : {}", profile.getSelectedErpLocation());
			
			erpCleanupDao.executeCleanupQueries(profile.getSimNo(), profile.getSelectedErpLocation());
			
			cleanupDetails.setStatus(SUCCESS_LEBEL);
			cleanupDetails.setResponse("OK");
			
		} catch(NoSuchMessageException e) {
			cleanupDetails.setStatus(ERROR_LABEL);
			cleanupDetails.setResponse("Fatal Exception.");
			
		}catch(ApplicationException ae) {
			cleanupDetails.setStatus(ERROR_LABEL);
			cleanupDetails.setResponse(ae.getErrorMessage());
			
			LOGGER.error("Error occurred while invoking service. Cause :", ae);
			
		}
		
		cleanupDetails.setExecEnd(new Date());
		return cleanupDetails;
	}
}
