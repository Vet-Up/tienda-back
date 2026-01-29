package es.VetUp.tienda_back.c_persistence.repository;

import es.VetUp.tienda_back.b_domain.repository.OrderRepository;
import es.VetUp.tienda_back.b_domain.repository.entity.OrderEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.OrderJpaDao;
import es.VetUp.tienda_back.c_persistence.repository.mapper.OrderPersistenceMapper;

import java.util.List;
import java.util.Optional;

public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaDao orderJpaDao;
    public OrderRepositoryImpl(OrderJpaDao orderJpaDao) {
        this.orderJpaDao = orderJpaDao;
    }

    @Override
    public List<OrderEntity> getAllOrders() {
        return orderJpaDao.getAllOrders().stream().map(OrderPersistenceMapper.getInstance()::fromOrderJpaEntityToOrderEntity).toList();
    }

    @Override
    public Optional<OrderEntity> getOrderById(Long id) {
        return orderJpaDao.getOrderById(id).map(OrderPersistenceMapper.getInstance()::fromOrderJpaEntityToOrderEntity);
    }

    @Override
    public List<OrderEntity> getOrdersByUserId(Long userId) {
        return orderJpaDao.getOrdersByUserId(userId).stream().map(OrderPersistenceMapper.getInstance()::fromOrderJpaEntityToOrderEntity).toList();
    }

    @Override
    public Optional<OrderEntity> findCartByUserId(Long userId) {
        return orderJpaDao.findCartByUserId(userId).map(OrderPersistenceMapper.getInstance()::fromOrderJpaEntityToOrderEntity);
    }

    @Override
    public OrderEntity createOrder(OrderEntity orderEntity) {
        return OrderPersistenceMapper.getInstance().fromOrderJpaEntityToOrderEntity(
            orderJpaDao.createOrder(
                OrderPersistenceMapper.getInstance().fromOrderEntityToOrderJpaEntity(orderEntity)
            )
        );
    }

    @Override
    public OrderEntity updateOrder(OrderEntity orderEntity) {
        return OrderPersistenceMapper.getInstance().fromOrderJpaEntityToOrderEntity(
            orderJpaDao.updateOrder(
                OrderPersistenceMapper.getInstance().fromOrderEntityToOrderJpaEntity(orderEntity)
            )
        );
    }

    @Override
    public void deleteOrder(Long id) {
        orderJpaDao.deleteOrder(id);
    }
}
