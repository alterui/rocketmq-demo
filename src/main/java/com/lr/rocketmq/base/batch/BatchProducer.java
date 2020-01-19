package com.lr.rocketmq.base.batch;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liurui
 * @date 2020/1/19 19:44
 */
public class BatchProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("group");

        producer.setNamesrvAddr("192.168.43.129:9876;192.168.43.128:9876");

        producer.start();

        List<Message> messageList = new ArrayList<>();

        Message message1 = new Message("batchTopic", "tag", ("hello world").getBytes());
        Message message2 = new Message("batchTopic", "tag", ("hello world").getBytes());
        Message message3 = new Message("batchTopic", "tag", ("hello world").getBytes());

        messageList.add(message1);
        messageList.add(message2);
        messageList.add(message3);

        SendResult send = producer.send(messageList);

        System.out.println("发送消息：" + send);


        producer.shutdown();
    }
}
