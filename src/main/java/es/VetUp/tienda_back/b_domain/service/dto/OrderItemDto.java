package es.VetUp.tienda_back.b_domain.service.dto;

import jakarta.validation.constraints.NotNull;

public record OrderItemDto(
        Long id,
        @NotNull
        Integer quantity,
        @NotNull
        Long orderId,
        @NotNull
        ProductDto product
) {
}

