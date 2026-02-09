package es.VetUp.tienda_back.c_persistence.dao.jpa;

import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.OrderItemJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemJpaDao extends JpaRepository<OrderItemJpaEntity, Long> {
    List<OrderItemJpaEntity> findByOrderId(Long orderId);
}


