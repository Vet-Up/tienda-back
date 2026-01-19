package es.VetUp.tienda_back.a_presentation.controller.webModel.response;

import java.math.BigDecimal;

public record ProductDetailResponse(
         Long productId,
         String name,
         String productDescription,
         BigDecimal  basePrice,
         BigDecimal discountedPrice,
         BigDecimal price,
         String pictureProduct,
         String brand,
         Long categoryId
) {
}


