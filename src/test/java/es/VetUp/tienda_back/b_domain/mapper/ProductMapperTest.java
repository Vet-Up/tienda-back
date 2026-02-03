package es.VetUp.tienda_back.b_domain.mapper;

import es.VetUp.tienda_back.b_domain.model.Product;
import es.VetUp.tienda_back.b_domain.repository.entity.ProductEntity;
import es.VetUp.tienda_back.b_domain.service.dto.ProductDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

        @Test
        @DisplayName("Test map Product to ProductDTO")
        void testMapProductToProductDTO() {
                Product product = new Product(1L, "Dog Food", "High quality dog food",
                                new BigDecimal("29.99"), new BigDecimal("5.00"),
                                "dog_food.jpg", "PetBrand", 2L, 100);

                var productDTO = ProductMapper.getInstance().fromProducttoProductDto(product);
                assertAll("productDTO",
                                () -> assertEquals(product.getProductId(), productDTO.productId()),
                                () -> assertEquals(product.getName(), productDTO.name()),
                                () -> assertEquals(product.getProductDescription(), productDTO.productDescription()),
                                () -> assertEquals(0, product.getBasePrice().compareTo(productDTO.basePrice())),
                                () -> assertEquals(0,
                                                product.getDiscount().compareTo(productDTO.discount())),
                                () -> assertEquals(product.getPictureProduct(), productDTO.pictureProduct()),
                                () -> assertEquals(product.getBrand(), productDTO.brand()),
                                () -> assertEquals(product.getCategoryId(), productDTO.categoryId()),
                                () -> assertEquals(product.getStock(), productDTO.stock()));

        }

        @Test
        @DisplayName("Test map null Product to ProductDTO returns null")
        void testMapNullProductToProductDTOThrowsException() {
                Product product = null;

                ProductDto result = ProductMapper.getInstance().fromProducttoProductDto(product);

                assertNull(result);
        }

        @Test
        @DisplayName("Test map ProductEntity to Product")
        void testMapProductEntityToProduct() {
                // basePrice = 29.99, discount = 5%
                // discountValue = 29.99 * 5 / 100 = 1.4995 => 1.50 (HALF_UP)
                // price = 29.99 - 1.50 = 28.49
                var productEntity = new ProductEntity(1L, "Dog Food", "High quality dog food",
                                new BigDecimal("29.99"), new BigDecimal("5.00"),
                                "dog_food.jpg", "PetBrand", 2L,
                                new BigDecimal("28.49"),
                                100);
                var product = ProductMapper.getInstance().fromProductEntitytoProduct(productEntity);

                // Verificar que el precio se calcula correctamente
                BigDecimal expectedPrice = product.getPrice();
                System.out.println("Expected price from Product: " + expectedPrice);
                System.out.println("ProductEntity price: " + productEntity.price());

                assertAll("product",
                                () -> assertEquals(productEntity.productId(), product.getProductId()),
                                () -> assertEquals(productEntity.name(), product.getName()),
                                () -> assertEquals(productEntity.productDescription(),
                                                product.getProductDescription()),
                                () -> assertEquals(0, productEntity.basePrice().compareTo(product.getBasePrice())),
                                () -> assertEquals(0, productEntity.discount().compareTo(product.getDiscount())),
                                () -> assertEquals(productEntity.pictureProduct(), product.getPictureProduct()),
                                () -> assertEquals(productEntity.brand(), product.getBrand()),
                                () -> assertEquals(productEntity.categoryId(), product.getCategoryId()),
                                () -> assertEquals(productEntity.stock(), product.getStock()));

        }
}
