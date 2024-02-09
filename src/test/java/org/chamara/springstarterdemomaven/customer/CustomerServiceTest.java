package org.chamara.springstarterdemomaven.customer;

import org.chamara.springstarterdemomaven.exception.DuplicateResourceException;
import org.chamara.springstarterdemomaven.exception.RequestValidationException;
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
    void itWillThrowWhenIdIsNotFound() {
        // when
        when(customerDoa.existsCustomerById(1L)).thenReturn(false);
        // then
        assertThrows(ResourceNotFoundException.class, () -> underTest.deleteCustomerById(1L));
        verify(customerDoa, never()).deleteCustomerById(1L);
    }

    @Test
    void itShouldUpdateCustomerById() {
        // given
        Long id = 1L;
        String actualName = "Updated Name";
        String actualEmail = "updated@gmail.com";
        Integer actualAge = 22;

        CustomerUpdateRequest update = new CustomerUpdateRequest(actualName, actualEmail, actualAge);

        Customer existingCustomer = new Customer("Old Name", "old@gmail.com", 21);
        when(customerDoa.selectCustomerById(id)).thenReturn(Optional.of(existingCustomer));
        when(customerDoa.existsCustomerWithEmail(actualEmail)).thenReturn(false);

        // when
        underTest.updateCustomerById(id, update);

        // then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDoa).updateCustomer(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();
        assertEquals(actualName, capturedCustomer.getName());
        assertEquals(actualEmail, capturedCustomer.getEmail());
        assertEquals(actualAge, capturedCustomer.getAge());
    }

    @Test
    void itShouldOnlyUpdateNameUpdateCustomerById() {
        // given
        Long id = 1L;
        String actualName = "Updated Name";

        CustomerUpdateRequest update = new CustomerUpdateRequest(actualName, null, null);

        Customer existingCustomer = new Customer("Old Name", "old@gmail.com", 21);
        when(customerDoa.selectCustomerById(id)).thenReturn(Optional.of(existingCustomer));

        // when
        underTest.updateCustomerById(id, update);

        // then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDoa).updateCustomer(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();
        assertEquals(actualName, capturedCustomer.getName());
        assertEquals(existingCustomer.getEmail(), capturedCustomer.getEmail());
        assertEquals(existingCustomer.getAge(), capturedCustomer.getAge());
    }

    @Test
    void itShouldOnlyUpdateAgeUpdateCustomerById() {
        // given
        Long id = 1L;
        Integer actualAge = 22;
        CustomerUpdateRequest update = new CustomerUpdateRequest(null, null, actualAge);

        Customer existingCustomer = new Customer("Old Name", "old@gmail.com", 21);
        when(customerDoa.selectCustomerById(id)).thenReturn(Optional.of(existingCustomer));

        // when
        underTest.updateCustomerById(id, update);

        // then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDoa).updateCustomer(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();
        assertEquals(existingCustomer.getName(), capturedCustomer.getName());
        assertEquals(existingCustomer.getEmail(), capturedCustomer.getEmail());
        assertEquals(actualAge, capturedCustomer.getAge());
    }

    @Test
    void itShouldOnlyUpdateEmailUpdateCustomerById() {
        // given
        Long id = 1L;
        String actualEmail = "updated@gmail.com";
        CustomerUpdateRequest update = new CustomerUpdateRequest(null, actualEmail, null);

        Customer existingCustomer = new Customer("Old Name", "old@gmail.com", 21);
        when(customerDoa.selectCustomerById(id)).thenReturn(Optional.of(existingCustomer));
        when(customerDoa.existsCustomerWithEmail(actualEmail)).thenReturn(false);
        // when
        underTest.updateCustomerById(id, update);

        // then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDoa).updateCustomer(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();
        assertEquals(existingCustomer.getName(), capturedCustomer.getName());
        assertEquals(actualEmail, capturedCustomer.getEmail());
        assertEquals(existingCustomer.getAge(), capturedCustomer.getAge());
    }

    @Test
    void itWillThrowWhenEmailAlreadyExistsUpdateCustomerById() {
        // given
        Long id = 1L;
        String actualName = "Updated Name";
        String actualEmail = "updated@gmail.com";
        Integer actualAge = 22;

//        CustomerUpdateRequest update = mock(CustomerUpdateRequest.class);
//        when(update.actualName()).thenReturn(actualName);
//        when(update.actualEmail()).thenReturn(actualEmail);
//        when(update.actualAge()).thenReturn(actualAge);
        CustomerUpdateRequest update = new CustomerUpdateRequest(actualName, actualEmail, actualAge);

        Customer existingCustomer = new Customer("Old Name", "old@gmail.com", 21);
        when(customerDoa.selectCustomerById(id)).thenReturn(Optional.of(existingCustomer));
        when(customerDoa.existsCustomerWithEmail(actualEmail)).thenReturn(true);

        // when
        assertThrows(DuplicateResourceException.class, () -> underTest.updateCustomerById(id, update));
        verify(customerDoa, never()).updateCustomer(any());

    }

    @Test
    void itWillThrowWhenNoChangeDetectedUpdateCustomerById() {
        // given
        Long id = 1L;
        String actualName = "Updated Name";
        String actualEmail = "updated@gmail.com";
        Integer actualAge = 22;

//        CustomerUpdateRequest update = mock(CustomerUpdateRequest.class);
//        when(update.actualName()).thenReturn(actualName);
//        when(update.actualEmail()).thenReturn(actualEmail);
//        when(update.actualAge()).thenReturn(actualAge);
        CustomerUpdateRequest update = new CustomerUpdateRequest(actualName, actualEmail, actualAge);

        Customer existingCustomer = new Customer(actualName, actualEmail, actualAge);
        when(customerDoa.selectCustomerById(id)).thenReturn(Optional.of(existingCustomer));
//        when(customerDoa.existsCustomerWithEmail(actualEmail)).thenReturn(false);

        // when
        assertThrows(RequestValidationException.class, () -> underTest.updateCustomerById(id, update));
        verify(customerDoa, never()).updateCustomer(any());
    }
}