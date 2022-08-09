package com.ooredoo.nc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.ooredoo.nc.utility.ApplicationConstants;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class Authorization extends WebSecurityConfigurerAdapter implements ApplicationConstants {
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.httpBasic()
    	.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    	.and().authorizeRequests().antMatchers("/actuator/*").permitAll()
    	.and().authorizeRequests().antMatchers("/actuator/jolokia/*").permitAll()
		.and().csrf().disable();
    }
}
