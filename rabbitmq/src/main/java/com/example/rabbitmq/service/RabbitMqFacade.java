package com.example.rabbitmq.service;

import com.example.rabbitmq.dto.MessageDto;
import com.example.rabbitmq.service.rabbit.RabbitMqService;
import com.example.rabbitmq.service.rabbit.enm.TypeHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class RabbitMqFacade {

    private final Map<TypeHandler, RabbitMqService> messageHandler = new HashMap<>();

    public void registry(TypeHandler typeHandler, RabbitMqService service) {
        messageHandler.put(typeHandler, service);
    }

    public void send(MessageDto message) {
        if (Objects.nonNull(message) && Objects.nonNull(message.getTypeHandler())) {
            messageHandler.get(message.getTypeHandler())
                    .send(message);
        }
    }
}
