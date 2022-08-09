package com.ooredoo.nc.exception;

import java.util.Arrays;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.ooredoo.nc.utility.ApplicationUtility;

@Component
public class ApplicationException extends Exception {

	private static final long serialVersionUID = 1L;
	private final String errorCode;
	private final String errorMessage;
	
	public ApplicationException() {
		errorCode = null;
		errorMessage = null;
	}
	
	public ApplicationException(String errorCode, Object[] exceptionArument, Locale locale, Throwable th) {
		super(ApplicationUtility.getMessage(errorCode, exceptionArument, locale), th, true, true);
		this.errorCode = errorCode;
		this.errorMessage = ApplicationUtility.getMessage(errorCode, exceptionArument, locale);
		
	}
	
	public ApplicationException(String errorCode, Object[] exceptionArgument, Throwable th) {
		super(ApplicationUtility.getMessage(errorCode, exceptionArgument, Locale.US), th, true, true);
		this.errorCode = errorCode;
		this.errorMessage = ApplicationUtility.getMessage(errorCode, exceptionArgument, Locale.US);
	}
	
	public ApplicationException(String errorCode, Object[] exceptionArgument) {
		super(ApplicationUtility.getMessage(errorCode, exceptionArgument, Locale.US), null, true, true);
		this.errorCode = errorCode;
		this.errorMessage = ApplicationUtility.getMessage(errorCode, exceptionArgument, Locale.US);
	}
	
	public ApplicationException(String errorCode, Throwable th) {
		super(ApplicationUtility.getMessage(errorCode, new Object[] {}, Locale.US), th, true, true);
		this.errorCode = errorCode;
		this.errorMessage = ApplicationUtility.getMessage(errorCode, new Object[] {}, Locale.US);
	}
	
	public ApplicationException(String errorCode) {
		super(ApplicationUtility.getMessage(errorCode, new Object[] {}, Locale.US), null, true, true);
		this.errorCode = errorCode;		
		this.errorMessage = ApplicationUtility.getMessage(errorCode, new Object[] {}, Locale.US);
	}
	
	public ApplicationException(String errorMsg, boolean isMsg) {
		super(errorMsg);
		this.errorCode = null;		
		this.errorMessage = errorMsg;
	}
	
	public ApplicationException(String errorMsg, Throwable th,  boolean isMsg) {
		super(errorMsg, th);
		this.errorCode = null;		
		this.errorMessage = errorMsg;
	}
	
	public ApplicationException(Throwable th) {
		super(th);
		this.errorMessage = th.getLocalizedMessage();
		this.errorCode = th.getMessage();
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
	@Override
	public String toString() {
		StringBuilder errorString = new StringBuilder();
		 if (getErrorCode() != null) {
			 errorString.append("\nError code: ");
			 errorString.append(getErrorCode());
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
}
