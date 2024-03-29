package org.chamara.springstarterdemomaven.customer;

import java.util.List;
import java.util.Optional;

interface CustomerDoa {
    List<Customer> selectAllCustomers();

    Optional<Customer> selectCustomerById(Long id);

    void insertCustomer(Customer customer);

    boolean existsCustomerWithEmail(String email);

    void deleteCustomerById(Long id);

    boolean existsCustomerById(Long id);

    void updateCustomer(Customer customer);
}
