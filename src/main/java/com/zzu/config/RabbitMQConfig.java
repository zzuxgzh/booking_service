package com.zzu.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    public static final String QUEUE = "buytiket";

    public Queue directQueue(){
        //第一个参数是队列名字，第二个是是否持久化
        return new Queue(QUEUE,true);
    }

}
