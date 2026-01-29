package es.VetUp.tienda_back.b_domain.service;

import es.VetUp.tienda_back.b_domain.service.dto.CartDto;

import java.util.List;
import java.util.Optional;

public interface CartService {
    List<CartDto> getAllCarts();
    Optional<CartDto> getCartById(Long id);
    Optional<CartDto> getCartByUserId(Long userId);
    CartDto createCart(CartDto cartDto);
    CartDto updateCart(CartDto cartDto);
    void deleteCart(Long id);
    CartDto addProductToCart(Long userId, Long productId, Integer quantity);
}

