package org.chamara.springstarterdemomaven.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDoa {

    private final JdbcTemplate jdbcTemplate;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        var sql = """
                SELECT id, name, email, age
                FROM customer
                """;
        RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
            var id = resultSet.getLong("id");
            var name = resultSet.getString("name");
            var email = resultSet.getString("email");
            var age = resultSet.getInt("age");
            return new Customer(id, name, email, age);
        };
        List<Customer> query = jdbcTemplate.query(sql, customerRowMapper);
        return query;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
                INSERT INTO customer (name, email, age)
                VALUES (?, ?, ?)
                """;
        int result = jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getAge());
        System.out.println(result + " rows affected");
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        return false;
    }

    @Override
    public void deleteCustomerById(Integer id) {

    }

    @Override
    public boolean existsCustomerById(Integer id) {
        return false;
    }

    @Override
    public void updateCustomer(Customer customer) {

    }
}
