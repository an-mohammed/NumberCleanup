package com.ooredoo.nc.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ooredoo.nc.dao.BSCSCleanupDao;
import com.ooredoo.nc.dto.ActiveSubscription;
import com.ooredoo.nc.dto.BSCSProfileDetails;
import com.ooredoo.nc.dto.NodalCleanupDetails;
import com.ooredoo.nc.dto.Occ;
import com.ooredoo.nc.dto.PendingRequest;
import com.ooredoo.nc.dto.SubscriberProfileDetails;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.utility.ApplicationConstants;

@Service
public class BSCSCleanupService implements ApplicationConstants {

	@Autowired
	private BSCSCleanupDao bscsCleanupDao;
		
	private static final Logger LOGGER = LoggerFactory.getLogger(BSCSCleanupService.class);
	
	/**
	 * 
	 * @param profile
	 * @return
	 */
	public NodalCleanupDetails deleteSubscriberFromBscs(SubscriberProfileDetails profile) {
		NodalCleanupDetails cleanupDetails = null;
		
		try {
			cleanupDetails = new NodalCleanupDetails();
			cleanupDetails.setNode(BSCS);
			cleanupDetails.setExecStart(new Date());
			
			String currentImsi = bscsCleanupDao.getImsiFromSimNo(profile.getSimNo());
			
			if(null != currentImsi && currentImsi.equals(profile.getImsi())) {
				bscsCleanupDao.executeCleanupQueries(profile.getMsisdn(), profile.getImsi(), profile.getSimNo());
				cleanupDetails.setStatus(SUCCESS_LEBEL);
				cleanupDetails.setResponse("OK");
				
			} else {
				throw new ApplicationException("Provided IMSI not matching with existing IMSI", true);
			}
		} catch (ApplicationException ae) {
			cleanupDetails.setStatus(ERROR_LABEL);
			cleanupDetails.setResponse(ae.getErrorMessage());
			
			LOGGER.error("Error occurred while invoking service. Cause :", ae);
		}

		cleanupDetails.setExecEnd(new Date());
		return cleanupDetails;
	}
	
	/**
	 * 
	 * @param msisdn
	 * @return
	 * @throws ApplicationException
	 */
	public BSCSProfileDetails extractBscsProfile(String msisdn) throws ApplicationException {
		BSCSProfileDetails profile = null;
		
		try {
			List<Map<String, Object>>rs = bscsCleanupDao.getBscsProfile(msisdn);
			
			if(null != rs && !rs.isEmpty()) {
				if(rs.size() > 1) {
					throw new ApplicationException("More than one row found.");
				}
				
				Map<String, Object> prof = rs.get(0);
				
				if(null != prof && !prof.isEmpty()) {
					profile = new BSCSProfileDetails();
					
					Long customerId = null != prof.get("CUSTOMER_ID") ? ((BigDecimal) prof.get("CUSTOMER_ID")).longValue() : null;
					Long contractId = null != prof.get("CO_ID") ? ((BigDecimal) prof.get("CO_ID")).longValue() : null;
					
					profile.setCustomerId(customerId);
					profile.setContractId(contractId);
					profile.setCustCode(null != prof.get("CUSTCODE") ? (String) prof.get("CUSTCODE") : null);
					profile.setCustomerStatus(null != prof.get("CSTYPE") ? (String) prof.get("CSTYPE") : null);
					profile.setContractStatus(null != prof.get("CH_STATUS") ? (String) prof.get("CH_STATUS") : null);
					profile.setCoInstalled(null != prof.get("CO_INSTALLED") ? (Date) prof.get("CO_INSTALLED") : null);
					profile.setCsActivated(null != prof.get("CSACTIVATED") ? (Date) prof.get("CSACTIVATED") : null);
					profile.setCsCurbalance(null != prof.get("CSCURBALANCE") ? ((BigDecimal) prof.get("CSCURBALANCE")).doubleValue() : null);
					profile.setLang(null != prof.get("LNG_DES") ? (String) prof.get("LNG_DES") : null);
					profile.setLbcDate(null != prof.get("LBC_DATE") ? (Date) prof.get("LBC_DATE") : null);
					profile.setPaymentResp(null != prof.get("PAYMNTRESP") ? (String) prof.get("PAYMNTRESP") : null);
					profile.setRatePlanCode(null != prof.get("TMCODE") ? ((BigDecimal) prof.get("TMCODE")).longValue() : null);
					profile.setRateplanDes(null != prof.get("DES") ? (String) prof.get("DES") : null);
					profile.setSncode(null != prof.get("SNCODE") ? ((BigDecimal) prof.get("SNCODE")).longValue() : null);
					
					if(null != customerId && null != contractId) {
						Occ occ = null;
						PendingRequest pReq = null;
						ActiveSubscription aSubs = null;
						
						List<Map<String, Object>>occRs = bscsCleanupDao.getBscsOcc(customerId, contractId);
						
						if(null != occRs && !occRs.isEmpty()) {
							for(Map<String, Object> o : occRs) {
								occ = new Occ();
								occ.setAmount(null != o.get("AMOUNT") ? ((BigDecimal) o.get("AMOUNT")).doubleValue() : null);
								occ.setCreatedDate(null != o.get("ENTDATE") ? (Date) o.get("ENTDATE") : null);
								occ.setDescription(null != o.get("REMARK") ? (String) o.get("CUREMARKSTCODE") : null);
								occ.setSnCode(null != o.get("SNCODE") ? ((BigDecimal) o.get("SNCODE")).longValue() : null);
								
								profile.getOccs().add(occ);
							}
						}
						
						
						List<Map<String, Object>>pendingReqRs = bscsCleanupDao.getBscsPendingReq(customerId, contractId);
						
						if(null != pendingReqRs && !pendingReqRs.isEmpty()) {
							for(Map<String, Object> o : pendingReqRs) {
								pReq = new PendingRequest();
								pReq.setRequestId(null != o.get("REQUEST") ? ((BigDecimal) o.get("REQUEST")).longValue() : null);
								pReq.setActionId(null != o.get("ACTION_ID") ? ((BigDecimal) o.get("ACTION_ID")).longValue() : null);
								pReq.setActionDesc(null != o.get("ACTION_DES") ? (String) o.get("ACTION_DES") : null);
								pReq.setStatus(null != o.get("STATUS") ? ((BigDecimal) o.get("STATUS")).longValue() : null);
								
								profile.getpRequests().add(pReq);
							}
						}
						
						
						List<Map<String, Object>>subsRs = bscsCleanupDao.getBscsSubscription(customerId, contractId);
						
						if(null != subsRs && !subsRs.isEmpty()) {
							for(Map<String, Object> o : subsRs) {
								aSubs = new ActiveSubscription();
								aSubs.setDesc(null != o.get("DESCRIPTION") ? (String) o.get("DESCRIPTION") : null);
								aSubs.setAccessFee(null != o.get("ACCESS_FEE") ? ((BigDecimal) o.get("ACCESS_FEE")).doubleValue() : null);
								aSubs.setSnCode(null != o.get("SNCODE") ? ((BigDecimal) o.get("SNCODE")).longValue() : null);
								
								profile.getActiveSubs().add(aSubs);
							}
						}
					}
				} else {
					throw new ApplicationException("No BSCS record found", true);
				}
			} else {
				throw new ApplicationException("No BSCS record found", true);
			}
		} catch(Exception e) {
			LOGGER.error("Error occurred while extracting BSCS profile. Cause : ", e);
			
			throw new ApplicationException(e.getMessage(), true);
		}
		
		return profile;
	}

	public BSCSProfileDetails extractBscsProfileForValidation(String msisdn) throws ApplicationException {
		BSCSProfileDetails prof = new BSCSProfileDetails();
		
		try {
			List<Map<String, Object>>rs = bscsCleanupDao.getBscsProfileForValidation(msisdn);
			
			if(null != rs && !rs.isEmpty()) {
				if(rs.size() > 1) {
					throw new ApplicationException("More than one row found.");
				} else {
					prof.setImsi((String) rs.get(0).get("IMSI"));
					prof.setSimNo((String) rs.get(0).get("SIM_NUMBER"));
				}
			}
		}catch(Exception e) {
			LOGGER.error("Error occurred while extracting BSCS profile. Cause : ", e);
			
			throw new ApplicationException(e.getMessage(), true);
		}
		
		return prof;
	}
}
