package es.VetUp.tienda_back.b_domain.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.VetUp.tienda_back.b_domain.model.Cart;
import es.VetUp.tienda_back.b_domain.model.CartItem;
import es.VetUp.tienda_back.b_domain.model.User;
import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import es.VetUp.tienda_back.b_domain.repository.entity.CartEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.UserEntity;
import es.VetUp.tienda_back.b_domain.service.dto.CartDto;
import es.VetUp.tienda_back.b_domain.service.dto.UserDto;

class CartMapperTest {

    @Test
    @DisplayName("Test map Cart to CartDto")
    void testMapCartToCartDto() {
        User user = new User(1L, "Test User", "testuser", "test@example.com",
                           "password", "Test Address", UserRole.CUSTOMER, 123456789,
                           "Spain", null, LocalDate.of(1990, 1, 1));
        List<CartItem> cartItems = new ArrayList<>();
        Cart cart = new Cart(1L, 5, new BigDecimal("99.99"), user, cartItems);

        CartDto cartDto = CartMapper.getInstance().fromCartToCartDto(cart);

        assertAll("cartDto",
                () -> assertEquals(cart.getId(), cartDto.id()),
                () -> assertEquals(cart.getTotalProducts(), cartDto.totalProducts()),
                () -> assertEquals(cart.getTotalPrice(), cartDto.totalPrice()),
                () -> assertNotNull(cartDto.user()),
                () -> assertEquals(cart.getUser().getId(), cartDto.user().id()));
    }

    @Test
    @DisplayName("Test map CartDto to Cart")
    void testMapCartDtoToCart() {
        UserDto userDto = new UserDto(1L, "Test User", "testuser", "test@example.com",
                                    "password", "Test Address", UserRole.CUSTOMER, 123456789,
                                    "Spain", null, LocalDate.of(1990, 1, 1));
        CartDto cartDto = new CartDto(1L, 5, new BigDecimal("99.99"), userDto, new ArrayList<>());

        Cart cart = CartMapper.getInstance().fromCartDtoToCart(cartDto);

        assertAll("cart",
                () -> assertEquals(cartDto.id(), cart.getId()),
                () -> assertEquals(cartDto.totalProducts(), cart.getTotalProducts()),
                () -> assertEquals(cartDto.totalPrice(), cart.getTotalPrice()),
                () -> assertNotNull(cart.getUser()),
                () -> assertEquals(cartDto.user().id(), cart.getUser().getId()));
    }

    @Test
    @DisplayName("Test map CartEntity to Cart")
    void testMapCartEntityToCart() {
        UserEntity userEntity = new UserEntity(1L, "Test User", "testuser", "test@example.com",
                                             "password", "Test Address", UserRole.CUSTOMER, 123456789,
                                             "Spain", null, LocalDate.of(1990, 1, 1));
        CartEntity cartEntity = new CartEntity(1L, 5, new BigDecimal("99.99"), userEntity, new ArrayList<>());

        Cart cart = CartMapper.getInstance().fromCartEntityToCart(cartEntity);

        assertAll("cart",
                () -> assertEquals(cartEntity.id(), cart.getId()),
                () -> assertEquals(cartEntity.totalProducts(), cart.getTotalProducts()),
                () -> assertEquals(cartEntity.totalPrice(), cart.getTotalPrice()),
                () -> assertNotNull(cart.getUser()),
                () -> assertEquals(cartEntity.user().id(), cart.getUser().getId()));
    }

    @Test
    @DisplayName("Test map Cart to CartEntity")
    void testMapCartToCartEntity() {
        User user = new User(1L, "Test User", "testuser", "test@example.com",
                           "password", "Test Address", UserRole.CUSTOMER, 123456789,
                           "Spain", null, LocalDate.of(1990, 1, 1));
        Cart cart = new Cart(1L, 5, new BigDecimal("99.99"), user, new ArrayList<>());

        CartEntity cartEntity = CartMapper.getInstance().fromCartToCartEntity(cart);

        assertAll("cartEntity",
                () -> assertEquals(cart.getId(), cartEntity.id()),
                () -> assertEquals(cart.getTotalProducts(), cartEntity.totalProducts()),
                () -> assertEquals(cart.getTotalPrice(), cartEntity.totalPrice()),
                () -> assertNotNull(cartEntity.user()),
                () -> assertEquals(cart.getUser().getId(), cartEntity.user().id()));
    }

    @Test
    @DisplayName("Test map null Cart returns null")
    void testMapNullCartReturnsNull() {
        assertNull(CartMapper.getInstance().fromCartToCartDto(null));
        assertNull(CartMapper.getInstance().fromCartDtoToCart(null));
        assertNull(CartMapper.getInstance().fromCartEntityToCart(null));
        assertNull(CartMapper.getInstance().fromCartToCartEntity(null));
    }
}

