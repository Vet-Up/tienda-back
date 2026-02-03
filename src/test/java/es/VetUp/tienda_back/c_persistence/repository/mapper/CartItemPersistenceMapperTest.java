package es.VetUp.tienda_back.c_persistence.repository.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.VetUp.tienda_back.b_domain.repository.entity.CartItemEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.ProductEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CartItemJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CartJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.ProductJpaEntity;

class CartItemPersistenceMapperTest {

    private ProductJpaEntity productJpaEntity;
    private ProductEntity productEntity;
    private CartJpaEntity cartJpaEntity;

    @BeforeEach
    void setUp() {
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

        cartJpaEntity = new CartJpaEntity();
        cartJpaEntity.setId(1L);
        cartJpaEntity.setTotalProducts(2);
        cartJpaEntity.setTotalPrice(new BigDecimal("179.98"));
    }

    @Test
    @DisplayName("Test map from CartItemJpaEntity to CartItemEntity")
    void testFromCartItemJpaEntityToCartItemEntity() {
        // Arrange
        CartItemJpaEntity cartItemJpaEntity = new CartItemJpaEntity();
        cartItemJpaEntity.setId(1L);
        cartItemJpaEntity.setQuantity(2);
        cartItemJpaEntity.setCart(cartJpaEntity);
        cartItemJpaEntity.setProduct(productJpaEntity);

        // Act
        CartItemEntity cartItemEntity = CartItemPersistenceMapper.getInstance()
                .fromCartItemJpaEntityToCartItemEntity(cartItemJpaEntity);

        // Assert
        assertNotNull(cartItemEntity);
        assertEquals(cartItemJpaEntity.getId(), cartItemEntity.id());
        assertEquals(cartItemJpaEntity.getQuantity(), cartItemEntity.quantity());
        assertEquals(cartJpaEntity.getId(), cartItemEntity.cartId());
        assertNotNull(cartItemEntity.product());
        assertEquals(productJpaEntity.getProductId(), cartItemEntity.product().productId());
    }

    @Test
    @DisplayName("Test map from CartItemJpaEntity to CartItemEntity with null")
    void testFromCartItemJpaEntityToCartItemEntityWithNull() {
        // Act
        CartItemEntity cartItemEntity = CartItemPersistenceMapper.getInstance()
                .fromCartItemJpaEntityToCartItemEntity(null);

        // Assert
        assertNull(cartItemEntity);
    }

    @Test
    @DisplayName("Test map from CartItemJpaEntity to CartItemEntity without cart")
    void testFromCartItemJpaEntityToCartItemEntityWithoutCart() {
        // Arrange
        CartItemJpaEntity cartItemJpaEntity = new CartItemJpaEntity();
        cartItemJpaEntity.setId(1L);
        cartItemJpaEntity.setQuantity(2);
        cartItemJpaEntity.setCart(null);
        cartItemJpaEntity.setProduct(productJpaEntity);

        // Act
        CartItemEntity cartItemEntity = CartItemPersistenceMapper.getInstance()
                .fromCartItemJpaEntityToCartItemEntity(cartItemJpaEntity);

        // Assert
        assertNotNull(cartItemEntity);
        assertEquals(cartItemJpaEntity.getId(), cartItemEntity.id());
        assertNull(cartItemEntity.cartId());
    }

    @Test
    @DisplayName("Test map from CartItemEntity to CartItemJpaEntity")
    void testFromCartItemEntityToCartItemJpaEntity() {
        // Arrange
        CartItemEntity cartItemEntity = new CartItemEntity(
                1L,
                2,
                1L,
                productEntity
        );

        // Act
        CartItemJpaEntity cartItemJpaEntity = CartItemPersistenceMapper.getInstance()
                .fromCartItemEntityToCartItemJpaEntity(cartItemEntity);

        // Assert
        assertNotNull(cartItemJpaEntity);
        assertEquals(cartItemEntity.id(), cartItemJpaEntity.getId());
        assertEquals(cartItemEntity.quantity(), cartItemJpaEntity.getQuantity());
        assertNotNull(cartItemJpaEntity.getCart());
        assertEquals(cartItemEntity.cartId(), cartItemJpaEntity.getCart().getId());
        assertNotNull(cartItemJpaEntity.getProduct());
        assertEquals(productEntity.productId(), cartItemJpaEntity.getProduct().getProductId());
    }

    @Test
    @DisplayName("Test map from CartItemEntity to CartItemJpaEntity with null")
    void testFromCartItemEntityToCartItemJpaEntityWithNull() {
        // Act
        CartItemJpaEntity cartItemJpaEntity = CartItemPersistenceMapper.getInstance()
                .fromCartItemEntityToCartItemJpaEntity(null);

        // Assert
        assertNull(cartItemJpaEntity);
    }

    @Test
    @DisplayName("Test map from CartItemEntity to CartItemJpaEntity without cart")
    void testFromCartItemEntityToCartItemJpaEntityWithoutCart() {
        // Arrange
        CartItemEntity cartItemEntity = new CartItemEntity(
                1L,
                2,
                null,
                productEntity
        );

        // Act
        CartItemJpaEntity cartItemJpaEntity = CartItemPersistenceMapper.getInstance()
                .fromCartItemEntityToCartItemJpaEntity(cartItemEntity);

        // Assert
        assertNotNull(cartItemJpaEntity);
        assertEquals(cartItemEntity.id(), cartItemJpaEntity.getId());
        assertEquals(cartItemEntity.quantity(), cartItemJpaEntity.getQuantity());
        assertNull(cartItemJpaEntity.getCart());
        assertNotNull(cartItemJpaEntity.getProduct());
    }

    @Test
    @DisplayName("Test singleton pattern")
    void testGetInstance() {
        // Act
        CartItemPersistenceMapper instance1 = CartItemPersistenceMapper.getInstance();
        CartItemPersistenceMapper instance2 = CartItemPersistenceMapper.getInstance();

        // Assert
        assertNotNull(instance1);
        assertSame(instance1, instance2);
    }
}

