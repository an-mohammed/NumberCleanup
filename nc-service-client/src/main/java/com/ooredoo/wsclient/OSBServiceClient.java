package com.ooredoo.wsclient;

import java.io.IOException;
import java.time.Duration;
import java.util.Locale;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.TransformerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.WebServiceIOException;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.transport.http.HttpUrlConnectionMessageSender;


@Configuration
public class OSBServiceClient extends WebServiceGatewaySupport implements WebServiceMessageCallback {

	@Autowired
	private ReloadableResourceBundleMessageSource externalConfig;
	
	private static final String NAMESPACE = "http://osb.wataniya.com";
	private static final Logger LOGGER = LoggerFactory.getLogger(OSBServiceClient.class);
	
	public synchronized Object callWebService(String url, Object request, String contextPathForMarshaller) {
		//WebServiceTemplate template = getWebServiceTemplate();
		Object obj=null;
		try {
		Jaxb2Marshaller m = new Jaxb2Marshaller();
		m.setContextPath(contextPathForMarshaller);
		
		getWebServiceTemplate().setMarshaller(m);
		getWebServiceTemplate().setUnmarshaller(m);
		getWebServiceTemplate().setDefaultUri(url);
		LOGGER.info("Invoking external service at :"  + url);
		
		HttpUrlConnectionMessageSender sender = new HttpUrlConnectionMessageSender();
        sender.setReadTimeout(Duration.ofMillis(Integer.parseInt(externalConfig.getMessage("service.osb.readTimeout", null, "300000", Locale.getDefault()))));
        sender.setConnectionTimeout(Duration.ofMillis(Integer.parseInt(externalConfig.getMessage("service.osb.connectionTimeout", null, "10000", Locale.getDefault()))));
        getWebServiceTemplate().setMessageSender(sender);
         obj=getWebServiceTemplate().marshalSendAndReceive(url, request, this);
        return obj;
		}catch(Exception e) {
			e.printStackTrace();
			LOGGER.error("callWebService service Error :::"  + e.getStackTrace());
		}
		return obj;
    }
	
	/*
	 * @SuppressWarnings("deprecation") public WebServiceMessageSender
	 * webServiceMessageSenders() { ConnectionKeepAliveStrategy
	 * connectionKeepAliveStrategy = new ConnectionKeepAliveStrategy() {
	 * 
	 * @Override public long getKeepAliveDuration(HttpResponse response, HttpContext
	 * context) { return 30; } };
	 * 
	 * ConnectionReuseStrategy reuseStrategy = new ConnectionReuseStrategy() {
	 * 
	 * @Override public boolean keepAlive(HttpResponse response, HttpContext
	 * context) { return false; } };
	 * 
	 * CloseableHttpClient httpClient =
	 * HttpClientBuilder.create().setKeepAliveStrategy(connectionKeepAliveStrategy).
	 * disableAuthCaching().setConnectionReuseStrategy(reuseStrategy).build();
	 * HttpComponentsMessageSender httpComponentsMessageSender = new
	 * HttpComponentsMessageSender(httpClient);
	 * httpComponentsMessageSender.setConnectionTimeout(Integer.parseInt(
	 * externalConfig.getMessage("service.endpoint.connectTimeout", null,
	 * Locale.ENGLISH)));
	 * httpComponentsMessageSender.setReadTimeout(Integer.parseInt(externalConfig.
	 * getMessage("service.endpoint.requestTimeOut", null, Locale.ENGLISH)));
	 * httpComponentsMessageSender.getHttpClient().getConnectionManager().
	 * closeExpiredConnections();
	 * httpComponentsMessageSender.getHttpClient().getConnectionManager().
	 * closeIdleConnections(30, TimeUnit.SECONDS); return
	 * httpComponentsMessageSender;
	 * 
	 * }
	 */

	@Override
	public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
		
		try {
			SaajSoapMessage saajSoapMessage = (SaajSoapMessage)message;
	        SOAPMessage soapMessage = saajSoapMessage.getSaajMessage();
	        SOAPPart soapPart = soapMessage.getSOAPPart();
	        SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
	        SOAPHeader soapHeader = soapEnvelope.getHeader();
	        
	        SOAPElement wataniyaAuth = soapHeader.addChildElement("auth", "wataniya", NAMESPACE);
			
			SOAPElement username = wataniyaAuth.addChildElement("username", null, null);
			username.addTextNode(externalConfig.getMessage("service.osb.username", null, Locale.getDefault()));
			
			SOAPElement password = wataniyaAuth.addChildElement("password", null, null);
			password.addTextNode(externalConfig.getMessage("service.osb.password", null, Locale.getDefault()));
			
			soapMessage.saveChanges();
	        
		} catch(SOAPException soe) {
			throw new TransformerException(soe.getMessage());
		}
	}
}
