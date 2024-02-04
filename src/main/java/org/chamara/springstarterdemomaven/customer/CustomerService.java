package org.chamara.springstarterdemomaven.customer;

import org.chamara.springstarterdemomaven.exception.ResourceNotFound;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerDoa customerDoa;

    public CustomerService(@Qualifier("jpa") CustomerDoa customerDoa) {
        this.customerDoa = customerDoa;
    }

    public List<Customer> getCustomers() {
        return customerDoa.selectAllCustomers();
    }

    public Customer getCustomerById(Integer id) {
        return customerDoa.selectCustomerById(id)
                .orElseThrow(() -> new ResourceNotFound("Customer with id [%s] not found ".formatted(id)));
    }

}
