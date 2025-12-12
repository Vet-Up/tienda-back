package es.VetUp.tienda_back.b_domain.repository;

import es.VetUp.tienda_back.b_domain.repository.entity.OrderEntity;
import es.VetUp.tienda_back.b_domain.service.dto.OrderDto;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    List<OrderEntity> getAllOrders();
    Optional<OrderEntity> getOrderById(Long id);
    Optional<OrderEntity> findCartByUserId(Long userId);
    OrderEntity createOrder(OrderEntity orderEntity);
    OrderEntity updateOrder(OrderEntity orderEntity);
    void deleteOrder(Long id);
}
