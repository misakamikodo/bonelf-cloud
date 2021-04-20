/*
 * Copyright (c) 2021. Bonelf.
 */

package com.bonelf.user.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.SubscribableChannel;

/**
 * sink可以配置多个表示发送到其他服务
 * NOTE 放开 extend和@import
 */
public interface ExampleSink extends Sink {
	/**
	 * 对应配置项
	 * spring.cloud.rocketmq.binder.bindings.xx(input)
	 * spring.cloud..bindings.xx(input)
	 */
	String INPUT = "input";

	@Override
	@Input(INPUT)
	SubscribableChannel input();
}
