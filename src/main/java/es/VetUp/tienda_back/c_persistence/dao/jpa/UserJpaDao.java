package es.VetUp.tienda_back.c_persistence.dao.jpa;


import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.UserJpaEntity;

import java.util.List;
import java.util.Optional;

public interface UserJpaDao {
    List<UserJpaEntity> getAllUsers();
    Optional<UserJpaEntity> getClientById(Long id);
    Optional<UserJpaEntity> getClientByEmail(String email);
    Optional<UserJpaEntity> getClientByUsername(String username);
    UserJpaEntity createClient(UserJpaEntity userJpaEntity);
    UserJpaEntity updateClient(UserJpaEntity userJpaEntity);
    void deleteClient(Long id);
}
