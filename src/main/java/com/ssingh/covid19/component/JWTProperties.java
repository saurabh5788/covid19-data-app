package com.ssingh.covid19.component;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JWTProperties {
	private Map<String, String> token;

	public Map<String, String> getToken() {
		return token;
	}

	public void setToken(Map<String, String> token) {
		this.token = token;
	}
}
