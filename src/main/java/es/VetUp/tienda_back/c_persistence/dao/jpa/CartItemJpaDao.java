package es.VetUp.tienda_back.c_persistence.dao.jpa;

import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CartItemJpaEntity;

import java.util.List;
import java.util.Optional;

public interface CartItemJpaDao {
    List<CartItemJpaEntity> getAllCartItems();
    Optional<CartItemJpaEntity> getCartItemById(Long id);
    List<CartItemJpaEntity> getCartItemsByCartId(Long cartId);
    CartItemJpaEntity createCartItem(CartItemJpaEntity cartItemJpaEntity);
    CartItemJpaEntity updateCartItem(CartItemJpaEntity cartItemJpaEntity);
    void deleteCartItem(Long id);
}

