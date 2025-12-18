package es.VetUp.tienda_back.c_persistence.repository.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import es.VetUp.tienda_back.b_domain.repository.entity.UserEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.UserJpaEntity;

class UserPersistenceMapperTets {

    @Test
    @DisplayName("Test map from UserJpaEntity to UserEntity")
    void testFromUserJpaEntityToUserEntity() {
        // Arrange
        UserJpaEntity jpaEntity = new UserJpaEntity(
                1L,
                "John Doe",
                "johndoe",
                "john@example.com",
                "password123",
                "123 Main St",
                UserRole.CUSTOMER,
                123456789,
                "Spain",
                "profile.jpg",
                LocalDate.of(1990, 5, 15));

        // Act
        UserEntity entity = UserPersistenceMapper.getInstance().fromUserJpaEntityToUserEntity(jpaEntity);

        // Assert
        assertEquals(jpaEntity.getId(), entity.id());
        assertEquals(jpaEntity.getName(), entity.name());
        assertEquals(jpaEntity.getUsername(), entity.username());
        assertEquals(jpaEntity.getEmail(), entity.email());
        assertEquals(jpaEntity.getPassword(), entity.password());
        assertEquals(jpaEntity.getAddress(), entity.address());
        assertEquals(jpaEntity.getIsAdmin(), entity.isAdmin());
        assertEquals(jpaEntity.getPhone(), entity.phone());
        assertEquals(jpaEntity.getCountry(), entity.country());
        assertEquals(jpaEntity.getProfilePicture(), entity.profilePicture());
        assertEquals(jpaEntity.getBirthdate(), entity.birthdate());
    }

    @Test
    @DisplayName("Test map from UserJpaEntity to UserEntity returns null when input is null")
    void testFromUserJpaEntityToUserEntityReturnsNull() {
        // Act
        UserEntity entity = UserPersistenceMapper.getInstance().fromUserJpaEntityToUserEntity(null);

        // Assert
        assertNull(entity);
    }

    @Test
    @DisplayName("Test map from UserEntity to UserJpaEntity")
    void testFromUserEntityToUserJpaEntity() {
        // Arrange
        UserEntity entity = new UserEntity(
                2L,
                "Jane Smith",
                "janesmith",
                "jane@example.com",
                "password456",
                "456 Oak Ave",
                UserRole.ADMIN,
                987654321,
                "USA",
                "profile2.jpg",
                LocalDate.of(1985, 8, 20));

        // Act
        UserJpaEntity jpaEntity = UserPersistenceMapper.getInstance().fromUserEntityToUserJpaEntity(entity);

        // Assert
        assertEquals(entity.id(), jpaEntity.getId());
        assertEquals(entity.name(), jpaEntity.getName());
        assertEquals(entity.username(), jpaEntity.getUsername());
        assertEquals(entity.email(), jpaEntity.getEmail());
        assertEquals(entity.password(), jpaEntity.getPassword());
        assertEquals(entity.address(), jpaEntity.getAddress());
        assertEquals(entity.isAdmin(), jpaEntity.getIsAdmin());
        assertEquals(entity.phone(), jpaEntity.getPhone());
        assertEquals(entity.country(), jpaEntity.getCountry());
        assertEquals(entity.profilePicture(), jpaEntity.getProfilePicture());
        assertEquals(entity.birthdate(), jpaEntity.getBirthdate());
    }

    @Test
    @DisplayName("Test map from UserEntity to UserJpaEntity returns null when input is null")
    void testFromUserEntityToUserJpaEntityReturnsNull() {
        // Act
        UserJpaEntity jpaEntity = UserPersistenceMapper.getInstance().fromUserEntityToUserJpaEntity(null);

        // Assert
        assertNull(jpaEntity);
    }
}
