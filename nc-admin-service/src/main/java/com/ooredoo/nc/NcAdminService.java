package com.ooredoo.nc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ooredoo.nc.config.RibbonConfiguration;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.utility.ApplicationContextHolder;
import com.ooredoo.nc.utility.ApplicationUtility;

@SpringBootApplication
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableScheduling
@ComponentScan({"com.ooredoo.nc", "com.ooredoo.wsclient"})
@EntityScan(basePackages={"com.ooredoo.nc.model"})
@EnableJpaRepositories(enableDefaultTransactions=true, basePackages= {"com.ooredoo.nc.repo"})
@ImportResource({"classpath:**/application_context*.xml"})
@RibbonClient(name = "NC-SERVICE-CLIENTS", configuration = RibbonConfiguration.class)
public class NcAdminService {
	
	public static ConfigurableApplicationContext context = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(NcAdminService.class);

	public static void main(String[] args) {
		try {
			initApplicationFramework(args);
			
		} catch(ApplicationException e) {
			
			LOGGER.error("Error occurred while starting-up number-cleanup-service. Cause-", e);
			ApplicationUtility.closeApplicationContext(context);
		}
	}
	
	private static void initApplicationFramework(String[] args) throws ApplicationException {
		try {
			context = SpringApplication.run(NcAdminService.class, args);
			context.registerShutdownHook();
			ApplicationContextHolder.getInstance().setApplicationContext("CAS", context);
		} catch(Exception e ) {
			throw new ApplicationException(e);
		}
	}
}
