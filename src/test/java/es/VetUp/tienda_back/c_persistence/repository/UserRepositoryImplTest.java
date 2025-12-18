package es.VetUp.tienda_back.c_persistence.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import es.VetUp.tienda_back.b_domain.repository.entity.UserEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.UserJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.UserJpaEntity;
import es.VetUp.tienda_back.c_persistence.repository.mapper.UserPersistenceMapper;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryImplTest {

    @Mock
    private UserJpaDao userJpaDao;

    @InjectMocks
    private UserRepositoryImpl userRepositoryImpl;

    UserJpaEntity userJpaEntity1;
    UserJpaEntity userJpaEntity2;

    @BeforeEach
    void setUp() {
        userJpaEntity1 = new UserJpaEntity(
                1L,
                "John Doe",
                "johndoe",
                "john@example.com",
                "password123",
                "123 Main St",
                UserRole.CUSTOMER,
                123456789,
                "Spain",
                "profile1.jpg",
                LocalDate.of(1990, 5, 15));

        userJpaEntity2 = new UserJpaEntity(
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
    }

    @Nested
    class GetAllUsersTests {
        @Test
        @DisplayName("getAllUsers should return all users")
        void testGetAllUsers() {
            List<UserJpaEntity> expectedList = List.of(userJpaEntity1, userJpaEntity2);

            when(userJpaDao.getAllUsers()).thenReturn(expectedList);

            List<UserEntity> actual = userRepositoryImpl.getAllUsers();

            List<UserEntity> expectedEntities = expectedList.stream()
                    .map(UserPersistenceMapper.getInstance()::fromUserJpaEntityToUserEntity)
                    .toList();

            assertAll(
                    () -> assertEquals(expectedEntities.size(), actual.size()),
                    () -> assertEquals(expectedEntities, actual));
        }

        @Test
        @DisplayName("getAllUsers should return empty list when no users exist")
        void testGetAllUsersEmpty() {
            List<UserJpaEntity> expectedList = List.of();

            when(userJpaDao.getAllUsers()).thenReturn(expectedList);

            List<UserEntity> actual = userRepositoryImpl.getAllUsers();

            assertEquals(0, actual.size());
        }
    }

    @Nested
    class GetClientByIdTests {
        @Test
        @DisplayName("getClientById should return user when found")
        void testGetClientById() {
            when(userJpaDao.getClientById(1L)).thenReturn(Optional.of(userJpaEntity1));

            Optional<UserEntity> actual = userRepositoryImpl.getClientById(1L);

            UserEntity expectedEntity = UserPersistenceMapper.getInstance()
                    .fromUserJpaEntityToUserEntity(userJpaEntity1);

            assertAll(
                    () -> assertEquals(true, actual.isPresent()),
                    () -> assertEquals(expectedEntity, actual.get()));
        }

        @Test
        @DisplayName("getClientById should return empty when not found")
        void testGetClientByIdNotFound() {
            when(userJpaDao.getClientById(99L)).thenReturn(Optional.empty());

            Optional<UserEntity> actual = userRepositoryImpl.getClientById(99L);

            assertEquals(false, actual.isPresent());
        }
    }

    @Nested
    class GetClientByEmailTests {
        @Test
        @DisplayName("getClientByEmail should return user when found")
        void testGetClientByEmail() {
            when(userJpaDao.getClientByEmail("john@example.com")).thenReturn(Optional.of(userJpaEntity1));

            Optional<UserEntity> actual = userRepositoryImpl.getClientByEmail("john@example.com");

            UserEntity expectedEntity = UserPersistenceMapper.getInstance()
                    .fromUserJpaEntityToUserEntity(userJpaEntity1);

            assertAll(
                    () -> assertEquals(true, actual.isPresent()),
                    () -> assertEquals(expectedEntity, actual.get()));
        }

        @Test
        @DisplayName("getClientByEmail should return empty when not found")
        void testGetClientByEmailNotFound() {
            when(userJpaDao.getClientByEmail("notfound@example.com")).thenReturn(Optional.empty());

            Optional<UserEntity> actual = userRepositoryImpl.getClientByEmail("notfound@example.com");

            assertEquals(false, actual.isPresent());
        }
    }

    @Nested
    class GetClientByUsernameTests {
        @Test
        @DisplayName("getClientByUsername should return user when found")
        void testGetClientByUsername() {
            when(userJpaDao.getClientByUsername("johndoe")).thenReturn(Optional.of(userJpaEntity1));

            Optional<UserEntity> actual = userRepositoryImpl.getClientByUsername("johndoe");

            UserEntity expectedEntity = UserPersistenceMapper.getInstance()
                    .fromUserJpaEntityToUserEntity(userJpaEntity1);

            assertAll(
                    () -> assertEquals(true, actual.isPresent()),
                    () -> assertEquals(expectedEntity, actual.get()));
        }

        @Test
        @DisplayName("getClientByUsername should return empty when not found")
        void testGetClientByUsernameNotFound() {
            when(userJpaDao.getClientByUsername("unknownuser")).thenReturn(Optional.empty());

            Optional<UserEntity> actual = userRepositoryImpl.getClientByUsername("unknownuser");

            assertEquals(false, actual.isPresent());
        }
    }

    @Nested
    class CreateClientTests {
        @Test
        @DisplayName("createClient should create and return user")
        void testCreateClient() {
            UserEntity userEntityToCreate = new UserEntity(
                    null,
                    "New User",
                    "newuser",
                    "newuser@example.com",
                    "newpassword",
                    "789 New St",
                    UserRole.CUSTOMER,
                    555555555,
                    "France",
                    "newprofile.jpg",
                    LocalDate.of(1995, 3, 10));

            when(userJpaDao.createClient(any(UserJpaEntity.class))).thenReturn(userJpaEntity1);

            UserEntity actual = userRepositoryImpl.createClient(userEntityToCreate);

            UserEntity expectedEntity = UserPersistenceMapper.getInstance()
                    .fromUserJpaEntityToUserEntity(userJpaEntity1);

            assertEquals(expectedEntity, actual);
        }
    }

    @Nested
    class UpdateClientTests {
        @Test
        @DisplayName("updateClient should update and return user")
        void testUpdateClient() {
            UserEntity userEntityToUpdate = UserPersistenceMapper.getInstance()
                    .fromUserJpaEntityToUserEntity(userJpaEntity1);

            when(userJpaDao.updateClient(any(UserJpaEntity.class))).thenReturn(userJpaEntity1);

            UserEntity actual = userRepositoryImpl.updateClient(userEntityToUpdate);

            UserEntity expectedEntity = UserPersistenceMapper.getInstance()
                    .fromUserJpaEntityToUserEntity(userJpaEntity1);

            assertEquals(expectedEntity, actual);
        }
    }

    @Nested
    class DeleteClientTests {
        @Test
        @DisplayName("deleteClient should call dao to delete user by id")
        void testDeleteClient() {
            Long userIdToDelete = 1L;

            userRepositoryImpl.deleteClient(userIdToDelete);

            verify(userJpaDao).deleteClient(userIdToDelete);
        }
    }
}
