package com.lr.rocketmq.base.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;

/**
 * @author liurui
 * @date 2020/1/19 11:36
 * 单向发送消息的生产者
 */
public class OneWayProducer {
    public static void main(String[] args) throws Exception {

        //1.创建消息生产者producer，并制定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //2.指定NameServer地址
        producer.setNamesrvAddr("192.168.47.129:9876;192.168.47.130:9876");
        //3.启动producer
        producer.start();
        //4.创建消息对象，指定主题Topic、Tag和消息体
        for (int i = 1; i <= 10; i++) {
            //创建消息体对象(topic,tags,body)

            Message message = new Message("baseTopic", "baseTop2", ("hello world" + i).getBytes());

            //5.发送消息
            producer.sendOneway(message);

        }

        producer.shutdown();


    }
}
