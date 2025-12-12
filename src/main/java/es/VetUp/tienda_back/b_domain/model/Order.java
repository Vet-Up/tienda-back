package es.VetUp.tienda_back.b_domain.model;

import es.VetUp.tienda_back.b_domain.model.enums.OrderState;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Order {
    private final Long id;
    private final Integer totalProducts;
    private final BigDecimal totalPrice;
    private final OrderState state;
    private final User user;

    public Order(Long id, Integer totalProducts, BigDecimal totalPrice, OrderState state, User user) {
        this.id = id;
        this.totalProducts = totalProducts;
        this.totalPrice = totalPrice;
        this.state = state;
        this.user = user;
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

}
