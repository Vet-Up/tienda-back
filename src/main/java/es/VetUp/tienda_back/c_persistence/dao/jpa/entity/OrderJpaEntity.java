package es.VetUp.tienda_back.c_persistence.dao.jpa.entity;
import es.VetUp.tienda_back.b_domain.model.enums.OrderState;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private Long id;

    @Column(name = "total_products")
    private Integer total_products;

    @Column(name = "total_price")
    private BigDecimal total_price;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private OrderState status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserJpaEntity user;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "order_at")
    private LocalDateTime orderAt;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItemJpaEntity> orderItems = new ArrayList<>();

    public OrderJpaEntity() {
    }

    public OrderJpaEntity(Long id, Integer total_products, BigDecimal total_price, OrderState status,
                          UserJpaEntity user, LocalDateTime createdAt, LocalDateTime orderAt, String address) {
        this.id = id;
        this.total_products = total_products;
        this.total_price = total_price;
        this.status = status;
        this.user = user;
        this.createdAt = createdAt;
        this.orderAt = orderAt;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public Integer getTotal_products() {
        return total_products;
    }

    public void setTotal_products(Integer total_products) {
        this.total_products = total_products;
    }

    public BigDecimal getTotal_price() {
        return total_price;
    }

    public void setTotal_price(BigDecimal total_price) {
        this.total_price = total_price;
    }

    public OrderState getStatus() {
        return status;
    }

    public void setStatus(OrderState status) {
        this.status = status;
    }

    public UserJpaEntity getUser() {
        return user;
    }

    public void setUser(UserJpaEntity user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getOrderAt() {
        return orderAt;
    }

    public void setOrderAt(LocalDateTime orderAt) {
        this.orderAt = orderAt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderItemJpaEntity> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemJpaEntity> orderItems) {
        this.orderItems = orderItems;
    }
}




