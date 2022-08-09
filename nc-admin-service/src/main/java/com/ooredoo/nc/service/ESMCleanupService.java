package com.ooredoo.nc.service;

import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.WebServiceIOException;

import com.ooredoo.nc.dto.NodalCleanupDetails;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.utility.ApplicationConstants;
import com.ooredoo.wsclient.WSClient;
import com.ooredoo.wsclient.dashboardhlr.HLRResponseType;
import com.ooredoo.wsclient.dashboardhlr.HLRrequestType;
import com.ooredoo.wsclient.esmprofile.DeleteUserProfile;
import com.ooredoo.wsclient.esmprofile.MetaWs;
import com.ooredoo.wsclient.esmprofile.SetMetas;
import com.ooredoo.wsclient.getcustomerprofile.proxy.GetCustomerProfile;
import com.ooredoo.wsclient.getcustomerprofile.proxy.GetCustomerProfileResponse;

import kw.com.ooredoo.esmoffers.QuerySubscriptionElement;
import kw.com.ooredoo.esmoffers.QuerySubscriptionResponseElement;

@Service
public class ESMCleanupService implements ApplicationConstants {

	@Autowired
	private WSClient wsclient;
	
	@Autowired
	private ReloadableResourceBundleMessageSource externalConfig;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ESMCleanupService.class);
	
	public NodalCleanupDetails deleteEsmProfile(String msisdn) {
		NodalCleanupDetails cleanupDetails = new NodalCleanupDetails();
		
		try {
			cleanupDetails.setNode(ESM);
			cleanupDetails.setExecStart(new Date());
			
			try {
				LOGGER.info("Validating ESM profile for parent profile check");
				QuerySubscriptionResponseElement respElem = getESMProfile(msisdn);
				
				if(null != respElem && null != respElem.getUserProfile()) {
					String sharedParent = respElem.getUserProfile().getSharedParent();
					
					if(null != sharedParent && !sharedParent.isEmpty() && (sharedParent.startsWith(MSISDN_PREFIX) || sharedParent.startsWith("96"))) {
						LOGGER.info("Shared parent Number found : {}. Invoking set meta API for the MSISDN", sharedParent, msisdn);
						
						com.ooredoo.wsclient.esmprofile.SetMetas setMetaReq = new com.ooredoo.wsclient.esmprofile.SetMetas();
						setMetaReq.setUserURI("msisdn:" + msisdn);
						
						com.ooredoo.wsclient.esmprofile.MetaWs m = new com.ooredoo.wsclient.esmprofile.MetaWs();
						m.setKey("esm:shared:parentMSISDN");
						m.setValue(BLANK);
						setMetaReq.getMetas().add(m);
						
						wsclient.setEsmMeta(externalConfig.getMessage("service.cleanup.esm.url", null, Locale.getDefault()), 
								setMetaReq, 
					externalConfig.getMessage("service.cleanup.esm.context", null, Locale.getDefault()));
					} else {
						LOGGER.info("No shared parent profile found. Number should be clear for clean-up");
					}
				}
				
			} catch(ApplicationException ae) {
				LOGGER.error("Error occurred while extracting ESM profile. Cause:", ae);
			}
			
			com.ooredoo.wsclient.esmprofile.DeleteUserProfile req = new com.ooredoo.wsclient.esmprofile.DeleteUserProfile();
			req.setUserURI("MSISDN:" + msisdn);
			wsclient.deleteEsmProfile(
					externalConfig.getMessage("service.cleanup.esm.url", null, Locale.getDefault()), 
					req, 
					externalConfig.getMessage("service.cleanup.esm.context", null, Locale.getDefault()));
			cleanupDetails.setStatus(SUCCESS_LEBEL);
			cleanupDetails.setResponse("OK");
			
		} catch(NoSuchMessageException e) {
			cleanupDetails.setStatus(ERROR_LABEL);
			cleanupDetails.setResponse("Fatal Exception.");
			
		} catch(ApplicationException ae) {
			
			if(ae.getErrorMessage().startsWith("User could not be found from userURI")) {
				cleanupDetails.setStatus(SUCCESS_LEBEL);
				cleanupDetails.setResponse("OK");
				
			} else {
				cleanupDetails.setStatus(ERROR_LABEL);
				cleanupDetails.setResponse(ae.getMessage());
				
			}
			
			LOGGER.error("Error occurred while invoking service. Cause :", ae);
		}
		
		cleanupDetails.setExecEnd(new Date());
		return cleanupDetails;
	}
	
	public QuerySubscriptionResponseElement getESMProfile(String msisdn) throws ApplicationException {
		QuerySubscriptionResponseElement response = null;
		
		try {
			QuerySubscriptionElement req = new QuerySubscriptionElement();
			req.setChannel("NumberCleanup");
			req.setMsisdn(msisdn);
			
			response = wsclient.getEsmProfile(
					externalConfig.getMessage("service.profile.esm.url", null, Locale.getDefault()), 
					req, 
					externalConfig.getMessage("service.profile.esm.context", null, Locale.getDefault()));
			
		} catch(ApplicationException ae) {
			LOGGER.error("Unable to extract ESM profile. Cause : ", ae);
			throw ae;
		}
		
		return response;
	}
	
	public GetCustomerProfileResponse getCustomerProfile(String msisdn) throws ApplicationException {
		GetCustomerProfileResponse profile = null;
		
		try {
			
			GetCustomerProfile req = new GetCustomerProfile();
			req.setChannel(externalConfig.getMessage("service.profile.customer.channel", null, Locale.getDefault()));
			req.setMsisdn(msisdn);
			
			String dataExtractSeriveUrl = externalConfig.getMessage("service.profile.customer.url", null, Locale.getDefault());
			profile = wsclient.getCustomerProfile(dataExtractSeriveUrl, req, SUBS_DATA_EXT_SERVICE_CONTEXT);
				
		}catch(WebServiceIOException ioe) {
			LOGGER.error("Unable to extract customer profile. Cause :", ioe);
			throw new ApplicationException(ioe.getMessage(), true);
			
		} catch(Exception e) {
			LOGGER.error("Unable to extract customer profile. Cause :", e);
			throw new ApplicationException(e.getMessage(), true);
		}
		
		return profile;
	}
	
	public HLRResponseType getHlrProfile(String msisdn) throws ApplicationException {
		HLRResponseType profile = null;
		
		try {
			
			HLRrequestType req = new HLRrequestType();
			req.setMsisdn(msisdn);
			
			String dataExtractSeriveUrl = externalConfig.getMessage("service.profile.hlr.url", null, Locale.getDefault());
			profile = wsclient.getHLRProfile(dataExtractSeriveUrl, req, HLR_DATA_EXT_SERVICE_CONTEXT);
				
		}catch(WebServiceIOException ioe) {
			LOGGER.error("Unable to extract HLR profile. Cause :", ioe);
			throw new ApplicationException(ioe.getMessage(), true);
			
		} catch(Exception e) {
			LOGGER.error("Unable to extract HLR profile. Cause :", e);
			throw new ApplicationException(e.getMessage(), true);
		}
		
		return profile;
	}
}
