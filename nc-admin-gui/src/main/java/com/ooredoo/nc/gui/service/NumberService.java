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

import com.ooredoo.nc.dto.ReleaseNumberFromAssignmentReq;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.exception.ApplicationRestException;
import com.ooredoo.nc.model.NumberAsgnmtHistory;
import com.ooredoo.nc.model.NumberPool;
import com.ooredoo.nc.utility.ApplicationConstants;


@Component("numberPoolSerivce")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NumberService extends ServiceConsumer implements Serializable, ApplicationConstants {
	
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	public List<NumberPool> getAllNumbers() throws ApplicationRestException  {
		List<NumberPool> allNumbers = null;
		HttpEntity<List<NumberPool>> allNoEntity = null;
		
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
		requestParams.add("findAll", "true");
		
		Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, allNoEntity, null, HttpMethod.GET,  new ParameterizedTypeReference<List<NumberPool>>() {
		}, getAllNumbersUrl(), requestParams);
		
		if(null != o) {
			allNumbers = (List<NumberPool>) o;
		}
		
		return allNumbers;
	}
	
	public NumberPool updateNumber(NumberPool req) throws ApplicationException {
		NumberPool updatedNumber = null;
		HttpEntity<NumberPool> responseNumber = null;
		
		try {
			
			Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, responseNumber, req, HttpMethod.PUT,  new ParameterizedTypeReference<NumberPool>() {
    		}, getUpdateNumberUrl(), null);
			
			if(null != o && o instanceof NumberPool) {
				updatedNumber = (NumberPool) o;
			}
		} catch(ApplicationRestException e) {
			throw new ApplicationException(e);
			
		}
		
		return updatedNumber;
	}
	
	public NumberPool updateNumber(ReleaseNumberFromAssignmentReq req) throws ApplicationException {
		NumberPool updatedNumber = null;
		HttpEntity<NumberPool> responseNumber = null;
		
		try {
			
			Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, responseNumber, req, HttpMethod.PUT,  new ParameterizedTypeReference<NumberPool>() {
    		}, getReleaseNumberFromAssignmentUrl(), null);
			
			if(null != o && o instanceof NumberPool) {
				updatedNumber = (NumberPool) o;
			}
		} catch(ApplicationRestException e) {
			throw new ApplicationException(e);
			
		}
		
		return updatedNumber;
	}

	public NumberPool addNumber(NumberPool newNumber) throws ApplicationException {
		NumberPool addedNumber = null;
		HttpEntity<NumberPool> responseNumber = null;
		
		try {
			
			Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, responseNumber, newNumber, HttpMethod.POST,  new ParameterizedTypeReference<NumberPool>() {
    		}, getAddNumberUrl(), null);
			
			if(null != o && o instanceof NumberPool) {
				addedNumber = (NumberPool) o;
			}
		} catch(ApplicationRestException e) {
			throw new ApplicationException(e);
			
		}
		
		return addedNumber;
	}
	
	public NumberAsgnmtHistory finishedAssignment(NumberAsgnmtHistory hist) throws ApplicationException {
		NumberAsgnmtHistory finishedTask = null;
		HttpEntity<NumberAsgnmtHistory> responseFTask = null;
		
		try {
			
			Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, responseFTask, hist, HttpMethod.POST,  new ParameterizedTypeReference<NumberAsgnmtHistory>() {
    		}, getFinishAssignmentUrl(), null);
			
			if(null != o && o instanceof NumberAsgnmtHistory) {
				finishedTask = (NumberAsgnmtHistory) o;
			}
		} catch(ApplicationRestException e) {
			throw new ApplicationException(e);
			
		}
		
		return finishedTask;
	}
	
	@SuppressWarnings("unchecked")
	public List<NumberAsgnmtHistory> getAssignmentsForUser(Long id) throws ApplicationRestException  {
		List<NumberAsgnmtHistory> allAssignment = null;
		HttpEntity<List<NumberAsgnmtHistory>> allAssignmentEntity = null;
		
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
		requestParams.add("userId", String.valueOf(id));
		
		Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, allAssignmentEntity, null, HttpMethod.GET,  new ParameterizedTypeReference<List<NumberAsgnmtHistory>>() {
		}, getAllAssignmentUrl(), requestParams);
		
		if(null != o) {
			allAssignment = (List<NumberAsgnmtHistory>) o;
		}
		
		return allAssignment;
	}
	
	@SuppressWarnings("unchecked")
	public List<NumberAsgnmtHistory> getAllAssignments() throws ApplicationRestException  {
		List<NumberAsgnmtHistory> allAssignment = null;
		HttpEntity<List<NumberAsgnmtHistory>> allAssignmentEntity = null;
		
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
		requestParams.add("findAll", "");
		
		Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, allAssignmentEntity, null, HttpMethod.GET,  new ParameterizedTypeReference<List<NumberAsgnmtHistory>>() {
		}, getAllAssignmentForAdminUrl(), requestParams);
		
		if(null != o) {
			allAssignment = (List<NumberAsgnmtHistory>) o;
		}
		
		return allAssignment;
	}
	
	public NumberAsgnmtHistory updateAssignment(NumberAsgnmtHistory req) throws ApplicationException {
		NumberAsgnmtHistory updatedNumber = null;
		HttpEntity<NumberAsgnmtHistory> responseNumber = null;
		
		try {
			
			Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, responseNumber, req, HttpMethod.PUT,  new ParameterizedTypeReference<NumberPool>() {
    		}, getUpdateAssignmentUrl(), null);
			
			if(null != o && o instanceof NumberAsgnmtHistory) {
				updatedNumber = (NumberAsgnmtHistory) o;
			}
		} catch(ApplicationRestException e) {
			throw new ApplicationException(e);
			
		}
		
		return updatedNumber;
	}

	public NumberAsgnmtHistory createNewAssignment(NumberAsgnmtHistory newAsmt) throws ApplicationException{
		NumberAsgnmtHistory addedAsmt = null;
		HttpEntity<NumberAsgnmtHistory> responseAsmt = null;
		
		try {
			
			Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, responseAsmt, newAsmt, HttpMethod.POST,  new ParameterizedTypeReference<NumberAsgnmtHistory>() {
    		}, getCreateAssignmentUrl(), null);
			
			if(null != o && o instanceof NumberAsgnmtHistory) {
				addedAsmt = (NumberAsgnmtHistory) o;
			}
		} catch(ApplicationRestException e) {
			throw new ApplicationException(e);
			
		}
		
		return addedAsmt;
	}
	
	@SuppressWarnings("unchecked")
	public List<NumberPool> getAllAvailableNumbers() throws ApplicationRestException  {
		List<NumberPool> allActiveNumbers = null;
		HttpEntity<List<NumberPool>> allANumberEntity = null;
		
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
		requestParams.add("findAll", "");
		
		Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, allANumberEntity, null, HttpMethod.GET,  new ParameterizedTypeReference<List<NumberPool>>() {
		}, getAvailableNumbersUrl(), requestParams);
		
		if(null != o) {
			allActiveNumbers = (List<NumberPool>) o;
		}
		
		return allActiveNumbers;
	}

	@SuppressWarnings("unchecked")
	public List<NumberPool> getAvailableNumbersToUserGroup(Long id)  throws ApplicationRestException {
		List<NumberPool> allActiveNumbers = null;
		HttpEntity<List<NumberPool>> allANumberEntity = null;
		
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
		requestParams.add("userId", String.valueOf(id));
		
		Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, allANumberEntity, null, HttpMethod.GET,  new ParameterizedTypeReference<List<NumberPool>>() {
		}, getAvailableNumbersToUserGrpUrl(), requestParams);
		
		if(null != o) {
			allActiveNumbers = (List<NumberPool>) o;
		}
		
		return allActiveNumbers;
	}
	
	@SuppressWarnings("unchecked")
	public List<NumberPool> getAllActiveNumbersForUser(Long id) throws ApplicationRestException {
		List<NumberPool> allActiveNumbers = null;
		HttpEntity<List<NumberPool>> allANumberEntity = null;
		
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
		requestParams.add("userId", String.valueOf(id));
		
		Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, allANumberEntity, null, HttpMethod.GET,  new ParameterizedTypeReference<List<NumberPool>>() {
		}, getAllActiveNumbersForUserUrl(), requestParams);
		
		if(null != o) {
			allActiveNumbers = (List<NumberPool>) o;
		}
		
		return allActiveNumbers;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getAllActiveNumbersForUsername(String username) throws ApplicationRestException {
		List<String> allActiveNumbers = null;
		HttpEntity<List<String>> allANumberEntity = null;
		
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
		requestParams.add("username", username);
		
		Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, allANumberEntity, null, HttpMethod.GET,  new ParameterizedTypeReference<List<String>>() {
		}, getAllActiveNumbersForUsernameUrl(), requestParams);
		
		if(null != o) {
			allActiveNumbers = (List<String>) o;
		}
		
		return allActiveNumbers;
	}

	@SuppressWarnings("unchecked")
	public List<NumberAsgnmtHistory> searchAssignment(String msisdn) throws ApplicationRestException{
		List<NumberAsgnmtHistory> allAssignment = null;
		HttpEntity<List<NumberAsgnmtHistory>> allAssignmentEntity = null;
		
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
		requestParams.add("msisdn", msisdn);
		
		Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, allAssignmentEntity, null, HttpMethod.GET,  new ParameterizedTypeReference<List<NumberAsgnmtHistory>>() {
		}, getSearchAssignmentUrl(), requestParams);
		
		if(null != o) {
			allAssignment = (List<NumberAsgnmtHistory>) o;
		}
		
		return allAssignment;
		
	}
	
	public NumberPool getNumberDetails(String msisdn) throws ApplicationRestException {
		NumberPool numberPool = null;
		HttpEntity<NumberPool> numberPoolEntity = null;
		
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
		requestParams.add("msisdn", msisdn);
		
		Object o = getExchangeService().exchangeData(NC_ADMIN_SERVICE, numberPoolEntity, null, HttpMethod.GET,  new ParameterizedTypeReference<NumberPool>() {
		}, getSearchNumberUrl(), requestParams);
		
		if(null != o) {
			numberPool = (NumberPool) o;
		}
		
		return numberPool;
	}
}
