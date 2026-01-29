package es.VetUp.tienda_back.b_domain.repository;

import es.VetUp.tienda_back.b_domain.repository.entity.CartEntity;

import java.util.List;
import java.util.Optional;

public interface CartRepository {
    List<CartEntity> getAllCarts();
    Optional<CartEntity> getCartById(Long id);
    Optional<CartEntity> getCartByUserId(Long userId);
    CartEntity createCart(CartEntity cartEntity);
    CartEntity updateCart(CartEntity cartEntity);
    void deleteCart(Long id);
}

