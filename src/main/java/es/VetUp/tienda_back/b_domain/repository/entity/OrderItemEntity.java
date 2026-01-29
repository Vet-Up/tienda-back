package es.VetUp.tienda_back.b_domain.repository.entity;

public record OrderItemEntity(
        Long id,
        Integer quantity,
        Long orderId,
        ProductEntity product
) {
}

