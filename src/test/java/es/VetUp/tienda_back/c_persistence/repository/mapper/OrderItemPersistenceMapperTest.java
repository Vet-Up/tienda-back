package es.VetUp.tienda_back.c_persistence.repository.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.VetUp.tienda_back.b_domain.model.enums.OrderState;
import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import es.VetUp.tienda_back.b_domain.repository.entity.OrderItemEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.ProductEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.OrderItemJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.OrderJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.ProductJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.UserJpaEntity;

class OrderItemPersistenceMapperTest {


    @Test
    @DisplayName("Test map from OrderItemJpaEntity to OrderItemEntity")
    void testFromOrderItemJpaEntityToOrderItemEntity() {
        // Arrange
        UserJpaEntity userJpaEntity = new UserJpaEntity(
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

        OrderJpaEntity orderJpaEntity = new OrderJpaEntity(
                1L,
                2,
                new BigDecimal("179.98"),
                OrderState.ORDER,
                userJpaEntity,
                LocalDateTime.of(2026, 1, 1, 10, 0),
                LocalDateTime.of(2026, 1, 1, 11, 0),
                "123 Delivery St"
        );

        ProductJpaEntity productJpaEntity = new ProductJpaEntity();
        productJpaEntity.setProductId(10L);
        productJpaEntity.setName("Producto 1");
        productJpaEntity.setProductDescription("Descripción producto 1");
        productJpaEntity.setBasePrice(new BigDecimal("99.99"));
        productJpaEntity.setPrice(new BigDecimal("89.99"));
        productJpaEntity.setPictureProduct("imagen1.jpg");
        productJpaEntity.setBrand("Marca1");
        productJpaEntity.setCategoryId(1L);
        productJpaEntity.setStock(100);

        OrderItemJpaEntity orderItemJpaEntity = new OrderItemJpaEntity();
        orderItemJpaEntity.setId(1L);
        orderItemJpaEntity.setQuantity(2);
        orderItemJpaEntity.setOrder(orderJpaEntity);
        orderItemJpaEntity.setProduct(productJpaEntity);

        // Act
        OrderItemEntity orderItemEntity = OrderItemPersistenceMapper.getInstance()
                .fromOrderItemJpaEntityToOrderItemEntity(orderItemJpaEntity);

        // Assert
        assertEquals(orderItemJpaEntity.getId(), orderItemEntity.id());
        assertEquals(orderItemJpaEntity.getQuantity(), orderItemEntity.quantity());
        assertEquals(orderJpaEntity.getId(), orderItemEntity.orderId());
        assertNotNull(orderItemEntity.product());
        assertEquals(productJpaEntity.getProductId(), orderItemEntity.product().productId());
    }

    @Test
    @DisplayName("Test map from OrderItemJpaEntity to OrderItemEntity with null")
    void testFromOrderItemJpaEntityToOrderItemEntityWithNull() {
        // Act
        OrderItemEntity orderItemEntity = OrderItemPersistenceMapper.getInstance()
                .fromOrderItemJpaEntityToOrderItemEntity(null);

        // Assert
        assertNull(orderItemEntity);
    }

    @Test
    @DisplayName("Test map from OrderItemEntity to OrderItemJpaEntity")
    void testFromOrderItemEntityToOrderItemJpaEntity() {
        // Arrange
        ProductEntity productEntity = new ProductEntity(
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

        OrderItemEntity orderItemEntity = new OrderItemEntity(
                1L,
                2,
                1L,
                productEntity
        );

        // Act
        OrderItemJpaEntity orderItemJpaEntity = OrderItemPersistenceMapper.getInstance()
                .fromOrderItemEntityToOrderItemJpaEntity(orderItemEntity);

        // Assert
        assertEquals(orderItemEntity.id(), orderItemJpaEntity.getId());
        assertEquals(orderItemEntity.quantity(), orderItemJpaEntity.getQuantity());
        assertNotNull(orderItemJpaEntity.getOrder());
        assertEquals(orderItemEntity.orderId(), orderItemJpaEntity.getOrder().getId());
        assertNotNull(orderItemJpaEntity.getProduct());
        assertEquals(productEntity.productId(), orderItemJpaEntity.getProduct().getProductId());
    }

    @Test
    @DisplayName("Test map from OrderItemEntity to OrderItemJpaEntity with null")
    void testFromOrderItemEntityToOrderItemJpaEntityWithNull() {
        // Act
        OrderItemJpaEntity orderItemJpaEntity = OrderItemPersistenceMapper.getInstance()
                .fromOrderItemEntityToOrderItemJpaEntity(null);

        // Assert
        assertNull(orderItemJpaEntity);
    }


    @Test
    @DisplayName("Test singleton pattern")
    void testGetInstance() {
        // Act
        OrderItemPersistenceMapper instance1 = OrderItemPersistenceMapper.getInstance();
        OrderItemPersistenceMapper instance2 = OrderItemPersistenceMapper.getInstance();

        // Assert
        assertNotNull(instance1);
        assertSame(instance1, instance2);
    }
}

