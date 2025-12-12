package es.VetUp.tienda_back.b_domain.service.impl;

import es.VetUp.tienda_back.b_domain.exception.BusinessException;
import es.VetUp.tienda_back.b_domain.mapper.OrderMapper;
import es.VetUp.tienda_back.b_domain.model.enums.OrderState;
import es.VetUp.tienda_back.b_domain.repository.OrderRepository;
import es.VetUp.tienda_back.b_domain.repository.UserRepository;
import es.VetUp.tienda_back.b_domain.repository.entity.OrderEntity;
import es.VetUp.tienda_back.b_domain.service.OrderService;
import es.VetUp.tienda_back.b_domain.service.dto.OrderDto;

import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    @Override
    public List<OrderDto> getAllOrders() {
        return orderRepository.getAllOrders()
                .stream()
                .map(OrderMapper.getInstance()::fromOrderEntityToOrder)
                .map(OrderMapper.getInstance()::fromOrderToOrderDto)
                .toList();
    }

    @Override
    public Optional<OrderDto> getOrderById(Long id) {
        return orderRepository.getOrderById(id)
                .map(OrderMapper.getInstance()::fromOrderEntityToOrder)
                .map(OrderMapper.getInstance()::fromOrderToOrderDto);
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {

        if (orderDto.state() == OrderState.CART) {
            Long userId = orderDto.user().id();
            Optional<OrderEntity> existingCart = orderRepository.findCartByUserId(userId);
                if (existingCart.isPresent()) {
                    throw new BusinessException("No es posible crear otro order de tipo carrito ya que el usuario ya tiene uno, id de usuario: " + userId);
                }
        }

        OrderEntity orderEntity = OrderMapper.getInstance().fromOrderToOrderEntity(
                OrderMapper.getInstance().fromOrderDtoToOrder(orderDto)
        );
        OrderEntity createdOrderEntity = orderRepository.createOrder(orderEntity);
        return OrderMapper.getInstance().fromOrderToOrderDto(
                OrderMapper.getInstance().fromOrderEntityToOrder(createdOrderEntity)
        );
    }

    @Override
    public OrderDto updateOrder(OrderDto orderDto) {
        orderRepository.getOrderById(orderDto.id())
                .orElseThrow(() -> new BusinessException("Order with id " + orderDto.id() + " does not exist"));
        OrderEntity orderEntity = OrderMapper.getInstance().fromOrderToOrderEntity(OrderMapper.getInstance().fromOrderDtoToOrder(orderDto));
        OrderEntity updatedOrderEntity = orderRepository.updateOrder(orderEntity);
        return OrderMapper.getInstance().fromOrderToOrderDto(
                OrderMapper.getInstance().fromOrderEntityToOrder(updatedOrderEntity)
        );
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.getOrderById(id)
                .orElseThrow(() -> new BusinessException("Order with id " + id + " does not exist"));
        orderRepository.deleteOrder(id);
    }
}
