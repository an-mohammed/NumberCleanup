package com.ooredoo.nc.service;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import com.ooredoo.nc.utility.ApplicationConstants;
import com.ooredoo.nc.utility.ExchangeInternalService;
import com.ooredoo.nc.utility.ExchangeService;

@Component("consumerService")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ConsumerService implements ApplicationConstants {
	
	@Inject
	@Named(value="exchangeService")
	private ExchangeService exchangeService;
	
	@Inject
	@Named(value="exchangeInternalService")
	private ExchangeInternalService exchangeInternalService;
	
	@Value("${consumer.service.bscs.cleanup}")
	private String bscsCleanupUrl;
	
	@Value("${consumer.service.bscs.getProfile}")
	private String bscsProfileUrl;
	
	@Value("${consumer.service.bscs.getProfileForValidation}")
	private String bscsProfileValidationUrl;
	
	@Value("${consumer.service.erp.cleanup}")
	private String erpCleanupUrl;
	
	@Value("${consumer.service.ras.cleanup}")
	private String rasCleanupUrl;
	
	@Value("${consumer.service.ras.disconnection}")
	private String rasDisconnectionUrl;
	
	@Value("${consumer.service.nms.cleanup}")
	private String nmsCleanupUrl;
	
	@Value("${consumer.service.bscs.getDbConfig}")
	private String bscsDbConfigUrl;
	
	@Value("${consumer.service.erp.getDbConfig}")
	private String erpDbConfigUrl;
	
	@Value("${consumer.service.nms.getDbConfig}")
	private String nmsDbConfigUrl;
	
	@Value("${consumer.service.ras.getDbConfig}")
	private String rasDbConfigUrl;
	
	@Autowired
	private ReloadableResourceBundleMessageSource externalConfig;

	public ExchangeService getExchangeService() {
		return exchangeService;
	}

	public void setExchangeService(ExchangeService exchangeService) {
		this.exchangeService = exchangeService;
	}

	public String getBscsCleanupUrl() {
		return bscsCleanupUrl;
	}

	public void setBscsCleanupUrl(String bscsCleanupUrl) {
		this.bscsCleanupUrl = bscsCleanupUrl;
	}

	public String getErpCleanupUrl() {
		return erpCleanupUrl;
	}

	public void setErpCleanupUrl(String erpCleanupUrl) {
		this.erpCleanupUrl = erpCleanupUrl;
	}

	public String getRasCleanupUrl() {
		return rasCleanupUrl;
	}

	public void setRasCleanupUrl(String rasCleanupUrl) {
		this.rasCleanupUrl = rasCleanupUrl;
	}

	public String getNmsCleanupUrl() {
		return nmsCleanupUrl;
	}

	public void setNmsCleanupUrl(String nmsCleanupUrl) {
		this.nmsCleanupUrl = nmsCleanupUrl;
	}

	public ReloadableResourceBundleMessageSource getExternalConfig() {
		return externalConfig;
	}

	public void setExternalConfig(ReloadableResourceBundleMessageSource externalConfig) {
		this.externalConfig = externalConfig;
	}

	public ExchangeInternalService getExchangeInternalService() {
		return exchangeInternalService;
	}

	public void setExchangeInternalService(ExchangeInternalService exchangeInternalService) {
		this.exchangeInternalService = exchangeInternalService;
	}

	public String getBscsProfileUrl() {
		return bscsProfileUrl;
	}

	public void setBscsProfileUrl(String bscsProfileUrl) {
		this.bscsProfileUrl = bscsProfileUrl;
	}

	public String getBscsDbConfigUrl() {
		return bscsDbConfigUrl;
	}

	public void setBscsDbConfigUrl(String bscsDbConfigUrl) {
		this.bscsDbConfigUrl = bscsDbConfigUrl;
	}

	public String getErpDbConfigUrl() {
		return erpDbConfigUrl;
	}

	public void setErpDbConfigUrl(String erpDbConfigUrl) {
		this.erpDbConfigUrl = erpDbConfigUrl;
	}

	public String getNmsDbConfigUrl() {
		return nmsDbConfigUrl;
	}

	public void setNmsDbConfigUrl(String nmsDbConfigUrl) {
		this.nmsDbConfigUrl = nmsDbConfigUrl;
	}

	public String getRasDbConfigUrl() {
		return rasDbConfigUrl;
	}

	public void setRasDbConfigUrl(String rasDbConfigUrl) {
		this.rasDbConfigUrl = rasDbConfigUrl;
	}

	public String getRasDisconnectionUrl() {
		return rasDisconnectionUrl;
	}

	public void setRasDisconnectionUrl(String rasDisconnectionUrl) {
		this.rasDisconnectionUrl = rasDisconnectionUrl;
	}

	public String getBscsProfileValidationUrl() {
		return bscsProfileValidationUrl;
	}

	public void setBscsProfileValidationUrl(String bscsProfileValidationUrl) {
		this.bscsProfileValidationUrl = bscsProfileValidationUrl;
	}
}
