package org.test.memsource.service;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.test.memsource.service.TokenServiceImpl.TOKEN_URL;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.test.memsource.Application;
import org.test.memsource.dto.UserRegistrationDto;
import org.test.memsource.entity.TokenRequest;
import org.test.memsource.entity.TokenResponse;
import org.test.memsource.entity.User;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TokenServiceImplTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TokenServiceImpl tokenService;

    private ObjectMapper mapper = new ObjectMapper();

    private MockRestServiceServer mockServer;

    @Before
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectResponse() throws URISyntaxException {
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(TOKEN_URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("AHoj")
                );

        tokenService.getToken(new UserRegistrationDto("", "", ""));
    }

    @Test(expected = HttpServerErrorException.class)
    public void testApiCallReturnedError() throws URISyntaxException {
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(TOKEN_URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{\"error\":\"ultimateError\"}")
                );

        tokenService.getToken(new UserRegistrationDto("", "", ""));
    }

    @Test
    public void testSunnyScenario() throws JsonProcessingException, URISyntaxException {
        String token = "superToken";
        TokenResponse tokenResponse = new TokenResponse(new User(), token, "2019-02-23");
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(TOKEN_URL)))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().json(mapper.writeValueAsString(new TokenRequest("existing", "password"))))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(tokenResponse))
                );

        String returnedToken = tokenService.getToken(new UserRegistrationDto("existing", "password", ""));

        assertEquals(token, returnedToken);
    }

}
