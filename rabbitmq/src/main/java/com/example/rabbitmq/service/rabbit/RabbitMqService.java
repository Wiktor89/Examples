package com.example.rabbitmq.service.rabbit;

import com.example.rabbitmq.dto.MessageDto;
import com.example.rabbitmq.service.RabbitMqFacade;
import com.example.rabbitmq.service.rabbit.enm.TypeHandler;
import org.springframework.beans.factory.annotation.Autowired;

public interface RabbitMqService {

    TypeHandler getTypeHandler();

    void send(MessageDto message);

    @Autowired
    default void registry(RabbitMqFacade rabbitMqFacade) {
        rabbitMqFacade.registry(getTypeHandler(), this);
    }
}
