package es.VetUp.tienda_back.b_domain.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.VetUp.tienda_back.b_domain.repository.CartItemRepository;
import es.VetUp.tienda_back.b_domain.repository.CartRepository;
import es.VetUp.tienda_back.b_domain.repository.ProductRepository;
import es.VetUp.tienda_back.b_domain.repository.entity.CartEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.CartItemEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.ProductEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.UserEntity;
import es.VetUp.tienda_back.b_domain.service.dto.CartItemDto;
import es.VetUp.tienda_back.b_domain.service.dto.ProductDto;
import es.VetUp.tienda_back.b_domain.model.enums.UserRole;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class CartItemServiceImplTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartItemServiceImpl cartItemServiceImpl;

    @Nested
    class GetAllCartItemsTests {
        @Test
        @DisplayName("getAllCartItems should return list of CartItemDto")
        void testGetAllCartItems() {
            ProductEntity product1 = new ProductEntity(10L, "Product 1", "Description", BigDecimal.valueOf(99.99),
                    BigDecimal.ZERO, "pic1.jpg", "Brand1", 1L, BigDecimal.valueOf(99.99), 10);
            ProductEntity product2 = new ProductEntity(11L, "Product 2", "Description", BigDecimal.valueOf(49.99),
                    BigDecimal.ZERO, "pic2.jpg", "Brand2", 1L, BigDecimal.valueOf(49.99), 5);

            CartItemEntity entity1 = new CartItemEntity(1L, 2, 1L, product1);
            CartItemEntity entity2 = new CartItemEntity(2L, 1, 1L, product2);

            when(cartItemRepository.getAllCartItems()).thenReturn(List.of(entity1, entity2));

            List<CartItemDto> result = cartItemServiceImpl.getAllCartItems();

            assertAll("result",
                    () -> assertEquals(2, result.size()),
                    () -> assertEquals(1L, result.get(0).id()),
                    () -> assertEquals(2L, result.get(1).id())
            );
        }

        @Test
        @DisplayName("getAllCartItems should return empty list when no items")
        void testGetAllCartItemsEmpty() {
            when(cartItemRepository.getAllCartItems()).thenReturn(List.of());

            List<CartItemDto> result = cartItemServiceImpl.getAllCartItems();

            assertTrue(result.isEmpty());
        }
    }

    @Nested
    class GetCartItemByIdTests {
        @Test
        @DisplayName("getCartItemById should return CartItemDto when found")
        void testGetCartItemById() {
            Long itemId = 1L;
            ProductEntity product = new ProductEntity(10L, "Product 1", "Description", BigDecimal.valueOf(99.99),
                    BigDecimal.ZERO, "pic1.jpg", "Brand1", 1L, BigDecimal.valueOf(99.99), 10);
            CartItemEntity entity = new CartItemEntity(itemId, 2, 1L, product);

            when(cartItemRepository.getCartItemById(itemId)).thenReturn(Optional.of(entity));

            Optional<CartItemDto> result = cartItemServiceImpl.getCartItemById(itemId);

            assertAll("result",
                    () -> assertTrue(result.isPresent()),
                    () -> assertEquals(itemId, result.get().id()),
                    () -> assertEquals(2, result.get().quantity())
            );
        }

        @Test
        @DisplayName("getCartItemById should return empty when not found")
        void testGetCartItemByIdNotFound() {
            Long itemId = 999L;
            when(cartItemRepository.getCartItemById(itemId)).thenReturn(Optional.empty());

            Optional<CartItemDto> result = cartItemServiceImpl.getCartItemById(itemId);

            assertFalse(result.isPresent());
        }
    }

    @Nested
    class GetCartItemsByCartIdTests {
        @Test
        @DisplayName("getCartItemsByCartId should return list of items")
        void testGetCartItemsByCartId() {
            Long cartId = 1L;
            ProductEntity product1 = new ProductEntity(10L, "Product 1", "Description", BigDecimal.valueOf(99.99),
                    BigDecimal.ZERO, "pic1.jpg", "Brand1", 1L, BigDecimal.valueOf(99.99), 10);
            ProductEntity product2 = new ProductEntity(11L, "Product 2", "Description", BigDecimal.valueOf(49.99),
                    BigDecimal.ZERO, "pic2.jpg", "Brand2", 1L, BigDecimal.valueOf(49.99), 5);

            CartItemEntity entity1 = new CartItemEntity(1L, 2, cartId, product1);
            CartItemEntity entity2 = new CartItemEntity(2L, 1, cartId, product2);

            when(cartItemRepository.getCartItemsByCartId(cartId)).thenReturn(List.of(entity1, entity2));

            List<CartItemDto> result = cartItemServiceImpl.getCartItemsByCartId(cartId);

            assertAll("result",
                    () -> assertEquals(2, result.size()),
                    () -> assertEquals(1L, result.get(0).id()),
                    () -> assertEquals(2L, result.get(1).id())
            );
        }

        @Test
        @DisplayName("getCartItemsByCartId should return empty list when no items")
        void testGetCartItemsByCartIdEmpty() {
            Long cartId = 999L;
            when(cartItemRepository.getCartItemsByCartId(cartId)).thenReturn(List.of());

            List<CartItemDto> result = cartItemServiceImpl.getCartItemsByCartId(cartId);

            assertTrue(result.isEmpty());
        }
    }

    @Nested
    class CreateCartItemTests {
        @Test
        @DisplayName("createCartItem should create and return CartItemDto")
        void testCreateCartItem() {
            ProductDto productDto = new ProductDto(10L, "Product 1", "Description", BigDecimal.valueOf(99.99),
                    BigDecimal.ZERO, BigDecimal.valueOf(99.99), "pic1.jpg", "Brand1", 1L, 10, 0.0, 0);
            CartItemDto dtoToCreate = new CartItemDto(null, 2, 1L, productDto);

            ProductEntity productEntity = new ProductEntity(10L, "Product 1", "Description", BigDecimal.valueOf(99.99),
                    BigDecimal.ZERO, "pic1.jpg", "Brand1", 1L, BigDecimal.valueOf(99.99), 10);
            CartItemEntity createdEntity = new CartItemEntity(1L, 2, 1L, productEntity);

            UserEntity userEntity = new UserEntity(1L, "Test User", "testuser", "test@test.com", "password",
                    "Test Address", UserRole.CUSTOMER, 123456789, "ES", null, LocalDate.of(1990, 1, 1));
            CartEntity cartEntity = new CartEntity(1L, 0, BigDecimal.ZERO, userEntity, List.of());
            CartEntity updatedCartEntity = new CartEntity(1L, 2, BigDecimal.valueOf(199.98), userEntity, List.of(createdEntity));

            when(cartItemRepository.createCartItem(any(CartItemEntity.class))).thenReturn(createdEntity);
            when(cartItemRepository.getCartItemsByCartId(1L)).thenReturn(List.of(createdEntity));
            when(cartRepository.getCartById(1L)).thenReturn(Optional.of(cartEntity));
            when(cartRepository.updateCart(any(CartEntity.class))).thenReturn(updatedCartEntity);

            CartItemDto result = cartItemServiceImpl.createCartItem(dtoToCreate);

            assertAll("result",
                    () -> assertNotNull(result),
                    () -> assertEquals(1L, result.id()),
                    () -> assertEquals(2, result.quantity()),
                    () -> verify(cartItemRepository).createCartItem(any(CartItemEntity.class)),
                    () -> verify(cartRepository).getCartById(1L),
                    () -> verify(cartRepository).updateCart(any(CartEntity.class))
            );
        }
    }

    @Nested
    class UpdateCartItemTests {
        @Test
        @DisplayName("updateCartItem should update and return CartItemDto")
        void testUpdateCartItem() {
            Long itemId = 1L;
            ProductEntity productEntity = new ProductEntity(10L, "Product 1", "Description", BigDecimal.valueOf(99.99),
                    BigDecimal.ZERO, "pic1.jpg", "Brand1", 1L, BigDecimal.valueOf(99.99), 10);
            ProductDto productDto = new ProductDto(10L, "Product 1", "Description", BigDecimal.valueOf(99.99),
                    BigDecimal.ZERO, BigDecimal.valueOf(99.99), "pic1.jpg", "Brand1", 1L, 10, 0.0, 0);

            CartItemDto dtoToUpdate = new CartItemDto(itemId, 3, 1L, productDto);
            CartItemEntity existingEntity = new CartItemEntity(itemId, 2, 1L, productEntity);
            CartItemEntity updatedEntity = new CartItemEntity(itemId, 3, 1L, productEntity);

            UserEntity userEntity = new UserEntity(1L, "Test User", "testuser", "test@test.com", "password",
                    "Test Address", UserRole.CUSTOMER, 123456789, "ES", null, LocalDate.of(1990, 1, 1));
            CartEntity cartEntity = new CartEntity(1L, 0, BigDecimal.ZERO, userEntity, List.of());
            CartEntity updatedCartEntity = new CartEntity(1L, 3, BigDecimal.valueOf(299.97), userEntity, List.of(updatedEntity));

            when(cartItemRepository.getCartItemById(itemId)).thenReturn(Optional.of(existingEntity));
            when(cartItemRepository.updateCartItem(any(CartItemEntity.class))).thenReturn(updatedEntity);
            when(cartItemRepository.getCartItemsByCartId(1L)).thenReturn(List.of(updatedEntity));
            when(cartRepository.getCartById(1L)).thenReturn(Optional.of(cartEntity));
            when(cartRepository.updateCart(any(CartEntity.class))).thenReturn(updatedCartEntity);

            CartItemDto result = cartItemServiceImpl.updateCartItem(dtoToUpdate);

            assertAll("result",
                    () -> assertNotNull(result),
                    () -> assertEquals(itemId, result.id()),
                    () -> assertEquals(3, result.quantity()),
                    () -> verify(cartItemRepository).updateCartItem(any(CartItemEntity.class)),
                    () -> verify(cartRepository).getCartById(1L),
                    () -> verify(cartRepository).updateCart(any(CartEntity.class))
            );
        }
    }

    @Nested
    class DeleteCartItemTests {
        @Test
        @DisplayName("deleteCartItem should delete item")
        void testDeleteCartItem() {
            Long itemId = 1L;
            ProductEntity productEntity = new ProductEntity(10L, "Product 1", "Description", BigDecimal.valueOf(99.99),
                    BigDecimal.ZERO, "pic1.jpg", "Brand1", 1L, BigDecimal.valueOf(99.99), 10);
            CartItemEntity existingEntity = new CartItemEntity(itemId, 2, 1L, productEntity);

            UserEntity userEntity = new UserEntity(1L, "Test User", "testuser", "test@test.com", "password",
                    "Test Address", UserRole.CUSTOMER, 123456789, "ES", null, LocalDate.of(1990, 1, 1));
            CartEntity cartEntity = new CartEntity(1L, 2, BigDecimal.valueOf(199.98), userEntity, List.of(existingEntity));
            CartEntity updatedCartEntity = new CartEntity(1L, 0, BigDecimal.ZERO, userEntity, List.of());

            when(cartItemRepository.getCartItemById(itemId)).thenReturn(Optional.of(existingEntity));
            when(cartItemRepository.getCartItemsByCartId(1L)).thenReturn(List.of());
            when(cartRepository.getCartById(1L)).thenReturn(Optional.of(cartEntity));
            when(cartRepository.updateCart(any(CartEntity.class))).thenReturn(updatedCartEntity);

            cartItemServiceImpl.deleteCartItem(itemId);

            verify(cartItemRepository).deleteCartItem(itemId);
            verify(cartRepository).getCartById(1L);
            verify(cartRepository).updateCart(any(CartEntity.class));
        }
    }
}

