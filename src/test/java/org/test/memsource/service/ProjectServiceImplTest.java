package org.test.memsource.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.test.memsource.service.ProjectServiceImpl.PROJECTS_URL;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.test.memsource.Application;
import org.test.memsource.entity.Projects;
import org.test.memsource.model.User;
import org.test.memsource.repository.UserRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ProjectServiceImplTest {

    @Autowired
    private RestTemplate restTemplate;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ProjectServiceImpl projectService;

    private ObjectMapper mapper = new ObjectMapper();

    private MockRestServiceServer mockServer;

    @Before
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void testUserDoesNotExistsInDB() {
        String username = "existing";

        when(userRepository.findByUsername(username)).thenReturn(null);

        Projects returnedProjects = projectService.getUserProjects("existing");

        assertNull(returnedProjects);
    }

    @Test(expected = HttpServerErrorException.class)
    public void testApiCallReturnedError() throws URISyntaxException, JsonProcessingException {
        String username = "existing";
        Projects projects = new Projects();
        projects.setPageNumber(1);
        projects.setPageSize(2);
        User user = new User();
        user.setUsername(username);
        user.setToken("testToken");

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(PROJECTS_URL + "?token=testToken")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{\"error\":\"utimateError\"}")
                );

        when(userRepository.findByUsername(username)).thenReturn(user);

        Projects returnedProjects = projectService.getUserProjects("existing");

        assertEquals(projects, returnedProjects);
    }

    @Test
    public void testSunnyScenario() throws JsonProcessingException, URISyntaxException {
        String username = "existing";
        Projects projects = new Projects();
        projects.setPageNumber(1);
        projects.setPageSize(2);
        User user = new User();
        user.setUsername(username);
        user.setToken("testToken");

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(PROJECTS_URL + "?token=testToken")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(projects))
                );

        when(userRepository.findByUsername(username)).thenReturn(user);

        Projects returnedProjects = projectService.getUserProjects("existing");

        assertEquals(projects, returnedProjects);
    }
}
