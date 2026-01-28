package es.VetUp.tienda_back.b_domain.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.VetUp.tienda_back.b_domain.mapper.CategoryMapper;
import es.VetUp.tienda_back.b_domain.repository.CategoryRepository;
import es.VetUp.tienda_back.b_domain.repository.entity.CategoryEntity;
import es.VetUp.tienda_back.b_domain.service.dto.CategoryDto;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

        @Mock
        private CategoryRepository categoryRepository;

        @Mock
        private es.VetUp.tienda_back.b_domain.repository.ProductRepository productRepository;

        @InjectMocks
        private CategoryServiceImpl categoryServiceImpl;

        @Nested
        class createCategory {
                @Test
                @DisplayName("createCategory should create and return CategoryDto")
                void testCreateCategory() {
                        CategoryDto categoryDtoToCreate = new CategoryDto(
                                        null,
                                        "New Category",
                                        "Description of new category");

                        CategoryEntity categoryEntityToCreate = CategoryMapper.getInstance()
                                        .fromCategorytoCategoryEntity(
                                                        CategoryMapper.getInstance().fromCategoryDtotoCategory(
                                                                        categoryDtoToCreate));

                        CategoryEntity createdCategoryEntity = new CategoryEntity(
                                        1L,
                                        "New Category",
                                        "Description of new category");

                        when(categoryRepository.saveCategory(categoryEntityToCreate)).thenReturn(createdCategoryEntity);

                        CategoryDto createdCategoryDto = categoryServiceImpl.createCategory(categoryDtoToCreate);

                        assertAll("result",
                                        () -> assertNotNull(createdCategoryDto),
                                        () -> assertEquals(1L, createdCategoryDto.categoryId()),
                                        () -> assertEquals("New Category", createdCategoryDto.name()),
                                        () -> assertEquals("Description of new category",
                                                        createdCategoryDto.description()));
                }

                @Test
                @DisplayName("createCategory should throw exception when categoryDto is null")
                void testCreateCategory_NullCategoryDto() {
                        assertThrows(NullPointerException.class, () -> {
                                categoryServiceImpl.createCategory(null);
                        });
                }
        }

        @Nested
        class updateCategory {
                @Test
                @DisplayName("updateCategory should update and return CategoryDto")
                void testUpdateCategory() {
                        Long categoryIdToUpdate = 1L;
                        CategoryDto categoryDtoToUpdate = new CategoryDto(
                                        null,
                                        "Updated Category",
                                        "Updated description");

                        CategoryEntity existingCategoryEntity = new CategoryEntity(
                                        categoryIdToUpdate,
                                        "Old Category",
                                        "Old description");

                        CategoryEntity categoryEntityToUpdate = CategoryMapper.getInstance()
                                        .fromCategorytoCategoryEntity(
                                                        CategoryMapper.getInstance().fromCategoryDtotoCategory(
                                                                        categoryDtoToUpdate));

                        CategoryEntity updatedCategoryEntity = new CategoryEntity(
                                        categoryIdToUpdate,
                                        "Updated Category",
                                        "Updated description");

                        when(categoryRepository.findCategoryById(categoryIdToUpdate))
                                        .thenReturn(java.util.Optional.of(existingCategoryEntity));
                        when(categoryRepository.updateCategory(categoryIdToUpdate, categoryEntityToUpdate))
                                        .thenReturn(updatedCategoryEntity);

                        CategoryDto updatedCategoryDto = categoryServiceImpl.updateCategory(categoryIdToUpdate,
                                        categoryDtoToUpdate);

                        assertAll("result",
                                        () -> assertNotNull(updatedCategoryDto),
                                        () -> assertEquals(categoryIdToUpdate, updatedCategoryDto.categoryId()),
                                        () -> assertEquals("Updated Category", updatedCategoryDto.name()),
                                        () -> assertEquals("Updated description", updatedCategoryDto.description()));
                }

                @Test
                @DisplayName("updateCategory should throw exception when category not found")
                void testUpdateCategory_CategoryNotFound() {
                        Long categoryIdToUpdate = 1L;
                        CategoryDto categoryDtoToUpdate = new CategoryDto(
                                        null,
                                        "Updated Category",
                                        "Updated description");

                        when(categoryRepository.findCategoryById(categoryIdToUpdate))
                                        .thenReturn(java.util.Optional.empty());

                        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                                categoryServiceImpl.updateCategory(categoryIdToUpdate, categoryDtoToUpdate);
                        });

                        assertEquals("Category with id 1 not found", exception.getMessage());
                }
        }

        @Nested
        class deleteCategory {
                @Test
                @DisplayName("deleteCategory should throw IllegalStateException when category has associated products")
                void testDeleteCategory() {
                        Long categoryIdToDelete = 1L;

                        // La implementación comprueba si existen productos asociados y lanza
                        // IllegalStateException si es true
                        when(productRepository.existsByCategoryId(categoryIdToDelete)).thenReturn(true);

                        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
                                categoryServiceImpl.deleteCategory(categoryIdToDelete);
                        });

                        assertEquals("The category cannot be deleted because it has associated products.",
                                        exception.getMessage());
                }
        }

        @Nested
        class getCategoryById {
                @Test
                @DisplayName("getCategoryById should return CategoryDto when found")
                void testGetCategoryById() {
                        Long categoryIdToGet = 1L;

                        CategoryEntity existingCategoryEntity = new CategoryEntity(
                                        categoryIdToGet,
                                        "Existing Category",
                                        "Description");

                        when(categoryRepository.findCategoryById(categoryIdToGet))
                                        .thenReturn(Optional.of(existingCategoryEntity));

                        CategoryDto categoryDto = categoryServiceImpl.getCategoryById(categoryIdToGet);

                        assertAll("result",
                                        () -> assertNotNull(categoryDto),
                                        () -> assertEquals(categoryIdToGet, categoryDto.categoryId()),
                                        () -> assertEquals("Existing Category", categoryDto.name()),
                                        () -> assertEquals("Description", categoryDto.description()));
                }

                @Test
                @DisplayName("getCategoryById should throw exception when category not found")
                void testGetCategoryById_CategoryNotFound() {
                        Long categoryIdToGet = 1L;

                        when(categoryRepository.findCategoryById(categoryIdToGet))
                                        .thenReturn(Optional.empty());

                        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                                categoryServiceImpl.getCategoryById(categoryIdToGet);
                        });

                        assertEquals("Category with id 1 not found", exception.getMessage());
                }
        }

        @Nested
        class getAllCategories {
                @Test
                @DisplayName("getAllCategories should return list of Categories")
                void testGetAllCategories() {
                        CategoryEntity categoryEntity1 = new CategoryEntity(
                                        1L,
                                        "Category 1",
                                        "Description 1");

                        CategoryEntity categoryEntity2 = new CategoryEntity(
                                        2L,
                                        "Category 2",
                                        "Description 2");

                        when(categoryRepository.findAllCategories())
                                        .thenReturn(java.util.List.of(categoryEntity1, categoryEntity2));

                        java.util.List<CategoryDto> categoryDtos = categoryServiceImpl.getAllCategories();

                        assertAll("result",
                                        () -> assertNotNull(categoryDtos),
                                        () -> assertEquals(2, categoryDtos.size()),
                                        () -> {
                                                CategoryDto dto1 = categoryDtos.get(0);
                                                assertEquals(1L, dto1.categoryId());
                                                assertEquals("Category 1", dto1.name());
                                                assertEquals("Description 1", dto1.description());
                                        },
                                        () -> {
                                                CategoryDto dto2 = categoryDtos.get(1);
                                                assertEquals(2L, dto2.categoryId());
                                                assertEquals("Category 2", dto2.name());
                                                assertEquals("Description 2", dto2.description());
                                        });
                }

                @Test
                @DisplayName("getAllCategories should return empty list when no categories exist")
                void testGetAllCategories_EmptyList() {
                        when(categoryRepository.findAllCategories())
                                        .thenReturn(java.util.List.of());

                        java.util.List<CategoryDto> categoryDtos = categoryServiceImpl.getAllCategories();

                        assertNotNull(categoryDtos);
                        assertTrue(categoryDtos.isEmpty());
                }
        }

}