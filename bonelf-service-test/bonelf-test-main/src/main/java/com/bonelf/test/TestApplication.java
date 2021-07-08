package com.bonelf.test;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.bonelf.cicada.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * <p>
 * 默认情况下，扫描入口类同级及其子级包下的所有文件，这里配置了扫描公共配置
 * EnableDiscoveryClient
 * </p>
 * @author bonelf
 * @since 2020/10/6 18:58
 */
@Slf4j
@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {
		"com.bonelf.test"
},
		exclude = {DruidDataSourceAutoConfigure.class})
public class TestApplication {
	@Value("${bonelf.mode}")
	private String test;
	@Value("${mybatis-plus.config-location}")
	private String te;

	public static void main(String[] args) {
		ConfigurableApplicationContext application;
		try {
			application = SpringApplication.run(TestApplication.class, args);
		} catch (Throwable e) {
			e.printStackTrace();
			return;
		}
		Environment env = application.getEnvironment();
		String ip = IpUtil.getWlanV4Ip();
		String port = env.getProperty("server.port");
		String path = env.getProperty("server.servlet.context-path");
		log.info("\n----------------------------------------------------------\n\t" +
				"Application is running! Access URLs:\n\t" +
				"Local: \t\thttp://127.0.0.1:" + port + path + "\n\t" +
				"External: \thttp://" + ip + ":" + port + path + "\n\t" +
				"Websocket: \tws://" + ip + ":" + port + "\n" +
				"----------------------------------------------------------");
	}
}