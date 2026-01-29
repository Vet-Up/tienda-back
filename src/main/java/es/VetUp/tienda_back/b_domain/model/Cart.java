package es.VetUp.tienda_back.b_domain.model;

import java.math.BigDecimal;
import java.util.List;

public class Cart {
    private final Long id;
    private final Integer totalProducts;
    private final BigDecimal totalPrice;
    private final User user;
    private final List<CartItem> cartItems;

    public Cart(Long id, Integer totalProducts, BigDecimal totalPrice, User user, List<CartItem> cartItems) {
        this.id = id;
        this.totalProducts = totalProducts;
        this.totalPrice = totalPrice;
        this.user = user;
        this.cartItems = cartItems;
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

    public User getUser() {
        return user;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }
}

