package com.ooredoo.nc.service;

import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import com.ooredoo.nc.dto.NodalCleanupDetails;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.utility.ApplicationConstants;
import com.ooredoo.nc.utility.ApplicationUtility;
import com.ooredoo.wsclient.WSClient;
import com.ooredoo.wsclient.pcrf.DelSubscriberRequest;
import com.ooredoo.wsclient.pcrf.DelsubscriberResponse;
import com.ooredoo.wsclient.pcrf.SAVP;

@Service
public class PCRFCleanupService implements ApplicationConstants {

	@Autowired
	private WSClient wsclient;
	
	@Autowired
	private ReloadableResourceBundleMessageSource externalConfig;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PCRFCleanupService.class);
	
	public NodalCleanupDetails deletePcrfProfile(String msisdn) {
		NodalCleanupDetails cleanupDetails = new NodalCleanupDetails();
		
		try {
			cleanupDetails.setNode(PCRF);
			cleanupDetails.setExecStart(new Date());
			
			com.ooredoo.wsclient.pcrf.DelSubscriberRequest delSubscriberRequest = new com.ooredoo.wsclient.pcrf.DelSubscriberRequest();
			delSubscriberRequest.setOrderId(ApplicationUtility.generateRandomTransactionId());
			delSubscriberRequest.setSourceSystem("NumberCleanup");
			delSubscriberRequest.setIsLogRequired("N");
			
			com.ooredoo.wsclient.pcrf.SAVP subscriberDetails = new com.ooredoo.wsclient.pcrf.SAVP();
			subscriberDetails.setKey("usrIdentifier");
			subscriberDetails.setValue(msisdn);
			delSubscriberRequest.getAttributes().add(subscriberDetails);
			DelsubscriberResponse response = wsclient.deletePcrfProfile(
					externalConfig.getMessage("service.cleanup.pcrf.url", null, Locale.getDefault()), 
					delSubscriberRequest, 
					externalConfig.getMessage("service.cleanup.pcrf.context", null, Locale.getDefault()));
			
			if(null != response) {
				String responseCode = response.getResponseCode();
				String responseMessage = response.getResponseMessage();
				
				if(null != responseCode && responseCode.equals("0") && null != responseMessage && responseMessage.equals("Operation succeeded")) {
					cleanupDetails.setStatus(SUCCESS_LEBEL);
					cleanupDetails.setResponse("OK");
				} else {
					cleanupDetails.setStatus(ERROR_LABEL);
					cleanupDetails.setResponse(responseMessage);
				}
			}
			
			
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
