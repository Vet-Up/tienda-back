package es.VetUp.tienda_back.a_presentation.controller;

import es.VetUp.tienda_back.a_presentation.controller.mapper.CategoryPresentationMapper;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.CategoryInsertRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.CategoryUpdateRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.response.CategoryDetailResponse;
import es.VetUp.tienda_back.b_domain.service.CategoryService;
import es.VetUp.tienda_back.b_domain.service.dto.CategoryDto;
import es.VetUp.tienda_back.config.annotation.RequireAdmin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {

    public final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDetailResponse>>findAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        List<CategoryDetailResponse> categoryResponses = categories.stream()
                .map(CategoryPresentationMapper.getInstance()::fromCategoryDtoToCategoryDetailResponse)
                .toList();
        return ResponseEntity.ok(categoryResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDetailResponse> getCategoryById(@PathVariable Long id) {
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        CategoryDetailResponse categoryDetailResponse = CategoryPresentationMapper.getInstance()
                .fromCategoryDtoToCategoryDetailResponse(categoryDto);
        return new ResponseEntity<>(categoryDetailResponse, HttpStatus.OK);
    }


    @PostMapping
    @RequireAdmin
    public ResponseEntity<CategoryDetailResponse> createCategory(@RequestBody CategoryInsertRequest categoryInsertRequest) {
        CategoryDto categoryDto = CategoryPresentationMapper.getInstance()
                .fromCategoryInsertRequestToCategoryDto(categoryInsertRequest);
        CategoryDto createdCategory = categoryService.createCategory(categoryDto);
        CategoryDetailResponse response = CategoryPresentationMapper.getInstance()
                .fromCategoryDtoToCategoryDetailResponse(createdCategory);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequireAdmin
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDetailResponse> updateCategory(@PathVariable("id") Long id, @RequestBody CategoryUpdateRequest categoryUpdateRequest) {
        if (!id.equals(categoryUpdateRequest.categoryId())) {
            throw new IllegalArgumentException("ID in path and request body must match");
        }
        CategoryDto categoryDto = CategoryPresentationMapper.getInstance()
                .fromCategoryUpdateRequestToCategoryDto(categoryUpdateRequest);
        CategoryDto updatedCategory = categoryService.updateCategory(id,categoryDto);
        CategoryDetailResponse response = CategoryPresentationMapper.getInstance()
                .fromCategoryDtoToCategoryDetailResponse(updatedCategory);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequireAdmin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();

    }


}
