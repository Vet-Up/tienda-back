package es.VetUp.tienda_back.c_persistence.repository;

import es.VetUp.tienda_back.b_domain.repository.OrderItemRepository;
import es.VetUp.tienda_back.b_domain.repository.entity.OrderItemEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.OrderItemJpaDao;
import es.VetUp.tienda_back.c_persistence.repository.mapper.OrderItemPersistenceMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderItemRepositoryImpl implements OrderItemRepository {

    private final OrderItemJpaDao orderItemJpaDao;

    public OrderItemRepositoryImpl(OrderItemJpaDao orderItemJpaDao) {
        this.orderItemJpaDao = orderItemJpaDao;
    }

    @Override
    public List<OrderItemEntity> getAllOrderItems() {
        return orderItemJpaDao.findAll().stream()
                .map(OrderItemPersistenceMapper.getInstance()::fromOrderItemJpaEntityToOrderItemEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderItemEntity> getOrderItemById(Long id) {
        return orderItemJpaDao.findById(id)
                .map(OrderItemPersistenceMapper.getInstance()::fromOrderItemJpaEntityToOrderItemEntity);
    }

    @Override
    public List<OrderItemEntity> getOrderItemsByOrderId(Long orderId) {
        return orderItemJpaDao.findByOrderId(orderId).stream()
                .map(OrderItemPersistenceMapper.getInstance()::fromOrderItemJpaEntityToOrderItemEntity)
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemEntity createOrderItem(OrderItemEntity orderItemEntity) {
        return OrderItemPersistenceMapper.getInstance().fromOrderItemJpaEntityToOrderItemEntity(
                orderItemJpaDao.save(
                        OrderItemPersistenceMapper.getInstance().fromOrderItemEntityToOrderItemJpaEntity(orderItemEntity)
                )
        );
    }

    @Override
    public OrderItemEntity updateOrderItem(OrderItemEntity orderItemEntity) {
        return OrderItemPersistenceMapper.getInstance().fromOrderItemJpaEntityToOrderItemEntity(
                orderItemJpaDao.save(
                        OrderItemPersistenceMapper.getInstance().fromOrderItemEntityToOrderItemJpaEntity(orderItemEntity)
                )
        );
    }

    @Override
    public void deleteOrderItem(Long id) {
        orderItemJpaDao.deleteById(id);
    }
}

