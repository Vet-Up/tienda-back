package es.VetUp.tienda_back.a_presentation.webModel.response;

import java.math.BigDecimal;

public record ProductDetailResponse(
         Long product_id,
         String name,
         String product_description,
         BigDecimal  price,
         BigDecimal discountedPrice,
         String pictureProduct,
         String brand
) {
}


