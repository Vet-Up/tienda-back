package es.VetUp.tienda_back.b_domain.service.impl;

import es.VetUp.tienda_back.b_domain.exception.BusinessException;
import es.VetUp.tienda_back.b_domain.mapper.OrderItemMapper;
import es.VetUp.tienda_back.b_domain.mapper.OrderMapper;
import es.VetUp.tienda_back.b_domain.mapper.UserMapper;
import es.VetUp.tienda_back.b_domain.model.enums.OrderState;
import es.VetUp.tienda_back.b_domain.repository.CartItemRepository;
import es.VetUp.tienda_back.b_domain.repository.CartRepository;
import es.VetUp.tienda_back.b_domain.repository.OrderItemRepository;
import es.VetUp.tienda_back.b_domain.repository.OrderRepository;
import es.VetUp.tienda_back.b_domain.repository.UserRepository;
import es.VetUp.tienda_back.b_domain.repository.entity.CartEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.CartItemEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.OrderEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.OrderItemEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.UserEntity;
import es.VetUp.tienda_back.b_domain.service.OrderService;
import es.VetUp.tienda_back.b_domain.service.dto.OrderDto;
import es.VetUp.tienda_back.b_domain.service.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, CartRepository cartRepository,
                           CartItemRepository cartItemRepository, OrderItemRepository orderItemRepository,
                           UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
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
    public List<OrderDto> getOrdersByUserId(Long userId) {
        return orderRepository.getOrdersByUserId(userId)
                .stream()
                .map(OrderMapper.getInstance()::fromOrderEntityToOrder)
                .map(OrderMapper.getInstance()::fromOrderToOrderDto)
                .toList();
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

    @Override
    public OrderDto checkout(Long userId, String address) {
        if (address == null || address.isBlank()) {
            throw new BusinessException("Address is required for checkout");
        }

        UserEntity userEntity = userRepository.getClientById(userId)
                .orElseThrow(() -> new BusinessException("User with id " + userId + " not found"));

        CartEntity cartEntity = cartRepository.getCartByUserId(userId)
                .orElseThrow(() -> new BusinessException("Cart not found for user with id " + userId));

        List<CartItemEntity> cartItems = cartItemRepository.getCartItemsByCartId(cartEntity.id());
        if (cartItems.isEmpty()) {
            throw new BusinessException("Cart is empty, cannot create order");
        }

        OrderEntity orderEntity = new OrderEntity(
                null,
                cartEntity.totalProducts(),
                cartEntity.totalPrice(),
                OrderState.ORDER,
                userEntity,
                LocalDateTime.now(),
                LocalDateTime.now(),
                address,
                null
        );

        OrderEntity createdOrder = orderRepository.createOrder(orderEntity);

        List<OrderItemEntity> orderItems = cartItems.stream()
                .map(cartItem -> new OrderItemEntity(
                        null,
                        cartItem.quantity(),
                        createdOrder.id(),
                        cartItem.product()
                ))
                .collect(Collectors.toList());

        orderItems.forEach(orderItem -> orderItemRepository.createOrderItem(orderItem));

        cartItems.forEach(item -> cartItemRepository.deleteCartItem(item.id()));

        CartEntity emptyCart = new CartEntity(
                cartEntity.id(),
                0,
                java.math.BigDecimal.ZERO,
                userEntity,
                null
        );
        cartRepository.updateCart(emptyCart);

        OrderEntity finalOrder = orderRepository.getOrderById(createdOrder.id())
                .orElseThrow(() -> new BusinessException("Error retrieving created order"));

        return OrderMapper.getInstance().fromOrderToOrderDto(
                OrderMapper.getInstance().fromOrderEntityToOrder(finalOrder)
        );
    }
}
