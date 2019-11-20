package org.test.memsource.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 */
@Controller
public class MainController {

    /**
     * @param model
     * @return
     */
    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

}
