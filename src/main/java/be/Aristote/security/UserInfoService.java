/* package be.Aristote.security;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import be.Aristote.domain.model.RoleEntity;
import be.Aristote.domain.model.UserEntity;
import be.Aristote.domain.repository.RoleRepository;
import be.Aristote.domain.repository.UserRepository;

@Service
public class UserInfoService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserInfoService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserEntity loadOrCreateUser(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.get("email");
        String login = (String) attributes.get("login"); // GitHub login
        String name = (String) attributes.get("name");
        String finalName = (name != null) ? name : login;
        String surname = finalName.contains(" ") ? finalName.substring(finalName.indexOf(" ") + 1) : "";

        // Vérifie si l'utilisateur existe déjà
        UserEntity existingUser = userRepository.findByEmail(email);
        if (existingUser != null) {
            return existingUser;
        }

        // Récupère le rôle USER
        RoleEntity roleUser = roleRepository.findByName("USER")
            .orElseThrow(() -> new IllegalStateException("Le rôle USER est manquant dans la base."));

        // Crée un nouvel utilisateur
        UserEntity newUser = new UserEntity();
        newUser.setName(finalName);
        newUser.setSurname(surname);
        newUser.setEmail(email);
        newUser.setPassword(""); // pas utilisé ici
        newUser.setRole(roleUser);
        newUser.setBillingAddress("");
        newUser.setShippingAddress("");
        newUser.setVatNumber("");
        newUser.setCreditCardNumber("");
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(newUser);
    }
} */