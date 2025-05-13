package be.Aristote.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import be.Aristote.api.dto.CartItemDTO;
import be.Aristote.domain.model.ArticleEntity;
import be.Aristote.domain.model.CartEntity;
import be.Aristote.domain.model.CartItemEntity;
import be.Aristote.domain.model.UserEntity;
import be.Aristote.domain.repository.ArticleRepository;
import be.Aristote.domain.repository.CartRepository;
import be.Aristote.domain.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public CartEntity getUserCart(String userEmail) {
        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return cartRepository.findByUser(user).orElseGet(() -> {
            CartEntity newCart = new CartEntity();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });
    }

    public CartEntity addArticleToCart(String userEmail, Long articleId, int quantity) {
        CartEntity cart = getUserCart(userEmail);
        ArticleEntity article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        Optional<CartItemEntity> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getArticle().getId().equals(articleId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItemEntity item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            CartItemEntity newItem = new CartItemEntity();
            newItem.setCart(cart);
            newItem.setArticle(article);
            newItem.setQuantity(quantity);
            cart.getCartItems().add(newItem);
        }

        return cartRepository.save(cart);
    }

    public void removeArticleFromCart(String userEmail, Long articleId) {
        CartEntity cart = getUserCart(userEmail);
        cart.getCartItems().removeIf(item -> item.getArticle().getId().equals(articleId));
        cartRepository.save(cart);
    }

    public CartEntity addMultipleArticlesToCart(String userEmail, List<CartItemDTO> items) {
        CartEntity cart = getUserCart(userEmail);

        for (CartItemDTO item : items) {
            ArticleEntity article = articleRepository.findById(item.getArticleId())
                    .orElseThrow(() -> new RuntimeException("Article not found"));

            Optional<CartItemEntity> existingItem = cart.getCartItems().stream()
                    .filter(ci -> ci.getArticle().getId().equals(item.getArticleId()))
                    .findFirst();

            if (existingItem.isPresent()) {
                existingItem.get().setQuantity(existingItem.get().getQuantity() + item.getQuantity());
            } else {
                CartItemEntity newItem = new CartItemEntity();
                newItem.setCart(cart);
                newItem.setArticle(article);
                newItem.setQuantity(item.getQuantity());
                cart.getCartItems().add(newItem);
            }
        }

        return cartRepository.save(cart);
    }
}