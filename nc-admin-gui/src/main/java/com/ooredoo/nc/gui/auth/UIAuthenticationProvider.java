package com.ooredoo.nc.gui.auth;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.ooredoo.nc.gui.managedbean.ManagedBean;
import com.ooredoo.nc.gui.service.UserService;

@Primary
@Component
public class UIAuthenticationProvider extends ManagedBean implements AuthenticationProvider {

	@Inject
	@Named("userSerivce")
	private UserService userService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        return userService.authenticate(username, password);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

	public UserService getHandler() {
		return userService;
	}

	public void setHandler(UserService userService) {
		this.userService = userService;
	}
}
