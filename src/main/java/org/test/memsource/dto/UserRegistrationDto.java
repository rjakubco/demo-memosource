package org.test.memsource.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

/**
 * Class for linking frontend and backend of the application.
 */
@Data
public class UserRegistrationDto {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private String confirmPassword;

}
