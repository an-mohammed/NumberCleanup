package com.ooredoo.nc.gui.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UIAuthenticationProvider authenticationProvider;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
		
		//http.requiresChannel().anyRequest().requiresSecure();
		
		http.authorizeRequests().antMatchers("/javax.faces.resource/**", "/images/**", "/actuator/**", "/fonts/**", "/portal/resetPassword.xhtml").permitAll().anyRequest().authenticated();
	    
	    http.formLogin().loginPage("/login.xhtml").loginProcessingUrl("/perform-login").defaultSuccessUrl("/portal/home.xhtml", true)
	        .failureUrl("/login.xhtml?error=true").successForwardUrl("/portal/home.xhtml").permitAll()
	        .and().sessionManagement().invalidSessionUrl("/login.xhtml")
	        .maximumSessions(1)
	        .maxSessionsPreventsLogin(true).sessionRegistry(sessionRegistry());
	    
	    http.logout().deleteCookies("JSESSIONID").invalidateHttpSession(true).logoutSuccessUrl("/login.xhtml");
	    
	    http.headers().cacheControl().disable();
	    http.csrf().disable();
	    
    }
	
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
	    return new HttpSessionEventPublisher();
	}
	
	@Bean
	public SessionRegistry sessionRegistry() {
	    SessionRegistry sessionRegistry = new SessionRegistryImpl();
	    return sessionRegistry;
	}
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	  }
	
	public UIAuthenticationProvider getAuthenticationProvider() {
		return authenticationProvider;
	}

	public void setAuthenticationProvider(UIAuthenticationProvider authenticationProvider) {
		this.authenticationProvider = authenticationProvider;
	}

}
