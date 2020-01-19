package com.lr.rocketmq.base.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * @author liurui
 * @date 2020/1/19 11:36
 * 同步发送消息的生产者
 */
public class SyncProducer {
    public static void main(String[] args) throws Exception {

        //1.创建消息生产者producer，并制定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //2.指定NameServer地址
        producer.setNamesrvAddr("192.168.43.129:9876;192.168.43.128:9876;192.168.43.127:9876");
        //3.启动producer
        producer.start();
        //4.创建消息对象，指定主题Topic、Tag和消息体
        for (int i = 1; i <= 10000; i++) {
            //创建消息体对象(topic,tags,body)

            /**
             * todo 这里发现一个神奇的问题，topic的名字设置为‘XX1’的话，就只消费一个broker_a
             */
            Message message = new Message("baseTopic", "baseTop", ("hello world" + i).getBytes());

            //5.发送消息
            SendResult sendResult = producer.send(message);



            //发送状态
            SendStatus sendStatus = sendResult.getSendStatus();
            //消息id
            String msgId = sendResult.getMsgId();
            //消息队列id
            int queueId = sendResult.getMessageQueue().getQueueId();

            System.out.println("消息发送状态：" + sendResult + ",消息id：" + msgId + "，消息队列id：" + queueId);

            //TimeUnit.SECONDS.sleep(1);


        }

        producer.shutdown();


    }
}
