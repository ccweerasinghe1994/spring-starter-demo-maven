package org.chamara.springstarterdemomaven.customer;

public record CustomerRegistrationRequest(
        String name,
        String email,
        Integer age
) {
}
