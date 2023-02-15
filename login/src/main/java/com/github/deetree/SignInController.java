package com.github.deetree;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

/**
 * @author Mariusz Bal
 */
@RestController
@RequestMapping("/api/signin")
class SignInController {

    @Autowired
    private SignInService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Response> signIn(@RequestBody User user) {
        try {
            String name = service.findName(user);
            Authentication authentication = service.signIn(user);
            return ResponseEntity.ok(new Response(name, "Success",
                    service.requestAuthToken(new TokenUser(user.email(), name)), URI.create("/lobby")));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new Response("", "Invalid credentials", "", URI.create("#")));
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new Response("", "Sign in attempt failed", "", URI.create("#")));
        }
    }
}
