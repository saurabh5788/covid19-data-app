package com.ssingh.covid19.config;

import java.io.IOException;
import java.time.Instant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ssingh.covid19.constants.ApplicationConstants;

@Configuration
public class JacksonConfig {
	@Bean
	public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder
				.json();
		builder.propertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE);
		builder.serializationInclusion(JsonInclude.Include.NON_NULL);
		builder.serializationInclusion(JsonInclude.Include.NON_EMPTY);
		builder.indentOutput(true);
		builder.featuresToDisable(SerializationFeature.WRAP_ROOT_VALUE);
		builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		builder.featuresToEnable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		// builder.serializers(new AppCustomSerializer());

		SimpleModule instantModule = new SimpleModule();
		instantModule.addSerializer(Instant.class, new InstantSerializer());
		instantModule.addDeserializer(Instant.class, new InstantDeserializer());
		builder.modules(instantModule);
		return builder;
	}

	private static class InstantSerializer extends JsonSerializer<Instant> {
		@Override
		public void serialize(Instant value, JsonGenerator gen,
				SerializerProvider serializers) throws IOException,
				JsonProcessingException {
			String str = ApplicationConstants.APP_DATE_FOMATTER.format(value);
			gen.writeString(str);
		}
	}

	private static class InstantDeserializer extends
			JsonDeserializer<Instant> {
		@Override
		public Instant deserialize(JsonParser p, DeserializationContext ctxt)
				throws IOException, JsonProcessingException {
			return Instant.from(ApplicationConstants.APP_DATE_FOMATTER.parse(p
					.getText()));
		}
	}
}
