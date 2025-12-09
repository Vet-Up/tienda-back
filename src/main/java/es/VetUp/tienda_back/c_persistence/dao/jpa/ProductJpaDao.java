package es.VetUp.tienda_back.c_persistence.dao.jpa;

import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.ProductJpaEntity;

import java.util.List;

public interface ProductJpaDao extends GenericJpaDao<ProductJpaEntity> {
    List<ProductJpaEntity> findByCategoryId(int categoryId,int page, int size);
    List<ProductJpaEntity> findByBrandId(String brand, int page, int size);

}
