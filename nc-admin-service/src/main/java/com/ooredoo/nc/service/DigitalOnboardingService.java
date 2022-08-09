package com.ooredoo.nc.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.ooredoo.nc.dto.AnaTrxUpdateRequest;
import com.ooredoo.nc.dto.BSCSProfileDetails;
import com.ooredoo.nc.dto.CleanupDetails;
import com.ooredoo.nc.dto.NodalCleanupDetails;
import com.ooredoo.nc.dto.SubscriberProfileCleanupRequest;
import com.ooredoo.nc.dto.SubscriberProfileCleanupResponse;
import com.ooredoo.nc.dto.SubscriberProfileDetails;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.model.DigitalOffer;
import com.ooredoo.nc.model.NcDigitalOnboarding;
import com.ooredoo.nc.model.NumberPool;
import com.ooredoo.nc.repo.CompositeRepository;
import com.ooredoo.nc.utility.ApplicationConstants;
import com.ooredoo.nc.utility.ApplicationUtility;
import com.ooredoo.wsclient.WSClient;
import com.ooredoo.wsclient.anaonboarding.AnaOnboardingRequest;
import com.ooredoo.wsclient.anaonboarding.AnaOnboardingRequest.AttachmentList;
import com.ooredoo.wsclient.anaonboarding.AnaOnboardingRequest.AttachmentList.Attachment;
import com.ooredoo.wsclient.anaonboarding.AnaOnboardingRequest.DocumentDetails;
import com.ooredoo.wsclient.anaonboarding.AnaOnboardingRequest.OfferDetails;
import com.ooredoo.wsclient.anaonboarding.AnaOnboardingRequest.OfferDetails.EsmMetas;
import com.ooredoo.wsclient.anaonboarding.AnaOnboardingRequest.OfferDetails.EsmMetas.EsmMeta;
import com.ooredoo.wsclient.anaonboarding.AnaOnboardingRequest.OrderItemList;
import com.ooredoo.wsclient.anaonboarding.AnaOnboardingRequest.OrderItemList.OrderItemDetail;
import com.ooredoo.wsclient.anaonboarding.AnaOnboardingRequest.PaymentList;
import com.ooredoo.wsclient.anaonboarding.AnaOnboardingRequest.PaymentList.PaymentDetail;
import com.ooredoo.wsclient.anaonboarding.AnaOnboardingRequest.SubscriberDetails;
import com.ooredoo.wsclient.anaonboarding.AnaOnboardingRequest.SubscriberDetails.AddressList;
import com.ooredoo.wsclient.anaonboarding.AnaOnboardingRequest.SubscriberDetails.AddressList.AddressDetail;
import com.ooredoo.wsclient.anaonboarding.AnaOnboardingRequest.SubscriberDetails.ProfileDetails;
import com.ooredoo.wsclient.anaonboarding.AnaOnboardingResponse;
import com.ooredoo.wsclient.numberreservation.NumberReservaionBeanIn;
import com.ooredoo.wsclient.numberreservation.NumberReservaionRequest;
import com.ooredoo.wsclient.numberreservation.NumberReservaionResponse;

@Service
public class DigitalOnboardingService implements ApplicationConstants {

	@Autowired
	private  CompositeRepository compositeRepository;
	
	@Autowired
	private BSCSCleanupService bscsCleanupService;
	
	@Autowired
	private ReloadableResourceBundleMessageSource externalConfig;
	
	@Autowired
	private ProfileCleanupService profileCleanupService;
	
	@Autowired
	private WSClient wsclient;
	private static final Logger LOGGER = LoggerFactory.getLogger(DigitalOnboardingService.class);
	
	/**
	 * 
	 * @param msisdn
	 * @param username
	 * @throws ApplicationException
	 */
	public String executeOnboarding(String msisdn, String username, String civilId, Long offerId) throws ApplicationException {
		String trxId = ApplicationUtility.generateRandomTransactionId();
		NcDigitalOnboarding trxProfile=null;
		try {
			String reqMsisdn = MSISDN_PREFIX + msisdn;
			 trxProfile = validateMsisdnProfile(reqMsisdn, username);
			 
			if(null != trxProfile) {
				if(null != trxProfile.getMsisdn() && !trxProfile.getMsisdn().isEmpty() 
						&& null != trxProfile.getLocalSim() && !trxProfile.getLocalSim().isEmpty() 
						&& null != trxProfile.getLocalImsi() && !trxProfile.getLocalImsi().isEmpty()) {
					
					//Cleaning up local profile
					SubscriberProfileCleanupRequest pcReq = new SubscriberProfileCleanupRequest();
					pcReq.setRequestDate(new Date());
					pcReq.setRequestUsername(username);
					
					String nodeString = externalConfig.getMessage("service.cleanup.availabe.system", null, Locale.getDefault());
					String[] splitString = nodeString.split(COMMA);
					
					pcReq.setNodes(Arrays.asList(splitString));
					
					List<SubscriberProfileDetails> profileList = new ArrayList<SubscriberProfileDetails>();
					SubscriberProfileDetails profile = new SubscriberProfileDetails();
					profile.setSubscriberType("PO");
					profile.setMsisdn(MSISDN_PREFIX + msisdn);
					profile.setMsisdnWithoutPrefix(msisdn);
					profile.setImsi(trxProfile.getLocalImsi());
					profile.setSimNo(trxProfile.getLocalSim());
					profile.setSelecedNmsPool("KIOSK MACHINE POOL");
					profile.setSelectedDealer("999999");
					profile.setPrice(0D);
					profile.setSelectedErpLocation("POS.ESHOP.MAIN");
					profileList.add(profile);
					
					pcReq.setProfiles(profileList);

					SubscriberProfileCleanupResponse cleanupResp = profileCleanupService.handleCleanup(pcReq, false);
					
					if(null != cleanupResp && null != cleanupResp.getCleanupDetails() && !cleanupResp.getCleanupDetails().isEmpty()) {
						CleanupDetails  cleanupDetails = cleanupResp.getCleanupDetails().get(MSISDN_PREFIX + msisdn);
						
						for(NodalCleanupDetails e : cleanupDetails.getCleanupDetails()) {
							if(null != e.getStatus() && !e.getStatus().isEmpty()) {
								if(e.getStatus().equals(SUCCESS_LEBEL)) {
									LOGGER.debug("Local Nodal cleanup is successful for Node - {} for MSISDN - {}", e.getNode(), msisdn);
								} else {
									trxProfile = updateTrxDetails("F", null, "Error response for Node- " + e.getNode() + ".Local Clean-up detail-"+ e.getResponse(), null, trxProfile);
									throw new ApplicationException("Error response for Node- " + e.getNode() + ".Local Clean-up detail-"+ e.getResponse(), true);
								}
							} else {
								trxProfile = updateTrxDetails("F", null, "System could not determine the Local cleanup status for node- " + e.getNode(), null, trxProfile);
								throw new ApplicationException("System could not determine the Local cleanup status for node- " + e.getNode(), true);
							}
						}
					} else {
						trxProfile = updateTrxDetails("F", null, "System could not retrieve the Local cleanup details", null, trxProfile);
						throw new ApplicationException("System could not retrieve the Local cleanup details", true);
					}	
					
					trxProfile = updateTrxDetails(null, null, "Local Profile cleanup successful", null, trxProfile);
					
					//Cleaning up existing digital profile.
					LOGGER.info("As local profile cleanup is successful. So cleaning up current digital profile for MSISDN-{}", msisdn);
					
					if(null != trxProfile.getMsisdn() && !trxProfile.getMsisdn().isEmpty() 
							&& null != trxProfile.getBscsSimNo() && !trxProfile.getBscsSimNo().isEmpty() 
							&& null != trxProfile.getBscsImsi() && !trxProfile.getBscsImsi().isEmpty()) {
						
						if(trxProfile.getBscsSimNo().equals(trxProfile.getLocalSim()) && trxProfile.getBscsImsi().equals(trxProfile.getLocalImsi())) {
							LOGGER.info("Skipping digital clean-up as Number pool and bscs SIM-IMSI combination is same for ythe MSISDN-{}", msisdn);
							
						} else {
							//Cleaning up digital profile
							SubscriberProfileCleanupRequest pcReqD = new SubscriberProfileCleanupRequest();
							pcReqD.setRequestDate(new Date());
							pcReqD.setRequestUsername(username);
							
							pcReq.setNodes(Arrays.asList(externalConfig.getMessage("service.cleanup.availabe.system", null, Locale.getDefault()).split(COMMA)));
							
							List<SubscriberProfileDetails> profileListD = new ArrayList<SubscriberProfileDetails>();
							SubscriberProfileDetails profileD = new SubscriberProfileDetails();
							profileD.setSubscriberType("PO");
							profileD.setMsisdn(MSISDN_PREFIX + msisdn);
							profileD.setMsisdnWithoutPrefix(msisdn);
							profileD.setImsi(trxProfile.getBscsImsi());
							profileD.setSimNo(trxProfile.getBscsSimNo());
							profileD.setSelecedNmsPool("KIOSK MACHINE POOL");
							profileD.setSelectedDealer("999999");
							profileD.setPrice(0D);
							profileD.setSelectedErpLocation("POS.ESHOP.MAIN");
							profileListD.add(profileD);
							
							pcReqD.setProfiles(profileListD);

							SubscriberProfileCleanupResponse cleanupRespD = profileCleanupService.handleCleanup(pcReqD, false);
							
							if(null != cleanupRespD && null != cleanupRespD.getCleanupDetails() && !cleanupRespD.getCleanupDetails().isEmpty()) {
								CleanupDetails  cleanupDetails = cleanupRespD.getCleanupDetails().get(MSISDN_PREFIX + msisdn);
								
								for(NodalCleanupDetails e : cleanupDetails.getCleanupDetails()) {
									if(null != e.getStatus() && !e.getStatus().isEmpty()) {
										if(e.getStatus().equals(SUCCESS_LEBEL)) {
											LOGGER.debug("Local Nodal cleanup is successful for Node - {} for MSISDN - {}", e.getNode(), msisdn);
										} else {
											trxProfile = updateTrxDetails("F", null, "Error response for Node- " + e.getNode() + ".Digital Clean-up detail-"+ e.getResponse(), null, trxProfile);
											throw new ApplicationException("Error response for Node- " + e.getNode() + ".Digital Clean-up detail-"+ e.getResponse(), true);
										}
									} else {
										trxProfile = updateTrxDetails("F", null, "System could not determine the Digital cleanup status for node- " + e.getNode(), null, trxProfile);
										throw new ApplicationException("System could not determine the Digital cleanup status for node- " + e.getNode(), true);
									}
								}
							} else {
								trxProfile = updateTrxDetails("F", null, "System could not retrieve the Digital cleanup details", null, trxProfile);
								throw new ApplicationException("System could not retrieve the Digital cleanup details", true);
							}
						}
					} else {
						LOGGER.info("Skipping digital clean-up as there is NO IMSI and SIM found");
					}
					
					trxProfile = updateTrxDetails("S", null, "Number cleanup is successful for both local and existing digital profile", null, trxProfile);
					
					//Initiate On-boarding
					
					//Invoke Number Reservation
					NumberReservaionRequest nResRequest = new NumberReservaionRequest();
					NumberReservaionBeanIn bI = new NumberReservaionBeanIn();
					bI.setACCOUNTID("Cleanup-Tool");
					bI.setACCOUNTTYPE("Residential");
					bI.setDEALERLOCATORID("Corporate");
					bI.setACTUALDEALERID("777777");
					bI.setLOGINID("CleanupTool");
					bI.setMSISDN(msisdn);
					bI.setRESERVATIONSTATUS("R");
					
					nResRequest.setNumberReservaionBeanIn(bI);
					
					NumberReservaionResponse resp = wsclient.numberReservation(externalConfig.getMessage("service.create.number.create.numberreservation", null, Locale.ENGLISH), nResRequest, NUMBER_RESERVATION_SERVICE_CONTEXT);
					LOGGER.info("Number Reservation Status : {}", resp.getNumberReservaionBeanOut().getStatus());

					//ANA On-boarding
					AnaOnboardingRequest req = prepareOnboardingRequest(msisdn, civilId, offerId, trxProfile.getLocalSim(), trxId);
					AnaOnboardingResponse onboardingResp = wsclient.anaOnboardingService(externalConfig.getMessage("service.create.number.create.anaonboarding", null, Locale.ENGLISH), req, ANA_ONBOARDING_SERVICE_CONTEXT);
					
					if(onboardingResp.getResponseCode().equals("0")) {
						if(offerId!=0)
						{
							trxProfile = updateTrxDetails(null, "S", "ANA order submitted and Provisining in progress "+trxId, onboardingResp.getTransactionId(), trxProfile);
						}else {
							trxProfile = updateTrxDetails(null, "S", "ANA onboarding successful", onboardingResp.getTransactionId(), trxProfile);
						}
						
					} else {
						trxProfile = updateTrxDetails(null, "F", onboardingResp.getResponseMessage(), onboardingResp.getTransactionId(), trxProfile);
						
					}
				} else {
					throw new ApplicationException("Invalid Local transaction profile. Missing MSISDN/SIM/IMSI", true);
				}
			} else {
				throw new ApplicationException("New transaction could not be created for MSISDN-" + msisdn, true);
			}
		} catch(ApplicationException e) {
			throw e;
		} catch (Exception e) {
			throw new ApplicationException(e.getMessage(), e, true);
		}
		LOGGER.info("trxProfile.getCleanupStatus() status :::"+trxProfile.getCleanupStatus());
		
		if(trxProfile.getCleanupStatus().equalsIgnoreCase("S")) {
			return "ANA onboarding request submitted successfully with trsansaction Id- " + trxId;
			//Order Submitted Successfully, Number provisioning in process refresh after 10 seconds
		}else {
			return "ANA onboarding request Fail with trsansaction Id- " + trxId;	
		}
		//return trxId;
	}
	
	/**
	 * 
	 * @param msisdn
	 * @param idNumber
	 * @param offerId
	 * @param localSimNo
	 * @param trxId
	 * @return
	 * @throws ApplicationException
	 */
	private AnaOnboardingRequest prepareOnboardingRequest(String msisdn, String idNumber, Long offerId, String localSimNo, String trxId) throws ApplicationException {
		AnaOnboardingRequest req = new AnaOnboardingRequest();
		req.setTransactionId(trxId);
		req.setTransactionDateTime(new SimpleDateFormat(DF_YYYYMMDD_HHMMSS).format(new Date()));
		req.setTransactionType("Onboarding");
		req.setMsisdn(MSISDN_PREFIX + msisdn);
		req.setChannel("Cleanuptool");
		req.setDeliveryFlag("Y");
		req.setDeliveryType("Normal");
		req.setIMSI(localSimNo);
		req.setNMSReservationId("CleanupTool");//Need to check
		req.setDealerID("777777");
		req.setLocation("Corporate");
		req.setUserType("PREPAID");
		
		SubscriberDetails subscriberDetails = new SubscriberDetails();
		ProfileDetails profileDetails = new ProfileDetails();
		profileDetails.setTitle("Mrs.");
		profileDetails.setFirstName("TestFNumber");
		profileDetails.setLastName("TestLastName");
		profileDetails.setDateOfBirth("09/10/1991");
		profileDetails.setGender("Female");
		profileDetails.setNationality("Kuwait");
		profileDetails.setIdType("Civil ID");
		
		if(null == idNumber || idNumber.isEmpty()) {
			profileDetails.setIdNumber("291091000454");
		} else {
			profileDetails.setIdNumber(idNumber);
		}
		profileDetails.setIdExpirationDate("05/20/2025");
		profileDetails.setEmailAddress("testmail@gmail.com");
		profileDetails.setLanguage("English");
		profileDetails.setAlternatePhone1("96566166893");

		subscriberDetails.setProfileDetails(profileDetails);
		
		AddressList addressList = new AddressList();
		AddressDetail ad = new AddressDetail();
		ad.setAddressType("Residence");
		ad.setAppartmentNo("14");
		ad.setFloor("0");
		ad.setBuildingNo("14");
		ad.setBlock("1");
		ad.setStreet("Instantly delivered via email");
		ad.setAvenue("A1");
		ad.setArea("Dahr");
		ad.setGovernorate("Al-Aasma");
		ad.setCityId("40");
		ad.setStreetId("4");
		ad.setQuarter("14");
		ad.setContactNumber("96566166893");
		ad.setAltContactNumber("96566166893");
		ad.setSkipAddressValidation("Y");
		ad.setExtraAddressDetails("Instantly delivered via email");
		addressList.setAddressDetail(ad);
		subscriberDetails.setAddressList(addressList);
		
		req.setSubscriberDetails(subscriberDetails);
		
		OfferDetails od = new OfferDetails();
		Optional<DigitalOffer> offerO = compositeRepository.getDigitalOfferRepo().findByOfferId(Long.valueOf(offerId));
		
		if(offerO.isPresent()) {
			od.setOfferId(String.valueOf(offerId));
			od.setOfferName(offerO.get().getOfferName());
			od.setOfferDescription(offerO.get().getOfferDesc());
			od.setAccountGroup(offerO.get().getAccGroup());
			od.setActualPrice("6000");
			od.setDiscountedPrice("6000");
			od.setTotalPrice("6000");
		} else {
			throw new ApplicationException("No offer details found for offer id-" + offerId, true);
		}
		
		EsmMetas metas = new EsmMetas();
		EsmMeta meta = new EsmMeta();
		meta.setEsmKey("esm:packValidity");
		meta.setEsmValue("30");
		metas.getEsmMeta().add(meta);
		od.setEsmMetas(metas);
		req.setOfferDetails(od);
		
		AttachmentList al = new AttachmentList();
		Attachment att = new Attachment();
		att.setAttachmentId("5671da4a-649b-473d-912e-e67a62698f11");
		att.setAttachmentSource("OPENKM");
		att.setFileType("FILE");
		att.setFileExt(".pdf");
		att.setFileName("5671da4a-649b-473d-912e-e67a62698f11");
		al.setAttachment(att);
		req.setAttachmentList(al);
		
		PaymentList paymentList = new PaymentList();
		PaymentDetail pd = new PaymentDetail();
		pd.setAuthorizationCode("chg_LV072420221352Kj7u2106286");
		pd.setPaymentMethod("Knet");
		pd.setPaymentAmount("6000");
		pd.setPaymentRefNo("WNEWK-220621135224624461");
		pd.setPaymentId("chg_LV072420221352Kj7u2106286");
		paymentList.setPaymentDetail(pd);
		req.setPaymentList(paymentList);
		
		OrderItemList oil = new OrderItemList();
		OrderItemDetail oid = new OrderItemDetail();
		oid.setSimType("Physical");
		oid.setItemId("SIM");
		oid.setItemCode("2593461208");
		oid.setLocationCode("Corporate");
		oid.setNetPrice("0");
		oid.setUnitPrice("0");
		oil.getOrderItemDetail().add(oid);
		req.setOrderItemList(oil);
		
		DocumentDetails dd = new DocumentDetails();
		dd.setDigitalFlag("Y");
		req.setDocumentDetails(dd);
		return req;
		
	}
	
	/**
	 * 
	 * @param cleanupStatus
	 * @param onboardingStatus
	 * @param details
	 * @param trxProfile
	 */
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public NcDigitalOnboarding updateTrxDetails(String cleanupStatus, String onboardingStatus, String details, String trxId, NcDigitalOnboarding trxProfile) {
		if(null != cleanupStatus) {
			trxProfile.setCleanupStatus(cleanupStatus);
		}
		
		if(null != onboardingStatus) {
			trxProfile.setOnboardingStatus(onboardingStatus);
		}
		
		if(null != details) {
			trxProfile.setTrxDetails(details);
		}
		
		if(null != trxId) {
			trxProfile.setTrxId(trxId);
		}
		
		NcDigitalOnboarding trxProfileU = compositeRepository.getDigitalCleanupRepo().save(trxProfile);
		return trxProfileU;
	}
	
	/**
	 * 
	 * @param msisdn
	 * @return
	 * @throws ApplicationException
	 */
	@Transactional(isolation = Isolation.READ_COMMITTED)
	private NcDigitalOnboarding validateMsisdnProfile(String msisdn, String username) throws ApplicationException{
		NcDigitalOnboarding trxNew = null;
		Optional<NumberPool> npO = compositeRepository.getNumberPoolRepo().findByMsisdn(msisdn);
		
		if(npO.isPresent()) {
			NumberPool np = npO.get();
			//Logic
			//Check if there is any open transaction in clean-up tool
				//If validated then go for clean up
				// If not matching remove the open transaction and populate the same with bscs records and go for clean up
			//If NOT available extract the record from bscs	
				//if NOT available in BSCS then consider the profile does not exists.
				Optional<NcDigitalOnboarding> openTrxO = compositeRepository.getDigitalCleanupRepo().findActiveOnboadringTrxForMsisdn(msisdn);
				
				if(openTrxO.isPresent()) {
					LOGGER.info("Open transaction found for MSISDN- {}", msisdn);
					trxNew = openTrxO.get();
					
					BSCSProfileDetails validationProfile = bscsCleanupService.getProfileValidationDetails(msisdn);
					
					if(null == validationProfile) {
						LOGGER.info("No details found with BSCS for MSISDN-{}. Considering profile to be cleanup with open transaction info again", msisdn);
					} else {
						if(null == validationProfile.getImsi() || validationProfile.getImsi().isEmpty()) {
							LOGGER.info(trxNew.getBscsImsi() + " * Unable to validate IMSI with BSCS as IMSI not found in BSCS");
						} else {
							if(validationProfile.getImsi().equals(trxNew.getBscsImsi())) {
								LOGGER.info("Open transaction IMSI matching with BSCS IMSI");
							} else {
								trxNew.setBscsImsi(validationProfile.getImsi());
							}
						}
						
						if(null == validationProfile.getSimNo() || validationProfile.getSimNo().isEmpty()) {
							LOGGER.info(trxNew.getBscsSimNo() + " * Unable to validate SIM with BSCS as SIM not found in BSCS");
						} else {
							if(validationProfile.getSimNo().equals(trxNew.getBscsSimNo())) {
								LOGGER.info("Open transaction SIM matching with BSCS SIM");
							} else {
								trxNew.setBscsSimNo(validationProfile.getSimNo());
							}
						}
					}
				} else {
					trxNew = new NcDigitalOnboarding();
					trxNew.setMsisdn(msisdn);
					trxNew.setLocalSim(np.getSimNo());
					trxNew.setLocalImsi(np.getImsi());
					
					BSCSProfileDetails validationProfile = bscsCleanupService.getProfileValidationDetails(msisdn);
					if(null == validationProfile) {
						LOGGER.info("No details found with BSCS for MSISDN-{}. Considering profile is cleaned up already", msisdn);
					} else {
						trxNew.setBscsImsi(validationProfile.getImsi());
						trxNew.setBscsSimNo(validationProfile.getSimNo());
					}
				}
				
				trxNew.setTrxDetails("Record validated/updated with BSCS. Cleanup will be done on both local and digital profile based on this information");
				trxNew.setCleanupStatus("I");
				trxNew.setOnboardingStatus("I");
				trxNew.setTrxDate(new Date());
				trxNew.setUsername(username);
				trxNew = compositeRepository.getDigitalCleanupRepo().save(trxNew);
			
		} else {
			throw new ApplicationException("MSISDN is not available in Cleanup Number Pool, Please add the same", true);
		}
		
		return trxNew;
	}
	
	/**
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	public List<DigitalOffer> getAllDigitalOffers() throws ApplicationException {
		return compositeRepository.getDigitalOfferRepo().findByEnabled(true);
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 * @throws ApplicationException
	 */
	public List<NcDigitalOnboarding> getAllDigitalTransaction(String username) throws ApplicationException {
		return compositeRepository.getDigitalCleanupRepo().findTrxForUsername(username);
	}
	
	/**
	 * 
	 * @param req
	 * @throws ApplicationException
	 */
	public void updateTransaction(AnaTrxUpdateRequest req) throws ApplicationException {
		
		List<NcDigitalOnboarding> allTrxForTrxId = compositeRepository.getDigitalCleanupRepo().findByTrxId(req.getTrxId());
		
		if(null != allTrxForTrxId && !allTrxForTrxId.isEmpty()) {
			ListIterator<NcDigitalOnboarding> li = allTrxForTrxId.listIterator();
			
			while(li.hasNext()) {
				NcDigitalOnboarding e = li.next();
				e.setTrxDetails((null != req.getTrxDetails() && !req.getTrxDetails().isEmpty()) ? req.getTrxDetails() + req.getSiebelOrder() : e.getTrxDetails());
				e.setOnboardingStatus((null != req.getTrxStatus() && !req.getTrxStatus().isEmpty()) ? req.getTrxStatus()  : e.getOnboardingStatus());
				li.set(e);
			}
			
			compositeRepository.getDigitalCleanupRepo().saveAll(allTrxForTrxId);
			LOGGER.info("Updated transaction details for transaction id#{}", req.getTrxId());
			
		} else {
			LOGGER.info("No transaction details found for transaction id#{}", req.getTrxId());
		}
	}
}
