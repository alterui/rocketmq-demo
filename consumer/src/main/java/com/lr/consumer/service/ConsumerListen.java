package com.lr.consumer.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author liurui
 * @date 2020/1/20 11:51
 */

@Slf4j
@Component
@RocketMQMessageListener(topic = "rocket",consumerGroup = "my-group")
public class ConsumerListen implements RocketMQListener<String> {
    @Override
    public void onMessage(String string) {
        log.info("接收到消息了："+string);
    }
}
