package es.VetUp.tienda_back.b_domain.service.dto;

import es.VetUp.tienda_back.b_domain.model.enums.OrderState;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
        Long id,
        Integer totalProducts,
        BigDecimal totalPrice,
        @NotNull
        OrderState state,
        @NotNull
        UserDto user,
        LocalDateTime createdAt,
        LocalDateTime orderAt,
        @NotNull
        String address,
        List<OrderItemDto> orderItems
) {

}
