package org.chamara.springstarterdemomaven.customer;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerDoaService customerDoaService;

    public CustomerService(CustomerDoaService customerDoaService) {
        this.customerDoaService = customerDoaService;
    }

    public List<Customer> getCustomers() {
        return customerDoaService.getCustomers();
    }

    public Customer getCustomerById(Integer id) {
        return customerDoaService.getCustomerById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }

}
