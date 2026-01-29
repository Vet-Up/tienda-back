package es.VetUp.tienda_back.c_persistence.repository.mapper;

import es.VetUp.tienda_back.b_domain.repository.entity.OrderItemEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.OrderItemJpaEntity;

public class OrderItemPersistenceMapper {
    private static OrderItemPersistenceMapper instance;

    private OrderItemPersistenceMapper() {
    }

    public static OrderItemPersistenceMapper getInstance() {
        if (instance == null) {
            instance = new OrderItemPersistenceMapper();
        }
        return instance;
    }

    public es.VetUp.tienda_back.b_domain.repository.entity.OrderItemEntity fromOrderItemJpaEntityToOrderItemEntity(es.VetUp.tienda_back.c_persistence.dao.jpa.entity.OrderItemJpaEntity orderItemJpaEntity) {
        if (orderItemJpaEntity == null) {
            return null;
        }
        return new es.VetUp.tienda_back.b_domain.repository.entity.OrderItemEntity(
            orderItemJpaEntity.getId(),
            orderItemJpaEntity.getQuantity(),
            orderItemJpaEntity.getOrder() != null ? orderItemJpaEntity.getOrder().getId() : null,
            ProductPersistenceMapper.getInstance().fromProductJpaEntitytoToProductEntity(orderItemJpaEntity.getProduct())
        );
    }

    public OrderItemJpaEntity fromOrderItemEntityToOrderItemJpaEntity(OrderItemEntity orderItemEntity) {
        if (orderItemEntity == null) {
            return null;
        }
        es.VetUp.tienda_back.c_persistence.dao.jpa.entity.OrderJpaEntity orderJpa = null;
        if (orderItemEntity.orderId() != null) {
            // use constructor that allows setting id
            orderJpa = new es.VetUp.tienda_back.c_persistence.dao.jpa.entity.OrderJpaEntity(
                    orderItemEntity.orderId(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
        }
        return new OrderItemJpaEntity(
            orderItemEntity.id(),
            orderItemEntity.quantity(),
            orderJpa,
            ProductPersistenceMapper.getInstance().fromProductEntitytoToProductJpaEntity(orderItemEntity.product())
        );
    }

}
