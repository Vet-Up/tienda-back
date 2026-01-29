package es.VetUp.tienda_back.b_domain.model;

public class CartItem {
    private final Long id;
    private final Integer quantity;
    private final Cart cart;
    private final Product product;

    public CartItem(Long id, Integer quantity, Cart cart, Product product) {
        this.id = id;
        this.quantity = quantity;
        this.cart = cart;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Cart getCart() {
        return cart;
    }

    public Product getProduct() {
        return product;
    }
}

