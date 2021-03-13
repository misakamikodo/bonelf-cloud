package com.bonelf.auth.provider;

import org.springframework.messaging.handler.annotation.Header;

public interface ExampleProducer {

	String example(String message, @Header(name = "TestTag") String header);

	String example2(String message);
}
