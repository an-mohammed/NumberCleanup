package com.ooredoo.nc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.model.AppUser;
import com.ooredoo.nc.model.NcAppUserGrp;
import com.ooredoo.nc.model.NumberPool;
import com.ooredoo.nc.repo.CompositeRepository;
import com.ooredoo.nc.utility.ApplicationConstants;

@Service
public class UserGroupService implements ApplicationConstants {
	
	@Autowired
	private CompositeRepository compositeRepo;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserGroupService.class);
	
	/**
	 * 
	 * @return
	 */
	public List<NcAppUserGrp> getAllUserGroups() {
		return compositeRepo.getUserGroupRepo().findAll();
	}
	
	/**
	 * 
	 * @return
	 */
	public NcAppUserGrp findUserGroupDetails(String groupName) {
		Optional<NcAppUserGrp> grpO = compositeRepo.getUserGroupRepo().findByGrpName(groupName);
		return grpO.isPresent() ? grpO.get() : null;
	}
	
	public NcAppUserGrp createGroup(NcAppUserGrp grp) throws ApplicationException {
		NcAppUserGrp updatedUserGroup = null;
		
		if(null == grp.getGrpName() || grp.getGrpName().isEmpty()) {
			throw new ApplicationException("CAS.008", new Object[] {"User Group", "Group Name"});
		}
		
		if(null == grp.getGrpDesc() || grp.getGrpDesc().isEmpty()) {
			throw new ApplicationException("CAS.008", new Object[] {"User Group", "Description"});
		}
		
		if(null == grp.getCreatedBy()) {
			throw new ApplicationException("CAS.008", new Object[] {"User Group", "Created By User"});
		}
		
		if(!compositeRepo.getUserGroupRepo().existsByGrpName(grp.getGrpName())) {
			Optional<AppUser> createdByO = compositeRepo.getUserRepo().findById(grp.getCreatedBy());
			
			if(!createdByO.isPresent()) {
				throw new ApplicationException("CAS.013", new Object[] {grp.getCreatedBy()});
			}
			
			if(!createdByO.get().isAdminUser() && !createdByO.get().isSuperUser()) {
				throw new ApplicationException("CAS.050", new Object[] {grp.getCreatedBy()});
			}
			
			grp.setCreatedOn(new Date());
			
			if(null != grp.getAllocatedNumbers() && !grp.getAllocatedNumbers().isEmpty()) {
				List<NumberPool> newNumberList = new ArrayList<NumberPool>();
				
				for(NumberPool np : grp.getAllocatedNumbers()) {
					Optional<NumberPool> n =compositeRepo.getNumberPoolRepo().findByMsisdn(np.getMsisdn());
					
					if(n.isPresent()) {
						newNumberList.add(n.get());
					} else {
						throw new ApplicationException("CAS.051", new Object[] {np.getMsisdn()});
					}
				}
				
				grp.getAllocatedNumbers().clear();
				grp.setAllocatedNumbers(newNumberList);
			}
			
			if(null != grp.getGroupMembers() && !grp.getGroupMembers().isEmpty()) {
				List<AppUser> newUserList = new ArrayList<AppUser>();
				
				for(AppUser np : grp.getGroupMembers()) {
					Optional<AppUser> n =compositeRepo.getUserRepo().findByUName(np.getUName());
					
					if(n.isPresent()) {
						newUserList.add(n.get());
					} else {
						throw new ApplicationException("CAS.013", new Object[] {np.getUName()});
					}
				}
				
				if(null != grp.getNcUserGrps() && !grp.getNcUserGrps().isEmpty()) {
					grp.getNcUserGrps().clear();
				}
				
				grp.setNcUserGrps(newUserList);
			}
			
			LOGGER.info("Createing new user group with group name : {}", grp.getGrpName());
			updatedUserGroup = compositeRepo.getUserGroupRepo().save(grp);
			
			LOGGER.info("New user group successfully created with group name : {}", grp.getGrpName());
			
		} else {
			throw new ApplicationException("CAS.049", new Object[] {grp.getGrpName()});
		}
		
		return updatedUserGroup;
	}
	
	public NcAppUserGrp updateGroup(NcAppUserGrp grp) throws ApplicationException {
		NcAppUserGrp updatedUserGroup = null;
		
		
		if(compositeRepo.getUserGroupRepo().existsByGrpName(grp.getGrpName())) {
			Optional<NcAppUserGrp> existingGrpO = compositeRepo.getUserGroupRepo().findByGrpName(grp.getGrpName());
			
			if(existingGrpO.isPresent()) {
				NcAppUserGrp existingGrp = existingGrpO.get();
				
				if(null != grp.getGrpDesc() && !grp.getGrpDesc().isEmpty()) {
					existingGrp.setGrpDesc(grp.getGrpDesc());
				}
				
				
				if(null != grp.getAllocatedNumbers() && !grp.getAllocatedNumbers().isEmpty()) {
					List<NumberPool> newNumberList = new ArrayList<NumberPool>();
					
					for(NumberPool np : grp.getAllocatedNumbers()) {
						Optional<NumberPool> n =compositeRepo.getNumberPoolRepo().findByMsisdn(np.getMsisdn());
						
						if(n.isPresent()) {
							newNumberList.add(n.get());
						} else {
							throw new ApplicationException("CAS.031", new Object[] {np.getMsisdn()});
						}
					}
					
					existingGrp.getAllocatedNumbers().clear();
					existingGrp.setAllocatedNumbers(newNumberList);
				}
				
				if(null != grp.getGroupMembers() && !grp.getGroupMembers().isEmpty()) {
					List<AppUser> newUserList = new ArrayList<AppUser>();
					
					for(AppUser np : grp.getGroupMembers()) {
						Optional<AppUser> n =compositeRepo.getUserRepo().findByUName(np.getUName());
						
						if(n.isPresent()) {
							newUserList.add(n.get());
						} else {
							throw new ApplicationException("CAS.003", new Object[] {np.getUName()});
						}
					}
					
					existingGrp.getNcUserGrps().clear();
					existingGrp.setNcUserGrps(newUserList);
				}
				
				
				updatedUserGroup = compositeRepo.getUserGroupRepo().save(existingGrp);
				
			} else {
				throw new ApplicationException("CAS.048", new Object[] {grp.getGrpName()});
			}
		} else {
			throw new ApplicationException("CAS.048", new Object[] {grp.getGrpName()});
		}
		
		return updatedUserGroup;
	}
	
	public List<String> getGroupListForUser(String userId) throws ApplicationException{
		List<String> grpList = null;
		List<NcAppUserGrp> usrGrpList = compositeRepo.getUserGroupRepo().getAllGroupsForUser(Long.valueOf(userId));
		
		if(null != usrGrpList && !usrGrpList.isEmpty()) {
			grpList = new ArrayList<String>();
			
			for(NcAppUserGrp e : usrGrpList) {
				grpList.add(e.getGrpName());
			}
		}
		
		return grpList;
	}
}
