package com.github.deetree;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

/**
 * @author Mariusz Bal
 */
@Service
class AuthService {

    private final RestOperations template = new RestTemplate();

    boolean isTokenValid(User user, String token) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(new ObjectMapper().writeValueAsString(user), headers);
        var responseCode = template.postForEntity("http://auth:9090/api/auth/validate", entity, String.class)
                .getStatusCode();
        return responseCode == HttpStatus.OK;//todo exception
    }
}
