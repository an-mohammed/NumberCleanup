package com.ooredoo.wsclient;

import java.io.IOException;
import java.util.Locale;

import javax.xml.transform.TransformerException;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.WebServiceIOException;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;


@Configuration
public class SPRServiceClient extends WebServiceGatewaySupport implements WebServiceMessageCallback {

	@Autowired
	private ReloadableResourceBundleMessageSource externalConfig;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SPRServiceClient.class);
	
	public synchronized Object callWebService(String url, Object request, String contextPathForMarshaller) throws WebServiceIOException{
		//WebServiceTemplate template = getWebServiceTemplate();
		
		Jaxb2Marshaller m = new Jaxb2Marshaller();
		m.setContextPath(contextPathForMarshaller);
		
		getWebServiceTemplate().setMarshaller(m);
		getWebServiceTemplate().setUnmarshaller(m);
		getWebServiceTemplate().setDefaultUri(url);
		getWebServiceTemplate().setMessageSender(sprMessageSender());
		
		LOGGER.info("Invoking external service at :"  + url);
		
        return getWebServiceTemplate().marshalSendAndReceive(url, request, this);
    }
	
	 @Bean
	    public HttpComponentsMessageSender sprMessageSender() {
	        HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
	        messageSender.setCredentials(
	        		new UsernamePasswordCredentials(externalConfig.getMessage("service.cleanup.spr.username", null, Locale.getDefault())
	        				, externalConfig.getMessage("service.cleanup.spr.password", null, Locale.getDefault())));
	        return messageSender;
	    }
	

	@Override
	public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
		
/*		try {
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
		}*/
	}
}
