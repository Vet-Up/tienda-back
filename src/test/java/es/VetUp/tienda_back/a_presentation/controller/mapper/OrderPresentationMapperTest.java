package es.VetUp.tienda_back.a_presentation.controller.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.VetUp.tienda_back.a_presentation.controller.webModel.request.OrderInsertRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.OrderUpdateRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.response.OrderDetailResponse;
import es.VetUp.tienda_back.b_domain.model.enums.OrderState;
import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import es.VetUp.tienda_back.b_domain.service.dto.OrderDto;
import es.VetUp.tienda_back.b_domain.service.dto.UserDto;

class OrderPresentationMapperTest {

    @Test
    @DisplayName("Test map from OrderDto to OrderDetailResponse")
    void testFromOrderDtoToOrderDetailResponse() {
        UserDto userDto = new UserDto(
                1L,
                "Test User",
                "testuser",
                "test@test.com",
                "password",
                "Test Address",
                UserRole.CUSTOMER,
                123456789,
                "Spain",
                "profile.jpg",
                LocalDate.of(1990, 1, 1));

        OrderDto orderDto = new OrderDto(
                1L,
                5,
                new BigDecimal("100.00"),
                OrderState.CART,
                userDto,
                LocalDateTime.now(),
                LocalDateTime.now(),
                "Shipping Address",
                Collections.emptyList());

        OrderDetailResponse response = OrderPresentationMapper.getInstance()
                .fromOrderDtoToOrderDetailResponse(orderDto);

        assertEquals(orderDto.id(), response.id());
        assertEquals(orderDto.totalProducts(), response.totalProducts());
        assertEquals(orderDto.totalPrice(), response.totalPrice());
        assertEquals(orderDto.state(), response.state());
        assertEquals(orderDto.user().id(), response.user().id());
        assertEquals(orderDto.createdAt(), response.createdAt());
        assertEquals(orderDto.orderAt(), response.orderAt());
        assertEquals(orderDto.address(), response.address());
        assertNotNull(response.orderItems());
    }

    @Test
    @DisplayName("Test map from OrderInsertRequest to OrderDto")
    void testFromOrderInsertRequestToOrderDto() {
        OrderInsertRequest request = new OrderInsertRequest(
                3,
                new BigDecimal("50.00"),
                OrderState.ORDER,
                10L,
                "Insert Address");

        OrderDto dto = OrderPresentationMapper.getInstance().fromOrderInsertRequestToOrderDto(request);

        assertNull(dto.id());
        assertEquals(request.totalProducts(), dto.totalProducts());
        assertEquals(request.totalPrice(), dto.totalPrice());
        assertEquals(request.state(), dto.state());
        // The mapper creates a dummy UserDto with just the ID
        assertEquals(request.userId(), dto.user().id());
        assertEquals(request.address(), dto.address());
        // createdAt is explicitly null in the mapper for insert request
        assertNull(dto.createdAt());
        // orderAt is explicitly null in the mapper for insert request
        assertNull(dto.orderAt());
        // orderItems is explicitly null in the mapper
        assertNull(dto.orderItems());
    }

    @Test
    @DisplayName("Test map from OrderUpdateRequest to OrderDto")
    void testFromOrderUpdateRequestToOrderDto() {
        LocalDateTime orderAt = LocalDateTime.now();
        OrderUpdateRequest request = new OrderUpdateRequest(
                2L,
                4,
                new BigDecimal("75.00"),
                OrderState.ORDER,
                20L,
                orderAt,
                "Update Address");

        OrderDto dto = OrderPresentationMapper.getInstance().fromOrderUpdateRequestToOrderDto(request);

        assertEquals(request.id(), dto.id());
        assertEquals(request.totalProducts(), dto.totalProducts());
        assertEquals(request.totalPrice(), dto.totalPrice());
        assertEquals(request.state(), dto.state());
        // The mapper creates a dummy UserDto with just the ID
        assertEquals(request.userId(), dto.user().id());
        // createdAt is null in mapper for update
        assertNull(dto.createdAt());
        assertEquals(request.orderAt(), dto.orderAt());
        assertEquals(request.address(), dto.address());
        // orderItems is explicitly null in the mapper
        assertNull(dto.orderItems());
    }
}
