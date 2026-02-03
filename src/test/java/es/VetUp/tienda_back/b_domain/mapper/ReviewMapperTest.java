package es.VetUp.tienda_back.b_domain.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.VetUp.tienda_back.b_domain.model.Review;
import es.VetUp.tienda_back.b_domain.repository.entity.ReviewEntity;

import java.time.LocalDateTime;

class ReviewMapperTest {

    @Test
    @DisplayName("Test map review to reviewDTO")
    void testMapReviewToReviewDTO() {
        LocalDateTime now = LocalDateTime.now();
        Review review = new Review(1L, 10L, 5L, "John Doe", 5, "Excellent!", now);

        var reviewDTO = ReviewMapper.getInstance().fromReviewToReviewDto(review);

        assertAll("reviewDTO",
                () -> assertEquals(review.getReviewId(), reviewDTO.reviewId()),
                () -> assertEquals(review.getProductId(), reviewDTO.productId()),
                () -> assertEquals(review.getUserId(), reviewDTO.userId()),
                () -> assertEquals(review.getUserName(), reviewDTO.userName()),
                () -> assertEquals(review.getRating(), reviewDTO.rating()),
                () -> assertEquals(review.getComment(), reviewDTO.comment()),
                () -> assertEquals(review.getCreatedAt(), reviewDTO.createdAt()));
    }

    @Test
    @DisplayName("Test map reviewEntity to Review")
    void testMapReviewEntityToReview() {
        LocalDateTime now = LocalDateTime.now();
        var reviewEntity = new ReviewEntity(1L, 10L, 5L, "John Doe", 5, "Excellent!", now);

        var review = ReviewMapper.getInstance().fromReviewEntityToReview(reviewEntity);

        assertAll("review",
                () -> assertEquals(reviewEntity.reviewId(), review.getReviewId()),
                () -> assertEquals(reviewEntity.productId(), review.getProductId()),
                () -> assertEquals(reviewEntity.userId(), review.getUserId()),
                () -> assertEquals(reviewEntity.userName(), review.getUserName()),
                () -> assertEquals(reviewEntity.rating(), review.getRating()),
                () -> assertEquals(reviewEntity.comment(), review.getComment()),
                () -> assertEquals(reviewEntity.createdAt(), review.getCreatedAt()));
    }

}
