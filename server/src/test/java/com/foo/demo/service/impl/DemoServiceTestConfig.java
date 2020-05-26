package com.foo.demo.service.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoServiceTestConfig {

	@Bean
	DemoServiceImpl demoServiceImpl() {
		return new DemoServiceImpl();
	}
}
