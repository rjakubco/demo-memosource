package org.test.memsource.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
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

    @Override
    public String getToken(UserRegistrationDto userDto) {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setUserName(userDto.getUsername());
        tokenRequest.setPassword(userDto.getPassword());

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request;
        ResponseEntity<String> response = null;
        try {
            request = new HttpEntity<>(objectMapper.writeValueAsString(tokenRequest), headers);

            // TODO: need handling for timeout
            response = restTemplate.postForEntity(TOKEN_URL, request, String.class);

            JsonNode root;

            root = objectMapper.readTree(response.getBody());
            JsonNode token = root.path("token");

            if (token.isMissingNode()) {
                throw new IllegalArgumentException("There was not token in the response from the Memsource API");
            }

            return token.asText();

        } catch (JsonProcessingException e) {
            log.error("Problem with parsing token request POJO");
            throw new IllegalArgumentException("Unable to parse request for Memsource API", e);
        } catch (HttpClientErrorException e) {
            log.error("Error in the API call", e);
            throw new IllegalArgumentException("There was a problem when calling Memsource API", e);
        } catch (IOException e) {
            log.error("There was a problem with parsing authentication POJO response. Response status code: {} and entity: {}", response.getStatusCode(), response.getBody());
            throw new IllegalArgumentException("Unable to parse response from Memsource API", e);
        }
    }

}
