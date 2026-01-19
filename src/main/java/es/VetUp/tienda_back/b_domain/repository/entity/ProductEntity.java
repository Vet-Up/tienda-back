package es.VetUp.tienda_back.b_domain.repository.entity;

import java.math.BigDecimal;

public record ProductEntity(
    Long productId,
    String name,
    String productDescription,
    BigDecimal  basePrice,
    BigDecimal discountedPrice,
    String pictureProduct,
    String brand,
    Long categoryId
) {
}
