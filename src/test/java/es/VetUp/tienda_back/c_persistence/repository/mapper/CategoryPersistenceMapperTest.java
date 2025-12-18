package es.VetUp.tienda_back.c_persistence.repository.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.VetUp.tienda_back.b_domain.repository.entity.CategoryEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity;

class CategoryPersistenceMapperTest {

    @Test
    @DisplayName("Test map from CategoryJpaEntity to CategoryEntity")
    void testFromCategoryJpaEntitytoToCategoryEntity() {
        // Arrange
        CategoryJpaEntity jpaEntity = new CategoryJpaEntity();
        jpaEntity.setCategoryId(1L);
        jpaEntity.setName("Category 1");
        jpaEntity.setDescription("Description 1");

        // Act
        CategoryEntity entity = CategoryPersistenceMapper.getInstance()
                .fromCategoryJpaEntitytoToCategoryEntity(jpaEntity);

        // Assert
        assertEquals(jpaEntity.getCategoryId(), entity.categoryId());
        assertEquals(jpaEntity.getName(), entity.name());
        assertEquals(jpaEntity.getDescription(), entity.description());
    }

    @Test
    @DisplayName("Test map from CategoryEntity to CategoryJpaEntity")
    void testFromCategoryEntitytoToCategoryJpaEntity() {
        // Arrange
        CategoryEntity entity = new CategoryEntity(
                2L,
                "Category 2",
                "Description 2");

        // Act
        CategoryJpaEntity jpaEntity = CategoryPersistenceMapper.getInstance()
                .fromCategoryEntitytoToCategoryJpaEntity(entity);

        // Assert
        assertEquals(entity.categoryId(), jpaEntity.getCategoryId());
        assertEquals(entity.name(), jpaEntity.getName());
        assertEquals(entity.description(), jpaEntity.getDescription());
    }

}