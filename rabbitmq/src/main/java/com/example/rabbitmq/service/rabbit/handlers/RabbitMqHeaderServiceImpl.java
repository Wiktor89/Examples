package com.example.rabbitmq.service.rabbit.handlers;

import com.example.rabbitmq.dto.MessageDto;
import com.example.rabbitmq.service.AbstractRabbitMqService;
import com.example.rabbitmq.service.rabbit.RabbitMqService;
import com.example.rabbitmq.service.rabbit.enm.TypeHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

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
        MessageProperties messageProperties = createHeader(message.getHeaders());
        send(exchange.concat("-header"), "", message, messageProperties);
    }

    private MessageProperties createHeader(Map<String, String> headers) {
        MessageProperties messageProperties = new MessageProperties();
        headers.forEach(messageProperties::setHeader);
        return messageProperties;
    }
}
