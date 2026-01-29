package es.VetUp.tienda_back.b_domain.service.dto;

import jakarta.validation.constraints.NotNull;

public record CartItemDto(
        Long id,
        @NotNull
        Integer quantity,
        @NotNull
        Long cartId,
        @NotNull
        ProductDto product
) {
}

