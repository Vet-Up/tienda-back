package es.VetUp.tienda_back.b_domain.mapper;

import es.VetUp.tienda_back.b_domain.model.Review;
import es.VetUp.tienda_back.b_domain.repository.entity.ReviewEntity;
import es.VetUp.tienda_back.b_domain.service.dto.ReviewDto;

public class ReviewMapper {
    private static ReviewMapper INSTANCE;

    private ReviewMapper() {}

    public static ReviewMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReviewMapper();
        }
        return INSTANCE;
    }

    public Review fromReviewEntityToReview(ReviewEntity entity) {
        return new Review(
            entity.reviewId(),
            entity.productId(),
            entity.userId(),
            entity.userName(),
            entity.rating(),
            entity.comment(),
            entity.createdAt()
        );
    }

    public ReviewEntity fromReviewToReviewEntity(Review review) {
        return new ReviewEntity(
            review.getReviewId(),
            review.getProductId(),
            review.getUserId(),
            review.getUserName(),
            review.getRating(),
            review.getComment(),
            review.getCreatedAt()
        );
    }

    public Review fromReviewDtoToReview(ReviewDto dto) {
        return new Review(
            dto.reviewId(),
            dto.productId(),
            dto.userId(),
            dto.userName(),
            dto.rating(),
            dto.comment(),
            dto.createdAt()
        );
    }

    public ReviewDto fromReviewToReviewDto(Review review) {
        return new ReviewDto(
            review.getReviewId(),
            review.getProductId(),
            review.getUserId(),
            review.getUserName(),
            review.getRating(),
            review.getComment(),
            review.getCreatedAt()
        );
    }
}
