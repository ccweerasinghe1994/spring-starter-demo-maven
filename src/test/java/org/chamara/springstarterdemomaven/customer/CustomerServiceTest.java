package org.chamara.springstarterdemomaven.customer;

import org.chamara.springstarterdemomaven.exception.DuplicateResourceException;
import org.chamara.springstarterdemomaven.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    private CustomerDoa customerDoa;
    private CustomerService underTest;
    
    @BeforeEach
    void setUp() {
        underTest = new CustomerService(customerDoa);
    }

    @Test
    void itShouldGetCustomers() {
        // when
        underTest.getCustomers();
        // then
        verify(customerDoa).selectAllCustomers();
    }

    @Test
    void itShouldGetCustomerById() {
        // given
        // when
        Customer customer = new Customer("name", "email", 12);
        when(customerDoa.selectCustomerById(1L)).thenReturn(Optional.of(customer));
        Customer actual = underTest.getCustomerById(1L);
        // then
        verify(customerDoa).selectCustomerById(1L);
        assertEquals(customer.getEmail(), actual.getEmail());
    }

    @Test
    void willThrowWhenShouldGetCustomerByIdReturnOptionalEmpty() {
        // given
        long id = 1L;
        when(customerDoa.selectCustomerById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> underTest.getCustomerById(id), "Customer with id [%s] not found ".formatted(id));
    }

    @Test
    void itShouldAddCustomer() {
        //
        String email = "abc@gmail.com";
        when(customerDoa.existsCustomerWithEmail(email)).thenReturn(false);
        // when
        CustomerRegistrationRequest customerRegistrationRequest = new CustomerRegistrationRequest("name", email, 12);
        underTest.addCustomer(customerRegistrationRequest);
        // then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDoa).insertCustomer(customerArgumentCaptor.capture());

        Customer CapturedCustomer = customerArgumentCaptor.getValue();
        assertNull(CapturedCustomer.getId());
        assertEquals(customerRegistrationRequest.name(), CapturedCustomer.getName());
        assertEquals(customerRegistrationRequest.email(), CapturedCustomer.getEmail());
        assertEquals(customerRegistrationRequest.age(), CapturedCustomer.getAge());

    }

    @Test
    void itShouldNotAddCustomerWhenAlreadyExistsEmailAddress() {
        //
        String email = "abc@gmail.com";
        when(customerDoa.existsCustomerWithEmail(email)).thenReturn(true);
        // when
        assertThrows(
                DuplicateResourceException.class,
                () -> underTest.addCustomer(new CustomerRegistrationRequest("name", email, 12)),
                "Email already taken"
        );
        verify(customerDoa, never()).insertCustomer(any());
    }

    @Test
    void itShouldDeleteCustomerById() {
        // given

        // when
        when(customerDoa.existsCustomerById(1L)).thenReturn(true);
        underTest.deleteCustomerById(1L);
        // then
        verify(customerDoa).deleteCustomerById(1L);
    }

    @Test
    void itShouldThrowWhenIdIsNotFound() {

        // when
        when(customerDoa.existsCustomerById(1L)).thenReturn(false);
        // then
        assertThrows(ResourceNotFoundException.class, () -> underTest.deleteCustomerById(1L));

    }

    @Test
    void itShouldUpdateCustomerById() {
        // given
        Customer customer = new Customer("name", "email", 12);
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest("name", "email", 12);
        // when
        underTest.updateCustomerById(1L, customerUpdateRequest);
        // then
        verify(customerDoa).updateCustomer(customer);
    }
}