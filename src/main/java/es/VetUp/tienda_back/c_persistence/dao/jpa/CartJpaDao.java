package es.VetUp.tienda_back.c_persistence.dao.jpa;

import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CartJpaEntity;

import java.util.List;
import java.util.Optional;

public interface CartJpaDao {
    List<CartJpaEntity> getAllCarts();
    Optional<CartJpaEntity> getCartById(Long id);
    Optional<CartJpaEntity> getCartByUserId(Long userId);
    CartJpaEntity createCart(CartJpaEntity cartJpaEntity);
    CartJpaEntity updateCart(CartJpaEntity cartJpaEntity);
    void deleteCart(Long id);
}

