package com.example.rabbitmq.config;

import org.junit.ClassRule;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.GenericContainer;

@Configuration
public class RabbitReceiver {

    @ClassRule
    private static GenericContainer rabbit = new GenericContainer("rabbitmq:3-management")
            .withExposedPorts(5672);
}
