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
public class ERPCleanupService extends ConsumerService implements ApplicationConstants {

	@Autowired
	private CompositeRepository compositeRepo;
	private static final Logger LOGGER = LoggerFactory.getLogger(ERPCleanupService.class);
	
	public NodalCleanupDetails deleteSubscriberFromErp(SubscriberProfileDetails profile) {
		NodalCleanupDetails cleanupDetails = null;
		HttpEntity<NodalCleanupDetails> cleanupResponse = null;
		
		try {
			Object o = getExchangeInternalService().exchangeData(NC_ERP_SERVICE, cleanupResponse, profile, HttpMethod.POST,  new ParameterizedTypeReference<NodalCleanupDetails>() {
    		}, getErpCleanupUrl(), null);
			
			if(null != o && o instanceof NodalCleanupDetails) {
				cleanupDetails = (NodalCleanupDetails) o;
			}
			
			Optional<NumberPool> npO = compositeRepo.getNumberPoolRepo().findByMsisdn(profile.getMsisdn());
			
			if(npO.isPresent() && null != cleanupDetails && null != cleanupDetails.getStatus() && SUCCESS_LEBEL.equals(cleanupDetails.getStatus())) {
				LOGGER.info("As clean up response from ERP is SUCCESS, so updating numberpool with current ERP location");
				NumberPool np = npO.get();
				np.setCurErpLoc(profile.getSelectedErpLocation());
				compositeRepo.getNumberPoolRepo().save(np);
			}
			
		} catch(ApplicationRestException e) {
			LOGGER.error("Error occurred while invoking ERP cleanup service. Cause : {}", e);
			cleanupDetails = new NodalCleanupDetails();
			cleanupDetails.setNode(ERP);
			cleanupDetails.setStatus(ERROR_LABEL);
			cleanupDetails.setResponse(e.getErrorMessage());
			cleanupDetails.setExecEnd(new Date());
		}
		
		return cleanupDetails;
		
	}
	
	public DatabaseConfigDetails getDBConfig() throws ApplicationException {
		DatabaseConfigDetails erpDbProfile = null;
		HttpEntity<DatabaseConfigDetails> erpDbProfileResp = null;
		
		try {
			
			MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
			requestParams.add("findAll", "true");
			
			Object o = getExchangeInternalService().exchangeData(NC_ERP_SERVICE, erpDbProfileResp, null, HttpMethod.GET,  new ParameterizedTypeReference<DatabaseConfigDetails>() {
    		}, getErpDbConfigUrl(), requestParams);
			
			if(null != o && o instanceof DatabaseConfigDetails) {
				erpDbProfile = (DatabaseConfigDetails) o;
			}
		} catch(ApplicationRestException e) {
			LOGGER.error("Error occurred while extracting ERP Db Comfig. Cause : {}", e);
			throw new ApplicationException(e.getErrorMessage(), true);
		}
		
		return erpDbProfile;
	}
}
