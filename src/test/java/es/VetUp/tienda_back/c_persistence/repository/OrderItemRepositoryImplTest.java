package es.VetUp.tienda_back.c_persistence.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.VetUp.tienda_back.b_domain.model.enums.OrderState;
import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import es.VetUp.tienda_back.b_domain.repository.entity.OrderItemEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.ProductEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.OrderItemJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.OrderItemJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.OrderJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.ProductJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.UserJpaEntity;

@ExtendWith(MockitoExtension.class)
class OrderItemRepositoryImplTest {

    @Mock
    private OrderItemJpaDao orderItemJpaDao;

    @InjectMocks
    private OrderItemRepositoryImpl orderItemRepositoryImpl;

    private OrderItemJpaEntity orderItemJpaEntity1;
    private OrderItemJpaEntity orderItemJpaEntity2;
    private OrderJpaEntity orderJpaEntity;
    private ProductJpaEntity productJpaEntity;

    @BeforeEach
    void setUp() {
        UserJpaEntity userJpaEntity = new UserJpaEntity(
                1L,
                "John Doe",
                "johndoe",
                "johndoe@example.com",
                "password",
                "123 Main St",
                UserRole.CUSTOMER,
                123456789,
                "USA",
                "profile.jpg",
                LocalDate.of(1990, 1, 1)
        );

        orderJpaEntity = new OrderJpaEntity();
        orderJpaEntity.setTotal_products(2);
        orderJpaEntity.setTotal_price(new BigDecimal("179.98"));
        orderJpaEntity.setStatus(OrderState.ORDER);
        orderJpaEntity.setUser(userJpaEntity);
        orderJpaEntity.setCreatedAt(LocalDateTime.of(2026, 1, 1, 10, 0));
        orderJpaEntity.setAddress("123 Delivery St");

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

        orderItemJpaEntity1 = new OrderItemJpaEntity();
        orderItemJpaEntity1.setId(1L);
        orderItemJpaEntity1.setQuantity(2);
        orderItemJpaEntity1.setOrder(orderJpaEntity);
        orderItemJpaEntity1.setProduct(productJpaEntity);

        orderItemJpaEntity2 = new OrderItemJpaEntity();
        orderItemJpaEntity2.setId(2L);
        orderItemJpaEntity2.setQuantity(3);
        orderItemJpaEntity2.setOrder(orderJpaEntity);
        orderItemJpaEntity2.setProduct(productJpaEntity);
    }

    @Nested
    class FindAllTest {
        @Test
        @DisplayName("Find all should return all order items")
        void testFindAll() {
            // Arrange
            List<OrderItemJpaEntity> jpaList = List.of(orderItemJpaEntity1, orderItemJpaEntity2);
            when(orderItemJpaDao.findAll()).thenReturn(jpaList);

            // Act
            List<OrderItemEntity> actualEntities = orderItemRepositoryImpl.getAllOrderItems();

            // Assert
            assertAll(
                    () -> assertEquals(2, actualEntities.size()),
                    () -> assertEquals(2, actualEntities.get(0).quantity()),
                    () -> assertEquals(3, actualEntities.get(1).quantity())
            );
            verify(orderItemJpaDao).findAll();
        }
    }

    @Nested
    class FindByIdTest {
        @Test
        @DisplayName("Find by id should return order item")
        void testFindById() {
            // Arrange
            when(orderItemJpaDao.findById(1L)).thenReturn(Optional.of(orderItemJpaEntity1));

            // Act
            Optional<OrderItemEntity> actualEntity = orderItemRepositoryImpl.getOrderItemById(1L);

            // Assert
            assertAll(
                    () -> assertTrue(actualEntity.isPresent()),
                    () -> assertEquals(2, actualEntity.get().quantity())
            );
            verify(orderItemJpaDao).findById(1L);
        }

        @Test
        @DisplayName("Find by id should return empty when not found")
        void testFindByIdNotFound() {
            // Arrange
            when(orderItemJpaDao.findById(999L)).thenReturn(Optional.empty());

            // Act
            Optional<OrderItemEntity> actualEntity = orderItemRepositoryImpl.getOrderItemById(999L);

            // Assert
            assertFalse(actualEntity.isPresent());
            verify(orderItemJpaDao).findById(999L);
        }
    }

    @Nested
    class SaveTest {
        @Test
        @DisplayName("Save should save order item")
        void testSave() {
            // Arrange
            ProductEntity product = new ProductEntity(
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

            OrderItemEntity orderItemToSave = new OrderItemEntity(
                    null,
                    2,
                    1L,
                    product
            );

            OrderItemJpaEntity savedJpaEntity = orderItemJpaEntity1;
            when(orderItemJpaDao.save(any(OrderItemJpaEntity.class))).thenReturn(savedJpaEntity);

            // Act
            OrderItemEntity result = orderItemRepositoryImpl.createOrderItem(orderItemToSave);

            // Assert
            assertNotNull(result);
            assertEquals(orderItemToSave.quantity(), result.quantity());
            verify(orderItemJpaDao).save(any(OrderItemJpaEntity.class));
        }
    }

    @Nested
    class UpdateTest {
        @Test
        @DisplayName("Update should update order item")
        void testUpdate() {
            // Arrange
            ProductEntity product = new ProductEntity(
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

            OrderItemEntity orderItemToUpdate = new OrderItemEntity(
                    1L,
                    5,
                    1L,
                    product
            );

            OrderItemJpaEntity updatedJpaEntity = orderItemJpaEntity1;
            updatedJpaEntity.setQuantity(5);

            when(orderItemJpaDao.save(any(OrderItemJpaEntity.class))).thenReturn(updatedJpaEntity);

            // Act
            OrderItemEntity result = orderItemRepositoryImpl.updateOrderItem(orderItemToUpdate);

            // Assert
            assertNotNull(result);
            assertEquals(5, result.quantity());
            verify(orderItemJpaDao).save(any(OrderItemJpaEntity.class));
        }
    }

    @Nested
    class DeleteByIdTest {
        @Test
        @DisplayName("Delete by id should delete order item")
        void testDeleteById() {
            // Act
            orderItemRepositoryImpl.deleteOrderItem(1L);

            // Assert
            verify(orderItemJpaDao).deleteById(1L);
        }
    }

    @Nested
    class FindByOrderIdTest {
        @Test
        @DisplayName("Find by order id should return order items")
        void testFindByOrderId() {
            // Arrange
            List<OrderItemJpaEntity> jpaList = List.of(orderItemJpaEntity1, orderItemJpaEntity2);
            when(orderItemJpaDao.findByOrderId(1L)).thenReturn(jpaList);

            // Act
            List<OrderItemEntity> actualEntities = orderItemRepositoryImpl.getOrderItemsByOrderId(1L);

            // Assert
            assertAll(
                    () -> assertEquals(2, actualEntities.size()),
                    () -> assertEquals(2, actualEntities.get(0).quantity()),
                    () -> assertEquals(3, actualEntities.get(1).quantity())
            );
            verify(orderItemJpaDao).findByOrderId(1L);
        }
    }
}

