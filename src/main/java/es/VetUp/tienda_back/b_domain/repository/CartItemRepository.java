package es.VetUp.tienda_back.b_domain.repository;

import es.VetUp.tienda_back.b_domain.repository.entity.CartItemEntity;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository {
    List<CartItemEntity> getAllCartItems();
    Optional<CartItemEntity> getCartItemById(Long id);
    List<CartItemEntity> getCartItemsByCartId(Long cartId);
    CartItemEntity createCartItem(CartItemEntity cartItemEntity);
    CartItemEntity updateCartItem(CartItemEntity cartItemEntity);
    void deleteCartItem(Long id);
}

