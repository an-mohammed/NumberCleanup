package com.ooredoo.nc.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import com.ooredoo.createorder.CreateOrderCriteriaType;
import com.ooredoo.createorder.CreateOrderRequestType;
import com.ooredoo.createorder.CreateOrderResponseType;
import com.ooredoo.createorder.ObjectFactory;
import com.ooredoo.createorder.OrderEntryAttachmentType;
import com.ooredoo.createorder.OrderEntryOrdersType;
import com.ooredoo.createorder.PaymentsDigitalType;
import com.ooredoo.createorder.PaymentsssType;
import com.ooredoo.createsubs.AccountBusinessAddressType;
import com.ooredoo.createsubs.CommonComponentsType;
import com.ooredoo.createsubs.ContactType;
import com.ooredoo.createsubs.CreateSubscriberCriteriaType;
import com.ooredoo.createsubs.CreateSubscriberRequestType;
import com.ooredoo.createsubs.CreateSubscriberResponseType;
import com.ooredoo.createsubs.CutServiceSubAccountsType;
import com.ooredoo.createsubs.IdentificationType;
import com.ooredoo.createsubs.PostpaidAccountInfoType;
import com.ooredoo.nc.dto.B2BSubscriberOnboardingRequest;
import com.ooredoo.nc.dto.B2BSubscriberOnboardingResponse;
import com.ooredoo.nc.dto.B2CSubscriberOnboardingRequest;
import com.ooredoo.nc.dto.B2CSubscriberOnboardingResponse;
import com.ooredoo.nc.dto.CleanupDetails;
import com.ooredoo.nc.dto.NewSubscriberProfile;
import com.ooredoo.nc.dto.NodalCleanupDetails;
import com.ooredoo.nc.dto.SubscriberProfileCleanupRequest;
import com.ooredoo.nc.dto.SubscriberProfileCleanupResponse;
import com.ooredoo.nc.dto.SubscriberProfileDetails;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.model.NumberPool;
import com.ooredoo.nc.utility.ApplicationConstants;
import com.ooredoo.nc.utility.ApplicationUtility;
import com.ooredoo.service.wsclient.b2bcustomercid.GetB2BCustomerCIDRequest;
import com.ooredoo.service.wsclient.b2bcustomercid.GetB2BCustomerCIDResponse;
import com.ooredoo.service.wsclient.createb2bsubscriberproxy.CreateB2BSubscriberRequest;
import com.ooredoo.service.wsclient.createb2bsubscriberproxy.CreateB2BSubscriberRequest.CreateOrderType;
import com.ooredoo.service.wsclient.createb2bsubscriberproxy.CreateB2BSubscriberRequest.CreateOrderType.CreateOrderInput;
import com.ooredoo.service.wsclient.createb2bsubscriberproxy.CreateB2BSubscriberRequest.CreateSubscriberType;
import com.ooredoo.service.wsclient.createb2bsubscriberproxy.CreateB2BSubscriberRequest.CreateSubscriberType.CreateSubscriberInput;
import com.ooredoo.service.wsclient.createb2bsubscriberproxy.CreateB2BSubscriberRequest.CreateSubscriberType.CreateSubscriberInput.AccountInfo;
import com.ooredoo.service.wsclient.createb2bsubscriberproxy.CreateB2BSubscriberRequest.CreateSubscriberType.CreateSubscriberInput.AccountInfo.AccountDetails;
import com.ooredoo.service.wsclient.createb2bsubscriberproxy.CreateB2BSubscriberRequest.ReserveNumberType;
import com.ooredoo.service.wsclient.createb2bsubscriberproxy.CreateB2BSubscriberResponse;
import com.ooredoo.service.wsclient.numberunreservepostpaidservice.CancelOrderBeanIn;
import com.ooredoo.service.wsclient.numberunreservepostpaidservice.CancelOrderRequest;
import com.ooredoo.service.wsclient.numberunreservepostpaidservice.CancelOrderResponse;
import com.ooredoo.service.wsclient.oraappservicenmtc.APPSRECTABVAR;
import com.ooredoo.service.wsclient.oraappservicenmtc.APPSRECVAR;
import com.ooredoo.service.wsclient.oraappservicenmtc.InputParameters;
import com.ooredoo.wsclient.WSClient;
import com.ooredoo.wsclient.numberreservation.NumberReservaionBeanIn;
import com.ooredoo.wsclient.numberreservation.NumberReservaionRequest;
import com.ooredoo.wsclient.numberreservation.NumberReservaionResponse;

@Service
public class CreatePrepaidSimService implements ApplicationConstants  {
	
	@Autowired
	private WSClient wsclient;
	
	@Autowired
	private ReloadableResourceBundleMessageSource externalConfig;
	
	@Autowired
	private NumberPoolService numberPoolService; 
	
	@Autowired
	private ProfileCleanupService profileCleanupService;
	
	@Autowired
	private CSCleanupService csCleanupService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CreatePrepaidSimService.class);

	private static final int String = 0;

	/**
	 * 
	 * @param msisdn
	 * @param simNo
	 * @param promoId
	 * @return
	 * @throws ApplicationException
	 */
	public CreateOrderResponseType createPrepaidNumber(String msisdn, String simNo, String promoId, String civilId, String subscriberType) throws ApplicationException {
		String senderId = externalConfig.getMessage("service.create.number.create.subscriber.senderId", null, Locale.ENGLISH);
		
		if(civilId == null || civilId.isEmpty()) {
			civilId = "283010112777";
		}
		
		CreateSubscriberRequestType req = prepareCreateSubscriberRequest(senderId, civilId);
		
		CreateSubscriberResponseType cSubsResp =  wsclient.createSubscriber(externalConfig.getMessage("service.create.number.create.subscriber", null, Locale.ENGLISH), req, CREATE_SUBSCRIBER_SERVICE_CONTEXT);
		
		String billingAccountId = cSubsResp.getBillingAccountId();
		String accountId = cSubsResp.getAccountId();
		String serviceAccountId = cSubsResp.getServiceAccountId();
		String bscsCustNum = cSubsResp.getBSCSCustNumber();
		
		//Invoke Number Reservation
		NumberReservaionRequest nResRequest = new NumberReservaionRequest();
		NumberReservaionBeanIn bI = new NumberReservaionBeanIn();
		bI.setACCOUNTID(accountId);
		bI.setACCOUNTTYPE("Residential");
		
		SimpleDateFormat sdf = new SimpleDateFormat(DF_YYYYMMDD);
		sdf.setLenient(false);
		bI.setACTUALDEALERID(sdf.format(new Date()));
		bI.setDEALERLOCATORID("Corporate");
		bI.setACTUALDEALERID("777777");
		bI.setLOGINID("CleanupTool");
		bI.setMSISDN(msisdn);
		bI.setRESERVATIONSTATUS("R");
		
		nResRequest.setNumberReservaionBeanIn(bI);
		
		NumberReservaionResponse resp = wsclient.numberReservation(externalConfig.getMessage("service.create.number.create.numberreservation", null, Locale.ENGLISH), nResRequest, NUMBER_RESERVATION_SERVICE_CONTEXT);
		LOGGER.info("Number Reservation Status : {}", resp.getNumberReservaionBeanOut().getStatus());
		
		LOGGER.info("Invoking create order request");
		CreateOrderRequestType createOrderReq = prepareCreateOrderRequest(billingAccountId, accountId, serviceAccountId, bscsCustNum, msisdn, simNo, senderId, promoId, subscriberType, null);
		CreateOrderResponseType cOrderResp =  wsclient.createOrder(externalConfig.getMessage("service.create.number.create.order", null, Locale.ENGLISH), createOrderReq, CREATE_ORDER_SERVICE_CONTEXT);
		
		String siebelRowId = cOrderResp.getSpcId();
		LOGGER.info("Prepaid number with MSISDN {} ans SIM {} successfully created with siebel row id -{}", msisdn, simNo, siebelRowId);
		
		return cOrderResp;
	}
	
	/**
	 * 
	 * @param msisdn
	 * @param simNo
	 * @param promoId
	 * @return
	 * @throws ApplicationException
	 * @throws DatatypeConfigurationException 
	 */
	public CreateB2BSubscriberResponse createB2BCPR(String msisdn, String simNo, String promoId, String civilId, String subscriberType, String custCode,String isEpr) throws ApplicationException, DatatypeConfigurationException {
		String senderId = externalConfig.getMessage("service.create.number.create.subscriber.senderId", null, Locale.ENGLISH);
		LOGGER.info("createB2BCPR :::: {}  ", senderId );
		if(civilId == null || civilId.isEmpty()) {
			//civilId = "283010112777";
			
			GetB2BCustomerCIDRequest req = prepareB2BCustomerCIDRequest(custCode);
			GetB2BCustomerCIDResponse customerCIDResponse =  wsclient.getb2bCustomerCID(externalConfig.getMessage("service.create.number.b2bcustomercid", null, Locale.ENGLISH), req, GET_CUSTOMERCID_SERVICE_CONTEXT);
			if(customerCIDResponse==null) {
				LOGGER.info("Customer Id not found fir Cust Code : {}  ", custCode);
				LOGGER.error("customerCIDResponse.getErrorMessage():{}", customerCIDResponse.getErrorMessage());
			}
			civilId = customerCIDResponse.getIdNumber();
			
		}
		//TODO
		/*CreateSubscriberRequestType req = prepareCreateSubscriberRequest(senderId, civilId);
		CreateSubscriberResponseType cSubsResp =  wsclient.createSubscriber(externalConfig.getMessage("service.create.number.create.subscriber", null, Locale.ENGLISH), req, CREATE_SUBSCRIBER_SERVICE_CONTEXT);
		
		String billingAccountId = cSubsResp.getBillingAccountId();
		String accountId = cSubsResp.getAccountId();
		String serviceAccountId = cSubsResp.getServiceAccountId();
		String bscsCustNum = cSubsResp.getBSCSCustNumber();
		*/
		//Invoke Number Reservation
		NumberReservaionRequest nResRequest = new NumberReservaionRequest();
		NumberReservaionBeanIn bI = new NumberReservaionBeanIn();
		
		String accountId = "1-1BKKZ0K1";
		String actType = "Corporate";
		String dealerId = "999999";
		String loginId = "23431312";
		
		LOGGER.info("Number Reservation accountId : {}",accountId);
		bI.setACCOUNTID(accountId);
		//bI.setACCOUNTTYPE("Residential");
		bI.setACCOUNTTYPE(actType);
		SimpleDateFormat sdf = new SimpleDateFormat(DF_YYYYMMDD);
		sdf.setLenient(false);
		bI.setACTUALDEALERID(sdf.format(new Date()));
		bI.setDEALERLOCATORID(actType);
		bI.setACTUALDEALERID(dealerId);
		bI.setLOGINID(loginId);
		bI.setMSISDN(msisdn);
		bI.setRESERVATIONSTATUS("R");
		
		
		String FORMATER = "yyyy-MM-dd";
		DateFormat format = new SimpleDateFormat(FORMATER);
		 Date date = new Date();
		    date.setDate(12); date.setMonth(6); date.setYear(2022-1900); //2022-07-12
		    XMLGregorianCalendar formattedEffectedDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(format.format(date));
		    LOGGER.info("Effected date Number :::"+formattedEffectedDate);
			
		bI.setEFFECTIVEDATE(formattedEffectedDate);
		
		nResRequest.setNumberReservaionBeanIn(bI);
		
		NumberReservaionResponse resp = wsclient.numberReservation(externalConfig.getMessage("service.create.number.create.numberreservation", null, Locale.ENGLISH), nResRequest, NUMBER_RESERVATION_SERVICE_CONTEXT);
		LOGGER.info("Number Reservation Status : {}", resp.getNumberReservaionBeanOut().getStatus());
		
		LOGGER.info("Invoking sim serial reservation service - Ora app service nmtc request");
		//AP:: resp.getNumberReservaionBeanOut().getStatus() is SUCCESS
		String message = simSerialReservation(simNo);
		
		if(!message.equalsIgnoreCase("Success")) {
			//if service faile will call unserver service call
			LOGGER.info("Start Invoking Number Unreserve Postpaid Service request");
			//AP::
			//Invoke Number UN Reservation
			CancelOrderRequest unResRequest = new CancelOrderRequest();
			CancelOrderBeanIn cancelOrderBeanIn = new CancelOrderBeanIn();
			cancelOrderBeanIn.setACCOUNTID(accountId);
			cancelOrderBeanIn.setACCOUNTTYPE(actType);
			cancelOrderBeanIn.setMSISDN(msisdn);

			//String FORMATER = "yyyy-MM-dd";
			//DateFormat format = new SimpleDateFormat(FORMATER);

		     date = new Date();
		    date.setDate(12); date.setMonth(6); date.setYear(2022-1900);//2022-07-12
		    XMLGregorianCalendar gDateFormatted = DatatypeFactory.newInstance().newXMLGregorianCalendar(format.format(date));
		    LOGGER.info("Effected date Number :::"+gDateFormatted);
		    
			cancelOrderBeanIn.setEFFECTIVEDATE(gDateFormatted);
			cancelOrderBeanIn.setLOGINID(loginId);
			cancelOrderBeanIn.setDEALERLOCATORID(actType);
			cancelOrderBeanIn.setDEALERID(dealerId);
			cancelOrderBeanIn.setISMANAGER(false);
			cancelOrderBeanIn.setACTUALDEALERID(dealerId);
			
			unResRequest.setCancelOrderBeanIn(cancelOrderBeanIn);
			CancelOrderResponse unReservationResp = wsclient.numberUnReservation(externalConfig.getMessage("service.create.number.create.numberunreservation", null, Locale.ENGLISH), unResRequest, NUMBER_UN_RESERVATION_SERVICE_CONTEXT);
			LOGGER.info("Number Un Reservation Response Code : {}", unReservationResp.getRESPONSECODE());
			LOGGER.info("Number Un Reservation Response Message : {}", unReservationResp.getRESPONSEMSG());
			
			LOGGER.info("End Number Unreserve Postpaid Service request ");
		}
		
		LOGGER.info("createB2BSubscribe Request msisdn :{}, custCode :{}, civilId :{}", msisdn, custCode, civilId);
		CreateB2BSubscriberRequest nB2bResRequest = prepareB2BSubscribeRequest(msisdn,custCode, civilId, simNo, promoId, isEpr);
       
		/*
		 * StringWriter sw = new StringWriter(); JAXB.marshal(nB2bResRequest, sw);
		 * String xmlString = sw.toString();
		 * 
		 * LOGGER.info("Create B2B Subscribe Request xml: "+xmlString);
		 */
		
		CreateB2BSubscriberResponse respB2BSubscribe = wsclient.b2bcprSubscriber(externalConfig.getMessage("service.create.number.create.b2bcprsubscriber", null, Locale.ENGLISH), nB2bResRequest, CREATE_B2BCPR_SUBSCRIBER_SERVICE_CONTEXT);
		
		/*
		 * sw = new StringWriter(); JAXB.marshal(respB2BSubscribe, sw); String
		 * respB2BSubscribexmlString = sw.toString();
		 * LOGGER.info("Create B2B Subscribe Response xml: "+respB2BSubscribexmlString);
		 */
		
		//TODO for client at server
		
		//TODO do we required prepareCreateOrderRequest for createB2BCPR?
		/*LOGGER.info("Invoking create order request");
		CreateOrderRequestType createOrderReq = prepareCreateOrderRequest(billingAccountId, accountId, serviceAccountId, bscsCustNum, msisdn, simNo, senderId, promoId, subscriberType, null);
		CreateOrderResponseType cOrderResp =  wsclient.createOrder(externalConfig.getMessage("service.create.number.create.order", null, Locale.ENGLISH), createOrderReq, CREATE_ORDER_SERVICE_CONTEXT);
		
		String siebelRowId = cOrderResp.getSpcId();
		LOGGER.info("Prepaid number with MSISDN {} ans SIM {} successfully created with siebel row id -{}", msisdn, simNo, siebelRowId);
		
		return cOrderResp;
		*/
		return respB2BSubscribe;
	}
	

	
	public com.ooredoo.service.wsclient.createb2bsubscriberproxy.CreateB2BSubscriberRequest prepareB2BSubscribeRequest(String msisdn,String custCode, String civilId, String simNo, String promoId, String isEpr){
		
		com.ooredoo.service.wsclient.createb2bsubscriberproxy.CreateB2BSubscriberRequest nB2bResRequest = new com.ooredoo.service.wsclient.createb2bsubscriberproxy.CreateB2BSubscriberRequest();
	
	nB2bResRequest.setTransactionID("22462cd870e717cd");
	nB2bResRequest.setTransactionDateTime("2022-07-12T17:37:44.000Z");
	nB2bResRequest.setChannel("B2BPORTAL");
	nB2bResRequest.setGenerateSAF("N");
	nB2bResRequest.setDigitalySigned("N");
	
		ReserveNumberType resType = new ReserveNumberType();
		resType.setAccountId("1-1BKKZ0K1");
		resType.setAccountType("Corporate");
		resType.setMsisdn(MSISDN_PREFIX+msisdn);
		resType.setLoginId("23431312");
		resType.setDealerLocatorId("Corporate");
		resType.setActualDealerId("999999");
	
	
	
		CreateSubscriberType crType=new CreateSubscriberType();
			CreateSubscriberInput crInput= new CreateSubscriberInput();
			crInput.setCreateSubscriberFlag("Y");
		
				AccountInfo acountInfo=new AccountInfo();
					AccountDetails acctDetails = new AccountDetails();
						acctDetails.setIDType("Civil ID");
						acctDetails.setVisaType("18");
						acctDetails.setIDNumber(civilId);//civilID from getcustomercid service
			    acountInfo.setAccountDetails(acctDetails);		
						/*AccountBusinessDetails acctBussinessDetails=new AccountBusinessDetails();
						  	AccountBusinessAddress accountBusinessAddress= new AccountBusinessAddress();
						  	accountBusinessAddress.setBlock("NA");
						  	accountBusinessAddress.setDeliveryType("Normal");
						acctBussinessDetails.setAccountBusinessAddress(accountBusinessAddress);
				   
			    acctDetails.setAccountBusinessDetails(acctBussinessDetails);*/
						
			crInput.setAccountInfo(acountInfo);
			crInput.setLargeAccountCustCode(custCode);//5.12444
			crInput.setCivilIDNumber(civilId);//223389675849
			//If EPR will set paymentResponsible
			LOGGER.info("=============Is B2B ERP============ : {} ",isEpr);
			if(isEpr.equals("EPR") ) {
				crInput.setPaymentResponsible("Y");
			}else {
				crInput.setPaymentResponsible("N");
			}
			
		crType.setCreateSubscriberInput(crInput);
		
			CreateOrderType createOrderType = new CreateOrderType();
			
				CreateOrderInput createOrderInput = new CreateOrderInput();
				createOrderInput.setSIMType("Physical");
				createOrderInput.setMsisdn(MSISDN_PREFIX+msisdn);
		createOrderInput.setPromotionId(promoId/* "1-1B0D6TK1" */);
				createOrderInput.setLocation("B2B_Location");
		createOrderInput.setRatePlanId(""/* "1-11IOYY5V" */);
				createOrderInput.setAccId("");
				createOrderInput.setSimNumber(simNo);//simNUmber
				createOrderType.setCreateOrderInput(createOrderInput);	
	
		nB2bResRequest.setReserveNumberType(resType);
	    nB2bResRequest.setCreateSubscriberType(crType);
	    nB2bResRequest.setCreateOrderType(createOrderType);
	
			
	return nB2bResRequest;
	}
	//AP::
	public String simSerialReservation(String serialNumber) throws NoSuchMessageException, ApplicationException{
		LOGGER.info("Invoking sim serial reservation service start ,serialNumber: {} ",serialNumber);
		String msg="";
		try {
			
			//TODO step 1 process
			//InputParameters nResRequest = csCleanupService.createSimeSerialRequest(serialNumber);
			
			
		//com.ooredoo.service.wsclient.oraappservicenmtc.ObjectFactory of = new com.ooredoo.service.wsclient.oraappservicenmtc.ObjectFactory();
		
		//InputParameters nResRequest =  of.createInputParameters();
			
			
			//TODO step 2 process
			InputParameters nResRequest =  new InputParameters();
		LOGGER.info(" sim serial reservation service nResRequest"+nResRequest);

		
		nResRequest.setPINTGREFERENCEID("1-1BKKZ0JX");
		nResRequest.setPSIEBELORDID("29032022022501");
		nResRequest.setPSIEBELORDTYPE("ReserveDevice");
		
		APPSRECTABVAR pinrecItemList = new APPSRECTABVAR();
		
			APPSRECVAR pinrecItem =new APPSRECVAR();
			pinrecItem.setPSIEBELITEMID("3CUT_RAW_SIM");
			pinrecItem.setPSIEBELSERIALNUM(serialNumber);//serialNumber
			pinrecItem.setPSIEBELORDERID("1-1D6275M4");
			pinrecItem.setPINTGREFID("Cleanup.1-1BKKZ0JX.ReserveDevice.B2B");
			pinrecItem.setPTXNTYPE("B2B_RESERVE_SIM");
			
		pinrecItemList.getPINRECITEM().add(pinrecItem);
		
		nResRequest.setPINREC(pinrecItemList);
		
		com.ooredoo.service.wsclient.oraappservicenmtc.OutputParameters resp = wsclient.simSerialReservation(externalConfig.getMessage("service.create.number.create.simserialreservation", null, Locale.ENGLISH), nResRequest, SIM_SERIAL_RESERVATION_SERVICE_CONTEXT);
		LOGGER.info("sim serial Reservation Status : {}", resp);
		
		if(resp != null) {
		//msg = resp.getPACKRETCODE().getValue();
			msg = resp.getPACKRETCODE();
		}

		}
		catch(Exception e) {
			LOGGER.error("sim serial Reservation Error : {}", e.getStackTrace());
			e.printStackTrace();
		}
		LOGGER.info("End sim serial reservation service msg: "+msg);
		return msg;
	}
	
	/**
	 * 
	 * @param profile
	 * @return
	 * @throws ApplicationException
	 */
	public CreateOrderResponseType createSubscriberProfile(NewSubscriberProfile profile) throws ApplicationException {
		String siebelRowId = null;
		
		String senderId = externalConfig.getMessage("service.create.number.create.subscriber.senderId", null, Locale.ENGLISH);
		
		CreateSubscriberRequestType req = prepareCreateSubscriberRequest(senderId, profile);
		
		CreateSubscriberResponseType cSubsResp =  wsclient.createSubscriber(externalConfig.getMessage("service.create.number.create.subscriber", null, Locale.ENGLISH), req, CREATE_SUBSCRIBER_SERVICE_CONTEXT);
		
		String billingAccountId = cSubsResp.getBillingAccountId();
		String accountId = cSubsResp.getAccountId();
		String serviceAccountId = cSubsResp.getServiceAccountId();
		String bscsCustNum = cSubsResp.getBSCSCustNumber();
		
		//Invoke Number Reservation
		NumberReservaionRequest nResRequest = new NumberReservaionRequest();
		NumberReservaionBeanIn bI = new NumberReservaionBeanIn();
		bI.setACCOUNTID(accountId);
		bI.setACCOUNTTYPE("Residential");
		
		SimpleDateFormat sdf = new SimpleDateFormat(DF_YYYYMMDD);
		sdf.setLenient(false);
		bI.setACTUALDEALERID(sdf.format(new Date()));
		bI.setDEALERLOCATORID("Corporate");
		bI.setACTUALDEALERID(profile.getDealerId());
		bI.setLOGINID("CleanupTool");
		bI.setMSISDN(profile.getMsisdn());
		bI.setRESERVATIONSTATUS("R");
		
		nResRequest.setNumberReservaionBeanIn(bI);
		
		NumberReservaionResponse resp = wsclient.numberReservation(externalConfig.getMessage("service.create.number.create.numberreservation", null, Locale.ENGLISH), nResRequest, NUMBER_RESERVATION_SERVICE_CONTEXT);
		LOGGER.info("Number Reservation Status : {}", resp.getNumberReservaionBeanOut().getStatus());
		
		LOGGER.info("Invoking create order request");
		CreateOrderRequestType createOrderReq = prepareCreateOrderRequest(billingAccountId, accountId, serviceAccountId, bscsCustNum, profile.getMsisdn(), profile.getSimNo(), senderId, profile.getPromoId(), profile.getSubscriberType(), profile.getTransactionNo());
		CreateOrderResponseType cOrderResp =  wsclient.createOrder(externalConfig.getMessage("service.create.number.create.order", null, Locale.ENGLISH), createOrderReq, CREATE_ORDER_SERVICE_CONTEXT);
		
		siebelRowId = cOrderResp.getSpcId();
		LOGGER.info("Prepaid number with MSISDN {} ans SIM {} successfully created with siebel row id -{}", profile.getMsisdn(), profile.getSimNo(), siebelRowId);
		
		return cOrderResp;
	}

	private CreateOrderRequestType prepareCreateOrderRequest(String billingAccountId, String accountId, String serviceAccountId, String bscsCustNum, String msisdn, String simNo, String senderId, String promoId, String subscriberType, String transactionNo) {
		ObjectFactory of = new ObjectFactory();
		CreateOrderRequestType req = of.createCreateOrderRequestType();
		com.ooredoo.createorder.CommonComponentsType cc = of.createCommonComponentsType();
		try {
			cc.setOrderDateTime(ApplicationUtility.getXmlgregCal(new Date()));
		} catch(Exception e) {
			LOGGER.error("Error occurred while getting request date");
		}
		
		if(null != transactionNo && !transactionNo.isEmpty()) {
			cc.setProcessingNumber(transactionNo);
		} else {
			cc.setProcessingNumber("TEST1234");
		}
		
		cc.setSenderID(senderId);
		req.setCommonComponents(cc);
		
		CreateOrderCriteriaType co = new CreateOrderCriteriaType();
		
		
		co.getContent().add(of.createCreateOrderCriteriaTypeSIMDelivery("N"));
		co.getContent().add(of.createCreateOrderCriteriaTypeSpcNumber(simNo));
		co.getContent().add(of.createCreateOrderCriteriaTypeMSISDN(msisdn));
		
		if(null != subscriberType && !subscriberType.isEmpty()) {
			co.getContent().add(of.createCreateOrderCriteriaTypeLineType(subscriberType));
		} else {
			co.getContent().add(of.createCreateOrderCriteriaTypeLineType("PREPAID"));
		}
		
		
		co.getContent().add(of.createCreateOrderCriteriaTypePromotionSpcId(promoId));
		//co.getContent().add(of.createCreateOrderCriteriaTypeRateSpcPlanSpcId("1-EDC8CWY"));
		
		OrderEntryOrdersType oe = new OrderEntryOrdersType();
		oe.setAccountId(accountId);
		oe.setServiceAccountId(serviceAccountId);
		oe.setBillingAccountId(billingAccountId);
		oe.setStatus("Pending");
		oe.setDigitalChannel(senderId);
		
		OrderEntryAttachmentType oea = new OrderEntryAttachmentType();
		oea.setAutoUpdFlg("Y");
		oea.setFileDeferFlg("R");
		oea.setFileExt("txt");
		oe.getOrderEntryAttachment().add(oea);
		
		PaymentsssType pmt = new PaymentsssType();
		PaymentsDigitalType pdt = new PaymentsDigitalType();
		pdt.setTransactionAmount("5.200");
		pdt.setPaymentStatus("Pending");
		pdt.setPaymentMethod("KIOSK CC NBK");
		pdt.setPaymentType("Account Payment");
		pdt.setCreatedByName("NKIOSK");
		pdt.setKIOSKRef("NEW_ KIOSEK_1");
		pdt.setAuthorizationCode("T6428");
		pdt.setCreditCardExpirationDate("06/25/2021");
		pdt.setCreditCardHolderName("ALI ALI");
		pdt.setCreditCardNumber("************2563");
		pdt.setIssuingBankName("National Bank of Kuwait");
		pdt.setExpirationDate("25");
		pdt.setExpirationMonth("6");
		pdt.setExpirationYear("2021");
		pmt.getPaymentsDigital().add(pdt);
		
		oe.setPaymentss(pmt);
		
		co.getContent().add(of.createCreateOrderCriteriaTypeOrderEntryOrders(oe));
		req.setCreateOrderCriteria(co);
		return req;
	}

	private CreateSubscriberRequestType prepareCreateSubscriberRequest(String senderId, NewSubscriberProfile profile) {
		CreateSubscriberRequestType req = new CreateSubscriberRequestType();
		
		CommonComponentsType cc = new CommonComponentsType();
		cc.setSenderID(senderId);
		//cc.setProcessingNumber("T040520200002");
		cc.setProcessingNumber(profile.getTransactionNo());
		
		try {
			cc.setOrderDateTime(ApplicationUtility.getXmlgregCal(new Date()));
		} catch(Exception e) {
			LOGGER.error("Error occurred while getting request date");
		}
		
		req.setCommonComponents(cc);
		
		CreateSubscriberCriteriaType sc = new CreateSubscriberCriteriaType();
		
		sc.setSubAccountPassword("1234");
		sc.setCardReaderFlag("Y");
		sc.setBSCSBillingAccNum("1245789");
		sc.setSIDType("Civil ID");
		//sc.setCivilId("283010112777");
		sc.setCivilId(profile.getIdValue());
		
		IdentificationType it = new IdentificationType();
		it.setIdType("Civil ID");
		//it.setIdValue("283010112777");
		it.setIdValue(profile.getIdValue());
		sc.setIdentification(it);
		
		PostpaidAccountInfoType ppa = new PostpaidAccountInfoType();
		ppa.setName("NC Subscriber Test");
		ppa.setType("Residential");
		ppa.setCustomerAccountGroup("NEW");
		ppa.setCustomerSubType("Normal");
		//ppa.setVisaType("18");
		ppa.setVisaType(profile.getVisaType());
		
		//Calendar c = Calendar.getInstance();
		//c.setTime(new Date());
		//c.add(Calendar.MONTH, 8);
		//ppa.setIDExpirationDate(new SimpleDateFormat("MM/dd/yyyy").format(c.getTime()));
		ppa.setIDExpirationDate(profile.getIdExpirationDate());
		//ppa.setIDNumber("283010112777");
		ppa.setIDNumber(profile.getIdValue());
		ppa.setIDType("Civil ID");
		
		AccountBusinessAddressType aba = new AccountBusinessAddressType();
		aba.setApartmentNumber("21");
		aba.setStreetAddress("Essa Al Qatami Street");
		aba.setArea("Hawalli");
		aba.setAvenue("A1");
		aba.setBlock("6");
		aba.setFloor("1");
		aba.setGovernorate("Hawalli");
		aba.setRoom("1");
		ppa.setAccountBusinessAddress(aba);
		
		ContactType cont = new ContactType();
		cont.setDateofBirth("03/25/1986");
		//cont.setEmailAddress("nctest@gmail.com");
		cont.setEmailAddress(profile.getEmail());
		cont.setFirstName(profile.getFirstName());
		//cont.setMiddleName("TEST");
		cont.setLastName(profile.getLastName());
		cont.setJobTitle("eng");
		cont.setLanguage(profile.getLanguage());
		cont.setMF("Male");
		cont.setMM(profile.getSalutation());
		cont.setAlternatePhone("00196597631404");
		cont.setAlternatePhone2("00196597631404");
		cont.setNationality(profile.getNationality());
		
		ppa.setContact(cont);
		
		CutServiceSubAccountsType cssa = new CutServiceSubAccountsType();
		cssa.setName(profile.getFirstName() + " " + profile.getLastName());
		cssa.setPassword("1234");
		ppa.setCutServiceSubAccounts(cssa);
		
		sc.setPostpaidAccountInfo(ppa);
		
		req.setCreateSubscriberCriteria(sc);
		return req;
	}
	
	private CreateSubscriberRequestType prepareCreateSubscriberRequest(String senderId, String civilId) {
		CreateSubscriberRequestType req = new CreateSubscriberRequestType();
		
		CommonComponentsType cc = new CommonComponentsType();
		cc.setSenderID(senderId);
		cc.setProcessingNumber("T040520200002");
		//cc.setProcessingNumber(profile.getTransactionNo());
		
		try {
			cc.setOrderDateTime(ApplicationUtility.getXmlgregCal(new Date()));
		} catch(Exception e) {
			LOGGER.error("Error occurred while getting request date");
		}
		
		req.setCommonComponents(cc);
		
		CreateSubscriberCriteriaType sc = new CreateSubscriberCriteriaType();
		
		sc.setSubAccountPassword("1234");
		sc.setCardReaderFlag("Y");
		sc.setBSCSBillingAccNum("1245789");
		sc.setSIDType("Civil ID");
		//sc.setCivilId("283010112777");
		sc.setCivilId(civilId);
		
		IdentificationType it = new IdentificationType();
		it.setIdType("Civil ID");
		//it.setIdValue("283010112777");
		it.setIdValue(civilId);
		sc.setIdentification(it);
		
		PostpaidAccountInfoType ppa = new PostpaidAccountInfoType();
		ppa.setName("NC Subscriber Test");
		ppa.setType("Residential");
		ppa.setCustomerAccountGroup("NEW");
		ppa.setCustomerSubType("Normal");
		ppa.setVisaType("18");
		
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, 8);
		ppa.setIDExpirationDate(new SimpleDateFormat("MM/dd/yyyy").format(c.getTime()));
		//ppa.setIDNumber("283010112777");
		ppa.setIDType("Civil ID");
		ppa.setIDNumber(civilId);
		
		AccountBusinessAddressType aba = new AccountBusinessAddressType();
		aba.setApartmentNumber("21");
		aba.setStreetAddress("Essa Al Qatami Street");
		aba.setArea("Hawalli");
		aba.setAvenue("A1");
		aba.setBlock("6");
		aba.setFloor("1");
		aba.setGovernorate("Hawalli");
		aba.setRoom("1");
		ppa.setAccountBusinessAddress(aba);
		
		ContactType cont = new ContactType();
		cont.setDateofBirth("03/25/1986");
		cont.setEmailAddress("nctest@gmail.com");
		cont.setFirstName("NC");
		cont.setMiddleName("TEST");
		cont.setLastName("NUMBER");
		cont.setJobTitle("eng");
		cont.setLanguage("English");
		cont.setMF("Male");
		cont.setMM("Mr.");
		cont.setAlternatePhone("00196597631404");
		cont.setAlternatePhone2("00196597631404");
		cont.setNationality("Syria");
		
		ppa.setContact(cont);
		
		CutServiceSubAccountsType cssa = new CutServiceSubAccountsType();
		cssa.setName("NC TEST NUMBER");
		cssa.setPassword("1234");
		ppa.setCutServiceSubAccounts(cssa);
		
		sc.setPostpaidAccountInfo(ppa);
		
		req.setCreateSubscriberCriteria(sc);
		return req;
	}
	
	public B2CSubscriberOnboardingResponse findCleanOnboardB2CProfile(B2CSubscriberOnboardingRequest req) throws ApplicationException {
		B2CSubscriberOnboardingResponse resp = new B2CSubscriberOnboardingResponse();
		
		//Extract Number Pool
		NumberPool np = numberPoolService.getNumberPoolByMsisdn(req.getMsisdn());
		
		if(null != np) {
			resp.setMsisdn(req.getMsisdn());
			resp.setSimNo(np.getSimNo());
			resp.setImsi(np.getImsi());
			
			//Clean-up Number Profile.
			SubscriberProfileCleanupRequest pcReq = new SubscriberProfileCleanupRequest();
			pcReq.setRequestDate(new Date());
			pcReq.setRequestUsername(req.getUsername());
			
			String nodeString = externalConfig.getMessage("service.cleanup.availabe.system", null, Locale.getDefault());
			String[] splitString = nodeString.split(COMMA);
			
			pcReq.setNodes(Arrays.asList(splitString));
			
			List<SubscriberProfileDetails> profileList = new ArrayList<SubscriberProfileDetails>();
			SubscriberProfileDetails profile = new SubscriberProfileDetails();
			profile.setSubscriberType("PO");
			profile.setMsisdn(MSISDN_PREFIX + req.getMsisdn());
			profile.setMsisdnWithoutPrefix(req.getMsisdn());
			profile.setImsi(np.getImsi());
			profile.setSimNo(np.getSimNo());
			profile.setSelecedNmsPool("KIOSK MACHINE POOL");
			profile.setSelectedDealer("999999");
			profile.setPrice(0D);
			profile.setSelectedErpLocation("POS.ESHOP.MAIN");
			profileList.add(profile);
			
			pcReq.setProfiles(profileList);

			SubscriberProfileCleanupResponse cleanupResp = profileCleanupService.handleCleanup(pcReq, true);
			
			if(null != cleanupResp && null != cleanupResp.getCleanupDetails() && !cleanupResp.getCleanupDetails().isEmpty()) {
				CleanupDetails  cleanupDetails = cleanupResp.getCleanupDetails().get(MSISDN_PREFIX + req.getMsisdn());
				
				for(NodalCleanupDetails e : cleanupDetails.getCleanupDetails()) {
					if(null != e.getStatus() && !e.getStatus().isEmpty()) {
						if(e.getStatus().equals(SUCCESS_LEBEL)) {
							LOGGER.debug("Nodal cleanup is successfull for Node - " + e.getNode() + " for MSISDN - " + req.getMsisdn());
						} else {
							throw new ApplicationException(e.getResponse(), true);
						}
					} else {
						throw new ApplicationException("System could not determine the cleanup status for node- " + e.getNode(), true);
					}
				}
				
				//Place Siebel order
				CreateOrderResponseType orderResp = createPrepaidNumber(req.getMsisdn(), np.getSimNo(), req.getPromoId(), req.getCivilId(), req.getSubscriberType());
				
				if(null != orderResp) {
					resp.setSiebelRowId(orderResp.getSpcId());
					resp.setSiebelOrderId(orderResp.getSpcType());
					resp.setStatus("OK");
				} else {
					resp.setStatus("Unable to determine subscriber onboarding status");
				}
				
			} else {
				throw new ApplicationException("System could not retrieve the cleanup details", true);
			}			
		} else {
			throw new ApplicationException("MSISDN not avaailable in system pool. Please add the same in pool", true);
		}
		
		
		return resp;
	}
	
	public B2BSubscriberOnboardingResponse findCleanOnboardB2BProfile(B2BSubscriberOnboardingRequest req) throws ApplicationException, DatatypeConfigurationException {
		B2BSubscriberOnboardingResponse resp = new B2BSubscriberOnboardingResponse();
		LOGGER.info(" findCleanOnboardB2BProfile service req :::"+req.getCustCode());
		//Extract Number Pool
		NumberPool np = numberPoolService.getNumberPoolByMsisdn(req.getMsisdn());
		LOGGER.info("Msisdn:::"+np.getMsisdn());
		if(null != np) {
			resp.setMsisdn(req.getMsisdn());
			resp.setSimNo(np.getSimNo());
			resp.setImsi(np.getImsi());
			
			LOGGER.info("reqmsisdn {}, simno {}, imsi{}  ::::",req.getMsisdn(),np.getSimNo(), np.getImsi() );
			
			//Clean-up Number Profile.
			SubscriberProfileCleanupRequest pcReq = new SubscriberProfileCleanupRequest();
			pcReq.setRequestDate(new Date());
			pcReq.setRequestUsername(req.getUsername());
			
			String nodeString = externalConfig.getMessage("service.cleanup.availabe.system", null, Locale.getDefault());
			String[] splitString = nodeString.split(COMMA);
			
			pcReq.setNodes(Arrays.asList(splitString));
			
			List<SubscriberProfileDetails> profileList = new ArrayList<SubscriberProfileDetails>();
			SubscriberProfileDetails profile = new SubscriberProfileDetails();
			profile.setSubscriberType("PO");
			profile.setMsisdn(MSISDN_PREFIX + req.getMsisdn());
			profile.setMsisdnWithoutPrefix(req.getMsisdn());
			profile.setImsi(np.getImsi());
			profile.setSimNo(np.getSimNo());
			profile.setSelecedNmsPool("KIOSK MACHINE POOL");
			profile.setSelectedDealer("999999");
			profile.setPrice(0D);
			//profile.setSelectedErpLocation("POS.ESHOP.MAIN");//"POS.ESHOP.MAIN"
			profile.setSelectedErpLocation("CORP.WT-MJRACT.MAIN");
			profileList.add(profile);
			
			pcReq.setProfiles(profileList);

			SubscriberProfileCleanupResponse cleanupResp = profileCleanupService.handleCleanup(pcReq, true);
			LOGGER.info("cleanupResp {}  ::::",cleanupResp );
			if(null != cleanupResp && null != cleanupResp.getCleanupDetails() && !cleanupResp.getCleanupDetails().isEmpty()) {
				CleanupDetails  cleanupDetails = cleanupResp.getCleanupDetails().get(MSISDN_PREFIX + req.getMsisdn());
				LOGGER.info("cleanupDetails {}  ::::",cleanupDetails );
				for(NodalCleanupDetails e : cleanupDetails.getCleanupDetails()) {
					if(null != e.getStatus() && !e.getStatus().isEmpty()) {
						if(e.getStatus().equals(SUCCESS_LEBEL)) {
							LOGGER.debug("Nodal cleanup is successfull for Node - " + e.getNode() + " for MSISDN - " + req.getMsisdn());
						} else {
							throw new ApplicationException(e.getResponse(), true);
						}
					} else {
						throw new ApplicationException("System could not determine the cleanup status for node- " + e.getNode(), true);
					}
				}
				
				//Place Siebel order
				//CreateOrderResponseType orderResp = createPrepaidNumber(req.getMsisdn(), np.getSimNo(), req.getPromoId(), req.getCivilId(), req.getSubscriberType());
				LOGGER.info("reqmsisdn :{}, simno :{}, imsi :{}  req.getPromoId :{}, req.getCivilId :{}, req.getSubscriberType :{},req.getCustCode :{} ::::",req.getMsisdn(),np.getSimNo(), np.getImsi(),req.getPromoId(), req.getCivilId(), req.getSubscriberType(),req.getCustCode() );
				CreateB2BSubscriberResponse createB2BSubscriberResponse = createB2BCPR(req.getMsisdn(), np.getSimNo(), req.getPromoId(), req.getCivilId(), req.getSubscriberType(),req.getCustCode(),req.getSuscriber());
				LOGGER.info("createB2BSubscriberResponse {}  ::::",createB2BSubscriberResponse );
				if (null != createB2BSubscriberResponse) {
					if (createB2BSubscriberResponse.getResponseCode()
							.equalsIgnoreCase(SUBS_DATA_EXT_SERVICE_SUCCESS_ECODE)) {
						resp.setStatus("OK");
						if (null != createB2BSubscriberResponse.getCreateOrderType()) {
							resp.setSiebelOrderId(createB2BSubscriberResponse.getCreateOrderType().getOrderID()); //oderId
							resp.setSiebelRowId(createB2BSubscriberResponse.getCreateOrderType().getOrderNumber());//OrderNumber
						}

					} else {
						resp.setStatus(createB2BSubscriberResponse.getResponseMessage());
					}

				} else {
					resp.setStatus("Unable to determine subscriber onboarding status");
				}
				
			} else {
				throw new ApplicationException("System could not retrieve the cleanup details", true);
			}			
		} else {
			throw new ApplicationException("MSISDN not avaailable in system pool. Please add the same in pool", true);
		}
		
		LOGGER.info("resp status ::::"+resp.getStatus());
		return resp;
	}
	
	public GetB2BCustomerCIDRequest prepareB2BCustomerCIDRequest(String custCode){
		GetB2BCustomerCIDRequest getB2BCustomerCIDRequest = new GetB2BCustomerCIDRequest();
			getB2BCustomerCIDRequest.setCustCode(custCode);
			getB2BCustomerCIDRequest.setChannel("B2BPORTAL");
			
		return getB2BCustomerCIDRequest;
	}
}
