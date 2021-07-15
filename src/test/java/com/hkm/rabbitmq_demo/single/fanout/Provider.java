package com.hkm.rabbitmq_demo.single.fanout;

import com.hkm.rabbitmq_demo.single.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.nio.charset.StandardCharsets;

public class Provider {
    public static void main(String[] args) throws Exception{
        //获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        /**
         * 将通道声明指定的交换机
         * 参数1：交换机的名称
         * 参数2：交换机的类型： fanout代表广播类型
         */
        channel.exchangeDeclare("logs","fanout");

        //发送消息
        channel.basicPublish("logs","",null,"fanout type message".getBytes(StandardCharsets.UTF_8));

        //释放资源
        RabbitMQUtils.closeConnectionAndChannel(channel,connection);
    }
}
