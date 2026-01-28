package es.VetUp.tienda_back.c_persistence.dao.jpa.impl;

import es.VetUp.tienda_back.b_domain.exception.ResourceNotFoundException;
import es.VetUp.tienda_back.b_domain.model.Page;
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
    public List<ProductJpaEntity> findByName(String name, int page, int size, String sort) {
        String sql;
        if ("top-rated".equals(sort)) {
            sql = "SELECT p FROM ProductJpaEntity p LEFT JOIN ReviewJpaEntity r ON r.product = p WHERE LOWER(p.name) LIKE :name GROUP BY p ORDER BY AVG(r.rating) DESC";
        } else {
            String orderByClause;
            if (sort == null || sort.isBlank()) {
                orderByClause = "p.productId ASC";
            } else {
                switch (sort) {
                    case "price-asc":
                        orderByClause = "COALESCE(p.price, p.basePrice) ASC";
                        break;
                    case "price-desc":
                        orderByClause = "COALESCE(p.price, p.basePrice) DESC";
                        break;
                    case "name-asc":
                        orderByClause = "p.name ASC";
                        break;
                    case "name-desc":
                        orderByClause = "p.name DESC";
                        break;
                    default:
                        orderByClause = "p.productId ASC";
                }
            }
            sql = "SELECT p FROM ProductJpaEntity p WHERE LOWER(p.name) LIKE :name ORDER BY " + orderByClause;
        }
        try {
            return entityManager.createQuery(sql, ProductJpaEntity.class)
                    .setParameter("name", "%" + name.toLowerCase() + "%")
                    .setFirstResult(page * size)
                    .setMaxResults(size)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public List<ProductJpaEntity> findAllOrdered(String order, int page, int size) {
        int pageIndex = Math.max(page - 1, 0);
        String sql;
        if ("top-rated".equals(order)) {
            sql = "SELECT p FROM ProductJpaEntity p LEFT JOIN ReviewJpaEntity r ON r.product = p GROUP BY p ORDER BY AVG(r.rating) DESC";
        } else {
            String orderByClause;
            switch (order) {
                case "price-asc":
                    orderByClause = "COALESCE(p.price, p.basePrice) ASC";
                    break;
                case "price-desc":
                    orderByClause = "COALESCE(p.price, p.basePrice) DESC";
                    break;
                case "name-asc":
                    orderByClause = "p.name ASC";
                    break;
                case "name-desc":
                    orderByClause = "p.name DESC";
                    break;
                default:
                    orderByClause = "p.name ASC";
            }
            sql = String.format("SELECT p FROM ProductJpaEntity p ORDER BY %s", orderByClause);
        }
        try {
            return entityManager.createQuery(sql, ProductJpaEntity.class)
                    .setFirstResult(pageIndex * size)
                    .setMaxResults(size)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public List<ProductJpaEntity> findByPriceRange(double minPrice, double maxPrice, int page, int size, String order) {
        int pageIndex = Math.max(page - 1, 0);
        String sql;
        if ("top-rated".equals(order)) {
            sql = "SELECT p FROM ProductJpaEntity p LEFT JOIN ReviewJpaEntity r ON r.product = p WHERE COALESCE(p.price, p.basePrice) BETWEEN :minPrice AND :maxPrice GROUP BY p ORDER BY AVG(r.rating) DESC";
        } else {
            String baseSql = "SELECT p FROM ProductJpaEntity p WHERE COALESCE(p.price, p.basePrice) BETWEEN :minPrice AND :maxPrice";
            String orderSql = "";
            if (order != null && !order.isBlank()) {
                switch (order) {
                    case "price-asc":
                        orderSql = " ORDER BY COALESCE(p.price, p.basePrice) ASC";
                        break;
                    case "price-desc":
                        orderSql = " ORDER BY COALESCE(p.price, p.basePrice) DESC";
                        break;
                    case "name-asc":
                        orderSql = " ORDER BY p.name ASC";
                        break;
                    case "name-desc":
                        orderSql = " ORDER BY p.name DESC";
                        break;
                    default:
                        orderSql = " ORDER BY p.productId ASC";
                }
            } else {
                orderSql = " ORDER BY p.productId ASC";
            }
            sql = baseSql + orderSql;
        }
        try {
            return entityManager.createQuery(sql, ProductJpaEntity.class)
                    .setParameter("minPrice", minPrice)
                    .setParameter("maxPrice", maxPrice)
                    .setFirstResult(pageIndex * size)
                    .setMaxResults(size)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public long countByPriceRange(double minPrice, double maxPrice) {
        String sql = "SELECT COUNT(p) FROM ProductJpaEntity p WHERE COALESCE(p.price, p.basePrice) BETWEEN :minPrice AND :maxPrice";
        Long count = entityManager.createQuery(sql, Long.class)
                .setParameter("minPrice", minPrice)
                .setParameter("maxPrice", maxPrice)
                .getSingleResult();
        return count;
    }

    @Override
    public boolean existsByCategoryId(Long categoryId) {
        String sql = "SELECT COUNT(p) FROM ProductJpaEntity p WHERE p.category.id = :categoryId";
        Long count = entityManager.createQuery(sql, Long.class).setParameter("categoryId", categoryId).getSingleResult();
        return count > 0;
    }

    @Override
    public ProductJpaEntity insert(ProductJpaEntity jpaEntity) {
        entityManager.persist(jpaEntity);
        return jpaEntity;

    }

    @Override
    public ProductJpaEntity update(ProductJpaEntity jpaEntity) {
        ProductJpaEntity managed = entityManager.find(ProductJpaEntity.class, jpaEntity.getProductId());
        if (managed == null) {
            throw new ResourceNotFoundException("Product not found with id: " + jpaEntity.getProductId());
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
    public List<ProductJpaEntity> findAll() {
        return List.of();
    }

    @Override
    public long count() {
        return entityManager.createQuery("SELECT COUNT(p) FROM ProductJpaEntity p", Long.class).getSingleResult();
    }

    @Override
    public List<ProductJpaEntity> findByCategoryIds(List<Integer> categoryIds, int page, int size, String order) {
        if (categoryIds == null || categoryIds.isEmpty()) return List.of();
        int pageIndex = Math.max(page, 0);
        String sql;
        if ("top-rated".equals(order)) {
            sql = "SELECT p FROM ProductJpaEntity p LEFT JOIN ReviewJpaEntity r ON r.product = p WHERE p.category.categoryId IN :categoryIds GROUP BY p ORDER BY AVG(r.rating) DESC";
        } else {
            String orderByClause;
            if (order == null || order.isBlank()) {
                orderByClause = "p.productId ASC";
            } else {
                switch (order) {
                    case "price-asc":
                        orderByClause = "COALESCE(p.price, p.basePrice) ASC";
                        break;
                    case "price-desc":
                        orderByClause = "COALESCE(p.price, p.basePrice) DESC";
                        break;
                    case "name-asc":
                        orderByClause = "p.name ASC";
                        break;
                    case "name-desc":
                        orderByClause = "p.name DESC";
                        break;
                    default:
                        orderByClause = "p.productId ASC";
                }
            }
            sql = "SELECT p FROM ProductJpaEntity p WHERE p.category.categoryId IN :categoryIds ORDER BY " + orderByClause;
        }
        try {
            return entityManager.createQuery(sql, ProductJpaEntity.class)
                    .setParameter("categoryIds", categoryIds)
                    .setFirstResult(pageIndex * size)
                    .setMaxResults(size)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
