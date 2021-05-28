package com.bonelf.test;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class ProducerTest {
	public static void main(String[] args) throws MQClientException, InterruptedException {
		DefaultMQProducer producer = new DefaultMQProducer("support");
		producer.setNamesrvAddr("192.168.31.60:9876");
		producer.start();
		for (int i = 0; i < 2; i++)
			try {
				{
					Message msg = new Message("websocketTopic",
							"TestTag",
							"OrderID188",
							"Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
					SendResult sendResult = producer.send(msg);
					System.out.printf("%s%n", sendResult);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		//producer.shutdown();
	}
}
