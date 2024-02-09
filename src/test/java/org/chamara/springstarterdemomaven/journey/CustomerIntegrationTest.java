package org.chamara.springstarterdemomaven.journey;

import com.github.javafaker.Faker;
import org.chamara.springstarterdemomaven.customer.Customer;
import org.chamara.springstarterdemomaven.customer.CustomerRegistrationRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CustomerIntegrationTest {

    @Autowired
    private WebTestClient webClient;
    private static final String CUSTOMER_URI = "api/v1/customers";

    @Test
    void canRegisterCustomer() {
        // create a customer registration request
        String name = Faker.instance().name().fullName();
        String email = Faker.instance().name().lastName() + UUID.randomUUID() + "@example.com";
        int age = Faker.instance().number().numberBetween(18, 100);
        CustomerRegistrationRequest customerRegistrationRequest = new CustomerRegistrationRequest(name, email, age);

        // let's send the request to the server
        webClient.post()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)  // we are expecting a json response
                .contentType(MediaType.APPLICATION_JSON)  // we are sending a json request
                .body(Mono.just(customerRegistrationRequest), CustomerRegistrationRequest.class) // the request body
                .exchange() // send the request
                .expectStatus() // we are expecting a status
                .isOk(); // we are expecting a 200 ok status

        // get all customers

        Customer expectedCustomer = new Customer(name, email, age);

        List<Customer> responseBody = webClient.get()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)  // we are expecting a json response
                .exchange() // send the request
                .expectStatus() // we are expecting a status
                .isOk() // we are expecting a 200 ok status
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                }) // we are expecting a list of customers
                .returnResult() // get the response
                .getResponseBody(); // get the response body
//        check if the customer is in the list ignoring the id
        assert responseBody != null;
        Customer savedCustomer = responseBody.stream().filter(customer -> Objects.equals(customer.getEmail(), email)).findFirst().orElseThrow(() -> new RuntimeException("Customer not found"));
        Assertions.assertEquals(expectedCustomer.getName(), savedCustomer.getName());
        Assertions.assertEquals(expectedCustomer.getEmail(), savedCustomer.getEmail());
        Assertions.assertEquals(expectedCustomer.getAge(), savedCustomer.getAge());
        

//        get a customer by id
        expectedCustomer.setId(savedCustomer.getId());

        webClient.get()
                .uri(CUSTOMER_URI + "/{id}", savedCustomer.getId())
                .accept(MediaType.APPLICATION_JSON)  // we are expecting a json response
                .exchange() // send the request
                .expectStatus() // we are expecting a status
                .isOk() // we are expecting a 200 ok status
                .expectBody(new ParameterizedTypeReference<Customer>() {
                }) // we are expecting a list of customers
                .isEqualTo(expectedCustomer);
    }
}
