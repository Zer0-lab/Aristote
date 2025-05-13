package be.Aristote.domain.mapper;


import be.Aristote.api.dto.CartDTO;
import be.Aristote.api.dto.CartItemDTO;
import be.Aristote.domain.model.CartEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartMapper {

    public CartDTO mapToDto(CartEntity cart) {
        CartDTO dto = new CartDTO();
        dto.setId(cart.getId());
        dto.setUserEmail(cart.getUser().getEmail());

        List<CartItemDTO> items = cart.getCartItems().stream().map(item -> {
            CartItemDTO itemDto = new CartItemDTO();
            itemDto.setArticleId(item.getArticle().getId());
            itemDto.setTitle(item.getArticle().getTitle());
            itemDto.setQuantity(item.getQuantity());
            return itemDto;
        }).toList();

        dto.setItems(items);
        return dto;
    }
}