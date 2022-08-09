package com.ooredoo.nc.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.ooredoo.nc.dto.DatabaseConfigDetails;
import com.ooredoo.nc.dto.DisconnectionOrderDetails;
import com.ooredoo.nc.dto.NodalCleanupDetails;
import com.ooredoo.nc.dto.SubscriberProfileDetails;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.exception.ApplicationRestException;
import com.ooredoo.nc.utility.ApplicationConstants;

@Service
public class RASCleanupService extends ConsumerService implements ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(RASCleanupService.class);
	
	/**
	 * 
	 * @param profile
	 * @return
	 */
	public NodalCleanupDetails deleteSubscriberFromRas(SubscriberProfileDetails profile) {
		NodalCleanupDetails cleanupDetails = null;
		HttpEntity<NodalCleanupDetails> cleanupResponse = null;
		
		try {
			Object o = getExchangeInternalService().exchangeData(NC_RAS_SERVICE, cleanupResponse, profile, HttpMethod.POST,  new ParameterizedTypeReference<NodalCleanupDetails>() {
    		}, getRasCleanupUrl(), null);
			
			if(null != o && o instanceof NodalCleanupDetails) {
				cleanupDetails = (NodalCleanupDetails) o;
			}
		} catch(ApplicationRestException e) {
			LOGGER.error("Error occurred while invoking RAS cleanup service. Cause : {}", e);
			cleanupDetails = new NodalCleanupDetails();
			cleanupDetails.setNode(RAS);
			cleanupDetails.setStatus(ERROR_LABEL);
			cleanupDetails.setResponse(e.getErrorMessage());
			cleanupDetails.setExecEnd(new Date());
		}
		
		return cleanupDetails;
		
	}
	
	/**
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	public DatabaseConfigDetails getDBConfig() throws ApplicationException {
		DatabaseConfigDetails rasDbProfile = null;
		HttpEntity<DatabaseConfigDetails> rasDbProfileResp = null;
		
		try {
			
			MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
			requestParams.add("findAll", "true");
			
			Object o = getExchangeInternalService().exchangeData(NC_RAS_SERVICE, rasDbProfileResp, null, HttpMethod.GET,  new ParameterizedTypeReference<DatabaseConfigDetails>() {
    		}, getRasDbConfigUrl(), requestParams);
			
			if(null != o && o instanceof DatabaseConfigDetails) {
				rasDbProfile = (DatabaseConfigDetails) o;
			}
		} catch(ApplicationRestException e) {
			LOGGER.error("Error occurred while extracting NMS Db Config. Cause : {}", e);
			throw new ApplicationException(e.getErrorMessage(), true);
		}
		
		return rasDbProfile;
	}

	/**
	 * 
	 * @param dcOrder
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DisconnectionOrderDetails> pushDisconnectionRequest(List<DisconnectionOrderDetails> dcOrderList) throws ApplicationException{
		List<DisconnectionOrderDetails> dcDetails = null;
		HttpEntity<List<DisconnectionOrderDetails>> dcDetailResponse = null;
		
		try {
			Object o = getExchangeInternalService().exchangeData(NC_RAS_SERVICE, dcDetailResponse, dcOrderList, HttpMethod.POST,  new ParameterizedTypeReference<List<DisconnectionOrderDetails>>() {
    		}, getRasDisconnectionUrl(), null);
			
			if(null != o ) {
				dcDetails = (List<DisconnectionOrderDetails>) o;
			}
		} catch(ApplicationRestException e) {
			LOGGER.error("Error occurred while invoking RAS disconnection service. Cause : {}", e);
			throw e;
		}
		
		return dcDetails;
	}
}
