package com.ooredoo.nc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import com.ooredoo.nc.dto.BSCSProfileDetails;
import com.ooredoo.nc.dto.CleanupDetails;
import com.ooredoo.nc.dto.NodalCleanupDetails;
import com.ooredoo.nc.dto.SubscriberProfileCleanupRequest;
import com.ooredoo.nc.dto.SubscriberProfileCleanupResponse;
import com.ooredoo.nc.dto.SubscriberProfileDetails;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.utility.ApplicationConstants;
import com.ooredoo.wsclient.WSClient;
import com.ooredoo.wsclient.downstreamprofilecheck.DownStreamProfileCheckRequest;
import com.ooredoo.wsclient.downstreamprofilecheck.DownStreamProfileCheckResponse;
import com.ooredoo.wsclient.downstreamprofilecheck.DownStreamProfileCheckResponse.BscsProfile;
import com.ooredoo.wsclient.downstreamprofilecheck.DownStreamProfileCheckResponse.CsProfile;
import com.ooredoo.wsclient.downstreamprofilecheck.DownStreamProfileCheckResponse.EsmProfile;
import com.ooredoo.wsclient.downstreamprofilecheck.DownStreamProfileCheckResponse.HLRProfile;
import com.ooredoo.wsclient.downstreamprofilecheck.DownStreamProfileCheckResponse.NmsProfile;
import com.ooredoo.wsclient.downstreamprofilecheck.DownStreamProfileCheckResponse.PcrfProfile;
import com.ooredoo.wsclient.downstreamprofilecheck.DownStreamProfileCheckResponse.RasProfile;
import com.ooredoo.wsclient.downstreamprofilecheck.DownStreamProfileCheckResponse.SPRProfile;

@Service
public class ProfileCleanupService implements ApplicationConstants, ProfileCleanupServiceI {
	
	@Autowired
	private ESMCleanupService esmCleanupService;
	
	@Autowired
	private PCRFCleanupService pcrfCleanupService;
	
	@Autowired
	private CSCleanupService csCleanupService;
	
	@Autowired
	private SPRCleanupService sprCleanupService;
	
	@Autowired
	private HLRCleanupService hlrCleanupService;
	
	@Autowired
	private BSCSCleanupService bscsCleanupService;
	
	@Autowired
	private RASCleanupService rasCleanupService;
	
	@Autowired
	private NMSCleanupService nmsCleanupService;
	
	@Autowired
	private ERPCleanupService erpCleanupService;
	
	@Autowired
	private CleanupHistoryService cleanupHistoryService;
	
	@Autowired
	private WSClient wsclient;
	
	@Autowired
	private ReloadableResourceBundleMessageSource externalConfig;
	private static final String 	DOWNSTREAM_API_INVALID_STATUS = "No Status Received from DownstreamStatusCheck API";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProfileCleanupService.class);

	public SubscriberProfileCleanupResponse handleCleanup(SubscriberProfileCleanupRequest req, boolean validate) {
		CleanupDetails cleanupDetails = null;
		NodalCleanupDetails details = null;
		List<NodalCleanupDetails> nodalCleanupDetails = null;
		Map<String, CleanupDetails> completeStatus = new HashMap<String, CleanupDetails>();
		SubscriberProfileCleanupResponse resp = new SubscriberProfileCleanupResponse();
		
		resp.setRequestUsername(req.getRequestUsername());
		resp.setRequestDate(req.getRequestDate());
		resp.setResponseDate(new Date());
		
		for(SubscriberProfileDetails profile : req.getProfiles()) {
			boolean validateCleanupWithDownstream = true;
			try {
				validateProfile(profile, validate);
				
				LOGGER.info("Cleaning up profile : {}", profile.toString());
				
				cleanupDetails = new CleanupDetails();
				nodalCleanupDetails = new ArrayList<NodalCleanupDetails>();
				
				for(String node : req.getNodes()) {
					LOGGER.info("Cleaning up profile from node: {}", node);
					
					if(node.equals(ESM)) {
						details = esmCleanupService.deleteEsmProfile(profile.getMsisdn());
						nodalCleanupDetails.add(details);
						
					} else if(node.equals(PCRF)) {
						details = pcrfCleanupService.deletePcrfProfile(profile.getMsisdn());
						nodalCleanupDetails.add(details);
						
					} else if(node.equals(HLR)) {
						details = hlrCleanupService.deleteSubscriberFromHlr(profile.getMsisdn(), profile.getImsi());
						nodalCleanupDetails.add(details);
						
					} else if(node.equals(BSCS)) {
						details = bscsCleanupService.deleteSubscriberFromBscs(profile);
						nodalCleanupDetails.add(details);
						
					} else if(node.equals(CS)) {
						details = csCleanupService.deleteCsProfile(profile.getMsisdn());
						nodalCleanupDetails.add(details);
						
					} else if(node.equals(SPR)) {
						details = sprCleanupService.deleteSubscriberFromSpr(profile.getMsisdn());
						nodalCleanupDetails.add(details);
						
					} else if(node.equals(RAS)) {
						details = rasCleanupService.deleteSubscriberFromRas(profile);
						nodalCleanupDetails.add(details);
						
					} else if(node.equals(ERP)) {
						LOGGER.info("ERP profile.getSelectedErpLocation()--------> {}", profile.getSelectedErpLocation());
						details = erpCleanupService.deleteSubscriberFromErp(profile);
						nodalCleanupDetails.add(details);
						
					} else if(node.equals(NMS)) {
						details = nmsCleanupService.deleteSubscriberFromNms(profile);
						nodalCleanupDetails.add(details);
						
					}
					
					cleanupDetails.setCleanupDetails(nodalCleanupDetails);
					completeStatus.put(profile.getMsisdn(), cleanupDetails);
				}
			} catch (ApplicationException e) {
				LOGGER.error("Error occurred while cleaning up subscriber profile. Cause :", e);
				validateCleanupWithDownstream = false;
				cleanupDetails = new CleanupDetails();
				nodalCleanupDetails = new ArrayList<NodalCleanupDetails>();
				
				for(String node : req.getNodes()) {
					
					if(node.equals(ESM)) {
						details = new NodalCleanupDetails();
						details.setNode(ESM);
						details.setStatus(SKIPPED_LEBEL);
						details.setResponse(e.getErrorMessage());
						nodalCleanupDetails.add(details);
						
					} else if(node.equals(PCRF)) {
						details = new NodalCleanupDetails();
						details.setNode(PCRF);
						details.setStatus(SKIPPED_LEBEL);
						details.setResponse(e.getErrorMessage());
						nodalCleanupDetails.add(details);
						
					} else if(node.equals(HLR)) {
						details = new NodalCleanupDetails();
						details.setNode(HLR);
						details.setStatus(SKIPPED_LEBEL);
						details.setResponse(e.getErrorMessage());
						nodalCleanupDetails.add(details);
						
					} else if(node.equals(BSCS)) {
						details = new NodalCleanupDetails();
						details.setNode(BSCS);
						details.setStatus(SKIPPED_LEBEL);
						details.setResponse(e.getErrorMessage());
						nodalCleanupDetails.add(details);
						
					} else if(node.equals(CS)) {
						details = new NodalCleanupDetails();
						details.setNode(CS);
						details.setStatus(SKIPPED_LEBEL);
						details.setResponse(e.getErrorMessage());
						nodalCleanupDetails.add(details);
						
					} else if(node.equals(SPR)) {
						details = new NodalCleanupDetails();
						details.setNode(SPR);
						details.setStatus(SKIPPED_LEBEL);
						details.setResponse(e.getErrorMessage());
						nodalCleanupDetails.add(details);
						
					} else if(node.equals(RAS)) {
						details = new NodalCleanupDetails();
						details.setNode(RAS);
						details.setStatus(SKIPPED_LEBEL);
						details.setResponse(e.getErrorMessage());
						nodalCleanupDetails.add(details);
						
					} else if(node.equals(ERP)) {
						details = new NodalCleanupDetails();
						details.setNode(ERP);
						details.setStatus(SKIPPED_LEBEL);
						details.setResponse(e.getErrorMessage());
						nodalCleanupDetails.add(details);
						
					} else if(node.equals(NMS)) {
						details = new NodalCleanupDetails();
						details.setNode(NMS);
						details.setStatus(SKIPPED_LEBEL);
						details.setResponse(e.getErrorMessage());
						nodalCleanupDetails.add(details);
						
					}
					
					cleanupDetails.setCleanupDetails(nodalCleanupDetails);
					completeStatus.put(profile.getMsisdn(), cleanupDetails);
				}
			}
			
			String validateCleanup = externalConfig.getMessage("profile.cleanup.validate.downstream", null, "Y", Locale.getDefault());
			
			if(validateCleanupWithDownstream && validateCleanup.equals("Y")) {
				DownStreamProfileCheckRequest downStreamProfileCheckRequest = new DownStreamProfileCheckRequest();
				downStreamProfileCheckRequest.setChannel("CleanupTool");
				downStreamProfileCheckRequest.setMsisdn(profile.getMsisdn());
				downStreamProfileCheckRequest.setSimSerial(profile.getSimNo());
				downStreamProfileCheckRequest.setTransactionId("1234567890");
				
				String profileCheckServiceUrl = externalConfig.getMessage("service.profile.downstream-status.url", null, Locale.getDefault());
				String profileCheckServiceContext = externalConfig.getMessage("service.profile.downstream-status.context", null, Locale.getDefault());
				
				DownStreamProfileCheckResponse pResp = null;
				
				try {
					pResp = wsclient.downStreamProfileCheck(profileCheckServiceUrl, downStreamProfileCheckRequest, profileCheckServiceContext);
				
					ListIterator<NodalCleanupDetails> li =  nodalCleanupDetails.listIterator();
					
					while(li.hasNext()) {
						NodalCleanupDetails ncd = li.next();
						
						if(ncd.getNode().equals(BSCS)) {
							BscsProfile bProfile = pResp.getBscsProfile();
							if(null != bProfile && (null == bProfile.getContractID() || bProfile.getContractID().isEmpty())) {
								ncd.setStatus(SUCCESS_LEBEL);
								ncd.setResponse("OK");
								
							} else {
								ncd.setStatus(ERROR_LABEL);
								ncd.setResponse((null != bProfile && null != bProfile.getContractID() && !bProfile.getContractID().isEmpty() ? "Profile Exists with Contract Id-" + bProfile.getContractID() : DOWNSTREAM_API_INVALID_STATUS));
							}
						} else if(ncd.getNode().equals(CS)) {
							CsProfile csProfile = pResp.getCsProfile();
							if(null != csProfile && (null == csProfile.getStatus() ||  csProfile.getStatus().equals("102"))) {
								ncd.setStatus(SUCCESS_LEBEL);
								ncd.setResponse("OK");
							} else {
								ncd.setStatus(ERROR_LABEL);
								ncd.setResponse((null != csProfile && null != csProfile.getStatus() && !csProfile.getStatus().isEmpty() ? "Profile Status -" + csProfile.getStatus() : DOWNSTREAM_API_INVALID_STATUS));
							}
						} else if(ncd.getNode().equals(RAS)) {
							RasProfile rasProfile = pResp.getRasProfile();
							if(null != rasProfile && (null == rasProfile.getCustomerNumber() || rasProfile.getCustomerNumber().isEmpty())) {
								ncd.setStatus(SUCCESS_LEBEL);
								ncd.setResponse("OK");
								
							} else {
								ncd.setStatus(ERROR_LABEL);
								ncd.setResponse((null != rasProfile && null !=rasProfile.getCustomerNumber() && !rasProfile.getCustomerNumber().isEmpty() ? "Profile Exists with Customer Number-" + rasProfile.getCustomerNumber() : DOWNSTREAM_API_INVALID_STATUS));
							}
						} else if(ncd.getNode().equals(SPR)) {
							SPRProfile sprProfile = pResp.getSPRProfile();
							if(null != sprProfile && (null == sprProfile.getSubscriberType() || sprProfile.getSubscriberType().isEmpty())) {
								ncd.setStatus(SUCCESS_LEBEL);
								ncd.setResponse("OK");
								
							} else {
								ncd.setStatus(ERROR_LABEL);
								ncd.setResponse((null != sprProfile && null != sprProfile.getSubscriberType() && !sprProfile.getSubscriberType().isEmpty() ? "Profile Exists with Subscriber Type-" + sprProfile.getSubscriberType() : DOWNSTREAM_API_INVALID_STATUS));
							}
						} else if(ncd.getNode().equals(HLR)) {
							HLRProfile hlrProfile = pResp.getHLRProfile();
							if(null != hlrProfile && null != hlrProfile.getErrorCode() && !hlrProfile.getErrorCode().equals("0")) {
								ncd.setStatus(SUCCESS_LEBEL);
								ncd.setResponse("OK");
								
							} else {
								ncd.setStatus(ERROR_LABEL);
								ncd.setResponse((null != hlrProfile && null != hlrProfile.getErrorMessage() && !hlrProfile.getErrorMessage().isEmpty() ? "Profile Check Response-" + hlrProfile.getErrorMessage() : DOWNSTREAM_API_INVALID_STATUS));
							}
						} else if(ncd.getNode().equals(ESM)) {
							EsmProfile esmProfile = pResp.getEsmProfile();
							if(null != esmProfile && null != esmProfile.getErrorCode() && !esmProfile.getErrorCode().equals("0")) {
								ncd.setStatus(SUCCESS_LEBEL);
								ncd.setResponse("OK");
								
							} else {
								ncd.setStatus(ERROR_LABEL);
								ncd.setResponse((null != esmProfile && null != esmProfile.getErrorMessage() && !esmProfile.getErrorMessage().isEmpty() ? "Profile Check Response-" + esmProfile.getErrorMessage() : DOWNSTREAM_API_INVALID_STATUS));
							}
						} else if(ncd.getNode().equals(NMS)) {
							NmsProfile nmsProfile = pResp.getNmsProfile();
							
							if(null != nmsProfile) {
								if(null != nmsProfile.getStatus() && nmsProfile.getStatus().equalsIgnoreCase("A")) {
									ncd.setStatus(SUCCESS_LEBEL);
									ncd.setResponse("OK");
								} else {
									ncd.setStatus(SUCCESS_LEBEL);
									ncd.setResponse("OK");
								}
							} else {
								ncd.setStatus(ERROR_LABEL);
								ncd.setResponse(DOWNSTREAM_API_INVALID_STATUS);
							}
							/*
							 if(null != nmsProfile && null != nmsProfile.getStatus() && nmsProfile.getStatus().equalsIgnoreCase("A")) {
								ncd.setStatus(SUCCESS_LEBEL);
								ncd.setResponse("OK");
								
							} else {
								ncd.setStatus(ERROR_LABEL);
								ncd.setResponse((null != nmsProfile && null != nmsProfile.getStatus() && !nmsProfile.getStatus().isEmpty() ? "Profile Check Response-" + nmsProfile.getStatus() : DOWNSTREAM_API_INVALID_STATUS));
							}
							*/
						} else if(ncd.getNode().equals(PCRF)) {
							PcrfProfile pcrfProfile = pResp.getPcrfProfile();
							if(null != pcrfProfile && null != pcrfProfile.getStatus() && pcrfProfile.getStatus().equals("Complete")) {
								ncd.setStatus(SUCCESS_LEBEL);
								ncd.setResponse("OK");
								
							} else {
								ncd.setStatus(ERROR_LABEL);
								ncd.setResponse((null != pcrfProfile && null != pcrfProfile.getStatus() && !pcrfProfile.getStatus().isEmpty() ? "Profile Check Response-" + pcrfProfile.getStatus() : DOWNSTREAM_API_INVALID_STATUS));
							}
						}
						
						li.set(ncd);
					}
				} catch (ApplicationException e) {
					LOGGER.error("Error occurred while extracting status from downstream. Cause-", e);
				}
			}			
		}
		
		resp.setCleanupDetails(completeStatus);
		resp.setProfiles(req.getProfiles());
		cleanupHistoryService.createHistory(resp);
		
		return resp;
	}

	@Override
	public void validateProfile(SubscriberProfileDetails profile, boolean validate) throws ApplicationException {
		if(validate) {
			LOGGER.info("Validating subscriber profile - {}", profile.toString());
			BSCSProfileDetails p = bscsCleanupService.getProfileValidationDetails(profile.getMsisdn());
			
			if(null == p.getImsi() || p.getImsi().isEmpty()) {
				LOGGER.info("NO IMSI found associated with MSISDN-{} in BSCS. Considering Subscriber profile - {} validated successfully", profile.getMsisdn(), profile.toString());
			} else {
				if(!p.getImsi().equals(profile.getImsi())) {
					throw new ApplicationException("Invalid IMSI. Actual IMSI in BSCS-" + p.getImsi(), true);
				}
			}
			
			if(null == p.getSimNo() || p.getSimNo().isEmpty()) {
				LOGGER.info("NO SIM found associated with MSISDN-{} in BSCS. Considering Subscriber profile - {} validated successfully", profile.getMsisdn(), profile.toString());
			} else {
				if(!p.getSimNo().equals(profile.getSimNo())) {
					throw new ApplicationException("Invalid SIM No. Actual SIM No in BSCS-" + p.getSimNo(), true);
				}
			}
		} else {
			LOGGER.info("Skipping subscriber profile validation - {}", profile.toString());
		}
	}
}
