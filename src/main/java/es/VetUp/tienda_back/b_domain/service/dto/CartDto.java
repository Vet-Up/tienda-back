package es.VetUp.tienda_back.b_domain.service.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record CartDto(
        Long id,
        Integer totalProducts,
        BigDecimal totalPrice,
        @NotNull
        UserDto user,
        List<CartItemDto> cartItems
) {
}

