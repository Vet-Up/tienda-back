package es.VetUp.tienda_back.b_domain.service.dto;

import es.VetUp.tienda_back.b_domain.model.enums.OrderState;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record OrderDto(
        Long id,
        @NotNull
        Integer totalProducts,
        @NotNull
        BigDecimal totalPrice,
        @NotNull
        OrderState state,
        @NotNull
        UserDto user
) {

}
