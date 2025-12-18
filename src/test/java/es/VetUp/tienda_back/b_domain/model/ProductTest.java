package es.VetUp.tienda_back.b_domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    @Test
    @DisplayName("Test Product Creation")
    void testProductCreation() {
        Long productId = 1L;
        String name = "Dog Food";
        String productDescription = "High quality dog food";
        BigDecimal price = new BigDecimal("29.99");
        BigDecimal discountedPrice = new BigDecimal("24.99");
        String pictureProduct = "dog_food.jpg";
        String brand = "PetBrand";
        Long categoryId = 2L;

        Product product = assertDoesNotThrow(() -> new Product(productId, name, productDescription, price,
                discountedPrice, pictureProduct, brand, categoryId));

        assertAll("product",
                () -> assertEquals(productId, product.getProduct_id()),
                () -> assertEquals(name, product.getName()),
                () -> assertEquals(productDescription, product.getProduct_description()),
                () -> assertEquals(0, price.compareTo(product.getPrice())),
                () -> assertEquals(0, discountedPrice.compareTo(product.getDiscountedPrice())),
                () -> assertEquals(pictureProduct, product.getPictureProduct()),
                () -> assertEquals(brand, product.getBrand()),
                () -> assertEquals(categoryId, product.getCategoryId()));
    }

}
