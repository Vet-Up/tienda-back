package es.VetUp.tienda_back.b_domain.mapper;

import es.VetUp.tienda_back.b_domain.model.CartItem;
import es.VetUp.tienda_back.b_domain.repository.entity.CartItemEntity;
import es.VetUp.tienda_back.b_domain.service.dto.CartItemDto;

public class CartItemMapper {
    private static CartItemMapper INSTANCE;

    private CartItemMapper() {
    }

    public static CartItemMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CartItemMapper();
        }
        return INSTANCE;
    }

    public CartItem fromCartItemDtoToCartItem(CartItemDto cartItemDto) {
        if (cartItemDto == null) {
            return null;
        }
        // mapear product sólo si no es null
        var product = cartItemDto.product() != null ? ProductMapper.getInstance().fromProductDtotoProduct(cartItemDto.product()) : null;
        return new CartItem(
                cartItemDto.id(),
                cartItemDto.quantity(),
                null,
                product
        );
    }

    public CartItemDto fromCartItemToCartItemDto(CartItem cartItem) {
        if (cartItem == null) {
            return null;
        }
        var productDto = cartItem.getProduct() != null ? ProductMapper.getInstance().fromProducttoProductDto(cartItem.getProduct()) : null;
        return new CartItemDto(
                cartItem.getId(),
                cartItem.getQuantity(),
                cartItem.getCart() != null ? cartItem.getCart().getId() : null,
                productDto
        );
    }

    public CartItem fromCartItemEntityToCartItem(CartItemEntity cartItemEntity) {
        if (cartItemEntity == null) {
            return null;
        }
        var product = cartItemEntity.product() != null ? ProductMapper.getInstance().fromProductEntitytoProduct(cartItemEntity.product()) : null;
        return new CartItem(
                cartItemEntity.id(),
                cartItemEntity.quantity(),
                null,
                product
        );
    }

    public CartItemEntity fromCartItemToCartItemEntity(CartItem cartItem) {
        if (cartItem == null) {
            return null;
        }
        var productEntity = cartItem.getProduct() != null ? ProductMapper.getInstance().fromProducttoProductEntity(cartItem.getProduct()) : null;
        return new CartItemEntity(
                cartItem.getId(),
                cartItem.getQuantity(),
                cartItem.getCart() != null ? cartItem.getCart().getId() : null,
                productEntity
        );
    }
}
