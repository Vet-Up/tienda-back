package es.VetUp.tienda_back.c_persistence.dao.jpa.impl;

import es.VetUp.tienda_back.c_persistence.dao.jpa.CartJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CartJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class CartJpaDaoImpl implements CartJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CartJpaEntity> getAllCarts() {
        return entityManager.createQuery(
                "SELECT DISTINCT c FROM CartJpaEntity c LEFT JOIN FETCH c.cartItems",
                CartJpaEntity.class)
                .getResultList();
    }

    @Override
    public Optional<CartJpaEntity> getCartById(Long id) {
        String jpql = "SELECT c FROM CartJpaEntity c LEFT JOIN FETCH c.cartItems WHERE c.id = :id";
        List<CartJpaEntity> results = entityManager.createQuery(jpql, CartJpaEntity.class)
                .setParameter("id", id)
                .getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Optional<CartJpaEntity> getCartByUserId(Long userId) {
        String jpql = "SELECT c FROM CartJpaEntity c LEFT JOIN FETCH c.cartItems WHERE c.user.id = :userId";
        return entityManager.createQuery(jpql, CartJpaEntity.class)
                .setParameter("userId", userId)
                .getResultStream()
                .findFirst();
    }

    @Override
    public CartJpaEntity createCart(CartJpaEntity cartJpaEntity) {
        entityManager.persist(cartJpaEntity);
        return cartJpaEntity;
    }

    @Override
    public CartJpaEntity updateCart(CartJpaEntity cartJpaEntity) {
        CartJpaEntity managed = entityManager.find(CartJpaEntity.class, cartJpaEntity.getId());
        if(managed == null) {
            throw new RuntimeException("Cart with id " + cartJpaEntity.getId() + " not found");
        }
        return entityManager.merge(cartJpaEntity);
    }

    @Override
    public void deleteCart(Long id) {
        CartJpaEntity managed = entityManager.find(CartJpaEntity.class, id);
        if(managed != null) {
            entityManager.remove(managed);
        }
    }
}

