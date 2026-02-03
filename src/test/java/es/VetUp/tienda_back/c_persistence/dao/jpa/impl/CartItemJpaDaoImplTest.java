package es.VetUp.tienda_back.c_persistence.dao.jpa.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import es.VetUp.tienda_back.c_persistence.TestConfig;
import es.VetUp.tienda_back.c_persistence.dao.jpa.CartItemJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CartItemJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CartJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.ProductJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@DataJpaTest
@Import(TestConfig.class)
class CartItemJpaDaoImplTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CartItemJpaDao cartItemJpaDao;

    private CartJpaEntity cart;
    private ProductJpaEntity product;
    private UserJpaEntity user;

    @BeforeEach
    void setUp() {
        // Crear categoría
        CategoryJpaEntity category = new CategoryJpaEntity(null, "Test Category", "Description");
        entityManager.persist(category);

        // Crear producto
        product = new ProductJpaEntity(
                null,
                "Test Product",
                "Test Description",
                BigDecimal.valueOf(100.00),
                BigDecimal.valueOf(90.00),
                "test.jpg",
                "Test Brand",
                category,
                10
        );
        entityManager.persist(product);

        // Crear usuario
        long timestamp = System.currentTimeMillis();
        user = new UserJpaEntity(
                null,
                "Test User",
                "testuser_" + timestamp,
                "test_" + timestamp + "@test.com",
                "password",
                "Test Address",
                UserRole.CUSTOMER,
                123456789,
                "España",
                null,
                LocalDate.of(1990, 1, 1)
        );
        entityManager.persist(user);

        // Crear carrito
        cart = new CartJpaEntity();
        cart.setUser(user);
        entityManager.persist(cart);

        entityManager.flush();
    }

    @Test
    void testGetAllCartItems() {
        // Arrange
        CartItemJpaEntity cartItem = new CartItemJpaEntity();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(2);
        entityManager.persist(cartItem);
        entityManager.flush();

        // Act
        List<CartItemJpaEntity> result = cartItemJpaDao.getAllCartItems();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.stream().anyMatch(ci -> ci.getQuantity() == 2));
    }

    @Test
    void testGetCartItemById() {
        // Arrange
        CartItemJpaEntity cartItem = new CartItemJpaEntity();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(3);
        entityManager.persist(cartItem);
        entityManager.flush();

        // Act
        Optional<CartItemJpaEntity> result = cartItemJpaDao.getCartItemById(cartItem.getId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(3, result.get().getQuantity());
    }

    @Test
    void testGetCartItemById_NotFound() {
        // Act
        Optional<CartItemJpaEntity> result = cartItemJpaDao.getCartItemById(999999L);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testGetCartItemsByCartId() {
        // Arrange
        CartItemJpaEntity cartItem1 = new CartItemJpaEntity();
        cartItem1.setCart(cart);
        cartItem1.setProduct(product);
        cartItem1.setQuantity(2);
        entityManager.persist(cartItem1);

        CartItemJpaEntity cartItem2 = new CartItemJpaEntity();
        cartItem2.setCart(cart);
        cartItem2.setProduct(product);
        cartItem2.setQuantity(3);
        entityManager.persist(cartItem2);
        entityManager.flush();

        // Act
        List<CartItemJpaEntity> result = cartItemJpaDao.getCartItemsByCartId(cart.getId());

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testGetCartItemsByCartId_EmptyList() {
        // Act
        List<CartItemJpaEntity> result = cartItemJpaDao.getCartItemsByCartId(999999L);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testCreateCartItem() {
        // Arrange
        CartItemJpaEntity cartItem = new CartItemJpaEntity();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(5);

        // Act
        CartItemJpaEntity result = cartItemJpaDao.createCartItem(cartItem);

        // Assert
        assertNotNull(result.getId());
        assertEquals(5, result.getQuantity());
        assertEquals(cart.getId(), result.getCart().getId());
    }

    @Test
    void testUpdateCartItem() {
        // Arrange
        CartItemJpaEntity cartItem = new CartItemJpaEntity();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(2);
        entityManager.persist(cartItem);
        entityManager.flush();

        cartItem.setQuantity(10);

        // Act
        CartItemJpaEntity result = cartItemJpaDao.updateCartItem(cartItem);

        // Assert
        assertNotNull(result);
        assertEquals(10, result.getQuantity());
    }

    @Test
    void testDeleteCartItem() {
        // Arrange
        CartItemJpaEntity cartItem = new CartItemJpaEntity();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(2);
        entityManager.persist(cartItem);
        entityManager.flush();
        Long cartItemId = cartItem.getId();

        // Act
        cartItemJpaDao.deleteCartItem(cartItemId);
        entityManager.flush();

        // Assert
        Optional<CartItemJpaEntity> result = cartItemJpaDao.getCartItemById(cartItemId);
        assertFalse(result.isPresent());
    }

    @Test
    void testDeleteCartItem_NotFound() {
        // Act & Assert - no debería lanzar excepción
        assertDoesNotThrow(() -> cartItemJpaDao.deleteCartItem(999999L));
    }

    @Test
    void testUpdateCartItem_NotFound() {
        // Arrange
        CartItemJpaEntity cartItem = new CartItemJpaEntity();
        cartItem.setId(999999L);
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(10);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> cartItemJpaDao.updateCartItem(cartItem));
    }
}

