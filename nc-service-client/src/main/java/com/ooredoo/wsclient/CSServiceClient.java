package com.ooredoo.wsclient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcTransport;
import org.apache.xmlrpc.client.XmlRpcTransportFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.utility.ApplicationConstants;
import com.ooredoo.wsclient.config.CSTypefactory;
import com.ooredoo.wsclient.config.MessageLoggingTransport;


@Configuration
public class CSServiceClient implements ApplicationConstants {

	private XmlRpcClient client = null;
	
	@Autowired
	private ReloadableResourceBundleMessageSource externalConfig;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CSServiceClient.class);
	
	public XmlRpcClient getUcipTemplate() throws ApplicationException {
		
		XmlRpcClientConfigImpl xmlRpcConfig = null;
		
		try {
			xmlRpcConfig = new XmlRpcClientConfigImpl();
			xmlRpcConfig.setServerURL(new URL(externalConfig.getMessage("service.cleanup.cs.url", null, Locale.getDefault())));
			xmlRpcConfig.setEnabledForExtensions(true);
			xmlRpcConfig.setBasicUserName(externalConfig.getMessage("service.cleanup.cs.username", null, Locale.getDefault()));
			xmlRpcConfig.setBasicPassword(externalConfig.getMessage("service.cleanup.cs.password", null, Locale.getDefault()));
			xmlRpcConfig.setContentLengthOptional(false);
			xmlRpcConfig.setConnectionTimeout(10 * 1000);
			xmlRpcConfig.setReplyTimeout(30 * 1000);
			xmlRpcConfig.setUserAgent(externalConfig.getMessage("service.cleanup.cs.useragent", null, Locale.getDefault()));
			
			XmlRpcTransportFactory transportFactory = new XmlRpcTransportFactory() {
			      public XmlRpcTransport getTransport() {
			          return new MessageLoggingTransport(client);
			      }
			  };
			  
			  client = new XmlRpcClient();
			  client.setTransportFactory(transportFactory);
			  client.setConfig(xmlRpcConfig);
			  client.setTypeFactory(new CSTypefactory(client));
			
			  
		} catch(MalformedURLException e ) {
			throw new ApplicationException(e);
		}
		
		LOGGER.info("UCIP Client instantiated successfully");
		return client;
	}
	
	public String getCsServerConfig(String key) {
		return externalConfig.getMessage(key, null, Locale.getDefault());
	}
}
