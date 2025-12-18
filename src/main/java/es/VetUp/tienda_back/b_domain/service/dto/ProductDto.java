package es.VetUp.tienda_back.b_domain.service.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductDto (
    Long productId,

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 150, message = "Name must be between 2 and 150 characters")
    String name,

    @NotBlank(message = "Product description is required")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    String productDescription,

    @Positive(message = "Price must be greater than 0")
    BigDecimal price,

    @PositiveOrZero(message = "Discounted price must be 0 or greater")
    BigDecimal  discountedPrice,

    @NotBlank(message = "Product picture URL is required")
    String pictureProduct,

    @NotBlank(message = "Brand is required")
    @Size(min = 2, max = 100, message = "Brand must be between 2 and 100 characters")
    String brand,

    @NotNull(message = "El id de categoría es obligatorio")
    Long categoryId
) {
    
}