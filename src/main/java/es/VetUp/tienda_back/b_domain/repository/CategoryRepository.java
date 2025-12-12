package es.VetUp.tienda_back.b_domain.repository;

import es.VetUp.tienda_back.b_domain.repository.entity.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    CategoryEntity saveCategory(CategoryEntity categoryEntity);
    Optional<CategoryEntity> findCategoryById(Long categoryId);
    CategoryEntity updateCategory(Long categoryId, CategoryEntity categoryEntity);
    void deleteCategory(Long categoryId);
    List<CategoryEntity> findAllCategories();


}
