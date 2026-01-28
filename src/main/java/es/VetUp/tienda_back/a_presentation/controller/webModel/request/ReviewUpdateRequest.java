package es.VetUp.tienda_back.a_presentation.controller.webModel.request;

public record ReviewUpdateRequest(
    Long reviewId,
    Long productId,
    Long userId,
    int rating,
    String comment
) {}
