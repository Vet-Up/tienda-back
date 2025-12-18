package es.VetUp.tienda_back.b_domain.service.impl;

import es.VetUp.tienda_back.b_domain.mapper.ProductMapper;
import es.VetUp.tienda_back.b_domain.model.Page;
import es.VetUp.tienda_back.b_domain.repository.ProductRepository;
import es.VetUp.tienda_back.b_domain.repository.entity.ProductEntity;
import es.VetUp.tienda_back.b_domain.service.dto.ProductDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    @Nested
    class GetAllTests {

        @Test
        @DisplayName("getAll should return list of products")
        void testGetAll() {
            int page = 1;
            int size = 10;

            ProductEntity product1 = new ProductEntity(
                    1L,
                    "Dog Food",
                    "High quality dog food",
                    new java.math.BigDecimal("29.99"),
                    new java.math.BigDecimal("24.99"),
                    "dog_food.jpg",
                    "PetBrand",
                    2L);

            ProductEntity product2 = new ProductEntity(
                    2L,
                    "Cat Food",
                    "Premium cat food",
                    new java.math.BigDecimal("19.99"),
                    new java.math.BigDecimal("15.99"),
                    "cat_food.jpg",
                    "CatBrand",
                    3L);

            List<ProductEntity> productEntities = List.of(product1, product2);
            Page<ProductEntity> productEntityPage = new Page<>(productEntities, page, size, 2);

            when(productRepository.findAllProducts(page, size)).thenReturn(productEntityPage);

            Page<ProductDto> result = productServiceImpl.getAllProducts(page, size);

            assertAll("result",
                    () -> assertNotNull(result, "Result should not be null"),
                    () -> assertEquals(2, result.data().size(), "Result size should be 2"),
                    () -> assertEquals("Dog Food", result.data().getFirst().name()),
                    () -> assertEquals("Cat Food", result.data().get(1).name()));

        }

        @ParameterizedTest
        @CsvSource({
                "1, 5",
                "2, 10",
                "1, 20",
                "3, 15"
        })
        @DisplayName("getAll should work with different page and size values - parameterized")
        void testGetAllParameterized(int page, int size) {
            ProductEntity product1 = new ProductEntity(
                    1L,
                    "Product 1",
                    "Description 1",
                    new java.math.BigDecimal("10.00"),
                    new java.math.BigDecimal("8.00"),
                    "pic1.jpg",
                    "Brand1",
                    1L);

            List<ProductEntity> productEntities = List.of(product1);
            Page<ProductEntity> productEntityPage = new Page<>(productEntities, page, size, 1);

            when(productRepository.findAllProducts(page, size)).thenReturn(productEntityPage);

            Page<ProductDto> result = productServiceImpl.getAllProducts(page, size);

            assertAll("result",
                    () -> assertNotNull(result),
                    () -> assertEquals(1, result.data().size()),
                    () -> assertEquals(page, result.pageNumber()),
                    () -> assertEquals(size, result.pageSize()));
        }

        @ParameterizedTest
        @CsvSource({
                "0, 10",
                "-1, 10",
                "1, 0",
                "1, -1",
                "0, 0"
        })
        @DisplayName("getAll should throw IllegalArgumentException when page or size are invalid")
        void testGetAllWithInvalidParameters(int page, int size) {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> productServiceImpl.getAllProducts(page, size));

            assertEquals("Page and size must be greater than 0", exception.getMessage());
        }

        @Test
        @DisplayName("getAll should return empty page when no products found")
        void testGetAllEmpty() {
            int page = 1;
            int size = 10;

            List<ProductEntity> emptyList = List.of();
            Page<ProductEntity> emptyPage = new Page<>(emptyList, page, size, 0);

            when(productRepository.findAllProducts(page, size)).thenReturn(emptyPage);

            Page<ProductDto> result = productServiceImpl.getAllProducts(page, size);

            assertAll("result",
                    () -> assertNotNull(result),
                    () -> assertEquals(0, result.data().size()),
                    () -> assertEquals(0, result.totalElements()));
        }
    }

    @Nested
    class GetProductsByIdTests {

        @Test
        @DisplayName("getProductById should return productDto when found")
        void testGetProductById() {
            Long productId = 1L;

            ProductEntity productEntity = new ProductEntity(
                    productId,
                    "Dog Food",
                    "High quality dog food",
                    new java.math.BigDecimal("29.99"),
                    new java.math.BigDecimal("24.99"),
                    "dog_food.jpg",
                    "PetBrand",
                    2L);

            ProductDto expected = ProductMapper.getInstance()
                    .fromProducttoProductDto(ProductMapper.getInstance().fromProductEntitytoProduct(productEntity));
            when(productRepository.findProductById(productId)).thenReturn(Optional.of(productEntity));

            ProductDto result = productServiceImpl.getProductById(productId);

            assertEquals(expected, result);

        }

        @Test
        @DisplayName("getProductById should throw exception when product not found")
        void testGetProductByIdNotFound() {
            Long productId = 1L;
            when(productRepository.findProductById(productId)).thenReturn(Optional.empty());
            Exception exception = assertThrows(RuntimeException.class,
                    () -> productServiceImpl.getProductById(productId));
            assertEquals("Product with id " + productId + " not found", exception.getMessage());

        }

    }

    @Nested
    class FindProductByIdTests {
        @Test
        @DisplayName("findProductById should return Product)Dto when found")
        void testFindProductById() {
            Long productId = 1L;
            ProductEntity productEntity = new ProductEntity(
                    productId,
                    "Dog Food",
                    "High quality dog food",
                    new java.math.BigDecimal("29.99"),
                    new java.math.BigDecimal("24.99"),
                    "dog_food.jpg",
                    "PetBrand",
                    2L);
            ProductDto expected = ProductMapper.getInstance()
                    .fromProducttoProductDto(ProductMapper.getInstance().fromProductEntitytoProduct(productEntity));
            when(productRepository.findProductById(productId)).thenReturn(Optional.of(productEntity));
            Optional<ProductDto> result = productServiceImpl.findProductById(productId);
            assertTrue(result.isPresent());
            assertEquals(expected, result.get());
        }

        @Test
        @DisplayName("findProductById should return empty when product not found")
        void testFindProductByIdNotFound() {
            Long productId = 1L;
            when(productRepository.findProductById(productId)).thenReturn(Optional.empty());
            Optional<ProductDto> result = productServiceImpl.findProductById(productId);
            assertFalse(result.isPresent());
        }

    }

    @Nested
    class GetProductsByCategoryIdTests {
        @Test
        @DisplayName("getProductsByCategoryId should return list of products")
        void testGetProductsByCategoryId() {
            int categoryId = 2;
            int page = 1;
            int size = 10;

            ProductEntity product1 = new ProductEntity(
                    1L,
                    "Dog Food",
                    "High quality dog food",
                    new java.math.BigDecimal("29.99"),
                    new java.math.BigDecimal("24.99"),
                    "dog_food.jpg",
                    "PetBrand",
                    2L);
            ProductEntity product2 = new ProductEntity(
                    2L,
                    "Dog Toy",
                    "Fun dog toy",
                    new java.math.BigDecimal("9.99"),
                    new java.math.BigDecimal("7.99"),
                    "dog_toy.jpg",
                    "ToyBrand",
                    2L);

            List<ProductEntity> productEntities = List.of(product1, product2);
            when(productRepository.getProductByCategory(categoryId, page, size)).thenReturn(productEntities);

            List<ProductDto> result = productServiceImpl.getProductByCategory(categoryId, page, size);

            assertAll("result",
                    () -> assertNotNull(result),
                    () -> assertEquals(2, result.size()),
                    () -> assertEquals("Dog Food", result.get(0).name()),
                    () -> assertEquals("Dog Toy", result.get(1).name()));
        }

        @Test
        @DisplayName("getProductsByCategoryId should return empty list when no products found")
        void testGetProductsByCategoryIdEmpty() {
            int categoryId = 99;
            int page = 1;
            int size = 10;

            when(productRepository.getProductByCategory(categoryId, page, size)).thenReturn(List.of());
            List<ProductDto> result = productServiceImpl.getProductByCategory(categoryId, page, size);
            assertNotNull(result);
            assertEquals(0, result.size());

        }

    }

    @Nested
    class GetProductsByBrandTests {
        @Test
        @DisplayName("getProductsByBrand should return list of products")
        void testGetProductsByBrand() {
            String brand = "PetBrand";
            int page = 1;
            int size = 10;

            ProductEntity product1 = new ProductEntity(
                    1L,
                    "Dog Food",
                    "High quality dog food",
                    new java.math.BigDecimal("29.99"),
                    new java.math.BigDecimal("24.99"),
                    "dog_food.jpg",
                    "PetBrand",
                    2L);

            List<ProductEntity> productEntities = List.of(product1);
            when(productRepository.getProductByBrand(brand, page, size)).thenReturn(productEntities);

            List<ProductDto> result = productServiceImpl.getProductByBrand(brand, page, size);

            assertAll("result",
                    () -> assertNotNull(result),
                    () -> assertEquals(1, result.size()),
                    () -> assertEquals("Dog Food", result.get(0).name()));
        }

        @Test
        @DisplayName("getProductsByBrand should throw exception when no products found")
        void testGetProductsByBrandEmpty() {
            String brand = "UnknownBrand";
            int page = 1;
            int size = 10;

            when(productRepository.getProductByBrand(brand, page, size)).thenReturn(List.of());
            RuntimeException exception = assertThrows(RuntimeException.class,
                    () -> productServiceImpl.getProductByBrand(brand, page, size));
            assertEquals("No products found for brand " + brand, exception.getMessage());
        }

    }

    @Nested
    class createProductTests {
        @Test
        @DisplayName("createProduct should create and return productDto")
        void testCreateProduct() {
            ProductDto productDtoToCreate = new ProductDto(
                    null,
                    "Dog Food",
                    "High quality dog food",
                    new java.math.BigDecimal("29.99"),
                    new java.math.BigDecimal("24.99"),
                    "dog_food.jpg",
                    "PetBrand",
                    2L);

            ProductEntity productEntityToCreate = ProductMapper.getInstance()
                    .fromProducttoProductEntity(
                            ProductMapper.getInstance().fromProductDtotoProduct(productDtoToCreate));

            ProductEntity createdProductEntity = new ProductEntity(
                    1L,
                    "Dog Food",
                    "High quality dog food",
                    new java.math.BigDecimal("29.99"),
                    new java.math.BigDecimal("24.99"),
                    "dog_food.jpg",
                    "PetBrand",
                    2L);

            when(productRepository.saveProduct(productEntityToCreate)).thenReturn(createdProductEntity);

            ProductDto result = productServiceImpl.createProduct(productDtoToCreate);

            assertAll("result",
                    () -> assertNotNull(result),
                    () -> assertEquals(1L, result.productId()),
                    () -> assertEquals("Dog Food", result.name()));
        }

        @Test
        @DisplayName("createProduct should throw exception when productDto is null")
        void testCreateProductNull() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> productServiceImpl.createProduct(null));
            assertEquals("ProductDto cannot be null", exception.getMessage());
        }

    }

    @Nested
    class updateProductTests {
        @Test
        @DisplayName("updateProduct should update and return productDto")
        void testUpdateProduct() {
            Long productIdToUpdate = 1L;
            ProductDto productDtoToUpdate = new ProductDto(
                    productIdToUpdate,
                    "Updated Dog Food",
                    "Updated description",
                    new java.math.BigDecimal("31.99"),
                    new java.math.BigDecimal("26.99"),
                    "updated_dog_food.jpg",
                    "PetBrand",
                    2L);

            ProductEntity existingProductEntity = new ProductEntity(
                    productIdToUpdate,
                    "Dog Food",
                    "High quality dog food",
                    new java.math.BigDecimal("29.99"),
                    new java.math.BigDecimal("24.99"),
                    "dog_food.jpg",
                    "PetBrand",
                    2L);

            ProductEntity productEntityToUpdate = ProductMapper.getInstance()
                    .fromProducttoProductEntity(
                            ProductMapper.getInstance().fromProductDtotoProduct(productDtoToUpdate));

            ProductEntity updatedProductEntity = new ProductEntity(
                    productIdToUpdate,
                    "Updated Dog Food",
                    "Updated description",
                    new java.math.BigDecimal("31.99"),
                    new java.math.BigDecimal("26.99"),
                    "updated_dog_food.jpg",
                    "PetBrand",
                    2L);

            when(productRepository.findProductById(productIdToUpdate)).thenReturn(Optional.of(existingProductEntity));
            when(productRepository.updateProduct(productIdToUpdate, productEntityToUpdate)).thenReturn(updatedProductEntity);

            ProductDto result = productServiceImpl.updateProduct(productIdToUpdate, productDtoToUpdate);

            assertAll("result",
                    () -> assertNotNull(result),
                    () -> assertEquals(productIdToUpdate, result.productId()),
                    () -> assertEquals("Updated Dog Food", result.name()));

        }

        @Test
        @DisplayName("updateProduct should throw exception when product does not exist")
        void testUpdateProductNotFound() {
            Long productIdToUpdate = 1L;
            ProductDto productDtoToUpdate = new ProductDto(
                    productIdToUpdate,
                    "Updated Dog Food",
                    "Updated description",
                    new java.math.BigDecimal("31.99"),
                    new java.math.BigDecimal("26.99"),
                    "updated_dog_food.jpg",
                    "PetBrand",
                    2L);

            when(productRepository.findProductById(productIdToUpdate)).thenReturn(Optional.empty());

            RuntimeException exception = assertThrows(RuntimeException.class,
                    () -> productServiceImpl.updateProduct(productIdToUpdate, productDtoToUpdate));

            assertEquals("Product with id " + productDtoToUpdate.productId() + " does not exist", exception.getMessage());
        }
    }

    @Nested
    class deleteProductTests {
        @Test
        @DisplayName("deleteProduct should delete product when it exists")
        void testDeleteProduct() {
            Long productIdToDelete = 1L;

            ProductEntity existingProductEntity = new ProductEntity(
                    productIdToDelete,
                    "Dog Food",
                    "High quality dog food",
                    new java.math.BigDecimal("29.99"),
                    new java.math.BigDecimal("24.99"),
                    "dog_food.jpg",
                    "PetBrand",
                    2L);

            when(productRepository.findProductById(productIdToDelete)).thenReturn(Optional.of(existingProductEntity));

            assertDoesNotThrow(() -> productServiceImpl.deleteProduct(productIdToDelete));
        }

        @Test
        @DisplayName("deleteProduct should throw exception when product does not exist")
        void testDeleteProductNotFound() {
            Long productIdToDelete = 1L;

            when(productRepository.findProductById(productIdToDelete)).thenReturn(Optional.empty());

            RuntimeException exception = assertThrows(RuntimeException.class,
                    () -> productServiceImpl.deleteProduct(productIdToDelete));

            assertEquals("Product with id " + productIdToDelete + " does not exist", exception.getMessage());
        }

    }

}