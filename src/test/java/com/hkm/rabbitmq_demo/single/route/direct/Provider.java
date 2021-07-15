package com.hkm.rabbitmq_demo.single.route.direct;

import com.hkm.rabbitmq_demo.single.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.nio.charset.StandardCharsets;

public class Provider {
    public static void main(String[] args) throws Exception{
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "log_direct";
        String routeKey = "info";
        /**
         * 通过通道声明交换机
         * 参数1：交换机名称
         * 参数2：direct 路由模式
         */
        channel.exchangeDeclare(exchangeName,"direct");
        channel.basicPublish(exchangeName,routeKey,null,("这是direct模型发布的基于routingKey:"+routeKey+"的消息").getBytes(StandardCharsets.UTF_8));
        RabbitMQUtils.closeConnectionAndChannel(channel,connection);
    }
}
