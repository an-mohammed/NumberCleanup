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

import com.ooredoo.nc.exception.ApplicationRestException;
import com.ooredoo.nc.model.NcAppUserGrp;
import com.ooredoo.nc.utility.ApplicationConstants;


@Component("userGrpSerivce")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserGrpService extends ServiceConsumer implements Serializable, ApplicationConstants {
	
	private static final long serialVersionUID = 1L;
	
	public NcAppUserGrp getGroupDetails(String groupName) throws ApplicationRestException  {
		NcAppUserGrp grp = null;
		HttpEntity<NcAppUserGrp> responseGroup = null;
		
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
		requestParams.add("grpName", groupName);
		
		Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, responseGroup, null, HttpMethod.GET,  new ParameterizedTypeReference<NcAppUserGrp>() {
		}, getFindGroupByNameUrl(), requestParams);
		
		if(null != o) {
			grp = (NcAppUserGrp) o;
		}
		
		return grp;
	}
	
	@SuppressWarnings("unchecked")
	public List<NcAppUserGrp> getAllGroups() throws ApplicationRestException  {
		List<NcAppUserGrp> allGroup = null;
		HttpEntity<List<NcAppUserGrp>> responseGrp = null;
		
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
		requestParams.add("findAll", "true");
		
		
		Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, responseGrp, null, HttpMethod.GET,  new ParameterizedTypeReference<List<NcAppUserGrp>>() {
		}, getFindAllGroupsUrl(), requestParams);
		
		if(null != o) {
			allGroup = (List<NcAppUserGrp>) o;
		}
		
		return allGroup;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getAllGroupsForUser(String id) throws ApplicationRestException  {
		List<String> allGroup = null;
		HttpEntity<List<String>> responseGrp = null;
		
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
		requestParams.add("userId", id);
		
		
		Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, responseGrp, null, HttpMethod.GET,  new ParameterizedTypeReference<List<String>>() {
		}, getFindAllGroupsForUserUrl(), requestParams);
		
		if(null != o) {
			allGroup = (List<String>) o;
		}
		
		return allGroup;
	}

	public NcAppUserGrp createGroup(NcAppUserGrp newGroup) throws ApplicationRestException {
		NcAppUserGrp responseGrp = null;
		HttpEntity<NcAppUserGrp> responseGrpDetails = null;
		
		try {
			Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, responseGrpDetails, newGroup, HttpMethod.POST,  new ParameterizedTypeReference<NcAppUserGrp>() {
    		}, getCreateGroupUrl(), null);
			
			if(null != o && o instanceof NcAppUserGrp) {
				responseGrp = (NcAppUserGrp) o;
			}
		} catch(ApplicationRestException e) {
			throw e;
			
		}
		
		return responseGrp;
	}
	
	public NcAppUserGrp updateGroup(NcAppUserGrp newGroup) throws ApplicationRestException {
		NcAppUserGrp responseGrp = null;
		HttpEntity<NcAppUserGrp> responseGrpDetails = null;
		
		try {
			Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, responseGrpDetails, newGroup, HttpMethod.PUT,  new ParameterizedTypeReference<NcAppUserGrp>() {
    		}, getUpdateGroupUrl(), null);
			
			if(null != o && o instanceof NcAppUserGrp) {
				responseGrp = (NcAppUserGrp) o;
			}
		} catch(ApplicationRestException e) {
			throw e;
			
		}
		
		return responseGrp;
	}
}
