package com.example.rabbitmq;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.metrics.ApplicationStartup;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("integration-test")
@Testcontainers
@ExtendWith({SpringExtension.class, OutputCaptureExtension.class})
@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTest {

    @Container
    private static GenericContainer rabbit = new GenericContainer("rabbitmq:3-management")
            .withExposedPorts(5672);

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues testPropertyValues = TestPropertyValues.of(
                    "spring.rabbit.host=" + rabbit.getHost(),
                    "spring.rabbit.port=" + rabbit.getMappedPort(5672));
            testPropertyValues.applyTo(applicationContext);
        }
    }
}
