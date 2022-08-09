package com.ooredoo.nc.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ooredoo.nc.dto.CleanupDetails;
import com.ooredoo.nc.dto.NodalCleanupDetails;
import com.ooredoo.nc.dto.SubscriberProfileCleanupResponse;
import com.ooredoo.nc.dto.SubscriberProfileDetails;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.model.CleanupLogMaster;
import com.ooredoo.nc.repo.CompositeRepository;
import com.ooredoo.nc.utility.ApplicationConstants;

@Service
public class CleanupHistoryService implements ApplicationConstants {
	
	@Autowired
	private CompositeRepository compositeRepo;
	private static final Logger LOGGER = LoggerFactory.getLogger(CleanupHistoryService.class);
	
	public void createHistory(SubscriberProfileCleanupResponse cleanUpDetails) {
		
		if(null != cleanUpDetails) {
			CleanupLogMaster logMaster = null;
			
			for(SubscriberProfileDetails prof : cleanUpDetails.getProfiles()) {
				logMaster = new CleanupLogMaster();
				logMaster.setMsisdn(prof.getMsisdn());
				logMaster.setImsi(prof.getImsi());
				logMaster.setSimNo(prof.getSimNo());
				logMaster.setNmsPool(prof.getSelecedNmsPool());
				logMaster.setNmsPrice(String.valueOf(prof.getPrice()));
				logMaster.setErpLoc(prof.getSelectedErpLocation());
				logMaster.setExecUser(cleanUpDetails.getRequestUsername());
				
				CleanupDetails det = cleanUpDetails.getCleanupDetails().get(prof.getMsisdn());

				for(NodalCleanupDetails ncd : det.getCleanupDetails()) {
					if(ncd.getNode().equals(CS)) {
						LOGGER.info(CS + COLON + ncd.getResponse());
						logMaster.setCs((null != ncd.getStatus() && !ncd.getStatus().isEmpty() ? ncd.getStatus() : BLANK)  
								+ (null != ncd.getResponse() && !ncd.getResponse().isEmpty() ? (COLON + ncd.getResponse()) : BLANK));
						
					} else if(ncd.getNode().equals(ESM)) {
						LOGGER.info(ESM + COLON + ncd.getResponse());
						logMaster.setEsm((null != ncd.getStatus() && !ncd.getStatus().isEmpty() ? ncd.getStatus() : BLANK)  
								+ (null != ncd.getResponse() && !ncd.getResponse().isEmpty() ? (COLON + ncd.getResponse()) : BLANK));
						
					} else if(ncd.getNode().equals(BSCS)) {
						LOGGER.info(BSCS +COLON + ncd.getResponse());
						logMaster.setBscs((null != ncd.getStatus() && !ncd.getStatus().isEmpty() ? ncd.getStatus() : BLANK)  
								+ (null != ncd.getResponse() && !ncd.getResponse().isEmpty() ? (COLON + ncd.getResponse()) : BLANK));
						
					} else if(ncd.getNode().equals(ERP)) {
						LOGGER.info(ERP + COLON + ncd.getResponse());
						logMaster.setErp((null != ncd.getStatus() && !ncd.getStatus().isEmpty() ? ncd.getStatus() : BLANK)  
								+ (null != ncd.getResponse() && !ncd.getResponse().isEmpty() ? (COLON + ncd.getResponse()) : BLANK));
						
					} else if(ncd.getNode().equals(RAS)) {
						LOGGER.info(RAS + COLON + ncd.getResponse());
						logMaster.setRas((null != ncd.getStatus() && !ncd.getStatus().isEmpty() ? ncd.getStatus() : BLANK)  
								+ (null != ncd.getResponse() && !ncd.getResponse().isEmpty() ? (COLON + ncd.getResponse()) : BLANK));
						
					} else if(ncd.getNode().equals(SPR)) {
						LOGGER.info(SPR + COLON + ncd.getResponse());
						logMaster.setSpr((null != ncd.getStatus() && !ncd.getStatus().isEmpty() ? ncd.getStatus() : BLANK)  
								+ (null != ncd.getResponse() && !ncd.getResponse().isEmpty() ? (COLON + ncd.getResponse()) : BLANK));
						
					} else if(ncd.getNode().equals(NMS)) {
						LOGGER.info(NMS + COLON + ncd.getResponse());
						logMaster.setNms((null != ncd.getStatus() && !ncd.getStatus().isEmpty() ? ncd.getStatus() : BLANK)  
								+ (null != ncd.getResponse() && !ncd.getResponse().isEmpty() ? (COLON + ncd.getResponse()) : BLANK));
						
					} else if(ncd.getNode().equals(PCRF)) {
						LOGGER.info(PCRF + COLON + ncd.getResponse());
						logMaster.setPcrf((null != ncd.getStatus() && !ncd.getStatus().isEmpty() ? ncd.getStatus() : BLANK)  
								+ (null != ncd.getResponse() && !ncd.getResponse().isEmpty() ? (COLON + ncd.getResponse()) : BLANK));
						
					} else if(ncd.getNode().equals(HLR)) {
						LOGGER.info(HLR + COLON + ncd.getResponse());
						logMaster.setHlr((null != ncd.getStatus() && !ncd.getStatus().isEmpty() ? ncd.getStatus() : BLANK)  
								+ (null != ncd.getResponse() && !ncd.getResponse().isEmpty() ? (COLON + ncd.getResponse()) : BLANK));
						
					}
					
					logMaster.setExecDate(new Date());
				}
				
				compositeRepo.getHistoryRepo().save(logMaster);
			}
		}
	}
	
	public List<CleanupLogMaster> searchHist(String msisdn) throws ApplicationException {
		return compositeRepo.getHistoryRepo().findAllByMsisdnWithLimit(msisdn);
	}
}
