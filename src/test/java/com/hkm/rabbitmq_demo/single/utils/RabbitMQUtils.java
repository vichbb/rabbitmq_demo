package com.hkm.rabbitmq_demo.single.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQUtils {
    private static final ConnectionFactory connectionFactory;
    static {
        //静态代码块，类加载时执行
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("ems");
        connectionFactory.setUsername("hkm");
        connectionFactory.setPassword("hkm");
    }


    //定义提供连接对象的方法
    public static Connection getConnection() {
        try {
            return connectionFactory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //关闭通道和关闭连接的工具方法
    public static void closeConnectionAndChannel(Channel channel,Connection connection){
        try {
            if(channel != null && connection != null) {
                channel.close();
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
