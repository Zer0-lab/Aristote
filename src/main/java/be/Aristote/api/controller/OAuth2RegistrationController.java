// Contrôleur pour afficher et traiter le formulaire d'inscription OAuth2
package be.Aristote.api.controller;

import be.Aristote.api.request.OAuth2RegistrationRequest;
import be.Aristote.domain.model.RoleEntity;
import be.Aristote.domain.model.UserEntity;
import be.Aristote.domain.repository.RoleRepository;
import be.Aristote.domain.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class OAuth2RegistrationController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/oauth2/register")
    public String showRegistrationForm(HttpSession session, Model model) {
        String email = (String) session.getAttribute("oauthUserEmail");
        String name = (String) session.getAttribute("oauthUserName");

        OAuth2RegistrationRequest request = new OAuth2RegistrationRequest();
        request.setEmail(email);
        request.setName(name);

        model.addAttribute("user", request);
        return "oauth2_register";
    }

    @PostMapping("/oauth2/register")
    public String registerUser(@Valid @ModelAttribute("user") OAuth2RegistrationRequest request,
            BindingResult result,
            HttpSession session) {
        if (result.hasErrors()) {
            return "oauth2_register";
        }

        UserEntity newUser = new UserEntity();
        newUser.setName(request.getName());
        newUser.setSurname(request.getSurname());
        newUser.setEmail(request.getEmail());
        newUser.setBillingAddress(request.getBillingAddress());
        newUser.setShippingAddress(request.getShippingAddress());
        newUser.setVat_number(request.getVatNumber());
        newUser.setCard(request.getCreditCardNumber());

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        newUser.setPassword(hashedPassword);

        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());

        RoleEntity clientRole = roleRepository.findById(2L).orElseThrow();
        newUser.setRole(clientRole);

        userRepository.save(newUser);

        session.setAttribute("fullName", newUser.getName() + " " + newUser.getSurname());
        session.removeAttribute("oauthUserEmail");
        session.removeAttribute("oauthUserName");

        return "oauth2_register";
    }
}
