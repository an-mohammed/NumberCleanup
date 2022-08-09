package com.ooredoo.nc.service;

import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import com.ooredoo.nc.dao.RASCleanupDao;
import com.ooredoo.nc.dto.DisconnectionOrderDetails;
import com.ooredoo.nc.dto.NodalCleanupDetails;
import com.ooredoo.nc.dto.SubscriberProfileDetails;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.utility.ApplicationConstants;

@Service
public class RASCleanupService implements ApplicationConstants {

	@Autowired
	private RASCleanupDao rasCleanupDao;
		
	private static final Logger LOGGER = LoggerFactory.getLogger(RASCleanupService.class);

	public NodalCleanupDetails deleteSubscriberFromRas(SubscriberProfileDetails profile) {
		NodalCleanupDetails cleanupDetails = new NodalCleanupDetails();
		
		try {
			cleanupDetails.setNode(RAS);
			cleanupDetails.setExecStart(new Date());
			
			rasCleanupDao.executeCleanupQueries(profile);
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
	
	/**
	 * 
	 * @param dcOrderList
	 * @return
	 */
	public List<DisconnectionOrderDetails> pushDisconnectionRequest(List<DisconnectionOrderDetails> dcOrderList){
		
		if(null != dcOrderList && !dcOrderList.isEmpty()) {
			
			ListIterator<DisconnectionOrderDetails> li = dcOrderList.listIterator();
			
			while(li.hasNext()) {
				DisconnectionOrderDetails dc = li.next();
				
				if(dc.getRequestStatus()) {
					try {
						int updateCount = rasCleanupDao.pushDisconnectionRequest(dc.getMsisdn(), dc.getContractId());
						
						if(updateCount > 0) {
							dc.setRequestStatus(true);
							dc.setRemarks("Request posted successfully");
						} else {
							dc.setRequestStatus(false);
							dc.setRemarks("Request could not be posted successfully");
						}
					} catch (ApplicationException e) {
						dc.setRequestStatus(false);
						dc.setRemarks(e.getErrorMessage());
					}
				}
				
				li.set(dc);
			}
		}
		
		return dcOrderList;
	}
}
