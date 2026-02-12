package es.VetUp.tienda_back.a_presentation.controller;

import es.VetUp.tienda_back.a_presentation.controller.mapper.OrderPresentationMapper;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.CheckoutRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.OrderInsertRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.OrderUpdateRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.response.OrderDetailResponse;
import es.VetUp.tienda_back.b_domain.service.JwtService;
import es.VetUp.tienda_back.b_domain.service.OrderService;
import es.VetUp.tienda_back.b_domain.service.dto.OrderDto;
import es.VetUp.tienda_back.b_domain.service.dto.UserDto;
import es.VetUp.tienda_back.config.annotation.RequireAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;
    private final JwtService jwtService;

    public OrderController(OrderService orderService, JwtService jwtService) {
        this.orderService = orderService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDetailResponse>> findAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders();
        List<OrderDetailResponse> orderResponses = orders.stream().map(OrderPresentationMapper.getInstance()::fromOrderDtoToOrderDetailResponse).toList();
        return new ResponseEntity<>(orderResponses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailResponse> getOrderById(@PathVariable Long id) {
        OrderDto orderDto = orderService.getOrderById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        OrderDetailResponse orderDetailResponse =
                OrderPresentationMapper.getInstance().fromOrderDtoToOrderDetailResponse(orderDto);
        return new ResponseEntity<>(orderDetailResponse, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDetailResponse>> getOrdersByUserId(@PathVariable Long userId) {
        List<OrderDto> orders = orderService.getOrdersByUserId(userId);
        List<OrderDetailResponse> orderResponses = orders.stream()
                .map(OrderPresentationMapper.getInstance()::fromOrderDtoToOrderDetailResponse)
                .toList();
        return new ResponseEntity<>(orderResponses, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/product/{productId}/purchased")
    public ResponseEntity<Boolean> hasUserPurchasedProduct(
            @PathVariable Long userId,
            @PathVariable Long productId) {
        boolean hasPurchased = orderService.hasUserPurchasedProduct(userId, productId);
        return new ResponseEntity<>(hasPurchased, HttpStatus.OK);
    }

    @RequireAdmin
    @PostMapping
    public ResponseEntity<OrderDetailResponse> createOrder(@RequestBody OrderInsertRequest orderInsertRequest) {
        OrderDto orderDto = OrderPresentationMapper.getInstance().fromOrderInsertRequestToOrderDto(orderInsertRequest);
        OrderDto createdOrder = orderService.createOrder(orderDto);
        OrderDetailResponse response = OrderPresentationMapper.getInstance().fromOrderDtoToOrderDetailResponse(createdOrder);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequireAdmin
    @PutMapping("/{id}")
    public ResponseEntity<OrderDetailResponse> updateOrder(@PathVariable("id") Long id, @RequestBody OrderUpdateRequest orderUpdateRequest) {
        if (!id.equals(orderUpdateRequest.id())) {
            throw new IllegalArgumentException("ID in path and request body must match");
        }
        OrderDto orderDto = OrderPresentationMapper.getInstance().fromOrderUpdateRequestToOrderDto(orderUpdateRequest);
        OrderDto updatedOrder = orderService.updateOrder(orderDto);
        OrderDetailResponse response = OrderPresentationMapper.getInstance().fromOrderDtoToOrderDetailResponse(updatedOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequireAdmin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderDetailResponse> checkout(
            @RequestAttribute(value = "userId", required = false) Long userId,
            @RequestBody CheckoutRequest request) {

        log.info("OrderController.checkout - Starting checkout");
        log.info("OrderController.checkout - userId from RequestAttribute: {}", userId);
        log.info("OrderController.checkout - Address: {}", request.address());
        log.info("OrderController.checkout - CardPaymentRequest present: {}", request.cardPaymentRequest() != null);

        if (userId == null) {
            log.error("OrderController.checkout - userId is null! RequestAttribute not set by filter");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            OrderDto order = orderService.checkout(userId, request.address(), request.cardPaymentRequest());
            log.info("OrderController.checkout - Order created successfully with ID: {}", order.id());

            OrderDetailResponse response = OrderPresentationMapper.getInstance()
                    .fromOrderDtoToOrderDetailResponse(order);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("OrderController.checkout - Error during checkout: {}", e.getMessage(), e);
            throw e;
        }
    }
}
