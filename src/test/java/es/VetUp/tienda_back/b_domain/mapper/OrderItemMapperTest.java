package es.VetUp.tienda_back.b_domain.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.VetUp.tienda_back.b_domain.model.Order;
import es.VetUp.tienda_back.b_domain.model.OrderItem;
import es.VetUp.tienda_back.b_domain.model.Product;
import es.VetUp.tienda_back.b_domain.model.enums.OrderState;
import es.VetUp.tienda_back.b_domain.repository.entity.OrderItemEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.ProductEntity;
import es.VetUp.tienda_back.b_domain.service.dto.OrderItemDto;
import es.VetUp.tienda_back.b_domain.service.dto.ProductDto;

import java.time.LocalDateTime;

class OrderItemMapperTest {

    private Product product;
    private ProductDto productDto;
    private ProductEntity productEntity;

    @BeforeEach
    void setUp() {
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
                new BigDecimal("10.00"),
                "pic1.jpg",
                "Brand1",
                1L,
                new BigDecimal("89.99"),
                100
        );
    }

    @Test
    @DisplayName("Test map OrderItem to OrderItemDto")
    void testMapOrderItemToOrderItemDto() {
        // Arrange
        Order order = new Order(
                1L,
                2,
                new BigDecimal("179.98"),
                OrderState.ORDER,
                null,
                LocalDateTime.now(),
                null,
                "123 Delivery St",
                null
        );
        OrderItem orderItem = new OrderItem(1L, 2, order, product);

        // Act
        OrderItemDto result = OrderItemMapper.getInstance().fromOrderItemToOrderItemDto(orderItem);

        // Assert
        assertAll("orderItemDto",
                () -> assertNotNull(result),
                () -> assertEquals(orderItem.getId(), result.id()),
                () -> assertEquals(orderItem.getQuantity(), result.quantity()),
                () -> assertEquals(order.getId(), result.orderId()),
                () -> assertNotNull(result.product()),
                () -> assertEquals(product.getProductId(), result.product().productId()));
    }

    @Test
    @DisplayName("Test map OrderItem without order to OrderItemDto")
    void testMapOrderItemWithoutOrderToOrderItemDto() {
        // Arrange
        OrderItem orderItem = new OrderItem(1L, 2, null, product);

        // Act
        OrderItemDto result = OrderItemMapper.getInstance().fromOrderItemToOrderItemDto(orderItem);

        // Assert
        assertAll("orderItemDto",
                () -> assertNotNull(result),
                () -> assertEquals(orderItem.getId(), result.id()),
                () -> assertEquals(orderItem.getQuantity(), result.quantity()),
                () -> assertNull(result.orderId()),
                () -> assertNotNull(result.product()));
    }

    @Test
    @DisplayName("Test map null OrderItem to OrderItemDto returns null")
    void testMapNullOrderItemToOrderItemDto() {
        OrderItemDto result = OrderItemMapper.getInstance().fromOrderItemToOrderItemDto(null);
        assertNull(result);
    }

    @Test
    @DisplayName("Test map OrderItemDto to OrderItem")
    void testMapOrderItemDtoToOrderItem() {
        // Arrange
        OrderItemDto orderItemDto = new OrderItemDto(1L, 2, 1L, productDto);

        // Act
        OrderItem result = OrderItemMapper.getInstance().fromOrderItemDtoToOrderItem(orderItemDto);

        // Assert
        assertAll("orderItem",
                () -> assertNotNull(result),
                () -> assertEquals(orderItemDto.id(), result.getId()),
                () -> assertEquals(orderItemDto.quantity(), result.getQuantity()),
                () -> assertNull(result.getOrder()), // No se mapea para evitar referencias circulares
                () -> assertNotNull(result.getProduct()),
                () -> assertEquals(productDto.productId(), result.getProduct().getProductId()));
    }

    @Test
    @DisplayName("Test map null OrderItemDto to OrderItem returns null")
    void testMapNullOrderItemDtoToOrderItem() {
        OrderItem result = OrderItemMapper.getInstance().fromOrderItemDtoToOrderItem(null);
        assertNull(result);
    }

    @Test
    @DisplayName("Test map OrderItemEntity to OrderItem")
    void testMapOrderItemEntityToOrderItem() {
        // Arrange
        OrderItemEntity orderItemEntity = new OrderItemEntity(1L, 2, 1L, productEntity);

        // Act
        OrderItem result = OrderItemMapper.getInstance().fromOrderItemEntityToOrderItem(orderItemEntity);

        // Assert
        assertAll("orderItem",
                () -> assertNotNull(result),
                () -> assertEquals(orderItemEntity.id(), result.getId()),
                () -> assertEquals(orderItemEntity.quantity(), result.getQuantity()),
                () -> assertNull(result.getOrder()), // No se mapea para evitar referencias circulares
                () -> assertNotNull(result.getProduct()),
                () -> assertEquals(productEntity.productId(), result.getProduct().getProductId()));
    }

    @Test
    @DisplayName("Test map null OrderItemEntity to OrderItem returns null")
    void testMapNullOrderItemEntityToOrderItem() {
        OrderItem result = OrderItemMapper.getInstance().fromOrderItemEntityToOrderItem(null);
        assertNull(result);
    }

    @Test
    @DisplayName("Test map OrderItem to OrderItemEntity")
    void testMapOrderItemToOrderItemEntity() {
        // Arrange
        Order order = new Order(
                1L,
                2,
                new BigDecimal("179.98"),
                OrderState.ORDER,
                null,
                LocalDateTime.now(),
                null,
                "123 Delivery St",
                null
        );
        OrderItem orderItem = new OrderItem(1L, 2, order, product);

        // Act
        OrderItemEntity result = OrderItemMapper.getInstance().fromOrderItemToOrderItemEntity(orderItem);

        // Assert
        assertAll("orderItemEntity",
                () -> assertNotNull(result),
                () -> assertEquals(orderItem.getId(), result.id()),
                () -> assertEquals(orderItem.getQuantity(), result.quantity()),
                () -> assertEquals(order.getId(), result.orderId()),
                () -> assertNotNull(result.product()),
                () -> assertEquals(product.getProductId(), result.product().productId()));
    }

    @Test
    @DisplayName("Test map null OrderItem to OrderItemEntity returns null")
    void testMapNullOrderItemToOrderItemEntity() {
        OrderItemEntity result = OrderItemMapper.getInstance().fromOrderItemToOrderItemEntity(null);
        assertNull(result);
    }

    @Test
    @DisplayName("Test map OrderItem list to OrderItemDto list")
    void testMapOrderItemListToOrderItemDtoList() {
        // Arrange
        OrderItem orderItem1 = new OrderItem(1L, 2, null, product);
        OrderItem orderItem2 = new OrderItem(2L, 3, null, product);
        List<OrderItem> orderItems = List.of(orderItem1, orderItem2);

        // Act
        List<OrderItemDto> result = OrderItemMapper.getInstance().fromOrderItemListToOrderItemDtoList(orderItems);

        // Assert
        assertAll("orderItemDtoList",
                () -> assertNotNull(result),
                () -> assertEquals(2, result.size()),
                () -> assertEquals(1L, result.get(0).id()),
                () -> assertEquals(2L, result.get(1).id()));
    }

    @Test
    @DisplayName("Test map null OrderItem list to OrderItemDto list returns null")
    void testMapNullOrderItemListToOrderItemDtoList() {
        List<OrderItemDto> result = OrderItemMapper.getInstance().fromOrderItemListToOrderItemDtoList(null);
        assertNull(result);
    }

    @Test
    @DisplayName("Test map OrderItemDto list to OrderItem list")
    void testMapOrderItemDtoListToOrderItemList() {
        // Arrange
        OrderItemDto orderItemDto1 = new OrderItemDto(1L, 2, 1L, productDto);
        OrderItemDto orderItemDto2 = new OrderItemDto(2L, 3, 1L, productDto);
        List<OrderItemDto> orderItemDtos = List.of(orderItemDto1, orderItemDto2);

        // Act
        List<OrderItem> result = OrderItemMapper.getInstance().fromOrderItemDtoListToOrderItemList(orderItemDtos);

        // Assert
        assertAll("orderItemList",
                () -> assertNotNull(result),
                () -> assertEquals(2, result.size()),
                () -> assertEquals(1L, result.get(0).getId()),
                () -> assertEquals(2L, result.get(1).getId()));
    }

    @Test
    @DisplayName("Test map null OrderItemDto list to OrderItem list returns null")
    void testMapNullOrderItemDtoListToOrderItemList() {
        List<OrderItem> result = OrderItemMapper.getInstance().fromOrderItemDtoListToOrderItemList(null);
        assertNull(result);
    }

    @Test
    @DisplayName("Test singleton pattern")
    void testGetInstance() {
        // Act
        OrderItemMapper instance1 = OrderItemMapper.getInstance();
        OrderItemMapper instance2 = OrderItemMapper.getInstance();

        // Assert
        assertNotNull(instance1);
        assertSame(instance1, instance2);
    }
}

