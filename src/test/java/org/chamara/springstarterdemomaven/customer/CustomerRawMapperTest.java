package org.chamara.springstarterdemomaven.customer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerRawMapperTest {

    @Test
    void itShouldMapRow() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("name")).thenReturn("chamara");
        when(resultSet.getString("email")).thenReturn("abc@123.com");
        when(resultSet.getInt("age")).thenReturn(1);
        // given
        CustomerRawMapper underTest = new CustomerRawMapper();
        // when
        Customer actual = underTest.mapRow(resultSet, 1);
        // then
        Customer expected = new Customer(1L, "chamara", "abc@123.com", 1);
        Assertions.assertEquals(expected, actual);

    }
}