package es.VetUp.tienda_back.c_persistence.dao.jpa.impl;

import java.util.List;
import java.util.Optional;

import es.VetUp.tienda_back.b_domain.repository.entity.ReviewEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.ReviewJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.ReviewJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class ReviewJpaDaoImpl implements ReviewJpaDao {
    
    @Override
    public long countByProductId(Long productId) {
        String sql = "SELECT COUNT(r) FROM ReviewJpaEntity r WHERE r.product.productId = :productId";
        try {
            return entityManager.createQuery(sql, Long.class)
                    .setParameter("productId", productId)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public long countByUserId(Long userId) {
        String sql = "SELECT COUNT(r) FROM ReviewJpaEntity r WHERE r.user.id = :userId";
        try {
            return entityManager.createQuery(sql, Long.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<ReviewJpaEntity> findByProductId(Long productId, int page, int size) {
        String sql = "SELECT r FROM ReviewJpaEntity r WHERE r.product.productId = :productId ORDER BY r.createdAt DESC";
        try {
            return entityManager.createQuery(sql, ReviewJpaEntity.class)
                    .setParameter("productId", productId)
                    .setFirstResult(page * size)
                    .setMaxResults(size)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public List<ReviewJpaEntity> findByUserId(Long userId, int page, int size) {
        String sql = "SELECT r FROM ReviewJpaEntity r WHERE r.user.id = :userId ORDER BY r.createdAt DESC";
        try {
            return entityManager.createQuery(sql, ReviewJpaEntity.class)
                    .setParameter("userId", userId)
                    .setFirstResult(page * size)
                    .setMaxResults(size)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public List<ReviewJpaEntity> findByUserAndProduct(Long userId, Long productId) {
        String sql = "SELECT r FROM ReviewJpaEntity r WHERE r.user.id = :userId AND r.product.productId = :productId";
        try {
            return entityManager.createQuery(sql, ReviewJpaEntity.class)
                    .setParameter("userId", userId)
                    .setParameter("productId", productId)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ReviewJpaEntity insert(ReviewJpaEntity jpaEntity) {
        entityManager.persist(jpaEntity);
        return jpaEntity;
    }

    @Override
    public ReviewJpaEntity update(ReviewJpaEntity jpaEntity) {
        ReviewJpaEntity managed = entityManager.find(ReviewJpaEntity.class, jpaEntity.getReviewId());
        if (managed != null) {
            managed.setRating(jpaEntity.getRating());
            managed.setComment(jpaEntity.getComment());
            managed.setProduct(jpaEntity.getProduct());
            managed.setUser(jpaEntity.getUser());
            entityManager.merge(managed);
        }
        return managed;

    }

    @Override
    public void deleteById(Long reviewId) {
        entityManager.remove(entityManager.find(ReviewJpaEntity.class, reviewId));
    }

    @Override
    public Optional<ReviewJpaEntity> findById(Long reviewId) {
        return Optional.ofNullable(entityManager.find(ReviewJpaEntity.class, reviewId));
    }

    @Override
    public List<ReviewJpaEntity> findAll(int page, int size) {
        String sql = "SELECT r FROM ReviewJpaEntity r";
        try {
            return entityManager.createQuery(sql, ReviewJpaEntity.class)
                    .setFirstResult(page * size)
                    .setMaxResults(size)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public List<ReviewJpaEntity> findAll() {
        String sql = "SELECT r FROM ReviewJpaEntity r";
        try {
            return entityManager.createQuery(sql, ReviewJpaEntity.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public long count() {
        return entityManager.createQuery("SELECT COUNT(r) FROM ReviewJpaEntity r", Long.class).getSingleResult();
    }

    @Override
    public double averageRatingByProductId(Long productId) {
        String sql = "SELECT AVG(r.rating) FROM ReviewJpaEntity r WHERE r.product.productId = :productId";
        try {
            Double avg = entityManager.createQuery(sql, Double.class)
                    .setParameter("productId", productId)
                    .getSingleResult();
            return avg != null ? avg : 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

}
