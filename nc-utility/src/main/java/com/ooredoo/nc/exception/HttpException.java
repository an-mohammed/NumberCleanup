package com.ooredoo.nc.exception;

import java.util.Arrays;

import org.springframework.stereotype.Component;

@Component
public class HttpException extends Exception {

	private static final long serialVersionUID = 1L;
	private final int errorCode;
	private final String errorMessage;
	
	public HttpException() {
		errorCode = 0;
		errorMessage = null;
	}
	
	public HttpException(int errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public HttpException(int errorCode, String errorMessage, Throwable t) {
		super(errorMessage, t);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	
	@Override
	public String toString() {
		StringBuilder errorString = new StringBuilder();
			 errorString.append("\nError code: ");
			 errorString.append(getErrorCode());
		 
		 if(getErrorMessage() != null) {
			 errorString.append("\nError message: ");
			 errorString.append(getErrorMessage());
		 }
	      
		 errorString.append("\nStack trace:\n");
	     StackTraceElement[] stackTrace = getStackTrace();
	      if (stackTrace != null) {
	    	  errorString.append(Arrays.asList(stackTrace));
	      }
		
		return errorString.toString();
	}



	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
