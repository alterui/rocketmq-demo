package com.lr.rocketmq.base.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

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
        /**
         * 设置这个组名的原因是为了同一个组的消费者可以负载均衡的消费订阅的同一个topic和tag消息。
         */
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");

        //2.指定NameServer地址
        consumer.setNamesrvAddr("192.168.47.130:9876;192.168.47.129:9876");

        //3.订阅主题Topic和Tag
        consumer.subscribe("baseTopic", "baseTag");

        //设置消费模式：负载均衡(默认)和广播模式

        consumer.setMessageModel(MessageModel.BROADCASTING);

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
