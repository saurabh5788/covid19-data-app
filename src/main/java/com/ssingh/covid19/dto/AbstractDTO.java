package com.ssingh.covid19.dto;

import java.io.Serializable;
import java.time.Instant;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AbstractDTO implements Serializable {

	private static final long serialVersionUID = 4385618447315211373L;

	private Instant time = Instant.now();
	
	public Instant getTime() {
		return time;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.JSON_STYLE);
	}
}
