package be.Aristote.domain.service;

import be.Aristote.api.dto.UserProfileDTO;
import be.Aristote.api.request.UserRequest;
import be.Aristote.domain.mapper.UserMapper;
import be.Aristote.domain.model.UserEntity;
import be.Aristote.domain.repository.UserRepository;
import be.Aristote.domain.repository.RoleRepository;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserProfileDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toProfileDTO)
                .orElseThrow(() -> new IllegalArgumentException("ID utilisateur invalide : " + id));
    }

    public List<UserProfileDTO> retrieveAll() {
        List<UserEntity> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new IllegalArgumentException("Aucun utilisateur trouvé");
        }

        return users.stream()
                .map(userMapper::toProfileDTO)
                .collect(Collectors.toList());
    }

    public void updateUser(Long id, UserRequest request) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));

        if (request.getName() != null)
            user.setName(request.getName());
        if (request.getSurname() != null)
            user.setSurname(request.getSurname());
        if (request.getEmail() != null)
            user.setEmail(request.getEmail());
        if (request.getBillingAddress() != null)
            user.setBillingAddress(request.getBillingAddress());
        if (request.getShippingAddress() != null)
            user.setShippingAddress(request.getShippingAddress());
        if (request.getVatNumber() != null)
            user.setVat_number(request.getVatNumber());
        if (request.getCreditCardNumber() != null)
            user.setCard(request.getCreditCardNumber());

        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }

    public ResponseEntity<?> createUser(UserRequest request) {
        UserEntity user = new UserEntity();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setBillingAddress(request.getBillingAddress());
        user.setShippingAddress(request.getShippingAddress());
        user.setVat_number(request.getVatNumber());
        user.setCard(request.getCreditCardNumber());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setRole(roleRepository.findByName("CLIENT").orElseThrow());

        this.save(user);

        return ResponseEntity.ok("Utilisateur créé avec succès.");
    }

    public UserEntity authenticate(String email, String rawPassword) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Email ou mot de passe invalide"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("Email ou mot de passe invalide");
        }

        return user;
    }

    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("Utilisateur non trouvé");
        }
        userRepository.deleteById(id);
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword()) 
                .roles(user.getRole().getName()) 
                .build();
    }

}
