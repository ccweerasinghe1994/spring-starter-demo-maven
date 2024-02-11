package org.chamara.springstarterdemomaven.customer;

import org.chamara.springstarterdemomaven.AbstractTestContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
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
        String emailAddress = FAKER.internet().emailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(FAKER.name().firstName(), emailAddress, FAKER.number().numberBetween(0, 100));
        underTest.insertCustomer(customer);
        // when
        Long customerId = underTest.selectAllCustomers().stream()
                .filter(c -> c.getEmail().equals(emailAddress))
                .findFirst()
                .map(Customer::getId)
                .orElseThrow();

        Optional<Customer> customerById = underTest.selectCustomerById(customerId);

        // then
        Assertions.assertTrue(customerById.isPresent());
        Assertions.assertEquals(customer.getEmail(), customerById.get().getEmail());
        Assertions.assertEquals(customer.getName(), customerById.get().getName());
        Assertions.assertEquals(customer.getAge(), customerById.get().getAge());


    }

    @Test
    void willReturnWhenCustomerNotFound() {
        // given
        Long customerId = -1L;
        // when
        Optional<Customer> actual = underTest.selectCustomerById(customerId);
        // then
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    void itShouldInsertCustomer() {
        // given
        Customer customer = new Customer(FAKER.name().firstName(), FAKER.internet().emailAddress() + "-" + UUID.randomUUID(), FAKER.number().numberBetween(0, 100));
        // when
        underTest.insertCustomer(customer);
        // then
        List<Customer> customers = underTest.selectAllCustomers();
        Assertions.assertTrue(customers.stream().anyMatch(c -> c.getEmail().equals(customer.getEmail())));
    }

    @Test
    void itShouldExistsCustomerWithEmail() {
        // given
        String emailAddress = FAKER.internet().emailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(FAKER.name().firstName(), emailAddress, FAKER.number().numberBetween(0, 100));
        // when
        underTest.insertCustomer(customer);
        // then
        Assertions.assertTrue(underTest.existsCustomerWithEmail(emailAddress));
    }

    @Test
    void itShouldDeleteCustomerById() {
        // given
        String emailAddress = FAKER.internet().emailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(FAKER.name().firstName(), emailAddress, FAKER.number().numberBetween(0, 100));
        underTest.insertCustomer(customer);
        // when
        Long customerId = underTest.selectAllCustomers().stream()
                .filter(c -> c.getEmail().equals(emailAddress))
                .findFirst()
                .map(Customer::getId)
                .orElseThrow();
        underTest.deleteCustomerById(customerId);
        // then
        List<Customer> customers = underTest.selectAllCustomers();
        Assertions.assertTrue(customers.stream().noneMatch(c -> c.getEmail().equals(emailAddress)));
    }

    @Test
    void itShouldExistsCustomerById() {
        // given
        String emailAddress = FAKER.internet().emailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(FAKER.name().firstName(), emailAddress, FAKER.number().numberBetween(0, 100));
        underTest.insertCustomer(customer);
        // when
        Long customerId = underTest.selectAllCustomers().stream()
                .filter(c -> c.getEmail().equals(emailAddress))
                .findFirst()
                .map(Customer::getId)
                .orElseThrow();
        // then
        Assertions.assertTrue(underTest.existsCustomerById(customerId));
    }

    @Test
    void itShouldUpdateCustomer() {
        // given
        String emailAddress = FAKER.internet().emailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(FAKER.name().firstName(), emailAddress, FAKER.number().numberBetween(0, 100));
        underTest.insertCustomer(customer);
        // when
        Long customerId = underTest.selectAllCustomers().stream()
                .filter(c -> c.getEmail().equals(emailAddress))
                .findFirst()
                .map(Customer::getId)
                .orElseThrow();
        Customer actual = new Customer(customerId, FAKER.name().firstName(), FAKER.internet().emailAddress() + "-" + UUID.randomUUID(), FAKER.number().numberBetween(0, 100));
        underTest.updateCustomer(actual);
        // then
        Optional<Customer> customerById = underTest.selectCustomerById(customerId);
        Assertions.assertTrue(customerById.isPresent());
        Assertions.assertEquals(actual.getEmail(), customerById.get().getEmail());
        Assertions.assertEquals(actual.getName(), customerById.get().getName());
        Assertions.assertEquals(actual.getAge(), customerById.get().getAge());

    }
}