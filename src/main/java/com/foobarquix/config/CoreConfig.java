package com.foobarquix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.foobarquix.core.FoobarQuixEngine;
import com.foobarquix.core.NumberTransformationEngine;

/**
 * author : Wassim MNASSER
 */
@Configuration
public class CoreConfig {

	@Bean
	public NumberTransformationEngine numberTransformationEngine() {
		return new FoobarQuixEngine();
	}
}