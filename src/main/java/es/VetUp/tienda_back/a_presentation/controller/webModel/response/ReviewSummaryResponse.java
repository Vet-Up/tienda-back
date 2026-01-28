package es.VetUp.tienda_back.a_presentation.controller.webModel.response;

import java.time.LocalDateTime;

public record ReviewSummaryResponse(
    Long reviewId,
    Long productId,
    Long userId,
    String userName,
    int rating,
    String comment,
    LocalDateTime createdAt
) {}
