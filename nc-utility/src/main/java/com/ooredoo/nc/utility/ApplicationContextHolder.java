package com.ooredoo.nc.utility;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.ConfigurableApplicationContext;

public class ApplicationContextHolder {
	
	private static ApplicationContextHolder instance;
	private static ConcurrentHashMap<String, ConfigurableApplicationContext> appContext;

	public static ApplicationContextHolder getInstance(){
	        if(instance == null){
	            instance = new ApplicationContextHolder();
	        }
	        
	        if(null == appContext) {
	        	appContext = new ConcurrentHashMap<String, ConfigurableApplicationContext>();
	        }
	        return instance;
	 }
	
	public void setApplicationContext(String contextIdentifier, ConfigurableApplicationContext context) {
		appContext.put(contextIdentifier, context);
	}
	
	public ConfigurableApplicationContext getContext(String module) {
		return appContext.get(module);
	}
}
