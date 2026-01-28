package es.VetUp.tienda_back.b_domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Review {
    private final Long reviewId;
    private final Long productId;
    private final Long userId;
    private final String userName;
    private final int rating;
    private final String comment;
    private final LocalDateTime createdAt;

    public Review(Long reviewId, Long productId, Long userId, String userName, int rating, String comment, LocalDateTime createdAt) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.userId = userId;
        this.userName = userName;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(reviewId, review.reviewId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewId);
    }
}
