package es.VetUp.tienda_back.b_domain.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import es.VetUp.tienda_back.b_domain.service.UserService;
import es.VetUp.tienda_back.b_domain.service.dto.UserDto;
import io.jsonwebtoken.Claims;

@ExtendWith(MockitoExtension.class)
class JwtServiceImplTest {

    @Mock
    private UserService userService;

    private JwtServiceImpl jwtService;
    private UserDto testUser;

    @BeforeEach
    void setUp() {
        jwtService = new JwtServiceImpl(userService);

        testUser = new UserDto(
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
    }

    @Test
    @DisplayName("generateToken should return a valid token")
    void testGenerateToken() {
        String token = jwtService.generateToken(testUser);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    @DisplayName("validateToken should return claims for valid token")
    void testValidateToken() {
        String token = jwtService.generateToken(testUser);

        Claims claims = jwtService.validateToken(token);

        assertNotNull(claims);
        assertEquals(testUser.id(), claims.get("id", Long.class));
    }

    @Test
    @DisplayName("validateToken should throw exception for invalid token")
    void testValidateTokenInvalid() {
        String invalidToken = "invalid.token.here";

        assertThrows(Exception.class, () -> jwtService.validateToken(invalidToken));
    }

    @Test
    @DisplayName("getUserFromToken should return user for valid token")
    void testGetUserFromToken() {
        String token = jwtService.generateToken(testUser);

        when(userService.getUserById(testUser.id())).thenReturn(Optional.of(testUser));

        UserDto result = jwtService.getUserFromToken(token);

        assertNotNull(result);
        assertEquals(testUser.id(), result.id());
        assertEquals(testUser.name(), result.name());
    }

    @Test
    @DisplayName("getUserFromToken should throw exception when user not found")
    void testGetUserFromTokenUserNotFound() {
        String token = jwtService.generateToken(testUser);

        when(userService.getUserById(testUser.id())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> jwtService.getUserFromToken(token));
    }
}
