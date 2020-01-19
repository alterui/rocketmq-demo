package com.lr.rocketmq.base.sql;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * @author liurui
 * @date 2020/1/19 19:44
 */
public class SqlProducer {
    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("group");

        producer.setNamesrvAddr("192.168.43.129:9876;192.168.43.128:9876");

        producer.start();

        for (int i = 1; i < 11; i++) {
            Message message = new Message("sqlTopic", "tag", ("hello world" + i).getBytes());

           // message.setDelayTimeLevel(3);
            message.putUserProperty("a", String.valueOf(i));

            SendResult send = producer.send(message);

            System.out.println("发送消息：" + send);
        }

        producer.shutdown();
    }
}
