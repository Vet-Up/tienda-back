package es.VetUp.tienda_back.b_domain.service.impl;

import es.VetUp.tienda_back.b_domain.mapper.CategoryMapper;
import es.VetUp.tienda_back.b_domain.repository.CategoryRepository;
import es.VetUp.tienda_back.b_domain.repository.ProductRepository;
import es.VetUp.tienda_back.b_domain.repository.entity.CategoryEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.ProductEntity;
import es.VetUp.tienda_back.b_domain.service.CategoryService;
import es.VetUp.tienda_back.b_domain.service.dto.CategoryDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        CategoryEntity categoryEntity = CategoryMapper.getInstance()
                .fromCategorytoCategoryEntity(CategoryMapper.getInstance().fromCategoryDtotoCategory(categoryDto));
        CategoryEntity createdCategoryEntity = categoryRepository.saveCategory(categoryEntity);
        return CategoryMapper.getInstance()
                .fromCategorytoCategoryDto(CategoryMapper.getInstance().fromCategoryEntitytoCategory(createdCategoryEntity));

    }

    @Transactional
    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        categoryRepository.findCategoryById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category with id " + categoryId + " not found"));
        CategoryEntity categoryEntity = CategoryMapper.getInstance()
                .fromCategorytoCategoryEntity(CategoryMapper.getInstance().fromCategoryDtotoCategory(categoryDto));
        CategoryEntity updatedCategoryEntity = categoryRepository.updateCategory(categoryId, categoryEntity);
        return CategoryMapper.getInstance()
                .fromCategorytoCategoryDto(CategoryMapper.getInstance().fromCategoryEntitytoCategory(updatedCategoryEntity));
    }

    @Transactional
    @Override
    public void deleteCategory(Long categoryId) {
        boolean hasProducts = productRepository.existsByCategoryId(categoryId);
        if (hasProducts) {
            throw new IllegalStateException("The category cannot be deleted because it has associated products.");
        }
        categoryRepository.deleteCategory(categoryId);

    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        CategoryEntity categoryEntity = categoryRepository.findCategoryById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category with id " + categoryId + " not found"));
        return CategoryMapper.getInstance()
                .fromCategorytoCategoryDto(CategoryMapper.getInstance().fromCategoryEntitytoCategory(categoryEntity));
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<CategoryEntity> categoryEntities = categoryRepository.findAllCategories();
        return categoryEntities.stream()
                .map(CategoryMapper.getInstance()::fromCategoryEntitytoCategory)
                .map(CategoryMapper.getInstance()::fromCategorytoCategoryDto)
                .toList();
    }
}
