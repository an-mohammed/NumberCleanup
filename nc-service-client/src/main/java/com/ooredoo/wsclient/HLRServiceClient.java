package com.ooredoo.wsclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.IOUtils;
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
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.utility.ApplicationConstants;
import com.ooredoo.wsclient.config.SimpleNamespaceContext;


@Configuration
public class HLRServiceClient extends WebServiceGatewaySupport implements WebServiceMessageCallback, ApplicationConstants {

	
	@Autowired
	private ReloadableResourceBundleMessageSource externalConfig;
	private static final String NAMESPACE = "http://www.huawei.com/HSS";
	private static final Logger LOGGER = LoggerFactory.getLogger(HLRServiceClient.class);
	
	/**
	 * 
	 * @param url
	 * @param request
	 * @param contextPathForMarshaller
	 * @return
	 * @throws WebServiceIOException
	 */
	public synchronized Object callWebService(String url, Object request, String contextPathForMarshaller) throws WebServiceIOException{
		//WebServiceTemplate template = getWebServiceTemplate();
		
		String[] contexts = contextPathForMarshaller.split(COMMA);
		Jaxb2Marshaller m = new Jaxb2Marshaller();
		m.setContextPaths(contexts[0], contexts[1]);
		getWebServiceTemplate().setMarshaller(m);
		getWebServiceTemplate().setUnmarshaller(m);
		getWebServiceTemplate().setDefaultUri(url);
		getWebServiceTemplate().setMessageSender(hlrMessageSender());
		
		LOGGER.info("Invoking external service at :"  + url);
		
        return getWebServiceTemplate().marshalSendAndReceive(url, request, this);
    }
	
	/**
	 * 
	 * @return
	 */
	 @Bean
	    public HttpComponentsMessageSender hlrMessageSender() {
	        HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
	        messageSender.setCredentials(
	        		new UsernamePasswordCredentials(externalConfig.getMessage("service.cleanup.hlr.username", null, Locale.getDefault())
	        				, externalConfig.getMessage("service.cleanup.hlr.password", null, Locale.getDefault())));
	        return messageSender;
	    }
	

	@Override
	public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
		try {
			SaajSoapMessage saajSoapMessage = (SaajSoapMessage)message;
	        SOAPMessage soapMessage = saajSoapMessage.getSaajMessage();
	        SOAPPart soapPart = soapMessage.getSOAPPart();
	        SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
	        SOAPHeader soapHeader = soapEnvelope.getHeader();
	        
	        SOAPElement hssAuth = soapHeader.addChildElement("Authentication", "m", NAMESPACE);
			
	        SOAPElement mename = hssAuth.addChildElement("MEName", "m", NAMESPACE);
	        mename.addTextNode(externalConfig.getMessage("service.cleanup.hlr.mename", null, Locale.getDefault()));
	        
			SOAPElement username = hssAuth.addChildElement("Username", "m", NAMESPACE);
			username.addTextNode(externalConfig.getMessage("service.cleanup.hlr.username", null, Locale.getDefault()));
			
			SOAPElement password = hssAuth.addChildElement("Password", "m", NAMESPACE);
			password.addTextNode(externalConfig.getMessage("service.cleanup.hlr.password", null, Locale.getDefault()));
			
			soapMessage.saveChanges();
	        
		} catch(SOAPException soe) {
			throw new TransformerException(soe.getMessage());
		}
	}
	
	/**
	 * 
	 * @param msisdnWithoutPrefix
	 * @throws Exception
	 */
	public void invokeRmvDnaPtrRec(String msisdnWithoutPrefix, String endPointUrl) throws ApplicationException {
		
		HttpURLConnection con = null;
		BufferedReader br = null;
		String inputLine = null;
		StringBuilder builder = null;
		OutputStream os = null;
		
		try {
			String requestContent = externalConfig.getMessage("service.cleanup.hlr.rmvdnaptrrec.requestxml", null, Locale.getDefault());
			
			if(null != requestContent && !requestContent.isEmpty()) {
				URL serviceUrl = new URL(endPointUrl);
				con = (HttpURLConnection) serviceUrl.openConnection();
				con.setRequestMethod("POST");
				con.setRequestProperty("User-Agent", USER_AGENT);
				con.setRequestProperty("SOAPAction", SOAP_ACTION);
				con.setRequestProperty("Content-Type", CONTENT_TYPE);
				con.setRequestProperty("Accept-Encoding", ACCEPTED_ENCODING);
				
				requestContent = requestContent.replaceAll("#MSISDN_WITHOUT_PREFIX#", msisdnWithoutPrefix);
				con.setRequestProperty("Content-Length", String.valueOf(requestContent.length()));
				
				con.setDoOutput(true);
				os = con.getOutputStream();
				os.write(requestContent.getBytes());
				
				int responseCode = con.getResponseCode();
				LOGGER.info("Service HTTP Response Code for RMV_DNAPTRREC:" + responseCode);
				
				if (responseCode == HttpURLConnection.HTTP_OK) {
					br = new BufferedReader(new InputStreamReader(con.getInputStream()));
					builder = new StringBuilder();

					while ((inputLine = br.readLine()) != null) {
						builder.append(inputLine);
					}
					
					parseResponseCodeFromResponseXml(builder.toString());
					
				} else {
					throw new ApplicationException("Unable to read response xml for HSS operation RMV_DNAPTRREC as Http Response Code:" + responseCode, true);
				}
			} else {
				throw new ApplicationException("Unable to read request xml for HSS operation RMV_DNAPTRREC", true);
			}
		} catch(MalformedURLException e) {
			throw new ApplicationException(e.getMessage(), true);
			
		}catch(IOException ioe) {
			throw new ApplicationException(ioe.getMessage(), true);
			
		}catch(ParserConfigurationException pce) {
			throw new ApplicationException(pce.getMessage(), true);
			
		}catch(SAXException se) {
			throw new ApplicationException(se.getMessage(), true);
			
		}finally {
			if(null != br) {
				try {
					br.close();
				} catch(IOException e) {
					LOGGER.error("IOException while parsing the service response");
				}
				
			}
			
			if(null != con) {
				con.disconnect();
			}
			
			if(null != os) {
				try {
					os.flush();
					os.close();
				} catch(IOException e) {
					LOGGER.error("IOException while parsing the service response");
				}
			}
		}
	}

	/**
	 * 
	 * @param responseXml
	 * @throws Exception
	 */
	private void parseResponseCodeFromResponseXml(String responseXml) throws ApplicationException, ParserConfigurationException, IOException, SAXException {
		if(null != responseXml) {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        factory.setNamespaceAware(true);
	        DocumentBuilder builder = null;
	        Document doc = null;
		        
	        builder = factory.newDocumentBuilder();
	        doc = builder.parse(IOUtils.toInputStream(responseXml));
	
	        String xpathValue = getXpathValue(doc, 
	        		externalConfig.getMessage("service.cleanup.hlr.rmvdnaptrrec.responsexpath", null, Locale.getDefault()),
	        		getNamespaceContext());
	        
	        LOGGER.info("Operation rmvdnaptrrec XPath Value :" + xpathValue);
	        
	        if(null != xpathValue && (xpathValue.equals(VENSTB_NOT_FOUND_ERROR) || xpathValue.equals(SUCCESS_RESPONSE_CODE) || xpathValue.equals("1001"))) {
	        	LOGGER.info("Method RMV_DNAPTRREC Successfully Executed with response code :" + xpathValue);
	        } else {
	        	throw new ApplicationException("Operation RMV_DNAPTRREC Responded with error code:" + xpathValue, true);
	        }
		}
	}

	/**
	 * 
	 * @return
	 */
	private Map<String, String> getNamespaceContext() {
		final Map<String, String> prefMap = new HashMap<String, String>() ;
	       prefMap.put("SOAP-ENV", "http://schemas.xmlsoap.org/soap/envelope/");
	       prefMap.put("xsi", "http://www.w3.org/2001/XMLSchema-instance");
	       prefMap.put("xsd", "http://www.w3.org/2001/XMLSchema");
	       prefMap.put("hss", "http://www.huawei.com/HSS");
	       
	       return prefMap;
		}
	
	public static String getXpathValue(Document doc, String xpathExpression, Map<String, String> namespaceContext) throws ApplicationException {
		   String value = null;
		   XPathFactory xpathFactory = XPathFactory.newInstance();
		   XPath xpath = xpathFactory.newXPath();
		   xpath.setNamespaceContext(new SimpleNamespaceContext(namespaceContext));
	        try {
	            XPathExpression expr = xpath.compile(xpathExpression);
	            value = (String) expr.evaluate(doc, XPathConstants.STRING);
	        } catch (XPathExpressionException e) {
	            throw new ApplicationException(e.getMessage(), e);
	        }

	        return value;
		}
}
