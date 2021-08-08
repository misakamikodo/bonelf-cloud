package com.bonelf.auth.producer;

import com.bonelf.frame.mq.bus.MqProducerService;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;

/**
 * ------
 * @author bonelf
 * @date 2021/5/28 16:48
 */
public class ExampleProducerImpl {

	private MqProducerService mqProducerService;

	@SendTo(Source.OUTPUT)
	public String example(String message, @Header(name = "TestTag") String header) {
		return "ok";
	}

	public String example2(String message) {
		mqProducerService.send("TestTag", message);
		return "ok";
	}
}
