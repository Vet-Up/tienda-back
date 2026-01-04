package es.VetUp.tienda_back.a_presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.UserInsertRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.UserUpdateRequest;
import es.VetUp.tienda_back.b_domain.model.Page;
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
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtService jwtService;

    private UserDto userDto1;
    private UserDto userDto2;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        userDto1 = new UserDto(
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

        userDto2 = new UserDto(
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

    @Test
    @DisplayName("GET /api/users - Success")
    void testGetAllUsersSuccess() throws Exception {
        Page<UserDto> userPage = new Page<>(List.of(userDto1, userDto2), 1, 10, 2L);
        when(userService.getAllUsers(1, 10)).thenReturn(userPage);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].name").value("John Doe"))
                .andExpect(jsonPath("$.data[1].name").value("Jane Smith"))
                .andExpect(jsonPath("$.pageNumber").value(1))
                .andExpect(jsonPath("$.pageSize").value(10))
                .andExpect(jsonPath("$.totalElements").value(2));
    }

    @Test
    @DisplayName("GET /api/users - Empty List")
    void testGetAllUsersEmpty() throws Exception {
        Page<UserDto> emptyPage = new Page<>(List.of(), 1, 10, 0L);
        when(userService.getAllUsers(1, 10)).thenReturn(emptyPage);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(0));
    }

    @Nested
    class GetByIdTests {
        @Test
        @DisplayName("GET /api/users/{id} - Success")
        void testGetUserByIdSuccess() throws Exception {
            when(userService.getUserById(1L)).thenReturn(Optional.of(userDto1));

            mockMvc.perform(get("/api/users/{id}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("John Doe"))
                    .andExpect(jsonPath("$.username").value("johndoe"))
                    .andExpect(jsonPath("$.email").value("john@example.com"))
                    .andExpect(jsonPath("$.address").value("123 Main St"))
                    .andExpect(jsonPath("$.isAdmin").value("CUSTOMER"))
                    .andExpect(jsonPath("$.phone").value(123456789))
                    .andExpect(jsonPath("$.country").value("Spain"));
        }

        @Test
        @DisplayName("GET /api/users/{id} - Not Found")
        void testGetUserByIdNotFound() throws Exception {
            when(userService.getUserById(99L)).thenReturn(Optional.empty());

            Exception exception = assertThrows(Exception.class, () ->
                    mockMvc.perform(get("/api/users/{id}", 99L)));

            assertNotNull(exception);
            Throwable cause = exception.getCause();
            assertNotNull(cause);
            assertTrue(cause.getMessage().contains("User not found"));
        }
    }

    @Nested
    class GetByEmailTests {
        @Test
        @DisplayName("GET /api/users/by-email - Success")
        void testGetUserByEmailSuccess() throws Exception {
            when(userService.getUserByEmail("john@example.com")).thenReturn(Optional.of(userDto1));

            mockMvc.perform(get("/api/users/by-email")
                    .param("email", "john@example.com"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("John Doe"))
                    .andExpect(jsonPath("$.email").value("john@example.com"));
        }

        @Test
        @DisplayName("GET /api/users/by-email - Not Found")
        void testGetUserByEmailNotFound() throws Exception {
            when(userService.getUserByEmail("notfound@example.com")).thenReturn(Optional.empty());

            Exception exception = assertThrows(Exception.class, () ->
                    mockMvc.perform(get("/api/users/by-email")
                            .param("email", "notfound@example.com")));

            assertTrue(exception.getCause().getMessage().contains("User not found"));
        }
    }

    @Test
    @DisplayName("POST /api/users - Success")
    void testCreateUserSuccess() throws Exception {
        UserInsertRequest insertRequest = new UserInsertRequest(
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

        UserDto createdUserDto = new UserDto(
                3L,
                "New User",
                "newuser",
                "newuser@example.com",
                "hashedpassword",
                "789 New St",
                UserRole.CUSTOMER,
                555555555,
                "France",
                "newprofile.jpg",
                LocalDate.of(1995, 3, 10));

        when(userService.createUser(any(UserDto.class))).thenReturn(createdUserDto);

        String insertRequestJson = objectMapper.writeValueAsString(insertRequest);

        mockMvc.perform(post("/api/users")
                .contentType("application/json")
                .content(insertRequestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("New User"))
                .andExpect(jsonPath("$.username").value("newuser"));
    }

    @Test
    @DisplayName("PUT /api/users/{id} - Success")
    void testUpdateUserSuccess() throws Exception {
        UserUpdateRequest updateRequest = new UserUpdateRequest(
                1L,
                "John Doe Updated",
                "johndoe",
                "john@example.com",
                "newpassword",
                "456 Updated St",
                UserRole.CUSTOMER,
                123456789,
                "Spain",
                "updated_profile.jpg",
                LocalDate.of(1990, 5, 15));

        UserDto updatedUserDto = new UserDto(
                1L,
                "John Doe Updated",
                "johndoe",
                "john@example.com",
                "hashedpassword",
                "456 Updated St",
                UserRole.CUSTOMER,
                123456789,
                "Spain",
                "updated_profile.jpg",
                LocalDate.of(1990, 5, 15));

        when(userService.updateUser(any(UserDto.class))).thenReturn(updatedUserDto);

        String updateRequestJson = objectMapper.writeValueAsString(updateRequest);

        mockMvc.perform(put("/api/users/{id}", 1L)
                .contentType("application/json")
                .content(updateRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe Updated"))
                .andExpect(jsonPath("$.address").value("456 Updated St"));
    }

    @Test
    @DisplayName("PUT /api/users/{id} - ID Mismatch")
    void testUpdateUserIdMismatch() throws Exception {
        UserUpdateRequest updateRequest = new UserUpdateRequest(
                2L,
                "John Doe Updated",
                "johndoe",
                "john@example.com",
                "newpassword",
                "456 Updated St",
                UserRole.CUSTOMER,
                123456789,
                "Spain",
                "updated_profile.jpg",
                LocalDate.of(1990, 5, 15));

        String updateRequestJson = objectMapper.writeValueAsString(updateRequest);

        Exception exception = assertThrows(Exception.class, () ->
                mockMvc.perform(put("/api/users/{id}", 1L)
                        .contentType("application/json")
                        .content(updateRequestJson)));

        assertTrue(exception.getCause().getMessage().contains("ID in path and request body must match"));
    }

    @Test
    @DisplayName("DELETE /api/users/{id} - Success")
    void testDeleteUserSuccess() throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/users/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
