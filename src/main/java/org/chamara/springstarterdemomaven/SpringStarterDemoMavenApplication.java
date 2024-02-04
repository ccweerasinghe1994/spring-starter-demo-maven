package org.chamara.springstarterdemomaven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class SpringStarterDemoMavenApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringStarterDemoMavenApplication.class, args);
    }

    @GetMapping("/greet")
    public GreetResponse greet() {
        return new GreetResponse(new Person("Chamara", 30), List.of("Java", "Kotlin", "Spring"));
    }

    record Person(String name, int age) {
    }

    record GreetResponse(Person person, List<String> favouriteProgrammingLanguages) {
    }
}
