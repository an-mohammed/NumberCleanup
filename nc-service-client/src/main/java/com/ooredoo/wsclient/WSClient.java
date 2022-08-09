package com.ooredoo.wsclient;

import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBElement;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.WebServiceIOException;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.drutt.ws.msdp.userprofile.SetMetasResponse;
import com.huawei.hss.LSTHSUB;
import com.huawei.hss.LSTHSUBResponse;
import com.huawei.hss.RMVHHSSSUB;
import com.huawei.hss.RMVHHSSSUBResponse;
import com.huawei.hss.RMVSUB;
import com.huawei.hss.RMVSUBResponse;
import com.ooredoo.createorder.CreateOrderRequestType;
import com.ooredoo.createorder.CreateOrderResponseType;
import com.ooredoo.createsubs.CreateSubscriberRequestType;
import com.ooredoo.createsubs.CreateSubscriberResponseType;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.utility.ApplicationConstants;
import com.ooredoo.nc.utility.ApplicationUtility;
import com.ooredoo.service.wsclient.b2bcustomercid.GetB2BCustomerCIDRequest;
import com.ooredoo.service.wsclient.b2bcustomercid.GetB2BCustomerCIDResponse;
import com.ooredoo.service.wsclient.createb2bsubscriberproxy.CreateB2BSubscriberResponse;
import com.ooredoo.service.wsclient.createb2bsubscriberproxy.CreateB2BSubscriberResponse.CreateOrderType;
import com.ooredoo.service.wsclient.numberunreservepostpaidservice.CancelOrderRequest;
import com.ooredoo.service.wsclient.numberunreservepostpaidservice.CancelOrderResponse;
import com.ooredoo.wsclient.anaonboarding.AnaOnboardingRequest;
import com.ooredoo.wsclient.anaonboarding.AnaOnboardingResponse;
import com.ooredoo.wsclient.dashboardhlr.HLRResponseType;
import com.ooredoo.wsclient.dashboardhlr.HLRrequestType;
import com.ooredoo.wsclient.downstreamprofilecheck.DownStreamProfileCheckRequest;
import com.ooredoo.wsclient.downstreamprofilecheck.DownStreamProfileCheckResponse;
import com.ooredoo.wsclient.emailwithattachmentproxy.EmailWithAttachment;
import com.ooredoo.wsclient.emailwithattachmentproxy.EmailWithAttachmentResponse;
import com.ooredoo.wsclient.getcustomerprofile.proxy.GetCustomerProfile;
import com.ooredoo.wsclient.getcustomerprofile.proxy.GetCustomerProfileResponse;
import com.ooredoo.wsclient.numberreservation.NumberReservaionRequest;
import com.ooredoo.wsclient.numberreservation.NumberReservaionResponse;
import com.ooredoo.wsclient.receivesdmdatarbt.ReceiveSDMDataRBTRequest;
import com.ooredoo.wsclient.receivesdmdatarbt.ReceiveSDMDataRBTResponse;
import com.ooredoo.wsclient.spr.InputParameters;
import com.ooredoo.wsclient.spr.OutputParameters;

import kw.com.ooredoo.esmoffers.QuerySubscriptionElement;
import kw.com.ooredoo.esmoffers.QuerySubscriptionResponseElement;

@Component
public class WSClient implements ApplicationConstants {
	
	@Autowired
	private OSBServiceClient osbClient;
	
	@Autowired
	private BSCSServiceClient bscsClient;
	
	@Autowired
	private ESMServiceClient esmClient;
	
	@Autowired
	private PCRFServiceClient pcrfClient;
	
	@Autowired
	private CSServiceClient csServiceClient;
	
	@Autowired
	private SPRServiceClient sprServiceClient;
	
	@Autowired
	private HLRServiceClient hlrServiceClient;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WSClient.class);
	
	
	/**
	 * 
	 * @param url
	 * @param req
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public GetCustomerProfileResponse getCustomerProfile(String url, GetCustomerProfile req, String context) throws ApplicationException {
		
		GetCustomerProfileResponse resp = null;
		
		try {
			Object responseObject = getOsbClient().callWebService(url, req, context);
			
			if(null != responseObject) {
				resp = (GetCustomerProfileResponse) responseObject;
				
				if(!resp.getServiceResponse().getResponseCode().equals(SUBS_DATA_EXT_SERVICE_SUCCESS_ECODE) 
						&& !resp.getServiceResponse().getResponseMessage().equals(SUBS_DATA_EXT_SERVICE_SUCCESS_EMSG)) {
					throw new ApplicationException(resp.getServiceResponse().getResponseMessage(), true);
				}
			}
		} catch(WebServiceIOException wsIOE) {
			throw new ApplicationContextException(wsIOE.getMessage(), wsIOE);
			
		}catch(Exception e) {
			throw new ApplicationContextException(e.getMessage(), e);
		}
		
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	public ReceiveSDMDataRBTResponse getCustomerInfo(String url, ReceiveSDMDataRBTRequest req, String context) throws ApplicationException {
		
		ReceiveSDMDataRBTResponse resp = null;
		
		try {
			Object responseObject = getBscsClient().callWebService(url, req, context);
			
			if(null != responseObject) {
				JAXBElement<ReceiveSDMDataRBTResponse> root = (JAXBElement<ReceiveSDMDataRBTResponse>) responseObject;
				resp = root.getValue();
			}
		} catch(SoapFaultClientException s) {
			throw new ApplicationException(s.getFaultStringOrReason(), true);
		}catch(Exception e) {
			throw new ApplicationContextException(e.getMessage(), e);
		}
		
		return resp;
	}
	
	/**
	 * 
	 * @param url
	 * @param req
	 * @param context
	 * @return
	 * @throws ApplicationException
	 */
	public EmailWithAttachmentResponse sendMailNotification(String url, EmailWithAttachment req, String context) throws ApplicationException {
		
		EmailWithAttachmentResponse response = null;
		
		try {
			Object responseObject = getOsbClient().callWebService(url, req, context);
			
			if(null != responseObject) {
				response = (EmailWithAttachmentResponse) responseObject;
			} else {
				throw new ApplicationException("DDS.088");
			}
		} catch(WebServiceIOException wsIOE) {
			throw new ApplicationContextException(wsIOE.getMessage(), wsIOE);
			
		}catch(Exception e) {
			throw new ApplicationContextException(e.getMessage(), e);
		}
		
		return response;
	}
	
	public com.ooredoo.wsclient.esmprofile.DeleteUserProfileResponse deleteEsmProfile(String url, com.ooredoo.wsclient.esmprofile.DeleteUserProfile req, String context) throws ApplicationException {
		
		com.ooredoo.wsclient.esmprofile.DeleteUserProfileResponse response = null;
		
		try {
			Object responseObject = getEsmClient().callWebService(url, req, context);
			
			if(null != responseObject) {
				response = (com.ooredoo.wsclient.esmprofile.DeleteUserProfileResponse) responseObject;
			} else {
				throw new ApplicationException("DDS.088");
			}
			
		} catch(SoapFaultClientException s) {
			throw new ApplicationException(s.getFaultStringOrReason(), true);
			
		}catch(WebServiceIOException wsIOE) {
			throw new ApplicationException(wsIOE.getMessage(), wsIOE, true);
			
		}catch(Exception e) {
			throw new ApplicationException(e.getMessage(), e);
			
		}
		
		return response;
	}
	
	public QuerySubscriptionResponseElement getEsmProfile(String url, QuerySubscriptionElement req, String context) throws ApplicationException {
		
		QuerySubscriptionResponseElement response = null;
		
		try {
			Object responseObject = getEsmClient().callWebService(url, req, context);
			
			if(null != responseObject) {
				response = (QuerySubscriptionResponseElement) responseObject;
			} else {
				throw new ApplicationException("DDS.088");
			}
			
		} catch(SoapFaultClientException s) {
			throw new ApplicationException(s.getFaultStringOrReason(), true);
			
		}catch(WebServiceIOException wsIOE) {
			throw new ApplicationException(wsIOE.getMessage(), wsIOE, true);
			
		}catch(Exception e) {
			throw new ApplicationException(e.getMessage(), e);
			
		}
		
		return response;
	}
	
	public SetMetasResponse setEsmMeta(String url, com.ooredoo.wsclient.esmprofile.SetMetas req, String context) throws ApplicationException {
		
		SetMetasResponse response = null;
		
		try {
			Object responseObject = getEsmClient().callWebService(url, req, context);
			
			if(null != responseObject) {
				response = (SetMetasResponse) responseObject;
			} else {
				throw new ApplicationException("DDS.088");
			}
			
		} catch(SoapFaultClientException s) {
			throw new ApplicationException(s.getFaultStringOrReason(), true);
			
		}catch(WebServiceIOException wsIOE) {
			throw new ApplicationException(wsIOE.getMessage(), wsIOE, true);
			
		}catch(Exception e) {
			throw new ApplicationException(e.getMessage(), e);
			
		}
		
		return response;
	}
	
	public com.ooredoo.wsclient.pcrf.DelsubscriberResponse deletePcrfProfile(String url, com.ooredoo.wsclient.pcrf.DelSubscriberRequest req, String context) throws ApplicationException {
		
		com.ooredoo.wsclient.pcrf.DelsubscriberResponse response = null;
		
		try {
			Object responseObject = getPcrfClient().callWebService(url, req, context);
			
			if(null != responseObject) {
				response = (com.ooredoo.wsclient.pcrf.DelsubscriberResponse) responseObject;
			} else {
				throw new ApplicationException("DDS.088");
			}
			
		} catch(WebServiceIOException wsIOE) {
			throw new ApplicationException(wsIOE.getMessage(), wsIOE, true);
			
		}catch(Exception e) {
			throw new ApplicationException(e.getMessage(), e);
			
		}
		
		return response;
	}
	
	@SuppressWarnings("unchecked")
	public void deleteSubscriberFromCs(String subscriberNumber) throws ApplicationException {
		 
		String CS_METHOD_NAME_DELETE_SUBS = "DeleteSubscriber";
		
		try {
			  Hashtable<String, Object> param = new Hashtable<String, Object>();
			  param.put("originHostName", this.getCsServiceClient().getCsServerConfig("service.cleanup.cs.originhostname"));
			  param.put("originNodeType", this.getCsServiceClient().getCsServerConfig("service.cleanup.cs.originnodetype"));
			  param.put("originTimeStamp", new Date());
			  param.put("originTransactionID", ApplicationUtility.generateRandomTransactionId());
			  param.put("subscriberNumber", subscriberNumber);
			  param.put("subscriberNumberNAI", new Integer(1));
			  param.put("originOperatorID", this.getCsServiceClient().getCsServerConfig("service.cleanup.cs.originoperatorid"));
			  param.put("barring", Boolean.FALSE);
		  
			  Object[] params = new Object[] {param};
			  
			  XmlRpcClient client = this.getCsServiceClient().getUcipTemplate();
			  
			  if(null != client) {
				  HashMap<String, Object> response = (HashMap<String, Object>) client.execute(CS_METHOD_NAME_DELETE_SUBS, params);
				  
				  LOGGER.info(response.toString());
				  
				  if(null != response && !response.isEmpty()) {
					  Integer responseCode = (Integer) response.get("responseCode");
					  
					  if(responseCode != 0) {
						  
						  if(responseCode == 102) {
							  LOGGER.info("Charging system response code : 102. Considering MSISDN doesn't exists in CS");
						  } else {
							  throw new ApplicationException("Charging system response Code : " + responseCode, true);
						  }
					  }
				  }
			  }
			} catch(XmlRpcException e) {
				throw new ApplicationException(e.getMessage(), true);
			}		
	}
	
	public OutputParameters deleteSubscriberFromSpr(String url, InputParameters req, String context) throws ApplicationException {

		OutputParameters response = null;
		
		try {
			Object responseObject = getSprServiceClient().callWebService(url, req, context);
			
			if(null != responseObject) {
				response = (OutputParameters) responseObject;
			} else {
				throw new ApplicationException("DDS.088");
			}
			
		} catch(WebServiceIOException wsIOE) {
			throw new ApplicationException(wsIOE.getMessage(), wsIOE, true);
			
		}catch(Exception e) {
			throw new ApplicationException(e.getMessage(), e);
			
		}
		
		return response;
	}
	
	public RMVSUBResponse rmvSubHlr(String url, RMVSUB req, String context) throws ApplicationException {

		RMVSUBResponse response = null;
		
		try {
			Object responseObject = getHlrServiceClient().callWebService(url, req, context);
			
			if(null != responseObject) {
				response = (RMVSUBResponse) responseObject;
			} else {
				throw new ApplicationException("DDS.088");
			}
			
		} catch(WebServiceIOException wsIOE) {
			throw new ApplicationException(wsIOE.getMessage(), wsIOE, true);
			
		}catch(Exception e) {
			throw new ApplicationException(e.getMessage(), e);
			
		}
		
		return response;
	}
	
	public LSTHSUBResponse lstHSUBHlr(String url, LSTHSUB req, String context) throws ApplicationException {

		LSTHSUBResponse response = null;
		
		try {
			Object responseObject = getHlrServiceClient().callWebService(url, req, context);
			
			if(null != responseObject) {
				response = (LSTHSUBResponse) responseObject;
			} else {
				throw new ApplicationException("DDS.088");
			}
			
		} catch(WebServiceIOException wsIOE) {
			throw new ApplicationException(wsIOE.getMessage(), wsIOE, true);
			
		}catch(Exception e) {
			throw new ApplicationException(e.getMessage(), e);
			
		}
		
		return response;
	}
	
	public RMVHHSSSUBResponse rmvHHSSSUBHlr(String url, RMVHHSSSUB req, String context) throws ApplicationException {

		RMVHHSSSUBResponse response = null;
		
		try {
			Object responseObject = getHlrServiceClient().callWebService(url, req, context);
			
			if(null != responseObject) {
				response = (RMVHHSSSUBResponse) responseObject;
			} else {
				throw new ApplicationException("DDS.088");
			}
			
		} catch(WebServiceIOException wsIOE) {
			throw new ApplicationException(wsIOE.getMessage(), wsIOE, true);
			
		}catch(Exception e) {
			throw new ApplicationException(e.getMessage(), e);
			
		}
		
		return response;
	}
	
	public void invokeRmvDnaPtrRec(String msisdnWithoutPrefix, String endPointUrl) throws ApplicationException {
		getHlrServiceClient().invokeRmvDnaPtrRec(msisdnWithoutPrefix, endPointUrl);
	}
	
	@SuppressWarnings("unchecked")
	public CreateSubscriberResponseType createSubscriber(String url, CreateSubscriberRequestType req, String context) throws ApplicationException {
		
		CreateSubscriberResponseType resp = null;
		
		try {
			Object responseObject = getOsbClient().callWebService(url, req, context);
			
			if(null != responseObject) {
				JAXBElement<CreateSubscriberResponseType> respJ = (JAXBElement<CreateSubscriberResponseType>) responseObject;
				resp = respJ.getValue();
				
				if(null != resp.getStatusInfo()) {
					if(null != resp.getStatusInfo().getStatusCode() && !resp.getStatusInfo().getStatusCode().isEmpty() && resp.getStatusInfo().getStatusCode().equals("0")) {
						LOGGER.info("Create subscriber request successfully executed");
					} else {
						throw new ApplicationException("Unable to create subscriber. Cause :" + resp.getStatusInfo().getStatusDesc(), true);
					}
				} else {
					throw new ApplicationException("Unable to retrieve create subscriber response status", true);
				}
			}
		} catch(WebServiceIOException wsIOE) {
			throw new ApplicationException(wsIOE.getMessage(), wsIOE, true);
			
		}catch(Exception e) {
			throw new ApplicationException(e.getMessage(), e, true);
		}
		
		return resp;
	}
	
	public NumberReservaionResponse numberReservation(String url, NumberReservaionRequest req, String context) throws ApplicationException {
		
		NumberReservaionResponse resp = null;
		
		try {
			Object responseObject = getOsbClient().callWebService(url, req, context);
			
			if(null != responseObject) {
				 resp = (NumberReservaionResponse) responseObject;
				
				if(null != resp.getNumberReservaionBeanOut()) {
					if(null != resp.getNumberReservaionBeanOut().getStatus() && !resp.getNumberReservaionBeanOut().getStatus().isEmpty() 
							&& resp.getNumberReservaionBeanOut().getStatus().equalsIgnoreCase("Success")) {
						LOGGER.info("Number Reservation request successfully executed");
					} else {
						throw new ApplicationException("Unable to create subscriber. Cause :" + resp.getNumberReservaionBeanOut().getErrorMessage(), true);
					}
				} else {
					throw new ApplicationException("Unable to retrieve create subscriber response status", true);
				}
			}
		} catch(WebServiceIOException wsIOE) {
			throw new ApplicationException(wsIOE.getMessage(), wsIOE, true);
			
		}catch(Exception e) {
			throw new ApplicationException(e.getMessage(), e, true);
		}
		
		return resp;
	}
	
public CancelOrderResponse numberUnReservation(String url, CancelOrderRequest req, String context) throws ApplicationException {
		
	CancelOrderResponse resp = null;
		
		try {
			Object responseObject = getOsbClient().callWebService(url, req, context);
			
			if(null != responseObject) {
				 resp = (CancelOrderResponse) responseObject;
				
				if(null != resp.getRESPONSECODE()) {
					if(null != resp.getRESPONSECODE() && !resp.getRESPONSECODE().isEmpty() 
							&& resp.getRESPONSECODE().equalsIgnoreCase("NIL10018")) {
						LOGGER.info("Number Un Reservation request successfully executed");
					} else {
						throw new ApplicationException("Unable to create subscriber. Cause :" + resp.getRESPONSEMSG(), true);
					}
				} else {
					throw new ApplicationException("Unable to retrieve create subscriber response status", true);
				}
			}
		} catch(WebServiceIOException wsIOE) {
			throw new ApplicationException(wsIOE.getMessage(), wsIOE, true);
			
		}catch(Exception e) {
			throw new ApplicationException(e.getMessage(), e, true);
		}
		
		return resp;
	}

public com.ooredoo.service.wsclient.createb2bsubscriberproxy.CreateB2BSubscriberResponse b2bcprSubscriber(String url, com.ooredoo.service.wsclient.createb2bsubscriberproxy.CreateB2BSubscriberRequest req, String context) throws ApplicationException {
	
	com.ooredoo.service.wsclient.createb2bsubscriberproxy.CreateB2BSubscriberResponse resp = null;
	JAXBElement<com.ooredoo.service.wsclient.createb2bsubscriberproxy.CreateB2BSubscriberResponse> resp1 = null;
		try {
			ObjectToXml(req);
			Object responseObject = getOsbClient().callWebService(url, req, context);
			
			if(null != responseObject) {
				 resp1 = (JAXBElement<com.ooredoo.service.wsclient.createb2bsubscriberproxy.CreateB2BSubscriberResponse>) responseObject;
				
				 if(null != resp1) {
			XmlToObject(resp1);
					 
					 LOGGER.info("B2B Subscriber ResponseCode :"+resp1.getValue().getResponseCode());
					 resp = new com.ooredoo.service.wsclient.createb2bsubscriberproxy.CreateB2BSubscriberResponse();
					 resp.setResponseCode(resp1.getValue().getResponseCode());
					 resp.setResponseMessage(resp1.getValue().getResponseMessage());
					 
						 CreateOrderType createOrderType=new CreateOrderType();
							 if(resp1.getValue().getCreateOrderType()!=null) {
								 createOrderType.setOrderID(resp1.getValue().getCreateOrderType().getOrderID()!=null ? resp1.getValue().getCreateOrderType().getOrderID() : "");
								 createOrderType.setOrderNumber(resp1.getValue().getCreateOrderType().getOrderNumber()!=null ? resp1.getValue().getCreateOrderType().getOrderNumber():"");
							 }
					 resp.setCreateOrderType(createOrderType);
					 
					 LOGGER.info("B2B Subscriber ResponseMessage:"+resp1.getValue().getResponseMessage());
				 }else {
					throw new ApplicationException("Unable to retrieve B2B create subscriber response status", true);
				}
			}
		} catch(WebServiceIOException wsIOE) {
			LOGGER.error("B2B Subscriber wsIOE error::"+wsIOE.getStackTrace());
			throw new ApplicationException(wsIOE.getMessage(), wsIOE, true);
			
		}catch(Exception e) {
			e.printStackTrace();
			LOGGER.error("B2B Subscriber exception error::"+e.getStackTrace());
			throw new ApplicationException(e.getMessage(), e, true);
		}
		LOGGER.info("B2B Subscriber resp"+resp);
		return resp;
	}

public static void ObjectToXml(Object req){
	StringWriter sw = new StringWriter();
	JAXB.marshal(req, sw);
	String xmlString = sw.toString();
	System.out.println("Request xml :\n "+xmlString);
}
public static void XmlToObject(Object res){
	StringWriter sw = new StringWriter(); 
	JAXB.marshal(res, sw);
 String xmlString = sw.toString();
 LOGGER.info("Response xml : \n "+xmlString);
}

	@SuppressWarnings("unchecked")
	public com.ooredoo.service.wsclient.oraappservicenmtc.OutputParameters simSerialReservation(String url, com.ooredoo.service.wsclient.oraappservicenmtc.InputParameters req, String context) throws ApplicationException {
		
		com.ooredoo.service.wsclient.oraappservicenmtc.OutputParameters resp = null;
		
		try {
			LOGGER.info("sim Serial  Reservation request  Object::::::"+req);
			
			//Print:
				//ObjectToXml(req);
				
			Object responseObject = getOsbClient().callWebService(url, req, context);
			LOGGER.info("sim Serial  Reservation  responseObject::::::"+responseObject);
			if(null != responseObject) {
				//process 1
				 resp = (com.ooredoo.service.wsclient.oraappservicenmtc.OutputParameters) responseObject;
				//process 2
				/*
				 * JAXBElement<com.ooredoo.service.wsclient.oraappservicenmtc.OutputParameters>
				 * respJ =
				 * (JAXBElement<com.ooredoo.service.wsclient.oraappservicenmtc.OutputParameters>
				 * ) responseObject;
				 * LOGGER.info("sim Serial  Reservation request  Object respJ::::::"+respJ);
				 * if(respJ!=null) { resp = respJ.getValue(); }
				 */
				 LOGGER.info("sim Serial  Reservation request  Object resp::::::"+resp);
				if(null != resp.getPACKRETCODE()) {
					LOGGER.info("sim Serial  Reservation request  Code::::::"+resp.getPACKRETCODE());
					LOGGER.info("sim Serial  Reservation request  Message::::::"+resp.getPACKRETMSG());
					
					if (null != resp.getPACKRETCODE()/* && (resp.getPACKRETCODE().getValue()).equals("Success") */
							 && !resp.getPACKRETCODE().isEmpty()  && resp.getPACKRETCODE().equalsIgnoreCase("Success") ) {
						LOGGER.info("sim Serial  Reservation request successfully executed");
					} else {
						throw new ApplicationException("Unable to create subscriber. Cause :" + resp.getPACKRETMSG(), true);
					}
				} else {
					throw new ApplicationException("Unable to retrieve create subscriber response status", true);
				}
			}
		} catch(WebServiceIOException wsIOE) {
			throw new ApplicationException(wsIOE.getMessage(), wsIOE, true);
			
		}catch(Exception e) {
			LOGGER.error("sim Serial  Reservation Error::: "+e.getStackTrace());
			throw new ApplicationException(e.getMessage(), e, true);
		}
		
		return resp;
	}
	

	@SuppressWarnings("unchecked")
	public CreateOrderResponseType createOrder(String url, CreateOrderRequestType req, String context) throws ApplicationException {
		
		CreateOrderResponseType resp = null;
		
		try {
			Object responseObject = getOsbClient().callWebService(url, req, context);
			
			if(null != responseObject) {
				JAXBElement<CreateOrderResponseType> respJ = (JAXBElement<CreateOrderResponseType>) responseObject;
				 resp = respJ.getValue();
				
				if(null != resp.getStatusInfo()) {
					if(null != resp.getStatusInfo().getStatusCode() && !resp.getStatusInfo().getStatusCode().isEmpty() && resp.getStatusInfo().getStatusCode().equals("0")) {
						LOGGER.info("Create order request successfully executed");
					} else {
						throw new ApplicationException("Unable to create order. Cause :" + resp.getStatusInfo().getStatusDesc(), true);
					}
				} else {
					throw new ApplicationException("Unable to retrieve create order response status", true);
				}
			}
		} catch(WebServiceIOException wsIOE) {
			throw new ApplicationException(wsIOE.getMessage(), wsIOE, true);
			
		}catch(Exception e) {
			throw new ApplicationException(e.getMessage(), e, true);
		}
		
		return resp;
	}
	
	/**
	 * 
	 * @param url
	 * @param req
	 * @param context
	 * @return
	 * @throws ApplicationException
	 */
	public DownStreamProfileCheckResponse downStreamProfileCheck(String url, DownStreamProfileCheckRequest req, String context) throws ApplicationException {
		
		DownStreamProfileCheckResponse resp = null;
		
		try {
			Object responseObject = getOsbClient().callWebService(url, req, context);
			
			if(null != responseObject) {
				resp = (DownStreamProfileCheckResponse) responseObject;
			} else {
				throw new ApplicationException("Unable to extract downstream status", true);
			}
		} catch(WebServiceIOException wsIOE) {
			throw new ApplicationException(wsIOE.getMessage(), wsIOE, true);
			
		}catch(Exception e) {
			throw new ApplicationException(e.getMessage(), e, true);
		}
		
		return resp;
	}
	
	/**
	 * 
	 * @param url
	 * @param req
	 * @param context
	 * @return
	 * @throws ApplicationException
	 */
	public AnaOnboardingResponse anaOnboardingService(String url, AnaOnboardingRequest req, String context) throws ApplicationException {
		
		AnaOnboardingResponse resp = null;
		
		try {
			Object responseObject = getOsbClient().callWebService(url, req, context);
			
			if(null != responseObject) {
				resp = (AnaOnboardingResponse) responseObject;
			} else {
				throw new ApplicationException("Unable to extract ANA onboarding status", true);
			}
		} catch(WebServiceIOException wsIOE) {
			throw new ApplicationException(wsIOE.getMessage(), wsIOE, true);
			
		}catch(Exception e) {
			throw new ApplicationException(e.getMessage(), e, true);
		}
		
		return resp;
	}
	
	/**
	 * 
	 * @param url
	 * @param req
	 * @param context
	 * @return
	 * @throws ApplicationException
	 */
	public HLRResponseType getHLRProfile(String url, HLRrequestType req, String context) throws ApplicationException {

		HLRResponseType resp = null;
		
		try {
			Object responseObject = getOsbClient().callWebService(url, req, context);
			
			if(null != responseObject) {
				resp = (HLRResponseType) responseObject;
				
			} else {
				throw new ApplicationException("Unable to extract HLR Profile", true);
			}
		} catch(WebServiceIOException wsIOE) {
			throw new ApplicationException(wsIOE.getMessage(), wsIOE, true);
			
		}catch(Exception e) {
			throw new ApplicationException(e.getMessage(), e, true);
		}
		
		return resp;
	}
	
	public OSBServiceClient getOsbClient() {
		return osbClient;
	}

	public BSCSServiceClient getBscsClient() {
		return bscsClient;
	}

	public ESMServiceClient getEsmClient() {
		return esmClient;
	}

	public PCRFServiceClient getPcrfClient() {
		return pcrfClient;
	}

	public CSServiceClient getCsServiceClient() {
		return csServiceClient;
	}

	public SPRServiceClient getSprServiceClient() {
		return sprServiceClient;
	}

	public HLRServiceClient getHlrServiceClient() {
		return hlrServiceClient;
	}
	
	
	/**
	 * 
	 * @param url
	 * @param req
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public GetB2BCustomerCIDResponse getb2bCustomerCID(String url, GetB2BCustomerCIDRequest req, String context) throws ApplicationException {
		
		GetB2BCustomerCIDResponse resp = null;
		
		try {
			LOGGER.info("Request for getb2bCustomerCID");
			ObjectToXml(req);
			Object responseObject = getOsbClient().callWebService(url, req, context);
			
			if(null != responseObject) {
				resp = (GetB2BCustomerCIDResponse) responseObject;
				LOGGER.info("Response for getb2bCustomerCID:"+resp);
				if(!resp.getErrorMessage().equalsIgnoreCase(SUCCESS_LEBEL)) {
					throw new ApplicationException(resp.getErrorMessage(), true);
				}
			}
		} catch(WebServiceIOException wsIOE) {
			throw new ApplicationContextException(wsIOE.getMessage(), wsIOE);
			
		}catch(Exception e) {
			throw new ApplicationContextException(e.getMessage(), e);
		}
		
		return resp;
	}
	
}
