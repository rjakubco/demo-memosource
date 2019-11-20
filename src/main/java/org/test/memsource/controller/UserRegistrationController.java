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
import org.springframework.web.servlet.view.RedirectView;
import org.test.memsource.dto.UserRegistrationDto;
import org.test.memsource.model.User;
import org.test.memsource.service.UserService;

/**
 *
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
     * @param model
     * @return
     */
    @GetMapping
    public String showRegistrationForm(Model model) {
        return "registration";
    }

    /**
     * @param userDto
     * @param result
     * @param redirectAttributes
     * @return
     */
    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto, BindingResult result, RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView();
        User existing = userService.findByUsername(userDto.getUsername());
        if (existing != null) {
            result.rejectValue("username", null, "There is already an account registered with that username");
        }

        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            result.rejectValue("confirmPassword", null, "Passwords don't match");
            redirectAttributes.addFlashAttribute("success", true);
        }

        if (result.hasErrors()) {
            redirectView.setContextRelative(false);
            redirectView.setUrl("registration");
            return "registration";
        }

        userService.save(userDto);
        redirectAttributes.addFlashAttribute("success", true);

        return "redirect:login";

    }

}
