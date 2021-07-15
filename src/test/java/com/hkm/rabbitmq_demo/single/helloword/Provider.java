package com.hkm.rabbitmq_demo.single.helloword;

import com.hkm.rabbitmq_demo.single.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Provider {

    //生产消息
    @Test
    public void testSendMessage() throws IOException, TimeoutException {
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
         * 发布消息
         * 参数1：交换机名称
         * 参数2：队列名称
         * 参数3：传递消息的额外设置：例如消息持久化(rabbitmq重启之后消息不会丢失): MessageProperties.PERSISTENT_TEXT_PLAIN
         * 参数4：消息体
         */
        channel.basicPublish("","hellow", MessageProperties.PERSISTENT_TEXT_PLAIN,"hello rabbitMQ".getBytes());

        RabbitMQUtils.closeConnectionAndChannel(channel,connection);
    }

}
