package es.VetUp.tienda_back.a_presentation.controller.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.VetUp.tienda_back.a_presentation.controller.webModel.response.OrderItemDetailResponse;
import es.VetUp.tienda_back.b_domain.service.dto.OrderItemDto;
import es.VetUp.tienda_back.b_domain.service.dto.ProductDto;

class OrderItemPresentationMapperTest {

    @Test
    @DisplayName("Test map from OrderItemDto to OrderItemDetailResponse")
    void testFromOrderItemDtoToOrderItemDetailResponse() {
        ProductDto productDto = new ProductDto(
                1L,
                "Producto 1",
                "Descripción producto 1",
                new BigDecimal("19.99"),
                new BigDecimal("5.00"),
                new BigDecimal("15.99"),
                "imagen1.jpg",
                "Marca1",
                1L,
                100,
                null,
                null);

        OrderItemDto orderItemDto = new OrderItemDto(
                1L,
                2,
                100L,
                productDto);

        OrderItemDetailResponse response = OrderItemPresentationMapper.getInstance()
                .fromOrderItemDtoToOrderItemDetailResponse(orderItemDto);

        assertEquals(orderItemDto.id(), response.id());
        assertEquals(orderItemDto.quantity(), response.quantity());
        assertEquals(productDto.productId(), response.product().productId());
    }
}
