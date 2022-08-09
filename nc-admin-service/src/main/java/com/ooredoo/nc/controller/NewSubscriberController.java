package com.ooredoo.nc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ooredoo.createorder.CreateOrderResponseType;
import com.ooredoo.nc.dto.AnaOnboardingRequest;
import com.ooredoo.nc.dto.AnaTrxUpdateRequest;
import com.ooredoo.nc.dto.AnaTrxUpdateResponse;
import com.ooredoo.nc.dto.B2BSubscriberOnboardingRequest;
import com.ooredoo.nc.dto.B2BSubscriberOnboardingResponse;
import com.ooredoo.nc.dto.B2CSubscriberOnboardingRequest;
import com.ooredoo.nc.dto.B2CSubscriberOnboardingResponse;
import com.ooredoo.nc.dto.NewSubscriberProfile;
import com.ooredoo.nc.dto.NewSubscriberProfileResp;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.model.DigitalOffer;
import com.ooredoo.nc.model.NcDigitalOnboarding;
import com.ooredoo.nc.service.CreatePrepaidSimService;
import com.ooredoo.nc.service.DigitalOnboardingService;
import com.ooredoo.nc.utility.ApplicationConstants;

@RestController
@RequestMapping(value = {"${service.subsprofile.base-url}"})
public class NewSubscriberController extends Controller implements ApplicationConstants {
	
	@Autowired
	private CreatePrepaidSimService createPrepaidSimService;
	
	@Autowired
	private DigitalOnboardingService digitalOnboardingService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NewSubscriberController.class);
	
	/**
	 * 
	 * @param profile
	 * @param servlet
	 * @return
	 */
	@CrossOrigin(origins = "*")
	@PostMapping(value={"/createSubscriberProfile"}, produces= MediaType.APPLICATION_JSON_VALUE, name="Ceate new subscriber")
	public ResponseEntity<NewSubscriberProfileResp> createSubscriberProfile(@RequestBody(required=true) NewSubscriberProfile profile, HttpServletRequest servlet) {
			
		LOGGER.info("Invoked createPrepaidNumber service");
		NewSubscriberProfileResp respO = new NewSubscriberProfileResp();
		
		try {
			profile.validate();
			CreateOrderResponseType resp = createPrepaidSimService.createSubscriberProfile(profile);
			
			respO.setSpcId(resp.getSpcId());
			respO.setSpcNum(resp.getSpcNum());
			respO.setRespCode("0");
			respO.setMessage("OK");
			
			return ResponseEntity.status(200).body(respO);
			
		} catch (ApplicationException e) {
			LOGGER.error("Error occurred while creating new subscriber. Cause : ", e);
			
			respO.setRespCode("500");
			respO.setMessage(e.getErrorMessage());
			return ResponseEntity.status(500).body(respO);
			
		} catch (Exception e) {
			LOGGER.error("Error occurred while creating new subscriber. Cause : ", e);
			
			respO.setRespCode("500");
			respO.setMessage(e.getMessage());
			return ResponseEntity.status(500).body(respO);
		}
	}
	
	/**
	 * 
	 * @param profile
	 * @param servlet
	 * @return
	 */
	@PostMapping(value={"/findCleanOnboardB2CProfile"}, produces= MediaType.APPLICATION_JSON_VALUE, name="Ceate new subscriber")
	public ResponseEntity<B2CSubscriberOnboardingResponse> findCleanOnboardB2CProfile(@RequestBody(required=true) B2CSubscriberOnboardingRequest profile, HttpServletRequest servlet) {
		B2CSubscriberOnboardingResponse resp = null;
		try {
			resp = createPrepaidSimService.findCleanOnboardB2CProfile(profile);
			return ResponseEntity.status(200).body(resp);
			
		} catch (ApplicationException e) {
			resp = new B2CSubscriberOnboardingResponse();
			resp.setStatus(e.getErrorMessage());
			return ResponseEntity.status(500).body(resp);
		} catch(Exception e) {
			resp = new B2CSubscriberOnboardingResponse();
			resp.setStatus(e.getMessage());
			return ResponseEntity.status(500).body(resp);
		}
	}
	
	/**
	 * 
	 * @param profile
	 * @param servlet
	 * @return
	 */
	@PostMapping(value={"/findCleanOnboardB2BProfile"}, produces= MediaType.APPLICATION_JSON_VALUE, name="Ceate new subscriber")
	public ResponseEntity<B2BSubscriberOnboardingResponse> findCleanOnboardB2BProfile(@RequestBody(required=true) B2BSubscriberOnboardingRequest profile, HttpServletRequest servlet) {
		LOGGER.info("Invoked findCleanOnboardB2BProfile service");
		B2BSubscriberOnboardingResponse resp = null;
		try {
			resp = createPrepaidSimService.findCleanOnboardB2BProfile(profile);
			return ResponseEntity.status(200).body(resp);
			
		} catch (ApplicationException e) {
			resp = new B2BSubscriberOnboardingResponse();
			resp.setStatus(e.getErrorMessage());
			return ResponseEntity.status(500).body(resp);
		} catch(Exception e) {
			resp = new B2BSubscriberOnboardingResponse();
			resp.setStatus(e.getMessage());
			return ResponseEntity.status(500).body(resp);
		}
	}
	/**
	 * 
	 * @param profile
	 * @param servlet
	 * @return
	 * @throws ApplicationException
	 */
	@PostMapping(value={"/digitalOnboarding"}, produces= MediaType.APPLICATION_JSON_VALUE, name="Digital Onboarding")
	public ResponseEntity<?> digitalOnboarding(@RequestBody(required=true) AnaOnboardingRequest profile, HttpServletRequest servlet) throws ApplicationException {
		LOGGER.info("Invoked digital subscriber onboarding");
		try {
			String trxId = digitalOnboardingService.executeOnboarding(profile.getMsisdn(), profile.getUsername(), profile.getCivilId(), Long.valueOf(profile.getOfferId()));
			//return ResponseEntity.status(200).body("ANA onboarding request submitted successfully with trsansaction Id- " + trxId);
			if(trxId.contains("Fail")) {
			return ResponseEntity.status(500).body(trxId);
			}else {
			return ResponseEntity.status(200).body(trxId);
			}
		} catch (ApplicationException e) {
			return ResponseEntity.status(500).body(e.getErrorMessage());
		} catch(Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
	
	@GetMapping(value={"/getAvailableDigitalOffers"}, produces= MediaType.APPLICATION_JSON_VALUE, name="Get All Digital Offers")
	public ResponseEntity<?> getAvailableDigitalOffers(HttpServletRequest servlet) throws ApplicationException {
		LOGGER.info("Invoked digital subscriber onboarding");
		try {
			List<DigitalOffer> offers = digitalOnboardingService.getAllDigitalOffers();
			return ResponseEntity.status(200).body(offers);
		} catch (ApplicationException e) {
			return ResponseEntity.status(500).body(e.getErrorMessage());
		} catch(Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
	
	@GetMapping(value={"/getAvailableTrx"}, produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAvailabletransactionForUser(@RequestParam(name="username", required=true) String username, HttpServletRequest servlet) throws ApplicationException {
		LOGGER.info("Invoked digital subscriber onboarding");
		try {
			List<NcDigitalOnboarding> trx = digitalOnboardingService.getAllDigitalTransaction(username);
			return ResponseEntity.status(200).body(trx);
		} catch (ApplicationException e) {
			return ResponseEntity.status(500).body(e.getErrorMessage());
		} catch(Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
	
	@CrossOrigin("*")
	@PostMapping(value={"/updateTrx"}, produces= MediaType.APPLICATION_JSON_VALUE, name="Digital Onboarding")
	public ResponseEntity<?> updateTrx(@RequestBody(required=true) AnaTrxUpdateRequest req, HttpServletRequest servlet) throws ApplicationException {
		AnaTrxUpdateResponse resp = new AnaTrxUpdateResponse();
		
		LOGGER.info("Updating transaction details");
		try {
			digitalOnboardingService.updateTransaction(req);
			resp.setResponseStatus("Success");
			return ResponseEntity.status(200).body(resp);
			
		} catch (ApplicationException e) {
			resp.setResponseStatus("Fail");
			resp.setDetails(e.getErrorMessage());
			return ResponseEntity.status(500).body(resp);
			
		} catch(Exception e) {
			resp.setResponseStatus("Fail");
			resp.setDetails(e.getMessage());
			return ResponseEntity.status(500).body(resp);
			
		}
	}
}
