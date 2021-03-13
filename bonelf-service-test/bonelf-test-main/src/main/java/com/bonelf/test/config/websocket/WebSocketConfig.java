package com.bonelf.test.config.websocket;

import com.bonelf.test.socket.WebSocketInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 配置类 ,采用redis发布订阅功能区做websocket消息的不通服务之间的传递<br/>
 * <a href="http://www.websocket-test.com/">websocket在线测试地址</a>
 * @see com.bonelf.test.socket.MainWebSocketHandler
 **/
@Slf4j
@Configuration
// @EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	@Autowired
	private WebSocketHandler mainWebSocketHandler;

	@PostConstruct
	public void init() {
		log.info("Spring Websocket (WebSocketConfig) 已开启");
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		///{userId}
		registry.addHandler(mainWebSocketHandler, "/test")
				.setAllowedOrigins("*").addInterceptors(webSocketInterceptor());
	}

	public HandshakeInterceptor webSocketInterceptor() {
		return new WebSocketInterceptor();
	}

	//@Bean
	//public WebSocketHandler zeusWebSocketHandler() {
	//	return new MainWebSocketHandler();
	//}

	@Bean
	public TaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler threadPoolScheduler = new ThreadPoolTaskScheduler();
		threadPoolScheduler.setThreadNamePrefix("SockJS-");
		threadPoolScheduler.setPoolSize(Runtime.getRuntime().availableProcessors());
		threadPoolScheduler.setRemoveOnCancelPolicy(true);
		return threadPoolScheduler;
	}
}