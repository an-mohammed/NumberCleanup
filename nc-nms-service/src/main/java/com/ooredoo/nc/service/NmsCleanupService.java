package com.ooredoo.nc.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import com.ooredoo.nc.dao.NMSCleanupDao;
import com.ooredoo.nc.dto.NodalCleanupDetails;
import com.ooredoo.nc.dto.SubscriberProfileDetails;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.utility.ApplicationConstants;

@Service
public class NmsCleanupService implements ApplicationConstants {

	@Autowired
	private NMSCleanupDao nmsCleanupDao;
		
	private static final Logger LOGGER = LoggerFactory.getLogger(NmsCleanupService.class);
	
	public NodalCleanupDetails deleteSubscriberFromNms(SubscriberProfileDetails profile) {
		NodalCleanupDetails cleanupDetails = new NodalCleanupDetails();
		
		try {
			cleanupDetails.setNode(NMS);
			cleanupDetails.setExecStart(new Date());
			
			nmsCleanupDao.executeCleanupQueries(profile);
			cleanupDetails.setStatus(SUCCESS_LEBEL);
			cleanupDetails.setResponse("OK");
			
		} catch(NoSuchMessageException e) {
			cleanupDetails.setStatus(ERROR_LABEL);
			cleanupDetails.setResponse("Fatal Exception.");
			
		}catch(ApplicationException ae) {
			cleanupDetails.setStatus(ERROR_LABEL);
			cleanupDetails.setResponse(ae.getMessage());
			
			LOGGER.error("Error occurred while invoking service. Cause :", ae);
			
		}
		
		cleanupDetails.setExecEnd(new Date());
		return cleanupDetails;
	}
}
