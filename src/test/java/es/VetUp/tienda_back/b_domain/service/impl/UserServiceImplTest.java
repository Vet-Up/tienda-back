package es.VetUp.tienda_back.b_domain.service.impl;

import es.VetUp.tienda_back.b_domain.exception.BusinessException;
import es.VetUp.tienda_back.b_domain.exception.ResourceNotFoundException;
import es.VetUp.tienda_back.b_domain.mapper.UserMapper;
import es.VetUp.tienda_back.b_domain.model.Page;
import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import es.VetUp.tienda_back.b_domain.repository.UserRepository;
import es.VetUp.tienda_back.b_domain.repository.entity.UserEntity;
import es.VetUp.tienda_back.b_domain.service.OrderService;
import es.VetUp.tienda_back.b_domain.service.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

        @Mock
        private UserRepository userRepository;

        @Mock
        private OrderService orderService;

        @Mock
        private PasswordEncoder passwordEncoder;

        @InjectMocks
        private UserServiceImpl userServiceImpl;

        @Nested
        class GetAllUsersTests {

                @Test
                @DisplayName("getAllUsers should return page of users")
                void testGetAllUsers() {
                        int page = 1;
                        int size = 10;

                        UserEntity user1 = new UserEntity(
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

                        UserEntity user2 = new UserEntity(
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

                        List<UserEntity> userEntities = List.of(user1, user2);
                        Page<UserEntity> userEntityPage = new Page<>(userEntities, page, size, 2L);

                        when(userRepository.getAllUsers(page, size)).thenReturn(userEntityPage);

                        Page<UserDto> result = userServiceImpl.getAllUsers(page, size);

                        assertAll("result",
                                        () -> assertNotNull(result, "Result should not be null"),
                                        () -> assertEquals(2, result.data().size(), "Result size should be 2"),
                                        () -> assertEquals("John Doe", result.data().get(0).name()),
                                        () -> assertEquals("Jane Smith", result.data().get(1).name()),
                                        () -> assertEquals(page, result.pageNumber()),
                                        () -> assertEquals(size, result.pageSize()),
                                        () -> assertEquals(2L, result.totalElements()));
                }

                @Test
                @DisplayName("getAllUsers should return empty page when no users found")
                void testGetAllUsersEmpty() {
                        int page = 1;
                        int size = 10;
                        Page<UserEntity> emptyPage = new Page<>(List.of(), page, size, 0L);

                        when(userRepository.getAllUsers(page, size)).thenReturn(emptyPage);

                        Page<UserDto> result = userServiceImpl.getAllUsers(page, size);

                        assertAll("result",
                                        () -> assertNotNull(result),
                                        () -> assertEquals(0, result.data().size()));
                }

                @Test
                @DisplayName("getAllUsers should throw exception when page or size less than 1")
                void testGetAllUsersInvalidParams() {
                        assertThrows(IllegalArgumentException.class, () -> userServiceImpl.getAllUsers(0, 10));
                        assertThrows(IllegalArgumentException.class, () -> userServiceImpl.getAllUsers(1, 0));
                }
        }

        @Nested
        class GetUserByIdTests {

                @Test
                @DisplayName("getUserById should return userDto when found")
                void testGetUserById() {
                        Long userId = 1L;

                        UserEntity userEntity = new UserEntity(
                                        userId,
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

                        when(userRepository.getClientById(userId)).thenReturn(Optional.of(userEntity));

                        Optional<UserDto> result = userServiceImpl.getUserById(userId);

                        assertTrue(result.isPresent());
                        assertEquals("John Doe", result.get().name());
                }

                @Test
                @DisplayName("getUserById should return empty when user not found")
                void testGetUserByIdNotFound() {
                        Long userId = 1L;
                        when(userRepository.getClientById(userId)).thenReturn(Optional.empty());

                        Optional<UserDto> result = userServiceImpl.getUserById(userId);

                        assertFalse(result.isPresent());
                }
        }

        @Nested
        class GetUserByEmailTests {

                @Test
                @DisplayName("getUserByEmail should return userDto when found")
                void testGetUserByEmail() {
                        String email = "john@example.com";

                        UserEntity userEntity = new UserEntity(
                                        1L,
                                        "John Doe",
                                        "johndoe",
                                        email,
                                        "password123",
                                        "123 Main St",
                                        UserRole.CUSTOMER,
                                        123456789,
                                        "Spain",
                                        "profile.jpg",
                                        LocalDate.of(1990, 5, 15));

                        when(userRepository.getClientByEmail(email)).thenReturn(Optional.of(userEntity));

                        Optional<UserDto> result = userServiceImpl.getUserByEmail(email);

                        assertTrue(result.isPresent());
                        assertEquals(email, result.get().email());
                }

                @Test
                @DisplayName("getUserByEmail should return empty when user not found")
                void testGetUserByEmailNotFound() {
                        String email = "notfound@example.com";
                        when(userRepository.getClientByEmail(email)).thenReturn(Optional.empty());

                        Optional<UserDto> result = userServiceImpl.getUserByEmail(email);

                        assertFalse(result.isPresent());
                }
        }

        @Nested
        class GetUserByUsernameTests {

                @Test
                @DisplayName("getUserByUsername should return userDto when found")
                void testGetUserByUsername() {
                        String username = "johndoe";

                        UserEntity userEntity = new UserEntity(
                                        1L,
                                        "John Doe",
                                        username,
                                        "john@example.com",
                                        "password123",
                                        "123 Main St",
                                        UserRole.CUSTOMER,
                                        123456789,
                                        "Spain",
                                        "profile.jpg",
                                        LocalDate.of(1990, 5, 15));

                        UserDto expected = UserMapper.getInstance()
                                        .fromUserToUserDto(UserMapper.getInstance().fromUserEntityToUser(userEntity));

                        when(userRepository.getClientByUsername(username)).thenReturn(Optional.of(userEntity));

                        UserDto result = userServiceImpl.getUserByUsername(username);

                        assertEquals(expected, result);
                }

                @Test
                @DisplayName("getUserByUsername should throw exception when user not found")
                void testGetUserByUsernameNotFound() {
                        String username = "unknownuser";
                        when(userRepository.getClientByUsername(username)).thenReturn(Optional.empty());

                        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                                        () -> userServiceImpl.getUserByUsername(username));

                        assertEquals("User with username " + username + " not found", exception.getMessage());
                }
        }

        @Nested
        class CreateUserTests {

                @Test
                @DisplayName("createUser should create and return userDto")
                void testCreateUser() {
                        UserDto userDtoToCreate = new UserDto(
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

                        UserEntity createdUserEntity = new UserEntity(
                                        1L,
                                        "John Doe",
                                        "johndoe",
                                        "john@example.com",
                                        "hashedPassword",
                                        "123 Main St",
                                        UserRole.CUSTOMER,
                                        123456789,
                                        "Spain",
                                        "profile.jpg",
                                        LocalDate.of(1990, 5, 15));

                        when(userRepository.getClientByEmail(userDtoToCreate.email())).thenReturn(Optional.empty());
                        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
                        when(userRepository.createClient(any(UserEntity.class))).thenReturn(createdUserEntity);

                        UserDto result = userServiceImpl.createUser(userDtoToCreate);

                        assertAll("result",
                                        () -> assertNotNull(result),
                                        () -> assertEquals(1L, result.id()),
                                        () -> assertEquals("John Doe", result.name()));
                }

                @Test
                @DisplayName("createUser should throw exception when email already exists")
                void testCreateUserEmailExists() {
                        UserDto userDtoToCreate = new UserDto(
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

                        UserEntity existingUser = new UserEntity(
                                        1L,
                                        "Existing User",
                                        "existinguser",
                                        "john@example.com",
                                        "password",
                                        "Address",
                                        UserRole.CUSTOMER,
                                        111111111,
                                        "Country",
                                        "pic.jpg",
                                        LocalDate.of(1990, 1, 1));

                        when(userRepository.getClientByEmail(userDtoToCreate.email()))
                                        .thenReturn(Optional.of(existingUser));

                        BusinessException exception = assertThrows(BusinessException.class,
                                        () -> userServiceImpl.createUser(userDtoToCreate));

                        assertEquals("User with email " + userDtoToCreate.email() + " already exists",
                                        exception.getMessage());
                }
        }

        @Nested
        class UpdateUserTests {

                @Test
                @DisplayName("updateUser should update and return userDto")
                void testUpdateUser() {
                        Long userIdToUpdate = 1L;
                        UserDto userDtoToUpdate = new UserDto(
                                        userIdToUpdate,
                                        "Updated John",
                                        "johndoe",
                                        "john@example.com",
                                        "newPassword",
                                        "456 New St",
                                        UserRole.CUSTOMER,
                                        123456789,
                                        "Spain",
                                        "new_profile.jpg",
                                        LocalDate.of(1990, 5, 15));

                        UserEntity existingUserEntity = new UserEntity(
                                        userIdToUpdate,
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

                        UserEntity updatedUserEntity = new UserEntity(
                                        userIdToUpdate,
                                        "Updated John",
                                        "johndoe",
                                        "john@example.com",
                                        "hashedNewPassword",
                                        "456 New St",
                                        UserRole.CUSTOMER,
                                        123456789,
                                        "Spain",
                                        "new_profile.jpg",
                                        LocalDate.of(1990, 5, 15));

                        when(userRepository.getClientById(userIdToUpdate)).thenReturn(Optional.of(existingUserEntity));
                        when(userRepository.getClientByEmail(userDtoToUpdate.email()))
                                        .thenReturn(Optional.of(existingUserEntity));
                        when(passwordEncoder.encode(anyString())).thenReturn("hashedNewPassword");
                        when(userRepository.updateClient(any(UserEntity.class))).thenReturn(updatedUserEntity);

                        UserDto result = userServiceImpl.updateUser(userDtoToUpdate);

                        assertAll("result",
                                        () -> assertNotNull(result),
                                        () -> assertEquals(userIdToUpdate, result.id()),
                                        () -> assertEquals("Updated John", result.name()));
                }

                @Test
                @DisplayName("updateUser should throw exception when user does not exist")
                void testUpdateUserNotFound() {
                        Long userIdToUpdate = 1L;
                        UserDto userDtoToUpdate = new UserDto(
                                        userIdToUpdate,
                                        "Updated John",
                                        "johndoe",
                                        "john@example.com",
                                        "newPassword",
                                        "456 New St",
                                        UserRole.CUSTOMER,
                                        123456789,
                                        "Spain",
                                        "new_profile.jpg",
                                        LocalDate.of(1990, 5, 15));

                        when(userRepository.getClientById(userIdToUpdate)).thenReturn(Optional.empty());

                        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                                        () -> userServiceImpl.updateUser(userDtoToUpdate));

                        assertEquals("User with id " + userIdToUpdate + " not found", exception.getMessage());
                }

                @Test
                @DisplayName("updateUser should throw exception when email belongs to another user")
                void testUpdateUserEmailConflict() {
                        Long userIdToUpdate = 1L;
                        UserDto userDtoToUpdate = new UserDto(
                                        userIdToUpdate,
                                        "Updated John",
                                        "johndoe",
                                        "other@example.com",
                                        "newPassword",
                                        "456 New St",
                                        UserRole.CUSTOMER,
                                        123456789,
                                        "Spain",
                                        "new_profile.jpg",
                                        LocalDate.of(1990, 5, 15));

                        UserEntity existingUserEntity = new UserEntity(
                                        userIdToUpdate,
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

                        UserEntity anotherUserEntity = new UserEntity(
                                        2L,
                                        "Other User",
                                        "otheruser",
                                        "other@example.com",
                                        "password",
                                        "Address",
                                        UserRole.CUSTOMER,
                                        111111111,
                                        "Country",
                                        "pic.jpg",
                                        LocalDate.of(1990, 1, 1));

                        when(userRepository.getClientById(userIdToUpdate)).thenReturn(Optional.of(existingUserEntity));
                        when(userRepository.getClientByEmail(userDtoToUpdate.email()))
                                        .thenReturn(Optional.of(anotherUserEntity));

                        BusinessException exception = assertThrows(BusinessException.class,
                                        () -> userServiceImpl.updateUser(userDtoToUpdate));

                        assertEquals("Another user with email " + userDtoToUpdate.email() + " already exists",
                                        exception.getMessage());
                }
        }

        @Nested
        class DeleteUserTests {

                @Test
                @DisplayName("deleteUser should delete user when it exists")
                void testDeleteUser() {
                        Long userIdToDelete = 1L;

                        UserEntity existingUserEntity = new UserEntity(
                                        userIdToDelete,
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

                        when(userRepository.getClientById(userIdToDelete)).thenReturn(Optional.of(existingUserEntity));

                        assertDoesNotThrow(() -> userServiceImpl.deleteUser(userIdToDelete));
                        verify(userRepository).deleteClient(userIdToDelete);
                }

                @Test
                @DisplayName("deleteUser should throw exception when user does not exist")
                void testDeleteUserNotFound() {
                        Long userIdToDelete = 1L;

                        when(userRepository.getClientById(userIdToDelete)).thenReturn(Optional.empty());

                        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                                        () -> userServiceImpl.deleteUser(userIdToDelete));

                        assertEquals("User with id " + userIdToDelete + " not found", exception.getMessage());
                }
        }

        @Nested
        class SearchByEmailTests {
                @Test
                @DisplayName("searchByEmail should return page of users")
                void testSearchByEmail() {
                        String email = "john";
                        int page = 1;
                        int size = 10;

                        UserEntity user1 = new UserEntity(
                                        1L, "John Doe", "johndoe", "john@example.com", "pass", "addr",
                                        UserRole.CUSTOMER, 123, "ES", "pic", LocalDate.now());

                        Page<UserEntity> userEntityPage = new Page<>(List.of(user1), page, size, 1L);
                        when(userRepository.searchByEmail(email, page, size)).thenReturn(userEntityPage);

                        Page<UserDto> result = userServiceImpl.searchByEmail(email, page, size);

                        assertNotNull(result);
                        assertEquals(1, result.data().size());
                        assertEquals("John Doe", result.data().get(0).name());
                }

                @Test
                @DisplayName("searchByEmail should throw exception when page or size less than 1")
                void testSearchByEmailInvalidParams() {
                        assertThrows(IllegalArgumentException.class,
                                        () -> userServiceImpl.searchByEmail("john", -1, 10));
                        assertThrows(IllegalArgumentException.class, () -> userServiceImpl.searchByEmail("john", 1, 0));
                }
        }
}
