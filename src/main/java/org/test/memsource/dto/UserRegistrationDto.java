package org.test.memsource.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

/**
 *
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
