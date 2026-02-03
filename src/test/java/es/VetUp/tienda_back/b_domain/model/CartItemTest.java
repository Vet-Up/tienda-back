package es.VetUp.tienda_back.b_domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CartItemTest {

    @Test
    @DisplayName("Test CartItem Creation")
    void testCartItemCreation() {
        Long id = 1L;
        Integer quantity = 3;

        User user = new User(1L, "Test User", "testuser", "test@example.com",
                           "password", "Test Address", UserRole.CUSTOMER, 123456789,
                           "Spain", null, LocalDate.of(1990, 1, 1));
        Cart cart = new Cart(1L, 0, BigDecimal.ZERO, user, new ArrayList<>());

        Product product = new Product(1L, "Test Product", "Test Description",
                                    new BigDecimal("19.99"), BigDecimal.ZERO,
                                    "http://test.com/image.jpg", "Test Brand",
                                    1L, 10);

        CartItem cartItem = assertDoesNotThrow(() -> new CartItem(id, quantity, cart, product));

        assertAll("cartItem",
                () -> assertEquals(id, cartItem.getId()),
                () -> assertEquals(quantity, cartItem.getQuantity()),
                () -> assertEquals(cart, cartItem.getCart()),
                () -> assertEquals(product, cartItem.getProduct()));
    }

    @Test
    @DisplayName("Test CartItem with Null Values")
    void testCartItemWithNullValues() {
        CartItem cartItem = assertDoesNotThrow(() -> new CartItem(null, null, null, null));

        assertAll("cartItem",
                () -> assertNull(cartItem.getId()),
                () -> assertNull(cartItem.getQuantity()),
                () -> assertNull(cartItem.getCart()),
                () -> assertNull(cartItem.getProduct()));
    }
}

