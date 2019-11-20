package org.test.memsource.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.test.memsource.dto.UserRegistrationDto;
import org.test.memsource.model.User;
import org.test.memsource.service.UserService;

/**
 * REST controller for registering user.
 */
@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    /**
     * Redirects to the registration page used in frontend.
     *
     * @param model model of MVC
     * @return registration
     */
    @GetMapping
    public String showRegistrationForm(Model model) {
        return "registration";
    }

    /**
     * Handles registration form for user on frontend and stores user to the database.
     *
     * @param userDto            object representing user filled with information from the frontend given by the user
     * @param result             binding result for handling error cases in registration form
     * @param redirectAttributes redirect attributes used for passing paramaters to another controller in thymeleaf
     * @return where user is redirected based on if registration for successful or not
     */
    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto, BindingResult result, RedirectAttributes redirectAttributes) {
        User existing = userService.findByUsername(userDto.getUsername());
        if (existing != null) {
            result.rejectValue("username", null, "There is already an account registered with that username");
        }

        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            result.rejectValue("confirmPassword", null, "Passwords don't match");
            redirectAttributes.addFlashAttribute("success", true);
        }

        if (result.hasErrors()) {
            return "registration";
        }
        try {
            userService.save(userDto);
        } catch (IllegalArgumentException e) {
            result.reject("400", "Problem during registration");
            return "registration";
        }

        redirectAttributes.addFlashAttribute("success", true);

        return "redirect:login";

    }

}
