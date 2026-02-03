package es.VetUp.tienda_back.a_presentation.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import es.VetUp.tienda_back.b_domain.service.CartService;
import es.VetUp.tienda_back.b_domain.service.JwtService;
import es.VetUp.tienda_back.b_domain.service.dto.CartDto;
import es.VetUp.tienda_back.b_domain.service.dto.UserDto;

@WebMvcTest(CartController.class)
@AutoConfigureMockMvc(addFilters = false)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CartService cartService;

    @MockitoBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    private CartDto cartDto1;
    private CartDto cartDto2;
    private UserDto userDto1;
    private UserDto userDto2;

    @BeforeEach
    void setUp() {
        userDto1 = new UserDto(
                1L,
                "John Doe",
                "johndoe",
                "john@example.com",
                "password123",
                "123 Main St",
                UserRole.CUSTOMER,
                123456789,
                "USA",
                "profile1.jpg",
                LocalDate.of(1990, 1, 1)
        );

        userDto2 = new UserDto(
                2L,
                "Jane Smith",
                "janesmith",
                "jane@example.com",
                "password456",
                "456 Oak Ave",
                UserRole.CUSTOMER,
                987654321,
                "Canada",
                "profile2.jpg",
                LocalDate.of(1995, 5, 15)
        );

        cartDto1 = new CartDto(
                1L,
                0,
                BigDecimal.ZERO,
                userDto1,
                new ArrayList<>()
        );

        cartDto2 = new CartDto(
                2L,
                0,
                BigDecimal.ZERO,
                userDto2,
                new ArrayList<>()
        );
    }

    @Nested
    class GetAllCartsTests {
        @Test
        @DisplayName("GET /api/carts - Success")
        void testGetAllCartsSuccess() throws Exception {
            when(cartService.getAllCarts()).thenReturn(List.of(cartDto1, cartDto2));

            mockMvc.perform(get("/api/carts"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(2))
                    .andExpect(jsonPath("$[0].id").value(1))
                    .andExpect(jsonPath("$[0].user.id").value(1))
                    .andExpect(jsonPath("$[1].id").value(2))
                    .andExpect(jsonPath("$[1].user.id").value(2));
        }
    }

    @Nested
    class GetCartByIdTests {
        @Test
        @DisplayName("GET /api/carts/{id} - Success")
        void testGetCartByIdSuccess() throws Exception {
            when(cartService.getCartById(1L)).thenReturn(Optional.of(cartDto1));

            mockMvc.perform(get("/api/carts/{id}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.user.id").value(1));
        }

        @Test
        @DisplayName("GET /api/carts/{id} - Not Found")
        void testGetCartByIdNotFound() throws Exception {
            when(cartService.getCartById(999L)).thenReturn(Optional.empty());

            mockMvc.perform(get("/api/carts/{id}", 999L))
                    .andExpect(status().isInternalServerError());
        }
    }

    @Nested
    class GetCartByUserIdTests {
        @Test
        @DisplayName("GET /api/carts/user/{userId} - Success")
        void testGetCartByUserIdSuccess() throws Exception {
            when(cartService.getCartByUserId(1L)).thenReturn(Optional.of(cartDto1));

            mockMvc.perform(get("/api/carts/user/{userId}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.user.id").value(1));
        }

        @Test
        @DisplayName("GET /api/carts/user/{userId} - Not Found")
        void testGetCartByUserIdNotFound() throws Exception {
            when(cartService.getCartByUserId(999L)).thenReturn(Optional.empty());

            mockMvc.perform(get("/api/carts/user/{userId}", 999L))
                    .andExpect(status().isInternalServerError());
        }
    }

    @Nested
    class CreateCartTests {
        @Test
        @DisplayName("POST /api/carts - Success")
        void testCreateCartSuccess() throws Exception {
            CartDto newCart = new CartDto(null, 0, BigDecimal.ZERO, userDto1, new ArrayList<>());
            when(cartService.createCart(any(CartDto.class))).thenReturn(cartDto1);

            mockMvc.perform(post("/api/carts")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(newCart)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.user.id").value(1));
        }
    }

    @Nested
    class UpdateCartTests {
        @Test
        @DisplayName("PUT /api/carts/{id} - Success")
        void testUpdateCartSuccess() throws Exception {
            CartDto updatedCart = new CartDto(1L, 2, new BigDecimal("50.00"), userDto2, new ArrayList<>());
            when(cartService.updateCart(any(CartDto.class))).thenReturn(updatedCart);

            mockMvc.perform(put("/api/carts/{id}", 1L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updatedCart)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.user.id").value(2));
        }
    }

    @Nested
    class DeleteCartTests {
        @Test
        @DisplayName("DELETE /api/carts/{id} - Success")
        void testDeleteCartSuccess() throws Exception {
            doNothing().when(cartService).deleteCart(1L);

            mockMvc.perform(delete("/api/carts/{id}", 1L))
                    .andExpect(status().isNoContent());

            verify(cartService).deleteCart(1L);
        }
    }
}

