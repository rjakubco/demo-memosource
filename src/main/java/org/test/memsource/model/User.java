package org.test.memsource.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * Entity class representing registered user.
 */
@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    private String username;

    private String password;

    @Transient
    private String passwordConfirm;

    /**
     * Memsource API token for the user used for API calls to the Memsource app.
     */
    private String token;

}
