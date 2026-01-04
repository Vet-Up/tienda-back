package es.VetUp.tienda_back.c_persistence.repository;

import es.VetUp.tienda_back.b_domain.model.Page;
import es.VetUp.tienda_back.b_domain.repository.UserRepository;
import es.VetUp.tienda_back.b_domain.repository.entity.UserEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.UserJpaDao;
import es.VetUp.tienda_back.c_persistence.repository.mapper.UserPersistenceMapper;

import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    private final UserJpaDao userJpaDao;

    public UserRepositoryImpl(UserJpaDao userJpaDao) {
        this.userJpaDao = userJpaDao;
    }

    @Override
    public Page<UserEntity> getAllUsers(int page, int size) {
        List<UserEntity> content = userJpaDao.getAllUsers(page, size).stream().map(UserPersistenceMapper.getInstance()::fromUserJpaEntityToUserEntity).toList();
        long totalElements = userJpaDao.count();
        return new Page<>(content, page, size, totalElements);
    }

    @Override
    public Page<UserEntity> searchByEmail(String email, int page, int size) {
        List<UserEntity> content = userJpaDao.searchByEmail(email, page, size).stream()
                .map(UserPersistenceMapper.getInstance()::fromUserJpaEntityToUserEntity).toList();
        long totalElements = userJpaDao.countByEmail(email);
        return new Page<>(content, page, size, totalElements);
    }

    @Override
    public Optional<UserEntity> getClientById(Long id) {
        return userJpaDao.getClientById(id).map(UserPersistenceMapper.getInstance()::fromUserJpaEntityToUserEntity);
    }

    @Override
    public Optional<UserEntity> getClientByEmail(String email) {
        return userJpaDao.getClientByEmail(email).map(UserPersistenceMapper.getInstance()::fromUserJpaEntityToUserEntity);
    }

    @Override
    public Optional<UserEntity> getClientByUsername(String username) {
        return userJpaDao.getClientByUsername(username).map(UserPersistenceMapper.getInstance()::fromUserJpaEntityToUserEntity);
    }

    @Override
    public UserEntity createClient(UserEntity userEntity) {
        return UserPersistenceMapper.getInstance().fromUserJpaEntityToUserEntity(
            userJpaDao.createClient(
                UserPersistenceMapper.getInstance().fromUserEntityToUserJpaEntity(userEntity)
            )
        );
    }

    @Override
    public UserEntity updateClient(UserEntity userEntity) {
        return UserPersistenceMapper.getInstance().fromUserJpaEntityToUserEntity(
            userJpaDao.updateClient(
                UserPersistenceMapper.getInstance().fromUserEntityToUserJpaEntity(userEntity)
            )
        );
    }

    @Override
    public void deleteClient(Long id) {
        userJpaDao.deleteClient(id);
    }
}
