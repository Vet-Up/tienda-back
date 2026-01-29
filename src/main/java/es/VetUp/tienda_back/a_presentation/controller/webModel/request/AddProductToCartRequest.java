package es.VetUp.tienda_back.a_presentation.controller.webModel.request;

public record AddProductToCartRequest(
        Long productId,
        Integer quantity
) {
}

