package com.ooredoo.nc.service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import com.ooredoo.nc.dto.BSCSProfileDetails;
import com.ooredoo.nc.dto.CustomerProfile;
import com.ooredoo.nc.dto.ESMOfferDetails;
import com.ooredoo.nc.dto.ESMProfileDetails;
import com.ooredoo.nc.dto.HLRProfile;
import com.ooredoo.nc.dto.SubscriberProfileManagementResponse;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.model.NumberPool;
import com.ooredoo.nc.utility.ApplicationConstants;
import com.ooredoo.wsclient.WSClient;
import com.ooredoo.wsclient.downstreamprofilecheck.DownStreamProfileCheckRequest;
import com.ooredoo.wsclient.downstreamprofilecheck.DownStreamProfileCheckResponse;
import com.ooredoo.wsclient.getcustomerprofile.proxy.GetCustomerProfileResponse;
import com.ooredoo.wsclient.getcustomerprofile.proxy.META;
import com.ooredoo.wsclient.getcustomerprofile.proxy.MSISDNProfileType;

import kw.com.ooredoo.esmoffers.QuerySubscriptionResponseElement;
import kw.com.ooredoo.esmoffers.QuerySubscriptionResponseElement.OffersList.Offers;
import kw.com.ooredoo.esmoffers.QuerySubscriptionResponseElement.OffersList.Offers.MetaList;
import kw.com.ooredoo.esmoffers.QuerySubscriptionResponseElement.OffersList.Offers.OfferLanguageList.LanguageDetails;

@Service
public class ProfileManagementService implements ApplicationConstants {
	
	@Autowired
	private ESMCleanupService esmCleanupService;
	
	@Autowired
	private CSCleanupService csCleanupService;
		
	@Autowired
	private BSCSCleanupService bscsCleanupService;
	
	@Autowired
	private WSClient wsclient;
	
	@Autowired
	private NumberPoolService npService;
	
	@Autowired
	private ReloadableResourceBundleMessageSource externalConfig;
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProfileManagementService.class);

	public SubscriberProfileManagementResponse extractProfile(String msisdn) throws ApplicationException {
		SubscriberProfileManagementResponse profile = null;
		String simNo = null;
		String imsi = null;
		LOGGER.info("Extracting profile for MSISDN : {}", msisdn);
		
		profile = new SubscriberProfileManagementResponse();
		
		NumberPool np = npService.searchNumber(msisdn);
		
		if(null != np) {
			if(null != np.getSimNo() && !np.getSimNo().isEmpty()) {
				simNo = np.getSimNo();
			}
			
			if(null != np.getImsi() && !np.getImsi().isEmpty()) {
				imsi = np.getImsi();
			}
		} else {
			throw new ApplicationException("MSISDN not available in application number-pool", true);
		}
		
		try {
			BSCSProfileDetails bscsProfile = bscsCleanupService.getBscsProfile(msisdn);
			
			BSCSProfileDetails validationProfile = bscsCleanupService.getProfileValidationDetails(msisdn);
			
			if(null == validationProfile || null == validationProfile.getImsi() || validationProfile.getImsi().isEmpty()) {
				bscsProfile.setImsi(imsi + " * Unable to validate IMSI with BSCS as IMSI not found in BSCS");
			} else {
				if(validationProfile.getImsi().equals(imsi)) {
					bscsProfile.setImsi(imsi);
				} else {
					bscsProfile.setImsi(imsi + " * IMSI NOT matching with BSCS IMSI#" + validationProfile.getImsi() + ". Contact admin to update the profile");
				}
			}
			
			if(null == validationProfile || null == validationProfile.getSimNo() || validationProfile.getSimNo().isEmpty()) {
				bscsProfile.setSimNo(simNo + " * Unable to validate SIM Serial with BSCS as SIM not found in BSCS");
			} else {
				if(validationProfile.getSimNo().equals(simNo)) {
					bscsProfile.setSimNo(simNo);
				} else {
					bscsProfile.setSimNo(simNo + " * SIM NOT matching with BSCS SIM#" + validationProfile.getSimNo() + ". Contact admin to update the profile");
				}
			}
			
			profile.setBscsProfile(bscsProfile);
			
		} catch(ApplicationException ae) {
			LOGGER.error("Unable to extract BSCS profile. cause : ", ae);
		}catch(Exception e) {
			LOGGER.error("Unable to extract BSCS profile as no response received", e);
		}
		
		try {
			QuerySubscriptionResponseElement response = esmCleanupService.getESMProfile(msisdn);
			
			if(null != response && "GEN000".equals(response.getServiceResponse().getResponseCodes().get(0))) {
				ESMProfileDetails esmDetails = new ESMProfileDetails();
				ESMOfferDetails offer = null;
				
				esmDetails.setActivationDate(response.getUserProfile().getLineActivationDate());
				esmDetails.setIsConverged(Boolean.valueOf(response.getUserProfile().getIsConvergedSubscriber()));
				esmDetails.setIsFmc(Boolean.valueOf(response.getUserProfile().getIsFMC()));
				esmDetails.setIsParent(Boolean.valueOf(response.getUserProfile().getIsParent()));
				esmDetails.setLanguage(response.getUserProfile().getLang());
				esmDetails.setMsisdn(msisdn);
				esmDetails.setSubscriberType(response.getUserProfile().getSubscriberType());
				esmDetails.setUserSegment(response.getUserProfile().getUserSegment());
				
				for(Offers o : response.getOffersList().getOffers()) {
					offer = new ESMOfferDetails();
					offer.setOfferId(o.getOfferId());
					
					for(LanguageDetails l : o.getOfferLanguageList().getLanguageDetails()) {
						if(l.getLanguage().equals("ENGLISH")) {
							offer.setOfferName(l.getName());
							break;
						}
					}
					
					offer.setOfferType(o.getOfferType());
					offer.setOfferCategory(o.getOfferCategory());
					offer.setPurchaseDate(o.getPurchaseDate());
					
					for (MetaList meta : o.getMetaLists()) {
						if ("esm:isOoredooPassport".equalsIgnoreCase(meta.getKey())  ) {
		                    offer.setIsOpOffer(Boolean.FALSE);
		                   
		                    if (meta.getValue().equalsIgnoreCase("TRUE")) {
		                    	offer.setIsOpOffer(Boolean.TRUE);
		                        break;
		                    }
		                }
					}
					
					esmDetails.getActiveOffers().add(offer);
				}
				
				profile.setEsmProfile(esmDetails);
				
			} else {
				if(null != response) {
					LOGGER.error("Unable to extract ESM profile. Response code :" + response.getServiceResponse().getResponseCodes().get(0) 
							+ " Response Message : " + response.getServiceResponse().getErrorDetails() );
				} else {
					LOGGER.error("Unable to extract ESM profile.");
				}
			}
		} catch(ApplicationException ae) {
			LOGGER.error("Unable to extract ESM profile.", ae);
		}catch(Exception e) {
			LOGGER.error("Unable to extract ESM profile as no response received", e);
		}
		
		try {
			GetCustomerProfileResponse getCustomerProfileResponse = esmCleanupService.getCustomerProfile(msisdn);
			
			if(null != getCustomerProfileResponse) {
				MSISDNProfileType msisdnProfile = getCustomerProfileResponse.getMSISDNProfile();
				
				if(null != msisdnProfile) {
					CustomerProfile cp = new CustomerProfile();
					cp.setCustomerId(msisdnProfile.getCustomerID());
					cp.setContractId(msisdnProfile.getContractID());
					cp.setContractStatus(msisdnProfile.getContractStatus());
					cp.setCustomerType(msisdnProfile.getContractStatus());
					cp.setCustomerType(msisdnProfile.getCustomerType());
					cp.setRatePlanType(msisdnProfile.getRatePlanType());
					cp.setRatePlanCode(msisdnProfile.getRatePlanCode());
					cp.setLanguage(msisdnProfile.getLanguage());
					cp.setCivilId(msisdnProfile.getCivilID());
					cp.setIddFlag(msisdnProfile.getIDDFlag());
					cp.setBarringStatus(msisdnProfile.getBarringStatus());
					cp.setPaymentResp((null != msisdnProfile.getPaymentResponsible() 
							&& !msisdnProfile.getPaymentResponsible().isEmpty() 
							&& msisdnProfile.getPaymentResponsible().equals("X")) ? true : false);
					cp.setPromoSegment(msisdnProfile.getPromoSegment());
					cp.setSiebelAccountCustCode(msisdnProfile.getSieblAccountCustCode());
					
					if(null != getCustomerProfileResponse.getResponseMeta() 
							&& null != getCustomerProfileResponse.getResponseMeta().getMeta()
							&& !getCustomerProfileResponse.getResponseMeta().getMeta().isEmpty()) {
						
						for(META m : getCustomerProfileResponse.getResponseMeta().getMeta()) {
							if(m.getKey().equals("CustCode")) {
								cp.setCustCode(m.getValue());
							}
							
							if(m.getKey().equals("IsHybrid")) {
								cp.setIsHybrid((null != m.getValue() && !m.getValue().isEmpty() && m.getValue().equals("Y")) ? true : false);
							}
							
							if(m.getKey().equals("IsDigital")) {
								cp.setIsDigital((null != m.getValue() && !m.getValue().isEmpty() && m.getValue().equals("Y")) ? true : false);
							}
							
							if(m.getKey().equals("PrepaidXpressDevice")) {
								cp.setPrepaidXpressDevice((null != m.getValue() && !m.getValue().isEmpty() && m.getValue().equals("Y")) ? true : false);
							}
							
							if(m.getKey().equals("CoreOffer")) {
								cp.setCoreOffer(m.getValue());
							}
							
							if(m.getKey().equals("CoreOfferPrice")) {
								cp.setCoreOfferPrice(m.getValue());
							}
							
							if(m.getKey().equals("ActivationDate")) {
								cp.setActivationDate(m.getValue());
							}
							
							if(m.getKey().equals("NJMembershipId")) {
								cp.setNojoomMemberId(m.getValue());
							}
							
							if(m.getKey().equals("NJPoints")) {
								cp.setNojoomMemberPoint(m.getValue());
							}
						}
					}
					
					profile.setCustomerProfile(cp);
				}
			} else {
				LOGGER.error("Unable to extract Customer profile as no response received");
			}
		} catch (ApplicationException e) {
			LOGGER.error("Unable to extract Customer profile as no response received", e);
		}catch(Exception e) {
			LOGGER.error("Unable to extract Customer profile as no response received", e);
		}
		
		try {
			DownStreamProfileCheckRequest downStreamProfileCheckRequest = new DownStreamProfileCheckRequest();
			downStreamProfileCheckRequest.setChannel("CleanupTool");
			downStreamProfileCheckRequest.setMsisdn(msisdn);
			
			
			if(null != np) {
				downStreamProfileCheckRequest.setSimSerial(np.getSimNo());
			} else {
				throw new ApplicationException("MSISDN does not exists in number pool.", true);
			}
			
			downStreamProfileCheckRequest.setTransactionId("1234567890");
			
			String profileCheckServiceUrl = externalConfig.getMessage("service.profile.downstream-status.url", null, Locale.getDefault());
			String profileCheckServiceContext = externalConfig.getMessage("service.profile.downstream-status.context", null, Locale.getDefault());
			
			DownStreamProfileCheckResponse pResp = null;
		
			pResp = wsclient.downStreamProfileCheck(profileCheckServiceUrl, downStreamProfileCheckRequest, profileCheckServiceContext); 
			
			if(null != pResp) {
				Map<String, String> rasProf = new HashMap<String, String>();
				Map<String, String> csProf = new HashMap<String, String>();
				Map<String, String> erpProf = new HashMap<String, String>();
				Map<String, String> nmsProf = new HashMap<String, String>();
				Map<String, String> pcrfProf = new HashMap<String, String>();
				HLRProfile hlrProfile = new HLRProfile();
				
				//Setting RAS Profile
				if(null != pResp.getRasProfile() && null != pResp.getRasProfile().getAccountName() && !pResp.getRasProfile().getAccountName().isEmpty()) {
					rasProf.put("Status", "Profile Found");
					rasProf.put("Account Name", pResp.getRasProfile().getAccountName());
					rasProf.put("Account Type", pResp.getRasProfile().getAccountType());
					rasProf.put("Civil Id", pResp.getRasProfile().getCivilID());
					rasProf.put("Line Type", pResp.getRasProfile().getDataVoice());
					rasProf.put("Plan", pResp.getRasProfile().getPlanName());
					rasProf.put("SIM type", pResp.getRasProfile().getSimType());
				} else {
					rasProf.put("Status", "Profile NOT Found");
				}
				profile.setRasProfile(rasProf);
				
				//Setting CS Profile
				if(null != pResp.getCsProfile() && null != pResp.getCsProfile().getStatus() && pResp.getCsProfile().getStatus().equals("0")) {
					csProf.put("Status", pResp.getCsProfile().getStatus());
					csProf.put("Service Class", pResp.getCsProfile().getServiceClass());
					csProf.put("Balance", pResp.getCsProfile().getBalance());
				} else {
					csProf.put("Status", "Profile NOT Found");
				}
				profile.setCsProfile(csProf);
				
				//Setting ERP Profile
				if(null != pResp.getErpProfile() && null != pResp.getErpProfile().getItemName() && !pResp.getErpProfile().getItemName().isEmpty()) {
					erpProf.put("Item Name", pResp.getErpProfile().getItemName());
					erpProf.put("Item Number", pResp.getErpProfile().getItemNumber());
					erpProf.put("Location", pResp.getErpProfile().getLocation());
					erpProf.put("Status", pResp.getErpProfile().getCurrentStatus());
				} else {
					erpProf.put("Status", "Profile NOT Found");
				}
				profile.setErpProfile(erpProf);
				
				//Setting HLR Profile
				if(null != pResp.getHLRProfile() && null != pResp.getHLRProfile().getErrorCode() && pResp.getHLRProfile().getErrorCode().equals("0")) {
					hlrProfile.setStatus("Profile exists in HLR");
					profile.setHlrProfile(hlrProfile);
					profile.setHlrProfileFound(true);
				} else {
					hlrProfile.setStatus("Profile does NOT exists in HLR");
					profile.setHlrProfile(hlrProfile);
					profile.setHlrProfileFound(false);
				}
				
				//Setting NMS Profile
				if(null != pResp.getNmsProfile() && null != pResp.getNmsProfile().getStatus() && !pResp.getNmsProfile().getStatus().isEmpty()) {
					nmsProf.put("Status", pResp.getNmsProfile().getStatus());
				} else {
					nmsProf.put("Status", "Status NOT defined in NMS");
				}
				profile.setNmsProfile(nmsProf);
				
				//Setting NMS Profile
				if(null != pResp.getPcrfProfile() && null != pResp.getPcrfProfile().getStatus() && pResp.getPcrfProfile().getStatus().equalsIgnoreCase("complete")) {
					pcrfProf.put("Status", "Profile exists in PCRF");
				} else {
					pcrfProf.put("Status", "Profile NOT exists in PCRF");
				}
				profile.setPcrfProfile(pcrfProf);
			}
		} catch(ApplicationException e) {
			LOGGER.error("Unable to extract HLR profile as no response received", e);
		} catch(Exception e) {
			LOGGER.error("Unable to extract HLR profile as no response received", e);
		}
		
		return profile;
		
	}

	public ESMCleanupService getEsmCleanupService() {
		return esmCleanupService;
	}

	public void setEsmCleanupService(ESMCleanupService esmCleanupService) {
		this.esmCleanupService = esmCleanupService;
	}

	public CSCleanupService getCsCleanupService() {
		return csCleanupService;
	}

	public void setCsCleanupService(CSCleanupService csCleanupService) {
		this.csCleanupService = csCleanupService;
	}

	public BSCSCleanupService getBscsCleanupService() {
		return bscsCleanupService;
	}

	public void setBscsCleanupService(BSCSCleanupService bscsCleanupService) {
		this.bscsCleanupService = bscsCleanupService;
	}
}
