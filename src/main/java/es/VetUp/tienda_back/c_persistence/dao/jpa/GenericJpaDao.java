package es.VetUp.tienda_back.c_persistence.dao.jpa;

import java.util.List;
import java.util.Optional;

public interface GenericJpaDao<T> {

    T insert(T jpaEntity);

    T update(T jpaEntity);

    void deleteById(Long productId);

    Optional<T> findById(Long productId);

    List<T> findAll(int page, int size);

    long count();
}
