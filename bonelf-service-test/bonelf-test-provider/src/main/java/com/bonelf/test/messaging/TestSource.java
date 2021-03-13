/*
 * Copyright (c) 2021. Bonelf.
 */

package com.bonelf.test.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;

/**
 * source通常配置一个就行表示自己这个服务
 */
public interface TestSource extends Source {
	/**
	 * 对应配置项
	 * spring.cloud.rocketmq.binder.bindings.xx(output)
	 * spring.cloud..bindings.xx(output)
	 */
	String OUTPUT = "BNF_test";

	@Output(OUTPUT)
	MessageChannel output();
}
