package es.VetUp.tienda_back.a_presentation.controller.webModel.response;

import java.math.BigDecimal;

public record ProductDetailResponse(
         Long productId,
         String name,
         String productDescription,
         BigDecimal  price,
         BigDecimal discountedPrice,
         String pictureProduct,
         String brand,
         Long categoryId
) {
}


