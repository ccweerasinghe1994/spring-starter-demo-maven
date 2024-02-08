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
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public void deleteCustomerById(Integer id) {
        var sql = """
                DELETE FROM customer
                WHERE id = ?
                """;
        int result = jdbcTemplate.update(sql, id);
        System.out.println("deleted " + result + " rows");
    }

    @Override
    public boolean existsCustomerById(Integer id) {
        var sql = """
                SELECT COUNT(*) = 1
                FROM customer
                WHERE id = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void updateCustomer(Customer update) {

        if (update.getName() != null) {
            var sql = """
                    UPDATE customer
                    SET name = ?
                    WHERE id = ?
                    """;
            int result = jdbcTemplate.update(sql, update.getName(), update.getId());
            System.out.println("Update Customer Name: " + result + " rows affected");
        }

        if (update.getAge() != null) {
            var sql = """
                    UPDATE customer
                    SET age = ?
                    WHERE id = ?
                    """;
            int result = jdbcTemplate.update(sql, update.getAge(), update.getId());
            System.out.println("Update Customer Age: " + result + " rows affected");
        }

        var sql = """
                UPDATE customer
                SET name = ?, email = ?, age = ?
                WHERE id = ?
                """;
        int result = jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getAge(), customer.getId());
    }
}
