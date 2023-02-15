package com.github.deetree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mariusz Bal
 */
@RestController
@RequestMapping("api/register")
class AccountController {

    @Autowired
    private AccountService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> registerAccount(@RequestBody UserDto userAccount) {
        try {
            return ResponseEntity.ok(service.register(userAccount));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
