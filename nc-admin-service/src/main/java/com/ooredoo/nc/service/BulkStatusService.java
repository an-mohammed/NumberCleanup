package com.ooredoo.nc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import com.ooredoo.nc.dto.ProfileStatus;
import com.ooredoo.nc.dto.StatusRequest;
import com.ooredoo.nc.dto.StatusResponse;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.utility.ApplicationConstants;
import com.ooredoo.wsclient.WSClient;
import com.ooredoo.wsclient.downstreamprofilecheck.DownStreamProfileCheckRequest;
import com.ooredoo.wsclient.downstreamprofilecheck.DownStreamProfileCheckResponse;

@Service
public class BulkStatusService implements ApplicationConstants  {
	
	@Autowired
	private ReloadableResourceBundleMessageSource externalConfig;
	
	@Autowired
	private WSClient wsclient; 
	private static final Logger LOGGER = LoggerFactory.getLogger(BulkStatusService.class);

	public StatusResponse getProfileStatus(StatusRequest req) throws ApplicationException {
		StatusResponse resp = new StatusResponse();
		
		
		
		if(null != req && null != req.getProfileStatus() && !req.getProfileStatus().isEmpty()) {
			for(ProfileStatus ps : req.getProfileStatus()) {
				LOGGER.info("Extracting profile status for -{}", ps.getMsisdn() + "#" + ps.getSimNo());
				try {
					DownStreamProfileCheckRequest downStreamProfileCheckRequest = new DownStreamProfileCheckRequest();
					downStreamProfileCheckRequest.setChannel("CleanupTool");
					downStreamProfileCheckRequest.setMsisdn(ps.getMsisdn());
					downStreamProfileCheckRequest.setSimSerial(ps.getSimNo());
					downStreamProfileCheckRequest.setTransactionId("1234567890");
					
					String profileCheckServiceUrl = externalConfig.getMessage("service.profile.downstream-status.url", null, Locale.getDefault());
					String profileCheckServiceContext = externalConfig.getMessage("service.profile.downstream-status.context", null, Locale.getDefault());
					
					DownStreamProfileCheckResponse pResp = wsclient.downStreamProfileCheck(profileCheckServiceUrl, downStreamProfileCheckRequest, profileCheckServiceContext);
					
					if(null != pResp) {
						
						if(null != pResp.getBscsProfile() && null != pResp.getBscsProfile().getContractID() && !pResp.getBscsProfile().getContractID().isEmpty()) {
							ps.setBscsProfileStatus("Contract Id : " + pResp.getBscsProfile().getContractID());
						} else {
							ps.setBscsProfileStatus("Not Found");
						}
						
						if(null != pResp.getCsProfile() && null != pResp.getCsProfile().getServiceClass() && !pResp.getCsProfile().getServiceClass().isEmpty()) {
							ps.setCsProfileStatus("Service Class : " + pResp.getCsProfile().getServiceClass());
						} else {
							ps.setCsProfileStatus("Not Found");
						}
						
						if(null != pResp.getErpProfile() && null != pResp.getErpProfile().getItemNumber() && !pResp.getErpProfile().getItemNumber().isEmpty()) {
							ps.setErpProfileStatus("Item No : " + pResp.getErpProfile().getItemNumber());
						} else {
							ps.setErpProfileStatus("Not Found");
						}
						
						if(null != pResp.getEsmProfile() && null != pResp.getEsmProfile().getErrorCode() && !pResp.getEsmProfile().getErrorCode().equals("0")) {
							ps.setEsmProfileStatus("Not Found");
						} else {
							ps.setEsmProfileStatus(pResp.getEsmProfile().getErrorMessage());
						}
						
						if(null != pResp.getHLRProfile() && null != pResp.getHLRProfile().getErrorCode() && !pResp.getHLRProfile().getErrorCode().equals("0")) {
							ps.setHlrProfileStatus("Not Found");
						} else {
							ps.setHlrProfileStatus(pResp.getHLRProfile().getErrorMessage());
						}
						//TODO
						if(null != pResp.getNmsProfile() && null != pResp.getNmsProfile().getStatus() && pResp.getNmsProfile().getStatus().equals("Complete")) {
							ps.setNmsProfileStatus("Not Found");
						} else {
							ps.setNmsProfileStatus("Status - " + pResp.getNmsProfile().getStatus());
						}
						//TODO
						if(null != pResp.getPcrfProfile() && null != pResp.getPcrfProfile().getStatus() && pResp.getPcrfProfile().getStatus().equals("Complete")) {
							ps.setPcrfProfileStatus("Not Found");
						} else {
							ps.setPcrfProfileStatus("Status - " + pResp.getPcrfProfile().getStatus());
						}
						
						if(null != pResp.getRasProfile() && (null == pResp.getRasProfile().getCustomerNumber() || pResp.getRasProfile().getCustomerNumber().isEmpty())) {
							ps.setRasProfileStatus("Not Found");
						} else {
							ps.setRasProfileStatus("Customer No. - " + pResp.getRasProfile().getCustomerNumber());
						}
						
						if(null != pResp.getSPRProfile() && (null == pResp.getSPRProfile().getSubscriberType() || pResp.getSPRProfile().getSubscriberType().isEmpty())) {
							ps.setSprProfileStatus("Not Found");
						} else {
							ps.setSprProfileStatus("Subs. Type - " + pResp.getSPRProfile().getSubscriberType());
						}
						
						ps.setErrorCode("500");
						ps.setErrorMessage("OK");
					} else {
						throw new ApplicationException("No profile status response received", true);
					}
				} catch(Exception e) {
					ps.setErrorCode("500");
					ps.setErrorMessage(e.getMessage());
				}
				
				if(null != resp.getProfileStatus()) {
					resp.getProfileStatus().add(ps);
				} else {
					List<ProfileStatus> psList = new ArrayList<ProfileStatus>();
					psList.add(ps);
					resp.setProfileStatus(psList);
				}
			}
			
			resp.setErrorCode("0");
			resp.setErrorMessage("OK");
			
		} else {
			resp.setErrorCode("500");
			resp.setErrorMessage("No profile found in the request");
		}
		
		return resp;
	}
}
