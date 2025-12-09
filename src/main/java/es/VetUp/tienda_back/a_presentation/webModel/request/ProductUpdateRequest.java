package es.VetUp.tienda_back.a_presentation.webModel.request;

import java.math.BigDecimal;

public record ProductUpdateRequest(
        Long product_id,
        String name,
        String product_description,
        BigDecimal  price,
        BigDecimal discountedPrice,
        String pictureProduct,
        String brand
){}