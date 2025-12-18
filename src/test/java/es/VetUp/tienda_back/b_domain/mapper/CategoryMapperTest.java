package es.VetUp.tienda_back.b_domain.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.VetUp.tienda_back.b_domain.model.Category;
import es.VetUp.tienda_back.b_domain.repository.entity.CategoryEntity;

class CategoryMapperTest {

    @Test
    @DisplayName("Test map category to categoryDTO")
    void testMapCategoryToCategoryDTO() {
        Category category = new Category(1L, "Dogs", "All dog products");

        var categoryDTO = CategoryMapper.getInstance().fromCategorytoCategoryDto(category);

        assertAll("categoryDTO",
                () -> assertEquals(category.getCategoryId(), categoryDTO.categoryId()),
                () -> assertEquals(category.getName(), categoryDTO.name()),
                () -> assertEquals(category.getDescription(), categoryDTO.description()));

    }

    @Test
    @DisplayName("Test map categoryEntity to Category")
    void testMapCategoryEntityToCategory() {
        var categoryEntity = new CategoryEntity(1L, "Dogs", "All dog products");

        var category = CategoryMapper.getInstance().fromCategoryEntitytoCategory(categoryEntity);

        assertAll("category",
                () -> assertEquals(categoryEntity.categoryId(), category.getCategoryId()),
                () -> assertEquals(categoryEntity.name(), category.getName()),
                () -> assertEquals(categoryEntity.description(), category.getDescription()));

    }

    

}