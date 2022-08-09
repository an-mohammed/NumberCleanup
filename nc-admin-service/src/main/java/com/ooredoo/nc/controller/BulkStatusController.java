package com.ooredoo.nc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ooredoo.nc.dto.StatusRequest;
import com.ooredoo.nc.dto.StatusResponse;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.service.BulkStatusService;
import com.ooredoo.nc.utility.ApplicationConstants;

@RestController
@RequestMapping(value = {"${service.bulkstatus.base-url}"})
public class BulkStatusController extends Controller implements ApplicationConstants {
	
	@Autowired
	private BulkStatusService bulkStatusService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BulkStatusController.class);
	
	@CrossOrigin(origins = "*")
	@PostMapping(value={"/getProfileStatus"}, produces= MediaType.APPLICATION_JSON_VALUE, name="GetProfileStatus")
	public @ResponseBody StatusResponse getProfileStatus(@RequestBody(required = true) StatusRequest request) {
		StatusResponse resp = null;
		
		try {
			resp = bulkStatusService.getProfileStatus(request);
		} catch(ApplicationException ae) {
			resp = new StatusResponse();
			resp.setErrorCode("500");
			resp.setErrorMessage(ae.getErrorMessage());
			
			LOGGER.error("Error occurred while extracting profile status. Cause :" , ae);
		} catch(Exception e) {
			resp = new StatusResponse();
			resp.setErrorCode("500");
			resp.setErrorMessage(e.getMessage());
			
			LOGGER.error("Error occurred while extracting profile status. Cause :" , e);
		}
		
		return resp;
	}
}
