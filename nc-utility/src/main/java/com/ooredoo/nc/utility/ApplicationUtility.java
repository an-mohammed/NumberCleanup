/**
 * 
 */
package com.ooredoo.nc.utility;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author S.Chatterjee
 *
 */
public class ApplicationUtility implements ApplicationConstants {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationUtility.class);
	
	 public static String getMessage(String key, Object[] args, Locale locale) {
		if(null != key && !key.isEmpty()) {
			String[] arrs = key.split("\\.");
			
			if(null != arrs && arrs.length ==2 & null != arrs[0] && !arrs[0].isEmpty()) {
				LOGGER.debug("Extracting context for :{}", arrs[0]);
				
				if(null != ApplicationContextHolder.getInstance().getContext(arrs[0])) {
					return ApplicationContextHolder.getInstance().getContext(arrs[0]).getMessage(key, args, locale);
				} else {
					return GENERIC_ERROR_MESSAGE;
				}
				
			} else {
				return GENERIC_ERROR_MESSAGE;
			}
		} else {
			return GENERIC_ERROR_MESSAGE;
		} 
	 }
	 
	 public static String getMessage(String key, Object[] args) {
		if(null != key && !key.isEmpty()) {
			String[] arrs = key.split("\\.");
			
			if(null != arrs && arrs.length ==2 & null != arrs[0] && !arrs[0].isEmpty()) {
				LOGGER.info("Extracting context for :{}", arrs[0]);
				
				if(null != ApplicationContextHolder.getInstance().getContext(arrs[0])) {
					return ApplicationContextHolder.getInstance().getContext(arrs[0]).getMessage(key, args, null);
				} else {
					return GENERIC_ERROR_MESSAGE;
				}
				
			} else {
				return GENERIC_ERROR_MESSAGE;
			}
		} else {
			return GENERIC_ERROR_MESSAGE;
		} 
	 }
	
	public static void closeApplicationContext(ConfigurableApplicationContext context) {
		if(null != context) {
			LOGGER.info("Closing Application context");
			context.close();
			context.stop();
			context = null;
			LOGGER.info("Application context successfully closed");
		}
	}
	
	public static String getFormattedDate(String dateFormat, Date date) {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		format.setLenient(false);
		return format.format(date);
	}
	
	public static String getFormattedDecimal(Double amount) {
		DecimalFormat fd = new DecimalFormat();
		fd.setMaximumFractionDigits(0);
		fd.setDecimalSeparatorAlwaysShown(false);
		fd.setGroupingUsed(false);
		
		return fd.format(amount);
	}
	
	
	public static String getFormattedDecimalWithFraction(Double amount) {
		DecimalFormat fd = new DecimalFormat();
		fd.setMaximumFractionDigits(3);
		fd.setMinimumFractionDigits(3);
		fd.setDecimalSeparatorAlwaysShown(false);
		fd.setGroupingUsed(false);
		return fd.format(amount);
	}
	
	public static Map<String, Object> getErrorObjectFromErrorResponse(String errorResponseFromRest) {
		Gson gson = new Gson();
		Map<String, Object> map = gson.fromJson(errorResponseFromRest, new TypeToken<Map<String, Object>>(){}.getType());
		return map;
	}	
	
	/**
	   * @param number The number in plain format
	   * @param mask The  mask pattern. 
	   *    Use # to include the digit from the position. 
	   *    Use x to mask the digit at that position.
	   *    Any other char will be inserted.
	   *
	   * @return The masked card number
	   */
	public static String maskNumber(String number, String mask) {
	   
      int index = 0;
      StringBuilder masked = new StringBuilder();
      for (int i = 0; i < mask.length(); i++) {
         char c = mask.charAt(i);
         if (c == '#') {
            masked.append(number.charAt(index));
            index++;
         } else if (c == 'X') {
            masked.append(c);
            index++;
         } else {
            masked.append(c);
         }
      }
      return masked.toString();
   }
	
	
	public static final String formatActionAuditLogMessage(String rawM, Object[] args) {
		String msg = null;
		
		if(null != rawM) {
			msg = rawM;
			
			if(null != args && args.length > 0) {
				for(int i = 0; i < args.length; i++) {
					msg = msg.replace("{" + i + "}", String.valueOf(args[i]));
				}
			}
		}
		
		return msg;
	}
	
	public static XMLGregorianCalendar getXmlgregCal(Date d) throws Exception {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(d);
		XMLGregorianCalendar convertedDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		return convertedDate;
	}
	
	public static String genericMasking(String value) {
		int length = StringUtils.length(value);
		
		if(length > 4) {
			String maskedPart = StringUtils.substring(value, 0, length - 4);
			
			StringBuffer buffer = new StringBuffer();
			
			for(int i = 0; i < maskedPart.length(); i++) {
				buffer.append("X");
			}
			
			buffer.append(value.substring(length - 4));
			
			return buffer.toString();
			
		} else if(length <= 4 && length > 2) {
			
			String maskedPart = StringUtils.substring(value, 0, length - 2);
			
			StringBuffer buffer = new StringBuffer();
			
			for(int i = 0; i < maskedPart.length(); i++) {
				buffer.append("X");
			}
			
			buffer.append(value.substring(length - 2));
			
			return buffer.toString();
		} else {
			return value;
		}
	}
	
	public static String getLastFourDigit(String value) {
		String lastFourDigit = null;
		
		if(null != value && !value.isEmpty()) {
			int length = StringUtils.length(value);
			
			if(length > 4) {
				lastFourDigit = value.substring(length - 4);
				
			} else if(length <= 4 && length > 2) {
				lastFourDigit = value.substring(length - 2);
				
			} else {
				return value;
			}
		}
		
		return lastFourDigit;
	}
	
	public static void main(String[] args) {
		String accountNo = "365";
		System.out.println(genericMasking(accountNo));
	}
	
	public static final String generateRandomTransactionId() {
		int length = 9;
	    boolean useLetters = false;
	    boolean useNumbers = true;
		return RandomStringUtils.random(length, useLetters, useNumbers);
	}
	
	 public static String generateBasicAutenticationHeader(String userName, String userPassword) {
		    byte[] encodedBytes = Base64.getEncoder().encode((userName + ":" + userPassword).getBytes());
		    return "Basic " + new String(encodedBytes);
		  }
}
