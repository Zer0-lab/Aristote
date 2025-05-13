package be.Aristote.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import be.Aristote.domain.model.UserEntity;
import be.Aristote.domain.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @PostMapping("/login-custom")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        try {
            UserEntity user = userService.authenticate(email, password);
            session.setAttribute("user", user);
            return "redirect:/articles";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // fichier login.html dans templates
    }
}