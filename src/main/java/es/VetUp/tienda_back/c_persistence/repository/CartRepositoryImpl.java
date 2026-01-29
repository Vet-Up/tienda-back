package es.VetUp.tienda_back.c_persistence.repository;

import es.VetUp.tienda_back.b_domain.repository.CartRepository;
import es.VetUp.tienda_back.b_domain.repository.entity.CartEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.CartJpaDao;
import es.VetUp.tienda_back.c_persistence.repository.mapper.CartPersistenceMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CartRepositoryImpl implements CartRepository {

    private final CartJpaDao cartJpaDao;

    public CartRepositoryImpl(CartJpaDao cartJpaDao) {
        this.cartJpaDao = cartJpaDao;
    }

    @Override
    public List<CartEntity> getAllCarts() {
        return cartJpaDao.getAllCarts().stream()
                .map(CartPersistenceMapper.getInstance()::fromCartJpaEntityToCartEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CartEntity> getCartById(Long id) {
        return cartJpaDao.getCartById(id)
                .map(CartPersistenceMapper.getInstance()::fromCartJpaEntityToCartEntity);
    }

    @Override
    public Optional<CartEntity> getCartByUserId(Long userId) {
        return cartJpaDao.getCartByUserId(userId)
                .map(CartPersistenceMapper.getInstance()::fromCartJpaEntityToCartEntity);
    }

    @Override
    public CartEntity createCart(CartEntity cartEntity) {
        return CartPersistenceMapper.getInstance().fromCartJpaEntityToCartEntity(
                cartJpaDao.createCart(
                        CartPersistenceMapper.getInstance().fromCartEntityToCartJpaEntity(cartEntity)
                )
        );
    }

    @Override
    public CartEntity updateCart(CartEntity cartEntity) {
        return CartPersistenceMapper.getInstance().fromCartJpaEntityToCartEntity(
                cartJpaDao.updateCart(
                        CartPersistenceMapper.getInstance().fromCartEntityToCartJpaEntity(cartEntity)
                )
        );
    }

    @Override
    public void deleteCart(Long id) {
        cartJpaDao.deleteCart(id);
    }
}

