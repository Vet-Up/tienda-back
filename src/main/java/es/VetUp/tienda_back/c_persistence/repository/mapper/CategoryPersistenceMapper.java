package es.VetUp.tienda_back.c_persistence.repository.mapper;

import es.VetUp.tienda_back.b_domain.repository.entity.CategoryEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity;

public class CategoryPersistenceMapper {
    private static CategoryPersistenceMapper INSTANCE;

    private CategoryPersistenceMapper() {
    }

    public static CategoryPersistenceMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CategoryPersistenceMapper();
        }
        return INSTANCE;
    }

    public CategoryEntity fromCategoryJpaEntitytoToCategoryEntity(CategoryJpaEntity categoryJpaEntity) {
        if(categoryJpaEntity == null) {
            return null;
        }

        return new CategoryEntity(
                categoryJpaEntity.getCategoryId(),
                categoryJpaEntity.getName(),
                categoryJpaEntity.getDescription()
        );
    }

    public CategoryJpaEntity fromCategoryEntitytoToCategoryJpaEntity(CategoryEntity categoryEntity) {
        if(categoryEntity == null) {
            return null;
        }

        return new CategoryJpaEntity(
                categoryEntity.categoryId(),
                categoryEntity.name(),
                categoryEntity.description()
        );
    }


}
