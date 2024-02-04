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
        customers.add(new Customer(1, "Chamara", "abc@123.com", 30));
        customers.add(new Customer(2, "Kasun", "abcd@123.com", 25));
        customers.add(new Customer(3, "Nimal", "abcde@123.com", 40));

    }
    
    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return customers.stream().filter(c -> c.getId().equals(id)).findFirst();
    }
}
