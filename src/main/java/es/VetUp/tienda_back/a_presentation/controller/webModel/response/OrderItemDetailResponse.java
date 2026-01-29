package es.VetUp.tienda_back.a_presentation.controller.webModel.response;

public record OrderItemDetailResponse(
        Long id,
        Integer quantity,
        ProductDetailResponse product
) {
}

