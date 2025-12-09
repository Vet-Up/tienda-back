package es.VetUp.tienda_back.b_domain.repository.entity;

import java.math.BigDecimal;

public record ProductEntity(
    Long product_id,
    String name,
    String product_description,
    BigDecimal  price,
    BigDecimal discountedPrice,
    String pictureProduct,
    String brand
) {

}
