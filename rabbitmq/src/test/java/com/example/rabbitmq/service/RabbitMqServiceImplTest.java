package com.example.rabbitmq.service;

import com.example.rabbitmq.dto.MessageDto;
import com.example.rabbitmq.service.rabbit.enm.TypeHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.OutputCaptureRule;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import org.testcontainers.shaded.org.awaitility.core.ConditionFactory;
import org.testcontainers.shaded.org.hamcrest.CoreMatchers;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = RabbitMqServiceImplTest.Initializer.class)
public class RabbitMqServiceImplTest {

    @Autowired
    private RabbitMqFacade rabbitMqFacade;

    @Container
    public static GenericContainer rabbit = new GenericContainer("rabbitmq:3-management")
            .withExposedPorts(5672);

//    @Rule
    public OutputCaptureRule outputCaptureRule = new OutputCaptureRule();

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues testPropertyValues = TestPropertyValues.of(
                    "spring.rabbit.host=" + rabbit.getHost(),
                    "spring.rabbit.port=" + rabbit.getMappedPort(5672));
            testPropertyValues.applyTo(applicationContext);
        }
    }

    @Test
    @DisplayName("Test exchange type fanout")
    public void fanout() {
        MessageDto messageDto = new MessageDto();
        messageDto.setPayload("test".getBytes(StandardCharsets.UTF_8));
        messageDto.setTypeHandler(TypeHandler.FANOUT);
        rabbitMqFacade.send(messageDto);
        ConditionFactory await = Awaitility.await();
        ConditionFactory conditionFactory = await.atMost(1, TimeUnit.SECONDS);
        conditionFactory.until(isMessageConsumed(), CoreMatchers.is(true));
        Assertions.assertFalse(Boolean.FALSE);
    }

    private Callable<Boolean> isMessageConsumed() {
        return () -> {
            return outputCaptureRule.toString().contains("Test");
        };
    }

    @Test
    @DisplayName("Test exchange type direct")
    void direct() {
    }

    @Test
    @DisplayName("Test exchange type topic")
    void topic() {
    }

    @Test
    @DisplayName("Test exchange type header")
    void header() {
    }

    @Test
    @DisplayName("Test exchange type consistent hashing")
    void consistentHashing() {
    }
}