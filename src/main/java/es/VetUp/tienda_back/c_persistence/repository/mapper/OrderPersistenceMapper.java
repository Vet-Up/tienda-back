package es.VetUp.tienda_back.c_persistence.repository.mapper;

import es.VetUp.tienda_back.b_domain.repository.entity.OrderEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.OrderJpaEntity;

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
            UserPersistenceMapper.getInstance().fromUserJpaEntityToUserEntity(orderJpaEntity.getUser())
        );
    }

    public OrderJpaEntity fromOrderEntityToOrderJpaEntity(OrderEntity orderEntity) {
        if (orderEntity == null) {
            return null;
        }
        return new OrderJpaEntity(
            orderEntity.id(),
            orderEntity.totalProducts(),
            orderEntity.totalPrice(),
            orderEntity.state(),
            UserPersistenceMapper.getInstance().fromUserEntityToUserJpaEntity(orderEntity.user())
        );
    }
}
