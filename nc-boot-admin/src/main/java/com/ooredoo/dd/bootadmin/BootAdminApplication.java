package com.ooredoo.dd.bootadmin;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import de.codecentric.boot.admin.server.config.EnableAdminServer;

@EnableWebSecurity
@EnableAdminServer
@SpringBootApplication
public class BootAdminApplication {
	
	private static ConfigurableApplicationContext context;
	private static final Logger LOGGER = LoggerFactory.getLogger(BootAdminApplication.class);

	public static void main(String[] args) {
		try {
			context = SpringApplication.run(BootAdminApplication.class, args);
			context.registerShutdownHook();
			
			LOGGER.info("Dashboard application is successfully started");
		} catch(Exception e) {
			LOGGER.error("Unable to start Dashboard. Cause :", e);
		}
		
	}
	
	@Configuration
	public static class SecuritySecureConfig extends WebSecurityConfigurerAdapter {
	    private final String adminContextPath;

	    public SecuritySecureConfig(AdminServerProperties adminServerProperties) {
	        this.adminContextPath = adminServerProperties.getContextPath();
	    }

	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
	        successHandler.setTargetUrlParameter("redirectTo");
	        successHandler.setDefaultTargetUrl(adminContextPath + "/");

	        http.authorizeRequests()
	            .antMatchers(adminContextPath + "/assets/**").permitAll() 
	            .antMatchers(adminContextPath + "/login").permitAll()
	            .antMatchers(adminContextPath + "/instances").permitAll()
	            .antMatchers(adminContextPath + "/actuator/**").permitAll()
	            .anyRequest().authenticated() 
	            .and()
	        .formLogin().loginPage(adminContextPath + "/login").successHandler(successHandler).and() 
	        .logout().logoutUrl(adminContextPath + "/logout").and()
	        .httpBasic().and() 
	        .csrf()
	            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())  
	            .ignoringAntMatchers(
	                adminContextPath + "/instances",   
	                adminContextPath + "/actuator/**"  
	            );
	    }
	}
	
	@PreDestroy
    private void shutDown() {
    	if(null != context && (context.isActive() || context.isRunning() )) {
    		context.stop();
    		context.close();
    		LOGGER.info("Application Shutdown complete");
    	}
    }
}
