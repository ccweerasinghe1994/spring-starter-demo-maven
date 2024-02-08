package org.chamara.springstarterdemomaven;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class TestContainerTest {

    private static final String POSTGRES_IMAGE = "postgres:16.1";
    private static final String POSTGRES_USERNAME = "chamara";
    private static final String POSTGRES_PASSWORD = "password";
    private static final String POSTGRES_DB_NAME = "test-container-db";


    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(POSTGRES_IMAGE)
            .withUsername(POSTGRES_USERNAME)
            .withPassword(POSTGRES_PASSWORD)
            .withDatabaseName(POSTGRES_DB_NAME);

    @Test
    void canStartPostgresDB() {
        Assertions.assertNotNull(postgreSQLContainer);
        Assertions.assertTrue(postgreSQLContainer.isCreated());
        Assertions.assertTrue(postgreSQLContainer.isRunning());
        // given
        // when
        // then
    }

    @Test
    void canApplyDBMigrationsWithFlyWay() {
        // given
        Flyway flyway = Flyway.configure().dataSource(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword()
        ).load();
        flyway.migrate();
        System.out.println("Migrations applied successfully");
        // when
        // then
    }
}
