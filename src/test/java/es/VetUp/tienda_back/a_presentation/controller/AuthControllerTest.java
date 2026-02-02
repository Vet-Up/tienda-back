package es.VetUp.tienda_back.a_presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.LoginRequest;
import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import es.VetUp.tienda_back.b_domain.service.JwtService;
import es.VetUp.tienda_back.b_domain.service.UserService;
import es.VetUp.tienda_back.b_domain.service.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    private ObjectMapper objectMapper;
    private UserDto testUser;
    private UserDto adminUser;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        testUser = new UserDto(
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

        adminUser = new UserDto(
                2L,
                "Admin User",
                "admin",
                "admin@example.com",
                "hashedPassword",
                "456 Admin St",
                UserRole.ADMIN,
                987654321,
                "USA",
                "admin.jpg",
                LocalDate.of(1985, 8, 20));
    }

    @Nested
    class LoginTests {

        @Test
        @DisplayName("POST /api/auth/login/CUSTOMER - Success")
        void testLoginCustomerSuccess() throws Exception {
            LoginRequest loginRequest = new LoginRequest("johndoe", "password123");

            when(userService.getUserByUsername("johndoe")).thenReturn(testUser);
            when(passwordEncoder.matches("password123", "hashedPassword")).thenReturn(true);
            when(jwtService.generateToken(any(UserDto.class))).thenReturn("test.jwt.token");

            String requestJson = objectMapper.writeValueAsString(loginRequest);

            mockMvc.perform(post("/api/auth/login/CUSTOMER")
                    .contentType("application/json")
                    .content(requestJson))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.token").value("test.jwt.token"));
        }

        @Test
        @DisplayName("POST /api/auth/login/ADMIN - Success")
        void testLoginAdminSuccess() throws Exception {
            LoginRequest loginRequest = new LoginRequest("admin", "adminpassword");

            when(userService.getUserByUsername("admin")).thenReturn(adminUser);
            when(passwordEncoder.matches("adminpassword", "hashedPassword")).thenReturn(true);
            when(jwtService.generateToken(any(UserDto.class))).thenReturn("admin.jwt.token");

            String requestJson = objectMapper.writeValueAsString(loginRequest);

            mockMvc.perform(post("/api/auth/login/ADMIN")
                    .contentType("application/json")
                    .content(requestJson))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.token").value("admin.jwt.token"));
        }

        @Test
        @DisplayName("POST /api/auth/login/CUSTOMER - Invalid Password")
        void testLoginInvalidPassword() throws Exception {
            LoginRequest loginRequest = new LoginRequest("johndoe", "wrongpassword");

            when(userService.getUserByUsername("johndoe")).thenReturn(testUser);
            when(passwordEncoder.matches("wrongpassword", "hashedPassword")).thenReturn(false);

            String requestJson = objectMapper.writeValueAsString(loginRequest);

            Exception exception = assertThrows(Exception.class, () ->
                    mockMvc.perform(post("/api/auth/login/CUSTOMER")
                            .contentType("application/json")
                            .content(requestJson)));

            assertNotNull(exception);
            assertTrue(exception.getCause().getMessage().contains("Invalid username or password"));
        }

        @Test
        @DisplayName("POST /api/auth/login/ADMIN - User is not admin")
        void testLoginUserNotAdmin() throws Exception {
            LoginRequest loginRequest = new LoginRequest("johndoe", "password123");

            when(userService.getUserByUsername("johndoe")).thenReturn(testUser);
            when(passwordEncoder.matches("password123", "hashedPassword")).thenReturn(true);

            String requestJson = objectMapper.writeValueAsString(loginRequest);

            Exception exception = assertThrows(Exception.class, () ->
                    mockMvc.perform(post("/api/auth/login/ADMIN")
                            .contentType("application/json")
                            .content(requestJson)));

            assertNotNull(exception);
            assertTrue(exception.getCause().getMessage().contains("User is not an admin"));
        }
    }

    @Nested
    class ValidateTokenTests {

        @Test
        @DisplayName("GET /api/auth/validate - Success")
        void testValidateTokenSuccess() throws Exception {
            when(jwtService.getUserFromToken("valid.jwt.token")).thenReturn(testUser);

            mockMvc.perform(get("/api/auth/validate")
                    .header("Authorization", "Bearer valid.jwt.token"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("John Doe"))
                    .andExpect(jsonPath("$.username").value("johndoe"));
        }

        @Test
        @DisplayName("GET /api/auth/validate - Invalid Token")
        void testValidateTokenInvalid() throws Exception {
            when(jwtService.getUserFromToken("invalid.token")).thenThrow(new RuntimeException("Invalid token"));

            Exception exception = assertThrows(Exception.class, () ->
                    mockMvc.perform(get("/api/auth/validate")
                            .header("Authorization", "Bearer invalid.token")));

            assertNotNull(exception);
            Throwable cause = exception.getCause();
            assertNotNull(cause);
            assertTrue(cause.getMessage().contains("Invalid token"));
        }
    }
}
