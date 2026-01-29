package es.VetUp.tienda_back.c_persistence.repository.mapper;

import es.VetUp.tienda_back.b_domain.repository.entity.CartItemEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CartItemJpaEntity;

public class CartItemPersistenceMapper {
    private static CartItemPersistenceMapper instance;

    private CartItemPersistenceMapper() {
    }

    public static CartItemPersistenceMapper getInstance() {
        if (instance == null) {
            instance = new CartItemPersistenceMapper();
        }
        return instance;
    }

    public CartItemEntity fromCartItemJpaEntityToCartItemEntity(CartItemJpaEntity cartItemJpaEntity) {
        if (cartItemJpaEntity == null) {
            return null;
        }
        return new CartItemEntity(
                cartItemJpaEntity.getId(),
                cartItemJpaEntity.getQuantity(),
                cartItemJpaEntity.getCart() != null ? cartItemJpaEntity.getCart().getId() : null,
                ProductPersistenceMapper.getInstance().fromProductJpaEntitytoToProductEntity(cartItemJpaEntity.getProduct())
        );
    }

    public CartItemJpaEntity fromCartItemEntityToCartItemJpaEntity(CartItemEntity cartItemEntity) {
        if (cartItemEntity == null) {
            return null;
        }
            es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CartJpaEntity cartJpa = null;
        if (cartItemEntity.cartId() != null) {
            cartJpa = new es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CartJpaEntity();
            cartJpa.setId(cartItemEntity.cartId());
        }
        return new CartItemJpaEntity(
                cartItemEntity.id(),
                cartItemEntity.quantity(),
                cartJpa,
                ProductPersistenceMapper.getInstance().fromProductEntitytoToProductJpaEntity(cartItemEntity.product())
        );
    }
}
