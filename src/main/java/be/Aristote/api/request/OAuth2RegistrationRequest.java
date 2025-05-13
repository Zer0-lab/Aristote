package be.Aristote.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OAuth2RegistrationRequest {

    @NotBlank(message = "Le nom est obligatoire")
    private String name;

    @NotBlank(message = "Le prénom est obligatoire")
    private String surname;

    @Email(message = "Email invalide")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    private String password;

    @NotBlank(message = "L'adresse de facturation est obligatoire")
    private String billingAddress;

    @NotBlank(message = "L'adresse de livraison est obligatoire")
    private String shippingAddress;

    private String vatNumber;

    private String creditCardNumber;
}