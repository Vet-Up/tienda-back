package es.VetUp.tienda_back.b_domain.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import es.VetUp.tienda_back.b_domain.exception.ReviewNotFoundException;
import es.VetUp.tienda_back.b_domain.mapper.ReviewMapper;
import es.VetUp.tienda_back.b_domain.model.Review;
import es.VetUp.tienda_back.b_domain.repository.ReviewRepository;
import es.VetUp.tienda_back.b_domain.repository.entity.ReviewEntity;
import es.VetUp.tienda_back.b_domain.service.ReviewService;
import es.VetUp.tienda_back.b_domain.service.dto.ReviewDto;
import jakarta.transaction.Transactional;

public class ReviewServiceImpl implements ReviewService {
    @Override
    public long countReviewsByProductId(Long productId) {
        return reviewRepository.countByProductId(productId);
    }

    @Override
    public long countReviewsByUserId(Long userId) {
        return reviewRepository.countByUserId(userId);
    }

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    @Transactional
    public ReviewDto saveReview(ReviewDto reviewDto) {
        // Validaciones de negocio
        if (reviewDto.rating() < 1 || reviewDto.rating() > 5) {
            throw new IllegalArgumentException("La puntuación debe estar entre 1 y 5.");
        }
        if (reviewDto.comment() == null || reviewDto.comment().trim().isEmpty()) {
            throw new IllegalArgumentException("El comentario no puede estar vacío.");
        }
        if (reviewDto.userId() == null || reviewDto.productId() == null) {
            throw new IllegalArgumentException("El usuario y el producto son obligatorios.");
        }

        ReviewDto reviewDtoToSave = reviewDto;
        if (reviewDto.reviewId() == null) {
            // Crear nueva review: no permitir más de una review por usuario-producto
            boolean exists = getReviewsByUserId(reviewDto.userId(), 0, Integer.MAX_VALUE).stream()
                    .anyMatch(r -> r.productId().equals(reviewDto.productId()));
            if (exists) {
                throw new IllegalArgumentException("El usuario ya ha realizado una review para este producto.");
            }
            if (reviewDto.createdAt() == null) {
                reviewDtoToSave = new ReviewDto(
                        null,
                        reviewDto.productId(),
                        reviewDto.userId(),
                        reviewDto.userName(),
                        reviewDto.rating(),
                        reviewDto.comment(),
                        LocalDateTime.now());
            }
            Review review = ReviewMapper.getInstance().fromReviewDtoToReview(reviewDtoToSave);
            ReviewEntity reviewEntity = ReviewMapper.getInstance().fromReviewToReviewEntity(review);
            ReviewEntity savedReviewEntity = reviewRepository.save(reviewEntity);
            Review savedReview = ReviewMapper.getInstance().fromReviewEntityToReview(savedReviewEntity);
            return ReviewMapper.getInstance().fromReviewToReviewDto(savedReview);
        } else {
            // Actualizar review existente: solo el autor puede actualizar
            Optional<ReviewEntity> existing = reviewRepository.findById(reviewDto.reviewId());
            if (existing.isEmpty()) {
                throw new IllegalArgumentException("No existe la review a actualizar.");
            }
            if (!existing.get().userId().equals(reviewDto.userId())) {
                throw new IllegalArgumentException("Solo el autor puede actualizar su review.");
            }
            Review review = ReviewMapper.getInstance().fromReviewDtoToReview(reviewDto);
            ReviewEntity reviewEntity = ReviewMapper.getInstance().fromReviewToReviewEntity(review);
            ReviewEntity savedReviewEntity = reviewRepository.update(reviewEntity);
            Review savedReview = ReviewMapper.getInstance().fromReviewEntityToReview(savedReviewEntity);
            return ReviewMapper.getInstance().fromReviewToReviewDto(savedReview);
        }
    }

    @Override
    public Optional<ReviewDto> getReviewById(Long reviewId) {
        Optional<ReviewEntity> reviewEntityOpt = reviewRepository.findById(reviewId);
        return reviewEntityOpt.map(ReviewMapper.getInstance()::fromReviewEntityToReview)
                .map(ReviewMapper.getInstance()::fromReviewToReviewDto);
    }

    @Override
    public List<ReviewDto> getReviewsByProductId(Long productId, int page, int size) {
        List<ReviewEntity> reviewEntities = reviewRepository.findByProductId(productId, page, size);
        return reviewEntities.stream()
                .map(ReviewMapper.getInstance()::fromReviewEntityToReview)
                .map(ReviewMapper.getInstance()::fromReviewToReviewDto).toList();
    }

    @Override
    public List<ReviewDto> getReviewsByUserId(Long userId, int page, int size) {
        List<ReviewEntity> reviewEntities = reviewRepository.findByUserId(userId, page, size);
        return reviewEntities.stream()
                .map(ReviewMapper.getInstance()::fromReviewEntityToReview)
                .map(ReviewMapper.getInstance()::fromReviewToReviewDto).toList();
    }

    @Override
    public Optional<ReviewDto> getReviewByUserAndProduct(Long userId, Long productId) {
        List<ReviewEntity> reviewEntities = reviewRepository.findByUserAndProduct(userId, productId)
                .stream()
                .toList();

        if (reviewEntities.isEmpty()) {
            throw new ReviewNotFoundException(
                    "No existe review del usuario " + userId + " para el producto " + productId
            );
        }

        ReviewEntity reviewEntity = reviewEntities.get(0);
        Review review = ReviewMapper.getInstance().fromReviewEntityToReview(reviewEntity);
        ReviewDto reviewDto = ReviewMapper.getInstance().fromReviewToReviewDto(review);
        return Optional.of(reviewDto);
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId) {
        boolean hasReview = reviewRepository.findById(reviewId).isPresent();
        if (hasReview) {
            reviewRepository.deleteById(reviewId);
        }
    }

    @Override
    public List<ReviewDto> getAllReviews() {
        List<ReviewEntity> reviewEntities = reviewRepository.findAll();
        return reviewEntities.stream()
                .map(ReviewMapper.getInstance()::fromReviewEntityToReview)
                .map(ReviewMapper.getInstance()::fromReviewToReviewDto).toList();

    }

}
