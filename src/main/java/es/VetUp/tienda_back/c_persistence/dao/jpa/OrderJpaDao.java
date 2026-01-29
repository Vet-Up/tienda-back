package es.VetUp.tienda_back.c_persistence.dao.jpa;

import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.OrderJpaEntity;

import java.util.List;
import java.util.Optional;

public interface OrderJpaDao {
    List<OrderJpaEntity> getAllOrders();
    Optional<OrderJpaEntity> getOrderById(Long id);
    List<OrderJpaEntity> getOrdersByUserId(Long userId);
    Optional<OrderJpaEntity> findCartByUserId(Long userId);
    OrderJpaEntity createOrder(OrderJpaEntity orderJpaEntity);
    OrderJpaEntity updateOrder(OrderJpaEntity orderJpaEntity);
    void deleteOrder(Long id);
}
