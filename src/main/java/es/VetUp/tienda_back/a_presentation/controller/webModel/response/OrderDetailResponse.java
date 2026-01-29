package es.VetUp.tienda_back.a_presentation.controller.webModel.response;

import es.VetUp.tienda_back.b_domain.model.enums.OrderState;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDetailResponse (
        Long id,
        Integer totalProducts,
        BigDecimal totalPrice,
        OrderState state,
        UserDetailResponse user,
        LocalDateTime createdAt,
        LocalDateTime orderAt,
        String address,
        List<OrderItemDetailResponse> orderItems
){
}
