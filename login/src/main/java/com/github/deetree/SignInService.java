package com.github.deetree;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Mariusz Bal
 */
@Service
class SignInService {

    private final RestTemplate template = new RestTemplate();
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private AccountRepository repository;

    Authentication signIn(User user) {
        return manager.authenticate(
                new UsernamePasswordAuthenticationToken(user.email(), user.password()));
    }

    String requestAuthToken(TokenUser user) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(new ObjectMapper().writeValueAsString(user), headers);
        return template.postForObject("http://auth:9090/api/auth/generate", entity, String.class);
    }

    String findName(User user) {
        return repository.findByEmail(user.email()).orElseThrow(() -> new BadCredentialsException("Bad credentials"))
                .getName();
    }
}
