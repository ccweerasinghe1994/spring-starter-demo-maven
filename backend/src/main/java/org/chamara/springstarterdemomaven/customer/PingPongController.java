package org.chamara.springstarterdemomaven.customer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingPongController {
    record PingPong(String message) {
    }
    private static int COUNTER = 0;
    @GetMapping("/ping")
    public PingPong ping() {
        return new PingPong("pong"+ ++COUNTER);
    }
}
