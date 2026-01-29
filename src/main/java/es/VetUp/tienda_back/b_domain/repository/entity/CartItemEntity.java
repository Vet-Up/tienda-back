package es.VetUp.tienda_back.b_domain.repository.entity;

public record CartItemEntity(
        Long id,
        Integer quantity,
        Long cartId,
        ProductEntity product
) {
}

