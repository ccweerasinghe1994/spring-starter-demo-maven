package org.chamara.springstarterdemomaven;

import com.github.javafaker.Faker;
import org.chamara.springstarterdemomaven.customer.Customer;
import org.chamara.springstarterdemomaven.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringStarterDemoMavenApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringStarterDemoMavenApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository) {
        return args -> {
            Faker faker = new Faker();
            String name;
            int age;
            String email;
            List<Customer> customers = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                name = faker.name().firstName();
                age = faker.number().numberBetween(20, 50);
                email = faker.internet().emailAddress();
                customers.add(new Customer(name, email, age));
            }
            customerRepository.saveAll(customers);
        };
    }
}
