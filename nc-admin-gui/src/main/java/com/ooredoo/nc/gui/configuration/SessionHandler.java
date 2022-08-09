package com.ooredoo.nc.gui.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import com.ooredoo.nc.model.AppUser;
import com.ooredoo.nc.model.Privilege;
import com.ooredoo.nc.model.Role;
import com.ooredoo.nc.utility.ApplicationConstants;

public class SessionHandler implements ApplicationConstants {

	public static void putToSession(Object object, String sessionAttributeName) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(sessionAttributeName, object);
	}
	
	public static void removeFromSession(String sessionAttributeName) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(sessionAttributeName);
	}
	
	public static Object getFromSession(String sessionAttributeName) {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(sessionAttributeName);
	}
	
	public static String getSessionId() {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
	}
	
	public static AppUser getLoggerInUserInfo() {
		AppUser u = (AppUser) getFromSession(S_CREDENTIAL);
		return u;
	}
	
	public static void putUserCredentialToSession(AppUser u) {
		putToSession(u, S_CREDENTIAL);
	}
	
	public static String getTransactionId() {
		try {
			String u = (String) getFromSession(S_TRANSACTION_ID);
			if (u == null) {
				return "";
			}
			return u;
		} catch (Exception e) {
			return "";
		}
	}
	public static void putTransactionToSession(String u) {
		putToSession(u, S_TRANSACTION_ID);
	}
	
	public static void putRoleAuthToSession(AppUser u) {
		Map<String, List<String>> userAuth = new HashMap<String, List<String>>();
		List<String> privList = null;
		
		for(Role r : u.getRoles()) {
			if(null != r.getPrivileges()) {				
				privList = new ArrayList<String>();
				
				for(Privilege p : r.getPrivileges()) {
					privList.add(p.getPName());
				}
			}
			
			userAuth.put(r.getName(), privList);
		}
		
		putToSession(userAuth, S_ROLE_PRIV);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, List<String>> getRoleAuthFromSession() {
		Map<String, List<String>> auth = (Map<String, List<String>>) getFromSession(S_ROLE_PRIV);
		return auth;
	}
}
