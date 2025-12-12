package es.VetUp.tienda_back.c_persistence.dao.jpa.impl;

import es.VetUp.tienda_back.c_persistence.dao.jpa.CategoryJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class CategoryJpaDaoImpl implements CategoryJpaDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CategoryJpaEntity> findByNameContainingIgnoreCase(String name) {
        String query = "SELECT c FROM CategoryJpaEntity c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))";
        return entityManager.createQuery(query, CategoryJpaEntity.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public CategoryJpaEntity insert(CategoryJpaEntity jpaEntity) {
        entityManager.persist(jpaEntity);
        return jpaEntity;
    }

    @Override
    public CategoryJpaEntity update(CategoryJpaEntity jpaEntity) {
        CategoryJpaEntity managed = entityManager.find(CategoryJpaEntity.class, jpaEntity.getCategoryId());
        if (managed == null) {
            throw new RuntimeException("Category not found with id: " + jpaEntity.getCategoryId());
        }
        entityManager.flush();
        return entityManager.merge(jpaEntity);
    }

    @Override
    public void deleteById(Long productId) {
        entityManager.remove(entityManager.find(CategoryJpaEntity.class, productId));

    }

    @Override
    public Optional<CategoryJpaEntity> findById(Long productId) {
        CategoryJpaEntity entity = entityManager.find(CategoryJpaEntity.class, productId);
        return entity != null ? Optional.of(entity) : Optional.empty();
    }

    @Override
    public List<CategoryJpaEntity> findAll(int page, int size) {
        String sql = "SELECT c FROM CategoryJpaEntity c";
        TypedQuery<CategoryJpaEntity> query = entityManager.createQuery(sql, CategoryJpaEntity.class);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public List<CategoryJpaEntity> findAll() {
        String sql = "SELECT c FROM CategoryJpaEntity c";
        TypedQuery<CategoryJpaEntity> query = entityManager.createQuery(sql, CategoryJpaEntity.class);
        return query.getResultList();
    }

    @Override
    public long count() {
        return 0;
    }
}
