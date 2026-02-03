package es.VetUp.tienda_back.c_persistence.repository.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.VetUp.tienda_back.b_domain.model.enums.OrderState;
import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import es.VetUp.tienda_back.b_domain.repository.entity.OrderEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.OrderItemEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.ProductEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.UserEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.OrderItemJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.OrderJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.ProductJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.UserJpaEntity;

class OrderPersistenceMapperTest {

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
    @DisplayName("Test map from OrderJpaEntity to OrderEntity")
    void testFromOrderJpaEntityToOrderEntity() {
        // Arrange
        OrderJpaEntity orderJpaEntity = new OrderJpaEntity(
                1L,
                2,
                new BigDecimal("179.98"),
                OrderState.ORDER,
                userJpaEntity,
                LocalDateTime.of(2026, 1, 1, 10, 0),
                LocalDateTime.of(2026, 1, 2, 10, 0),
                "123 Delivery St"
        );

        OrderItemJpaEntity orderItemJpaEntity = new OrderItemJpaEntity();
        orderItemJpaEntity.setQuantity(2);
        orderItemJpaEntity.setOrder(orderJpaEntity);
        orderItemJpaEntity.setProduct(productJpaEntity);

        List<OrderItemJpaEntity> orderItems = new ArrayList<>();
        orderItems.add(orderItemJpaEntity);
        orderJpaEntity.setOrderItems(orderItems);

        // Act
        OrderEntity orderEntity = OrderPersistenceMapper.getInstance().fromOrderJpaEntityToOrderEntity(orderJpaEntity);

        // Assert
        assertNotNull(orderEntity);
        assertEquals(orderJpaEntity.getId(), orderEntity.id());
        assertEquals(orderJpaEntity.getTotal_products(), orderEntity.totalProducts());
        assertEquals(orderJpaEntity.getTotal_price(), orderEntity.totalPrice());
        assertEquals(orderJpaEntity.getStatus(), orderEntity.state());
        assertNotNull(orderEntity.user());
        assertEquals(userJpaEntity.getId(), orderEntity.user().id());
        assertEquals(orderJpaEntity.getCreatedAt(), orderEntity.createdAt());
        assertEquals(orderJpaEntity.getOrderAt(), orderEntity.orderAt());
        assertEquals(orderJpaEntity.getAddress(), orderEntity.address());
        assertNotNull(orderEntity.orderItems());
        assertEquals(1, orderEntity.orderItems().size());
    }

    @Test
    @DisplayName("Test map from OrderJpaEntity to OrderEntity with null")
    void testFromOrderJpaEntityToOrderEntityWithNull() {
        // Act
        OrderEntity orderEntity = OrderPersistenceMapper.getInstance().fromOrderJpaEntityToOrderEntity(null);

        // Assert
        assertNull(orderEntity);
    }

    @Test
    @DisplayName("Test map from OrderJpaEntity to OrderEntity without order items")
    void testFromOrderJpaEntityToOrderEntityWithoutOrderItems() {
        // Arrange
        OrderJpaEntity orderJpaEntity = new OrderJpaEntity(
                1L,
                0,
                BigDecimal.ZERO,
                OrderState.CART,
                userJpaEntity,
                LocalDateTime.of(2026, 1, 1, 10, 0),
                null,
                "123 Delivery St"
        );
        orderJpaEntity.setOrderItems(null);

        // Act
        OrderEntity orderEntity = OrderPersistenceMapper.getInstance().fromOrderJpaEntityToOrderEntity(orderJpaEntity);

        // Assert
        assertNotNull(orderEntity);
        assertEquals(orderJpaEntity.getId(), orderEntity.id());
        assertNull(orderEntity.orderItems());
    }

    @Test
    @DisplayName("Test map from OrderEntity to OrderJpaEntity")
    void testFromOrderEntityToOrderJpaEntity() {
        // Arrange
        OrderItemEntity orderItemEntity = new OrderItemEntity(
                1L,
                2,
                1L,
                productEntity
        );

        List<OrderItemEntity> orderItems = new ArrayList<>();
        orderItems.add(orderItemEntity);

        OrderEntity orderEntity = new OrderEntity(
                1L,
                2,
                new BigDecimal("179.98"),
                OrderState.CART,
                userEntity,
                LocalDateTime.of(2026, 1, 1, 10, 0),
                LocalDateTime.of(2026, 1, 2, 10, 0),
                "123 Delivery St",
                orderItems
        );

        // Act
        OrderJpaEntity orderJpaEntity = OrderPersistenceMapper.getInstance().fromOrderEntityToOrderJpaEntity(orderEntity);

        // Assert
        assertNotNull(orderJpaEntity);
        assertEquals(orderEntity.id(), orderJpaEntity.getId());
        assertEquals(orderEntity.totalProducts(), orderJpaEntity.getTotal_products());
        assertEquals(orderEntity.totalPrice(), orderJpaEntity.getTotal_price());
        assertEquals(orderEntity.state(), orderJpaEntity.getStatus());
        assertNotNull(orderJpaEntity.getUser());
        assertEquals(userEntity.id(), orderJpaEntity.getUser().getId());
        assertEquals(orderEntity.createdAt(), orderJpaEntity.getCreatedAt());
        assertEquals(orderEntity.orderAt(), orderJpaEntity.getOrderAt());
        assertEquals(orderEntity.address(), orderJpaEntity.getAddress());
    }

    @Test
    @DisplayName("Test map from OrderEntity to OrderJpaEntity with null")
    void testFromOrderEntityToOrderJpaEntityWithNull() {
        // Act
        OrderJpaEntity orderJpaEntity = OrderPersistenceMapper.getInstance().fromOrderEntityToOrderJpaEntity(null);

        // Assert
        assertNull(orderJpaEntity);
    }

    @Test
    @DisplayName("Test map from OrderEntity to OrderJpaEntity without order items")
    void testFromOrderEntityToOrderJpaEntityWithoutOrderItems() {
        // Arrange
        OrderEntity orderEntity = new OrderEntity(
                1L,
                0,
                BigDecimal.ZERO,
                OrderState.ORDER,
                userEntity,
                LocalDateTime.of(2026, 1, 1, 10, 0),
                null,
                "123 Delivery St",
                null
        );

        // Act
        OrderJpaEntity orderJpaEntity = OrderPersistenceMapper.getInstance().fromOrderEntityToOrderJpaEntity(orderEntity);

        // Assert
        assertNotNull(orderJpaEntity);
        assertEquals(orderEntity.id(), orderJpaEntity.getId());
        assertEquals(orderEntity.totalProducts(), orderJpaEntity.getTotal_products());
        assertNull(orderJpaEntity.getOrderAt());
    }

    @Test
    @DisplayName("Test singleton pattern")
    void testGetInstance() {
        // Act
        OrderPersistenceMapper instance1 = OrderPersistenceMapper.getInstance();
        OrderPersistenceMapper instance2 = OrderPersistenceMapper.getInstance();

        // Assert
        assertNotNull(instance1);
        assertSame(instance1, instance2);
    }
}

