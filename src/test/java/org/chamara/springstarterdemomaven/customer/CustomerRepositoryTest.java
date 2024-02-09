package org.chamara.springstarterdemomaven.customer;

import org.chamara.springstarterdemomaven.AbstractTestContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest extends AbstractTestContainer {
    @Autowired
    private CustomerRepository underTest;

    @BeforeEach
    void setUp() {
    }

    @Test
    void itShouldExistsCustomerByEmail() {
        // given
        String emailAddress = FAKER.internet().emailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(FAKER.name().firstName(), emailAddress, FAKER.number().numberBetween(0, 100));
        underTest.save(customer);
        // when
        boolean actual = underTest.existsCustomerByEmail(emailAddress);

        // then
        Assertions.assertTrue(actual);
    }

    @Test
    void itShouldExistsCustomerByEmailFailWhenEmailIsNotFound() {
        // given
        String emailAddress = FAKER.internet().emailAddress() + "-" + UUID.randomUUID();
        // when
        boolean actual = underTest.existsCustomerByEmail(emailAddress);

        // then
        Assertions.assertFalse(actual);
    }

    @Test
    void itShouldExistsCustomerById() {
        // given
        String emailAddress = FAKER.internet().emailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(FAKER.name().firstName(), emailAddress, FAKER.number().numberBetween(0, 100));
        underTest.save(customer);
        // when
        Long customerId = underTest.findAll().stream()
                .filter(c -> c.getEmail().equals(emailAddress))
                .findFirst()
                .map(Customer::getId)
                .orElseThrow();

        boolean actual = underTest.existsCustomerById(Math.toIntExact(customerId));

        // then
        Assertions.assertTrue(actual);
    }

    @Test
    void itShouldExistsCustomerByIdFailWhenIdIsNotFound() {
        // given
        Long customerId = -1L;

        boolean actual = underTest.existsCustomerById(Math.toIntExact(customerId));

        // then
        Assertions.assertFalse(actual);
    }
}