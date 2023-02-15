package com.github.deetree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Mariusz Bal
 */
@Service
class AccountService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    String register(UserDto userDto) {
        checkAccountExists(userDto);
        if (userDto.password().equals(userDto.confirmPassword())) {
            repository.save(new Account(userDto.name(), userDto.email(), encoder.encode(userDto.password())));
            return "You have been registered and can sign in now.";
        } else throw new IllegalArgumentException("Passwords don't match");
    }

    private void checkAccountExists(UserDto userDto) {
        if (repository.existsAccountByEmail(userDto.email()))
            throw new IllegalArgumentException("The account cannot be created.");
    }
}
