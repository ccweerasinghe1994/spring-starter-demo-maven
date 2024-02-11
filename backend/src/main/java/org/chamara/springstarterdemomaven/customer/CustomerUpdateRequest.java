package org.chamara.springstarterdemomaven.customer;

public record CustomerUpdateRequest(
        String name,
        String email,
        Integer age
) {
}
