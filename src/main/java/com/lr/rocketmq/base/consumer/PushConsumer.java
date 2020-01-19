package com.lr.rocketmq.base.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.Arrays;
import java.util.List;

/**
 * @author liurui
 * @date 2020/1/19 15:55
 * 消息消费者
 */
public class PushConsumer {

    public static void main(String[] args) throws Exception{

        //1.创建消费者Consumer，制定消费者组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");

        //2.指定NameServer地址
        consumer.setNamesrvAddr("192.168.43.128:9876;192.168.43.129:9876");

        //3.订阅主题Topic和Tag
        consumer.subscribe("baseTopic", "baseTop");

        //4.设置回调函数，处理消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt msg : list) {
                    System.out.println(new String(msg.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //5.启动消费者consumer
        consumer.start();


    }
}
