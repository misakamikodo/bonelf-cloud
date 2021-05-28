/*
 * Copyright (c) 2021. Bonelf.
 */

package com.bonelf.auth.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * FIXME mq启用后 打开@StreamListener注释
 */
@Slf4j
@Component
public class ExampleMqConsumer {

	/**
	 * 可以定义Sink筛选Tag
	 * 也可以使用condition筛选
	 * 我使用Tag筛选Topic，condition筛选head
	 * @param message
	 */
	// @StreamListener(value = ExampleSink.INPUT, condition = "headers['rocketmq_TAGS']=='TestTag'")
	public void receiveInput(String message) {
		log.info("Receive MQ input: \n" + message);
	}

	// @StreamListener(value = ExampleSink.INPUT)
	// public void userReceive(@Payload TempBean temp, @Headers Map<String, ?> headers, @Header(name = "rocketmq_TAGS") Object name) {
	// 	log.info(headers.get("contentType").toString());
	// 	log.info("name : {}", name.toString());
	// 	log.info("Received from {} channel username: {}", Sink.INPUT, temp.getTest());
	// }
}
