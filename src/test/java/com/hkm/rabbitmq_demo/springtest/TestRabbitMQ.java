package com.hkm.rabbitmq_demo.springtest;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRabbitMQ {
    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * log
     */
    @Test
    public void testLog(){
        log.info("===============1");
        log.warn("===============1");
        log.error("===============3");
    }

    /**
     * hello world模型
     */
    @Test
    public void testHelloWorld(){
        rabbitTemplate.convertAndSend("hello","hello world");
    }

    /**
     * springboot默认是公平调度，如果实现能者多劳需要额外配置
     */
    @Test
    public void testWork(){
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("work",i+"work 模型");
        }
    }

    /**
     * 广播
     */
    @Test
    public void testFanout(){
        rabbitTemplate.convertAndSend("logs","","Fanout的MQ消息模型");
    }
    /**
     * 路由
     */
    @Test
    public void testRoute(){
        String routeKey = "info";
        if((int)(Math.random()*3+1) == 1){
            routeKey = "info";
        }
        if((int)(Math.random()*3+1) == 2){
            routeKey = "warn";
        }
        if((int)(Math.random()*3+1) == 3){
            routeKey = "error";
        }
        rabbitTemplate.convertAndSend("directs",routeKey,"发送的是"+routeKey+"的key的路由信息");
    }

    /**
     * topic 动态路由订阅模式
     */
    @Test
    public void testTopic(){
        String routeKey = "user.save";
        if((int)(Math.random()*3+1) == 1){
            routeKey = "order.add";
        }
        if((int)(Math.random()*3+1) == 2){
            routeKey = "product.add";
        }
        if((int)(Math.random()*3+1) == 3){
            routeKey = "user.save";
        }
        rabbitTemplate.convertAndSend("topics",routeKey,"发送的是"+routeKey+"的key的路由信息");
    }

}
