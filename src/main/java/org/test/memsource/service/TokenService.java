package org.test.memsource.service;

import org.test.memsource.dto.UserRegistrationDto;

/**
 *
 */
public interface TokenService {

    /**
     * @param userDto
     * @return
     */
    String getToken(UserRegistrationDto userDto);

}
