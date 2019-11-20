package org.test.memsource.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.test.memsource.dto.UserRegistrationDto;
import org.test.memsource.model.User;

/**
 * Interface for defining how to work with users in the app. Mainly for storing/registering new users
 * or finding existing users. Used by login feature and many controllers
 */
public interface UserService extends UserDetailsService {

    /**
     * Stores new user in the database.
     *
     * @param user object representing user from the frontend
     */
    void save(UserRegistrationDto user);

    /**
     * Finds user by provided username.
     *
     * @param username username
     * @return returns User with provided username otherwise null
     */
    User findByUsername(String username);

}
