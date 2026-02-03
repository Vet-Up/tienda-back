package es.VetUp.tienda_back.c_persistence.dao.jpa.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import es.VetUp.tienda_back.c_persistence.TestConfig;
import es.VetUp.tienda_back.c_persistence.dao.jpa.CartJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CartJpaEntity;
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
class CartJpaDaoImplTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CartJpaDao cartJpaDao;

    private UserJpaEntity user;

    @BeforeEach
    void setUp() {
        // Crear usuario con timestamp para unicidad
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
        entityManager.flush();
    }

    @Test
    void testGetAllCarts() {
        // Arrange
        CartJpaEntity cart = new CartJpaEntity();
        cart.setUser(user);
        entityManager.persist(cart);
        entityManager.flush();

        // Act
        List<CartJpaEntity> result = cartJpaDao.getAllCarts();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.stream().anyMatch(c -> c.getUser().getId().equals(user.getId())));
    }

    @Test
    void testGetCartById() {
        // Arrange
        CartJpaEntity cart = new CartJpaEntity();
        cart.setUser(user);
        entityManager.persist(cart);
        entityManager.flush();

        // Act
        Optional<CartJpaEntity> result = cartJpaDao.getCartById(cart.getId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user.getId(), result.get().getUser().getId());
    }

    @Test
    void testGetCartById_NotFound() {
        // Act
        Optional<CartJpaEntity> result = cartJpaDao.getCartById(999999L);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testGetCartByUserId() {
        // Arrange
        CartJpaEntity cart = new CartJpaEntity();
        cart.setUser(user);
        entityManager.persist(cart);
        entityManager.flush();

        // Act
        Optional<CartJpaEntity> result = cartJpaDao.getCartByUserId(user.getId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user.getId(), result.get().getUser().getId());
    }

    @Test
    void testGetCartByUserId_NotFound() {
        // Act
        Optional<CartJpaEntity> result = cartJpaDao.getCartByUserId(999999L);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testCreateCart() {
        // Arrange
        CartJpaEntity cart = new CartJpaEntity();
        cart.setUser(user);

        // Act
        CartJpaEntity result = cartJpaDao.createCart(cart);

        // Assert
        assertNotNull(result.getId());
        assertEquals(user.getId(), result.getUser().getId());
    }

    @Test
    void testUpdateCart() {
        // Arrange
        CartJpaEntity cart = new CartJpaEntity();
        cart.setUser(user);
        entityManager.persist(cart);
        entityManager.flush();

        // Crear nuevo usuario para actualizar
        long timestamp = System.currentTimeMillis();
        UserJpaEntity newUser = new UserJpaEntity(
                null,
                "New User",
                "newuser_" + timestamp,
                "new_" + timestamp + "@test.com",
                "password",
                "New Address",
                UserRole.CUSTOMER,
                987654321,
                "España",
                null,
                LocalDate.of(1995, 5, 5)
        );
        entityManager.persist(newUser);
        entityManager.flush();

        cart.setUser(newUser);

        // Act
        CartJpaEntity result = cartJpaDao.updateCart(cart);

        // Assert
        assertNotNull(result);
        assertEquals(newUser.getId(), result.getUser().getId());
    }

    @Test
    void testDeleteCart() {
        // Arrange
        CartJpaEntity cart = new CartJpaEntity();
        cart.setUser(user);
        entityManager.persist(cart);
        entityManager.flush();
        Long cartId = cart.getId();

        // Act
        cartJpaDao.deleteCart(cartId);
        entityManager.flush();

        // Assert
        Optional<CartJpaEntity> result = cartJpaDao.getCartById(cartId);
        assertFalse(result.isPresent());
    }

    @Test
    void testDeleteCart_NotFound() {
        // Act & Assert - no debería lanzar excepción
        assertDoesNotThrow(() -> cartJpaDao.deleteCart(999999L));
    }
}

