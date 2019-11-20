package org.test.memsource.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

    private static final String PROJECTS_URL = "https://cloud.memsource.com/web/api2/v1/projects";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public Projects getUserProjects(String username) {
        log.info("Logged user:{}", username);
        User user = userRepository.findByUsername(username);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<String> response = restTemplate.getForEntity(PROJECTS_URL + "?token=" + user.getToken(), String.class);

        try {
            Projects projects = objectMapper.readValue(response.getBody(), Projects.class);
            log.info(projects.toString());
            return projects;
        } catch (IOException e) {
            log.error("Bad parsing");
            return null;
        }
    }

}
