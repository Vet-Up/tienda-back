package es.VetUp.tienda_back.a_presentation.controller.webModel.response;

import java.math.BigDecimal;

public record ProductSummaryResponse(
            Long product_id,
            String name,
            BigDecimal  price,
            BigDecimal discountedPrice,
            String pictureProduct,
            String brand,
            Long categoryId
){}
