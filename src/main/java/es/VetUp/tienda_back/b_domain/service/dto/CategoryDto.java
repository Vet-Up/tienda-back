package es.VetUp.tienda_back.b_domain.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryDto(
        Long categoryId,

        @NotBlank(message = "El nombre de la categoría es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
        String name,

        @Size(max = 255, message = "La descripción no puede superar 255 caracteres")
        String description


) {
}
