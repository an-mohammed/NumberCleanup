package com.ooredoo.nc.config;

import java.io.Serializable;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.ldap.LdapContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ooredoo.nc.exception.ApplicationException;

@Component
public class LdapAuthnticationContext implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Value("${auth.ldap.controlFactories}")
	private String controlFactories;
	
	@Value("${auth.ldap.initalContextFactories}")
	private String initalContextFactories;
	
	@Value("${auth.ldap.providerUrl}")
	private String providerUrl;
	
	@Value("${auth.ldap.securityAuthentication}")
	private String securityAuthentication;
	
	@Value("${auth.ldap.securityPrincipal}")
	private String securityPrincipal;
	
	@Value("${auth.ldap.bypass}")
	private String bypassLdapAuthForTesting;
	
	private String userName;
	
	private String password;
	
	public Hashtable<String, String> getInitialLdapContext(String username, String password) throws Exception {
		this.userName = username;
		this.password = password;
		
		Hashtable<String, String> env = new Hashtable<String, String>();
		
		env.put(LdapContext.CONTROL_FACTORIES, this.controlFactories);
		env.put(Context.INITIAL_CONTEXT_FACTORY, this.initalContextFactories);
		env.put(Context.PROVIDER_URL, this.providerUrl);
		env.put(Context.SECURITY_AUTHENTICATION, this.securityAuthentication);
		env.put(Context.SECURITY_PRINCIPAL, getSecurityPrincipal());
		env.put(Context.SECURITY_CREDENTIALS, password);
		
		return env;
}

	private String getSecurityPrincipal() throws ApplicationException {
		
		if(null != this.securityPrincipal && !this.securityPrincipal.trim().isEmpty()) {
			return this.securityPrincipal.trim() + "\\" + userName;
		} else {
			throw new ApplicationException("Invalid Security Principle Defined for LDAP Authentication");
		}
	}

	public void setSecurityPrincipal(String securityPrincipal) {
		this.securityPrincipal = securityPrincipal;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBypassLdapAuthForTesting() {
		return bypassLdapAuthForTesting;
	}

	public void setBypassLdapAuthForTesting(String bypassLdapAuthForTesting) {
		this.bypassLdapAuthForTesting = bypassLdapAuthForTesting;
	}

}
