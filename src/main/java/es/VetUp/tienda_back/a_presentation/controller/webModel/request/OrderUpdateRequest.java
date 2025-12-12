package es.VetUp.tienda_back.a_presentation.controller.webModel.request;

import es.VetUp.tienda_back.a_presentation.controller.webModel.response.UserDetailResponse;
import es.VetUp.tienda_back.b_domain.model.enums.OrderState;

import java.math.BigDecimal;

public record OrderUpdateRequest(
        Long id,
        Integer totalProducts,
        BigDecimal totalPrice,
        OrderState state,
        Long userId
) {
}
