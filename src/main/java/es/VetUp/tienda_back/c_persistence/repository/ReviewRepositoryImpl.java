package es.VetUp.tienda_back.c_persistence.repository;

import java.util.List;
import java.util.Optional;


import es.VetUp.tienda_back.b_domain.repository.ReviewRepository;
import es.VetUp.tienda_back.b_domain.repository.entity.ReviewEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.ReviewJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.ProductJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.UserJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.ProductJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.ReviewJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.UserJpaEntity;
import es.VetUp.tienda_back.c_persistence.repository.mapper.ReviewPersistenceMapper;

public class ReviewRepositoryImpl implements ReviewRepository {

    private final ReviewJpaDao reviewJpaDao;
    private final ProductJpaDao productJpaDao;
    private final UserJpaDao userJpaDao;

    public ReviewRepositoryImpl(ReviewJpaDao reviewJpaDao, ProductJpaDao productJpaDao, UserJpaDao userJpaDao) {
        this.reviewJpaDao = reviewJpaDao;
        this.productJpaDao = productJpaDao;
        this.userJpaDao = userJpaDao;
    }

    @Override
    public ReviewEntity save(ReviewEntity review) {
        ProductJpaEntity product = productJpaDao.findById(review.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        UserJpaEntity user = userJpaDao.getClientById(review.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        ReviewJpaEntity reviewJpaEntity = ReviewPersistenceMapper.getInstance()
                .fromReviewEntityToReviewJpaEntity(review, product, user);

        ReviewJpaEntity savedEntity = reviewJpaDao.insert(reviewJpaEntity);
        return ReviewPersistenceMapper.getInstance().fromReviewJpaEntityToReviewEntity(savedEntity);


    }

    @Override
    public ReviewEntity update(ReviewEntity review) {
        ProductJpaEntity product = productJpaDao.findById(review.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        UserJpaEntity user = userJpaDao.getClientById(review.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        ReviewJpaEntity reviewJpaEntity = ReviewPersistenceMapper.getInstance()
                .fromReviewEntityToReviewJpaEntity(review, product, user);
        ReviewJpaEntity updatedEntity = reviewJpaDao.update(reviewJpaEntity);
        return ReviewPersistenceMapper.getInstance().fromReviewJpaEntityToReviewEntity(updatedEntity);
    }

    @Override
    public Optional<ReviewEntity> findById(Long reviewId) {
        return reviewJpaDao.findById(reviewId)
                .map(ReviewPersistenceMapper.getInstance()::fromReviewJpaEntityToReviewEntity);
    }

    @Override
    public List<ReviewEntity> findByProductId(Long productId, int page, int size) {
        return reviewJpaDao.findByProductId(productId, page, size).stream()
                .map(ReviewPersistenceMapper.getInstance()::fromReviewJpaEntityToReviewEntity)
                .toList();
    }

    @Override
    public List<ReviewEntity> findByUserId(Long userId, int page, int size) {
        return reviewJpaDao.findByUserId(userId, page, size).stream()
                .map(ReviewPersistenceMapper.getInstance()::fromReviewJpaEntityToReviewEntity)
                .toList();
    }

    @Override
    public Optional<ReviewEntity> findByUserAndProduct(Long userId, Long productId) {
        return reviewJpaDao.findByUserAndProduct(userId, productId).stream()
                .findFirst()
                .map(ReviewPersistenceMapper.getInstance()::fromReviewJpaEntityToReviewEntity);
    }

    @Override
    public void deleteById(Long reviewId) {
        reviewJpaDao.deleteById(reviewId);
    }

    @Override
    public List<ReviewEntity> findAll() {
        return reviewJpaDao.findAll().stream()
                .map(ReviewPersistenceMapper.getInstance()::fromReviewJpaEntityToReviewEntity).toList();

    }

    @Override
    public long countByProductId(Long productId) {
        return reviewJpaDao.countByProductId(productId);
    }

    @Override
    public long countByUserId(Long userId) {
        return reviewJpaDao.countByUserId(userId);
    }

    @Override
    public double averageRatingByProductId(Long productId) {
        return reviewJpaDao.averageRatingByProductId(productId);
    }
}
