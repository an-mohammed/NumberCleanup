package com.ooredoo.nc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ooredoo.nc.dto.SubscriberProfileCleanupRequest;
import com.ooredoo.nc.dto.SubscriberProfileCleanupResponse;
import com.ooredoo.nc.dto.SubscriberProfileManagementResponse;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.model.CleanupLogMaster;
import com.ooredoo.nc.service.CleanupHistoryService;
import com.ooredoo.nc.service.ProfileCleanupService;
import com.ooredoo.nc.service.ProfileManagementService;
import com.ooredoo.nc.utility.ApplicationConstants;

@RestController
@RequestMapping(value = {"${service.profilemanagement.base-url}"})
public class ProfileManagementController extends Controller implements ApplicationConstants {
	
	@Autowired
	private ProfileCleanupService cleanupService;
	
	@Autowired
	private CleanupHistoryService historyService;
	
	@Autowired
	private ProfileManagementService profileManagementService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProfileManagementController.class);
	
	
	@PostMapping(value={"/cleanup"}, consumes=MediaType.APPLICATION_JSON_VALUE	, produces= MediaType.APPLICATION_JSON_VALUE, name="Cleanup user profile")
	public @ResponseBody SubscriberProfileCleanupResponse cleanupProfile(@RequestBody(required=true) SubscriberProfileCleanupRequest req, HttpServletRequest servlet) throws ApplicationException {
		LOGGER.info("Invoked cleanup service");
		SubscriberProfileCleanupResponse resp = cleanupService.handleCleanup(req, true);
		resp.setProfiles(req.getProfiles());
		return resp;
	}
	
	@GetMapping(value={"/search"}, produces= MediaType.APPLICATION_JSON_VALUE, name="Manage Subscriber Profile")
	public @ResponseBody SubscriberProfileManagementResponse searchProfile(@RequestParam(required=true, name="msisdn") String msisdn, HttpServletRequest servlet) throws ApplicationException {
		LOGGER.info("Invoked cleanup search");
		return profileManagementService.extractProfile(msisdn);
	}
	
	@GetMapping(value={"/searchHistory"}, produces= MediaType.APPLICATION_JSON_VALUE, name="Extract cleanup history")
	public List<CleanupLogMaster> showCleanupHistory(@RequestParam(required=true, name="msisdn") String msisdn, HttpServletRequest servlet) throws ApplicationException {
		LOGGER.info("Invoked cleanup search history");
		return historyService.searchHist(msisdn);
	}
}
