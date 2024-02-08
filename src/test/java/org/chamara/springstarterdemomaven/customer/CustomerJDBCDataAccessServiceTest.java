package org.chamara.springstarterdemomaven.customer;

import org.chamara.springstarterdemomaven.AbstractTestContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

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
        Customer customer = new Customer(FAKER.name().firstName(), FAKER.internet().emailAddress() + "-" + UUID.randomUUID(), FAKER.number().numberBetween(0, 100));
        underTest.insertCustomer(customer);
        // when
        List<Customer> customers = underTest.selectAllCustomers();

        // then
        Assertions.assertTrue(customers.size() > 0);
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