package org.test.memsource.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.test.memsource.dto.UserRegistrationDto;
import org.test.memsource.model.User;

/**
 *
 */
public interface UserService extends UserDetailsService {

    /**
     * @param user
     */
    void save(UserRegistrationDto user);

    /**
     * @param username
     * @return
     */
    User findByUsername(String username);

}
