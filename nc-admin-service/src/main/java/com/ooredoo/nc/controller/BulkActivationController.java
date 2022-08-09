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

import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.model.BulkActivationDetail;
import com.ooredoo.nc.service.BulkActivationService;
import com.ooredoo.nc.utility.ApplicationConstants;

@RestController
@RequestMapping(value = {"${service.bulkactivation.base-url}"})
public class BulkActivationController extends Controller implements ApplicationConstants {
	
	@Autowired
	private BulkActivationService bulkActivationService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BulkActivationController.class);
	
	/**
	 * 
	 * @param username
	 * @param servlet
	 * @return
	 */
	@CrossOrigin(origins = "*")
	@GetMapping(value={"/getActivationDetailsForUsername"}, produces= MediaType.APPLICATION_JSON_VALUE, name="Ceate new subscriber")
	public ResponseEntity<List<BulkActivationDetail>> getActivationDetailsForUser(@RequestParam(required=true) String username, HttpServletRequest servlet) {
		List<BulkActivationDetail> activationDetailsList = null;
		LOGGER.info("Get Activation details for username {}", username);
		try {
			activationDetailsList = bulkActivationService.getActivationDetailsForUser(username);
			
			return ResponseEntity.status(200).body(activationDetailsList);
			
		} catch (ApplicationException e) {
			LOGGER.error("Error occurred while extracting activation details . Cause : ", e);
			return ResponseEntity.status(500).body(activationDetailsList);
			
		} catch (Exception e) {
			LOGGER.error("Error occurred while extracting activation details . Cause : ", e);
			return ResponseEntity.status(500).body(activationDetailsList);
		}
	}
	
	/**
	 * 
	 * @param activationList
	 * @param servlet
	 * @return
	 */
	@PostMapping(value={"/createActivationDetailsForUser"}, name="Ceate new subscriber")
	public ResponseEntity<String> createActivationDetailsForUser(@RequestBody(required = true) List<BulkActivationDetail> activationList, HttpServletRequest servlet) {
		try {
			Long batchId = bulkActivationService.createActivationDetailsForUser(activationList);
			
			return ResponseEntity.status(200).body("Request successfully posted with batch Id-" + batchId);
			
		} catch (ApplicationException e) {
			LOGGER.error("Error occurred while creating activation details . Cause : ", e);
			return ResponseEntity.status(200).body(e.getErrorMessage());
			
		} catch (Exception e) {
			LOGGER.error("Error occurred while creating activation details . Cause : ", e);
			return ResponseEntity.status(200).body(e.getMessage());
		}
	}
}
