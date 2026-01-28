package es.VetUp.tienda_back.a_presentation.controller.webModel.request;

import java.math.BigDecimal;

public record ProductUpdateRequest(
                Long productId,
                String name,
                String productDescription,
                BigDecimal basePrice,
                BigDecimal discount,
                String pictureProduct,
                String brand,
                Long categoryId,
                Integer stock) {
}