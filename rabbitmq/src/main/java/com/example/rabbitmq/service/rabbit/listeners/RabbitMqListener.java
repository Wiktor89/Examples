package com.example.rabbitmq.service.rabbit.listeners;

import com.example.rabbitmq.config.RabbitMqConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqListener {

    @Autowired
    private RabbitMqConfig rabbitMqConfig;

    @RabbitListener(queues = "#{rabbitMqConfig.getQueue().concat('-fanout')}")
    public void fanoutListener(Message message) {
        System.out.println(new String(message.getBody()));
    }

    @RabbitListener(queues = "#{rabbitMqConfig.getQueue().concat('-direct')}")
    public void directListener(Message message) {
        System.out.println(new String(message.getBody()));
    }

    @RabbitListener(queues = "#{rabbitMqConfig.getQueue().concat('-topic')}")
    public void topicListener(Message message) {
        System.out.println(new String(message.getBody()));
    }

    @RabbitListener(queues = {
            "#{rabbitMqConfig.getQueue().concat('-header-one')}",
            "#{rabbitMqConfig.getQueue().concat('-header-two')}",
            "#{rabbitMqConfig.getQueue().concat('-header-three')}"})
    public void headerListener(Message message) {
        System.out.println(new String(message.getBody()));
    }
}
