package com.ssingh.covid19.constants;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public interface ApplicationConstants {
	String APP_DATE_FORMAT = "dd-MM-YY HH:mm:ss O";
	DateTimeFormatter APP_DATE_FOMATTER = DateTimeFormatter.ofPattern(
			"yyyy-MM-dd HH:mm:ss O")
			.withZone(ZoneId.of(ZoneOffset.UTC.getId()));

	String[] AUTH_WHITELIST = {
			// Swagger UI v2
			"/v2/api-docs", "/swagger-resources", "/swagger-resources/**",
			"/configuration/ui", "/configuration/security", "/swagger-ui.html",
			"/webjars/**",
			// Swagger UI v3 (OpenAPI)
			"/v3/api-docs/**", "/swagger-ui/**",
			// Actuator
			"/actuator",
			// Application End points
			"/user/add", "/user/jwt", "/state/list" };

	String AUTH_REQUEST_HEADER = "Authorization";
	String JWT_TOKEN_PREFIX = "Bearer ";
}
