package com.ooredoo.dd.discovery;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;

@EnableEurekaServer
@SpringBootApplication
public class DiscoveryApplication {
	private static ConfigurableApplicationContext context;
	private static final Logger LOGGER = LoggerFactory.getLogger(DiscoveryApplication.class);

	public static void main(String[] args) {
		try {
			context = SpringApplication.run(DiscoveryApplication.class, args);
			context.registerShutdownHook();
			
			LOGGER.info("Dashboard application is successfully started");
		} catch(Exception e) {
			LOGGER.error("Unable to start Dashboard. Cause :", e);
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
