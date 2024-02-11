package org.chamara.springstarterdemomaven.customer;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping()
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping("{id}")
    public Customer getCustomerById(@PathVariable("id") Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping()
    public void addCustomer(@RequestBody CustomerRegistrationRequest customer) {
        customerService.addCustomer(customer);
    }

    @DeleteMapping("{id}")
    public void deleteCustomerById(@PathVariable("id") Long id) {
        customerService.deleteCustomerById(id);
    }

    @PutMapping("{id}")
    public void updateCustomerById(@PathVariable("id") Long id, @RequestBody CustomerUpdateRequest customer) {
        customerService.updateCustomerById(id, customer);
    }
}
