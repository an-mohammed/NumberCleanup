package com.ooredoo.nc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ooredoo.nc.dto.ReleaseNumberFromAssignmentReq;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.model.AppUser;
import com.ooredoo.nc.model.NumberAsgnmtHistory;
import com.ooredoo.nc.model.NumberPool;
import com.ooredoo.nc.service.NumberPoolService;
import com.ooredoo.nc.utility.ApplicationConstants;

@RestController
@RequestMapping(value = {"${service.numberpool.base-url}"})
public class NumberPoolController extends Controller implements ApplicationConstants {
	
	@Autowired
	private NumberPoolService numberPoolService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NumberPoolController.class);
	
	
	@PostMapping(value={"/addNumberToPool"}, consumes=MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE, name="Add new Number To Number Pool")
	public @ResponseBody NumberPool addNumberToPool(@RequestBody(required=true) NumberPool req, HttpServletRequest servlet) throws ApplicationException {
		LOGGER.info("Invoked addNumberToPool service");
		NumberPool resp = numberPoolService.addNumberToPool(req);
		return resp;
	}
	
	@GetMapping(value={"/getAll"}, produces= MediaType.APPLICATION_JSON_VALUE, name="Add new Number To Number Pool")
	public @ResponseBody List<NumberPool> getAll(HttpServletRequest servlet) throws ApplicationException {
		LOGGER.info("Invoked getAll service");
		List<NumberPool> resp = numberPoolService.getAllNumbers();
		return resp;
	}
	
	@GetMapping(value={"/getAllAssignment"}, produces= MediaType.APPLICATION_JSON_VALUE, name="Add new Number To Number Pool")
	public @ResponseBody List<NumberAsgnmtHistory> getAllAssignment(HttpServletRequest servlet) throws ApplicationException {
		LOGGER.info("Invoked getAllAssignment service");
		List<NumberAsgnmtHistory> resp = numberPoolService.getAllAssignment();
		
		if(null != resp && !resp.isEmpty()) {
			ListIterator<NumberAsgnmtHistory> li = resp.listIterator();
			
			while(li.hasNext()) {
				NumberAsgnmtHistory i = li.next();
				i.setAssignedToUser(i.getAppUser().getFirstname() + " " + i.getAppUser().getLastname());
				li.set(i);
			}
		}
		
		return resp;
	}
	
	@GetMapping(value={"/getAssignmentForUser"}, produces= MediaType.APPLICATION_JSON_VALUE, name="Get All Assignment for user")
	public @ResponseBody List<NumberAsgnmtHistory> getAssignmentForUser(@RequestParam(required=true, name="userId") Long userId, HttpServletRequest servlet) throws ApplicationException {
		LOGGER.info("Invoked getAssignmentForUser service");
		List<NumberAsgnmtHistory> resp = numberPoolService.getAvailableAssignmentForUser(userId);
		return resp;
	}
	
	@GetMapping(value={"/getAllActive"}, produces= MediaType.APPLICATION_JSON_VALUE, name="Add new Number To Number Pool")
	public @ResponseBody List<NumberPool> getAllActive(HttpServletRequest servlet) throws ApplicationException {
		LOGGER.info("Invoked getAll service");
		List<NumberPool> resp = numberPoolService.getAllActiveNumbers();
		return resp;
	}
	
	@GetMapping(value={"/getAvailable"}, produces= MediaType.APPLICATION_JSON_VALUE, name="Add new Number To Number Pool")
	public @ResponseBody List<NumberPool> getAvailable(HttpServletRequest servlet) throws ApplicationException {
		LOGGER.info("Invoked getAvailable service");
		List<NumberPool> resp = numberPoolService.getAllAvailableNumbers();
		return resp;
	}
	
	@PutMapping(value={"/enableNumber"}, produces= MediaType.APPLICATION_JSON_VALUE, name="Add new Number To Number Pool")
	public @ResponseBody NumberPool enableNumber(@RequestParam(name="msisdn", required=true) String msisdn, @RequestParam(name="username", required=true)String username, HttpServletRequest servlet) throws ApplicationException {
		LOGGER.info("Invoked enableNumber service");
		NumberPool resp = numberPoolService.enableNumber(msisdn, username);
		return resp;
	}
	
	@PutMapping(value={"/disableNumber"}, produces= MediaType.APPLICATION_JSON_VALUE, name="Add new Number To Number Pool")
	public @ResponseBody NumberPool disableNumber(@RequestParam(name="msisdn", required=true) String msisdn, @RequestParam(name="username", required=true)String username, HttpServletRequest servlet) throws ApplicationException {
		LOGGER.info("Invoked disableNumber service");
		NumberPool resp = numberPoolService.disableNumber(msisdn, username);
		return resp;
	}
	
	@PutMapping(value={"/updateNumber"}, consumes=MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE, name="Add new Number To Number Pool")
	public @ResponseBody NumberPool updateNumber(@RequestBody(required=true) NumberPool req, HttpServletRequest servlet) throws ApplicationException {
		LOGGER.info("Invoked updateNumber service");
		NumberPool resp = numberPoolService.updateNumberToPool(req);
		return resp;
	}
	
	@PutMapping(value={"/releaseNumberFromAssignment"}, consumes=MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE, name="Add new Number To Number Pool")
	public @ResponseBody NumberPool releaseNumberFromAssignment(@RequestBody(required=true) ReleaseNumberFromAssignmentReq req, HttpServletRequest servlet) throws ApplicationException {
		LOGGER.info("Invoked updateNumber service");
		NumberPool resp = numberPoolService.updateNumberToPool(req);
		return resp;
	}
	
	@PostMapping(value={"/createAssignment"}, consumes=MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE, name="Add new Number To Number Pool")
	public @ResponseBody NumberAsgnmtHistory createAssignment(@RequestBody(required=true) NumberAsgnmtHistory req, HttpServletRequest servlet) throws ApplicationException {
		LOGGER.info("Invoked createAssignment service");
		NumberAsgnmtHistory resp = numberPoolService.createAssignment(req);
		return resp;
	}
	
	@PostMapping(value={"/completeAssignment"}, consumes=MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE, name="Add new Number To Number Pool")
	public @ResponseBody NumberAsgnmtHistory completeAssignment(@RequestBody(required=true) NumberAsgnmtHistory req, HttpServletRequest servlet) throws ApplicationException {
		LOGGER.info("Invoked completeAssignment service");
		NumberAsgnmtHistory resp = numberPoolService.completeAssignment(req);
		return resp;
	}
	
	@PutMapping(value={"/updateAssignment"}, consumes=MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE, name="Add new Number To Number Pool")
	public @ResponseBody NumberAsgnmtHistory updateAssignment(@RequestBody(required=true) NumberAsgnmtHistory req, HttpServletRequest servlet) throws ApplicationException {
		LOGGER.info("Invoked updateAssignment service");
		NumberAsgnmtHistory resp = numberPoolService.updateAssignment(req);
		return resp;
	}
	
	@GetMapping(value={"/searchAssignment"}, produces= MediaType.APPLICATION_JSON_VALUE, name="Search assignment with MSISDN")
	public @ResponseBody List<NumberAsgnmtHistory>  searchAssignment(@RequestParam(required=true, name="msisdn")String msisdn, HttpServletRequest servlet) throws ApplicationException {
		LOGGER.info("Invoked search assignment service");
		List<NumberAsgnmtHistory> resp = numberPoolService.searchAssignment(msisdn);
		
		if(null != resp && !resp.isEmpty()) {
			ListIterator<NumberAsgnmtHistory> li = resp.listIterator();
			
			while(li.hasNext()) {
				NumberAsgnmtHistory i = li.next();
				i.setAssignedToUser(i.getAppUser().getFirstname() + " " + i.getAppUser().getLastname());
				li.set(i);
			}
		}
		
		return resp;
	}
	
	@GetMapping(value={"/searchNumber"}, produces= MediaType.APPLICATION_JSON_VALUE, name="Search assignment with MSISDN")
	public @ResponseBody NumberPool  searchNumber(@RequestParam(required=true, name="msisdn")String msisdn, HttpServletRequest servlet) throws ApplicationException {
		LOGGER.info("Invoked search number service");
		NumberPool resp = numberPoolService.searchNumber(msisdn);
		
		return resp;
	}
	
	@GetMapping(value={"/getAllAssignedNumberToUserGroup"}, produces= MediaType.APPLICATION_JSON_VALUE, name="Get All Assignment for user")
	public @ResponseBody List<NumberPool> getAllAssignedNumberToUserGroup(@RequestParam(required=true, name="userId") Long userId, HttpServletRequest servlet) throws ApplicationException {
		LOGGER.info("Invoked getAssignmentForUser service");
		List<NumberPool> resp = numberPoolService.getAllAssignedNumberToUserGroup(userId);
		return resp;
	}
	
	@GetMapping(value={"/getAllActiveNumbersForUser"}, produces= MediaType.APPLICATION_JSON_VALUE, name="Get All Assignment for user")
	public @ResponseBody List<NumberPool> getAllActiveNumbersForUser(@RequestParam(required=true, name="userId") Long userId, HttpServletRequest servlet) throws ApplicationException {
		LOGGER.info("Invoked getAllActiveNumbersForUser service");
		List<NumberPool> resp = numberPoolService.getAllActiveNumbersForUser(userId);
		return resp;
	}
	
	@GetMapping(value={"/getAllActiveNumbersForUsername"}, produces= MediaType.APPLICATION_JSON_VALUE, name="Get All Assignment for user")
	@CrossOrigin(origins = "*")
	public @ResponseBody List<String> getAllActiveNumbersForUsername(@RequestParam(required=true, name="username") String username, HttpServletRequest servlet) throws ApplicationException {
		List<String> msisdn = null;
		LOGGER.info("Invoked getAllActiveNumbersForUsername service");
		Optional<AppUser> userO = getRepository().getUserRepo().findByUNameAndIsEnabledEquals(username, true);
		
		if(userO.isPresent()) {
			AppUser au = userO.get();
			List<NumberPool> resp = null;
			
			if(au.isAdminUser() || au.isSuperUser()) {
				resp = numberPoolService.getAllActiveNumbers();
			} else {
				resp = numberPoolService.getAllActiveNumbersForUser(au.getId());
			}
			
			if(null != resp && !resp.isEmpty()) {
				//String msisdnWithoutPrefix = null;
				msisdn = new ArrayList<String>();
				
				for(NumberPool np : resp) {
					//msisdnWithoutPrefix = np.getMsisdn().replaceFirst(MSISDN_PREFIX, BLANK);
					msisdn.add(np.getMsisdn());
				}
			} 
		} else {
			throw new ApplicationException("Invalid username or user is not active", true);
		}
		
		return msisdn;
	}
	
}
