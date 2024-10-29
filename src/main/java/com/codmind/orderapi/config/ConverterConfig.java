package com.codmind.orderapi.config;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import com.codmind.orderapi.converters.UserConverter;

@Configuration
public class ConverterConfig {
	
	@Value("${config.datetimeFormat}")
	private String datetimeFormat;

	
	@Bean
	public UserConverter getUserConverter() {
		return new UserConverter();
	}
}
