package es.VetUp.tienda_back.c_persistence.repository.mapper;

import es.VetUp.tienda_back.b_domain.repository.entity.ReviewEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.ProductJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.ReviewJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.UserJpaEntity;

public class ReviewPersistenceMapper {
    private static ReviewPersistenceMapper INSTANCE;

    private ReviewPersistenceMapper() {}

    public static ReviewPersistenceMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReviewPersistenceMapper();
        }
        return INSTANCE;
    }

    public ReviewEntity fromReviewJpaEntityToReviewEntity(ReviewJpaEntity reviewJpaEntity) {
        if (reviewJpaEntity == null) {
            return null;
        }
        return new ReviewEntity(
                reviewJpaEntity.getReviewId(),
                reviewJpaEntity.getProduct() != null ? reviewJpaEntity.getProduct().getProductId() : null,
                reviewJpaEntity.getUser() != null ? reviewJpaEntity.getUser().getId() : null,
                reviewJpaEntity.getUser() != null ? reviewJpaEntity.getUser().getName() : null,
                reviewJpaEntity.getRating(),
                reviewJpaEntity.getComment(),
                reviewJpaEntity.getCreatedAt()
        );
    }

    public ReviewJpaEntity fromReviewEntityToReviewJpaEntity(ReviewEntity reviewEntity, ProductJpaEntity productJpaEntity, UserJpaEntity userJpaEntity) {
        if (reviewEntity == null) {
            return null;
        }
        return new ReviewJpaEntity(
                reviewEntity.reviewId(),
                productJpaEntity,
                userJpaEntity,
                reviewEntity.rating(),
                reviewEntity.comment(),
                reviewEntity.createdAt()
        );
    }
}
