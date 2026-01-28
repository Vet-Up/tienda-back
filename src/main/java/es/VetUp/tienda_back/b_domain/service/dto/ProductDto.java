package es.VetUp.tienda_back.b_domain.service.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductDto(
        Long productId,

        @NotNull(message = "Product name is required") @Size(min = 2, max = 150, message = "Name must be between 2 and 150 characters") String name,

        @NotBlank(message = "Product description is required") @Size(max = 1000, message = "Description cannot exceed 1000 characters") String productDescription,

        @NotNull(message = "El precio base no puede ser nulo")
        @DecimalMin(value = "0.0", inclusive = true, message = "El precio base debe ser mayor o igual a 0")
        BigDecimal basePrice,
        @NotNull
        @DecimalMin(value = "0.0", inclusive = true, message = "El descuento no puede ser menor a 0")
        @DecimalMax(value = "100.0", inclusive = true, message = "El descuento no puede ser mayor a 100")
        BigDecimal discount,
        BigDecimal price,

        @NotBlank(message = "Product picture URL is required") String pictureProduct,

        @NotBlank(message = "Brand is required") @Size(min = 2, max = 100, message = "Brand must be between 2 and 100 characters") String brand,

        @NotNull(message = "El id de categoría es obligatorio") Long categoryId,

        @PositiveOrZero(message = "Stock must be 0 or greater") Integer stock,

        Double averageRating,

        Integer reviewsCount) {

}