package com.ooredoo.nc.service;

import java.util.Date;
import java.util.Locale;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import com.ooredoo.nc.dto.NodalCleanupDetails;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.utility.ApplicationConstants;
import com.ooredoo.wsclient.WSClient;
import com.ooredoo.wsclient.spr.InputParameters;
import com.ooredoo.wsclient.spr.OutputParameters;

@Service
public class SPRCleanupService implements ApplicationConstants {

	@Autowired
	private WSClient wsclient;
	
	@Autowired
	private ReloadableResourceBundleMessageSource externalConfig;
	
	private final String DELETE = "DELETE";
	private final String DEACTIVATE = "deactive";
	private final String CLEANUP_TOOL = "CleanupTool";
	private final String NUMBER_CLEANUP = "Number Cleanup";
	private final String PREFIX = "sdm";
	private final String NAMESPACE = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA";
	private final String MSISDN = "MSISDN";
	private final String ACTIONTYPE = "ACTIONTYPE";
	private final String CONTRACT_STATUS = "CONTRACT_STATUS";
	private final String LAST_UPDT_BY = "LAST_UPDT_BY";
	private final String UPDATE_REASON = "UPDATE_REASON";
	private static final Logger LOGGER = LoggerFactory.getLogger(SPRCleanupService.class);
	
	public NodalCleanupDetails deleteSubscriberFromSpr(String msisdn) {
		NodalCleanupDetails cleanupDetails = new NodalCleanupDetails();
		
		try {
			cleanupDetails.setNode(SPR);
			cleanupDetails.setExecStart(new Date());
			
			InputParameters req = new InputParameters();
			req.setMSISDN(new JAXBElement<String>(new QName(NAMESPACE, MSISDN, PREFIX), String.class, msisdn));
			req.setACTIONTYPE(new JAXBElement<String>(new QName(NAMESPACE, ACTIONTYPE, PREFIX), String.class, DELETE));
			req.setCONTRACTSTATUS(new JAXBElement<String>(new QName(NAMESPACE, CONTRACT_STATUS, PREFIX), String.class, DEACTIVATE));
			req.setLASTUPDTBY(new JAXBElement<String>(new QName(NAMESPACE, LAST_UPDT_BY, PREFIX), String.class, CLEANUP_TOOL));
			req.setUPDATEREASON(new JAXBElement<String>(new QName(NAMESPACE, UPDATE_REASON, PREFIX), String.class, NUMBER_CLEANUP));
			
			OutputParameters response = wsclient.deleteSubscriberFromSpr(
					externalConfig.getMessage("service.cleanup.spr.url", null, Locale.getDefault()), 
					req, 
					externalConfig.getMessage("service.cleanup.spr.context", null, Locale.getDefault()));
			
			if(null != response && null != response.getRESULTCODE() && response.getRESULTCODE().getValue().equals("0")) {
				cleanupDetails.setStatus(SUCCESS_LEBEL);
				cleanupDetails.setResponse(OK_LABEL);
			} else {
				cleanupDetails.setStatus(ERROR_LABEL);
				
				if(null != response && null != response.getRESULTCODE() && !response.getRESULTCODE().getValue().isEmpty()) {
					cleanupDetails.setResponse(response.getRESULTDESC().getValue());
				} else {
					cleanupDetails.setResponse(FATAL_ERROR_LABEL);
				}
			}
		} catch(NoSuchMessageException e) {
			cleanupDetails.setStatus(ERROR_LABEL);
			cleanupDetails.setResponse(FATAL_ERROR_LABEL);
			
		}catch(ApplicationException ae) {
			cleanupDetails.setStatus(ERROR_LABEL);
			cleanupDetails.setResponse(ae.getMessage());
			
			LOGGER.error("Error occurred while invoking service. Cause :", ae);
			
		}
		
		cleanupDetails.setExecEnd(new Date());
		return cleanupDetails;
	}
}
