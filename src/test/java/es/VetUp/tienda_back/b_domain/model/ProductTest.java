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
        int stock = 50;
        Long categoryId = 2L;


        Product product = assertDoesNotThrow(() -> new Product(productId, name, productDescription, price,
            discountedPrice, null, pictureProduct, brand, categoryId,stock));

        assertAll("product",
            () -> assertEquals(productId, product.getProductId()),
            () -> assertEquals(name, product.getName()),
            () -> assertEquals(productDescription, product.getProductDescription()),
            () -> assertEquals(0, price.compareTo(product.getBasePrice())),
            () -> assertEquals(0, discountedPrice.compareTo(product.getDiscountedPrice())),
            () -> assertEquals(pictureProduct, product.getPictureProduct()),
            () -> assertEquals(brand, product.getBrand()),
            () -> assertEquals(categoryId, product.getCategoryId()));
    }

}
