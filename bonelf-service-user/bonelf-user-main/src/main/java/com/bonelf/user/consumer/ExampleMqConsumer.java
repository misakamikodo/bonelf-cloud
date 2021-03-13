/*
 * Copyright (c) 2021. Bonelf.
 */

package com.bonelf.user.consumer;

import com.bonelf.user.messaging.ExampleSink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
// import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * FIXME mq启用后 打开@StreamListener注释
 */
@Slf4j
@ConditionalOnBean(name = "BNF_support")
@Component
public class ExampleMqConsumer {

	/**
	 * 可以定义Sink筛选Tag
	 * 也可以使用condition筛选
	 * 我使用Tag筛选Topic，condition筛选head
	 * @param message
	 */
	// @StreamListener(value = ExampleSink.INPUT, condition = "headers['TAGS']=='TestTag'")
	public void receiveInput(String message) {
		log.info("Receive input: " + message);
	}

	// @StreamListener(value = ExampleSink.INPUT)
	// public void userReceive(@Payload TempBean temp, @Headers Map<String, ?> headers, @Header(name = "TAGS") Object name) {
	// 	log.info(headers.get("contentType").toString());
	// 	log.info("name : {}", name.toString());
	// 	log.info("Received from {} channel username: {}", Sink.INPUT, temp.getTest());
	// }
}
