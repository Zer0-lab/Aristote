package be.Aristote.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OAuth2RegisterRequest {

    @NotBlank(message = "Le nom est requis")
    private String name;

    @NotBlank(message = "Le prénom est requis")
    private String surname;

    @Email(message = "Email invalide")
    @NotBlank(message = "L'email est requis")
    private String email;

    @NotBlank(message = "L'adresse de facturation est requise")
    private String billingAddress;

    @NotBlank(message = "L'adresse de livraison est requise")
    private String shippingAddress;

    private String vatNumber;
    private String creditCardNumber;
}