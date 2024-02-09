package org.chamara.springstarterdemomaven.customer;

import org.chamara.springstarterdemomaven.exception.DuplicateResourceException;
import org.chamara.springstarterdemomaven.exception.RequestValidationException;
import org.chamara.springstarterdemomaven.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerDoa customerDoa;

    public CustomerService(@Qualifier("jdbc") CustomerDoa customerDoa) {
        this.customerDoa = customerDoa;
    }

    public List<Customer> getCustomers() {
        return customerDoa.selectAllCustomers();
    }

    public Customer getCustomerById(Long id) {
        return customerDoa.selectCustomerById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with id [%s] not found ".formatted(id)));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        String email = customerRegistrationRequest.email();

        if (customerDoa.existsCustomerWithEmail(email)) {
            throw new DuplicateResourceException("Email already taken");
        }

        String name = customerRegistrationRequest.name();
        Integer age = customerRegistrationRequest.age();

        Customer customer = new Customer(name, email, age);
        customerDoa.insertCustomer(customer);
    }

    public void deleteCustomerById(Long id) {
        if (!customerDoa.existsCustomerById(id)) {
            throw new ResourceNotFoundException("Customer with id [%s] not found ".formatted(id));
        }
        customerDoa.deleteCustomerById(id);
    }

    public void updateCustomerById(Long id, CustomerUpdateRequest update) {
        Customer customer = getCustomerById(id);

        boolean changed = false;

        if (update.name() != null && !update.name().equals(customer.getName())) {
            customer.setName(update.name());
            changed = true;
        }

        if (update.email() != null && !update.email().equals(customer.getEmail())) {
            if (customerDoa.existsCustomerWithEmail(update.email())) {
                throw new DuplicateResourceException("Email already taken");
            }
            customer.setEmail(update.email());
            changed = true;
        }

        if (update.age() != null && !update.age().equals(customer.getAge())) {
            customer.setAge(update.age());
            changed = true;
        }

        if (changed) {
            customerDoa.updateCustomer(customer);
        } else {
            throw new RequestValidationException("No changes detected");
        }
    }

}
