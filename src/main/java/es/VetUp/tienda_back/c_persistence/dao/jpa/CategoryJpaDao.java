package es.VetUp.tienda_back.c_persistence.dao.jpa;

import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity;

import java.util.List;

public interface CategoryJpaDao extends GenericJpaDao<CategoryJpaEntity> {
    List<CategoryJpaEntity> findByNameContainingIgnoreCase(String name);


}
