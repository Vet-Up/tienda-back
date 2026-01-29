package es.VetUp.tienda_back.b_domain.service.impl;

import es.VetUp.tienda_back.b_domain.exception.BusinessException;
import es.VetUp.tienda_back.b_domain.mapper.CartItemMapper;
import es.VetUp.tienda_back.b_domain.repository.CartItemRepository;
import es.VetUp.tienda_back.b_domain.repository.CartRepository;
import es.VetUp.tienda_back.b_domain.repository.ProductRepository;
import es.VetUp.tienda_back.b_domain.repository.entity.CartEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.CartItemEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.ProductEntity;
import es.VetUp.tienda_back.b_domain.service.CartItemService;
import es.VetUp.tienda_back.b_domain.service.dto.CartItemDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class CartItemServiceImpl implements CartItemService {

    private static final Logger log = LoggerFactory.getLogger(CartItemServiceImpl.class);

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, CartRepository cartRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<CartItemDto> getAllCartItems() {
        return cartItemRepository.getAllCartItems()
                .stream()
                .map(CartItemMapper.getInstance()::fromCartItemEntityToCartItem)
                .map(CartItemMapper.getInstance()::fromCartItemToCartItemDto)
                .toList();
    }

    @Override
    public Optional<CartItemDto> getCartItemById(Long id) {
        return cartItemRepository.getCartItemById(id)
                .map(CartItemMapper.getInstance()::fromCartItemEntityToCartItem)
                .map(CartItemMapper.getInstance()::fromCartItemToCartItemDto);
    }

    @Override
    public List<CartItemDto> getCartItemsByCartId(Long cartId) {
        return cartItemRepository.getCartItemsByCartId(cartId)
                .stream()
                .map(CartItemMapper.getInstance()::fromCartItemEntityToCartItem)
                .map(CartItemMapper.getInstance()::fromCartItemToCartItemDto)
                .toList();
    }

    @Override
    public CartItemDto createCartItem(CartItemDto cartItemDto) {
        CartItemEntity cartItemEntity = CartItemMapper.getInstance().fromCartItemToCartItemEntity(
                CartItemMapper.getInstance().fromCartItemDtoToCartItem(cartItemDto)
        );
        CartItemEntity createdCartItemEntity = cartItemRepository.createCartItem(cartItemEntity);

        if (createdCartItemEntity != null && createdCartItemEntity.cartId() != null) {
            recalculateAndUpdateCart(createdCartItemEntity.cartId());
        }

        return CartItemMapper.getInstance().fromCartItemToCartItemDto(
                CartItemMapper.getInstance().fromCartItemEntityToCartItem(createdCartItemEntity)
        );
    }

    @Override
    public CartItemDto updateCartItem(CartItemDto cartItemDto) {
        CartItemEntity existing = cartItemRepository.getCartItemById(cartItemDto.id())
                .orElseThrow(() -> new BusinessException("CartItem with id " + cartItemDto.id() + " does not exist"));

        CartItemEntity toUpdate = new CartItemEntity(
                existing.id(),
                cartItemDto.quantity() != null ? cartItemDto.quantity() : existing.quantity(),
                existing.cartId(),
                existing.product()
        );

        CartItemEntity updated = cartItemRepository.updateCartItem(toUpdate);

        if (updated != null && updated.cartId() != null) {
            recalculateAndUpdateCart(updated.cartId());
        }

        return CartItemMapper.getInstance().fromCartItemToCartItemDto(
                CartItemMapper.getInstance().fromCartItemEntityToCartItem(updated)
        );
    }

    @Override
    public void deleteCartItem(Long id) {
        CartItemEntity existing = cartItemRepository.getCartItemById(id)
                .orElseThrow(() -> new BusinessException("CartItem with id " + id + " does not exist"));
        Long cartId = existing.cartId();
        cartItemRepository.deleteCartItem(id);

        if (cartId != null) {
            recalculateAndUpdateCart(cartId);
        }
    }

    private void recalculateAndUpdateCart(Long cartId) {
        List<CartItemEntity> items = cartItemRepository.getCartItemsByCartId(cartId);
        int totalProducts = items.stream().mapToInt(CartItemEntity::quantity).sum();
        BigDecimal totalPrice = items.stream()
                .map(item -> {
                    BigDecimal price = BigDecimal.ZERO;
                    if (item.product() != null) {
                        ProductEntity prod = item.product();
                        if (prod.price() != null) price = prod.price();
                        else if (prod.price() != null) price = prod.price();
                        else if (prod.basePrice() != null) price = prod.basePrice();
                    }
                    return price.multiply(BigDecimal.valueOf(item.quantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        CartEntity cartEntity = cartRepository.getCartById(cartId)
                .orElseThrow(() -> new BusinessException("Cart with id " + cartId + " not found when recalculating totals"));

        CartEntity updated = new CartEntity(
                cartEntity.id(),
                totalProducts,
                totalPrice,
                cartEntity.user(),
                items
        );

        cartRepository.updateCart(updated);
    }
}
