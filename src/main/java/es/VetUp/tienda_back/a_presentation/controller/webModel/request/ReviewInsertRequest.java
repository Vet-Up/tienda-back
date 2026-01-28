package es.VetUp.tienda_back.a_presentation.controller.webModel.request;

public record ReviewInsertRequest(
    Long productId,
    Long userId,
    int rating,
    String comment
) {}
