package es.VetUp.tienda_back.b_domain.repository;

import es.VetUp.tienda_back.b_domain.repository.entity.OrderItemEntity;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository {
    List<OrderItemEntity> getAllOrderItems();
    Optional<OrderItemEntity> getOrderItemById(Long id);
    List<OrderItemEntity> getOrderItemsByOrderId(Long orderId);
    OrderItemEntity createOrderItem(OrderItemEntity orderItemEntity);
    OrderItemEntity updateOrderItem(OrderItemEntity orderItemEntity);
    void deleteOrderItem(Long id);
}

