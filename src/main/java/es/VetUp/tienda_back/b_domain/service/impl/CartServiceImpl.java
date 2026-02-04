package es.VetUp.tienda_back.b_domain.service.impl;

import es.VetUp.tienda_back.b_domain.exception.BusinessException;
import es.VetUp.tienda_back.b_domain.mapper.CartMapper;
import es.VetUp.tienda_back.b_domain.mapper.CartItemMapper;
import es.VetUp.tienda_back.b_domain.mapper.ProductMapper;
import es.VetUp.tienda_back.b_domain.mapper.UserMapper;
import es.VetUp.tienda_back.b_domain.repository.CartRepository;
import es.VetUp.tienda_back.b_domain.repository.CartItemRepository;
import es.VetUp.tienda_back.b_domain.repository.ProductRepository;
import es.VetUp.tienda_back.b_domain.repository.UserRepository;
import es.VetUp.tienda_back.b_domain.repository.entity.CartEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.CartItemEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.ProductEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.UserEntity;
import es.VetUp.tienda_back.b_domain.service.CartService;
import es.VetUp.tienda_back.b_domain.service.dto.CartDto;
import es.VetUp.tienda_back.b_domain.service.dto.UserDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository,
                           ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<CartDto> getAllCarts() {
        return cartRepository.getAllCarts()
                .stream()
                .map(CartMapper.getInstance()::fromCartEntityToCart)
                .map(CartMapper.getInstance()::fromCartToCartDto)
                .toList();
    }

    @Override
    public Optional<CartDto> getCartById(Long id) {
        return cartRepository.getCartById(id)
                .map(CartMapper.getInstance()::fromCartEntityToCart)
                .map(CartMapper.getInstance()::fromCartToCartDto);
    }

    @Override
    public Optional<CartDto> getCartByUserId(Long userId) {
        return cartRepository.getCartByUserId(userId)
                .map(CartMapper.getInstance()::fromCartEntityToCart)
                .map(CartMapper.getInstance()::fromCartToCartDto);
    }

    @Override
    public CartDto createCart(CartDto cartDto) {
        CartEntity cartEntity = CartMapper.getInstance().fromCartToCartEntity(
                CartMapper.getInstance().fromCartDtoToCart(cartDto)
        );
        CartEntity createdCartEntity = cartRepository.createCart(cartEntity);
        return CartMapper.getInstance().fromCartToCartDto(
                CartMapper.getInstance().fromCartEntityToCart(createdCartEntity)
        );
    }

    @Override
    public CartDto updateCart(CartDto cartDto) {
        cartRepository.getCartById(cartDto.id())
                .orElseThrow(() -> new BusinessException("Cart with id " + cartDto.id() + " does not exist"));
        CartEntity cartEntity = CartMapper.getInstance().fromCartToCartEntity(
                CartMapper.getInstance().fromCartDtoToCart(cartDto)
        );
        CartEntity updatedCartEntity = cartRepository.updateCart(cartEntity);
        return CartMapper.getInstance().fromCartToCartDto(
                CartMapper.getInstance().fromCartEntityToCart(updatedCartEntity)
        );
    }

    @Override
    public void deleteCart(Long id) {
        cartRepository.getCartById(id)
                .orElseThrow(() -> new BusinessException("Cart with id " + id + " does not exist"));
        cartRepository.deleteCart(id);
    }

    @Override
    public CartDto addProductToCart(Long userId, Long productId, Integer quantity) {
        UserEntity userEntity = userRepository.getClientById(userId)
                .orElseThrow(() -> new BusinessException("User with id " + userId + " not found"));

        ProductEntity productEntity = productRepository.findProductById(productId)
                .orElseThrow(() -> new BusinessException("Product with id " + productId + " not found"));

        Optional<CartEntity> existingCart = cartRepository.getCartByUserId(userId);
        CartEntity cartEntity;

        if (existingCart.isEmpty()) {
            CartEntity newCart = new CartEntity(
                    null,
                    0,
                    BigDecimal.ZERO,
                    userEntity,
                    new ArrayList<>()
            );
            cartEntity = cartRepository.createCart(newCart);
        } else {
            cartEntity = existingCart.get();
        }

        List<CartItemEntity> existingItems = cartItemRepository.getCartItemsByCartId(cartEntity.id());
        Optional<CartItemEntity> existingItem = existingItems.stream()
                .filter(item -> item.product().productId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItemEntity itemToUpdate = existingItem.get();
            CartItemEntity updatedItem = new CartItemEntity(
                    itemToUpdate.id(),
                    itemToUpdate.quantity() + quantity,
                    cartEntity.id(),
                    productEntity
            );
            cartItemRepository.updateCartItem(updatedItem);
        } else {
            CartItemEntity newItem = new CartItemEntity(
                    null,
                    quantity,
                    cartEntity.id(),
                    productEntity
            );
            cartItemRepository.createCartItem(newItem);
        }

        List<CartItemEntity> allItems = cartItemRepository.getCartItemsByCartId(cartEntity.id());
        int totalProducts = allItems.stream().mapToInt(CartItemEntity::quantity).sum();
        BigDecimal totalPrice = allItems.stream()
                .map(item -> {
                    BigDecimal effectivePrice = (item.product().price() != null && item.product().price().compareTo(BigDecimal.ZERO) > 0)
                            ? item.product().price()
                            : item.product().basePrice();
                    return effectivePrice.multiply(BigDecimal.valueOf(item.quantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        CartEntity updatedCart = new CartEntity(
                cartEntity.id(),
                totalProducts,
                totalPrice,
                userEntity,
                allItems
        );
        CartEntity finalCart = cartRepository.updateCart(updatedCart);

        return CartMapper.getInstance().fromCartToCartDto(
                CartMapper.getInstance().fromCartEntityToCart(finalCart)
        );
    }
}

