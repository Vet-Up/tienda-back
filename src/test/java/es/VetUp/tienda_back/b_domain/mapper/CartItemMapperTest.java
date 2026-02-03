package es.VetUp.tienda_back.b_domain.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.VetUp.tienda_back.b_domain.model.Cart;
import es.VetUp.tienda_back.b_domain.model.CartItem;
import es.VetUp.tienda_back.b_domain.model.Product;
import es.VetUp.tienda_back.b_domain.repository.entity.CartItemEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.ProductEntity;
import es.VetUp.tienda_back.b_domain.service.dto.CartItemDto;
import es.VetUp.tienda_back.b_domain.service.dto.ProductDto;

class CartItemMapperTest {

    @Test
    @DisplayName("Test map CartItem to CartItemDto")
    void testMapCartItemToCartItemDto() {
        // Arrange
        Product product = new Product(1L, "Dog Food", "High quality dog food",
                new BigDecimal("29.99"), new BigDecimal("5.00"),
                "dog_food.jpg", "PetBrand", 2L, 100);
        Cart cart = new Cart(1L, 5, new BigDecimal("149.95"), null, null);
        CartItem cartItem = new CartItem(1L, 5, cart, product);

        // Act
        CartItemDto result = CartItemMapper.getInstance().fromCartItemToCartItemDto(cartItem);

        // Assert
        assertAll("cartItemDto",
                () -> assertNotNull(result),
                () -> assertEquals(cartItem.getId(), result.id()),
                () -> assertEquals(cartItem.getQuantity(), result.quantity()),
                () -> assertEquals(cart.getId(), result.cartId()),
                () -> assertNotNull(result.product()),
                () -> assertEquals(product.getProductId(), result.product().productId()));
    }

    @Test
    @DisplayName("Test map null CartItem to CartItemDto returns null")
    void testMapNullCartItemToCartItemDto() {
        CartItemDto result = CartItemMapper.getInstance().fromCartItemToCartItemDto(null);
        assertNull(result);
    }

    @Test
    @DisplayName("Test map CartItemDto to CartItem")
    void testMapCartItemDtoToCartItem() {
        // Arrange
        ProductDto productDto = new ProductDto(1L, "Dog Food", "High quality dog food",
                new BigDecimal("29.99"), new BigDecimal("5.00"),
                new BigDecimal("28.49"), "dog_food.jpg", "PetBrand", 2L, 100, 4.5, 10);
        CartItemDto dto = new CartItemDto(1L, 3, 1L, productDto);

        // Act
        CartItem result = CartItemMapper.getInstance().fromCartItemDtoToCartItem(dto);

        // Assert
        assertAll("cartItem",
                () -> assertNotNull(result),
                () -> assertEquals(dto.id(), result.getId()),
                () -> assertEquals(dto.quantity(), result.getQuantity()),
                () -> assertNull(result.getCart()),
                () -> assertNotNull(result.getProduct()),
                () -> assertEquals(productDto.productId(), result.getProduct().getProductId()));
    }

    @Test
    @DisplayName("Test map null CartItemDto to CartItem returns null")
    void testMapNullCartItemDtoToCartItem() {
        CartItem result = CartItemMapper.getInstance().fromCartItemDtoToCartItem(null);
        assertNull(result);
    }

    @Test
    @DisplayName("Test map CartItemEntity to CartItem")
    void testMapCartItemEntityToCartItem() {
        // Arrange
        ProductEntity productEntity = new ProductEntity(1L, "Dog Food", "High quality dog food",
                new BigDecimal("29.99"), new BigDecimal("5.00"),
                "dog_food.jpg", "PetBrand", 2L,
                new BigDecimal("28.49"), 100);
        CartItemEntity entity = new CartItemEntity(1L, 4, 1L, productEntity);

        // Act
        CartItem result = CartItemMapper.getInstance().fromCartItemEntityToCartItem(entity);

        // Assert
        assertAll("cartItem",
                () -> assertNotNull(result),
                () -> assertEquals(entity.id(), result.getId()),
                () -> assertEquals(entity.quantity(), result.getQuantity()),
                () -> assertNull(result.getCart()),
                () -> assertNotNull(result.getProduct()),
                () -> assertEquals(productEntity.productId(), result.getProduct().getProductId()));
    }

    @Test
    @DisplayName("Test mapper instance is singleton")
    void testMapperIsSingleton() {
        CartItemMapper instance1 = CartItemMapper.getInstance();
        CartItemMapper instance2 = CartItemMapper.getInstance();

        assertSame(instance1, instance2);
    }
}

