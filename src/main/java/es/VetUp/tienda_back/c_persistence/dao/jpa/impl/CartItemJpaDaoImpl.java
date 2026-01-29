package es.VetUp.tienda_back.c_persistence.dao.jpa.impl;

import es.VetUp.tienda_back.c_persistence.dao.jpa.CartItemJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CartItemJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CartJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.ProductJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Transactional
public class CartItemJpaDaoImpl implements CartItemJpaDao {

    private static final Logger log = LoggerFactory.getLogger(CartItemJpaDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CartItemJpaEntity> getAllCartItems() {
        return entityManager.createQuery("SELECT ci FROM CartItemJpaEntity ci", CartItemJpaEntity.class)
                .getResultList();
    }

    @Override
    public Optional<CartItemJpaEntity> getCartItemById(Long id) {
        return Optional.ofNullable(entityManager.find(CartItemJpaEntity.class, id));
    }

    @Override
    public List<CartItemJpaEntity> getCartItemsByCartId(Long cartId) {
        String jpql = "SELECT ci FROM CartItemJpaEntity ci WHERE ci.cart.id = :cartId";
        return entityManager.createQuery(jpql, CartItemJpaEntity.class)
                .setParameter("cartId", cartId)
                .getResultList();
    }

    @Override
    public CartItemJpaEntity createCartItem(CartItemJpaEntity cartItemJpaEntity) {
        // debug incoming state
        log.debug("createCartItem - incoming cart id = {}, product id = {}",
                cartItemJpaEntity.getCart() == null ? null : cartItemJpaEntity.getCart().getId(),
                cartItemJpaEntity.getProduct() == null ? null : cartItemJpaEntity.getProduct().getProductId());

        // ensure cart and product are managed references to avoid transient/null constraint violations
        if (cartItemJpaEntity.getCart() != null && cartItemJpaEntity.getCart().getId() != null) {
            log.debug("createCartItem - resolving cartRef for cartId={}", cartItemJpaEntity.getCart().getId());
            CartJpaEntity cartRef = entityManager.getReference(CartJpaEntity.class, cartItemJpaEntity.getCart().getId());
            cartItemJpaEntity.setCart(cartRef);
            log.debug("createCartItem - cartRef resolved: {}", cartRef.getId());
        }
        if (cartItemJpaEntity.getProduct() != null) {
            // ProductJpaEntity uses field 'productId' as id
            Long prodId = cartItemJpaEntity.getProduct().getProductId();
            if (prodId != null) {
                log.debug("createCartItem - resolving prodRef for prodId={}", prodId);
                ProductJpaEntity prodRef = entityManager.getReference(ProductJpaEntity.class, prodId);
                cartItemJpaEntity.setProduct(prodRef);
                log.debug("createCartItem - prodRef resolved: {}", prodRef.getProductId());
            }
        }
        entityManager.persist(cartItemJpaEntity);
        log.debug("createCartItem - persisted cartItem cartId={}, productId={}, assignedId={}",
                cartItemJpaEntity.getCart() == null ? null : cartItemJpaEntity.getCart().getId(),
                cartItemJpaEntity.getProduct() == null ? null : cartItemJpaEntity.getProduct().getProductId(),
                cartItemJpaEntity.getId());
        return cartItemJpaEntity;
    }

    @Override
    public CartItemJpaEntity updateCartItem(CartItemJpaEntity cartItemJpaEntity) {
        CartItemJpaEntity managed = entityManager.find(CartItemJpaEntity.class, cartItemJpaEntity.getId());
        if(managed == null) {
            throw new RuntimeException("CartItem with id " + cartItemJpaEntity.getId() + " not found");
        }
        return entityManager.merge(cartItemJpaEntity);
    }

    @Override
    public void deleteCartItem(Long id) {
        CartItemJpaEntity managed = entityManager.find(CartItemJpaEntity.class, id);
        if(managed != null) {
            entityManager.remove(managed);
        }
    }
}
