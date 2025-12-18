package es.VetUp.tienda_back.c_persistence.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.VetUp.tienda_back.b_domain.repository.entity.CategoryEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.CategoryJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity;
import es.VetUp.tienda_back.c_persistence.repository.mapper.CategoryPersistenceMapper;

@ExtendWith(MockitoExtension.class)
class CategoryRepositoryImplTest {

    @Mock
    private CategoryJpaDao categoryJpaDao;

    @InjectMocks
    private CategoryRepositoryImpl categoryRepositoryImpl;

    CategoryJpaEntity categoryJpaEntity1;
    CategoryJpaEntity categoryJpaEntity2;

    @BeforeEach
    void setUp() {
        categoryJpaEntity1 = new CategoryJpaEntity();
        categoryJpaEntity1.setCategoryId(1L);
        categoryJpaEntity1.setName("Category 1");
        categoryJpaEntity1.setDescription("Description 1");

        categoryJpaEntity2 = new CategoryJpaEntity();
        categoryJpaEntity2.setCategoryId(2L);
        categoryJpaEntity2.setName("Category 2");
        categoryJpaEntity2.setDescription("Description 2");
    }

    @Nested
    class FindAllCategoriesTest {
        @Test
        @DisplayName("Find all should return all categories")
        void testFindAllCategories() {
            List<CategoryJpaEntity> expectedList = List.of(categoryJpaEntity1, categoryJpaEntity2);
            when(categoryJpaDao.findAll()).thenReturn(expectedList);

            List<CategoryEntity> expectedEntities = expectedList.stream()
                    .map(CategoryPersistenceMapper.getInstance()::fromCategoryJpaEntitytoToCategoryEntity)
                    .toList();

            List<CategoryEntity> actualEntities = categoryRepositoryImpl.findAllCategories();

            assertAll(
                    () -> assertEquals(expectedEntities.size(), actualEntities.size()),
                    () -> assertEquals(expectedEntities.get(0).categoryId(), actualEntities.get(0).categoryId()),
                    () -> assertEquals(expectedEntities.get(1).categoryId(), actualEntities.get(1).categoryId()));
        }

        @Test
        @DisplayName("Find all should return empty list when no categories")
        void testFindAllCategoriesEmpty() {
            when(categoryJpaDao.findAll()).thenReturn(List.of());

            List<CategoryEntity> actualEntities = categoryRepositoryImpl.findAllCategories();

            assertTrue(actualEntities.isEmpty());
        }

    }

    @Nested
    class FindCategoryByIdTest {
        @Test
        @DisplayName("Find by id should return category when found")
        void testFindCategoryByIdFound() {
            Long categoryId = 1L;
            when(categoryJpaDao.findById(categoryId)).thenReturn(java.util.Optional.of(categoryJpaEntity1));

            Optional<CategoryEntity> actual = categoryRepositoryImpl.findCategoryById(categoryId);

            CategoryEntity expectedEntity = CategoryPersistenceMapper.getInstance()
                    .fromCategoryJpaEntitytoToCategoryEntity(categoryJpaEntity1);

            assertAll(
                    () -> assertEquals(true, actual.isPresent()),
                    () -> assertEquals(expectedEntity.categoryId(), actual.get().categoryId()));
        }

        @Test
        @DisplayName("Find by id should return empty when not found")
        void testFindCategoryByIdNotFound() {
            Long categoryId = 3L;
            when(categoryJpaDao.findById(categoryId)).thenReturn(java.util.Optional.empty());

            Optional<CategoryEntity> actual = categoryRepositoryImpl.findCategoryById(categoryId);

            assertEquals(false, actual.isPresent());
        }
    }

    @Nested
    class SaveCategoryTest {

        @Test
        @DisplayName("Save with category should add it to dao")
        void testSaveCategory() {
            // id null para que se ejecute la rama insert en saveCategory
            CategoryEntity categoryEntityToSave = new CategoryEntity(null, "New Category", "New Description");

            when(categoryJpaDao.insert(any(CategoryJpaEntity.class))).thenReturn(categoryJpaEntity1);

            CategoryEntity actual = categoryRepositoryImpl.saveCategory(categoryEntityToSave);

            CategoryEntity expected = CategoryPersistenceMapper.getInstance()
                    .fromCategoryJpaEntitytoToCategoryEntity(categoryJpaEntity1);

            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Save with existing category should update it in dao")
        void testUpdateCategory() {
            CategoryEntity categoryEntityToUpdate = new CategoryEntity(1L, "Updated Category", "Updated Description");

            when(categoryJpaDao.update(any(CategoryJpaEntity.class))).thenReturn(categoryJpaEntity1);

            CategoryEntity actual = categoryRepositoryImpl.saveCategory(categoryEntityToUpdate);

            CategoryEntity expected = CategoryPersistenceMapper.getInstance()
                    .fromCategoryJpaEntitytoToCategoryEntity(categoryJpaEntity1);

            assertEquals(expected, actual);
        }

    }

    @Nested
    class UpdateCategoryTest {
        @Test
        @DisplayName("UpdateCategory should update existing category")
        void testUpdateCategory() {
            CategoryEntity categoryEntityToUpdate = new CategoryEntity(1L, "Updated Category", "Updated Description");

            when(categoryJpaDao.update(any(CategoryJpaEntity.class))).thenReturn(categoryJpaEntity1);

            CategoryEntity actual = categoryRepositoryImpl.updateCategory(1L,categoryEntityToUpdate);

            CategoryEntity expected = CategoryPersistenceMapper.getInstance()
                    .fromCategoryJpaEntitytoToCategoryEntity(categoryJpaEntity1);

            assertEquals(expected, actual);
        }
    }

    @Nested
    class DeleteCategoryTest {
        @Test
        @DisplayName("deleteCategory should call dao to delete category by id")
        void testDeleteCategory() {
            Long categoryIdToDelete = 1L;

            categoryRepositoryImpl.deleteCategory(categoryIdToDelete);
        }
    }

}