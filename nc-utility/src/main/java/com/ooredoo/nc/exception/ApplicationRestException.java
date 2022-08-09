package com.ooredoo.nc.exception;

import java.util.Arrays;

import org.springframework.stereotype.Component;

@Component
public class ApplicationRestException extends ApplicationException {

	private static final long serialVersionUID = 1L;
	private int httpErrorCode = 500;
	
	public ApplicationRestException() {
	}
	
	public ApplicationRestException(int errorCode, String errorMessage) {
		super(errorMessage, true);
		httpErrorCode = errorCode;
	}
	
	public ApplicationRestException(int errorCode, String errorMessage, Throwable t) {
		super(errorMessage, t, true);
		httpErrorCode = errorCode;
	}
	
	public ApplicationRestException(String errorCode, Throwable t) {
		super(errorCode, t);
	}
	
	public ApplicationRestException(Throwable t) {
		super(t);
		httpErrorCode = 500;
	}
	
	
	@Override
	public String toString() {
		StringBuilder errorString = new StringBuilder();
		 if (getErrorCode() != null) {
			 errorString.append("\nError code: ");
			 errorString.append(getHttpErrorCode());
		 }
		 
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

	public int getHttpErrorCode() {
		return httpErrorCode;
	}
}
