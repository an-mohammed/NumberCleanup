package com.ooredoo.nc.gui.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.ooredoo.nc.dto.SubscriberProfileCleanupRequest;
import com.ooredoo.nc.dto.SubscriberProfileCleanupResponse;
import com.ooredoo.nc.dto.SubscriberProfileManagementResponse;
import com.ooredoo.nc.exception.ApplicationRestException;
import com.ooredoo.nc.model.CleanupLogMaster;
import com.ooredoo.nc.utility.ApplicationConstants;


@Component("profileSerivce")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ProfileService extends ServiceConsumer implements Serializable, ApplicationConstants {
	
	private static final long serialVersionUID = 1L;
	
	public SubscriberProfileCleanupResponse cleanup(SubscriberProfileCleanupRequest cleanupRequest) throws ApplicationRestException {
		SubscriberProfileCleanupResponse responseUser = null;
		HttpEntity<SubscriberProfileCleanupResponse> cleanupResponse = null;
		
		try {
			Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, cleanupResponse, cleanupRequest, HttpMethod.POST,  new ParameterizedTypeReference<SubscriberProfileCleanupResponse>() {
    		}, getProfileCleanupUrl(), null);
			
			if(null != o && o instanceof SubscriberProfileCleanupResponse) {
				responseUser = (SubscriberProfileCleanupResponse) o;
			}
		} catch(ApplicationRestException e) {
			throw e;
		}
		
		return responseUser;
	}
	
	@SuppressWarnings("unchecked")
	public List<CleanupLogMaster> showHist(String msisdn) throws ApplicationRestException {
		List<CleanupLogMaster> history = null;
		HttpEntity<List<CleanupLogMaster>> historyResponse = null;
		
		try {
			
			MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
			requestParams.add("msisdn", msisdn);
			
			Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, historyResponse, null, HttpMethod.GET,  new ParameterizedTypeReference<List<CleanupLogMaster>>() {
    		}, getCleanupHistSearchUrl(), requestParams);
			
			if(null != o && o instanceof List) {
				history = (List<CleanupLogMaster>) o;
			}
		} catch(ApplicationRestException e) {
			throw e;
		}
		
		return history;
	}
	
	/**
	 * 
	 * @param msisdn
	 * @return
	 * @throws ApplicationRestException
	 */
	public SubscriberProfileManagementResponse searchProfile(String msisdn) throws ApplicationRestException {
		SubscriberProfileManagementResponse profile = null;
		HttpEntity<SubscriberProfileManagementResponse> profileResponse = null;
		
		try {
			
			MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
			requestParams.add("msisdn", msisdn);
			
			Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, profileResponse, null, HttpMethod.GET,  new ParameterizedTypeReference<SubscriberProfileManagementResponse>() {
    		}, getProfileSearchUrl(), requestParams);
			
			if(null != o && o instanceof SubscriberProfileManagementResponse) {
				profile = (SubscriberProfileManagementResponse) o;
			}
		} catch(ApplicationRestException e) {
			throw e;
		}
		
		return profile;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getAllErpLocations() throws ApplicationRestException {
		Map<String, String> allErpLocations = null;
		HttpEntity<Map<String, String>> erpLocations = null;
		
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
		requestParams.add("findAll", "true");
		
		
		Object o = getExchangeInternalService().exchangeData(NC_ERP_SERVICE, erpLocations, null, HttpMethod.GET,  new ParameterizedTypeReference<Map<String, String>>() {
		}, getAllErpLocationsUrl(), requestParams);
		
		if(null != o) {
			allErpLocations = (Map<String, String>) o;
		}
		
		return allErpLocations;
	}
}
