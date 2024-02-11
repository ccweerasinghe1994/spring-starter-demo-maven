package org.chamara.springstarterdemomaven.customer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("list")
public class CustomerDoaService implements CustomerDoa {
    private static List<Customer> customers;

    static {
        customers = new ArrayList<>();
        customers.add(new Customer(1l, "Chamara", "abc@123.com", 30));
        customers.add(new Customer(2l, "Kasun", "abcd@123.com", 25));
        customers.add(new Customer(3l, "Nimal", "abcde@123.com", 40));

    }

    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Long id) {
        return customers.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        return customers.stream().anyMatch(c -> c.getEmail().equals(email));
    }

    @Override
    public void deleteCustomerById(Long id) {
        customers.removeIf(c -> c.getId().equals(id));
    }

    @Override
    public boolean existsCustomerById(Long id) {
        return customers.stream().anyMatch(c -> c.getId().equals(id));
    }

    @Override
    public void updateCustomer(Customer customer) {
        customers.add(customer);
    }
}
