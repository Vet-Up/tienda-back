package es.VetUp.tienda_back.c_persistence.repository.mapper;

import es.VetUp.tienda_back.b_domain.repository.entity.OrderEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.OrderJpaEntity;

import java.util.stream.Collectors;

public class OrderPersistenceMapper {
    private static OrderPersistenceMapper instance;
    private OrderPersistenceMapper() {
    }
    public static OrderPersistenceMapper getInstance() {
        if (instance == null) {
            instance = new OrderPersistenceMapper();
        }
        return instance;
    }

    public OrderEntity fromOrderJpaEntityToOrderEntity(OrderJpaEntity orderJpaEntity) {
        if (orderJpaEntity == null) {
            return null;
        }
        return new OrderEntity(
            orderJpaEntity.getId(),
            orderJpaEntity.getTotal_products(),
            orderJpaEntity.getTotal_price(),
            orderJpaEntity.getStatus(),
            UserPersistenceMapper.getInstance().fromUserJpaEntityToUserEntity(orderJpaEntity.getUser()),
            orderJpaEntity.getCreatedAt(),
            orderJpaEntity.getOrderAt(),
            orderJpaEntity.getAddress(),
            orderJpaEntity.getOrderItems() == null ? null :
                orderJpaEntity.getOrderItems().stream()
                    .map(OrderItemPersistenceMapper.getInstance()::fromOrderItemJpaEntityToOrderItemEntity)
                    .collect(Collectors.toList())
        );
    }

    public OrderJpaEntity fromOrderEntityToOrderJpaEntity(OrderEntity orderEntity) {
        if (orderEntity == null) {
            return null;
        }
        OrderJpaEntity orderJpaEntity = new OrderJpaEntity(
            orderEntity.id(),
            orderEntity.totalProducts(),
            orderEntity.totalPrice(),
            orderEntity.state(),
            UserPersistenceMapper.getInstance().fromUserEntityToUserJpaEntity(orderEntity.user()),
            orderEntity.createdAt(),
            orderEntity.orderAt(),
            orderEntity.address()
        );
        return orderJpaEntity;
    }
}
