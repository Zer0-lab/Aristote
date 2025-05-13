package be.Aristote.domain.service;

import be.Aristote.api.request.OAuth2RegistrationRequest;
import be.Aristote.domain.model.UserEntity;
import be.Aristote.domain.model.RoleEntity;
import be.Aristote.domain.repository.RoleRepository;
import be.Aristote.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OAuth2RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(OAuth2RegistrationRequest request) {
        UserEntity user = new UserEntity();

        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setBillingAddress(request.getBillingAddress());
        user.setShippingAddress(request.getShippingAddress());
        user.setVat_number(request.getVatNumber());
        user.setCard(request.getCreditCardNumber());

        RoleEntity role = roleRepository.findById(Long.valueOf(2))
                .orElseThrow(() -> new IllegalArgumentException("Le rôle CLIENT est introuvable"));
        user.setRole(role);

        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);
    }
}
