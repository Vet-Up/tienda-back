package es.VetUp.tienda_back.b_domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CartTest {

    @Test
    @DisplayName("Test Cart Creation")
    void testCartCreation() {
        Long id = 1L;
        Integer totalProducts = 5;
        BigDecimal totalPrice = new BigDecimal("99.99");
        User user = new User(1L, "Test User", "testuser", "test@example.com",
                           "password", "Test Address", UserRole.CUSTOMER, 123456789,
                           "Spain", null, LocalDate.of(1990, 1, 1));
        List<CartItem> cartItems = new ArrayList<>();

        Cart cart = assertDoesNotThrow(() -> new Cart(id, totalProducts, totalPrice, user, cartItems));

        assertAll("cart",
                () -> assertEquals(id, cart.getId()),
                () -> assertEquals(totalProducts, cart.getTotalProducts()),
                () -> assertEquals(totalPrice, cart.getTotalPrice()),
                () -> assertEquals(user, cart.getUser()),
                () -> assertEquals(cartItems, cart.getCartItems()));
    }

    @Test
    @DisplayName("Test Cart with Null Values")
    void testCartWithNullValues() {
        Cart cart = assertDoesNotThrow(() -> new Cart(null, null, null, null, null));

        assertAll("cart",
                () -> assertNull(cart.getId()),
                () -> assertNull(cart.getTotalProducts()),
                () -> assertNull(cart.getTotalPrice()),
                () -> assertNull(cart.getUser()),
                () -> assertNull(cart.getCartItems()));
    }
}

