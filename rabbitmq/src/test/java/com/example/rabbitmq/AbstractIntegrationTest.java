package com.example.rabbitmq;

import org.junit.ClassRule;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;

//@ActiveProfiles("integration-test")
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTest {

//    @ClassRule
//    private static GenericContainer rabbit = new GenericContainer("rabbitmq:3-management")
//            .withExposedPorts(5672);
//
//    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
//
//        @Override
//        public void initialize(ConfigurableApplicationContext applicationContext) {
//            TestPropertyValues testPropertyValues = TestPropertyValues.of("spring.rabbit.host=" + rabbit.getHost(),
//                    "spring.rabbit.port=" + rabbit.getPortBindings());
//            testPropertyValues.applyTo(applicationContext);
//        }
//    }
}
