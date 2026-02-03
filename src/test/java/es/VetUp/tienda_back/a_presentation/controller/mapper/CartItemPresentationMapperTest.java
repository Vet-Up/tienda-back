package es.VetUp.tienda_back.a_presentation.controller.mapper;

import es.VetUp.tienda_back.b_domain.service.dto.CartItemDto;
import es.VetUp.tienda_back.b_domain.service.dto.ProductDto;
import es.VetUp.tienda_back.a_presentation.controller.webModel.response.CartItemDetailResponse;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.CartItemUpdateRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.CartItemInsertRequest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class CartItemPresentationMapperTest {

    @Test
    @DisplayName("Test mapper instance is singleton")
    void testMapperIsSingleton() {
        CartItemPresentationMapper instance1 = CartItemPresentationMapper.getInstance();
        CartItemPresentationMapper instance2 = CartItemPresentationMapper.getInstance();

        assertSame(instance1, instance2);
    }

    @Test
    @DisplayName("Test map null request returns null")
    void testMapNullRequestReturnsNull() {
        assertNull(CartItemPresentationMapper.getInstance().fromCartItemInsertRequestToCartItemDto(null));
        assertNull(CartItemPresentationMapper.getInstance().fromCartItemUpdateRequestToCartItemDto(null));
    }

    @Test
    @DisplayName("Test map null CartItemDto returns null")
    void testMapNullCartItemDtoReturnsNull() {
        assertNull(CartItemPresentationMapper.getInstance().fromCartItemDtoToCartItemDetailResponse(null));
    }

    @Test
    @DisplayName("Test map from CartItemUpdateRequest to CartItemDto")
    void testFromCartItemUpdateRequestToCartItemDto() {
        CartItemUpdateRequest request = new CartItemUpdateRequest(
                1L,
                3);

        CartItemDto dto = CartItemPresentationMapper.getInstance()
                .fromCartItemUpdateRequestToCartItemDto(request);

        assertAll("dto",
                () -> assertEquals(request.id(), dto.id()),
                () -> assertEquals(request.quantity(), dto.quantity()),
                () -> assertNull(dto.cartId()), // Update request doesn't set cartId
                () -> assertNull(dto.product())); // Update request doesn't set product
    }

    @Test
    @DisplayName("Test map from CartItemInsertRequest to CartItemDto")
    void testFromCartItemInsertRequestToCartItemDto() {
        CartItemInsertRequest request = new CartItemInsertRequest(
                2,
                1L,
                10L);

        CartItemDto dto = CartItemPresentationMapper.getInstance()
                .fromCartItemInsertRequestToCartItemDto(request);

        assertAll("dto",
                () -> assertNull(dto.id()), // Insert request doesn't have id
                () -> assertEquals(request.cartId(), dto.cartId()),
                () -> assertEquals(request.quantity(), dto.quantity()),
                () -> assertNull(dto.product())); // Product is set to null (to be fetched by service)
    }

    @Test
    @DisplayName("Test map from CartItemDto to CartItemDetailResponse")
    void testFromCartItemDtoToCartItemDetailResponse() {
        // Create a mock ProductDto
        ProductDto productDto = new ProductDto(
                10L,
                "Test Product",
                "Test Description",
                java.math.BigDecimal.valueOf(100.0),
                java.math.BigDecimal.valueOf(10.0),
                java.math.BigDecimal.valueOf(90.0),
                "https://example.com/image.jpg",
                "Test Brand",
                1L,
                10,
                4.5,
                5
        );

        CartItemDto cartItemDto = new CartItemDto(
                1L,
                2,
                1L,
                productDto);

        CartItemDetailResponse response = CartItemPresentationMapper.getInstance()
                .fromCartItemDtoToCartItemDetailResponse(cartItemDto);

        assertAll("response",
                () -> assertEquals(cartItemDto.id(), response.id()),
                () -> assertEquals(cartItemDto.quantity(), response.quantity()),
                () -> assertNotNull(response.product())); // Product is mapped
    }
}

