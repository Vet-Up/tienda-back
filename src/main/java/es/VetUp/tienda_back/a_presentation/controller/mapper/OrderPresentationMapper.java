package es.VetUp.tienda_back.a_presentation.controller.mapper;

import es.VetUp.tienda_back.a_presentation.controller.webModel.request.OrderInsertRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.response.OrderDetailResponse;
import es.VetUp.tienda_back.a_presentation.controller.webModel.response.UserDetailResponse;
import es.VetUp.tienda_back.b_domain.service.dto.OrderDto;
import es.VetUp.tienda_back.b_domain.service.dto.UserDto;
import es.VetUp.tienda_back.c_persistence.repository.mapper.OrderPersistenceMapper;

public class OrderPresentationMapper {
    private static OrderPresentationMapper INSTANCE;
    private OrderPresentationMapper() {
    }
    public static OrderPresentationMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrderPresentationMapper();
        }
        return INSTANCE;
    }



    public OrderDetailResponse fromOrderDtoToOrderDetailResponse(es.VetUp.tienda_back.b_domain.service.dto.OrderDto orderDto) {
        if (orderDto == null) {
            return null;
        }
        return new OrderDetailResponse(
                orderDto.id(),
                orderDto.totalProducts(),
                orderDto.totalPrice(),
                orderDto.state(),
                UserPresentationMapper.getInstance().fromUserDtoToUserDetailResponse(orderDto.user())
        );
    }

    public OrderDto fromOrderInsertRequestToOrderDto(OrderInsertRequest orderInsertRequest) {
        if (orderInsertRequest == null) {
            return null;
        }
        return new es.VetUp.tienda_back.b_domain.service.dto.OrderDto(
                null,
                orderInsertRequest.totalProducts(),
                orderInsertRequest.totalPrice(),
                orderInsertRequest.state(),
                mapUser(orderInsertRequest.userId())
        );
    }

    public OrderDto fromOrderUpdateRequestToOrderDto(es.VetUp.tienda_back.a_presentation.controller.webModel.request.OrderUpdateRequest orderUpdateRequest) {
        if (orderUpdateRequest == null) {
            return null;
        }
        return new es.VetUp.tienda_back.b_domain.service.dto.OrderDto(
                orderUpdateRequest.id(),
                orderUpdateRequest.totalProducts(),
                orderUpdateRequest.totalPrice(),
                orderUpdateRequest.state(),
                mapUser(orderUpdateRequest.userId())
        );
    }

    private UserDto mapUser(Long userId) {
        if (userId == null) {
            return null;
        }
        return new UserDto(userId, null, null, null, null, null, null, null, null, null, null);
    }

}
