package es.VetUp.tienda_back.b_domain.service.dto;

import java.time.LocalDateTime;

public record ReviewDto(
    Long reviewId,
    Long productId,
    Long userId,
    String userName,
    int rating,
    String comment,
    LocalDateTime createdAt
) {}
