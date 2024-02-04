package org.chamara.springstarterdemomaven.customer;

import java.util.List;
import java.util.Optional;

interface CustomerDoa {
    List<Customer> getCustomers();

    Optional<Customer> getCustomerById(Integer id);

}
