package com.example.test.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;
import java.util.Objects;

@Slf4j
@Configuration
public class DataSourceConfiguration {

    private static final String REGISTRY_URL_PREFIX = System.getenv("TESTCONTAINERS_HUB_IMAGE_NAME_PREFIX");
    private static final PostgreSQLContainer<?> PG_CONTAINER;

    static {
        PG_CONTAINER = Objects.isNull(REGISTRY_URL_PREFIX)
                ? new PostgreSQLContainer<>("postgres:12.7")
                : new PostgreSQLContainer<>(DockerImageName.parse(REGISTRY_URL_PREFIX + "testcontainers/postgresql:12.7")
                        .asCompatibleSubstituteFor("postgres:12.7"));

        PG_CONTAINER
                .withDatabaseName("db")
                .withUsername("postgres")
                .withPassword("postgres")
                .withInitScript("postgresql.sql")
                .waitingFor(Wait.forListeningPort())
                .start();
    }

    @Bean
    public DataSource dataSource() {
        PostgreSQLContainer<?> container = PG_CONTAINER;

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:" + container.getFirstMappedPort() + "/" + container.getDatabaseName());
        config.setUsername(container.getUsername());
        config.setPassword(container.getPassword());
        config.setMaximumPoolSize(1);
        log.info("URL test db = {}", container.getJdbcUrl());
        return new HikariDataSource(config);
    }
}
