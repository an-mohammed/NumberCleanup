package com.ooredoo.nc.service;

import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.ooredoo.nc.dto.DatabaseConfigDetails;
import com.ooredoo.nc.dto.NodalCleanupDetails;
import com.ooredoo.nc.dto.SubscriberProfileDetails;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.exception.ApplicationRestException;
import com.ooredoo.nc.model.NumberPool;
import com.ooredoo.nc.repo.CompositeRepository;
import com.ooredoo.nc.utility.ApplicationConstants;

@Service
public class NMSCleanupService extends ConsumerService implements ApplicationConstants {
		
	@Autowired
	private CompositeRepository compositeRepo;
	private static final Logger LOGGER = LoggerFactory.getLogger(NMSCleanupService.class);
	
	public NodalCleanupDetails deleteSubscriberFromNms(SubscriberProfileDetails profile) {
		NodalCleanupDetails cleanupDetails = null;
		HttpEntity<NodalCleanupDetails> cleanupResponse = null;
		
		try {
			Object o = getExchangeInternalService().exchangeData(NC_NMS_SERVICE, cleanupResponse, profile, HttpMethod.POST,  new ParameterizedTypeReference<NodalCleanupDetails>() {
    		}, getNmsCleanupUrl(), null);
			
			if(null != o && o instanceof NodalCleanupDetails) {
				cleanupDetails = (NodalCleanupDetails) o;
			}
			
			Optional<NumberPool> npO = compositeRepo.getNumberPoolRepo().findByMsisdn(profile.getMsisdn());
			
			if(npO.isPresent() && null != cleanupDetails && SUCCESS_LEBEL.equals(cleanupDetails.getStatus())) {
				LOGGER.info("As clean up response from NMS is SUCCESS, so updating numberpool with current NMS Pool");
				NumberPool np = npO.get();
				np.setCurNmsPool(profile.getSelecedNmsPool());
				compositeRepo.getNumberPoolRepo().save(np);
			}
			
		} catch(ApplicationRestException e) {
			LOGGER.error("Error occurred while invoking NMS cleanup service. Cause : {}", e);
			cleanupDetails = new NodalCleanupDetails();
			cleanupDetails.setNode(NMS);
			cleanupDetails.setStatus(ERROR_LABEL);
			cleanupDetails.setResponse(e.getErrorMessage());
			cleanupDetails.setExecEnd(new Date());
		}
		
		return cleanupDetails;
		
	}
	
	public DatabaseConfigDetails getDBConfig() throws ApplicationException {
		DatabaseConfigDetails nmsDbProfile = null;
		HttpEntity<DatabaseConfigDetails> nmsDbProfileResp = null;
		
		try {
			
			MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
			requestParams.add("findAll", "true");
			
			Object o = getExchangeInternalService().exchangeData(NC_NMS_SERVICE, nmsDbProfileResp, null, HttpMethod.GET,  new ParameterizedTypeReference<DatabaseConfigDetails>() {
    		}, getNmsDbConfigUrl(), requestParams);
			
			if(null != o && o instanceof DatabaseConfigDetails) {
				nmsDbProfile = (DatabaseConfigDetails) o;
			}
		} catch(ApplicationRestException e) {
			LOGGER.error("Error occurred while extracting NMS Db Config. Cause : {}", e);
			throw new ApplicationException(e.getErrorMessage(), true);
		}
		
		return nmsDbProfile;
	}
}
