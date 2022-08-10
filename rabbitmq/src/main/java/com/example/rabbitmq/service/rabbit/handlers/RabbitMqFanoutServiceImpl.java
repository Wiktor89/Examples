package com.example.rabbitmq.service.rabbit.handlers;

import com.example.rabbitmq.dto.MessageDto;
import com.example.rabbitmq.service.AbstractRabbitMqService;
import com.example.rabbitmq.service.rabbit.RabbitMqService;
import com.example.rabbitmq.service.rabbit.enm.TypeHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitMqFanoutServiceImpl extends AbstractRabbitMqService implements RabbitMqService {

    public RabbitMqFanoutServiceImpl(RabbitTemplate rabbitTemplate) {
        super(rabbitTemplate);
    }

    @Override
    public TypeHandler getTypeHandler() {
        return TypeHandler.FANOUT;
    }

    @Override
    public void send(MessageDto message) {
        send(exchange.concat("-fanout"), "", message);
    }

}
