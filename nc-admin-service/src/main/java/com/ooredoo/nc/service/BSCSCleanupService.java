package com.ooredoo.nc.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.ooredoo.nc.dto.BSCSProfileDetails;
import com.ooredoo.nc.dto.DatabaseConfigDetails;
import com.ooredoo.nc.dto.NodalCleanupDetails;
import com.ooredoo.nc.dto.SubscriberProfileDetails;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.exception.ApplicationRestException;
import com.ooredoo.nc.utility.ApplicationConstants;

@Service
public class BSCSCleanupService extends ConsumerService implements ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(BSCSCleanupService.class);
	
	/**
	 * 
	 * @param profile
	 * @return
	 */
	public NodalCleanupDetails deleteSubscriberFromBscs(SubscriberProfileDetails profile) {
		NodalCleanupDetails cleanupDetails = null;
		HttpEntity<NodalCleanupDetails> cleanupResponse = null;
		
		try {
			Object o = getExchangeInternalService().exchangeData(NC_BSCS_SERVICE, cleanupResponse, profile, HttpMethod.POST,  new ParameterizedTypeReference<NodalCleanupDetails>() {
    		}, getBscsCleanupUrl(), null);
			
			if(null != o && o instanceof NodalCleanupDetails) {
				cleanupDetails = (NodalCleanupDetails) o;
			}
		} catch(ApplicationRestException e) {
			LOGGER.error("Error occurred while invoking bscs cleanup service. Cause : {}", e);
			cleanupDetails = new NodalCleanupDetails();
			cleanupDetails.setNode(BSCS);
			cleanupDetails.setStatus(ERROR_LABEL);
			cleanupDetails.setResponse(e.getErrorMessage());
			cleanupDetails.setExecEnd(new Date());
		}
		
		return cleanupDetails;
	}
	
	/**
	 * 
	 * @param msisdn
	 * @return
	 * @throws ApplicationException
	 */
	public BSCSProfileDetails getBscsProfile(String msisdn) throws ApplicationException {
		BSCSProfileDetails bscsProfile = null;
		HttpEntity<BSCSProfileDetails> bscsProfileResp = null;
		
		try {
			
			MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
			requestParams.add("msisdn", msisdn);
			
			Object o = getExchangeInternalService().exchangeData(NC_BSCS_SERVICE, bscsProfileResp, null, HttpMethod.GET,  new ParameterizedTypeReference<BSCSProfileDetails>() {
    		}, getBscsProfileUrl(), requestParams);
			
			if(null != o && o instanceof BSCSProfileDetails) {
				bscsProfile = (BSCSProfileDetails) o;
			}
		} catch(ApplicationRestException e) {
			LOGGER.error("Error occurred while extracting bscs profile. Cause : {}", e);
			throw new ApplicationException(e.getErrorMessage(), true);
		}
		
		return bscsProfile;
	}
	
	public DatabaseConfigDetails getDBConfig() throws ApplicationException {
		DatabaseConfigDetails bscsDbProfile = null;
		HttpEntity<DatabaseConfigDetails> bscsDbProfileResp = null;
		
		try {
			
			MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
			requestParams.add("findAll", "true");
			
			Object o = getExchangeInternalService().exchangeData(NC_BSCS_SERVICE, bscsDbProfileResp, null, HttpMethod.GET,  new ParameterizedTypeReference<DatabaseConfigDetails>() {
    		}, getBscsDbConfigUrl(), requestParams);
			
			if(null != o && o instanceof DatabaseConfigDetails) {
				bscsDbProfile = (DatabaseConfigDetails) o;
			}
		} catch(ApplicationRestException e) {
			LOGGER.error("Error occurred while extracting bscs profile. Cause : {}", e);
			throw new ApplicationException(e.getErrorMessage(), true);
		}
		
		return bscsDbProfile;
	}
	
	/**
	 * 
	 * @param msisdn
	 * @return
	 * @throws ApplicationException
	 */
	public BSCSProfileDetails getProfileValidationDetails(String msisdn) throws ApplicationException {
		HttpEntity<BSCSProfileDetails> bscsProfileResp = null;
		BSCSProfileDetails bscsProfile = null;
		
		try {
			
			MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
			requestParams.add("msisdn", msisdn);
			
			Object o = getExchangeInternalService().exchangeData(NC_BSCS_SERVICE, bscsProfileResp, null, HttpMethod.GET,  new ParameterizedTypeReference<BSCSProfileDetails>() {
    		}, getBscsProfileValidationUrl(), requestParams);
			
			if(null != o && o instanceof BSCSProfileDetails) {
				bscsProfile = (BSCSProfileDetails) o;
			}
		} catch(ApplicationRestException e) {
			LOGGER.error("Error occurred while extracting bscs profile for validation. Cause : {}", e);
			throw new ApplicationException(e.getErrorMessage(), true);
		}
		
		return bscsProfile;
	}
}
