package be.Aristote.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {
    private String name;
    private String surname;
    private String email;
    private String shippingAddress;
}
