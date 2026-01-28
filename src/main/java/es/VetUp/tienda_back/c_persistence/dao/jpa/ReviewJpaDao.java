package es.VetUp.tienda_back.c_persistence.dao.jpa;

import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.ReviewJpaEntity;

import java.util.List;

public interface ReviewJpaDao extends GenericJpaDao<ReviewJpaEntity> {
    long countByProductId(Long productId);

    long countByUserId(Long userId);

    double averageRatingByProductId(Long productId);

    List<ReviewJpaEntity> findByProductId(Long productId, int page, int size);

    List<ReviewJpaEntity> findByUserId(Long userId, int page, int size);

    List<ReviewJpaEntity> findByUserAndProduct(Long userId, Long productId);
}
