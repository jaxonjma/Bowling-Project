package com.jaxon.bowling.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	
	@Value("${file.processor.origin}")
	private String filesOrigin;

	@Bean
	public Path path() {
		return Paths.get(filesOrigin);
	}
}
