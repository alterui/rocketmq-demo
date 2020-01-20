package com.lr.dubbo.producer.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.lr.UserService;
import org.springframework.stereotype.Component;

/**
 * @author liurui
 * @date 2020/1/20 14:59
 */

@Component
@Service
public class UserServiceImpl implements UserService {

    @Override
    public String sayHello() {
        return "hello world";
    }
}
