package es.VetUp.tienda_back.c_persistence.repository.mapper;

import es.VetUp.tienda_back.b_domain.repository.entity.CartEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CartJpaEntity;

import java.util.List;
import java.util.stream.Collectors;

public class CartPersistenceMapper {
    private static CartPersistenceMapper instance;

    private CartPersistenceMapper() {
    }

    public static CartPersistenceMapper getInstance() {
        if (instance == null) {
            instance = new CartPersistenceMapper();
        }
        return instance;
    }

    public CartEntity fromCartJpaEntityToCartEntity(CartJpaEntity cartJpaEntity) {
        if (cartJpaEntity == null) {
            return null;
        }
        return new CartEntity(
                cartJpaEntity.getId(),
                cartJpaEntity.getTotalProducts(),
                cartJpaEntity.getTotalPrice(),
                UserPersistenceMapper.getInstance().fromUserJpaEntityToUserEntity(cartJpaEntity.getUser()),
                cartJpaEntity.getCartItems() == null ? null :
                        cartJpaEntity.getCartItems().stream()
                                .map(CartItemPersistenceMapper.getInstance()::fromCartItemJpaEntityToCartItemEntity)
                                .collect(Collectors.toList())
        );
    }

    public CartJpaEntity fromCartEntityToCartJpaEntity(CartEntity cartEntity) {
        if (cartEntity == null) {
            return null;
        }
        CartJpaEntity cartJpa = new CartJpaEntity(
                cartEntity.id(),
                cartEntity.totalProducts(),
                cartEntity.totalPrice(),
                UserPersistenceMapper.getInstance().fromUserEntityToUserJpaEntity(cartEntity.user())
        );
        // map and set cart items to avoid losing them on merge
        if (cartEntity.cartItems() != null) {
            List<es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CartItemJpaEntity> items =
                    cartEntity.cartItems().stream()
                            .map(CartItemPersistenceMapper.getInstance()::fromCartItemEntityToCartItemJpaEntity)
                            .collect(Collectors.toList());
            // ensure each item references the same cartJpa instance
            for (es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CartItemJpaEntity item : items) {
                item.setCart(cartJpa);
            }
            cartJpa.setCartItems(items);
        }
        return cartJpa;
    }
}
