package com.ooredoo.nc.config;

import java.util.Collections;
import java.util.Locale;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.ooredoo.nc.utility.ExchangeInternalService;
import com.ooredoo.nc.utility.ExchangeService;
import com.ooredoo.nc.utility.RequestResponseLoggingInterceptor;

@Configuration
public class ApplicationConfiguration {

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	    messageSource.setBasename("classpath:messages");
	    return messageSource;
	}
	
	@Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.ENGLISH);
        return slr;
    }
	
    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
    	BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
    	return pwdEncoder;
    }
    
    @Bean("exchangeService")
    public ExchangeService exchangeService() {
        ExchangeService exchangeService = new ExchangeService();
        return exchangeService;
    }
    
    @Bean("exchangeInternalService")
    public ExchangeInternalService exchangeInternalService() {
        ExchangeInternalService exchangeInternalService = new ExchangeInternalService();
        return exchangeInternalService;
    }
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new HttpComponentsClientHttpRequestFactory());
		RestTemplate restTemplate = new RestTemplate(factory);
		restTemplate.setInterceptors(Collections.singletonList(new RequestResponseLoggingInterceptor()));
		return restTemplate;
	}
}
