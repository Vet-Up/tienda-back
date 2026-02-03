package es.VetUp.tienda_back.b_domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReviewTest {

    @Test
    @DisplayName("Test Review Creation")
    void testReviewCreation() {
        Long reviewId = 1L;
        Long productId = 10L;
        Long userId = 5L;
        String userName = "John Doe";
        int rating = 5;
        String comment = "Excellent product!";
        LocalDateTime createdAt = LocalDateTime.now();

        Review review = assertDoesNotThrow(() -> new Review(reviewId, productId, userId, userName, rating, comment, createdAt));

        assertAll("review",
                () -> assertEquals(reviewId, review.getReviewId()),
                () -> assertEquals(productId, review.getProductId()),
                () -> assertEquals(userId, review.getUserId()),
                () -> assertEquals(userName, review.getUserName()),
                () -> assertEquals(rating, review.getRating()),
                () -> assertEquals(comment, review.getComment()),
                () -> assertEquals(createdAt, review.getCreatedAt()));
    }
}

