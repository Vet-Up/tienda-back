package es.VetUp.tienda_back.b_domain.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.VetUp.tienda_back.b_domain.model.Order;
import es.VetUp.tienda_back.b_domain.model.OrderItem;
import es.VetUp.tienda_back.b_domain.model.Product;
import es.VetUp.tienda_back.b_domain.model.User;
import es.VetUp.tienda_back.b_domain.model.enums.OrderState;
import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import es.VetUp.tienda_back.b_domain.repository.entity.OrderEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.OrderItemEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.ProductEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.UserEntity;
import es.VetUp.tienda_back.b_domain.service.dto.OrderDto;
import es.VetUp.tienda_back.b_domain.service.dto.OrderItemDto;
import es.VetUp.tienda_back.b_domain.service.dto.ProductDto;
import es.VetUp.tienda_back.b_domain.service.dto.UserDto;

class OrderMapperTest {

    private User user;
    private UserDto userDto;
    private UserEntity userEntity;
    private Product product;
    private ProductDto productDto;
    private ProductEntity productEntity;

    @BeforeEach
    void setUp() {
        user = new User(
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

        userDto = new UserDto(
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

        product = new Product(
                10L,
                "Product 1",
                "Description",
                new BigDecimal("99.99"),
                new BigDecimal("10.00"),
                "pic1.jpg",
                "Brand1",
                1L,
                100
        );

        productDto = new ProductDto(
                10L,
                "Product 1",
                "Description",
                new BigDecimal("99.99"),
                new BigDecimal("10.00"),
                new BigDecimal("89.99"),
                "pic1.jpg",
                "Brand1",
                1L,
                100,
                null,
                null
        );

        productEntity = new ProductEntity(
                10L,
                "Product 1",
                "Description",
                new BigDecimal("99.99"),
                new BigDecimal("89.99"),
                "pic1.jpg",
                "Brand1",
                1L,
                new BigDecimal("89.99"),
                100
        );
    }

    @Test
    @DisplayName("Test map OrderDto to Order")
    void testFromOrderDtoToOrder() {
        // Arrange
        OrderItemDto orderItemDto = new OrderItemDto(1L, 2, 1L, productDto);
        List<OrderItemDto> orderItems = new ArrayList<>();
        orderItems.add(orderItemDto);

        OrderDto orderDto = new OrderDto(
                1L,
                2,
                new BigDecimal("179.98"),
                OrderState.ORDER,
                userDto,
                LocalDateTime.of(2026, 1, 1, 10, 0),
                LocalDateTime.of(2026, 1, 2, 10, 0),
                "123 Delivery St",
                orderItems
        );

        // Act
        Order order = OrderMapper.getInstance().fromOrderDtoToOrder(orderDto);

        // Assert
        assertAll("order",
                () -> assertNotNull(order),
                () -> assertEquals(orderDto.id(), order.getId()),
                () -> assertEquals(orderDto.totalProducts(), order.getTotalProducts()),
                () -> assertEquals(orderDto.totalPrice(), order.getTotalPrice()),
                () -> assertEquals(orderDto.state(), order.getState()),
                () -> assertNotNull(order.getUser()),
                () -> assertEquals(userDto.id(), order.getUser().getId()),
                () -> assertEquals(orderDto.createdAt(), order.getCreatedAt()),
                () -> assertEquals(orderDto.orderAt(), order.getOrderAt()),
                () -> assertEquals(orderDto.address(), order.getAddress()),
                () -> assertNotNull(order.getOrderItems()),
                () -> assertEquals(1, order.getOrderItems().size()));
    }

    @Test
    @DisplayName("Test map null OrderDto to Order returns null")
    void testFromNullOrderDtoToOrder() {
        Order order = OrderMapper.getInstance().fromOrderDtoToOrder(null);
        assertNull(order);
    }

    @Test
    @DisplayName("Test map Order to OrderDto")
    void testFromOrderToOrderDto() {
        // Arrange
        OrderItem orderItem = new OrderItem(1L, 2, null, product);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);

        Order order = new Order(
                1L,
                2,
                new BigDecimal("179.98"),
                OrderState.ORDER,
                user,
                LocalDateTime.of(2026, 1, 1, 10, 0),
                LocalDateTime.of(2026, 1, 2, 10, 0),
                "123 Delivery St",
                orderItems
        );

        // Act
        OrderDto orderDto = OrderMapper.getInstance().fromOrderToOrderDto(order);

        // Assert
        assertAll("orderDto",
                () -> assertNotNull(orderDto),
                () -> assertEquals(order.getId(), orderDto.id()),
                () -> assertEquals(order.getTotalProducts(), orderDto.totalProducts()),
                () -> assertEquals(order.getTotalPrice(), orderDto.totalPrice()),
                () -> assertEquals(order.getState(), orderDto.state()),
                () -> assertNotNull(orderDto.user()),
                () -> assertEquals(user.getId(), orderDto.user().id()),
                () -> assertEquals(order.getCreatedAt(), orderDto.createdAt()),
                () -> assertEquals(order.getOrderAt(), orderDto.orderAt()),
                () -> assertEquals(order.getAddress(), orderDto.address()),
                () -> assertNotNull(orderDto.orderItems()),
                () -> assertEquals(1, orderDto.orderItems().size()));
    }

    @Test
    @DisplayName("Test map null Order to OrderDto returns null")
    void testFromNullOrderToOrderDto() {
        OrderDto orderDto = OrderMapper.getInstance().fromOrderToOrderDto(null);
        assertNull(orderDto);
    }

    @Test
    @DisplayName("Test map OrderEntity to Order")
    void testFromOrderEntityToOrder() {
        // Arrange
        OrderItemEntity orderItemEntity = new OrderItemEntity(1L, 2, 1L, productEntity);
        List<OrderItemEntity> orderItems = new ArrayList<>();
        orderItems.add(orderItemEntity);

        OrderEntity orderEntity = new OrderEntity(
                1L,
                2,
                new BigDecimal("179.98"),
                OrderState.ORDER,
                userEntity,
                LocalDateTime.of(2026, 1, 1, 10, 0),
                LocalDateTime.of(2026, 1, 2, 10, 0),
                "123 Delivery St",
                orderItems
        );

        // Act
        Order order = OrderMapper.getInstance().fromOrderEntityToOrder(orderEntity);

        // Assert
        assertAll("order",
                () -> assertNotNull(order),
                () -> assertEquals(orderEntity.id(), order.getId()),
                () -> assertEquals(orderEntity.totalProducts(), order.getTotalProducts()),
                () -> assertEquals(orderEntity.totalPrice(), order.getTotalPrice()),
                () -> assertEquals(orderEntity.state(), order.getState()),
                () -> assertNotNull(order.getUser()),
                () -> assertEquals(userEntity.id(), order.getUser().getId()),
                () -> assertEquals(orderEntity.createdAt(), order.getCreatedAt()),
                () -> assertEquals(orderEntity.orderAt(), order.getOrderAt()),
                () -> assertEquals(orderEntity.address(), order.getAddress()),
                () -> assertNotNull(order.getOrderItems()),
                () -> assertEquals(1, order.getOrderItems().size()));
    }

    @Test
    @DisplayName("Test map null OrderEntity to Order returns null")
    void testFromNullOrderEntityToOrder() {
        Order order = OrderMapper.getInstance().fromOrderEntityToOrder(null);
        assertNull(order);
    }

    @Test
    @DisplayName("Test map Order to OrderEntity")
    void testFromOrderToOrderEntity() {
        // Arrange
        OrderItem orderItem = new OrderItem(1L, 2, null, product);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);

        Order order = new Order(
                1L,
                2,
                new BigDecimal("179.98"),
                OrderState.ORDER,
                user,
                LocalDateTime.of(2026, 1, 1, 10, 0),
                LocalDateTime.of(2026, 1, 2, 10, 0),
                "123 Delivery St",
                orderItems
        );

        // Act
        OrderEntity orderEntity = OrderMapper.getInstance().fromOrderToOrderEntity(order);

        // Assert
        assertAll("orderEntity",
                () -> assertNotNull(orderEntity),
                () -> assertEquals(order.getId(), orderEntity.id()),
                () -> assertEquals(order.getTotalProducts(), orderEntity.totalProducts()),
                () -> assertEquals(order.getTotalPrice(), orderEntity.totalPrice()),
                () -> assertEquals(order.getState(), orderEntity.state()),
                () -> assertNotNull(orderEntity.user()),
                () -> assertEquals(user.getId(), orderEntity.user().id()),
                () -> assertEquals(order.getCreatedAt(), orderEntity.createdAt()),
                () -> assertEquals(order.getOrderAt(), orderEntity.orderAt()),
                () -> assertEquals(order.getAddress(), orderEntity.address()),
                () -> assertNotNull(orderEntity.orderItems()),
                () -> assertEquals(1, orderEntity.orderItems().size()));
    }

    @Test
    @DisplayName("Test map null Order to OrderEntity returns null")
    void testFromNullOrderToOrderEntity() {
        OrderEntity orderEntity = OrderMapper.getInstance().fromOrderToOrderEntity(null);
        assertNull(orderEntity);
    }

    @Test
    @DisplayName("Test map Order without order items to OrderDto")
    void testFromOrderWithoutOrderItemsToOrderDto() {
        // Arrange
        Order order = new Order(
                1L,
                0,
                BigDecimal.ZERO,
                OrderState.CART,
                user,
                LocalDateTime.of(2026, 1, 1, 10, 0),
                null,
                "123 Delivery St",
                null
        );

        // Act
        OrderDto orderDto = OrderMapper.getInstance().fromOrderToOrderDto(order);

        // Assert
        assertAll("orderDto",
                () -> assertNotNull(orderDto),
                () -> assertEquals(order.getId(), orderDto.id()),
                () -> assertNull(orderDto.orderItems()));
    }

    @Test
    @DisplayName("Test singleton pattern")
    void testGetInstance() {
        // Act
        OrderMapper instance1 = OrderMapper.getInstance();
        OrderMapper instance2 = OrderMapper.getInstance();

        // Assert
        assertNotNull(instance1);
        assertSame(instance1, instance2);
    }
}

