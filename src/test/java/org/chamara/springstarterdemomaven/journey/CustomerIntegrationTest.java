package org.chamara.springstarterdemomaven.journey;

import com.github.javafaker.Faker;
import org.chamara.springstarterdemomaven.customer.CustomerRegistrationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CustomerIntegrationTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    void canRegisterCustomer() {
        // create a customer registration request
        String name = Faker.instance().name().fullName();
        String email = Faker.instance().name().lastName() + UUID.randomUUID() + "@example.com";
        int age = Faker.instance().number().numberBetween(18, 100);
        CustomerRegistrationRequest customerRegistrationRequest = new CustomerRegistrationRequest(name, email, age);
//        lets send the request to the server
        webClient.post()
                .uri("api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)  // we are expecting a json response
                .contentType(MediaType.APPLICATION_JSON)  // we are sending a json request
                .body(Mono.just(customerRegistrationRequest), CustomerRegistrationRequest.class) // the request body
                .exchange() // send the request
                .expectStatus() // we are expecting a status
                .isOk(); // we are expecting a 200 ok status

    }
}
