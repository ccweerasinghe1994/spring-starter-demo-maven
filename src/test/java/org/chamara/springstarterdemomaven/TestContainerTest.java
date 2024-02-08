package org.chamara.springstarterdemomaven;

import org.chamara.springstarterdemomaven.customer.CustomerJDBCDataAccessService;
import org.chamara.springstarterdemomaven.customer.CustomerRawMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;


public class TestContainerTest extends AbstractTestContainer {

    private CustomerJDBCDataAccessService underTest;


    private final CustomerRawMapper customerRawMapper = new CustomerRawMapper();

    @BeforeEach
    void setUp() {
        underTest = new CustomerJDBCDataAccessService(new JdbcTemplate(), customerRawMapper);
    }

    @Test
    void canStartPostgresDB() {
        Assertions.assertNotNull(postgreSQLContainer);
        Assertions.assertTrue(postgreSQLContainer.isCreated());
        Assertions.assertTrue(postgreSQLContainer.isRunning());
        // given
        // when
        // then
    }

}
