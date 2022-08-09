package com.ooredoo.nc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.utility.ApplicationConstants;
import com.ooredoo.nc.utility.ApplicationContextHolder;
import com.ooredoo.nc.utility.ApplicationUtility;

@SpringBootApplication
@EnableAutoConfiguration
@EnableTransactionManagement
@ComponentScan({"com.ooredoo.nc"})
@ImportResource({"classpath:**/application_context*.xml"})

public class NcRasService implements ApplicationConstants {
	
	public static ConfigurableApplicationContext context = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(NcRasService.class);

	public static void main(String[] args) {
		try {
			initApplicationFramework(args);
			
		} catch(ApplicationException e) {
			
			LOGGER.error("Error occurred while starting-up ras-cleanup-service. Cause-", e);
			ApplicationUtility.closeApplicationContext(context);
		}
	}
	
	private static void initApplicationFramework(String[] args) throws ApplicationException {
		try {
			context = SpringApplication.run(NcRasService.class, args);
			context.registerShutdownHook();
			ApplicationContextHolder.getInstance().setApplicationContext(RAS, context);
		} catch(Exception e ) {
			throw new ApplicationException(e);
		}
	}
}
