package es.VetUp.tienda_back.b_domain.model;

import es.VetUp.tienda_back.b_domain.model.enums.OrderState;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private final Long id;
    private final Integer totalProducts;
    private final BigDecimal totalPrice;
    private final OrderState state;
    private final User user;
    private final LocalDateTime createdAt;
    private final LocalDateTime orderAt;
    private final String address;
    private final List<OrderItem> orderItems;

    public Order(Long id, Integer totalProducts, BigDecimal totalPrice, OrderState state, User user,
                 LocalDateTime createdAt, LocalDateTime orderAt, String address, List<OrderItem> orderItems) {
        this.id = id;
        this.totalProducts = totalProducts;
        this.totalPrice = totalPrice;
        this.state = state;
        this.user = user;
        this.createdAt = createdAt;
        this.orderAt = orderAt;
        this.address = address;
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }

    public Integer getTotalProducts() {
        return totalProducts;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public OrderState getState() {
        return state;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getOrderAt() {
        return orderAt;
    }

    public String getAddress() {
        return address;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
