package es.VetUp.tienda_back.c_persistence.dao.jpa.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
public class CartJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cart")
    private Long id;

    @Column(name = "total_products")
    private Integer totalProducts;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserJpaEntity user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CartItemJpaEntity> cartItems = new ArrayList<>();

    public CartJpaEntity() {
    }

    public CartJpaEntity(Long id, Integer totalProducts, BigDecimal totalPrice, UserJpaEntity user) {
        this.id = id;
        this.totalProducts = totalProducts;
        this.totalPrice = totalPrice;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(Integer totalProducts) {
        this.totalProducts = totalProducts;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public UserJpaEntity getUser() {
        return user;
    }

    public void setUser(UserJpaEntity user) {
        this.user = user;
    }

    public List<CartItemJpaEntity> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemJpaEntity> cartItems) {
        this.cartItems = cartItems;
    }
}

