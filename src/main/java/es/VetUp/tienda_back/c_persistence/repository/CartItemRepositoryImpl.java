package es.VetUp.tienda_back.c_persistence.repository;

import es.VetUp.tienda_back.b_domain.repository.CartItemRepository;
import es.VetUp.tienda_back.b_domain.repository.entity.CartItemEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.CartItemJpaDao;
import es.VetUp.tienda_back.c_persistence.repository.mapper.CartItemPersistenceMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CartItemRepositoryImpl implements CartItemRepository {

    private final CartItemJpaDao cartItemJpaDao;

    public CartItemRepositoryImpl(CartItemJpaDao cartItemJpaDao) {
        this.cartItemJpaDao = cartItemJpaDao;
    }

    @Override
    public List<CartItemEntity> getAllCartItems() {
        return cartItemJpaDao.getAllCartItems().stream()
                .map(CartItemPersistenceMapper.getInstance()::fromCartItemJpaEntityToCartItemEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CartItemEntity> getCartItemById(Long id) {
        return cartItemJpaDao.getCartItemById(id)
                .map(CartItemPersistenceMapper.getInstance()::fromCartItemJpaEntityToCartItemEntity);
    }

    @Override
    public List<CartItemEntity> getCartItemsByCartId(Long cartId) {
        return cartItemJpaDao.getCartItemsByCartId(cartId).stream()
                .map(CartItemPersistenceMapper.getInstance()::fromCartItemJpaEntityToCartItemEntity)
                .collect(Collectors.toList());
    }

    @Override
    public CartItemEntity createCartItem(CartItemEntity cartItemEntity) {
        return CartItemPersistenceMapper.getInstance().fromCartItemJpaEntityToCartItemEntity(
                cartItemJpaDao.createCartItem(
                        CartItemPersistenceMapper.getInstance().fromCartItemEntityToCartItemJpaEntity(cartItemEntity)
                )
        );
    }

    @Override
    public CartItemEntity updateCartItem(CartItemEntity cartItemEntity) {
        return CartItemPersistenceMapper.getInstance().fromCartItemJpaEntityToCartItemEntity(
                cartItemJpaDao.updateCartItem(
                        CartItemPersistenceMapper.getInstance().fromCartItemEntityToCartItemJpaEntity(cartItemEntity)
                )
        );
    }

    @Override
    public void deleteCartItem(Long id) {
        cartItemJpaDao.deleteCartItem(id);
    }
}

