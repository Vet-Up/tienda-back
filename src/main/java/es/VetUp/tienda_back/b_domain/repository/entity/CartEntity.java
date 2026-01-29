package es.VetUp.tienda_back.b_domain.repository.entity;

import java.math.BigDecimal;
import java.util.List;

public record CartEntity(
        Long id,
        Integer totalProducts,
        BigDecimal totalPrice,
        UserEntity user,
        List<CartItemEntity> cartItems
) {
}

