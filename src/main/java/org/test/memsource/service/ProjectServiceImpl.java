package org.test.memsource.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.test.memsource.entity.Projects;
import org.test.memsource.model.User;
import org.test.memsource.repository.UserRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    public static final String PROJECTS_URL = "https://cloud.memsource.com/web/api2/v1/projects";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    public Projects getUserProjects(String username) {
        log.info("Logged user:{}", username);
        User user = userRepository.findByUsername(username);

        if (user == null) {
            log.error("Something funky happened. We didn't find logged user into database... spooky");
            return null;
        }

        ResponseEntity<String> response = restTemplate.getForEntity(PROJECTS_URL + "?token=" + user.getToken(), String.class);

        try {
            Projects projects = objectMapper.readValue(response.getBody(), Projects.class);
            log.info(projects.toString());
            return projects;
        } catch (IOException e) {
            log.error("Error when parsing response for getting user projects from Memsource API", e);
            return null;
        }
    }

}
