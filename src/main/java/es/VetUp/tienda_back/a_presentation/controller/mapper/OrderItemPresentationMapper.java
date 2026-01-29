package es.VetUp.tienda_back.a_presentation.controller.mapper;

import es.VetUp.tienda_back.a_presentation.controller.webModel.response.OrderItemDetailResponse;
import es.VetUp.tienda_back.b_domain.service.dto.OrderItemDto;

public class OrderItemPresentationMapper {
    private static OrderItemPresentationMapper INSTANCE;

    private OrderItemPresentationMapper() {
    }

    public static OrderItemPresentationMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrderItemPresentationMapper();
        }
        return INSTANCE;
    }

    public OrderItemDetailResponse fromOrderItemDtoToOrderItemDetailResponse(OrderItemDto orderItemDto) {
        if (orderItemDto == null) {
            return null;
        }
        return new OrderItemDetailResponse(
                orderItemDto.id(),
                orderItemDto.quantity(),
                ProductPresentationMapper.getInstance().fromProductDtoToToProductDetailResponse(orderItemDto.product())
        );
    }
}

