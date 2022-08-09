package com.ooredoo.nc.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ooredoo.nc.dto.BSCSProfileDetails;
import com.ooredoo.nc.dto.DisconnectionOrderDetails;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.utility.ApplicationConstants;

@Service
public class DisconnectionService implements ApplicationConstants  {
	
	@Autowired
	private BSCSCleanupService bscsCleanupService;
	
	@Autowired
	private RASCleanupService rasCleanupService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DisconnectionService.class);

	public List<DisconnectionOrderDetails> createDisconnectionRequest(List<String> msisdnList) throws ApplicationException {
		List<DisconnectionOrderDetails> dcOrderReqList = null;
		List<DisconnectionOrderDetails> dcOrderRespList = null;
		
		if(null != msisdnList && !msisdnList.isEmpty()) {
			dcOrderReqList = new ArrayList<DisconnectionOrderDetails>();
			DisconnectionOrderDetails dcOrder = null;
			
			for(String msisdn : msisdnList) {
				dcOrder = new DisconnectionOrderDetails();
				dcOrder.setMsisdn(msisdn);
				dcOrder.setRequestStatus(false);
				
				LOGGER.info("Extracting BSCS Profile for MSISDN - {}", msisdn);
				
				try {
					BSCSProfileDetails bscsProfile = bscsCleanupService.getBscsProfile(msisdn);
					
					if(null != bscsProfile) {
						Long contractId = bscsProfile.getContractId();
						
						if(null != contractId) {
							LOGGER.info("Found BSCS contract Id - {}", contractId);
							dcOrder.setContractId(contractId);
							dcOrder.setRequestStatus(true);
							
						} else {
							dcOrder.setRemarks("Record will be skipped as No Contract-Id found");
						}
					} else {
						dcOrder.setRemarks("Record will be skipped as Unable to extract BSCS profile to extract Contract Id");
					}
				} catch(ApplicationException ae) {
					dcOrder.setRemarks(ae.getErrorMessage());
				}
				
				dcOrderReqList.add(dcOrder);
			}
			
			dcOrderRespList = rasCleanupService.pushDisconnectionRequest(dcOrderReqList);
		}
		
		return dcOrderRespList;
	}
}
