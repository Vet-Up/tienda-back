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

        private void cleanUp() {
                entityManager.createQuery("DELETE FROM ReviewJpaEntity").executeUpdate();
                entityManager.createQuery("DELETE FROM ProductJpaEntity").executeUpdate();
                entityManager.createQuery("DELETE FROM CategoryJpaEntity").executeUpdate();
                entityManager.flush();
                entityManager.clear();
        }

        @Test
        void testFindAll() {
                // Limpia la tabla antes de persistir
                cleanUp();

                CategoryJpaEntity category = new CategoryJpaEntity();
                category.setName("Categoría Test");
                entityManager.persist(category);
                entityManager.flush();
                entityManager.clear();

                ProductJpaEntity entity = new ProductJpaEntity(
                                null,
                                "Producto Test",
                                "Descripción Test",
                                new java.math.BigDecimal("19.99"),
                                new java.math.BigDecimal("15.99"),
                                "imagen.jpg",
                                "MarcaTest",
                                category,
                                10);
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
                es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity category = new es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity();
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
                                category,
                                10);
                entityManager.persist(entity);
                entityManager.flush();

                // Act
                List<ProductJpaEntity> result = productJpaDao.findByCategoryId(category.getCategoryId().intValue(), 1,
                                10);

                // Assert
                assertNotNull(result);
                assertFalse(result.isEmpty());
                assertEquals(category.getCategoryId(), result.get(0).getCategory().getCategoryId());
        }

        @Test
        void testFindByBrandId() {
                // Arrange: persiste una categoría y un producto en la BD en memoria
                es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity category = new es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity();
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
                                category,
                                10);
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
                es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity category = new es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity();
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
                                category,
                                10);

                // Act
                ProductJpaEntity insertedEntity = productJpaDao.insert(entity);

                // Assert
                assertNotNull(insertedEntity.getProductId());
                assertEquals("Producto Insert", insertedEntity.getName());
        }

        @Test
        void testUpdate() {
                // Arrange: persiste una categoría y un producto en la BD en memoria
                es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity category = new es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity();
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
                                category,
                                10);
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
                es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity category = new es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity();
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
                                category,
                                10);
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
                es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity category = new es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity();
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
                                category,
                                10);
                entityManager.persist(entity);
                entityManager.flush();
                Long productId = entity.getProductId();

                // Act
                var result = productJpaDao.findById(productId);

                // Assert
                assertTrue(result.isPresent());
                assertEquals("Producto Buscar", result.get().getName());
        }

        @Test
        void testFindByName() {
                // Arrange
                cleanUp();
                CategoryJpaEntity category = new CategoryJpaEntity(null, "Test Cat", "Desc");
                entityManager.persist(category);

                ProductJpaEntity p1 = new ProductJpaEntity(null, "Dog Food", "Desc", new java.math.BigDecimal("10.00"),
                                new java.math.BigDecimal("10.00"), "pic", "Brand", category, 10);
                ProductJpaEntity p2 = new ProductJpaEntity(null, "Cat Food", "Desc", new java.math.BigDecimal("12.00"),
                                new java.math.BigDecimal("12.00"), "pic", "Brand", category, 10);
                entityManager.persist(p1);
                entityManager.persist(p2);
                entityManager.flush();
                entityManager.clear();

                // Act
                List<ProductJpaEntity> result = productJpaDao.findByName("food", 0, 10, "name-asc");

                // Assert
                assertEquals(2, result.size());
                assertEquals("Cat Food", result.get(0).getName());
        }

        @Test
        void testFindAllOrdered() {
                // Arrange
                cleanUp();
                CategoryJpaEntity category = new CategoryJpaEntity(null, "Test Cat", "Desc");
                entityManager.persist(category);

                ProductJpaEntity p1 = new ProductJpaEntity(null, "Product B", "Desc", new java.math.BigDecimal("20.00"),
                                new java.math.BigDecimal("20.00"), "pic", "Brand", category, 10);
                ProductJpaEntity p2 = new ProductJpaEntity(null, "Product A", "Desc", new java.math.BigDecimal("10.00"),
                                new java.math.BigDecimal("10.00"), "pic", "Brand", category, 10);
                entityManager.persist(p1);
                entityManager.persist(p2);
                entityManager.flush();
                entityManager.clear();

                // Act
                List<ProductJpaEntity> result = productJpaDao.findAllOrdered("price-asc", 1, 10);

                // Assert
                assertEquals(2, result.size());
                assertEquals("Product A", result.get(0).getName());
        }

        @Test
        void testFindByPriceRange() {
                // Arrange
                cleanUp();
                CategoryJpaEntity category = new CategoryJpaEntity(null, "Test Cat", "Desc");
                entityManager.persist(category);

                ProductJpaEntity p1 = new ProductJpaEntity(null, "Prod 1", "Desc", new java.math.BigDecimal("10.00"),
                                new java.math.BigDecimal("10.00"), "pic", "Brand", category, 10);
                ProductJpaEntity p2 = new ProductJpaEntity(null, "Prod 2", "Desc", new java.math.BigDecimal("30.00"),
                                new java.math.BigDecimal("30.00"), "pic", "Brand", category, 10);
                entityManager.persist(p1);
                entityManager.persist(p2);
                entityManager.flush();
                entityManager.clear();

                // Act
                List<ProductJpaEntity> result = productJpaDao.findByPriceRange(5.0, 15.0, 1, 10, "price-asc");

                // Assert
                assertEquals(1, result.size());
                assertEquals("Prod 1", result.get(0).getName());
        }

        @Test
        void testFindByCategoryIds() {
                // Arrange
                cleanUp();
                CategoryJpaEntity c1 = new CategoryJpaEntity(null, "C1", "Desc");
                CategoryJpaEntity c2 = new CategoryJpaEntity(null, "C2", "Desc");
                entityManager.persist(c1);
                entityManager.persist(c2);

                ProductJpaEntity p1 = new ProductJpaEntity(null, "Prod 1", "Desc", new java.math.BigDecimal("10.00"),
                                new java.math.BigDecimal("10.00"), "pic", "Brand", c1, 10);
                ProductJpaEntity p2 = new ProductJpaEntity(null, "Prod 2", "Desc", new java.math.BigDecimal("20.00"),
                                new java.math.BigDecimal("20.00"), "pic", "Brand", c2, 10);
                entityManager.persist(p1);
                entityManager.persist(p2);
                entityManager.flush();
                entityManager.clear();

                // Act
                List<ProductJpaEntity> result = productJpaDao.findByCategoryIds(
                                List.of(c1.getCategoryId().intValue(), c2.getCategoryId().intValue()), 0, 10,
                                "name-asc");

                // Assert
                assertEquals(2, result.size());
        }
}