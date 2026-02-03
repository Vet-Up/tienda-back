package es.VetUp.tienda_back.b_domain.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.VetUp.tienda_back.b_domain.exception.BusinessException;
import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import es.VetUp.tienda_back.b_domain.repository.CartRepository;
import es.VetUp.tienda_back.b_domain.repository.CartItemRepository;
import es.VetUp.tienda_back.b_domain.repository.ProductRepository;
import es.VetUp.tienda_back.b_domain.repository.UserRepository;
import es.VetUp.tienda_back.b_domain.repository.entity.CartEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.UserEntity;
import es.VetUp.tienda_back.b_domain.service.dto.CartDto;
import es.VetUp.tienda_back.b_domain.service.dto.UserDto;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    private CartEntity cartEntity;
    private CartDto cartDto;
    private UserEntity userEntity;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = new UserDto(1L, "Test User", "testuser", "test@test.com", "password",
                "Test Address", UserRole.CUSTOMER, 123456789, "ES", null, LocalDate.of(1990, 1, 1));
        userEntity = new UserEntity(1L, "Test User", "testuser", "test@test.com", "password",
                "Test Address", UserRole.CUSTOMER, 123456789, "ES", null, LocalDate.of(1990, 1, 1));
        cartEntity = new CartEntity(1L, 0, BigDecimal.ZERO, userEntity, new ArrayList<>());
        cartDto = new CartDto(1L, 0, BigDecimal.ZERO, userDto, new ArrayList<>());
    }

    @Test
    @DisplayName("Test get all carts")
    void testGetAllCarts() {
        // Arrange
        when(cartRepository.getAllCarts()).thenReturn(List.of(cartEntity));

        // Act
        List<CartDto> result = cartService.getAllCarts();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.getFirst().id());
        verify(cartRepository, times(1)).getAllCarts();
    }

    @Test
    @DisplayName("Test get all carts - empty list")
    void testGetAllCarts_EmptyList() {
        // Arrange
        when(cartRepository.getAllCarts()).thenReturn(List.of());

        // Act
        List<CartDto> result = cartService.getAllCarts();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(cartRepository, times(1)).getAllCarts();
    }

    @Test
    @DisplayName("Test get cart by id - found")
    void testGetCartById_Found() {
        // Arrange
        when(cartRepository.getCartById(1L)).thenReturn(Optional.of(cartEntity));

        // Act
        Optional<CartDto> result = cartService.getCartById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().id());
        assertEquals("Test User", result.get().user().name());
        verify(cartRepository, times(1)).getCartById(1L);
    }

    @Test
    @DisplayName("Test get cart by id - not found")
    void testGetCartById_NotFound() {
        // Arrange
        when(cartRepository.getCartById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<CartDto> result = cartService.getCartById(999L);

        // Assert
        assertTrue(result.isEmpty());
        verify(cartRepository, times(1)).getCartById(999L);
    }

    @Test
    @DisplayName("Test get cart by user id - found")
    void testGetCartByUserId_Found() {
        // Arrange
        when(cartRepository.getCartByUserId(1L)).thenReturn(Optional.of(cartEntity));

        // Act
        Optional<CartDto> result = cartService.getCartByUserId(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().user().id());
        verify(cartRepository, times(1)).getCartByUserId(1L);
    }

    @Test
    @DisplayName("Test get cart by user id - not found")
    void testGetCartByUserId_NotFound() {
        // Arrange
        when(cartRepository.getCartByUserId(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<CartDto> result = cartService.getCartByUserId(999L);

        // Assert
        assertTrue(result.isEmpty());
        verify(cartRepository, times(1)).getCartByUserId(999L);
    }

    @Test
    @DisplayName("Test create cart")
    void testCreateCart() {
        // Arrange
        CartDto newCartDto = new CartDto(null, 0, BigDecimal.ZERO, userDto, new ArrayList<>());
        CartEntity savedCartEntity = new CartEntity(1L, 0, BigDecimal.ZERO, userEntity, new ArrayList<>());
        when(cartRepository.createCart(any(CartEntity.class))).thenReturn(savedCartEntity);

        // Act
        CartDto result = cartService.createCart(newCartDto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(1L, result.user().id());
        verify(cartRepository, times(1)).createCart(any(CartEntity.class));
    }

    @Test
    @DisplayName("Test update cart - found")
    void testUpdateCart_Found() {
        // Arrange
        when(cartRepository.getCartById(1L)).thenReturn(Optional.of(cartEntity));
        when(cartRepository.updateCart(any(CartEntity.class))).thenReturn(cartEntity);

        // Act
        CartDto result = cartService.updateCart(cartDto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        verify(cartRepository, times(1)).getCartById(1L);
        verify(cartRepository, times(1)).updateCart(any(CartEntity.class));
    }

    @Test
    @DisplayName("Test update cart - not found")
    void testUpdateCart_NotFound() {
        // Arrange
        when(cartRepository.getCartById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BusinessException.class, () -> cartService.updateCart(cartDto));
        verify(cartRepository, times(1)).getCartById(1L);
        verify(cartRepository, never()).updateCart(any(CartEntity.class));
    }

    @Test
    @DisplayName("Test delete cart - found")
    void testDeleteCart_Found() {
        // Arrange
        when(cartRepository.getCartById(1L)).thenReturn(Optional.of(cartEntity));
        doNothing().when(cartRepository).deleteCart(1L);

        // Act
        cartService.deleteCart(1L);

        // Assert
        verify(cartRepository, times(1)).getCartById(1L);
        verify(cartRepository, times(1)).deleteCart(1L);
    }

    @Test
    @DisplayName("Test delete cart - not found")
    void testDeleteCart_NotFound() {
        // Arrange
        when(cartRepository.getCartById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BusinessException.class, () -> cartService.deleteCart(999L));
        verify(cartRepository, times(1)).getCartById(999L);
        verify(cartRepository, never()).deleteCart(anyLong());
    }
}

