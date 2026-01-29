package es.VetUp.tienda_back.b_domain.service;

import es.VetUp.tienda_back.b_domain.service.dto.CartItemDto;

import java.util.List;
import java.util.Optional;

public interface CartItemService {
    List<CartItemDto> getAllCartItems();
    Optional<CartItemDto> getCartItemById(Long id);
    List<CartItemDto> getCartItemsByCartId(Long cartId);
    CartItemDto createCartItem(CartItemDto cartItemDto);
    CartItemDto updateCartItem(CartItemDto cartItemDto);
    void deleteCartItem(Long id);
}

