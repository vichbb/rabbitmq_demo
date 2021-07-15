package com.hkm.rabbitmq_demo.single.helloword;

import com.hkm.rabbitmq_demo.single.utils.RabbitMQUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Consumer {


    public static void main(String[] args) throws Exception{
        //获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        //获取连接通道
        Channel channel = connection.createChannel();

        /**
         * 通道绑定消息队列
         * 参数1：队列名称，不存在则自动创建
         * 参数2：用来定义队列是否要持久化，true为要持久化 false为不持久化
         * 参数3：exclisive 是否独占队列，这个队列只允许当前这个connection可用，其他的connection不能用，true 独占 false 不独占
         * 参数4：autoDelete 是否在消费完成后自动删除队列 true 自动删除 false 不自动删除
         * 参数5：额外附加参数
         */
        channel.queueDeclare("hellow",false,false,false,null);

        /**
         * 消费消息
         * 参数1：消费哪个队列的消息 队列名称
         * 参数2：开启消息的自动确认机制
         * 参数3：消费消息时的回调接口
         */
        channel.basicConsume("hellow",true,new DefaultConsumer(channel){
            /**
             *
             * @param consumerTag
             * @param envelope
             * @param properties
             * @param body :消息队列中取出的消息
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("msgBody==:"+new String(body));
            }
        });
        /**
         * 如果关闭的话只会消费一条
         * 不关闭则会一直监听一直消费
         */
//        RabbitMQUtils.closeConnectionAndChannel(channel,connection);

    }

}
