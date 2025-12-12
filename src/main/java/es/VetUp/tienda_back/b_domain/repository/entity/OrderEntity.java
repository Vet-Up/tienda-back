package es.VetUp.tienda_back.b_domain.repository.entity;

import es.VetUp.tienda_back.b_domain.model.enums.OrderState;

import java.math.BigDecimal;

public record OrderEntity(
        Long id,
        Integer totalProducts,
        BigDecimal totalPrice,
        OrderState state,
        UserEntity user
) {
}
