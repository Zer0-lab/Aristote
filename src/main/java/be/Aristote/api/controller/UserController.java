package be.Aristote.api.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.Aristote.api.dto.UserProfileDTO;
import be.Aristote.api.request.UserRequest;
import be.Aristote.domain.model.UserEntity;
import be.Aristote.domain.repository.RoleRepository;
import be.Aristote.domain.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final RoleRepository roleRepository;

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserProfileDTO>> index() {
        List<UserProfileDTO> users = this.userService.retrieveAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDTO> show(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }

        UserProfileDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRequest request) {
        userService.createUser(request);
        return ResponseEntity.ok("Utilisateur créé avec succès.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody @Valid UserRequest request) {
        userService.updateUser(id, request);
        return ResponseEntity.ok("Utilisateur mis à jour avec succès.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok("Utilisateur supprimé avec succès.");
    }

    @PostMapping("/oauth2/register")
    public String registerUser(@ModelAttribute("user") @Valid UserRequest request,
            BindingResult result,
            HttpSession session) {

        if (result.hasErrors()) {
            return "oauth2_register";
        }

        String email = (String) session.getAttribute("oauthUserEmail");
        String name = (String) session.getAttribute("oauthUserName");

        if (email == null || name == null) {
            return "redirect:/login";
        }

        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setName(name);
        user.setSurname(request.getSurname());
        user.setPassword("");
        user.setRole(roleRepository.findByName("CLIENT").orElseThrow());
        user.setBillingAddress(request.getBillingAddress());
        user.setShippingAddress(request.getShippingAddress());
        user.setVat_number(request.getVatNumber());
        user.setCard(null);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userService.save(user);

        session.removeAttribute("oauthUserEmail");
        session.removeAttribute("oauthUserName");

        return "redirect:/articles";
    }

}
