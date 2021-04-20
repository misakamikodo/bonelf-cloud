package com.bonelf.test.config;

// import org.springframework.cloud.stream.annotation.EnableBinding;
import com.bonelf.test.messaging.ExampleSink;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 测试用Bean注入
 * FIXME 正式放开 @EnableBinding({ExampleSink.class})
 * </p>
 * @author bonelf
 * @since 2020/10/5 21:07
 */
// @ConditionalOnProperty(prefix = BonelfConstant.PROJECT_NAME + ".rocketmq",
// 		value = "enable", havingValue = "true")
@EnableBinding({ExampleSink.class})
// @EnableBinding({Processor.class})
@Configuration
public class RocketMqConfig {
}
