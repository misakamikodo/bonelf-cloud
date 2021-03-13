package com.bonelf.test.config.websocket;

import com.bonelf.test.socket.SocketMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * 发布订阅
 * 为解决问题：
 * WebSocketSession这个对象不支持序列化，所以就无法缓存到Redis中；
 * 在分布式系统中就无法获取到所有session，这里就需要使用一个中间件将消息推送到各个系统；
 *
 * 也就是说这个服务启动了websocket 其他服务添加一样的RedisSubscriptionConfig配置即可收发消息 可通过channel区分订阅消息
 *
 * 此发布订阅配置使用websocket服务同样适用
 * @author bonelf
 **/
@Configuration
@Slf4j
public class RedisSubscriptionConfig {
	@Value("${spring.application.name:test}")
	private String serviceName;

	/**
	 * 消息监听器适配器，绑定消息处理器，利用反射技术调用消息处理器的业务方法
	 * @param receiver
	 * @return
	 */
	@Bean
	public MessageListenerAdapter listenerAdapter(SocketMessageService receiver) {
		return new MessageListenerAdapter(receiver);
		//return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	@Bean
	public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
												   MessageListenerAdapter listenerAdapter) {

		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		// 可以添加多个 messageListener，配置不同的交换机
		container.addMessageListener(listenerAdapter, new PatternTopic(serviceName));
		return container;
	}
}
