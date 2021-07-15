package com.hkm.rabbitmq_demo.single.workqueue;

import com.hkm.rabbitmq_demo.single.utils.RabbitMQUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.SneakyThrows;

import java.io.IOException;

public class Consumer1 {

    public static void main(String[] args) throws Exception{
        Connection connection = RabbitMQUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.basicQos(1);//每一次只能消费一个消息
        channel.queueDeclare("work",true,false,false,null);
        channel.basicConsume("work",false,new DefaultConsumer(channel){
            @SneakyThrows
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                Thread.sleep(1000);
                System.out.println("消费者1:"+new String(body));
                /**
                 * 参数1：确认队列中的某一个消息
                 * 参数2：是否开启多个消息同时确认
                 */
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });
    }
}
