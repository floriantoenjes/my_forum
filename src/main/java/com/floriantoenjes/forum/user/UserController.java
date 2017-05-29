package com.floriantoenjes.forum.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Validator validator;

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @RequestMapping("/members")
    public String members(Model model) {
        model.addAttribute("users", userService.findAll());
        return "members";
    }

    @RequestMapping("/register")
    public String registerForm(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(User user, @RequestParam String passwordAgain, BindingResult result, RedirectAttributes redirectAttributes) {
        user.setRole(new Role("ROLE_USER"));
        validator.validate(user, result);
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/register";
        } else if (userService.findByUsername(user.getUsername()) != null) {
            redirectAttributes.addFlashAttribute("userExistsError", "true");
            redirectAttributes.addFlashAttribute("userExistsErrorMessage", "user already exists");
            redirectAttributes.addFlashAttribute("user", user);

            return "redirect:/register";
        } else if (!user.getPassword().equals(passwordAgain)) {
            redirectAttributes.addFlashAttribute("passwordConfirmationError", "true");
            redirectAttributes.addFlashAttribute("passwordConfirmationErrorMessage", "the passwords have to match");
            redirectAttributes.addFlashAttribute("user", user);

            return "redirect:/register";
        }
        userService.save(user);
        return "redirect:/boards/";
    }
}
