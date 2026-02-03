package es.VetUp.tienda_back.a_presentation.controller.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.VetUp.tienda_back.a_presentation.controller.webModel.request.CartInsertRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.CartUpdateRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.response.CartDetailResponse;
import es.VetUp.tienda_back.b_domain.service.dto.CartDto;
import es.VetUp.tienda_back.b_domain.service.dto.UserDto;

import java.math.BigDecimal;

class CartPresentationMapperTest {

    @Test
    @DisplayName("Test map from CartDto to CartDetailResponse")
    void testFromCartDtoToCartDetailResponse() {
        UserDto userDto = new UserDto(1L, "john", null, null, null, null, null, null, null, null, null);
        CartDto cartDto = new CartDto(1L, 5, new BigDecimal("100.00"), userDto, null);

        CartDetailResponse response = CartPresentationMapper.getInstance()
                .fromCartDtoToCartDetailResponse(cartDto);

        assertEquals(cartDto.id(), response.id());
        assertEquals(cartDto.totalProducts(), response.totalProducts());
        assertEquals(cartDto.totalPrice(), response.totalPrice());
        assertNotNull(response.user());
        assertEquals(cartDto.user().id(), response.user().id());
    }

    @Test
    @DisplayName("Test map from CartInsertRequest to CartDto")
    void testFromCartInsertRequestToCartDto() {
        CartInsertRequest request = new CartInsertRequest(5, new BigDecimal("100.00"), 1L);

        CartDto dto = CartPresentationMapper.getInstance()
                .fromCartInsertRequestToCartDto(request);

        assertNull(dto.id());
        assertEquals(request.totalProducts(), dto.totalProducts());
        assertEquals(request.totalPrice(), dto.totalPrice());
        assertEquals(request.userId(), dto.user().id());
    }

    @Test
    @DisplayName("Test map from CartUpdateRequest to CartDto")
    void testFromCartUpdateRequestToCartDto() {
        CartUpdateRequest request = new CartUpdateRequest(1L, 7, new BigDecimal("150.00"), 1L);

        CartDto dto = CartPresentationMapper.getInstance()
                .fromCartUpdateRequestToCartDto(request);

        assertEquals(request.id(), dto.id());
        assertEquals(request.totalProducts(), dto.totalProducts());
        assertEquals(request.totalPrice(), dto.totalPrice());
        assertEquals(request.userId(), dto.user().id());
    }

    @Test
    @DisplayName("Test map null CartDto returns null")
    void testMapNullCartDtoReturnsNull() {
        assertNull(CartPresentationMapper.getInstance().fromCartDtoToCartDetailResponse(null));
    }

    @Test
    @DisplayName("Test map null request returns null")
    void testMapNullRequestReturnsNull() {
        assertNull(CartPresentationMapper.getInstance().fromCartInsertRequestToCartDto(null));
        assertNull(CartPresentationMapper.getInstance().fromCartUpdateRequestToCartDto(null));
    }

    @Test
    @DisplayName("Test mapper instance is singleton")
    void testMapperIsSingleton() {
        CartPresentationMapper instance1 = CartPresentationMapper.getInstance();
        CartPresentationMapper instance2 = CartPresentationMapper.getInstance();

        assertSame(instance1, instance2);
    }
}

