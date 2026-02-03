package es.VetUp.tienda_back.c_persistence.repository.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import es.VetUp.tienda_back.b_domain.repository.entity.CartEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.CartItemEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.ProductEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.UserEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CartItemJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CartJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.ProductJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.UserJpaEntity;

class CartPersistenceMapperTest {

    private UserJpaEntity userJpaEntity;
    private UserEntity userEntity;
    private ProductJpaEntity productJpaEntity;
    private ProductEntity productEntity;

    @BeforeEach
    void setUp() {
        userJpaEntity = new UserJpaEntity(
                1L,
                "John Doe",
                "johndoe",
                "john@example.com",
                "password123",
                "123 Main St",
                UserRole.CUSTOMER,
                123456789,
                "USA",
                "profile1.jpg",
                LocalDate.of(1990, 1, 1)
        );

        userEntity = new UserEntity(
                1L,
                "John Doe",
                "johndoe",
                "john@example.com",
                "password123",
                "123 Main St",
                UserRole.CUSTOMER,
                123456789,
                "USA",
                "profile1.jpg",
                LocalDate.of(1990, 1, 1)
        );

        productJpaEntity = new ProductJpaEntity();
        productJpaEntity.setProductId(10L);
        productJpaEntity.setName("Producto 1");
        productJpaEntity.setProductDescription("Descripción producto 1");
        productJpaEntity.setBasePrice(new BigDecimal("99.99"));
        productJpaEntity.setPrice(new BigDecimal("89.99"));
        productJpaEntity.setPictureProduct("imagen1.jpg");
        productJpaEntity.setBrand("Marca1");
        productJpaEntity.setCategoryId(1L);
        productJpaEntity.setStock(100);

        productEntity = new ProductEntity(
                10L,
                "Producto 1",
                "Descripción producto 1",
                new BigDecimal("99.99"),
                new BigDecimal("89.99"),
                "imagen1.jpg",
                "Marca1",
                1L,
                new BigDecimal("89.99"),
                100
        );
    }

    @Test
    @DisplayName("Test map from CartJpaEntity to CartEntity")
    void testFromCartJpaEntityToCartEntity() {
        // Arrange
        CartJpaEntity cartJpaEntity = new CartJpaEntity();
        cartJpaEntity.setId(1L);
        cartJpaEntity.setTotalProducts(2);
        cartJpaEntity.setTotalPrice(new BigDecimal("179.98"));
        cartJpaEntity.setUser(userJpaEntity);

        CartItemJpaEntity cartItemJpaEntity = new CartItemJpaEntity();
        cartItemJpaEntity.setId(1L);
        cartItemJpaEntity.setQuantity(2);
        cartItemJpaEntity.setCart(cartJpaEntity);
        cartItemJpaEntity.setProduct(productJpaEntity);

        List<CartItemJpaEntity> cartItems = new ArrayList<>();
        cartItems.add(cartItemJpaEntity);
        cartJpaEntity.setCartItems(cartItems);

        // Act
        CartEntity cartEntity = CartPersistenceMapper.getInstance().fromCartJpaEntityToCartEntity(cartJpaEntity);

        // Assert
        assertNotNull(cartEntity);
        assertEquals(cartJpaEntity.getId(), cartEntity.id());
        assertEquals(cartJpaEntity.getTotalProducts(), cartEntity.totalProducts());
        assertEquals(cartJpaEntity.getTotalPrice(), cartEntity.totalPrice());
        assertNotNull(cartEntity.user());
        assertEquals(userJpaEntity.getId(), cartEntity.user().id());
        assertNotNull(cartEntity.cartItems());
        assertEquals(1, cartEntity.cartItems().size());
        assertEquals(1L, cartEntity.cartItems().get(0).id());
    }

    @Test
    @DisplayName("Test map from CartJpaEntity to CartEntity with null")
    void testFromCartJpaEntityToCartEntityWithNull() {
        // Act
        CartEntity cartEntity = CartPersistenceMapper.getInstance().fromCartJpaEntityToCartEntity(null);

        // Assert
        assertNull(cartEntity);
    }

    @Test
    @DisplayName("Test map from CartJpaEntity to CartEntity without cart items")
    void testFromCartJpaEntityToCartEntityWithoutCartItems() {
        // Arrange
        CartJpaEntity cartJpaEntity = new CartJpaEntity();
        cartJpaEntity.setId(1L);
        cartJpaEntity.setTotalProducts(0);
        cartJpaEntity.setTotalPrice(BigDecimal.ZERO);
        cartJpaEntity.setUser(userJpaEntity);
        cartJpaEntity.setCartItems(null);

        // Act
        CartEntity cartEntity = CartPersistenceMapper.getInstance().fromCartJpaEntityToCartEntity(cartJpaEntity);

        // Assert
        assertNotNull(cartEntity);
        assertEquals(cartJpaEntity.getId(), cartEntity.id());
        assertNull(cartEntity.cartItems());
    }

    @Test
    @DisplayName("Test map from CartEntity to CartJpaEntity")
    void testFromCartEntityToCartJpaEntity() {
        // Arrange
        CartItemEntity cartItemEntity = new CartItemEntity(
                1L,
                2,
                1L,
                productEntity
        );

        List<CartItemEntity> cartItems = new ArrayList<>();
        cartItems.add(cartItemEntity);

        CartEntity cartEntity = new CartEntity(
                1L,
                2,
                new BigDecimal("179.98"),
                userEntity,
                cartItems
        );

        // Act
        CartJpaEntity cartJpaEntity = CartPersistenceMapper.getInstance().fromCartEntityToCartJpaEntity(cartEntity);

        // Assert
        assertNotNull(cartJpaEntity);
        assertEquals(cartEntity.id(), cartJpaEntity.getId());
        assertEquals(cartEntity.totalProducts(), cartJpaEntity.getTotalProducts());
        assertEquals(cartEntity.totalPrice(), cartJpaEntity.getTotalPrice());
        assertNotNull(cartJpaEntity.getUser());
        assertEquals(userEntity.id(), cartJpaEntity.getUser().getId());
        assertNotNull(cartJpaEntity.getCartItems());
        assertEquals(1, cartJpaEntity.getCartItems().size());
        assertEquals(1L, cartJpaEntity.getCartItems().get(0).getId());
        assertSame(cartJpaEntity, cartJpaEntity.getCartItems().get(0).getCart());
    }

    @Test
    @DisplayName("Test map from CartEntity to CartJpaEntity with null")
    void testFromCartEntityToCartJpaEntityWithNull() {
        // Act
        CartJpaEntity cartJpaEntity = CartPersistenceMapper.getInstance().fromCartEntityToCartJpaEntity(null);

        // Assert
        assertNull(cartJpaEntity);
    }

    @Test
    @DisplayName("Test map from CartEntity to CartJpaEntity without cart items")
    void testFromCartEntityToCartJpaEntityWithoutCartItems() {
        // Arrange
        CartEntity cartEntity = new CartEntity(
                1L,
                0,
                BigDecimal.ZERO,
                userEntity,
                new ArrayList<>()
        );

        // Act
        CartJpaEntity cartJpaEntity = CartPersistenceMapper.getInstance().fromCartEntityToCartJpaEntity(cartEntity);

        // Assert
        assertNotNull(cartJpaEntity);
        assertEquals(cartEntity.id(), cartJpaEntity.getId());
        assertNotNull(cartJpaEntity.getCartItems());
        assertTrue(cartJpaEntity.getCartItems().isEmpty());
    }

    @Test
    @DisplayName("Test singleton pattern")
    void testGetInstance() {
        // Act
        CartPersistenceMapper instance1 = CartPersistenceMapper.getInstance();
        CartPersistenceMapper instance2 = CartPersistenceMapper.getInstance();

        // Assert
        assertNotNull(instance1);
        assertSame(instance1, instance2);
    }
}

