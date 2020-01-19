package com.lr.rocketmq.base.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * @author liurui
 * @date 2020/1/19 15:09
 * 异步发送消息
 */
public class AsyncProducer {
    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("group3");

        producer.setNamesrvAddr("192.168.43.128:9876;192.168.43.129:9876");
        producer.start();


        for (int i = 1; i <= 10; i++) {
            Message message = new Message("baseTopic", "baseTop2", ("hello world" + i).getBytes());

            producer.send(message, new SendCallback() {
                public void onSuccess(SendResult sendResult) {
                    System.out.println("发送消息成功：" + sendResult);
                }

                public void onException(Throwable throwable) {
                    System.out.println("发送消息失败：" + throwable);

                }


            });
            TimeUnit.SECONDS.sleep(1);
        }

        producer.shutdown();
    }

}
