package com.ooredoo.nc.service;

import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import com.huawei.hss.RMVHHSSSUB;
import com.huawei.hss.RMVHHSSSUBResponse;
import com.huawei.hss.RMVSUB;
import com.huawei.hss.RMVSUBResponse;
import com.ooredoo.nc.dto.NodalCleanupDetails;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.utility.ApplicationConstants;
import com.ooredoo.wsclient.WSClient;

@Service
public class HLRCleanupService implements ApplicationConstants {

	@Autowired
	private WSClient wsclient;
	
	@Autowired
	private ReloadableResourceBundleMessageSource externalConfig;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HLRCleanupService.class);
	
	public NodalCleanupDetails deleteSubscriberFromHlr(String msisdn, String imsi) {
		NodalCleanupDetails cleanupDetails = null;
		
		try {
			cleanupDetails = new NodalCleanupDetails();
			cleanupDetails.setNode(HLR);
			cleanupDetails.setExecStart(new Date());
			
			LOGGER.info("Cleanup HLR - Invoking operation :  RMVHHSSSUB with MSISDN {}", PLUS + msisdn);
			RMVHHSSSUB rMVHHSSSUBReq = new RMVHHSSSUB();
			rMVHHSSSUBReq.setSUBID(PLUS + msisdn);
			RMVHHSSSUBResponse response = wsclient.rmvHHSSSUBHlr(
					externalConfig.getMessage("service.cleanup.hlr.hss.url", null, Locale.getDefault())
					, rMVHHSSSUBReq
					, externalConfig.getMessage("service.cleanup.hlr.context", null, Locale.getDefault()));
			
			if(null != response) {
				String resultCode = response.getResult().getResultCode();
				String resultMessage = response.getResult().getResultDesc();
				
				if(null != resultCode && null != resultMessage 
						&& (resultCode.equals(SUCCESS_RESPONSE_CODE) || resultCode.equals(SUBS_NOT_FOUND_ERROR))) { //900003113 Modify based on success response code
					LOGGER.info("Subscriber clean up complete from HLR#rmvHHSSSUB with response code :" + resultCode + " response message :" + resultMessage);
				} else {
					throw new ApplicationException("Operation : RMVHHSSSUB # Error Code : " + resultCode + " Error Message : " + resultMessage, true);
				}
			}
			
			LOGGER.info("Cleanup HLR - Invoking operation :  RMVHHSSSUB with IMSI {}", imsi);
			 rMVHHSSSUBReq = new RMVHHSSSUB();
			rMVHHSSSUBReq.setIMSI(imsi);
			 response = wsclient.rmvHHSSSUBHlr(
					externalConfig.getMessage("service.cleanup.hlr.hss.url", null, Locale.getDefault())
					, rMVHHSSSUBReq
					, externalConfig.getMessage("service.cleanup.hlr.context", null, Locale.getDefault()));
			
			if(null != response) {
				String resultCode = response.getResult().getResultCode();
				String resultMessage = response.getResult().getResultDesc();
				
				if(null != resultCode && null != resultMessage 
						&& (resultCode.equals(SUCCESS_RESPONSE_CODE) || resultCode.equals(IMSI_NOT_FOUND_ERROR))) { //900003113 Modify based on success response code
					LOGGER.info("Subscriber clean up complete from HLR#rmvHHSSSUB with response code :" + resultCode + " response message :" + resultMessage);
				} else {
					throw new ApplicationException("Operation : RMVHHSSSUB # Error Code : " + resultCode + " Error Message : " + resultMessage, true);
				}
			}
			
			LOGGER.info("Cleanup HLR - Invoking operation :  RMVSUB with MSISDN {}", msisdn);
			RMVSUB req = new RMVSUB();
			req.setISDN(msisdn);
			RMVSUBResponse result = wsclient.rmvSubHlr(
					externalConfig.getMessage("service.cleanup.hlr.hss.url", null, Locale.getDefault()), 
					req, 
					externalConfig.getMessage("service.cleanup.hlr.context", null, Locale.getDefault()));
			
			if(null != result) {
				String resultCode = result.getResult().getResultCode();
				String resultMessage = result.getResult().getResultDesc();
				
				if(null != resultCode && null != resultMessage 
						&& (resultCode.equals(SUCCESS_RESPONSE_CODE) || resultCode.equals(SUBSCRIBER_NOT_FOUND_ERROR))) { //Modify based on success response code
					LOGGER.info("Subscriber clean up complete from HLR#rmvSUB with response code :" + resultCode + " response message :" + resultMessage);
				} else {
					throw new ApplicationException("Operation : RMVSUB with MSISDN # Error Code : " + resultCode + " Error Message : " + resultMessage, true);
				}
			}
			
			LOGGER.info("Cleanup HLR - Invoking operation :  RMVSUB with IMSI {}", imsi);
			req = new RMVSUB();
			req.setIMSI(imsi);
			result = wsclient.rmvSubHlr(
					externalConfig.getMessage("service.cleanup.hlr.hss.url", null, Locale.getDefault()), 
					req, 
					externalConfig.getMessage("service.cleanup.hlr.context", null, Locale.getDefault()));
			
			if(null != result) {
				String resultCode = result.getResult().getResultCode();
				String resultMessage = result.getResult().getResultDesc();
				
				if(null != resultCode && null != resultMessage 
						&& (resultCode.equals(SUCCESS_RESPONSE_CODE) || resultCode.equals(SUBSCRIBER_NOT_FOUND_ERROR))) { //Modify based on success response code
					LOGGER.info("Subscriber clean up complete from HLR#rmvSUB with response code :" + resultCode + " response message :" + resultMessage);
				} else {
					throw new ApplicationException("Operation : RMVSUB with IMSI # Error Code : " + resultCode + " Error Message : " + resultMessage, true);
				}
			}
			
			LOGGER.info("Cleanup HLR - Invoking operation :  RmvDnaPtrRec with MSISDN {}", msisdn.replaceFirst(MSISDN_PREFIX, BLANK));
			wsclient.invokeRmvDnaPtrRec(msisdn.replaceFirst(MSISDN_PREFIX, BLANK), externalConfig.getMessage("service.cleanup.hlr.rmvdnaptrrec.url", null, Locale.getDefault()));
			
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
