package es.VetUp.tienda_back.c_persistence.dao.jpa.entity;
import es.VetUp.tienda_back.b_domain.model.enums.OrderState;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "orders")
public class OrderJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private Long id;
    private Integer total_products;
    private BigDecimal total_price;
    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private OrderState status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserJpaEntity user;

    public OrderJpaEntity() {
    }

    public OrderJpaEntity(Long id, Integer total_products, BigDecimal total_price, OrderState status, UserJpaEntity user) {
        this.id = id;
        this.total_products = total_products;
        this.total_price = total_price;
        this.status = status;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public Integer getTotal_products() {
        return total_products;
    }

    public BigDecimal getTotal_price() {
        return total_price;
    }

    public OrderState getStatus() {
        return status;
    }

    public UserJpaEntity getUser() {
        return user;
    }
}




