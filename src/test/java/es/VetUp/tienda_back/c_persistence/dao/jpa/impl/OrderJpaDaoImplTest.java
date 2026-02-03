package es.VetUp.tienda_back.c_persistence.dao.jpa.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import es.VetUp.tienda_back.b_domain.model.enums.OrderState;
import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import es.VetUp.tienda_back.c_persistence.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import es.VetUp.tienda_back.c_persistence.dao.jpa.OrderJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.OrderJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.UserJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(TestConfig.class)
class OrderJpaDaoImplTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private OrderJpaDao orderJpaDao;

    @Test
    void testGetAllOrders() {
        // Arrange: persiste un usuario y una orden
        UserJpaEntity user = new UserJpaEntity(
                null,
                "Test User",
                "testuser",
                "test@example.com",
                "password123",
                "123 Test St",
                UserRole.CUSTOMER,
                123456789,
                "USA",
                "profile.jpg",
                LocalDate.of(1990, 1, 1)
        );
        entityManager.persist(user);
        entityManager.flush();

        OrderJpaEntity order = new OrderJpaEntity();
        order.setTotal_products(1);
        order.setTotal_price(new BigDecimal("90.00"));
        order.setStatus(OrderState.ORDER);
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setAddress("123 Delivery St");
        entityManager.persist(order);
        entityManager.flush();

        // Act
        List<OrderJpaEntity> result = orderJpaDao.getAllOrders();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.stream().anyMatch(o -> o.getUser().getEmail().equals("test@example.com")));
    }

    @Test
    void testGetOrderById() {
        // Arrange: persiste un usuario y una orden
        UserJpaEntity user = new UserJpaEntity(
                null,
                "Test User",
                "testuser",
                "test@example.com",
                "password123",
                "123 Test St",
                UserRole.CUSTOMER,
                123456789,
                "USA",
                "profile.jpg",
                LocalDate.of(1990, 1, 1)
        );
        entityManager.persist(user);
        entityManager.flush();

        OrderJpaEntity order = new OrderJpaEntity();
        order.setTotal_products(1);
        order.setTotal_price(new BigDecimal("90.00"));
        order.setStatus(OrderState.ORDER);
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setAddress("123 Delivery St");
        entityManager.persist(order);
        entityManager.flush();
        Long orderId = order.getId();

        // Act
        var result = orderJpaDao.getOrderById(orderId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(orderId, result.get().getId());
    }

    @Test
    void testGetOrdersByUserId() {
        // Arrange: persiste un usuario y una orden
        UserJpaEntity user = new UserJpaEntity(
                null,
                "Test User",
                "testuser",
                "test@example.com",
                "password123",
                "123 Test St",
                UserRole.CUSTOMER,
                123456789,
                "USA",
                "profile.jpg",
                LocalDate.of(1990, 1, 1)
        );
        entityManager.persist(user);
        entityManager.flush();

        OrderJpaEntity order = new OrderJpaEntity();
        order.setTotal_products(1);
        order.setTotal_price(new BigDecimal("90.00"));
        order.setStatus(OrderState.ORDER);
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setAddress("123 Delivery St");
        entityManager.persist(order);
        entityManager.flush();

        // Act
        List<OrderJpaEntity> result = orderJpaDao.getOrdersByUserId(user.getId());

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(user.getId(), result.getFirst().getUser().getId());
    }

    @Test
    void testFindCartByUserId() {
        // Arrange: persiste un usuario y un carrito (orden con estado CART)
        UserJpaEntity user = new UserJpaEntity(
                null,
                "Test User",
                "testuser",
                "test@example.com",
                "password123",
                "123 Test St",
                UserRole.CUSTOMER,
                123456789,
                "USA",
                "profile.jpg",
                LocalDate.of(1990, 1, 1)
        );
        entityManager.persist(user);
        entityManager.flush();

        OrderJpaEntity cart = new OrderJpaEntity();
        cart.setTotal_products(1);
        cart.setTotal_price(new BigDecimal("90.00"));
        cart.setStatus(OrderState.CART);
        cart.setUser(user);
        cart.setCreatedAt(LocalDateTime.now());
        cart.setAddress("123 Delivery St");
        entityManager.persist(cart);
        entityManager.flush();

        // Act
        var result = orderJpaDao.findCartByUserId(user.getId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(OrderState.CART, result.get().getStatus());
    }

    @Test
    void testCreateOrder() {
        // Arrange: persiste un usuario
        UserJpaEntity user = new UserJpaEntity(
                null,
                "Test User",
                "testuser",
                "test@example.com",
                "password123",
                "123 Test St",
                UserRole.CUSTOMER,
                123456789,
                "USA",
                "profile.jpg",
                LocalDate.of(1990, 1, 1)
        );
        entityManager.persist(user);
        entityManager.flush();

        OrderJpaEntity order = new OrderJpaEntity();
        order.setTotal_products(1);
        order.setTotal_price(new BigDecimal("100.00"));
        order.setStatus(OrderState.ORDER);
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setAddress("456 Delivery St");

        // Act
        OrderJpaEntity createdOrder = orderJpaDao.createOrder(order);

        // Assert
        assertNotNull(createdOrder.getId());
        assertEquals(new BigDecimal("100.00"), createdOrder.getTotal_price());
    }

    @Test
    void testUpdateOrder() {
        // Arrange: persiste un usuario y una orden
        UserJpaEntity user = new UserJpaEntity(
                null,
                "Test User",
                "testuser",
                "test@example.com",
                "password123",
                "123 Test St",
                UserRole.CUSTOMER,
                123456789,
                "USA",
                "profile.jpg",
                LocalDate.of(1990, 1, 1)
        );
        entityManager.persist(user);
        entityManager.flush();

        OrderJpaEntity order = new OrderJpaEntity();
        order.setTotal_products(1);
        order.setTotal_price(new BigDecimal("90.00"));
        order.setStatus(OrderState.ORDER);
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setAddress("123 Delivery St");
        entityManager.persist(order);
        entityManager.flush();

        // Act
        order.setTotal_price(new BigDecimal("120.00"));
        OrderJpaEntity updatedOrder = orderJpaDao.updateOrder(order);

        // Assert
        assertEquals(new BigDecimal("120.00"), updatedOrder.getTotal_price());
    }

    @Test
    void testDeleteOrder() {
        // Arrange: persiste un usuario y una orden
        UserJpaEntity user = new UserJpaEntity(
                null,
                "Test User",
                "testuser",
                "test@example.com",
                "password123",
                "123 Test St",
                UserRole.CUSTOMER,
                123456789,
                "USA",
                "profile.jpg",
                LocalDate.of(1990, 1, 1)
        );
        entityManager.persist(user);
        entityManager.flush();

        OrderJpaEntity order = new OrderJpaEntity();
        order.setTotal_products(1);
        order.setTotal_price(new BigDecimal("90.00"));
        order.setStatus(OrderState.ORDER);
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setAddress("123 Delivery St");
        entityManager.persist(order);
        entityManager.flush();
        Long orderId = order.getId();

        // Act
        orderJpaDao.deleteOrder(orderId);
        OrderJpaEntity deletedOrder = entityManager.find(OrderJpaEntity.class, orderId);

        // Assert
        assertNull(deletedOrder);
    }
}

