package com.ooredoo.nc.gui.managedbean;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.ooredoo.nc.gui.configuration.SessionHandler;
import com.ooredoo.nc.utility.ApplicationConstants;

@Named(value="so")
@ViewScoped
public class SubscriberOnboarding extends ManagedBean implements ApplicationConstants {
	private static final Logger LOGGER = LoggerFactory.getLogger(SubscriberOnboarding.class);
	
	@Value("${service.url.subscriber.onboarding}")
	private String subscriberOnboardingUrl;

	public String getSubscriberOnboardingUrl() {
		this.subscriberOnboardingUrl = subscriberOnboardingUrl + "?username=" + SessionHandler.getLoggerInUserInfo().getUName();
		LOGGER.info("Subscriber onboarding URL -{}", this.subscriberOnboardingUrl);
		return subscriberOnboardingUrl;
	}

	public void setSubscriberOnboardingUrl(String subscriberOnboardingUrl) {
		this.subscriberOnboardingUrl = subscriberOnboardingUrl;
	}
	
	
}
