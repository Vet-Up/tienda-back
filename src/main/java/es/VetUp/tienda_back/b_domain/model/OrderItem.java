package es.VetUp.tienda_back.b_domain.model;

public class OrderItem {
    private final Long id;
    private final Integer quantity;
    private final Order order;
    private final Product product;

    public OrderItem(Long id, Integer quantity, Order order, Product product) {
        this.id = id;
        this.quantity = quantity;
        this.order = order;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Order getOrder() {
        return order;
    }

    public Product getProduct() {
        return product;
    }
}

