package org.test.memsource.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO representing response for token from Memsouce API.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse implements Serializable {

    private User user;

    private String token;

    private String expires;

}