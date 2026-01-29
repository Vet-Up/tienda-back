package es.VetUp.tienda_back.a_presentation.controller.webModel.response;

public record CartItemDetailResponse(
        Long id,
        Integer quantity,
        ProductDetailResponse product
) {
}

