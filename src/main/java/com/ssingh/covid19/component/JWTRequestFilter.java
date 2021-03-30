package com.ssingh.covid19.component;

import io.jsonwebtoken.ExpiredJwtException;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssingh.covid19.constants.ApplicationConstants;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JWTHelper jwtHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		final String requestJWTTokenHeader = request.getHeader("Authorization");
		logger.debug("Request Token Header : " + requestJWTTokenHeader);

		String username = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (StringUtils.isNotBlank(requestJWTTokenHeader)
				&& requestJWTTokenHeader
						.startsWith(ApplicationConstants.JWT_TOKEN_PREFIX)) {
			jwtToken = StringUtils.substring(requestJWTTokenHeader,
					StringUtils.length(ApplicationConstants.JWT_TOKEN_PREFIX));
			try {
				username = jwtHelper.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				logger.debug("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				logger.error("JWT Token expired.");
			}
		} else {
			logger.warn("JWT Token either not present or malformed.");
		}

		// Once we get the token validate it.
		if (StringUtils.isNotBlank(username)) {
			UserDetails userDetails = userDetailsService
					.loadUserByUsername(username);

			// if token is valid configure Spring Security to manually set
			// authentication
			if (jwtHelper.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource()
								.buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(
						usernamePasswordAuthenticationToken);
			}
		}
		chain.doFilter(request, response);
	}
}
