package com.ooredoo.nc.utility;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ooredoo.nc.exception.ApplicationRestException;

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ExchangeInternalService implements ApplicationConstants, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ReloadableResourceBundleMessageSource externalConfig;
	
	private Gson gson;
	private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeInternalService.class);
	
	@Value("#{serviceRegistry}")
	private Map<String, String> serviceRegistry;
	
	@Autowired
	RestTemplate restTemplate;
	
	private Map<String, Object> getErrorObjectFromErrorResponse(String errorResponseFromRest) {
		LOGGER.info("Error Response : {}", errorResponseFromRest);
		gson = new Gson();
		Map<String, Object> map = gson.fromJson(errorResponseFromRest, new TypeToken<Map<String, Object>>(){}.getType());
		return map;
	}
	
	/**
	 * 
	 * @param serviceInstance
	 * @param responseEntity
	 * @param requestObject
	 * @param httpMethod
	 * @param responseType
	 * @param url
	 * @param requestParams
	 * @param username
	 * @param password
	 * @return
	 * @throws ApplicationRestException
	 */
	public Object exchangeData(String serviceInstance, HttpEntity<?> responseEntity, Object requestObject, HttpMethod httpMethod
			, ParameterizedTypeReference<?> responseType, String url, MultiValueMap<String, String> requestParams) throws ApplicationRestException {
		
		Object responseBody = null;
		HttpEntity<?> respEntity = null;
		
		try {
			if(null != requestParams && !requestParams.isEmpty()) {
				UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(getRequestURL(serviceInstance, url)).queryParams((LinkedMultiValueMap<String, String>) requestParams); 
			    UriComponents uriComponents = builder.build().encode();  
			    
			    respEntity = restTemplate.exchange(
			    		uriComponents.toUri()
			    		, httpMethod
			    		,getRequestHttpEntity(requestObject, serviceInstance)
			    		, responseType);
			} else {
				respEntity = restTemplate.exchange(
						new URI(getRequestURL(serviceInstance, url))
						, httpMethod
						, getRequestHttpEntity(requestObject, serviceInstance)
						, responseType);
			}
			
			//responseEntity = respEntity;
			responseBody = respEntity.getBody();
			
		} catch (HttpStatusCodeException e) {
			if(null !=  e.getResponseBodyAsString() &&  !e.getResponseBodyAsString().isEmpty()) {
				
				String errorMessage = (String) getErrorObjectFromErrorResponse(e.getResponseBodyAsString()).get(ERROR_MESSAGE_JSON_TAG);
				LOGGER.error("Error Code :" + e.getRawStatusCode() + " Error Message :" + errorMessage);
				throw new ApplicationRestException(e.getRawStatusCode(), errorMessage);
				
			} else {
				LOGGER.error("Error Code :" + e.getRawStatusCode() + " Error Message :" + e.getMessage());
				throw new ApplicationRestException("AUI.999", e);
			}
		} catch (URISyntaxException e) {
			throw new ApplicationRestException(e);
			
		} catch(ResourceAccessException e) {
			throw new ApplicationRestException("AUI.999", e);
			
		}
		
		return responseBody;
	}
	
	public ReloadableResourceBundleMessageSource getExternalConfig() {
		return externalConfig;
	}

	public void setExternalConfig(ReloadableResourceBundleMessageSource externalConfig) {
		this.externalConfig = externalConfig;
	}

	private HttpEntity<?> getRequestHttpEntity(Object requestBody, String serviceInstance) throws ApplicationRestException {
		if(null != requestBody) {
			return new HttpEntity<>(requestBody, createHeadersForAppRequest(serviceInstance));
		} else {
			return new HttpEntity<>(createHeadersForAppRequest(serviceInstance));
		}
	}
	
	private HttpHeaders createHeadersForAppRequest(String serviceInstance) throws ApplicationRestException {
		HttpHeaders headers = new HttpHeaders() {
			private static final long serialVersionUID = 1L; 
			
			{
				try {
					 String username = externalConfig.getMessage(serviceInstance + ".service.username", null, Locale.getDefault());
					 String password = externalConfig.getMessage(serviceInstance + ".service.password", null, Locale.getDefault());
					 
					 //LOGGER.info("Password :" + password);
					 //password = CryptoConverter.getDecryptedValue(password);
					 //LOGGER.info("Password :" + password);
					 
			         String auth =  username + ":" + password ;
			         byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")) );
			         String authHeader = "Basic " + new String( encodedAuth );
			         
			         set("Authorization", authHeader );
			         
				} catch(NoSuchMessageException e) {
					LOGGER.error("Error Occurred while creating authentication header for user");
					throw new ApplicationRestException("AUI.008", e);
				}
				
		      }};
		      
		      return headers;
	}
	
	private String getRequestURL(String serviceInstance, String requestUrl) throws ApplicationRestException {
		String hostUrl = getServiceRegistry().get(serviceInstance);
		
		if(null != hostUrl && !hostUrl.isEmpty()) {
			
			String url = HTTP_URL_PREFIX + hostUrl + requestUrl;
					LOGGER.info("Request Url : " + url);
			return url;
		} else {
			throw new ApplicationRestException(404, "No service instance access path registered for service instance-" + serviceInstance);
		}
	}

	public Map<String, String> getServiceRegistry() {
		return serviceRegistry;
	}

	public void setServiceRegistry(Map<String, String> serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}
}
