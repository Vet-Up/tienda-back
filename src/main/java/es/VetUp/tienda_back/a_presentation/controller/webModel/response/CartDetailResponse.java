package es.VetUp.tienda_back.a_presentation.controller.webModel.response;

import java.math.BigDecimal;
import java.util.List;

public record CartDetailResponse(
        Long id,
        Integer totalProducts,
        BigDecimal totalPrice,
        UserDetailResponse user,
        List<CartItemDetailResponse> cartItems
) {
}

