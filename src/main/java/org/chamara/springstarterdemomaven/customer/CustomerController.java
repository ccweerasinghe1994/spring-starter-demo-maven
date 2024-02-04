package org.chamara.springstarterdemomaven.customer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {
    private final CustomerDoa customerService;

    public CustomerController(@Qualifier("jpa") CustomerDoa customerService) {
        this.customerService = customerService;
    }

    @GetMapping("api/v1/customers")
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping("api/v1/customer/{id}")
    public Optional<Customer> getCustomerById(@PathVariable("id") Integer id) {
        return customerService.getCustomerById(id);
    }
}
