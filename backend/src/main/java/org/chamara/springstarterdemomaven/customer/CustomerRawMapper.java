package org.chamara.springstarterdemomaven.customer;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CustomerRawMapper implements RowMapper<Customer> {

    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        var id = rs.getLong("id");
        var name = rs.getString("name");
        var email = rs.getString("email");
        var age = rs.getInt("age");
        return new Customer(id, name, email, age);
    }
}
