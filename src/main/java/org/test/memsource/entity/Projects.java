package org.test.memsource.entity;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO representing object returned by Memsource API for getting all projects of the user.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Projects implements Serializable {

    private Integer pageNumber;

    private Integer numberOfElements;

    private Integer totalElements;

    private Integer pageSize;

    private Integer totalPages;

    private List<Project> content;
}