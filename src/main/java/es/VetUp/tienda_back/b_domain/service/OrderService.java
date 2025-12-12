package es.VetUp.tienda_back.b_domain.service;

import es.VetUp.tienda_back.b_domain.service.dto.OrderDto;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<OrderDto> getAllOrders();
    Optional<OrderDto> getOrderById(Long id);
    OrderDto createOrder(OrderDto orderDto);
    OrderDto updateOrder(OrderDto orderDto);
    void deleteOrder(Long id);
}
