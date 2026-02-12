package es.VetUp.tienda_back.b_domain.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.VetUp.tienda_back.b_domain.exception.BusinessException;
import es.VetUp.tienda_back.b_domain.model.enums.OrderState;
import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import es.VetUp.tienda_back.b_domain.repository.CartItemRepository;
import es.VetUp.tienda_back.b_domain.repository.CartRepository;
import es.VetUp.tienda_back.b_domain.repository.OrderItemRepository;
import es.VetUp.tienda_back.b_domain.repository.OrderRepository;
import es.VetUp.tienda_back.b_domain.repository.UserRepository;
import es.VetUp.tienda_back.b_domain.repository.entity.CartEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.CartItemEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.OrderEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.OrderItemEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.ProductEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.UserEntity;
import es.VetUp.tienda_back.b_domain.service.dto.OrderDto;
import es.VetUp.tienda_back.infrastructure.PaymentGateway;
import es.VetUp.tienda_back.infrastructure.model.CardPaymentRequest;
import es.VetUp.tienda_back.infrastructure.model.CardPaymentResponse;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PaymentGateway paymentGateway;

    @InjectMocks
    private OrderServiceImpl orderServiceImpl;

    private UserEntity createUserEntity() {
        return new UserEntity(
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
    }

    private OrderEntity createOrderEntity() {
        return new OrderEntity(
                1L,
                2,
                new BigDecimal("179.98"),
                OrderState.ORDER,
                createUserEntity(),
                LocalDateTime.of(2026, 1, 1, 10, 0),
                null,
                "123 Delivery St",
                null
        );
    }

    @Nested
    class GetAllOrdersTests {
        @Test
        @DisplayName("getAllOrders should return list of OrderDto")
        void testGetAllOrders() {
            OrderEntity entity1 = createOrderEntity();
            OrderEntity entity2 = new OrderEntity(
                    2L,
                    1,
                    new BigDecimal("89.99"),
                    OrderState.ORDER,
                    createUserEntity(),
                    LocalDateTime.of(2026, 1, 2, 12, 0),
                    null,
                    "456 Delivery Ave",
                    null
            );

            when(orderRepository.getAllOrders()).thenReturn(List.of(entity1, entity2));

            List<OrderDto> result = orderServiceImpl.getAllOrders();

            assertAll("result",
                    () -> assertEquals(2, result.size()),
                    () -> assertEquals(1L, result.getFirst().id()),
                    () -> assertEquals(2L, result.get(1).id())
            );
        }

        @Test
        @DisplayName("getAllOrders should return empty list when no orders")
        void testGetAllOrdersEmpty() {
            when(orderRepository.getAllOrders()).thenReturn(List.of());

            List<OrderDto> result = orderServiceImpl.getAllOrders();

            assertTrue(result.isEmpty());
        }
    }

    @Nested
    class GetOrderByIdTests {
        @Test
        @DisplayName("getOrderById should return OrderDto when found")
        void testGetOrderById() {
            Long orderId = 1L;
            OrderEntity entity = createOrderEntity();

            when(orderRepository.getOrderById(orderId)).thenReturn(Optional.of(entity));

            Optional<OrderDto> result = orderServiceImpl.getOrderById(orderId);

            assertAll("result",
                    () -> assertTrue(result.isPresent()),
                    () -> assertEquals(orderId, result.orElseThrow().id()),
                    () -> assertEquals(2, result.orElseThrow().totalProducts())
            );
        }

        @Test
        @DisplayName("getOrderById should return empty when not found")
        void testGetOrderByIdNotFound() {
            Long orderId = 999L;
            when(orderRepository.getOrderById(orderId)).thenReturn(Optional.empty());

            Optional<OrderDto> result = orderServiceImpl.getOrderById(orderId);

            assertFalse(result.isPresent());
        }
    }

    @Nested
    class GetOrdersByUserIdTests {
        @Test
        @DisplayName("getOrdersByUserId should return list of OrderDto")
        void testGetOrdersByUserId() {
            Long userId = 1L;
            OrderEntity entity1 = createOrderEntity();
            OrderEntity entity2 = new OrderEntity(
                    2L,
                    1,
                    new BigDecimal("89.99"),
                    OrderState.ORDER,
                    createUserEntity(),
                    LocalDateTime.of(2026, 1, 2, 12, 0),
                    null,
                    "456 Delivery Ave",
                    null
            );

            when(orderRepository.getOrdersByUserId(userId)).thenReturn(List.of(entity1, entity2));

            List<OrderDto> result = orderServiceImpl.getOrdersByUserId(userId);

            assertAll("result",
                    () -> assertEquals(2, result.size()),
                    () -> assertEquals(1L, result.getFirst().id()),
                    () -> assertEquals(2L, result.get(1).id())
            );
        }

        @Test
        @DisplayName("getOrdersByUserId should return empty list when no orders")
        void testGetOrdersByUserIdEmpty() {
            Long userId = 1L;
            when(orderRepository.getOrdersByUserId(userId)).thenReturn(List.of());

            List<OrderDto> result = orderServiceImpl.getOrdersByUserId(userId);

            assertTrue(result.isEmpty());
        }
    }

    @Nested
    class CreateOrderTests {
        @Test
        @DisplayName("createOrder should create order successfully")
        void testCreateOrder() {
            OrderEntity entity = createOrderEntity();
            OrderDto orderDto = new OrderDto(
                    null,
                    2,
                    new BigDecimal("179.98"),
                    OrderState.ORDER,
                    null,
                    null,
                    null,
                    "123 Delivery St",
                    null
            );

            when(orderRepository.createOrder(any(OrderEntity.class))).thenReturn(entity);

            OrderDto result = orderServiceImpl.createOrder(orderDto);

            assertNotNull(result);
            assertEquals(1L, result.id());
            verify(orderRepository).createOrder(any(OrderEntity.class));
        }

        @Test
        @DisplayName("createOrder should throw exception when cart already exists")
        void testCreateOrderCartAlreadyExists() {
            UserEntity userEntity = createUserEntity();
            es.VetUp.tienda_back.b_domain.service.dto.UserDto userDto =
                new es.VetUp.tienda_back.b_domain.service.dto.UserDto(
                    userEntity.id(),
                    userEntity.name(),
                    userEntity.username(),
                    userEntity.email(),
                    userEntity.password(),
                    userEntity.address(),
                    userEntity.isAdmin(),
                    userEntity.phone(),
                    userEntity.country(),
                    userEntity.profilePicture(),
                    userEntity.birthdate()
                );

            OrderDto orderDto = new OrderDto(
                    null,
                    0,
                    BigDecimal.ZERO,
                    OrderState.CART,
                    userDto,
                    null,
                    null,
                    null,
                    null
            );

            when(orderRepository.findCartByUserId(1L)).thenReturn(Optional.of(createOrderEntity()));

            assertThrows(BusinessException.class, () -> orderServiceImpl.createOrder(orderDto));
        }
    }

    @Nested
    class UpdateOrderTests {
        @Test
        @DisplayName("updateOrder should update order successfully")
        void testUpdateOrder() {
            OrderEntity existingEntity = createOrderEntity();
            OrderEntity updatedEntity = new OrderEntity(
                    1L,
                    3,
                    new BigDecimal("269.97"),
                    OrderState.ORDER,
                    createUserEntity(),
                    LocalDateTime.of(2026, 1, 1, 10, 0),
                    LocalDateTime.now(),
                    "123 Delivery St",
                    null
            );

            OrderDto orderDto = new OrderDto(
                    1L,
                    3,
                    new BigDecimal("269.97"),
                    OrderState.ORDER,
                    null,
                    null,
                    null,
                    "123 Delivery St",
                    null
            );

            when(orderRepository.getOrderById(1L)).thenReturn(Optional.of(existingEntity));
            when(orderRepository.updateOrder(any(OrderEntity.class))).thenReturn(updatedEntity);

            OrderDto result = orderServiceImpl.updateOrder(orderDto);

            assertNotNull(result);
            assertEquals(3, result.totalProducts());
            verify(orderRepository).updateOrder(any(OrderEntity.class));
        }

        @Test
        @DisplayName("updateOrder should throw exception when order not found")
        void testUpdateOrderNotFound() {
            OrderDto orderDto = new OrderDto(
                    999L,
                    2,
                    new BigDecimal("179.98"),
                    OrderState.ORDER,
                    null,
                    null,
                    null,
                    "123 Delivery St",
                    null
            );

            when(orderRepository.getOrderById(999L)).thenReturn(Optional.empty());

            assertThrows(BusinessException.class, () -> orderServiceImpl.updateOrder(orderDto));
        }
    }

    @Nested
    class DeleteOrderTests {
        @Test
        @DisplayName("deleteOrder should delete order successfully")
        void testDeleteOrder() {
            Long orderId = 1L;
            when(orderRepository.getOrderById(orderId)).thenReturn(Optional.of(createOrderEntity()));

            orderServiceImpl.deleteOrder(orderId);

            verify(orderRepository).deleteOrder(orderId);
        }

        @Test
        @DisplayName("deleteOrder should throw exception when order not found")
        void testDeleteOrderNotFound() {
            Long orderId = 999L;
            when(orderRepository.getOrderById(orderId)).thenReturn(Optional.empty());

            assertThrows(BusinessException.class, () -> orderServiceImpl.deleteOrder(orderId));
        }
    }

    @Nested
    class CheckoutTests {
        @Test
        @DisplayName("checkout should create order from cart successfully")
        void testCheckout() {
            Long userId = 1L;
            String address = "123 Delivery St";
            CardPaymentRequest paymentRequest = new CardPaymentRequest(null, null, null, null);
            CardPaymentResponse paymentResponse = new CardPaymentResponse("txn123", "success", "Payment successful");

            UserEntity userEntity = createUserEntity();
            CartEntity cartEntity = new CartEntity(
                    1L,
                    2,
                    new BigDecimal("179.98"),
                    userEntity,
                    null
            );

            ProductEntity product = new ProductEntity(
                    10L,
                    "Product 1",
                    "Description",
                    new BigDecimal("99.99"),
                    new BigDecimal("89.99"),
                    "pic1.jpg",
                    "Brand1",
                    1L,
                    new BigDecimal("89.99"),
                    10
            );

            CartItemEntity cartItem = new CartItemEntity(1L, 2, 1L, product);
            OrderEntity createdOrder = createOrderEntity();

            when(paymentGateway.payment(paymentRequest)).thenReturn(paymentResponse);
            when(userRepository.getClientById(userId)).thenReturn(Optional.of(userEntity));
            when(cartRepository.getCartByUserId(userId)).thenReturn(Optional.of(cartEntity));
            when(cartItemRepository.getCartItemsByCartId(1L)).thenReturn(List.of(cartItem));
            when(orderRepository.createOrder(any(OrderEntity.class))).thenReturn(createdOrder);
            when(orderItemRepository.createOrderItem(any(OrderItemEntity.class)))
                    .thenReturn(new OrderItemEntity(1L, 2, 1L, product));
            when(cartRepository.updateCart(any(CartEntity.class))).thenReturn(cartEntity);
            when(orderRepository.getOrderById(1L)).thenReturn(Optional.of(createdOrder));

            OrderDto result = orderServiceImpl.checkout(userId, address, paymentRequest);

            assertNotNull(result);
            assertEquals(1L, result.id());
            verify(paymentGateway).payment(paymentRequest);
            verify(orderRepository).createOrder(any(OrderEntity.class));
            verify(orderItemRepository).createOrderItem(any(OrderItemEntity.class));
            verify(cartItemRepository).deleteCartItem(1L);
            verify(cartRepository).updateCart(any(CartEntity.class));
        }

        @Test
        @DisplayName("checkout should throw exception when address is null")
        void testCheckoutNullAddress() {
            Long userId = 1L;
            CardPaymentRequest paymentRequest = new CardPaymentRequest(null, null, null, null);

            assertThrows(BusinessException.class, () -> orderServiceImpl.checkout(userId, null, paymentRequest));
        }

        @Test
        @DisplayName("checkout should throw exception when address is blank")
        void testCheckoutBlankAddress() {
            Long userId = 1L;
            CardPaymentRequest paymentRequest = new CardPaymentRequest(null, null, null, null);

            assertThrows(BusinessException.class, () -> orderServiceImpl.checkout(userId, "   ", paymentRequest));
        }

        @Test
        @DisplayName("checkout should throw exception when payment request is null")
        void testCheckoutNullPaymentRequest() {
            Long userId = 1L;
            String address = "123 Delivery St";

            assertThrows(BusinessException.class, () -> orderServiceImpl.checkout(userId, address, null));
        }

        @Test
        @DisplayName("checkout should throw exception when payment fails")
        void testCheckoutPaymentFailed() {
            Long userId = 1L;
            String address = "123 Delivery St";
            CardPaymentRequest paymentRequest = new CardPaymentRequest(null, null, null, null);
            CardPaymentResponse paymentResponse = new CardPaymentResponse("txn123", "failed", "Insufficient funds");

            when(paymentGateway.payment(paymentRequest)).thenReturn(paymentResponse);

            assertThrows(BusinessException.class, () -> orderServiceImpl.checkout(userId, address, paymentRequest));
        }

        @Test
        @DisplayName("checkout should throw exception when user not found")
        void testCheckoutUserNotFound() {
            Long userId = 999L;
            String address = "123 Delivery St";
            CardPaymentRequest paymentRequest = new CardPaymentRequest(null, null, null, null);
            CardPaymentResponse paymentResponse = new CardPaymentResponse("txn123", "success", "Payment successful");

            when(paymentGateway.payment(paymentRequest)).thenReturn(paymentResponse);
            when(userRepository.getClientById(userId)).thenReturn(Optional.empty());

            assertThrows(BusinessException.class, () -> orderServiceImpl.checkout(userId, address, paymentRequest));
        }

        @Test
        @DisplayName("checkout should throw exception when cart not found")
        void testCheckoutCartNotFound() {
            Long userId = 1L;
            String address = "123 Delivery St";
            CardPaymentRequest paymentRequest = new CardPaymentRequest(null, null, null, null);
            CardPaymentResponse paymentResponse = new CardPaymentResponse("txn123", "success", "Payment successful");
            UserEntity userEntity = createUserEntity();

            when(paymentGateway.payment(paymentRequest)).thenReturn(paymentResponse);
            when(userRepository.getClientById(userId)).thenReturn(Optional.of(userEntity));
            when(cartRepository.getCartByUserId(userId)).thenReturn(Optional.empty());

            assertThrows(BusinessException.class, () -> orderServiceImpl.checkout(userId, address, paymentRequest));
        }

        @Test
        @DisplayName("checkout should throw exception when cart is empty")
        void testCheckoutEmptyCart() {
            Long userId = 1L;
            String address = "123 Delivery St";
            CardPaymentRequest paymentRequest = new CardPaymentRequest(null, null, null, null);
            CardPaymentResponse paymentResponse = new CardPaymentResponse("txn123", "success", "Payment successful");
            UserEntity userEntity = createUserEntity();
            CartEntity cartEntity = new CartEntity(
                    1L,
                    0,
                    BigDecimal.ZERO,
                    userEntity,
                    null
            );

            when(paymentGateway.payment(paymentRequest)).thenReturn(paymentResponse);
            when(userRepository.getClientById(userId)).thenReturn(Optional.of(userEntity));
            when(cartRepository.getCartByUserId(userId)).thenReturn(Optional.of(cartEntity));
            when(cartItemRepository.getCartItemsByCartId(1L)).thenReturn(List.of());

            assertThrows(BusinessException.class, () -> orderServiceImpl.checkout(userId, address, paymentRequest));
        }
    }
}

