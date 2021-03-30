package com.ssingh.covid19.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ssingh.covid19.component.JWTRequestFilter;
import com.ssingh.covid19.constants.ApplicationConstants;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SecurityConfig.class);

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private JWTRequestFilter requestFilter;

	/**
	 * Will be injected in Security AuthenticationManagerBuilder automatically,
	 * so no need to explicit set passwordEncoder(...) for AuthenticationManager.
	 * 
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new Argon2PasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// Don't need CSRF
		httpSecurity.csrf().disable()
		// Authorize Requests
				.authorizeRequests()
				// Theses URLs are open
				.antMatchers(ApplicationConstants.AUTH_WHITELIST).permitAll()
				// Authenticate all other URLs.
				.anyRequest().authenticated().and()
		         .httpBasic();		
		// Disable Session Creation
		httpSecurity.sessionManagement().sessionCreationPolicy(
				SessionCreationPolicy.STATELESS);
		// Handles Unauthenticated Requests
		httpSecurity.exceptionHandling().authenticationEntryPoint(
				authenticationEntryPoint);
		// Add filter to validate the tokens with every request
		httpSecurity.addFilterBefore(requestFilter,
				UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {

		/*
		 * String encodedPassword = passwordEncoder().encode("password");
		 * 
		 * LOGGER.info("Encoded Password : {}", encodedPassword);
		 * auth.inMemoryAuthentication()// //
		 * .passwordEncoder(NoOpPasswordEncoder.getInstance())
		 * .withUser("admin").password(encodedPassword).roles("USER");
		 */

		auth.userDetailsService(userDetailsService);
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
