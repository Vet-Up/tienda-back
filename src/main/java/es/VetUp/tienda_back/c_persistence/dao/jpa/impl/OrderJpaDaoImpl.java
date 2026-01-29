package es.VetUp.tienda_back.c_persistence.dao.jpa.impl;

import es.VetUp.tienda_back.b_domain.model.enums.OrderState;
import es.VetUp.tienda_back.b_domain.repository.entity.OrderEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.OrderJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.OrderJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class OrderJpaDaoImpl implements OrderJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<OrderJpaEntity> getAllOrders() {
        return entityManager.createQuery(
                "SELECT DISTINCT o FROM OrderJpaEntity o LEFT JOIN FETCH o.orderItems",
                OrderJpaEntity.class)
                .getResultList();
    }

    @Override
    public Optional<OrderJpaEntity> getOrderById(Long id) {
        String jpql = "SELECT o FROM OrderJpaEntity o LEFT JOIN FETCH o.orderItems WHERE o.id = :id";
        List<OrderJpaEntity> results = entityManager.createQuery(jpql, OrderJpaEntity.class)
                .setParameter("id", id)
                .getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public List<OrderJpaEntity> getOrdersByUserId(Long userId) {
        String jpql = "SELECT DISTINCT o FROM OrderJpaEntity o LEFT JOIN FETCH o.orderItems WHERE o.user.id = :userId ORDER BY COALESCE(o.orderAt, o.createdAt) DESC";
        return entityManager.createQuery(jpql, OrderJpaEntity.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public Optional<OrderJpaEntity> findCartByUserId(Long userId) {
        String jpql = "SELECT o FROM OrderJpaEntity o LEFT JOIN FETCH o.orderItems WHERE o.user.id = :userId AND o.status = :state";
        return entityManager.createQuery(jpql, OrderJpaEntity.class)
                .setParameter("userId", userId)
                .setParameter("state", OrderState.CART)
                .getResultStream()
                .findFirst();
    }

    @Override
    public OrderJpaEntity createOrder(OrderJpaEntity orderJpaEntity) {
        entityManager.persist(orderJpaEntity);
        return orderJpaEntity;
    }

    @Override
    public OrderJpaEntity updateOrder(OrderJpaEntity orderJpaEntity) {
        OrderJpaEntity managed = entityManager.find(OrderJpaEntity.class, orderJpaEntity.getId());
        if(managed == null) {
            throw new RuntimeException("Order with id " + orderJpaEntity.getId() + " not found");
        }
        return entityManager.merge(orderJpaEntity);
    }

    @Override
    public void deleteOrder(Long id) {
        OrderJpaEntity managed = entityManager.find(OrderJpaEntity.class, id);
        if(managed != null) {
            entityManager.remove(managed);
        }
    }

}
