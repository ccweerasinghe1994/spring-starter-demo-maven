package org.chamara.springstarterdemomaven;

import com.github.javafaker.Faker;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

@Testcontainers
public abstract class AbstractTestContainer {

    @BeforeAll
    static void beforeAll() {
        Flyway flyway = Flyway.configure().dataSource(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword()
        ).load();
        flyway.migrate();
        System.out.println("Migrations applied successfully");
    }

    private static final String POSTGRES_IMAGE = "postgres:16.1";
    private static final String POSTGRES_USERNAME = "chamara";
    private static final String POSTGRES_PASSWORD = "password";
    private static final String POSTGRES_DB_NAME = "test-container-db";


    @Container
    protected static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(POSTGRES_IMAGE)
            .withUsername(POSTGRES_USERNAME)
            .withPassword(POSTGRES_PASSWORD)
            .withDatabaseName(POSTGRES_DB_NAME);

    @DynamicPropertySource
    static void setPostgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    private static DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder
                .create()
                .driverClassName(postgreSQLContainer.getDriverClassName());
        dataSourceBuilder.url(postgreSQLContainer.getJdbcUrl());
        dataSourceBuilder.username(postgreSQLContainer.getUsername());
        dataSourceBuilder.password(postgreSQLContainer.getPassword());
        return dataSourceBuilder.build();
    }

    protected JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }

    protected final Faker FAKER = new Faker();
}
