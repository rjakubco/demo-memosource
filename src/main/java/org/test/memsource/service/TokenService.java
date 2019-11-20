package org.test.memsource.service;

import org.test.memsource.dto.UserRegistrationDto;

/**
 * Service for getting token from Memsource app.
 */
public interface TokenService {

    /**
     * Method for getting token from the Memsource app, that is later used for other API calls.
     * Token is generated when user registers to the app for the simplicity.
     *
     * @param userDto object representing user's information from the registration
     * @return token as string otherwise {@link IllegalArgumentException}
     */
    String getToken(UserRegistrationDto userDto);

}
