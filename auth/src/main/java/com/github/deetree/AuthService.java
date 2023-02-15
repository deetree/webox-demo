package com.github.deetree;

import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Mariusz Bal
 */
@Service
class AuthService {

    @Autowired
    private JwtService jwtService;

    String generate(User user) {
        return jwtService.generateToken(user.email(), user.convertExtras());
    }

    boolean validate(String token, User user) {
        try {
            return jwtService.isValid(trimTokenType(token), user);
        } catch (MalformedJwtException e) { //fixme change to jwt exception
            return false;
        }
    }

    private String trimTokenType(String token) {
        return token.replace("Bearer", "").trim();
    }
}
