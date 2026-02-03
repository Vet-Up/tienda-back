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
import es.VetUp.tienda_back.b_domain.repository.entity.OrderEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.OrderJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.OrderJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.UserJpaEntity;
import es.VetUp.tienda_back.c_persistence.repository.mapper.OrderPersistenceMapper;

@ExtendWith(MockitoExtension.class)
class OrderRepositoryImplTest {

    @Mock
    private OrderJpaDao orderJpaDao;

    @InjectMocks
    private OrderRepositoryImpl orderRepositoryImpl;

    private OrderJpaEntity orderJpaEntity1;
    private OrderJpaEntity orderJpaEntity2;
    private UserJpaEntity userJpaEntity;

    @BeforeEach
    void setUp() {
        userJpaEntity = new UserJpaEntity(
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

        orderJpaEntity1 = new OrderJpaEntity();
        orderJpaEntity1.setTotal_products(2);
        orderJpaEntity1.setTotal_price(new BigDecimal("179.98"));
        orderJpaEntity1.setStatus(OrderState.ORDER);
        orderJpaEntity1.setUser(userJpaEntity);
        orderJpaEntity1.setCreatedAt(LocalDateTime.of(2026, 1, 1, 10, 0));
        orderJpaEntity1.setAddress("123 Delivery St");

        orderJpaEntity2 = new OrderJpaEntity();
        orderJpaEntity2.setTotal_products(1);
        orderJpaEntity2.setTotal_price(new BigDecimal("89.99"));
        orderJpaEntity2.setStatus(OrderState.CART);
        orderJpaEntity2.setUser(userJpaEntity);
        orderJpaEntity2.setCreatedAt(LocalDateTime.of(2026, 1, 2, 12, 0));
        orderJpaEntity2.setAddress("456 Delivery Ave");
    }

    @Nested
    class FindAllTest {
        @Test
        @DisplayName("Find all should return all orders")
        void testFindAll() {
            // Arrange
            List<OrderJpaEntity> jpaList = List.of(orderJpaEntity1, orderJpaEntity2);
            when(orderJpaDao.getAllOrders()).thenReturn(jpaList);

            // Act
            List<OrderEntity> actualEntities = orderRepositoryImpl.getAllOrders();

            // Assert
            assertAll(
                    () -> assertEquals(2, actualEntities.size()),
                    () -> assertEquals(2, actualEntities.get(0).totalProducts()),
                    () -> assertEquals(1, actualEntities.get(1).totalProducts())
            );
            verify(orderJpaDao).getAllOrders();
        }
    }

    @Nested
    class FindByIdTest {
        @Test
        @DisplayName("Find by id should return order")
        void testFindById() {
            // Arrange
            when(orderJpaDao.getOrderById(1L)).thenReturn(Optional.of(orderJpaEntity1));

            // Act
            Optional<OrderEntity> actualEntity = orderRepositoryImpl.getOrderById(1L);

            // Assert
            assertAll(
                    () -> assertTrue(actualEntity.isPresent()),
                    () -> assertEquals(2, actualEntity.get().totalProducts()),
                    () -> assertEquals(new BigDecimal("179.98"), actualEntity.get().totalPrice())
            );
            verify(orderJpaDao).getOrderById(1L);
        }

        @Test
        @DisplayName("Find by id should return empty when not found")
        void testFindByIdNotFound() {
            // Arrange
            when(orderJpaDao.getOrderById(999L)).thenReturn(Optional.empty());

            // Act
            Optional<OrderEntity> actualEntity = orderRepositoryImpl.getOrderById(999L);

            // Assert
            assertFalse(actualEntity.isPresent());
            verify(orderJpaDao).getOrderById(999L);
        }
    }

    @Nested
    class SaveTest {
        @Test
        @DisplayName("Save should save order")
        void testSave() {
            // Arrange
            OrderEntity orderToSave = OrderPersistenceMapper.getInstance()
                    .fromOrderJpaEntityToOrderEntity(orderJpaEntity1);

            when(orderJpaDao.createOrder(any(OrderJpaEntity.class))).thenReturn(orderJpaEntity1);

            // Act
            OrderEntity result = orderRepositoryImpl.createOrder(orderToSave);

            // Assert
            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(2, result.totalProducts()),
                    () -> assertEquals(new BigDecimal("179.98"), result.totalPrice())
            );
            verify(orderJpaDao).createOrder(any(OrderJpaEntity.class));
        }
    }

    @Nested
    class UpdateTest {
        @Test
        @DisplayName("Update should update order")
        void testUpdate() {
            // Arrange
            OrderEntity orderToUpdate = OrderPersistenceMapper.getInstance()
                    .fromOrderJpaEntityToOrderEntity(orderJpaEntity1);

            when(orderJpaDao.updateOrder(any(OrderJpaEntity.class))).thenReturn(orderJpaEntity1);

            // Act
            OrderEntity result = orderRepositoryImpl.updateOrder(orderToUpdate);

            // Assert
            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(2, result.totalProducts()),
                    () -> assertEquals(new BigDecimal("179.98"), result.totalPrice())
            );
            verify(orderJpaDao).updateOrder(any(OrderJpaEntity.class));
        }
    }

    @Nested
    class DeleteByIdTest {
        @Test
        @DisplayName("Delete by id should delete order")
        void testDeleteById() {
            // Act
            orderRepositoryImpl.deleteOrder(1L);

            // Assert
            verify(orderJpaDao).deleteOrder(1L);
        }
    }

    @Nested
    class FindByUserIdTest {
        @Test
        @DisplayName("Find by user id should return orders")
        void testFindByUserId() {
            // Arrange
            List<OrderJpaEntity> jpaList = List.of(orderJpaEntity1, orderJpaEntity2);
            when(orderJpaDao.getOrdersByUserId(1L)).thenReturn(jpaList);

            // Act
            List<OrderEntity> actualEntities = orderRepositoryImpl.getOrdersByUserId(1L);

            // Assert
            assertAll(
                    () -> assertEquals(2, actualEntities.size()),
                    () -> assertEquals(2, actualEntities.get(0).totalProducts()),
                    () -> assertEquals(1, actualEntities.get(1).totalProducts())
            );
            verify(orderJpaDao).getOrdersByUserId(1L);
        }
    }
}

