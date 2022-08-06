package com.example.test.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.Collection;

@Slf4j
@Configuration
@ConditionalOnProperty(name = "spring.show.config", havingValue = "true")
public class ShowPropertyConfig {

    @EventListener
    public void printActiveProperties(ContextRefreshedEvent contextRefreshedEvent) {
        ConfigurableEnvironment environment = (ConfigurableEnvironment) contextRefreshedEvent.getApplicationContext().getEnvironment();
        printActiveProperties(environment);
    }

    private void printActiveProperties(ConfigurableEnvironment environment) {
        System.out.println("************************* ACTIVE APP PROPERTIES ******************************");
        environment.getPropertySources()
                .stream()
                .filter(it -> it instanceof MapPropertySource && it.getName().contains("application"))
                .map(cast -> (MapPropertySource) cast)
                .map(val -> val.getSource().keySet())
                .flatMap(Collection::stream)
                .distinct()
                .sorted()
                .forEach(key -> {
                    try {
                        if (!key.contains("password")) {
                            log.info("{} = {}", key, environment.getProperty(key));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        System.out.println("******************************************************************************");
    }
}
