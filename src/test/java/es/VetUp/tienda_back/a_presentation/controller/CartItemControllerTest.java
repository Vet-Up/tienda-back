package es.VetUp.tienda_back.a_presentation.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
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

import es.VetUp.tienda_back.a_presentation.controller.webModel.request.CartItemInsertRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.CartItemUpdateRequest;
import es.VetUp.tienda_back.b_domain.service.CartItemService;
import es.VetUp.tienda_back.b_domain.service.dto.CartItemDto;
import es.VetUp.tienda_back.b_domain.service.dto.ProductDto;

@WebMvcTest(CartItemController.class)
@AutoConfigureMockMvc(addFilters = false)
class CartItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CartItemService cartItemService;

    @MockitoBean
    private es.VetUp.tienda_back.b_domain.service.JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    private CartItemDto cartItemDto1;
    private CartItemDto cartItemDto2;
    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        productDto = new ProductDto(
                1L,
                "Producto 1",
                "Descripción producto 1",
                new BigDecimal("19.99"),
                new BigDecimal("5.00"),
                new BigDecimal("15.99"),
                "imagen1.jpg",
                "Marca1",
                1L,
                100,
                null,
                null);

        cartItemDto1 = new CartItemDto(
                1L,
                2,
                100L,
                productDto);

        cartItemDto2 = new CartItemDto(
                2L,
                1,
                101L,
                productDto);
    }

    @Nested
    class FindAllCartItemsTests {
        @Test
        @DisplayName("GET /api/cart-items - Success")
        void testFindAllCartItems() throws Exception {
            when(cartItemService.getAllCartItems()).thenReturn(List.of(cartItemDto1, cartItemDto2));

            mockMvc.perform(get("/api/cart-items"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(2))
                    .andExpect(jsonPath("$[0].id").value(1))
                    .andExpect(jsonPath("$[1].id").value(2));
        }
    }

    @Nested
    class GetCartItemByIdTests {
        @Test
        @DisplayName("GET /api/cart-items/{id} - Success")
        void testGetCartItemById() throws Exception {
            when(cartItemService.getCartItemById(1L)).thenReturn(Optional.of(cartItemDto1));

            mockMvc.perform(get("/api/cart-items/{id}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.quantity").value(2));
        }

        @Test
        @DisplayName("GET /api/cart-items/{id} - Not Found")
        void testGetCartItemByIdNotFound() throws Exception {
            when(cartItemService.getCartItemById(999L)).thenReturn(Optional.empty());

            mockMvc.perform(get("/api/cart-items/{id}", 999L))
                    .andExpect(status().isInternalServerError());
        }
    }

    @Nested
    class GetCartItemsByCartIdTests {
        @Test
        @DisplayName("GET /api/cart-items/cart/{cartId} - Success")
        void testGetCartItemsByCartId() throws Exception {
            when(cartItemService.getCartItemsByCartId(100L)).thenReturn(List.of(cartItemDto1));

            mockMvc.perform(get("/api/cart-items/cart/{cartId}", 100L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(1))
                    .andExpect(jsonPath("$[0].id").value(1));
        }
    }

    @Nested
    class CreateCartItemTests {
        @Test
        @DisplayName("POST /api/cart-items - Success")
        void testCreateCartItem() throws Exception {
            CartItemInsertRequest request = new CartItemInsertRequest(2, 100L, 1L);

            when(cartItemService.createCartItem(any(CartItemDto.class))).thenReturn(cartItemDto1);

            mockMvc.perform(post("/api/cart-items")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.quantity").value(2));
        }
    }

    @Nested
    class UpdateCartItemTests {
        @Test
        @DisplayName("PUT /api/cart-items/{id} - Success")
        void testUpdateCartItem() throws Exception {
            CartItemUpdateRequest request = new CartItemUpdateRequest(1L, 5);
            CartItemDto updatedDto = new CartItemDto(1L, 5, 100L, productDto);

            when(cartItemService.updateCartItem(any(CartItemDto.class))).thenReturn(updatedDto);

            mockMvc.perform(put("/api/cart-items/{id}", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.quantity").value(5));
        }
    }

    @Nested
    class DeleteCartItemTests {
        @Test
        @DisplayName("DELETE /api/cart-items/{id} - Success")
        void testDeleteCartItem() throws Exception {
            doNothing().when(cartItemService).deleteCartItem(1L);

            mockMvc.perform(delete("/api/cart-items/{id}", 1L))
                    .andExpect(status().isNoContent());

            verify(cartItemService).deleteCartItem(1L);
        }
    }
}
