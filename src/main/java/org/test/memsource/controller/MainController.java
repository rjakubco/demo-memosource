package org.test.memsource.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Main controller for the app and handling of first request to the app.
 */
@Controller
public class MainController {

    /**
     * Redirects to the login page.
     *
     * @param model MVC model
     * @return login page
     */
    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

}
