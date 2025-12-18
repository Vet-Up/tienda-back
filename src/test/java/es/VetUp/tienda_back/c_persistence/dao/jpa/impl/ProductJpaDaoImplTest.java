package es.VetUp.tienda_back.c_persistence.dao.jpa.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import es.VetUp.tienda_back.c_persistence.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import es.VetUp.tienda_back.c_persistence.dao.jpa.ProductJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.ProductJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(TestConfig.class)
class ProductJpaDaoImplTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProductJpaDao productJpaDao;

    @Test
    void testFindAll() {
        // Limpia la tabla antes de persistir
        entityManager.createQuery("DELETE FROM ProductJpaEntity").executeUpdate();
        entityManager.createQuery("DELETE FROM CategoryJpaEntity").executeUpdate();
        entityManager.flush();

        CategoryJpaEntity category = new CategoryJpaEntity();
        category.setName("Categoría Test");
        entityManager.persist(category);
        entityManager.flush();

        ProductJpaEntity entity = new ProductJpaEntity(
                null,
                "Producto Test",
                "Descripción Test",
                new java.math.BigDecimal("19.99"),
                new java.math.BigDecimal("15.99"),
                "imagen.jpg",
                "MarcaTest",
                category
        );
        entityManager.persist(entity);
        entityManager.flush();

        List<ProductJpaEntity> result = productJpaDao.findAll(0, 10);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.stream().anyMatch(p -> "Producto Test".equals(p.getName())));
    }

    @Test
        void testFindByCategoryId() {
        // Arrange: persiste una categoría y un producto relacionado
        es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity category =
            new es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity();
        category.setName("Categoría Test");
        entityManager.persist(category);
        entityManager.flush();

        ProductJpaEntity entity = new ProductJpaEntity(
                null,
                "Producto Test",
                "Descripción Test",
                new java.math.BigDecimal("19.99"),
                new java.math.BigDecimal("15.99"),
                "imagen.jpg",
                "MarcaTest",
                category
        );
        entityManager.persist(entity);
        entityManager.flush();

        // Act
        List<ProductJpaEntity> result = productJpaDao.findByCategoryId(category.getCategoryId().intValue(), 1, 10);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(category.getCategoryId(), result.get(0).getCategory().getCategoryId());
        }

    @Test
    void testFindByBrandId() {
        // Arrange: persiste una categoría y un producto en la BD en memoria
        es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity category =
            new es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity();
        category.setName("Categoría Test");
        entityManager.persist(category);
        entityManager.flush();

        ProductJpaEntity entity = new ProductJpaEntity(
                null,
                "Producto Test",
                "Descripción Test",
                new java.math.BigDecimal("19.99"),
                new java.math.BigDecimal("15.99"),
                "imagen.jpg",
                "MarcaTest",
                category
        );
        entityManager.persist(entity);
        entityManager.flush();

        // Act
        List<ProductJpaEntity> result = productJpaDao.findByBrandId("MarcaTest", 1, 10);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("MarcaTest", result.get(0).getBrand());
    }

    @Test
    void testInsert() {
        // Arrange: persiste una categoría
        es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity category =
                new es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity();
        category.setName("Categoría Test");
        entityManager.persist(category);
        entityManager.flush();

        ProductJpaEntity entity = new ProductJpaEntity(
                null,
                "Producto Insert",
                "Descripción Insert",
                new java.math.BigDecimal("29.99"),
                new java.math.BigDecimal("25.99"),
                "imagen_insert.jpg",
                "MarcaInsert",
                category
        );

        // Act
        ProductJpaEntity insertedEntity = productJpaDao.insert(entity);

        // Assert
        assertNotNull(insertedEntity.getProductId());
        assertEquals("Producto Insert", insertedEntity.getName());
    }

    @Test
    void testUpdate() {
        // Arrange: persiste una categoría y un producto en la BD en memoria
        es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity category =
                new es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity();
        category.setName("Categoría Test");
        entityManager.persist(category);
        entityManager.flush();

        ProductJpaEntity entity = new ProductJpaEntity(
                null,
                "Producto Original",
                "Descripción Original",
                new java.math.BigDecimal("39.99"),
                new java.math.BigDecimal("35.99"),
                "imagen_original.jpg",
                "MarcaOriginal",
                category
        );
        entityManager.persist(entity);
        entityManager.flush();

        // Act
        entity.setName("Producto Actualizado");
        ProductJpaEntity updatedEntity = productJpaDao.update(entity);

        // Assert
        assertEquals("Producto Actualizado", updatedEntity.getName());
    }

    @Test
    void testDeleteById() {
        // Arrange: persiste una categoría y un producto en la BD en memoria
        es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity category =
                new es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity();
        category.setName("Categoría Test");
        entityManager.persist(category);
        entityManager.flush();

        ProductJpaEntity entity = new ProductJpaEntity(
                null,
                "Producto A Eliminar",
                "Descripción A Eliminar",
                new java.math.BigDecimal("49.99"),
                new java.math.BigDecimal("45.99"),
                "imagen_eliminar.jpg",
                "MarcaEliminar",
                category
        );
        entityManager.persist(entity);
        entityManager.flush();
        Long productId = entity.getProductId();

        // Act
        productJpaDao.deleteById(productId);
        ProductJpaEntity deletedEntity = entityManager.find(ProductJpaEntity.class, productId);

        // Assert
        assertNull(deletedEntity);
    }

    @Test
    void testFindById() {
        // Arrange: persiste una categoría y un producto en la BD en memoria
        es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity category =
                new es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity();
        category.setName("Categoría Test");
        entityManager.persist(category);
        entityManager.flush();

        ProductJpaEntity entity = new ProductJpaEntity(
                null,
                "Producto Buscar",
                "Descripción Buscar",
                new java.math.BigDecimal("59.99"),
                new java.math.BigDecimal("55.99"),
                "imagen_buscar.jpg",
                "MarcaBuscar",
                category
        );
        entityManager.persist(entity);
        entityManager.flush();
        Long productId = entity.getProductId();

        // Act
        var result = productJpaDao.findById(productId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Producto Buscar", result.get().getName());
    }












}