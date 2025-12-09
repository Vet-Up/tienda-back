package es.VetUp.tienda_back.c_persistence.dao.jpa.impl;

import es.VetUp.tienda_back.b_domain.exception.ResourceNotFoundException;
import es.VetUp.tienda_back.c_persistence.dao.jpa.ProductJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.ProductJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class ProductJpaDaoImpl implements ProductJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ProductJpaEntity> findByCategoryId(int categoryId, int page, int size) {
        String sql = "SELECT p FROM ProductJpaEntity p WHERE p.category.id = :categoryId";

        try {
            return entityManager.createQuery(sql, ProductJpaEntity.class).setParameter("categoryId", categoryId).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }

    }

    @Override
    public List<ProductJpaEntity> findByBrandId(String brand, int page, int size) {
        String sql = "SELECT p FROM ProductJpaEntity p WHERE p.brand = :brand";

        try {
            return entityManager.createQuery(sql, ProductJpaEntity.class).setParameter("brand", brand).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }

    }

    @Override
    public ProductJpaEntity insert(ProductJpaEntity jpaEntity) {
        entityManager.persist(jpaEntity);
        return jpaEntity;

    }

    @Override
    public ProductJpaEntity update(ProductJpaEntity jpaEntity) {
        ProductJpaEntity managed = entityManager.find(ProductJpaEntity.class, jpaEntity.getProduct_id());
        if (managed == null) {
            throw new ResourceNotFoundException("Product not found with id: " + jpaEntity.getProduct_id());
        }
        entityManager.flush();
        return entityManager.merge(jpaEntity);
    }

    @Override
    public void deleteById(Long productId) {
        entityManager.remove(entityManager.find(ProductJpaEntity.class, productId));

    }

    @Override
    public Optional<ProductJpaEntity> findById(Long productId) {
        return Optional.ofNullable(entityManager.find(ProductJpaEntity.class, productId));
    }

    @Override
    public List<ProductJpaEntity> findAll(int page, int size) {
        int pageIndex = Math.max(page - 1, 0);
        String sql = "SELECT p FROM ProductJpaEntity p ORDER BY p.id";
        TypedQuery<ProductJpaEntity> productJpaEntityPage = entityManager.createQuery(sql, ProductJpaEntity.class).setFirstResult(pageIndex * size).setMaxResults(size);

        return productJpaEntityPage.getResultList();

    }

    @Override
    public long count() {
        return entityManager.createQuery("SELECT COUNT(p) FROM ProductJpaEntity p", Long.class).getSingleResult();
    }
}
