package com.lr.rocketmq.base.order;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author liurui
 * @date 2020/1/19 17:27
 * 订单生产者
 */

public class OrderProducer {
    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("group9");

        producer.setNamesrvAddr("192.168.47.130:9876;192.168.47.129:9876");
        producer.start();


        OrderStep orderStep = new OrderStep();
        List<OrderStep> orderStepList = orderStep.buildOrders();

        for (OrderStep order : orderStepList) {
            Message message = new Message("orderTopic", "orderTag", order.toString().getBytes());


            SendResult send = producer.send(message, new MessageQueueSelector() {
                /**
                 *
                 * @param list 所有的消息队列
                 * @param message 发送的消息
                 * @param o 可选择的比较
                 * @return
                 */
                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                    /**
                     * 每个Order的订单号相同，把订单号对队列个数进行取模，就能保证一个订单的所有操作都在同一个队列里面。
                     */
                    long orderId = (Long) o;
                    long index = orderId % list.size();
                    System.out.println(index);
                    return list.get((int) index);
                }
            }, order.getOrderId());

            System.out.println("发送消息："+ send);


        }

        producer.shutdown();


    }
}
