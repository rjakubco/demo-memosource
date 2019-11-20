package org.test.memsource.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 *
 */
@Data
public class Projects implements Serializable {

    private Integer pageNumber;

    private Integer numberOfElements;

    private Integer totalElements;

    private Integer pageSize;

    private Integer totalPages;

    private List<Project> content;
}