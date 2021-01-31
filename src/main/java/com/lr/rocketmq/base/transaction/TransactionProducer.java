package com.lr.rocketmq.base.transaction;

import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * @author liurui
 * @date 2020/1/19 19:44
 */
public class TransactionProducer {
    public static void main(String[] args) throws Exception{

        TransactionMQProducer producer = new TransactionMQProducer("group");

        producer.setNamesrvAddr("192.168.47.129:9876;192.168.47.130:9876");

        //设置事务的监听器
        producer.setTransactionListener(new TransactionListener() {

            /**
             * 执行本地事务
             * @param message
             * @param o
             * @return
             */
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {

                /**
                 * 事务的三种状态： 提交状态，回滚状态，中间状态
                 *
                 */

                if ("tagA".equals(message.getTags())) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                } else if ("tagB".equals(message.getTags())) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                } else if("tagC".equals(message.getTags())){
                    return LocalTransactionState.UNKNOW;
                }
                return null;
            }

            /**
             * 检查本地事务
             *
             * @param messageExt
             * @return
             */
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                System.out.println("消息的id" + messageExt.getTags());
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });

        producer.start();
        String[] tags = {"tagA","tagB","tagC"};

        for (int i = 0; i < 3; i++) {
            Message message = new Message("transactionTopic", tags[i], ("hello world" + i).getBytes());

           // message.setDelayTimeLevel(3);
           // message.putUserProperty("a", String.valueOf(i));

            SendResult send = producer.sendMessageInTransaction(message,null);

            System.out.println("发送消息：" + send);
        }

        //保证回调
       // producer.shutdown();
    }
}
