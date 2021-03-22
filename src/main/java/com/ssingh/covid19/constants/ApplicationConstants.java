package com.ssingh.covid19.constants;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public interface ApplicationConstants {
	String APP_DATE_FORMAT = "dd-MM-YY HH:mm:ss O";
	DateTimeFormatter APP_DATE_FOMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss O").withZone(ZoneId.of(ZoneOffset.UTC.getId()));
}
