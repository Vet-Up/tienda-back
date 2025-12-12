package es.VetUp.tienda_back.b_domain.mapper;

import es.VetUp.tienda_back.b_domain.model.Category;
import es.VetUp.tienda_back.b_domain.repository.entity.CategoryEntity;
import es.VetUp.tienda_back.b_domain.service.dto.CategoryDto;

public class CategoryMapper {

    private static CategoryMapper INSTANCE;

    private CategoryMapper() {
    }

    public static CategoryMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CategoryMapper();
        }
        return INSTANCE;
    }

    public Category fromCategoryEntitytoCategory(CategoryEntity categoryEntity) {
        return new Category(
                categoryEntity.categoryId(),
                categoryEntity.name(),
                categoryEntity.description()
        );
    }

    public CategoryEntity fromCategorytoCategoryEntity(Category category) {
        return new CategoryEntity(
                category.getCategoryId(),
                category.getName(),
                category.getDescription()
        );
    }

    public Category fromCategoryDtotoCategory(CategoryDto categoryDto) {
        return new Category(
                categoryDto.categoryId(),
                categoryDto.name(),
                categoryDto.description()
        );
    }

    public CategoryDto fromCategorytoCategoryDto(Category category) {
        return new CategoryDto(
                category.getCategoryId(),
                category.getName(),
                category.getDescription()
        );
    }
}
