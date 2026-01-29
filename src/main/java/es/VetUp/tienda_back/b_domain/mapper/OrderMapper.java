package es.VetUp.tienda_back.b_domain.mapper;

import es.VetUp.tienda_back.b_domain.model.Order;
import es.VetUp.tienda_back.b_domain.repository.entity.OrderEntity;
import es.VetUp.tienda_back.b_domain.service.dto.OrderDto;

import java.util.stream.Collectors;

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
            UserMapper.getInstance().fromUserDtoToUser(orderDto.user()),
            orderDto.createdAt(),
            orderDto.orderAt(),
            orderDto.address(),
            orderDto.orderItems() == null ? null :
                orderDto.orderItems().stream()
                    .map(OrderItemMapper.getInstance()::fromOrderItemDtoToOrderItem)
                    .collect(Collectors.toList())
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
            UserMapper.getInstance().fromUserToUserDto(order.getUser()),
            order.getCreatedAt(),
            order.getOrderAt(),
            order.getAddress(),
            order.getOrderItems() == null ? null :
                order.getOrderItems().stream()
                    .map(OrderItemMapper.getInstance()::fromOrderItemToOrderItemDto)
                    .collect(Collectors.toList())
        );
    }

    public Order fromOrderEntityToOrder(OrderEntity orderEntity) {
        if (orderEntity == null) {
            return null;
        }
        return new Order(
            orderEntity.id(),
            orderEntity.totalProducts(),
            orderEntity.totalPrice(),
            orderEntity.state(),
            UserMapper.getInstance().fromUserEntityToUser(orderEntity.user()),
            orderEntity.createdAt(),
            orderEntity.orderAt(),
            orderEntity.address(),
            orderEntity.orderItems() == null ? null :
                orderEntity.orderItems().stream()
                    .map(OrderItemMapper.getInstance()::fromOrderItemEntityToOrderItem)
                    .collect(Collectors.toList())
        );
    }

    public OrderEntity fromOrderToOrderEntity(Order order) {
        if (order == null) {
            return null;
        }
        return new OrderEntity(
            order.getId(),
            order.getTotalProducts(),
            order.getTotalPrice(),
            order.getState(),
            UserMapper.getInstance().fromUserToUserEntity(order.getUser()),
            order.getCreatedAt(),
            order.getOrderAt(),
            order.getAddress(),
            order.getOrderItems() == null ? null :
                order.getOrderItems().stream()
                    .map(OrderItemMapper.getInstance()::fromOrderItemToOrderItemEntity)
                    .collect(Collectors.toList())
        );
    }
}
