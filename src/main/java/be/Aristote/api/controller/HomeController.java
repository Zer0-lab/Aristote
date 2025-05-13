package be.Aristote.api.controller;

import be.Aristote.domain.model.UserEntity;
import be.Aristote.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping("/articles")
    public String index(@AuthenticationPrincipal OAuth2User principal, Model model) {
        if (principal != null) {
            String email = (String) principal.getAttribute("email");
            UserEntity user = userService.findByEmail(email);
            if (user != null) {
                model.addAttribute("fullName", user.getName() + " " + user.getSurname());
            }
        }
        return "index";
    }
}