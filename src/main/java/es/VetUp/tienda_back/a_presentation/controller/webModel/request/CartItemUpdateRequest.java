package es.VetUp.tienda_back.a_presentation.controller.webModel.request;

public record CartItemUpdateRequest(
        Long id,
        Integer quantity
) {
}

