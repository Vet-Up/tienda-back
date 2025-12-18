package es.VetUp.tienda_back.c_persistence.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import es.VetUp.tienda_back.b_domain.model.Page;
import es.VetUp.tienda_back.b_domain.repository.entity.ProductEntity;

import org.mockito.junit.jupiter.MockitoExtension;

import es.VetUp.tienda_back.c_persistence.dao.jpa.ProductJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.ProductJpaEntity;
import es.VetUp.tienda_back.c_persistence.repository.mapper.ProductPersistenceMapper;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryImplTest {

    @Mock
    private ProductJpaDao productJpaDao;

    @InjectMocks
    private ProductRepositoryImpl productRepositoryImpl;

    ProductJpaEntity productJpaEntity1;
    ProductJpaEntity productJpaEntity2;

    @BeforeEach
    void setUp() {
        productJpaEntity1 = new ProductJpaEntity();
        productJpaEntity1.setProductId(1L);
        productJpaEntity1.setName("Product 1");
        productJpaEntity1.setBrand("Brand A");
        productJpaEntity1.setCategoryId(100L);

        productJpaEntity2 = new ProductJpaEntity();
        productJpaEntity2.setProductId(2L);
        productJpaEntity2.setName("Product 2");
        productJpaEntity2.setBrand("Brand B");
        productJpaEntity2.setCategoryId(200L);
    }

    @Nested
    class findAllProducts {
        @Test
        @DisplayName("findAll should return all products paginated")
        void testFindAllProducts() {
            List<ProductJpaEntity> expectedList = List.of(productJpaEntity1, productJpaEntity2);
            Page<ProductJpaEntity> expectedPage = new Page<>(expectedList, 1, 10, 2);

            when(productJpaDao.findAll(1, 10)).thenReturn(expectedList);
            when(productJpaDao.count()).thenReturn(2L);

            Page<ProductEntity> actual = productRepositoryImpl.findAllProducts(1, 10);

            List<ProductEntity> expectedEntities = expectedList.stream()
                    .map(ProductPersistenceMapper.getInstance()::fromProductJpaEntitytoToProductEntity)
                    .toList();

            assertAll(
                    () -> assertEquals(expectedEntities, actual.data()),
                    () -> assertEquals(expectedPage.pageNumber(), actual.pageNumber()),
                    () -> assertEquals(expectedPage.pageSize(), actual.pageSize()),
                    () -> assertEquals(expectedPage.totalPages(), actual.totalPages()));
        }

        @Test
        @DisplayName("findAll should return empty page when no products exist")
        void testFindAllProductsEmpty() {

            List<ProductJpaEntity> expectedList = List.of();
            Page<ProductJpaEntity> expectedPage = new Page<>(expectedList, 1, 10, 0);

            when(productJpaDao.findAll(1, 10)).thenReturn(expectedList);
            when(productJpaDao.count()).thenReturn(0L);

            Page<ProductEntity> actual = productRepositoryImpl.findAllProducts(1, 10);

            List<ProductEntity> expectedEntities = expectedList.stream()
                    .map(ProductPersistenceMapper.getInstance()::fromProductJpaEntitytoToProductEntity)
                    .toList();

            assertAll(
                    () -> assertEquals(expectedEntities, actual.data()),
                    () -> assertEquals(expectedPage.pageNumber(), actual.pageNumber()),
                    () -> assertEquals(expectedPage.pageSize(), actual.pageSize()),
                    () -> assertEquals(expectedPage.totalPages(), actual.totalPages()));
        }

    }

    @Nested
    class findProductById {
        @Test
        @DisplayName("findProductById should return product when found")
        void testFindProductById() {
            when(productJpaDao.findById(1L)).thenReturn(java.util.Optional.of(productJpaEntity1));

            java.util.Optional<ProductEntity> actual = productRepositoryImpl.findProductById(1L);

            ProductEntity expectedEntity = ProductPersistenceMapper.getInstance()
                    .fromProductJpaEntitytoToProductEntity(productJpaEntity1);

            assertAll(
                    () -> assertEquals(true, actual.isPresent()),
                    () -> assertEquals(expectedEntity, actual.get()));
        }

        @Test
        @DisplayName("findProductById should return empty when not found")
        void testFindProductByIdNotFound() {
            when(productJpaDao.findById(3L)).thenReturn(java.util.Optional.empty());

            java.util.Optional<ProductEntity> actual = productRepositoryImpl.findProductById(3L);

            assertAll(
                    () -> assertEquals(false, actual.isPresent()));
        }

    }

    @Nested
    class getProductByCategory {
        @Test
        @DisplayName("getProductByCategory should return products for given category")
        void testGetProductByCategory() {
            List<ProductJpaEntity> expectedList = List.of(productJpaEntity1);

            when(productJpaDao.findByCategoryId(100, 1, 10)).thenReturn(expectedList);

            List<ProductEntity> actual = productRepositoryImpl.getProductByCategory(100, 1, 10);

            List<ProductEntity> expectedEntities = expectedList.stream()
                    .map(ProductPersistenceMapper.getInstance()::fromProductJpaEntitytoToProductEntity)
                    .toList();

            assertEquals(expectedEntities, actual);
        }

        @Test
        @DisplayName("getProductByCategory should return empty list when no products found for given category")
        void testGetProductByCategoryEmpty() {
            List<ProductJpaEntity> expectedList = List.of();

            when(productJpaDao.findByCategoryId(300, 1, 10)).thenReturn(expectedList);

            List<ProductEntity> actual = productRepositoryImpl.getProductByCategory(300, 1, 10);

            List<ProductEntity> expectedEntities = expectedList.stream()
                    .map(ProductPersistenceMapper.getInstance()::fromProductJpaEntitytoToProductEntity)
                    .toList();

            assertEquals(expectedEntities, actual);
        }

    }

    @Nested
    class getProductByBrand {
        @Test
        @DisplayName("getProductByBrand should return products for given brand")
        void testGetProductByBrand() {
            List<ProductJpaEntity> expectedList = List.of(productJpaEntity2);

            when(productJpaDao.findByBrandId("Brand B", 1, 10)).thenReturn(expectedList);

            List<ProductEntity> actual = productRepositoryImpl.getProductByBrand("Brand B", 1, 10);

            List<ProductEntity> expectedEntities = expectedList.stream()
                    .map(ProductPersistenceMapper.getInstance()::fromProductJpaEntitytoToProductEntity)
                    .toList();

            assertEquals(expectedEntities, actual);
        }

        @Test
        @DisplayName("getProductByBrand should return empty list when no products found for given brand")
        void testGetProductByBrandEmpty() {
            List<ProductJpaEntity> expectedList = List.of();

            when(productJpaDao.findByBrandId("Brand C", 1, 10)).thenReturn(expectedList);

            List<ProductEntity> actual = productRepositoryImpl.getProductByBrand("Brand C", 1, 10);

            List<ProductEntity> expectedEntities = expectedList.stream()
                    .map(ProductPersistenceMapper.getInstance()::fromProductJpaEntitytoToProductEntity)
                    .toList();

            assertEquals(expectedEntities, actual);
        }
    }

    @Nested
    class saveProduct {
        @Test
        @DisplayName("save with product should add it to dao")
        void testSaveProduct() {
            // Creamos un ProductEntity con productId=null para simular un nuevo producto
            ProductEntity productEntityToSave = new ProductEntity(
                    null,
                    "Product 1",
                    null,
                    null,
                    null,
                    null,
                    "Brand A",
                    100L);

            when(productJpaDao.insert(any(ProductJpaEntity.class))).thenReturn(productJpaEntity1);

            ProductEntity actual = productRepositoryImpl.saveProduct(productEntityToSave);

            ProductEntity expectedEntity = ProductPersistenceMapper.getInstance()
                    .fromProductJpaEntitytoToProductEntity(productJpaEntity1);

            assertEquals(expectedEntity, actual);
        }

        @Test
        @DisplayName("save with existing product should update it in dao")
        void testSaveExistingProduct() {
            ProductEntity productEntityToUpdate = ProductPersistenceMapper.getInstance()
                    .fromProductJpaEntitytoToProductEntity(productJpaEntity1);

            when(productJpaDao.update(any(ProductJpaEntity.class))).thenReturn(productJpaEntity1);

            ProductEntity actual = productRepositoryImpl.saveProduct(productEntityToUpdate);

            ProductEntity expectedEntity = ProductPersistenceMapper.getInstance()
                    .fromProductJpaEntitytoToProductEntity(productJpaEntity1);

            assertEquals(expectedEntity, actual);
        }
    }

    @Nested
    class updateProduct {
        @Test
        @DisplayName("updateProduct should update existing product")
        void testUpdateProduct() {
            ProductEntity productEntityToUpdate = ProductPersistenceMapper.getInstance()
                    .fromProductJpaEntitytoToProductEntity(productJpaEntity1);

            when(productJpaDao.update(any(ProductJpaEntity.class))).thenReturn(productJpaEntity1);

            ProductEntity actual = productRepositoryImpl.updateProduct(1L, productEntityToUpdate);

            ProductEntity expectedEntity = ProductPersistenceMapper.getInstance()
                    .fromProductJpaEntitytoToProductEntity(productJpaEntity1);

            assertEquals(expectedEntity, actual);
        }

    }

    @Nested
    class deleteProduct {
        @Test
        @DisplayName("deleteProduct should call dao to delete product by id")
        void testDeleteProduct() {
            Long productIdToDelete = 1L;

            productRepositoryImpl.deleteProduct(productIdToDelete);

        }

    }

}
