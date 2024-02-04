package org.chamara.springstarterdemomaven.customer;

import org.chamara.springstarterdemomaven.exception.ResourceNotFound;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .orElseThrow(() -> new ResourceNotFound("Customer with id [%s] not found ".formatted(id)));
    }

}
