package com.ooredoo.nc.service;

import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import com.ooredoo.nc.dto.NodalCleanupDetails;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.utility.ApplicationConstants;
import com.ooredoo.service.wsclient.oraappservicenmtc.APPSRECTABVAR;
import com.ooredoo.service.wsclient.oraappservicenmtc.APPSRECVAR;
import com.ooredoo.service.wsclient.oraappservicenmtc.InputParameters;
import com.ooredoo.service.wsclient.oraappservicenmtc.ObjectFactory;
import com.ooredoo.wsclient.WSClient;

@Service
public class CSCleanupService implements ApplicationConstants {

	@Autowired
	private WSClient wsclient;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CSCleanupService.class);
	
	public NodalCleanupDetails deleteCsProfile(String msisdn) {
		NodalCleanupDetails cleanupDetails = new NodalCleanupDetails();
		
		try {
			cleanupDetails.setNode(CS);
			cleanupDetails.setExecStart(new Date());
			
			wsclient.deleteSubscriberFromCs(msisdn);
			cleanupDetails.setStatus(SUCCESS_LEBEL);
			cleanupDetails.setResponse("OK");
			
		} catch(NoSuchMessageException e) {
			cleanupDetails.setStatus(ERROR_LABEL);
			cleanupDetails.setResponse("Fatal Exception.");
			
		}catch(ApplicationException ae) {
			cleanupDetails.setStatus(ERROR_LABEL);
			cleanupDetails.setResponse(ae.getMessage());
			
			LOGGER.error("Error occurred while invoking service. Cause :", ae);
		}
		
		cleanupDetails.setExecEnd(new Date());
		return cleanupDetails;
	}
	
	public InputParameters createSimeSerialRequest(String serialNumber){
		/*InputParameters nResRequest =  null;
		try {
		ObjectFactory of = new ObjectFactory();
		
		nResRequest =  of.createInputParameters();
					
						JAXBContext jc = JAXBContext.newInstance(of.getClass());
					
					//InputParameters nResRequest =  new InputParameters();
				LOGGER.info(" sim serial reservation service nResRequest"+nResRequest);

				
				nResRequest.setPINTGREFERENCEID(of.createInputParametersPINTGREFERENCEID("1-1BKKZ0JX"));
				nResRequest.setPSIEBELORDID(of.createInputParametersPSIEBELORDID("29032022022501"));
				nResRequest.setPSIEBELORDTYPE(of.createInputParametersPSIEBELORDTYPE("ReserveDevice"));
				
				APPSRECTABVAR pinrecItemList =of.createAPPSRECTABVAR();
				
					APPSRECVAR pinrecItem =of.createAPPSRECVAR();
					pinrecItem.setPSIEBELITEMID(of.createAPPSRECVARPSIEBELITEMID("3CUT_RAW_SIM"));
					pinrecItem.setPSIEBELSERIALNUM(of.createAPPSRECVARPSIEBELSERIALNUM(serialNumber));//serialNumber
					pinrecItem.setPSIEBELORDERID(of.createAPPSRECVARPSIEBELORDERID("1-1D6275M4"));
					pinrecItem.setPINTGREFID(of.createAPPSRECVARPINTGREFID("Cleanup.1-1BKKZ0JX.ReserveDevice.B2B"));
					pinrecItem.setPTXNTYPE(of.createAPPSRECVARPTXNTYPE("B2B_RESERVE_SIM"));
					
				pinrecItemList.getPINRECITEM().add(pinrecItem);
				
				nResRequest.setPINREC(of.createInputParametersPINREC(pinrecItemList));
				return nResRequest;
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nResRequest;
		*/
		return null;
		
	}
}
