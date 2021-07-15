package com.hkm.rabbitmq_demo.single.route.topic;

import com.hkm.rabbitmq_demo.single.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Provider {
    public static void main(String[] args) throws Exception{

        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "topics";
        String routeKey = "user.save";
        /**
         * 声明交换机以及其类型
         */
        channel.exchangeDeclare(exchangeName,"topic");

        //发布消息
        channel.basicPublish(exchangeName,routeKey,null,("这是topic动态路由模型"+routeKey).getBytes());
        RabbitMQUtils.closeConnectionAndChannel(channel,connection);

    }
}
