package es.VetUp.tienda_back.b_domain.repository;

import es.VetUp.tienda_back.b_domain.model.Review;
import es.VetUp.tienda_back.b_domain.repository.entity.ReviewEntity;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository {
    long countByProductId(Long productId);

    long countByUserId(Long userId);

    double averageRatingByProductId(Long productId);

    ReviewEntity save(ReviewEntity review);

    ReviewEntity update(ReviewEntity review);

    Optional<ReviewEntity> findById(Long reviewId);

    List<ReviewEntity> findByProductId(Long productId, int page, int size);

    List<ReviewEntity> findByUserId(Long userId, int page, int size);

    Optional<ReviewEntity> findByUserAndProduct(Long userId, Long productId);

    void deleteById(Long reviewId);

    List<ReviewEntity> findAll();
}
