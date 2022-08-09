package com.ooredoo.nc.gui.configuration;

import java.util.Collections;
import java.util.Locale;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.ooredoo.nc.utility.ExchangeInternalService;
import com.ooredoo.nc.utility.ExchangeService;
import com.ooredoo.nc.utility.RequestResponseLoggingInterceptor;

@Configuration
public class UiConfig implements WebMvcConfigurer {
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		 registry.addViewController("/").setViewName("forward:/index.xhtml");
	     registry.setOrder( Ordered.HIGHEST_PRECEDENCE );
		WebMvcConfigurer.super.addViewControllers(registry);
	}
	
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
