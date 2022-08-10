package com.example.rabbitmq.service;

import com.example.rabbitmq.dto.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public abstract class AbstractRabbitMqService {
    protected final RabbitTemplate rabbitTemplate;
    @Value("${spring.rabbitmq.template.default-receive-queue}")
    protected String queue;
    @Value("${spring.rabbitmq.template.exchange}")
    protected String exchange;

    public AbstractRabbitMqService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    protected void send(String exchange, String queue, MessageDto message) {
        log.info("Start send message exchange = {}, queue = {}", exchange, queue);
        rabbitTemplate.convertAndSend(exchange, queue, new Message(message.getPayload()));
        log.info("End send message");
    }

    protected void send(String exchange, String queue, MessageDto message, MessageProperties messageProperties) {
        log.info("Start send message exchange = {}, queue = {}, headers = {}", exchange, queue, messageProperties);
        rabbitTemplate.convertAndSend(exchange, queue, new Message(message.getPayload(), messageProperties));
        log.info("End send message");
    }
}
