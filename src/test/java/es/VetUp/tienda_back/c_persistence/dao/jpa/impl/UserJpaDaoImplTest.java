package es.VetUp.tienda_back.c_persistence.dao.jpa.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import es.VetUp.tienda_back.c_persistence.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import es.VetUp.tienda_back.c_persistence.dao.jpa.UserJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.UserJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(TestConfig.class)
class UserJpaDaoImplTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserJpaDao userJpaDao;

    @Test
    void testGetAllUsers() {
        // Arrange: persiste usuarios en la BD en memoria
        entityManager.createQuery("DELETE FROM UserJpaEntity").executeUpdate();
        entityManager.flush();

        UserJpaEntity user1 = new UserJpaEntity(
                null,
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
        entityManager.persist(user1);

        UserJpaEntity user2 = new UserJpaEntity(
                null,
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
        entityManager.persist(user2);
        entityManager.flush();

        // Act
        List<UserJpaEntity> result = userJpaDao.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(u -> "John Doe".equals(u.getName())));
        assertTrue(result.stream().anyMatch(u -> "Jane Smith".equals(u.getName())));
    }

    @Test
    void testGetClientById() {
        // Arrange: persiste un usuario en la BD en memoria
        UserJpaEntity user = new UserJpaEntity(
                null,
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
        entityManager.persist(user);
        entityManager.flush();
        Long userId = user.getId();

        // Act
        var result = userJpaDao.getClientById(userId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
        assertEquals("johndoe", result.get().getUsername());
    }

    @Test
    void testGetClientByIdNotFound() {
        // Act
        var result = userJpaDao.getClientById(99999L);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testGetClientByEmail() {
        // Arrange: persiste un usuario en la BD en memoria
        UserJpaEntity user = new UserJpaEntity(
                null,
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
        entityManager.persist(user);
        entityManager.flush();

        // Act
        var result = userJpaDao.getClientByEmail("john@example.com");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
        assertEquals("john@example.com", result.get().getEmail());
    }

    @Test
    void testGetClientByEmailNotFound() {
        // Act
        var result = userJpaDao.getClientByEmail("notfound@example.com");

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testGetClientByUsername() {
        // Arrange: persiste un usuario en la BD en memoria
        UserJpaEntity user = new UserJpaEntity(
                null,
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
        entityManager.persist(user);
        entityManager.flush();

        // Act
        var result = userJpaDao.getClientByUsername("johndoe");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
        assertEquals("johndoe", result.get().getUsername());
    }

    @Test
    void testGetClientByUsernameNotFound() {
        // Act
        var result = userJpaDao.getClientByUsername("unknownuser");

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testCreateClient() {
        // Arrange
        UserJpaEntity user = new UserJpaEntity(
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

        // Act
        UserJpaEntity createdUser = userJpaDao.createClient(user);

        // Assert
        assertNotNull(createdUser.getId());
        assertEquals("New User", createdUser.getName());
        assertEquals("newuser", createdUser.getUsername());
    }

    @Test
    void testUpdateClient() {
        // Arrange: persiste un usuario en la BD en memoria
        UserJpaEntity user = new UserJpaEntity(
                null,
                "Original Name",
                "originaluser",
                "original@example.com",
                "originalpassword",
                "Original Address",
                UserRole.CUSTOMER,
                111111111,
                "Original Country",
                "original.jpg",
                LocalDate.of(1990, 1, 1));
        entityManager.persist(user);
        entityManager.flush();

        // Act
        UserJpaEntity updatedUser = new UserJpaEntity(
                user.getId(),
                "Updated Name",
                "updateduser",
                "updated@example.com",
                "updatedpassword",
                "Updated Address",
                UserRole.ADMIN,
                222222222,
                "Updated Country",
                "updated.jpg",
                LocalDate.of(1990, 1, 1));
        UserJpaEntity result = userJpaDao.updateClient(updatedUser);

        // Assert
        assertEquals("Updated Name", result.getName());
        assertEquals("updateduser", result.getUsername());
        assertEquals("updated@example.com", result.getEmail());
    }

    @Test
    void testUpdateClientNotFound() {
        // Arrange
        UserJpaEntity nonExistentUser = new UserJpaEntity(
                99999L,
                "Non Existent",
                "nonexistent",
                "nonexistent@example.com",
                "password",
                "Address",
                UserRole.CUSTOMER,
                123456789,
                "Country",
                "pic.jpg",
                LocalDate.of(1990, 1, 1));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userJpaDao.updateClient(nonExistentUser));
    }

    @Test
    void testDeleteClient() {
        // Arrange: persiste un usuario en la BD en memoria
        UserJpaEntity user = new UserJpaEntity(
                null,
                "User To Delete",
                "deleteuser",
                "delete@example.com",
                "password",
                "Delete Address",
                UserRole.CUSTOMER,
                999999999,
                "Delete Country",
                "delete.jpg",
                LocalDate.of(1990, 1, 1));
        entityManager.persist(user);
        entityManager.flush();
        Long userId = user.getId();

        // Act
        userJpaDao.deleteClient(userId);
        entityManager.flush();
        UserJpaEntity deletedUser = entityManager.find(UserJpaEntity.class, userId);

        // Assert
        assertNull(deletedUser);
    }
}
