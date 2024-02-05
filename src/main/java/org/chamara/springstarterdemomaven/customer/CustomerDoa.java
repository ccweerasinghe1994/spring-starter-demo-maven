package org.chamara.springstarterdemomaven.customer;

import java.util.List;
import java.util.Optional;

interface CustomerDoa {
    List<Customer> selectAllCustomers();

    Optional<Customer> selectCustomerById(Integer id);

    void insertCustomer(Customer customer);

    boolean existsCustomerWithEmail(String email);

    void deleteCustomerById(Integer id);

    boolean existsCustomerById(Integer id);

    void updateCustomerById(Customer customer);
}
