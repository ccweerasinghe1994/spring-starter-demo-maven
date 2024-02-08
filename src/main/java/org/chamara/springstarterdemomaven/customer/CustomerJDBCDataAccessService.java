package org.chamara.springstarterdemomaven.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDoa {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRawMapper customerRawMapper;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate, CustomerRawMapper customerRawMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRawMapper = customerRawMapper;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        var sql = """
                SELECT id, name, email, age
                FROM customer
                """;
        return jdbcTemplate.query(sql, customerRawMapper);
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        var sql = """
                SELECT id, name, email, age
                FROM customer
                WHERE id = ?
                """;
        return jdbcTemplate.query(sql, customerRawMapper, id)
                .stream()
                .findFirst();

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
        var sql = """
                SELECT COUNT(email)
                FROM customer
                WHERE email = ?
                """;
        List<Boolean> query = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getBoolean(1), email);
        return query.getFirst();
    }

    @Override
    public void deleteCustomerById(Integer id) {
        var sql = """
                DELETE FROM customer
                WHERE id = ?
                """;
        int result = jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsCustomerById(Integer id) {
        var sql = """
                SELECT COUNT(*) = 1
                FROM customer
                WHERE id = ?
                """;
        List<Boolean> query = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getBoolean(1), id);
        return query.getFirst();
    }

    @Override
    public void updateCustomer(Customer customer) {
        var sql = """
                UPDATE customer
                SET name = ?, email = ?, age = ?
                WHERE id = ?
                """;
        int result = jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getAge(), customer.getId());
    }
}
