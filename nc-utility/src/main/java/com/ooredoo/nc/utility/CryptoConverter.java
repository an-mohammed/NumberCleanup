package com.ooredoo.nc.utility;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;

import org.bouncycastle.util.encoders.Base64;


public class CryptoConverter implements AttributeConverter<String, String> {

	 private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
	 private static final byte[] KEY = "Oord003nCr!pt10n".getBytes();
	 
	@Override
	public String convertToDatabaseColumn(String attributeValue) {
		
		if(null != attributeValue && !attributeValue.isEmpty()) {
			Key key = new SecretKeySpec(KEY, "AES");
		      try {
		         Cipher c = Cipher.getInstance(ALGORITHM);
		         c.init(Cipher.ENCRYPT_MODE, key);
		         
		         return new String(Base64.encode(c.doFinal(attributeValue.getBytes())));
		      } catch (Exception e) {
		         throw new RuntimeException(e);
		      }
		} else {
			return null;
		}
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		
		if(null != dbData && !dbData.isEmpty()) {
		  Key key = new SecretKeySpec(KEY, "AES");
			
	      try {
	        Cipher c = Cipher.getInstance(ALGORITHM);
	        c.init(Cipher.DECRYPT_MODE, key);
	        return new String(c.doFinal(Base64.decode(dbData)));
	        
	      } catch (Exception e) {
	        throw new RuntimeException(e);
	      }
		} else {
			return null;
		}
	 }
	
	public static String getDecryptedValue(String encryptedValue) {
		CryptoConverter cc = new CryptoConverter();
		encryptedValue = encryptedValue.replace("ENC(", "");
		encryptedValue = encryptedValue.replace(Character.toString(encryptedValue.charAt(encryptedValue.length() - 1)), "");
		
		String decryptedValue = cc.convertToEntityAttribute(encryptedValue);
		return decryptedValue;
	}
	
	public static String getEncryptedValue(String rawValue) {
		CryptoConverter cc = new CryptoConverter();
		
		String encryptedValue = cc.convertToDatabaseColumn(rawValue);
		return encryptedValue;
	}
}
