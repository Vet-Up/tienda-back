package es.VetUp.tienda_back.b_domain.mapper;

import es.VetUp.tienda_back.b_domain.model.User;
import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import es.VetUp.tienda_back.b_domain.repository.entity.UserEntity;
import es.VetUp.tienda_back.b_domain.service.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    @DisplayName("Test map User to UserDTO")
    void testMapUserToUserDTO() {
        User user = new User(1L, "John Doe", "johndoe", "john@example.com",
                "password123", "123 Main St", UserRole.CUSTOMER, 123456789,
                "Spain", "profile.jpg", LocalDate.of(1990, 5, 15));

        var userDTO = UserMapper.getInstance().fromUserToUserDto(user);

        assertAll("userDTO",
                () -> assertEquals(user.getId(), userDTO.id()),
                () -> assertEquals(user.getName(), userDTO.name()),
                () -> assertEquals(user.getUsername(), userDTO.username()),
                () -> assertEquals(user.getEmail(), userDTO.email()),
                () -> assertEquals(user.getPassword(), userDTO.password()),
                () -> assertEquals(user.getAddress(), userDTO.address()),
                () -> assertEquals(user.getAdmin(), userDTO.isAdmin()),
                () -> assertEquals(user.getPhone(), userDTO.phone()),
                () -> assertEquals(user.getCountry(), userDTO.country()),
                () -> assertEquals(user.getProfilePicture(), userDTO.profilePicture()),
                () -> assertEquals(user.getBirthdate(), userDTO.birthdate()));
    }

    @Test
    @DisplayName("Test map null User to UserDTO returns null")
    void testMapNullUserToUserDTOReturnsNull() {
        User user = null;

        var userDTO = UserMapper.getInstance().fromUserToUserDto(user);

        assertNull(userDTO);
    }

    @Test
    @DisplayName("Test map UserDTO to User")
    void testMapUserDTOToUser() {
        var userDTO = new UserDto(1L, "John Doe", "johndoe", "john@example.com",
                "password123", "123 Main St", UserRole.CUSTOMER, 123456789,
                "Spain", "profile.jpg", LocalDate.of(1990, 5, 15));

        var user = UserMapper.getInstance().fromUserDtoToUser(userDTO);

        assertAll("user",
                () -> assertEquals(userDTO.id(), user.getId()),
                () -> assertEquals(userDTO.name(), user.getName()),
                () -> assertEquals(userDTO.username(), user.getUsername()),
                () -> assertEquals(userDTO.email(), user.getEmail()),
                () -> assertEquals(userDTO.password(), user.getPassword()),
                () -> assertEquals(userDTO.address(), user.getAddress()),
                () -> assertEquals(userDTO.isAdmin(), user.getAdmin()),
                () -> assertEquals(userDTO.phone(), user.getPhone()),
                () -> assertEquals(userDTO.country(), user.getCountry()),
                () -> assertEquals(userDTO.profilePicture(), user.getProfilePicture()),
                () -> assertEquals(userDTO.birthdate(), user.getBirthdate()));
    }

    @Test
    @DisplayName("Test map null UserDTO to User returns null")
    void testMapNullUserDTOToUserReturnsNull() {
        UserDto userDTO = null;

        var user = UserMapper.getInstance().fromUserDtoToUser(userDTO);

        assertNull(user);
    }

    @Test
    @DisplayName("Test map UserEntity to User")
    void testMapUserEntityToUser() {
        var userEntity = new UserEntity(1L, "John Doe", "johndoe", "john@example.com",
                "password123", "123 Main St", UserRole.CUSTOMER, 123456789,
                "Spain", "profile.jpg", LocalDate.of(1990, 5, 15));

        var user = UserMapper.getInstance().fromUserEntityToUser(userEntity);

        assertAll("user",
                () -> assertEquals(userEntity.id(), user.getId()),
                () -> assertEquals(userEntity.name(), user.getName()),
                () -> assertEquals(userEntity.username(), user.getUsername()),
                () -> assertEquals(userEntity.email(), user.getEmail()),
                () -> assertEquals(userEntity.password(), user.getPassword()),
                () -> assertEquals(userEntity.address(), user.getAddress()),
                () -> assertEquals(userEntity.isAdmin(), user.getAdmin()),
                () -> assertEquals(userEntity.phone(), user.getPhone()),
                () -> assertEquals(userEntity.country(), user.getCountry()),
                () -> assertEquals(userEntity.profilePicture(), user.getProfilePicture()),
                () -> assertEquals(userEntity.birthdate(), user.getBirthdate()));
    }

    @Test
    @DisplayName("Test map null UserEntity to User returns null")
    void testMapNullUserEntityToUserReturnsNull() {
        UserEntity userEntity = null;

        var user = UserMapper.getInstance().fromUserEntityToUser(userEntity);

        assertNull(user);
    }

    @Test
    @DisplayName("Test map User to UserEntity")
    void testMapUserToUserEntity() {
        User user = new User(1L, "John Doe", "johndoe", "john@example.com",
                "password123", "123 Main St", UserRole.CUSTOMER, 123456789,
                "Spain", "profile.jpg", LocalDate.of(1990, 5, 15));

        var userEntity = UserMapper.getInstance().fromUserToUserEntity(user);

        assertAll("userEntity",
                () -> assertEquals(user.getId(), userEntity.id()),
                () -> assertEquals(user.getName(), userEntity.name()),
                () -> assertEquals(user.getUsername(), userEntity.username()),
                () -> assertEquals(user.getEmail(), userEntity.email()),
                () -> assertEquals(user.getPassword(), userEntity.password()),
                () -> assertEquals(user.getAddress(), userEntity.address()),
                () -> assertEquals(user.getAdmin(), userEntity.isAdmin()),
                () -> assertEquals(user.getPhone(), userEntity.phone()),
                () -> assertEquals(user.getCountry(), userEntity.country()),
                () -> assertEquals(user.getProfilePicture(), userEntity.profilePicture()),
                () -> assertEquals(user.getBirthdate(), userEntity.birthdate()));
    }

    @Test
    @DisplayName("Test map null User to UserEntity returns null")
    void testMapNullUserToUserEntityReturnsNull() {
        User user = null;

        var userEntity = UserMapper.getInstance().fromUserToUserEntity(user);

        assertNull(userEntity);
    }
}
