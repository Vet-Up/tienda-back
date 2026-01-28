package es.VetUp.tienda_back.b_domain.repository.entity;
import java.time.LocalDateTime;

public record ReviewEntity(
    Long reviewId,
    Long productId,
    Long userId,
    String userName,
    int rating,
    String comment,
    LocalDateTime createdAt
) {}