package org.test.memsource.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project implements Serializable {

    private String name;

    private String status;

    private String sourceLang;

    private List<String> targetLangs;
}