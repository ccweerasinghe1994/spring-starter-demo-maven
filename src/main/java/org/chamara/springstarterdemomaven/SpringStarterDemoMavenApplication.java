package org.chamara.springstarterdemomaven;

import org.chamara.springstarterdemomaven.customer.Customer;
import org.chamara.springstarterdemomaven.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SpringStarterDemoMavenApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringStarterDemoMavenApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository) {

        return args -> {
            Customer customer1 = new Customer(1, "Chamara", "abc@123.com", 30);
            Customer customer2 = new Customer(2, "Kasun", "abcd@123.com", 25);
            Customer customer3 = new Customer(3, "Nimal", "abcde@123.com", 40);
            List<Customer> customers = List.of(customer1, customer2, customer3);
            customerRepository.saveAll(customers);
            System.out.println("Hello World");
        };
    }
}
