package org.test.memsource.service;

import org.test.memsource.entity.Projects;

/**
 * Project service used for different call to Memsource's projects API.
 */
public interface ProjectService {

    /**
     * Gets all projects for the provided username.
     *
     * @param username username of registered user
     * @return object representing projects if the API call is successful otherwise null
     */
    Projects getUserProjects(String username);

}
