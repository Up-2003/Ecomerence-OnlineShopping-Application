package com.ecomerence.onlineshopping.model.controller;

import com.ecomerence.onlineshopping.dto.RegisterForm;
import com.ecomerence.onlineshopping.model.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error,
                            @RequestParam(required = false) String logout,
                            Model model) {
        if (error != null) model.addAttribute("error", "Invalid username or password");
        if (logout != null) model.addAttribute("message", "Logged out successfully");
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("form", new RegisterForm());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("form") RegisterForm form,
                           BindingResult result,
                           RedirectAttributes ra) {
        if (!form.passwordsMatch()) {
            result.rejectValue("confirmPassword", "mismatch", "Passwords do not match");
        }
        if (result.hasErrors()) {
            return "auth/register";
        }
        try {
            userService.register(form);
            ra.addFlashAttribute("success", "Registration successful! Please login.");
            return "redirect:/auth/login";
        } catch (Exception e) {
            result.rejectValue("username", "error", e.getMessage());
            return "auth/register";
        }
    }}
