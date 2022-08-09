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
import org.springframework.ws.transport.context.TransportContext;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpComponentsConnection;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import com.ooredoo.nc.utility.ApplicationUtility;


@Configuration
public class ESMServiceClient extends WebServiceGatewaySupport implements WebServiceMessageCallback {

	@Autowired
	private ReloadableResourceBundleMessageSource externalConfig;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ESMServiceClient.class);
	
	public synchronized Object callWebService(String url, Object request, String contextPathForMarshaller) throws WebServiceIOException{
		//WebServiceTemplate template = getWebServiceTemplate();
		
		Jaxb2Marshaller m = new Jaxb2Marshaller();
		m.setContextPath(contextPathForMarshaller);
		
		getWebServiceTemplate().setMarshaller(m);
		getWebServiceTemplate().setUnmarshaller(m);
		getWebServiceTemplate().setDefaultUri(url);
		getWebServiceTemplate().setMessageSender(esmMessageSender());
		LOGGER.info("Invoking external service at :"  + url);
		
        return getWebServiceTemplate().marshalSendAndReceive(url, request, this);
    }
	
	 @Bean
	    public HttpComponentsMessageSender esmMessageSender() {
	        HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
	        messageSender.setCredentials(esmUsernamePasswordCredentials());
	        return messageSender;
	    }
	
	 @Bean
	  public UsernamePasswordCredentials esmUsernamePasswordCredentials() {
	    // pass the user name and password to be used
	    return new UsernamePasswordCredentials(externalConfig.getMessage("service.cleanup.esm.username", null, Locale.getDefault())
				, externalConfig.getMessage("service.cleanup.esm.password", null, Locale.getDefault()));
	  }

	@Override
	public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
		TransportContext context = TransportContextHolder.getTransportContext();
		HttpComponentsConnection  connection = (HttpComponentsConnection ) context.getConnection();
        connection.addRequestHeader("Authorization",
            ApplicationUtility.generateBasicAutenticationHeader(externalConfig.getMessage("service.cleanup.esm.username", null, Locale.getDefault())
    				, externalConfig.getMessage("service.cleanup.esm.password", null, Locale.getDefault())));
	}
}
