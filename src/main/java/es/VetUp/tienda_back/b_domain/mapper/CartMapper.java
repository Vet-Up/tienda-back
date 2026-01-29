package es.VetUp.tienda_back.b_domain.mapper;

import es.VetUp.tienda_back.b_domain.model.Cart;
import es.VetUp.tienda_back.b_domain.repository.entity.CartEntity;
import es.VetUp.tienda_back.b_domain.service.dto.CartDto;

import java.util.stream.Collectors;

public class CartMapper {
    private static CartMapper INSTANCE;

    private CartMapper() {
    }

    public static CartMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CartMapper();
        }
        return INSTANCE;
    }

    public Cart fromCartDtoToCart(CartDto cartDto) {
        if (cartDto == null) {
            return null;
        }
        return new Cart(
                cartDto.id(),
                cartDto.totalProducts(),
                cartDto.totalPrice(),
                UserMapper.getInstance().fromUserDtoToUser(cartDto.user()),
                cartDto.cartItems() == null ? null :
                        cartDto.cartItems().stream()
                                .map(CartItemMapper.getInstance()::fromCartItemDtoToCartItem)
                                .collect(Collectors.toList())
        );
    }

    public CartDto fromCartToCartDto(Cart cart) {
        if (cart == null) {
            return null;
        }
        return new CartDto(
                cart.getId(),
                cart.getTotalProducts(),
                cart.getTotalPrice(),
                UserMapper.getInstance().fromUserToUserDto(cart.getUser()),
                cart.getCartItems() == null ? null :
                        cart.getCartItems().stream()
                                .map(CartItemMapper.getInstance()::fromCartItemToCartItemDto)
                                .collect(Collectors.toList())
        );
    }

    public Cart fromCartEntityToCart(CartEntity cartEntity) {
        if (cartEntity == null) {
            return null;
        }
        return new Cart(
                cartEntity.id(),
                cartEntity.totalProducts(),
                cartEntity.totalPrice(),
                UserMapper.getInstance().fromUserEntityToUser(cartEntity.user()),
                cartEntity.cartItems() == null ? null :
                        cartEntity.cartItems().stream()
                                .map(CartItemMapper.getInstance()::fromCartItemEntityToCartItem)
                                .collect(Collectors.toList())
        );
    }

    public CartEntity fromCartToCartEntity(Cart cart) {
        if (cart == null) {
            return null;
        }
        return new CartEntity(
                cart.getId(),
                cart.getTotalProducts(),
                cart.getTotalPrice(),
                UserMapper.getInstance().fromUserToUserEntity(cart.getUser()),
                cart.getCartItems() == null ? null :
                        cart.getCartItems().stream()
                                .map(CartItemMapper.getInstance()::fromCartItemToCartItemEntity)
                                .collect(Collectors.toList())
        );
    }
}

