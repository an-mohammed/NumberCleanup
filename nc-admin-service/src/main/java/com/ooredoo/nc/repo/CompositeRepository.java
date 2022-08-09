package com.ooredoo.nc.repo;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompositeRepository implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private PrivilegeRepo privRepo;
	
	@Autowired
	private CleanupHistoryRepo historyRepo;
	
	@Autowired
	private NumberPoolRepo numberPoolRepo;
	
	@Autowired
	private NumberAssignmentHistRepo aHistRepo;
	
	@Autowired
	private UserGroupRepo userGroupRepo;
	
	@Autowired
	private BulkActivationDetailsRepo bulkActivationRepo;
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private DigitalCleanupRepo digitalCleanupRepo;
	
	@Autowired
	private DigitalOfferRepo digitalOfferRepo;
	
	
	public PrivilegeRepo getPrivRepo() {
		return privRepo;
	}
	public void setPrivRepo(PrivilegeRepo privRepo) {
		this.privRepo = privRepo;
	}
	public RoleRepo getRoleRepo() {
		return roleRepo;
	}
	public void setRoleRepo(RoleRepo roleRepo) {
		this.roleRepo = roleRepo;
	}
	
	public UserRepo getUserRepo() {
		return userRepo;
	}
	public void setUserRepo(UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	public CleanupHistoryRepo getHistoryRepo() {
		return historyRepo;
	}
	public void setHistoryRepo(CleanupHistoryRepo historyRepo) {
		this.historyRepo = historyRepo;
	}
	public NumberPoolRepo getNumberPoolRepo() {
		return numberPoolRepo;
	}
	public void setNumberPoolRepo(NumberPoolRepo numberPoolRepo) {
		this.numberPoolRepo = numberPoolRepo;
	}
	public NumberAssignmentHistRepo getaHistRepo() {
		return aHistRepo;
	}
	public void setaHistRepo(NumberAssignmentHistRepo aHistRepo) {
		this.aHistRepo = aHistRepo;
	}
	public UserGroupRepo getUserGroupRepo() {
		return userGroupRepo;
	}
	public void setUserGroupRepo(UserGroupRepo userGroupRepo) {
		this.userGroupRepo = userGroupRepo;
	}
	public BulkActivationDetailsRepo getBulkActivationRepo() {
		return bulkActivationRepo;
	}
	public void setBulkActivationRepo(BulkActivationDetailsRepo bulkActivationRepo) {
		this.bulkActivationRepo = bulkActivationRepo;
	}
	public CommonDao getCommonDao() {
		return commonDao;
	}
	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}
	public DigitalCleanupRepo getDigitalCleanupRepo() {
		return digitalCleanupRepo;
	}
	public void setDigitalCleanupRepo(DigitalCleanupRepo digitalCleanupRepo) {
		this.digitalCleanupRepo = digitalCleanupRepo;
	}
	public DigitalOfferRepo getDigitalOfferRepo() {
		return digitalOfferRepo;
	}
	public void setDigitalOfferRepo(DigitalOfferRepo digitalOfferRepo) {
		this.digitalOfferRepo = digitalOfferRepo;
	}
}
