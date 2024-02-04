package org.chamara.springstarterdemomaven;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringStarterDemoMavenApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringStarterDemoMavenApplication.class, args);
    }

    @GetMapping("/greet")
    public GreetResponse greet(@RequestParam(value = "userName", required = false) String name) {
        Optional<String> optionalName = Optional.ofNullable(name);
        String nameValue = optionalName.orElseGet(() -> "No Name Provided");
        // let's remove the double quotes from the name
        nameValue = nameValue.replaceAll("\"", "");
        // check if the name is empty
        nameValue = nameValue.isBlank() ? "Empty String" : nameValue;
        return new GreetResponse(new Person(nameValue, 30), List.of("Java", "Kotlin", "Spring"));
    }

    record Person(String name, int age) {
    }

    record GreetResponse(Person person, List<String> favouriteProgrammingLanguages) {
    }
}
