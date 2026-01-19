package es.VetUp.tienda_back.b_domain.mapper;

import es.VetUp.tienda_back.b_domain.exception.BusinessException;
import es.VetUp.tienda_back.b_domain.model.Product;
import es.VetUp.tienda_back.b_domain.repository.entity.ProductEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

        @Test
        @DisplayName("Test map Product to ProductDTO")
        void testMapProductToProductDTO() {
                Product product = new Product(1L, "Dog Food", "High quality dog food",
                                new BigDecimal("29.99"), new BigDecimal("24.99"),
                                null, "dog_food.jpg", "PetBrand", 2L);

                var productDTO = ProductMapper.getInstance().fromProducttoProductDto(product);
                assertAll("productDTO",
                                () -> assertEquals(product.getProductId(), productDTO.productId()),
                                () -> assertEquals(product.getName(), productDTO.name()),
                                () -> assertEquals(product.getProductDescription(), productDTO.productDescription()),
                                () -> assertEquals(0, product.getBasePrice().compareTo(productDTO.basePrice())),
                                () -> assertEquals(0,
                                                product.getDiscountedPrice().compareTo(productDTO.discountedPrice())),
                                () -> assertEquals(product.getPictureProduct(), productDTO.pictureProduct()),
                                () -> assertEquals(product.getBrand(), productDTO.brand()),
                                () -> assertEquals(product.getCategoryId(), productDTO.categoryId()));

        }

        @Test
        @DisplayName("Test map null Product to ProductDTO throws Exception")
        void testMapNullProductToProductDTOThrowsException() {
                Product product = null;

                assertThrows(NullPointerException.class,
                                () -> ProductMapper.getInstance().fromProducttoProductDto(product));

        }

        @Test
        @DisplayName("Test map ProductEntity to Product")
        void testMapProductEntityToProduct() {
                var productEntity = new ProductEntity(1L, "Dog Food", "High quality dog food",
                                new BigDecimal("29.99"), new BigDecimal("24.99"),
                                "dog_food.jpg", "PetBrand", 2L);
                var product = ProductMapper.getInstance().fromProductEntitytoProduct(productEntity);
                assertAll("product",
                                () -> assertEquals(productEntity.productId(), product.getProductId()),
                                () -> assertEquals(productEntity.name(), product.getName()),
                                () -> assertEquals(productEntity.productDescription(),
                                                product.getProductDescription()),
                                () -> assertEquals(0, productEntity.basePrice().compareTo(product.getBasePrice())),
                                () -> assertEquals(0,
                                                productEntity.discountedPrice()
                                                                .compareTo(product.getDiscountedPrice())),
                                () -> assertEquals(productEntity.pictureProduct(), product.getPictureProduct()),
                                () -> assertEquals(productEntity.brand(), product.getBrand()),
                                () -> assertEquals(productEntity.categoryId(), product.getCategoryId()));

        }
}
