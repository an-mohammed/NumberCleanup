package com.ooredoo.nc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ooredoo.nc.dto.DisconnectionOrderDetails;
import com.ooredoo.nc.dto.DisconnectionRequest;
import com.ooredoo.nc.dto.DisconnectionResponse;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.service.DisconnectionService;
import com.ooredoo.nc.utility.ApplicationConstants;

@RestController
@RequestMapping(value = {"${service.disconnection.base-url}"})
public class DisconnectionController extends Controller implements ApplicationConstants {
	
	@Autowired
	private DisconnectionService discService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DisconnectionController.class);
	
	@CrossOrigin(origins = "*")
	@PostMapping(value={"/discSubscriberProfile"}, produces= MediaType.APPLICATION_JSON_VALUE, name="Disconnection Order")
	public ResponseEntity<DisconnectionResponse> discSubscriberProfile(@RequestBody(required=true) DisconnectionRequest discReq, HttpServletRequest servlet) {
			
		LOGGER.info("Invoked disconnection request");
		DisconnectionResponse respO = new DisconnectionResponse();
		
		try {
			List<DisconnectionOrderDetails> discDetails = discService.createDisconnectionRequest(discReq.getMsisdnList());
			respO.setDisconnectionOrderDetails(discDetails);
			respO.setStatus(OK_LABEL);
			return ResponseEntity.status(200).body(respO);
			
		} catch (ApplicationException e) {
			LOGGER.error("Error occurred while posting disconnection request. Cause : ", e);
			respO.setDisconnectionOrderDetails(null);
			respO.setStatus(e.getErrorMessage());
			return ResponseEntity.status(500).body(respO);
			
		} catch (Exception e) {
			LOGGER.error("Error occurred while posting disconnection request. Cause : ", e);
			respO.setDisconnectionOrderDetails(null);
			respO.setStatus(e.getMessage());
			return ResponseEntity.status(500).body(respO);
		}
	}			
}
