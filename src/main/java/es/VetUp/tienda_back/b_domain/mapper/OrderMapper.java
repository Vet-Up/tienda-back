package es.VetUp.tienda_back.b_domain.mapper;

import es.VetUp.tienda_back.b_domain.model.Order;
import es.VetUp.tienda_back.b_domain.repository.entity.OrderEntity;
import es.VetUp.tienda_back.b_domain.service.dto.OrderDto;

public class OrderMapper {
    private static OrderMapper INSTANCE;
    private OrderMapper() {
    }

    public static OrderMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrderMapper();
        }
        return INSTANCE;
    }

    public Order fromOrderDtoToOrder(OrderDto orderDto) {
        if (orderDto == null) {
            return null;
        }
        return new Order(
            orderDto.id(),
            orderDto.totalProducts(),
            orderDto.totalPrice(),
            orderDto.state(),
            UserMapper.getInstance().fromUserDtoToUser(orderDto.user())
        );
    }

    public OrderDto fromOrderToOrderDto(Order order) {
        if (order == null) {
            return null;
        }
        return new OrderDto(
            order.getId(),
            order.getTotalProducts(),
            order.getTotalPrice(),
            order.getState(),
            UserMapper.getInstance().fromUserToUserDto(order.getUser())
        );
    }

    public Order fromOrderEntityToOrder(OrderEntity orderEntity) {
        if (orderEntity == null) {
            return null;
        }
        return new es.VetUp.tienda_back.b_domain.model.Order(
            orderEntity.id(),
            orderEntity.totalProducts(),
            orderEntity.totalPrice(),
            orderEntity.state(),
            UserMapper.getInstance().fromUserEntityToUser(orderEntity.user())
        );
    }

    public OrderEntity fromOrderToOrderEntity(Order order) {
        if (order == null) {
            return null;
        }
        return new es.VetUp.tienda_back.b_domain.repository.entity.OrderEntity(
            order.getId(),
            order.getTotalProducts(),
            order.getTotalPrice(),
            order.getState(),
            UserMapper.getInstance().fromUserToUserEntity(order.getUser())
        );
    }
}
