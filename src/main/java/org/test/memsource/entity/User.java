package org.test.memsource.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO representing User from Memsource API.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private String userName;
    private String uuid;
    private String id;
    private String firstName;
    private String lastName;
    private String role;
    private String email;
}
