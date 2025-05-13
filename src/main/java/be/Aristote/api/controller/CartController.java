package be.Aristote.api.controller;

import be.Aristote.api.dto.CartDTO;
import be.Aristote.domain.model.CartEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import be.Aristote.domain.service.CartService;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add-single")
    public ResponseEntity<CartEntity> addToCart(@RequestParam Long articleId,
            @RequestParam int quantity,
            @AuthenticationPrincipal UserDetails userDetails) {
        CartEntity cart = cartService.addArticleToCart(userDetails.getUsername(), articleId, quantity);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add")
    public ResponseEntity<CartEntity> addMultipleItems(@RequestBody CartDTO request) {
        CartEntity cart = cartService.addMultipleArticlesToCart(request.getUserEmail(), request.getItems());
        return ResponseEntity.ok(cart);
    }

    @GetMapping
    public ResponseEntity<CartEntity> getCart(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(cartService.getUserCart(userDetails.getUsername()));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeFromCart(@RequestParam Long articleId,
            @AuthenticationPrincipal UserDetails userDetails) {
        cartService.removeArticleFromCart(userDetails.getUsername(), articleId);
        return ResponseEntity.noContent().build();
    }

}