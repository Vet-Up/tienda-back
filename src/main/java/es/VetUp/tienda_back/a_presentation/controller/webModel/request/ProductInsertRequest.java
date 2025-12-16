package es.VetUp.tienda_back.a_presentation.controller.webModel.request;

import java.math.BigDecimal;

public record ProductInsertRequest (
        String name,
        String productDescription,
        BigDecimal  price,
        BigDecimal discountedPrice,
        String pictureProduct,
        String brand,
        Long categoryId
){}
