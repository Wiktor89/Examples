package com.example.rabbitmq.controllers;

import com.example.rabbitmq.dto.MessageDto;
import com.example.rabbitmq.service.RabbitMqFacade;
import com.example.rabbitmq.service.rabbit.enm.TypeHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RabbitMqController {

    private final RabbitMqFacade rabbitMqFacade;
    private final ObjectMapper mapper;
    @Value("${spring.repeat.count}")
    private int repeat;

    @SneakyThrows
    @PostMapping("/send")
    public ResponseEntity<String> sendRabbit(@RequestBody MessageDto message) {
        rabbitMqFacade.send(message);
        return ResponseEntity.ok("Ok send message");
    }

    @SneakyThrows
    @EventListener(ApplicationReadyEvent.class)
    public void ping() {
        MessageDto message = new MessageDto();
        for (int i = 0; i < repeat; i++) {
            Arrays.stream(TypeHandler.values()).forEach(val -> {
                message.setTypeHandler(val);
                message.setPayload(val.name().getBytes(StandardCharsets.UTF_8));
                sendRabbit(message);
            });
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
