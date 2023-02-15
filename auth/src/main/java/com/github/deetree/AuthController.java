package com.github.deetree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mariusz Bal
 */
@RestController
@RequestMapping("/api/auth")
class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping(value = "/generate", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> generateToken(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.generate(user));
    }

    @PostMapping(value = "/validate", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token,
                                         @RequestBody User user) {
        if (service.validate(token, user))
            return ResponseEntity.ok().build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }
}
