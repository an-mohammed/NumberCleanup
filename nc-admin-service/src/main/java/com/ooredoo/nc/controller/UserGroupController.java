package com.ooredoo.nc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.model.NcAppUserGrp;
import com.ooredoo.nc.service.UserGroupService;
import com.ooredoo.nc.utility.ApplicationConstants;

@RestController
@RequestMapping(value = {"${service.usrgrp.base-url}"})
public class UserGroupController extends Controller implements ApplicationConstants {
	
	@Autowired
	private UserGroupService userGroupService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserGroupController.class);
	
	
	@GetMapping(value={"/getAll"}, produces= MediaType.APPLICATION_JSON_VALUE, name="Add new Number To Number Pool")
	public @ResponseBody List<NcAppUserGrp> getAll(HttpServletRequest servlet) throws ApplicationException {
		LOGGER.info("Invoked getAll service");
		List<NcAppUserGrp> resp = userGroupService.getAllUserGroups();
		return resp;
	}
	
	@GetMapping(value={"/findByName"}, produces= MediaType.APPLICATION_JSON_VALUE, name="Add new Number To Number Pool")
	public @ResponseBody NcAppUserGrp findByName(@RequestParam(name="grpName", required=true) String grpName, HttpServletRequest servlet) throws ApplicationException {
		LOGGER.info("Invoked findByName service");
		NcAppUserGrp resp =userGroupService.findUserGroupDetails(grpName);
		return resp;
	}
	
	@PutMapping(value={"/update"}, produces= MediaType.APPLICATION_JSON_VALUE, name="Add new Number To Number Pool")
	public @ResponseBody NcAppUserGrp update(@RequestBody(required=true) NcAppUserGrp grp, HttpServletRequest servlet) throws ApplicationException {
		LOGGER.info("Invoked update service");
		NcAppUserGrp resp =userGroupService.updateGroup(grp);
		return resp;
	}
	
	@PostMapping(value={"/create"}, produces= MediaType.APPLICATION_JSON_VALUE, name="Add new Number To Number Pool")
	public @ResponseBody NcAppUserGrp create(@RequestBody(required=true) NcAppUserGrp grp, HttpServletRequest servlet) throws ApplicationException {
		LOGGER.info("Invoked create service");
		NcAppUserGrp resp =userGroupService.createGroup(grp);
		return resp;
	}
	
	@GetMapping(value={"/getGroupListForUser"}, produces= MediaType.APPLICATION_JSON_VALUE, name="Add new Number To Number Pool")
	public @ResponseBody List<String> getGroupListForUser(@RequestParam(name="userId", required=true) String userId, HttpServletRequest servlet) throws ApplicationException {
		LOGGER.info("Invoked getGrouplist for user");
		List<String> resp =userGroupService.getGroupListForUser(userId);
		return resp;
	}
	
}
