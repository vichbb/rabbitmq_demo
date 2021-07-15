package com.hkm.rabbitmq_demo.single.fanout;

import com.hkm.rabbitmq_demo.single.utils.RabbitMQUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;

public class Consumer2 {
    public static void main(String[] args) throws Exception{
        //获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        //通道绑定交换机
        channel.exchangeDeclare("logs","fanout");
        //临时队列
        String queueName = channel.queueDeclare().getQueue();
        //绑定交换机和队列
        channel.queueBind(queueName,"logs","");
        //消费消息
        channel.basicConsume(queueName,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("消费者2:"+new String(body));
            }
        });
    }
}
