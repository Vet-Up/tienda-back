package es.VetUp.tienda_back.c_persistence.repository.mapper;

import es.VetUp.tienda_back.b_domain.repository.entity.UserEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.UserJpaEntity;

public class UserPersistenceMapper {
    private static UserPersistenceMapper INSTANCE;
    private UserPersistenceMapper() {
    }
    public static UserPersistenceMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserPersistenceMapper();
        }
        return INSTANCE;
    }

    public UserEntity fromUserJpaEntityToUserEntity(UserJpaEntity userJpaEntity) {
        if (userJpaEntity == null) {
            return null;
        }
        return new UserEntity(
            userJpaEntity.getId(),
            userJpaEntity.getName(),
            userJpaEntity.getUsername(),
            userJpaEntity.getEmail(),
            userJpaEntity.getPassword(),
            userJpaEntity.getAddress(),
            userJpaEntity.getIsAdmin(),
            userJpaEntity.getPhone(),
            userJpaEntity.getCountry(),
            userJpaEntity.getProfilePicture(),
            userJpaEntity.getBirthdate()
        );
    }

    public UserJpaEntity fromUserEntityToUserJpaEntity(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        return new UserJpaEntity(
            userEntity.id(),
            userEntity.name(),
            userEntity.username(),
            userEntity.email(),
            userEntity.password(),
            userEntity.address(),
            userEntity.isAdmin(),
            userEntity.phone(),
            userEntity.country(),
            userEntity.profilePicture(),
            userEntity.birthdate()

        );
    }
}
