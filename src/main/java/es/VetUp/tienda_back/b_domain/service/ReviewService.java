package es.VetUp.tienda_back.b_domain.service;

import es.VetUp.tienda_back.b_domain.model.Review;
import es.VetUp.tienda_back.b_domain.service.dto.ReviewDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ReviewService {
    long countReviewsByProductId(Long productId);

    long countReviewsByUserId(Long userId);

    ReviewDto saveReview(ReviewDto review);

    Optional<ReviewDto> getReviewById(Long reviewId);

    List<ReviewDto> getReviewsByProductId(Long productId, int page, int size);

    List<ReviewDto> getReviewsByUserId(Long userId, int page, int size);

    Optional<ReviewDto> getReviewByUserAndProduct(Long userId, Long productId);

    void deleteReview(Long reviewId);

    List<ReviewDto> getAllReviews();

}
