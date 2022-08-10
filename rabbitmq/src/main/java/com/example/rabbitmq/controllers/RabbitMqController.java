package com.example.rabbitmq.controllers;

import com.example.rabbitmq.dto.MessageDto;
import com.example.rabbitmq.service.RabbitMqFacade;
import com.example.rabbitmq.service.rabbit.enm.TypeHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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

    @SneakyThrows
    @PostMapping("/send")
    public ResponseEntity<String> sendRabbit(@RequestBody MessageDto message) {
        String s = mapper.writeValueAsString(message);
        System.out.println(s);
        rabbitMqFacade.send(message);
        return ResponseEntity.ok("Ok send message");
    }

    @SneakyThrows
    @EventListener(ApplicationReadyEvent.class)
    public void ping() {
        MessageDto message = new MessageDto();
        message.setPayload("Test".getBytes(StandardCharsets.UTF_8));
        for (int i = 0; i < 50; i++) {
            Arrays.stream(TypeHandler.values()).forEach(val -> {
                message.setTypeHandler(val);
                sendRabbit(message);
            });
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
