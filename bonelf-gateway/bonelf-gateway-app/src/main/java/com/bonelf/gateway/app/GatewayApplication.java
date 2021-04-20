package com.bonelf.gateway.app;

import com.bonelf.cicada.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * 如需配置扫包，不要配置对common模块 spring-boot-starter-web架包相关 的扫包（config目录）
 * 可以配置对common.util的工具类（redisUtil）等的扫包
 *
 * 不可使用jrebel热部署启动，否则出现EndpointDiscoverer$EndpointBean cannot be cast to JrEndPointBean
 */
@Slf4j
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {
		"com.bonelf.gateway.main",
})
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(GatewayApplication.class);
		// 该设置方式 也可以解决missing ServletWebServerFactory bean
		// application.setWebApplicationType(WebApplicationType.REACTIVE);
		ConfigurableApplicationContext applicationContext = application.run(args);
		Environment env = applicationContext.getEnvironment();
		//String ip = InetAddress.getLocalHost().getHostAddress(); //.getLocalHost()
		String ip = IpUtil.getWlanV4Ip();
		String port = env.getProperty("server.port");
		String path = env.getProperty("server.servlet.context-path");
		log.info("\n----------------------------------------------------------\n\t" +
				"Application is running! Access URLs:\n\t" +
				"Local: \t\t\thttp://127.0.0.1:" + port + path + "/\n\t" +
				"Wlan: \t\t\thttp://" + ip + ":" + port + path + "/\n\t" +
				"swagger-ui: \t1: http://" + ip + ":" + port + "/doc.html\t" + "2: http://" + ip + ":" + port + "/swagger-ui/\n\t" +
				// 与gateway无关写死了，根据具体自行修改~
				"Websocket: \t\tws://" + ip + ":" + 8802 + "/\n" +
				"----------------------------------------------------------");
	}
}
