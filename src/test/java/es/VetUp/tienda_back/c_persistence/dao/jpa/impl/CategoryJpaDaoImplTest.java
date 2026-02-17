package es.VetUp.tienda_back.c_persistence.dao.jpa.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import es.VetUp.tienda_back.c_persistence.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import es.VetUp.tienda_back.c_persistence.dao.jpa.CategoryJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@DataJpaTest
@Import(TestConfig.class)
class CategoryJpaDaoImplTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CategoryJpaDao categoryJpaDao;

    @Test
    void testFindAll() {
        // Arrange: persiste una categoría en la BD en memoria
        CategoryJpaEntity category = new CategoryJpaEntity(
                null,
                "Categoría Test",
                "Descripción Test");

        entityManager.persist(category);
        entityManager.flush();

        // Act
        List<CategoryJpaEntity> result = categoryJpaDao.findAll();
        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.stream().anyMatch(c -> "Categoría Test".equals(c.getName())));
    }

    @Test
    void testFindById() {
        // Arrange: persiste una categoría en la BD en memoria
        CategoryJpaEntity category = new CategoryJpaEntity(
                null,
                "Categoría Test",
                "Descripción Test");

        entityManager.persist(category);
        entityManager.flush();

        // Act
        CategoryJpaEntity result = categoryJpaDao.findById(category.getCategoryId()).orElse(null);

        // Assert
        assertNotNull(result);
        assertEquals("Categoría Test", result.getName());
    }

    @Test
    void testInsert() {
        // Arrange
        CategoryJpaEntity category = new CategoryJpaEntity(
                null,
                "Categoría Nueva",
                "Descripción Nueva");

        // Act
        CategoryJpaEntity result = categoryJpaDao.insert(category);

        // Assert
        assertNotNull(result.getCategoryId());
        assertEquals("Categoría Nueva", result.getName());
    }

    @Test
    void testUpdate() {
        // Arrange: persiste una categoría en la BD en memoria
        CategoryJpaEntity category = new CategoryJpaEntity(
                null,
                "Categoría Original",
                "Descripción Original");

        entityManager.persist(category);
        entityManager.flush();

        // Modifica la categoría
        category.setName("Categoría Actualizada");
        category.setDescription("Descripción Actualizada");

        // Act
        CategoryJpaEntity result = categoryJpaDao.update(category);

        // Assert
        assertNotNull(result);
        assertEquals("Categoría Actualizada", result.getName());
    }

    @Test
    void testDeleteById() {
        // Arrange: persiste una categoría en la BD en memoria
        CategoryJpaEntity category = new CategoryJpaEntity(
                null,
                "Categoría A Eliminar",
                "Descripción A Eliminar");

        entityManager.persist(category);
        entityManager.flush();

        Long categoryIdToDelete = category.getCategoryId();

        // Act
        categoryJpaDao.deleteById(categoryIdToDelete);

        // Assert
        CategoryJpaEntity deletedCategory = entityManager.find(CategoryJpaEntity.class, categoryIdToDelete);
        assertNull(deletedCategory);
    }

    @Test
    void testFindByNameContainingIgnoreCase() {
        // Arrange
        entityManager.createQuery("DELETE FROM ProductJpaEntity").executeUpdate();
        entityManager.createQuery("DELETE FROM CategoryJpaEntity").executeUpdate();
        entityManager.flush();
        entityManager.clear();

        CategoryJpaEntity category1 = new CategoryJpaEntity(null, "Dog Food", "Food for dogs");
        CategoryJpaEntity category2 = new CategoryJpaEntity(null, "Cat Food", "Food for cats");
        CategoryJpaEntity category3 = new CategoryJpaEntity(null, "Toys", "Toys for pets");

        entityManager.persist(category1);
        entityManager.persist(category2);
        entityManager.persist(category3);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<CategoryJpaEntity> result = categoryJpaDao.findByNameContainingIgnoreCase("Food");

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(c -> "Dog Food".equals(c.getName())));
        assertTrue(result.stream().anyMatch(c -> "Cat Food".equals(c.getName())));
    }

    @Test
    void testFindAllPaginated() {
        // Arrange
        entityManager.createQuery("DELETE FROM ProductJpaEntity").executeUpdate();
        entityManager.createQuery("DELETE FROM CategoryJpaEntity").executeUpdate();
        entityManager.flush();
        entityManager.clear();

        for (int i = 0; i < 5; i++) {
            entityManager.persist(new CategoryJpaEntity(null, "Category " + i, "Desc " + i));
        }
        entityManager.flush();
        entityManager.clear();

        // Act
        List<CategoryJpaEntity> result = categoryJpaDao.findAll(0, 3);

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
    }
}