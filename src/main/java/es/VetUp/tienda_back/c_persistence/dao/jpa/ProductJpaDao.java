package es.VetUp.tienda_back.c_persistence.dao.jpa;

import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.ProductJpaEntity;

import java.util.List;

public interface ProductJpaDao extends GenericJpaDao<ProductJpaEntity> {
    List<ProductJpaEntity> findByCategoryId(int categoryId,int page, int size);
    List<ProductJpaEntity> findByBrandId(String brand, int page, int size);
    boolean existsByCategoryId(Long categoryId);
    List<ProductJpaEntity> findByName(String name, int page, int size, String sort);
    List<ProductJpaEntity> findAllOrdered(String order, int page, int size);
    List<ProductJpaEntity> findByPriceRange(double minPrice, double maxPrice, int page, int size, String order);
    List<ProductJpaEntity> findByCategoryIds(List<Integer> categoryIds, int page, int size, String order);

    long countByPriceRange(double minPrice, double maxPrice);
}
