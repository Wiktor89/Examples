package com.example.rabbitmq.service;

import com.example.rabbitmq.AbstractIntegrationTest;
import com.example.rabbitmq.dto.MessageDto;
import com.example.rabbitmq.service.rabbit.enm.TypeHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.system.CapturedOutput;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import org.testcontainers.shaded.org.hamcrest.CoreMatchers;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;


public class RabbitMqServiceImplTest extends AbstractIntegrationTest {

    @Autowired
    private RabbitMqFacade rabbitMqFacade;

    @Test
    @DisplayName("Test exchange type fanout")
    public void fanout(CapturedOutput output) {
        rabbitMqFacade.send(createMessage(TypeHandler.FANOUT, "fanout UP"));
        Awaitility.await().atMost(1, TimeUnit.SECONDS).until(isMessageConsumed(output, "fanout UP"), CoreMatchers.is(true));
    }

    @Test
    @DisplayName("Test exchange type direct")
    void direct(CapturedOutput output) {
        rabbitMqFacade.send(createMessage(TypeHandler.DIRECT, "direct UP"));
        Awaitility.await().atMost(1, TimeUnit.SECONDS).until(isMessageConsumed(output, "direct UP"), CoreMatchers.is(true));
    }

    @Test
    @DisplayName("Test exchange type topic")
    void topic(CapturedOutput output) {
        rabbitMqFacade.send(createMessage(TypeHandler.TOPIC, "topic UP"));
        Awaitility.await().atMost(1, TimeUnit.SECONDS).until(isMessageConsumed(output, "topic UP"), CoreMatchers.is(true));
    }

    @Test
    @DisplayName("Test exchange type header: x-match = all")
    void header_0(CapturedOutput output) {
        MessageDto message = createMessage(TypeHandler.HEADER, "x-match = all UP");
        Map<String, String> headers = new HashMap<>();
        headers.put("x-match", "all");
        headers.put("h1", "Header1");
        headers.put("h2", "Header2");
        message.setHeaders(headers);
        rabbitMqFacade.send(message);
        Awaitility.await().atMost(1, TimeUnit.SECONDS).until(isMessageConsumed(output, "x-match = all UP"), CoreMatchers.is(true));
    }

    @Test
    @DisplayName("Test exchange type header: x-match = any Header1")
    void header_1(CapturedOutput output) {
        MessageDto message = createMessage(TypeHandler.HEADER, "x-match = any Header1");
        Map<String, String> headers = new HashMap<>();
        headers.put("x-match", "any");
        headers.put("h1", "Header1");
        message.setHeaders(headers);
        rabbitMqFacade.send(message);
        Awaitility.await().atMost(1, TimeUnit.SECONDS).until(isMessageConsumed(output, "x-match = any Header1"), CoreMatchers.is(true));
    }

    @Test
    @DisplayName("Test exchange type header: x-match = any Header2")
    void header_2(CapturedOutput output) {
        MessageDto message = createMessage(TypeHandler.HEADER, "x-match = any Header2");
        Map<String, String> headers = new HashMap<>();
        headers.put("x-match", "any");
        headers.put("h2", "Header2");
        message.setHeaders(headers);
        rabbitMqFacade.send(message);
        Awaitility.await().atMost(1, TimeUnit.SECONDS).until(isMessageConsumed(output, "x-match = any Header2"), CoreMatchers.is(true));
    }

    private MessageDto createMessage(TypeHandler typeHandler, String message) {
        MessageDto messageDto = new MessageDto();
        messageDto.setPayload(message.getBytes(StandardCharsets.UTF_8));
        messageDto.setTypeHandler(typeHandler);
        return messageDto;
    }

    private Callable<Boolean> isMessageConsumed(CapturedOutput output, String message) {
        return () -> output.toString().contains(message);
    }
}