/*
 * Copyright (c) 2021. Bonelf.
 */

package com.bonelf.user.consumer;

import com.alibaba.fastjson.JSON;
import com.bonelf.frame.core.websocket.SocketRespMessage;
import com.bonelf.user.messaging.ExampleSink;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.context.IntegrationContextUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Component;

/**
 *  mq启用后 打开@StreamListener注释
 */
@Slf4j
@Component
public class ExampleMqConsumer {

	@StreamListener(value = ExampleSink.INPUT, condition = "headers['rocketmq_TAGS']=='TestTag'")
	public void receiveInput1(@Payload String message) {
		log.info("Receive MQ input: \n" + message);
	}

	@StreamListener(value = ExampleSink.INPUT, condition = "headers['rocketmq_TAGS']=='websocket-user'")
	public void websocketUserMsg(@Payload SocketRespMessage message) {
		log.info("Receive MQ input: \n" + JSON.toJSONString(message));
	}

	/**
	 * 可以定义Sink筛选Tag
	 * 也可以使用condition筛选
	 * 我使用Tag筛选Topic，condition筛选head
	 * , condition = "headers['rocketmq_TAGS']=='TestTag'"
	 * @param message
	 */
	// @StreamListener(value = ExampleSink.INPUT)
	// public void receiveInput2(@Payload String message, @Headers Map<String, ?> headers) {
	// 	log.info("Receive MQ input: \n" + message);
	// 	log.info("headers: " + JSON.toJSONString(headers));
	// }

	// @StreamListener(value = ExampleSink.INPUT)
	// public void userReceive(@Payload TempBean temp, @Headers Map<String, ?> headers, @Header(name = "rocketmq_TAGS") Object name) {
	// 	log.info(headers.get("contentType").toString());
	// 	log.info("name : {}", name.toString());
	// 	log.info("Received from {} channel username: {}", Sink.INPUT, temp.getTest());
	// }

	/**
	 * errorChannel
	 * @param errorMessage
	 */
	@StreamListener(IntegrationContextUtils.ERROR_CHANNEL_BEAN_NAME)
	public void globalHandleError(ErrorMessage errorMessage) {
		log.error("[globalHandleError][payload：{}]", ExceptionUtils.getRootCauseMessage(errorMessage.getPayload()));
		log.error("[globalHandleError][originalMessage：{}]", errorMessage.getOriginalMessage());
		log.error("[globalHandleError][headers：{}]", errorMessage.getHeaders());
	}
}
