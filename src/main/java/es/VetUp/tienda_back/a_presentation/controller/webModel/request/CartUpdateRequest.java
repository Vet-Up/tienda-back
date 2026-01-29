package es.VetUp.tienda_back.a_presentation.controller.webModel.request;

import java.math.BigDecimal;

public record CartUpdateRequest(
        Long id,
        Integer totalProducts,
        BigDecimal totalPrice,
        Long userId
) {
}

