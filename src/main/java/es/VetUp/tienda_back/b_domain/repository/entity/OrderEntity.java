package es.VetUp.tienda_back.b_domain.repository.entity;

import es.VetUp.tienda_back.b_domain.model.enums.OrderState;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderEntity(
        Long id,
        Integer totalProducts,
        BigDecimal totalPrice,
        OrderState state,
        UserEntity user,
        LocalDateTime createdAt,
        LocalDateTime orderAt,
        String address,
        List<OrderItemEntity> orderItems
) {
}
