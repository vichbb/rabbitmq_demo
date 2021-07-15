package com.hkm.rabbitmq_demo.single.route.topic;

import com.hkm.rabbitmq_demo.single.utils.RabbitMQUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class Consumer1 {
    public static void main(String[] args) throws Exception{
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        String exchangeName = "topics";
        //通道声明交换机
        channel.exchangeDeclare(exchangeName,"topic");
        //创建一个临时队列
        String queueName = channel.queueDeclare().getQueue();
        //基于route key 绑定交换机和队列
        channel.queueBind(queueName,exchangeName,"user.*");
        channel.basicConsume(queueName,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("消费者1：{}"+new String(body));
            }
        });
    }
}
