package com.ooredoo.nc.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ooredoo.nc.model.AppUser;
import com.ooredoo.nc.model.Role;
import com.ooredoo.nc.repo.CompositeRepository;



@Service
public class AuthUserDetailsService implements UserDetailsService {
	
	@Autowired
	CompositeRepository repo;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthUserDetailsService.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserDetails userDetails = null;
		LOGGER.info("Authenticating for user : {}", username);
		Optional<AppUser> userInformation = repo.getUserRepo().findByUName(username);
		
		if(userInformation.isPresent()) {
			AppUser u = userInformation.get();
			LOGGER.info("User password : {}", u.getUPwd());
			LOGGER.info("User password : {}", u.getuPwd());
			
			userDetails = new UserDetails() {
				
				private static final long serialVersionUID = 1L;

				@Override
				public boolean isEnabled() {
					return u.getIsEnabled();
				}
				
				@Override
				public boolean isCredentialsNonExpired() {
					return true;
				}
				
				@Override
				public boolean isAccountNonLocked() {
					return true;
				}
				
				@Override
				public boolean isAccountNonExpired() {
					return true;
				}
				
				@Override
				public String getUsername() {
					return u.getUName();
				}
				
				@Override
				public String getPassword() {
					return u.getUPwd();
				}
				
				@Override
				public Collection<? extends GrantedAuthority> getAuthorities() {
					
					List<GrantedAuthority> authorities = new ArrayList<>();
					GrantedAuthority authority = null;
					
					for(Role role : u.getRoles()) {
						authority = new SimpleGrantedAuthority(role.getName());
						authorities.add(authority);
					}
					return authorities;
				}
			};
		} else {
			throw new UsernameNotFoundException("No User found with username :" + username);
		}
		
		LOGGER.info("User authticated with username : {}", username);
		return userDetails;
	}

}
