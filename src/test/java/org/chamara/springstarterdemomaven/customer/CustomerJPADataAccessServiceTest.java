package org.chamara.springstarterdemomaven.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class CustomerJPADataAccessServiceTest {

    private CustomerJPADataAccessService underTest;
    private AutoCloseable autoCloseable;
    
    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJPADataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void itShouldSelectAllCustomers() {
        // when
        underTest.selectAllCustomers();
        // then
        verify(customerRepository).findAll();
    }

    @Test
    void itShouldSelectCustomerById() {
        // given
        Long id = 1L;
        // when
        underTest.selectCustomerById(id);
        // then
        verify(customerRepository).findById(Math.toIntExact(id));
    }

    @Test
    void itShouldInsertCustomer() {
        // given
        Customer customer = new Customer();
        customer.setId(1l);
        customer.setEmail("aasa");
        customer.setName("asas");
        customer.setAge(12);
        // when
        underTest.insertCustomer(customer);
        // then
        verify(customerRepository).save(customer);
    }

    @Test
    void itShouldExistsCustomerWithEmail() {
        // given
        String email = "email";
        // when
        underTest.existsCustomerWithEmail(email);
        // then
        verify(customerRepository).existsCustomerByEmail(email);
    }

    @Test
    void itShouldDeleteCustomerById() {
        // given
        Long id = 1L;
        // when
        underTest.deleteCustomerById(id);
        // then
        verify(customerRepository).deleteById(Math.toIntExact(id));
    }

    @Test
    void itShouldExistsCustomerById() {
        // given
        Long id = 1L;
        // when
        underTest.existsCustomerById(id);
        // then
        verify(customerRepository).existsCustomerById(Math.toIntExact(id));
    }

    @Test
    void itShouldUpdateCustomer() {
        // given
        Customer customer = new Customer();
        // when
        underTest.updateCustomer(customer);
        // then
        verify(customerRepository).save(customer);
    }
}