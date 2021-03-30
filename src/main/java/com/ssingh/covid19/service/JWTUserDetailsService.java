package com.ssingh.covid19.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JWTUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if ("javainuse".equals(username)) {
			return new User("javainuse", "$argon2id$v=19$m=4096,t=3,p=1$KapRc3IaujL57ws/QjqmKw$jJBsNWfqMWJXhHKvxrBQONLMD0rXRuMkGuMFJQzAOL0",
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("No user found with username: " + username);
		}
	}
}
