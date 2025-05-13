package be.Aristote.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "Le nom est requis")
    private String name;

    @NotBlank(message = "Le prénom est requis")
    private String surname;

    @Email(message = "Email invalide")
    @NotBlank(message = "L'email est requis")
    private String email;

    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=[\\]{}|;':\",.<>/?]).{8,}$", 
        message = "Le mot de passe doit contenir au moins une majuscule, une minuscule, un chiffre et un caractère spécial")
    private String password;

    @NotBlank(message = "L'adresse de facturation est requise")
    private String billingAddress;

    @NotBlank(message = "L'adresse de livraison est requise")
    private String shippingAddress;

    private String vatNumber;
    private String creditCardNumber;
}