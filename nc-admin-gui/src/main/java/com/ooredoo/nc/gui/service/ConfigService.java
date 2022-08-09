package com.ooredoo.nc.gui.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.ooredoo.nc.dto.AnaOnboardingRequest;
import com.ooredoo.nc.dto.B2BSubscriberOnboardingRequest;
import com.ooredoo.nc.dto.B2BSubscriberOnboardingResponse;
import com.ooredoo.nc.dto.B2CSubscriberOnboardingRequest;
import com.ooredoo.nc.dto.B2CSubscriberOnboardingResponse;
import com.ooredoo.nc.dto.CleanupSystemConfig;
import com.ooredoo.nc.dto.DisconnectionRequest;
import com.ooredoo.nc.dto.DisconnectionResponse;
import com.ooredoo.nc.dto.StatusRequest;
import com.ooredoo.nc.dto.StatusResponse;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.exception.ApplicationRestException;
import com.ooredoo.nc.model.BulkActivationDetail;
import com.ooredoo.nc.model.DigitalOffer;
import com.ooredoo.nc.model.NcDigitalOnboarding;
import com.ooredoo.nc.utility.ApplicationConstants;


@Component("configurationSerivce")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ConfigService extends ServiceConsumer implements Serializable, ApplicationConstants {
	
	private static final long serialVersionUID = 1L;
	
	public CleanupSystemConfig getSystemConfig() throws ApplicationRestException  {
		CleanupSystemConfig sysConfig = null;
		HttpEntity<List<CleanupSystemConfig>> sysConfigEntity = null;
		
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
		requestParams.add("findAll", "true");
		
		Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, sysConfigEntity, null, HttpMethod.GET,  new ParameterizedTypeReference<CleanupSystemConfig>() {
		}, getSystemConfigUrl(), requestParams);
		
		if(null != o) {
			sysConfig = (CleanupSystemConfig) o;
		}
		
		return sysConfig;
	}
	
	public String createPrepaidNumber(String msisdn, String simNo, String selectedPromoId) throws ApplicationRestException  {
		String msg = null;
		HttpEntity<String> resp = null;
		
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
		requestParams.add("msisdn", msisdn);
		requestParams.add("sim", simNo);
		requestParams.add("promoId", selectedPromoId);
		
		Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, resp, null, HttpMethod.GET,  new ParameterizedTypeReference<String>() {
		}, getCreateNumberUrl(), requestParams);
		
		if(null != o) {
			msg = (String) o;
		}
		
		return msg;
	}
	
	@SuppressWarnings("unchecked")
	public List<DigitalOffer> getDigitalPromoList() throws ApplicationRestException  {
		List<DigitalOffer> offers = null;
		HttpEntity<List<DigitalOffer>> resp = null;
		
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
		requestParams.add("test", "test");
		
		Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, resp, null, HttpMethod.GET,  new ParameterizedTypeReference<List<DigitalOffer>>() {
		}, getDigitalPromoListUrl(), requestParams);
		
		if(null != o) {
			offers = (List<DigitalOffer>) o;
		}
		
		return offers;
	}
	
	public B2CSubscriberOnboardingResponse createSubscriberProfile(B2CSubscriberOnboardingRequest req) throws ApplicationRestException  {
		B2CSubscriberOnboardingResponse msg = null;
		HttpEntity<B2CSubscriberOnboardingResponse> resp = null;
		
		Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, resp, req, HttpMethod.POST,  new ParameterizedTypeReference<B2CSubscriberOnboardingResponse>() {
		}, getSubscriberOnboardingUrl(), null);
		
		if(null != o) {
			msg = (B2CSubscriberOnboardingResponse) o;
		}
		
		return msg;
	}

	public B2BSubscriberOnboardingResponse createB2BSubscriberProfile(B2BSubscriberOnboardingRequest req) throws ApplicationRestException  {
		B2BSubscriberOnboardingResponse msg = null;
		HttpEntity<B2BSubscriberOnboardingResponse> resp = null;
		
		Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, resp, req, HttpMethod.POST,  new ParameterizedTypeReference<B2BSubscriberOnboardingResponse>() {
		}, getSubscriberOnboardingB2BUrl(), null);
		
		if(null != o) {
			msg = (B2BSubscriberOnboardingResponse) o;
		}
		
		return msg;
	}
	
	public String createAnaSubscriberProfile(AnaOnboardingRequest req) throws ApplicationRestException  {
		String msg = null;
		HttpEntity<String> resp = null;
		
		Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, resp, req, HttpMethod.POST,  new ParameterizedTypeReference<String>() {
		}, getSubscriberOnboardingAnaUrl(), null);
		
		if(null != o) {
			msg = (String) o;
		}
		
		return msg;
	}
	
	public DisconnectionResponse disconnection(DisconnectionRequest req) throws ApplicationRestException  {
		DisconnectionResponse discResp = null;
		HttpEntity<DisconnectionResponse> resp = null;
		
		Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, resp, req, HttpMethod.POST,  new ParameterizedTypeReference<DisconnectionResponse>() {
		}, getSubscriberDisconnectionUrl(), null);
		
		if(null != o) {
			discResp = (DisconnectionResponse) o;
		}
		
		return discResp;
	}
	
	@SuppressWarnings("unchecked")
	public List<BulkActivationDetail> getBulkActivationDetails(String username) throws ApplicationRestException  {
		List<BulkActivationDetail> aDetails = null;
		HttpEntity<List<BulkActivationDetail>> resp = null;
		
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
		requestParams.add("username", username);
		
		Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, resp, null, HttpMethod.GET,  new ParameterizedTypeReference<List<BulkActivationDetail>>() {
		}, getBulkActivationDetailsUrl(), requestParams);
		
		if(null != o) {
			aDetails = (List<BulkActivationDetail>) o;
		}
		
		return aDetails;
	}
	
	@SuppressWarnings("unchecked")
	public List<NcDigitalOnboarding> getDigitalActivationDetails(String username) throws ApplicationRestException  {
		List<NcDigitalOnboarding> aDetails = null;
		HttpEntity<List<NcDigitalOnboarding>> resp = null;
		
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
		requestParams.add("username", username);
		
		Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, resp, null, HttpMethod.GET,  new ParameterizedTypeReference<List<NcDigitalOnboarding>>() {
		}, getDigitalOnboardingTransactionsUrl(), requestParams);
		
		if(null != o) {
			aDetails = (List<NcDigitalOnboarding>) o;
		}
		
		return aDetails;
	}

	public String uploadFileContentForBulkActivation(List<BulkActivationDetail> bulkActivationDetailsRequestList) throws ApplicationRestException{
		String aDetails = null;
		HttpEntity<String> resp = null;
		
		Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, resp, bulkActivationDetailsRequestList, HttpMethod.POST,  new ParameterizedTypeReference<String>() {
		}, getCreateBulkActivationDetailsUrl(), null);
		
		if(null != o) {
			aDetails = (String) o;
		}
		
		return aDetails;
	}

	public StatusResponse getProfileStatus(StatusRequest req) throws ApplicationException {
		StatusResponse status = null;
		HttpEntity<StatusResponse> resp = null;
		
		Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, resp, req, HttpMethod.POST,  new ParameterizedTypeReference<StatusResponse>() {
		}, getBulkProfileStatusUrl(), null);
		
		if(null != o) {
			status = (StatusResponse) o;
		}
		
		return status;
		
	}
	
}
