package org.chamara.springstarterdemomaven.customer;

import org.chamara.springstarterdemomaven.AbstractTestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerJDBCDataAccessServiceTest extends AbstractTestContainer {
    private CustomerJDBCDataAccessService underTest;


    private final CustomerRawMapper customerRawMapper = new CustomerRawMapper();

    @BeforeEach
    void setUp() {
        underTest = new CustomerJDBCDataAccessService(getJdbcTemplate(), customerRawMapper);
    }

    @Test
    void itShouldSelectAllCustomers() {
        // given
        // when
        // then
    }

    @Test
    void itShouldSelectCustomerById() {
        // given
        // when
        // then
    }

    @Test
    void itShouldInsertCustomer() {
        // given
        // when
        // then
    }

    @Test
    void itShouldExistsCustomerWithEmail() {
        // given
        // when
        // then
    }

    @Test
    void itShouldDeleteCustomerById() {
        // given
        // when
        // then
    }

    @Test
    void itShouldExistsCustomerById() {
        // given
        // when
        // then
    }

    @Test
    void itShouldUpdateCustomer() {
        // given
        // when
        // then
    }
}