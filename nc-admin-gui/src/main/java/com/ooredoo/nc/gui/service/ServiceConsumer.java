package com.ooredoo.nc.gui.service;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ooredoo.nc.utility.ExchangeInternalService;
import com.ooredoo.nc.utility.ExchangeService;

@Component("consumer")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ServiceConsumer {
	
	@Inject
	@Named(value="exchangeService")
	private ExchangeService exchangeService;
	
	@Inject
	@Named(value="exchangeInternalService")
	private ExchangeInternalService exchangeInternalService;
	
	@Value("${consumer.service.url.user.authenticate}")
	private String authenticateUserUrl;
	
	@Value("${consumer.service.url.user.create}")
	private String createUserUrl;
	
	@Value("${consumer.service.url.user.search}")
	private String searchUserUrl;
	
	@Value("${consumer.service.url.user.findAllValid}")
	private String findAllValidUserUrl;

	@Value("${consumer.service.url.user.resetPassword}")
	private String resetUserPasswordUrl;
	
	@Value("${consumer.service.url.user.update}")
	private String updateUserUrl;
	
	@Value("${consumer.service.url.role.findByName}")
	private String findRoleByNameUrl;
	
	@Value("${consumer.service.url.role.findAll}")
	private String findAllRolesUrl;
	
	@Value("${consumer.service.url.role.create}")
	private String createRoleUrl;
	
	@Value("${consumer.service.url.priv.findByName}")
	private String findPrivByName;
	
	@Value("${consumer.service.url.priv.findAll}")
	private String findAllPrivUrl;
	
	@Value("${consumer.service.url.priv.create}")
	private String createPrivUrl;
	
	@Value("${consumer.service.url.role.update}")
	private String updateRoleUrl;
	
	@Value("${consumer.service.url.surole.findAll}")
	private String findAllServiceRoleUrl;
	
	@Value("${consumer.service.url.serviceuser.create}")
	private String createServiceUserUrl;
	
	@Value("${consumer.service.url.serviceuser.search}")
	private String searchServiceUserUrl;
	
	@Value("${consumer.service.url.serviceuser.update}")
	private String updateServiceUserUrl;
	
	@Value("${consumer.service.url.profile.cleanup}")
	private String profileCleanupUrl;
	
	@Value("${consumer.service.url.erp.getAllLocation}")
	private String allErpLocationsUrl;
	
	@Value("${consumer.service.url.profile.search}")
	private String profileSearchUrl;
	
	@Value("${consumer.service.url.hist.search}")
	private String cleanupHistSearchUrl;
	
	@Value("${consumer.service.url.np.getAllNumbers}")
	private String allNumbersUrl;
	
	@Value("${consumer.service.url.np.updateNumber}")
	private String updateNumberUrl;
	
	@Value("${consumer.service.url.np.releaseNumberFromAssignment}")
	private String releaseNumberFromAssignmentUrl;
	
	@Value("${consumer.service.url.np.addNumber}")
	private String addNumberUrl;
	
	@Value("${consumer.service.url.np.allAssignment}")
	private String allAssignmentUrl;
	
	@Value("${consumer.service.url.np.finishAssignment}")
	private String finishAssignmentUrl;
	
	@Value("${consumer.service.url.np.allAssignmentForAdmin}")
	private String allAssignmentForAdminUrl;
	
	@Value("${consumer.service.url.np.updateAssignment}")
	private String updateAssignmentUrl;
	
	@Value("${consumer.service.url.np.createAssignment}")
	private String createAssignmentUrl;
	
	@Value("${consumer.service.url.np.searchAssignment}")
	private String searchAssignmentUrl;
	
	@Value("${consumer.service.url.np.searchNumber}")
	private String searchNumberUrl;
	
	@Value("${consumer.service.url.np.availableNumbers}")
	private String availableNumbersUrl;
	
	@Value("${consumer.service.url.np.availableNumbersToUserGroup}")
	private String availableNumbersToUserGrpUrl;
	
	@Value("${consumer.service.url.np.allActiveNumbersForUser}")
	private String allActiveNumbersForUserUrl;
	
	@Value("${consumer.service.url.np.allActiveNumbersForUsername}")
	private String allActiveNumbersForUsernameUrl;
	
	@Value("${consumer.service.url.config.systemConfig}")
	private String systemConfigUrl;
	
	@Value("${consumer.service.url.config.createNumber}")
	private String createNumberUrl;
	
	@Value("${consumer.service.url.config.subscriberOnboarding}")
	private String subscriberOnboardingUrl;
	
	@Value("${consumer.service.url.config.subscriberOnboardingB2B}")
	private String subscriberOnboardingB2BUrl;
	
	@Value("${consumer.service.url.config.subscriberOnboardingAna}")
	private String subscriberOnboardingAnaUrl;
	
	@Value("${consumer.service.url.config.digitalPromoList}")
	private String digitalPromoListUrl;
	
	@Value("${consumer.service.url.config.digitalOnboardingTransactions}")
	private String digitalOnboardingTransactionsUrl;
	
	@Value("${consumer.service.url.config.bulkActivationDetails}")
	private String bulkActivationDetailsUrl;
	
	@Value("${consumer.service.url.config.createBulkActivationDetails}")
	private String createBulkActivationDetailsUrl;
	
	@Value("${consumer.service.url.config.subscriberDisconnection}")
	private String subscriberDisconnectionUrl;
	
	@Value("${consumer.service.url.grp.findAll}")
	private String findAllGroupsUrl;
	
	@Value("${consumer.service.url.grp.findAllGroupsForUserUrl}")
	private String findAllGroupsForUserUrl;
	
	@Value("${consumer.service.url.grp.findByName}")
	private String findGroupByNameUrl;
	
	@Value("${consumer.service.url.grp.create}")
	private String createGroupUrl;
	
	@Value("${consumer.service.url.grp.update}")
	private String updateGroupUrl;
	
	@Value("${consumer.service.url.bulk.status}")
	private String bulkProfileStatusUrl;

	public String getResetUserPasswordUrl() {
		return resetUserPasswordUrl;
	}

	public void setResetUserPasswordUrl(String resetUserPasswordUrl) {
		this.resetUserPasswordUrl = resetUserPasswordUrl;
	}
	
	public String getFindRoleByNameUrl() {
		return findRoleByNameUrl;
	}

	public void setFindRoleByNameUrl(String findRoleByNameUrl) {
		this.findRoleByNameUrl = findRoleByNameUrl;
	}

	public String getFindAllRolesUrl() {
		return findAllRolesUrl;
	}

	public void setFindAllRolesUrl(String findAllRolesUrl) {
		this.findAllRolesUrl = findAllRolesUrl;
	}

	public String getCreateRoleUrl() {
		return createRoleUrl;
	}

	public void setCreateRoleUrl(String createRoleUrl) {
		this.createRoleUrl = createRoleUrl;
	}

	public String getFindPrivByName() {
		return findPrivByName;
	}

	public void setFindPrivByName(String findPrivByName) {
		this.findPrivByName = findPrivByName;
	}

	public String getFindAllPrivUrl() {
		return findAllPrivUrl;
	}

	public void setFindAllPrivUrl(String findAllPrivUrl) {
		this.findAllPrivUrl = findAllPrivUrl;
	}

	public String getCreatePrivUrl() {
		return createPrivUrl;
	}

	public void setCreatePrivUrl(String createPrivUrl) {
		this.createPrivUrl = createPrivUrl;
	}

	public String getUpdateRoleUrl() {
		return updateRoleUrl;
	}

	public void setUpdateRoleUrl(String updateRoleUrl) {
		this.updateRoleUrl = updateRoleUrl;
	}

	public String getUpdatePrivRole() {
		return updatePrivRole;
	}

	public void setUpdatePrivRole(String updatePrivRole) {
		this.updatePrivRole = updatePrivRole;
	}

	@Value("consumer.service.url.priv.update")
	private String updatePrivRole;
	
	
	public String getAuthenticateUserUrl() {
		return authenticateUserUrl;
	}

	public void setAuthenticateUserUrl(String authenticateUserUrl) {
		this.authenticateUserUrl = authenticateUserUrl;
	}

	public ExchangeService getExchangeService() {
		return exchangeService;
	}

	public void setExchangeService(ExchangeService exchangeService) {
		this.exchangeService = exchangeService;
	}

	public String getCreateUserUrl() {
		return createUserUrl;
	}

	public void setCreateUserUrl(String createUserUrl) {
		this.createUserUrl = createUserUrl;
	}
	
	
	public String getSearchUserUrl() {
		return searchUserUrl;
	}

	public void setSearchUserUrl(String searchUserUrl) {
		this.searchUserUrl = searchUserUrl;
	}

	public String getUpdateUserUrl() {
		return updateUserUrl;
	}

	public void setUpdateUserUrl(String updateUserUrl) {
		this.updateUserUrl = updateUserUrl;
	}

	public String getCreateServiceUserUrl() {
		return createServiceUserUrl;
	}

	public void setCreateServiceUserUrl(String createServiceUserUrl) {
		this.createServiceUserUrl = createServiceUserUrl;
	}

	public String getSearchServiceUserUrl() {
		return searchServiceUserUrl;
	}

	public void setSearchServiceUserUrl(String searchServiceUserUrl) {
		this.searchServiceUserUrl = searchServiceUserUrl;
	}

	public String getUpdateServiceUserUrl() {
		return updateServiceUserUrl;
	}

	public void setUpdateServiceUserUrl(String updateServiceUserUrl) {
		this.updateServiceUserUrl = updateServiceUserUrl;
	}

	public String getFindAllServiceRoleUrl() {
		return findAllServiceRoleUrl;
	}

	public void setFindAllServiceRoleUrl(String findAllServiceRoleUrl) {
		this.findAllServiceRoleUrl = findAllServiceRoleUrl;
	}

	public String getProfileCleanupUrl() {
		return profileCleanupUrl;
	}

	public void setProfileCleanupUrl(String profileCleanupUrl) {
		this.profileCleanupUrl = profileCleanupUrl;
	}

	public String getProfileSearchUrl() {
		return profileSearchUrl;
	}

	public void setProfileSearchUrl(String profileSearchUrl) {
		this.profileSearchUrl = profileSearchUrl;
	}

	public String getCleanupHistSearchUrl() {
		return this.cleanupHistSearchUrl;
	}

	public void setCleanupHistSearchUrl(String cleanupHistSearchUrl) {
		this.cleanupHistSearchUrl = cleanupHistSearchUrl;
	}

	public ExchangeInternalService getExchangeInternalService() {
		return exchangeInternalService;
	}

	public void setExchangeInternalService(ExchangeInternalService exchangeInternalService) {
		this.exchangeInternalService = exchangeInternalService;
	}

	public String getAllErpLocationsUrl() {
		return allErpLocationsUrl;
	}

	public void setAllErpLocationsUrl(String allErpLocationsUrl) {
		this.allErpLocationsUrl = allErpLocationsUrl;
	}

	public String getAllNumbersUrl() {
		return allNumbersUrl;
	}

	public void setAllNumbersUrl(String allNumbersUrl) {
		this.allNumbersUrl = allNumbersUrl;
	}

	public String getUpdateNumberUrl() {
		return updateNumberUrl;
	}

	public void setUpdateNumberUrl(String updateNumberUrl) {
		this.updateNumberUrl = updateNumberUrl;
	}

	public String getAddNumberUrl() {
		return addNumberUrl;
	}

	public void setAddNumberUrl(String addNumberUrl) {
		this.addNumberUrl = addNumberUrl;
	}

	public String getAllAssignmentUrl() {
		return allAssignmentUrl;
	}

	public void setAllAssignmentUrl(String allAssignmentUrl) {
		this.allAssignmentUrl = allAssignmentUrl;
	}

	public String getFinishAssignmentUrl() {
		return finishAssignmentUrl;
	}

	public void setFinishAssignmentUrl(String finishAssignmentUrl) {
		this.finishAssignmentUrl = finishAssignmentUrl;
	}

	public String getAllAssignmentForAdminUrl() {
		return allAssignmentForAdminUrl;
	}

	public void setAllAssignmentForAdminUrl(String allAssignmentForAdminUrl) {
		this.allAssignmentForAdminUrl = allAssignmentForAdminUrl;
	}

	public String getUpdateAssignmentUrl() {
		return updateAssignmentUrl;
	}

	public void setUpdateAssignmentUrl(String updateAssignmentUrl) {
		this.updateAssignmentUrl = updateAssignmentUrl;
	}

	public String getFindAllValidUserUrl() {
		return findAllValidUserUrl;
	}

	public void setFindAllValidUserUrl(String findAllValidUserUrl) {
		this.findAllValidUserUrl = findAllValidUserUrl;
	}

	public String getCreateAssignmentUrl() {
		return createAssignmentUrl;
	}

	public void setCreateAssignmentUrl(String createAssignmentUrl) {
		this.createAssignmentUrl = createAssignmentUrl;
	}

	public String getAvailableNumbersUrl() {
		return availableNumbersUrl;
	}

	public void setAvailableNumbersUrl(String availableNumbersUrl) {
		this.availableNumbersUrl = availableNumbersUrl;
	}

	public String getSystemConfigUrl() {
		return systemConfigUrl;
	}

	public void setSystemConfigUrl(String systemConfigUrl) {
		this.systemConfigUrl = systemConfigUrl;
	}

	public String getFindAllGroupsUrl() {
		return findAllGroupsUrl;
	}

	public void setFindAllGroupsUrl(String findAllGroupsUrl) {
		this.findAllGroupsUrl = findAllGroupsUrl;
	}

	public String getFindGroupByNameUrl() {
		return findGroupByNameUrl;
	}

	public void setFindGroupByNameUrl(String findGroupByNameUrl) {
		this.findGroupByNameUrl = findGroupByNameUrl;
	}

	public String getCreateGroupUrl() {
		return createGroupUrl;
	}

	public void setCreateGroupUrl(String createGroupUrl) {
		this.createGroupUrl = createGroupUrl;
	}

	public String getUpdateGroupUrl() {
		return updateGroupUrl;
	}

	public void setUpdateGroupUrl(String updateGroupUrl) {
		this.updateGroupUrl = updateGroupUrl;
	}

	public String getAvailableNumbersToUserGrpUrl() {
		return availableNumbersToUserGrpUrl;
	}

	public void setAvailableNumbersToUserGrpUrl(String availableNumbersToUserGrpUrl) {
		this.availableNumbersToUserGrpUrl = availableNumbersToUserGrpUrl;
	}

	public String getAllActiveNumbersForUserUrl() {
		return allActiveNumbersForUserUrl;
	}

	public void setAllActiveNumbersForUserUrl(String allActiveNumbersForUserUrl) {
		this.allActiveNumbersForUserUrl = allActiveNumbersForUserUrl;
	}

	public String getCreateNumberUrl() {
		return createNumberUrl;
	}

	public void setCreateNumberUrl(String createNumberUrl) {
		this.createNumberUrl = createNumberUrl;
	}

	public String getSearchAssignmentUrl() {
		return searchAssignmentUrl;
	}

	public void setSearchAssignmentUrl(String searchAssignmentUrl) {
		this.searchAssignmentUrl = searchAssignmentUrl;
	}

	public String getSearchNumberUrl() {
		return searchNumberUrl;
	}

	public void setSearchNumberUrl(String searchNumberUrl) {
		this.searchNumberUrl = searchNumberUrl;
	}

	public String getFindAllGroupsForUserUrl() {
		return findAllGroupsForUserUrl;
	}

	public void setFindAllGroupsForUserUrl(String findAllGroupsForUserUrl) {
		this.findAllGroupsForUserUrl = findAllGroupsForUserUrl;
	}

	public String getSubscriberOnboardingUrl() {
		return subscriberOnboardingUrl;
	}

	public void setSubscriberOnboardingUrl(String subscriberOnboardingUrl) {
		this.subscriberOnboardingUrl = subscriberOnboardingUrl;
	}

	public String getAllActiveNumbersForUsernameUrl() {
		return allActiveNumbersForUsernameUrl;
	}

	public void setAllActiveNumbersForUsernameUrl(String allActiveNumbersForUsernameUrl) {
		this.allActiveNumbersForUsernameUrl = allActiveNumbersForUsernameUrl;
	}

	public String getSubscriberDisconnectionUrl() {
		return subscriberDisconnectionUrl;
	}

	public void setSubscriberDisconnectionUrl(String subscriberDisconnectionUrl) {
		this.subscriberDisconnectionUrl = subscriberDisconnectionUrl;
	}

	public String getBulkActivationDetailsUrl() {
		return bulkActivationDetailsUrl;
	}

	public void setBulkActivationDetailsUrl(String bulkActivationDetailsUrl) {
		this.bulkActivationDetailsUrl = bulkActivationDetailsUrl;
	}

	public String getCreateBulkActivationDetailsUrl() {
		return createBulkActivationDetailsUrl;
	}

	public void setCreateBulkActivationDetailsUrl(String createBulkActivationDetailsUrl) {
		this.createBulkActivationDetailsUrl = createBulkActivationDetailsUrl;
	}

	public String getBulkProfileStatusUrl() {
		return bulkProfileStatusUrl;
	}

	public void setBulkProfileStatusUrl(String bulkProfileStatusUrl) {
		this.bulkProfileStatusUrl = bulkProfileStatusUrl;
	}

	public String getDigitalPromoListUrl() {
		return digitalPromoListUrl;
	}

	public void setDigitalPromoListUrl(String digitalPromoListUrl) {
		this.digitalPromoListUrl = digitalPromoListUrl;
	}

	public String getSubscriberOnboardingAnaUrl() {
		return subscriberOnboardingAnaUrl;
	}

	public void setSubscriberOnboardingAnaUrl(String subscriberOnboardingAnaUrl) {
		this.subscriberOnboardingAnaUrl = subscriberOnboardingAnaUrl;
	}

	public String getDigitalOnboardingTransactionsUrl() {
		return digitalOnboardingTransactionsUrl;
	}

	public void setDigitalOnboardingTransactionsUrl(String digitalOnboardingTransactionsUrl) {
		this.digitalOnboardingTransactionsUrl = digitalOnboardingTransactionsUrl;
	}

	public String getReleaseNumberFromAssignmentUrl() {
		return releaseNumberFromAssignmentUrl;
	}

	public void setReleaseNumberFromAssignmentUrl(String releaseNumberFromAssignmentUrl) {
		this.releaseNumberFromAssignmentUrl = releaseNumberFromAssignmentUrl;
	}

	public String getSubscriberOnboardingB2BUrl() {
		return subscriberOnboardingB2BUrl;
	}

	public void setSubscriberOnboardingB2BUrl(String subscriberOnboardingB2BUrl) {
		this.subscriberOnboardingB2BUrl = subscriberOnboardingB2BUrl;
	}
}
