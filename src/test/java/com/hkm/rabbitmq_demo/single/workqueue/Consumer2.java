package com.hkm.rabbitmq_demo.single.workqueue;

import com.hkm.rabbitmq_demo.single.utils.RabbitMQUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class Consumer2 {
    public static void main(String[] args) throws Exception{
        Connection connection = RabbitMQUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.basicQos(1);//每一次只能消费一个消息
        channel.queueDeclare("work",true,false,false,null);
        channel.basicConsume("work",false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("消费者2:"+new String(body));
                //手动确认消费了
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });
    }
}
