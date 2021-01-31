package com.lr.rocketmq.base.order;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author liurui
 * @date 2020/1/19 17:41
 * 订单的消费者
 */
public class OrderConsumer {
    public static void main(String[] args) throws  Exception{
        System.out.println("开始");
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group9");

        consumer.setNamesrvAddr("192.168.47.130:9876;192.168.47.129:9876");
        consumer.subscribe("orderTopic", "orderTag");

        /**
         * 使用顺序消费，只能保证同一个队列的顺序消费；
         * 因为消费者方有多个线程，去消费queue里面的消息。
         */
        consumer.registerMessageListener(new MessageListenerOrderly() {
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {

                for (MessageExt messageExt : list) {

                    System.out.println("当前线程：" + Thread.currentThread().getName() +",消息为："+ new String(messageExt.getBody()));
                }


                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();


    }

}
