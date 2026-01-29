package es.VetUp.tienda_back.a_presentation.controller.webModel.request;

import java.math.BigDecimal;

public record CartInsertRequest(
        Integer totalProducts,
        BigDecimal totalPrice,
        Long userId
) {
}

