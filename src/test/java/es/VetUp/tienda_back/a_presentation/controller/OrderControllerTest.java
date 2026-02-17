package es.VetUp.tienda_back.a_presentation.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.VetUp.tienda_back.a_presentation.controller.webModel.request.CheckoutRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.OrderInsertRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.OrderUpdateRequest;
import es.VetUp.tienda_back.b_domain.model.enums.OrderState;
import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import es.VetUp.tienda_back.b_domain.service.JwtService;
import es.VetUp.tienda_back.b_domain.service.OrderService;
import es.VetUp.tienda_back.b_domain.service.dto.OrderDto;
import es.VetUp.tienda_back.b_domain.service.dto.UserDto;
import es.VetUp.tienda_back.infrastructure.model.CardPaymentRequest;
import es.VetUp.tienda_back.infrastructure.model.smallModels.Authorization;
import es.VetUp.tienda_back.infrastructure.model.smallModels.Destination;
import es.VetUp.tienda_back.infrastructure.model.smallModels.Origin;
import es.VetUp.tienda_back.infrastructure.model.smallModels.Pay;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDto userDto;
    private OrderDto orderDto;

    @BeforeEach
    void setUp() {
        userDto = new UserDto(
                1L,
                "Test User",
                "testuser",
                "test@test.com",
                "Password123",
                "Test Address",
                UserRole.CUSTOMER,
                123456789,
                "Spain",
                "profile.jpg",
                LocalDate.of(1990, 1, 1));

        orderDto = new OrderDto(
                1L,
                5,
                new BigDecimal("100.00"),
                OrderState.CART,
                userDto,
                LocalDateTime.now(),
                LocalDateTime.now(),
                "Shipping Address",
                Collections.emptyList());
    }

    @Nested
    class FindAllOrdersTests {
        @Test
        @DisplayName("GET /api/orders - Success")
        void testFindAllOrders() throws Exception {
            when(orderService.getAllOrders()).thenReturn(List.of(orderDto));

            mockMvc.perform(get("/api/orders"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(1))
                    .andExpect(jsonPath("$[0].id").value(1));
        }
    }

    @Nested
    class GetOrderByIdTests {
        @Test
        @DisplayName("GET /api/orders/{id} - Success")
        void testGetOrderById() throws Exception {
            when(orderService.getOrderById(1L)).thenReturn(Optional.of(orderDto));

            mockMvc.perform(get("/api/orders/{id}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1));
        }

        @Test
        @DisplayName("GET /api/orders/{id} - Not Found")
        void testGetOrderByIdNotFound() throws Exception {
            when(orderService.getOrderById(999L)).thenReturn(Optional.empty());

            mockMvc.perform(get("/api/orders/{id}", 999L))
                    .andExpect(status().isInternalServerError());
        }
    }

    @Nested
    class GetOrdersByUserIdTests {
        @Test
        @DisplayName("GET /api/orders/user/{userId} - Success")
        void testGetOrdersByUserId() throws Exception {
            when(orderService.getOrdersByUserId(1L)).thenReturn(List.of(orderDto));

            mockMvc.perform(get("/api/orders/user/{userId}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(1))
                    .andExpect(jsonPath("$[0].id").value(1));
        }
    }

    @Nested
    class HasUserPurchasedProductTests {
        @Test
        @DisplayName("GET /api/orders/user/{userId}/product/{productId}/purchased - True")
        void testHasUserPurchasedProductTrue() throws Exception {
            when(orderService.hasUserPurchasedProduct(1L, 100L)).thenReturn(true);

            mockMvc.perform(get("/api/orders/user/{userId}/product/{productId}/purchased", 1L, 100L))
                    .andExpect(status().isOk())
                    .andExpect(content().string("true"));
        }

        @Test
        @DisplayName("GET /api/orders/user/{userId}/product/{productId}/purchased - False")
        void testHasUserPurchasedProductFalse() throws Exception {
            when(orderService.hasUserPurchasedProduct(1L, 100L)).thenReturn(false);

            mockMvc.perform(get("/api/orders/user/{userId}/product/{productId}/purchased", 1L, 100L))
                    .andExpect(status().isOk())
                    .andExpect(content().string("false"));
        }
    }

    @Nested
    class CreateOrderTests {
        @Test
        @DisplayName("POST /api/orders - Success")
        void testCreateOrder() throws Exception {
            OrderInsertRequest request = new OrderInsertRequest(
                    5,
                    new BigDecimal("100.00"),
                    OrderState.CART,
                    1L,
                    "Shipping Address");

            when(orderService.createOrder(any(OrderDto.class))).thenReturn(orderDto);

            mockMvc.perform(post("/api/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(1));
        }
    }

    @Nested
    class UpdateOrderTests {
        @Test
        @DisplayName("PUT /api/orders/{id} - Success")
        void testUpdateOrder() throws Exception {
            OrderUpdateRequest request = new OrderUpdateRequest(
                    1L,
                    5,
                    new BigDecimal("100.00"),
                    OrderState.ORDER,
                    1L,
                    LocalDateTime.now(),
                    "Updated Address");

            OrderDto updatedOrderDto = new OrderDto(
                    1L,
                    5,
                    new BigDecimal("100.00"),
                    OrderState.ORDER,
                    userDto,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    "Updated Address",
                    Collections.emptyList());

            when(orderService.updateOrder(any(OrderDto.class))).thenReturn(updatedOrderDto);

            mockMvc.perform(put("/api/orders/{id}", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.state").value("ORDER"));
        }

        @Test
        @DisplayName("PUT /api/orders/{id} - ID Mismatch")
        void testUpdateOrderIdMismatch() throws Exception {
            OrderUpdateRequest request = new OrderUpdateRequest(
                    2L,
                    5,
                    new BigDecimal("100.00"),
                    OrderState.ORDER,
                    1L,
                    LocalDateTime.now(),
                    "Updated Address");

            mockMvc.perform(put("/api/orders/{id}", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").value("ID in path and request body must match"));
        }
    }

    @Nested
    class DeleteOrderTests {
        @Test
        @DisplayName("DELETE /api/orders/{id} - Success")
        void testDeleteOrder() throws Exception {
            doNothing().when(orderService).deleteOrder(1L);

            mockMvc.perform(delete("/api/orders/{id}", 1L))
                    .andExpect(status().isNoContent());

            verify(orderService).deleteOrder(1L);
        }
    }

    @Nested
    class CheckoutTests {
        @Test
        @DisplayName("POST /api/orders/checkout - Success")
        void testCheckoutSuccess() throws Exception {
            CardPaymentRequest cardPaymentRequest = new CardPaymentRequest(
                    new Authorization("test_login", "test_api_token"),
                    new Origin("1234567890123456", "12/25", "123", "John Doe"),
                    new Destination("ES1234567890123456789012"),
                    new Pay(100.00, "Order Payment"));

            CheckoutRequest request = new CheckoutRequest(
                    "Checkout Address",
                    cardPaymentRequest);

            when(orderService.checkout(eq(1L), eq("Checkout Address"), any(CardPaymentRequest.class)))
                    .thenReturn(orderDto);

            mockMvc.perform(post("/api/orders/checkout")
                    .requestAttr("userId", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(1));
        }

        @Test
        @DisplayName("POST /api/orders/checkout - Unauthorized (Missing User ID)")
        void testCheckoutUnauthorized() throws Exception {
            CheckoutRequest request = new CheckoutRequest("Address", null);

            mockMvc.perform(post("/api/orders/checkout")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isUnauthorized());
        }
    }
}
