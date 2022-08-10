package com.example.rabbitmq.service.rabbit.handlers;

import com.example.rabbitmq.dto.MessageDto;
import com.example.rabbitmq.service.AbstractRabbitMqService;
import com.example.rabbitmq.service.rabbit.RabbitMqService;
import com.example.rabbitmq.service.rabbit.enm.TypeHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitMqHeaderServiceImpl extends AbstractRabbitMqService implements RabbitMqService {

    public RabbitMqHeaderServiceImpl(RabbitTemplate rabbitTemplate) {
        super(rabbitTemplate);
    }

    @Override
    public TypeHandler getTypeHandler() {
        return TypeHandler.HEADER;
    }

    @Override
    public void send(MessageDto message) {
        MessageProperties messageProperties = new MessageProperties();
        if ((System.currentTimeMillis() % 2) == 0) {
            messageProperties.setHeader("x-match", "all");
            messageProperties.setHeader("h1", "Header1");
            messageProperties.setHeader("h2", "Header2");
        } else {
            if ((System.currentTimeMillis() % 2) == 0) {
                messageProperties.setHeader("h2", "Header2");
            } else {
                messageProperties.setHeader("h1", "Header1");
            }
            messageProperties.setHeader("x-match", "any");
        }
        send(exchange.concat("-header"), "", message, messageProperties);
    }

}
