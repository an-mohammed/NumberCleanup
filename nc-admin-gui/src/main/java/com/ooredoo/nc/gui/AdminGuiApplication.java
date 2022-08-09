package com.ooredoo.nc.gui;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.context.ServletContextAware;

import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.gui.configuration.RibbonConfiguration;
import com.ooredoo.nc.utility.ApplicationContextHolder;
import com.ooredoo.nc.utility.ApplicationUtility;

@SpringBootApplication
@EnableAutoConfiguration
@ServletComponentScan
@ComponentScan(basePackages="com.ooredoo.nc.gui")
@ImportResource({"classpath:**/application_context*.xml"})
@RibbonClient(name = "NC-SERVICE", configuration = RibbonConfiguration.class)
@EnableDiscoveryClient

/**
 * This Class is responsible for instantiating and start up the Number Cleanup Admin GUI container along with java server faces
 * 
 * @author S.Chatterjee
 *
 */
public class AdminGuiApplication  implements ServletContextInitializer, ServletContextAware {
	public static ConfigurableApplicationContext context = null;
	private static final Logger logger = LoggerFactory.getLogger(AdminGuiApplication.class);

	public static void main(String[] args) {
		try {
			initApplicationFramework(args);
		} catch(ApplicationException e) {
			logger.error("Error Occurred while starting-up direct-debit admin GUI. Cause-", e);
			ApplicationUtility.closeApplicationContext(context);
		}
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());			
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		servletContext.setInitParameter("com.sun.faces.enableRestoreView11Compatibility", "true");
		servletContext.setInitParameter("javax.faces.FACELETS_LIBRARIES", "/resources/springsecurity.taglib.xml");
	}
	
	private static void initApplicationFramework(String[] args) throws ApplicationException {
		try {
			context = SpringApplication.run(AdminGuiApplication.class, args);
			context.registerShutdownHook();
			ApplicationContextHolder.getInstance().setApplicationContext("AUI", context);
		} catch(Exception e ) {
			throw new ApplicationException(e);
		}
	}

}
