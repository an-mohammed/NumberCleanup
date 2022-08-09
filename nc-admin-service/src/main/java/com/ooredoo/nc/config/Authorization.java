package com.ooredoo.nc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ooredoo.nc.utility.ApplicationConstants;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class Authorization extends WebSecurityConfigurerAdapter implements ApplicationConstants {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncryptor;
	
	@Autowired
	private AuthUserDetailsService userDetailsService;
	
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.httpBasic().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    	.and().authorizeRequests().antMatchers("/actuator/*").permitAll()
    	.and().authorizeRequests().antMatchers("/actuator/jolokia/*").permitAll()
    	.and().authorizeRequests().antMatchers("/nc/user/*").permitAll()
		.and().csrf().disable();
    }
    
    @Override	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.authenticationProvider(authenticationProvider());
	}
    
    @Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService);
	    authProvider.setPasswordEncoder(passwordEncryptor);
	    return authProvider;
	}
}
