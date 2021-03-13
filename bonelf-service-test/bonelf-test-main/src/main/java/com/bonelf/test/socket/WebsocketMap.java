package com.bonelf.test.socket;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebsocketMap初始化存放地址
 **/
@Component
@Slf4j
@Data
public class WebsocketMap {
	/**
	 * userIdStr：session
	 */
	private ConcurrentHashMap<String, WebSocketSession> socketSessionMap;

	@PostConstruct
	public void init() {
		log.info("创建websocket 存储对象 WebsocketMap");
		/*
		 * 根据预估用户量调整这个初始值大小，避免频繁rehash
		 */
		this.socketSessionMap = new ConcurrentHashMap<>(1000);
	}
}
