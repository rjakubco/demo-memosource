package org.test.memsource.entity;

import lombok.Data;

/**
 * POJO representing JSON for the token request from the Memsource API.
 */
@Data
public class TokenRequest {

    private String userName;

    private String password;
}
