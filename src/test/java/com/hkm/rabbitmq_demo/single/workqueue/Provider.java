package com.hkm.rabbitmq_demo.single.workqueue;

import com.hkm.rabbitmq_demo.single.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Provider {
    public static void main(String[] args) throws Exception{
        //获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare("work",true,false,false,null);
        for (int i = 1; i <= 1000; i++) {
            //发送消息
            channel.basicPublish("","work",null,(i+"hellow workqueue").getBytes());
        }


        //关闭资源
        RabbitMQUtils.closeConnectionAndChannel(channel,connection);
    }
}
