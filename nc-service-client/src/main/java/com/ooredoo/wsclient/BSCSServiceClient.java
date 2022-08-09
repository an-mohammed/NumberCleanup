package com.ooredoo.wsclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.MockValidationCallbackHandler;


@Configuration
public class BSCSServiceClient extends WebServiceGatewaySupport {
	private static final Logger LOGGER = LoggerFactory.getLogger(BSCSServiceClient.class);

	public synchronized Object callWebService(String url, Object request, String contextPathForMarshaller){
		//WebServiceTemplate template = getWebServiceTemplate();
		
		Jaxb2Marshaller m = new Jaxb2Marshaller();
		m.setContextPath(contextPathForMarshaller);
		
		getWebServiceTemplate().setMarshaller(m);
		getWebServiceTemplate().setUnmarshaller(m);
		getWebServiceTemplate().setDefaultUri(url);
		
		ClientInterceptor[] interceptors = new ClientInterceptor[] {getXwsSecurityInterceptor()};
		getWebServiceTemplate().setInterceptors(interceptors);
		
		LOGGER.info("Invoking external service at :"  + url);
		
        return getWebServiceTemplate().marshalSendAndReceive(url, request);
    }
	
	@Bean
	public XwsSecurityInterceptor getXwsSecurityInterceptor() {
		XwsSecurityInterceptor xwsSecurityInterceptor = new XwsSecurityInterceptor();
		
		Resource r = new ClassPathResource("bscs-ws-security.xml");
		xwsSecurityInterceptor.setPolicyConfiguration(r);
		xwsSecurityInterceptor.setCallbackHandler(new MockValidationCallbackHandler());
		
		return xwsSecurityInterceptor;
	}
}
