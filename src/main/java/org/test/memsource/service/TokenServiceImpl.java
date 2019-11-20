package org.test.memsource.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.test.memsource.dto.UserRegistrationDto;
import org.test.memsource.entity.TokenRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TokenServiceImpl implements TokenService {

    private static final String TOKEN_URL = "https://cloud.memsource.com/web/api2/v1/auth/login";

    @Autowired
    private ObjectMapper objectMapper;

    public String getToken(UserRegistrationDto userDto) {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setUserName(userDto.getUsername());
        tokenRequest.setPassword(userDto.getPassword());

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = null;
        try {
            request = new HttpEntity<String>(objectMapper.writeValueAsString(tokenRequest), headers);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("ups");
            return null;
        }

        ResponseEntity<String> response = restTemplate.postForEntity(TOKEN_URL, request, String.class);

        JsonNode root = null;
        try {
            root = objectMapper.readTree(response.getBody());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        JsonNode token = root.path("token");

        return token.asText();
    }

}
