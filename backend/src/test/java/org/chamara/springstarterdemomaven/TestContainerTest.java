package org.chamara.springstarterdemomaven;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class TestContainerTest extends AbstractTestContainer {

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
