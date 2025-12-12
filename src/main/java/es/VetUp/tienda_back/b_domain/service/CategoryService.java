package es.VetUp.tienda_back.b_domain.service;

import es.VetUp.tienda_back.b_domain.repository.entity.CategoryEntity;
import es.VetUp.tienda_back.b_domain.service.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto);
    void deleteCategory(Long categoryId);
    CategoryDto getCategoryById(Long categoryId);
    List<CategoryDto> getAllCategories();

}
