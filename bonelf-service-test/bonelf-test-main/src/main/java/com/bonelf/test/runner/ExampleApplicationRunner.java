/*
 * Copyright (c) 2021. Bonelf.
 */

package com.bonelf.test.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExampleApplicationRunner implements ApplicationRunner {
	// @Autowired
	// private ObjectMapper objectMapper;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// System.out.println("=test objectMapperName=" + objectMapper.getClass().getSimpleName());
		// System.out.println("=test=" + objectMapper.writeValueAsString(new TestConverterVO()));
	}
}
