package es.VetUp.tienda_back.c_persistence.dao.jpa.impl;


import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import es.VetUp.tienda_back.c_persistence.dao.jpa.UserJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.UserJpaEntity;

import java.util.List;
import java.util.Optional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Transactional
public class UserJpaDaoImpl implements UserJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserJpaEntity> getAllUsers() {
        return entityManager.createQuery(
                "SELECT u FROM UserJpaEntity u",
                UserJpaEntity.class)
                .getResultList();
    }


    @Override
    public Optional<UserJpaEntity> getClientById(Long id) {
        return Optional.ofNullable(entityManager.find(UserJpaEntity.class, id));
    }

    @Override
    public Optional<UserJpaEntity> getClientByEmail(String email) {
        String sql = "SELECT u FROM UserJpaEntity u WHERE u.email = :email";
        try {
            return Optional.of(entityManager.createQuery(sql, UserJpaEntity.class)
                    .setParameter("email", email)
                    .getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserJpaEntity> getClientByUsername(String username) {
        String sql = "SELECT u FROM UserJpaEntity u WHERE u.username = :username";
        try {
            return Optional.of(entityManager.createQuery(sql, UserJpaEntity.class)
                    .setParameter("username", username)
                    .getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public UserJpaEntity createClient(UserJpaEntity userJpaEntity) {
        entityManager.persist(userJpaEntity);
        return userJpaEntity;
    }

    @Override
    public UserJpaEntity updateClient(UserJpaEntity userJpaEntity) {
        UserJpaEntity managed = entityManager.find(UserJpaEntity.class, userJpaEntity.getId());
        if(managed == null) {
            throw new RuntimeException("User with id " + userJpaEntity.getId() + " not found");
        }
        return entityManager.merge(userJpaEntity);
    }

    @Override
    public void deleteClient(Long id) {
        UserJpaEntity managed = entityManager.find(UserJpaEntity.class, id);
        if(managed != null) {
            entityManager.remove(managed);
        }
    }
}
