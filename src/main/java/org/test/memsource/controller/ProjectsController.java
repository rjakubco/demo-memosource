package org.test.memsource.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.test.memsource.service.ProjectServiceImpl;

/**
 * REST controller for working with projects.
 */
@Controller
@RequestMapping("/projects")
public class ProjectsController {

    @Autowired
    private ProjectServiceImpl projectService;

    /**
     * Gets all projects for the authenticated user and send them to the frontend by the model.
     *
     * @param model MVC model
     * @param principal authenticated user
     * @return redirect the projects page
     */
    @GetMapping
    public String getProjects(Model model, Principal principal) {
        model.addAttribute("projects", projectService.getUserProjects(principal.getName()).getContent());
        return "projects";
    }

}
