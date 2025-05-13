package be.Aristote.domain.mapper;

import org.springframework.stereotype.Component;

import be.Aristote.api.dto.UserProfileDTO;
import be.Aristote.domain.model.UserEntity;

@Component
public class UserMapper {

    public UserProfileDTO toProfileDTO(UserEntity entity) {
        UserProfileDTO userDTO = new UserProfileDTO();
        userDTO.setName(entity.getName());
        userDTO.setSurname(entity.getSurname());
        userDTO.setEmail(entity.getEmail());
        userDTO.setShippingAddress(entity.getShippingAddress());
        return userDTO;
    }
}
