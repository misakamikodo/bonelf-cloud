package com.bonelf.system.provider.impl;

import com.bonelf.system.messaging.SystemSource;
import com.bonelf.system.provider.ExampleProducer;
import com.bonelf.frame.mq.bus.MqProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
public class ExampleProducerImpl implements ExampleProducer {
	@Autowired
	private MqProducerService mqProducerService;

	@Override
	@SendTo(SystemSource.OUTPUT)
	public String example(String message, @Header(name = "TestTag") String header) {
		return "ok";
	}

	@Override
	public String example2(String message) {
		mqProducerService.send("TestTag", message);
		return "ok";
	}
}
