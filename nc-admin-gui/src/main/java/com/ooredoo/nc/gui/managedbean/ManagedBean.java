package com.ooredoo.nc.gui.managedbean;

import java.util.Locale;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ooredoo.nc.gui.configuration.SessionHandler;
import com.ooredoo.nc.gui.service.ConfigService;
import com.ooredoo.nc.gui.service.NumberService;
import com.ooredoo.nc.gui.service.ProfileService;
import com.ooredoo.nc.gui.service.RoleService;
import com.ooredoo.nc.gui.service.UserGrpService;
import com.ooredoo.nc.gui.service.UserService;
import com.ooredoo.nc.model.AppUser;

@Named(value="mb")
@ViewScoped
public class ManagedBean {

	@Inject
	@Named("userSerivce")
	private UserService userService;
	
	@Inject
	@Named("roleSerivce")
	private RoleService roleService;
	
	@Inject
	@Named("profileSerivce")
	private ProfileService profileService;
	
	@Inject
	@Named("numberPoolSerivce")
	private NumberService numberPoolSerivce;
	
	@Inject
	@Named("configurationSerivce")
	private ConfigService configService;
	
	@Inject
	@Named("userGrpSerivce")
	private UserGrpService userGrpService;
	
	@Inject
	@Named("messageSource")
	private MessageSource messageSource;
	
	@Autowired
	private ReloadableResourceBundleMessageSource externalConfig;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ManagedBean.class);
	
	public String getFatalErrorMessage() {
		return messageSource.getMessage("AUI.000", null, "Fatal Error: Please contact system admin for more details", Locale.ENGLISH);
	}
	
	public String getActionUser() {
		AppUser loggedInUser = null;
		
		loggedInUser = SessionHandler.getLoggerInUserInfo();
		
		if(null == loggedInUser) {
			loggedInUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		LOGGER.debug("Logged user details : {}", loggedInUser.getUName());
		
		return loggedInUser.getUName();
	}
	
	public Long getActionUserId() {
		AppUser loggedInUser = null;
		
		loggedInUser = SessionHandler.getLoggerInUserInfo();
		
		if(null == loggedInUser) {
			loggedInUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		
		return loggedInUser.getId();
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	public String getMessage(String key) {
		return getMessageSource().getMessage(key, null, Locale.ENGLISH);
	}
	
	public String getMessage(String key, Object[] args) {
		return getMessageSource().getMessage(key, args, Locale.ENGLISH);
	}

	public ProfileService getProfileService() {
		return profileService;
	}

	public void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
	}

	public String getExternalConfigValue(String key) {
		return externalConfig.getMessage(key, null, Locale.getDefault());
	}

	public NumberService getNumberPoolSerivce() {
		return numberPoolSerivce;
	}

	public void setNumberPoolSerivce(NumberService numberPoolSerivce) {
		this.numberPoolSerivce = numberPoolSerivce;
	}

	public ConfigService getConfigService() {
		return configService;
	}

	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

	public UserGrpService getUserGrpService() {
		return userGrpService;
	}

	public void setUserGrpService(UserGrpService userGrpService) {
		this.userGrpService = userGrpService;
	}
}
