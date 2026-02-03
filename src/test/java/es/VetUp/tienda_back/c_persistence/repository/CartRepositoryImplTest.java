package es.VetUp.tienda_back.c_persistence.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.VetUp.tienda_back.b_domain.repository.entity.CartEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.CartJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CartJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.UserJpaEntity;
import es.VetUp.tienda_back.c_persistence.repository.mapper.CartPersistenceMapper;

@ExtendWith(MockitoExtension.class)
class CartRepositoryImplTest {

    @Mock
    private CartJpaDao cartJpaDao;

    @InjectMocks
    private CartRepositoryImpl cartRepositoryImpl;

    CartJpaEntity cartJpaEntity1;
    CartJpaEntity cartJpaEntity2;
    UserJpaEntity userJpaEntity;

    @BeforeEach
    void setUp() {
        userJpaEntity = new UserJpaEntity(
                1L,
                "John Doe",
                "johndoe",
                "johndoe@example.com",
                "password",
                null,
                null,
                null,
                null,
                null,
                null
        );

        cartJpaEntity1 = new CartJpaEntity();
        cartJpaEntity1.setId(1L);
        cartJpaEntity1.setUser(userJpaEntity);

        cartJpaEntity2 = new CartJpaEntity();
        cartJpaEntity2.setId(2L);
        cartJpaEntity2.setUser(userJpaEntity);
    }

    @Nested
    class GetAllCartsTest {
        @Test
        @DisplayName("Get all should return all carts")
        void testGetAllCarts() {
            List<CartJpaEntity> expectedList = List.of(cartJpaEntity1, cartJpaEntity2);
            when(cartJpaDao.getAllCarts()).thenReturn(expectedList);

            List<CartEntity> expectedEntities = expectedList.stream()
                    .map(CartPersistenceMapper.getInstance()::fromCartJpaEntityToCartEntity)
                    .toList();

            List<CartEntity> actualEntities = cartRepositoryImpl.getAllCarts();

            assertAll(
                    () -> assertEquals(expectedEntities.size(), actualEntities.size()),
                    () -> assertEquals(expectedEntities.getFirst().id(), actualEntities.getFirst().id()),
                    () -> assertEquals(expectedEntities.get(1).id(), actualEntities.get(1).id())
            );
        }
    }

    @Nested
    class GetCartByIdTest {
        @Test
        @DisplayName("Get cart by id should return cart")
        void testGetCartById() {
            when(cartJpaDao.getCartById(1L)).thenReturn(Optional.of(cartJpaEntity1));

            CartEntity expectedEntity = CartPersistenceMapper.getInstance()
                    .fromCartJpaEntityToCartEntity(cartJpaEntity1);

            Optional<CartEntity> actualEntity = cartRepositoryImpl.getCartById(1L);

            assertAll(
                    () -> assertTrue(actualEntity.isPresent()),
                    () -> assertEquals(expectedEntity.id(), actualEntity.get().id())
            );
        }

        @Test
        @DisplayName("Get cart by id should return empty when not found")
        void testGetCartByIdNotFound() {
            when(cartJpaDao.getCartById(999L)).thenReturn(Optional.empty());

            Optional<CartEntity> actualEntity = cartRepositoryImpl.getCartById(999L);

            assertTrue(actualEntity.isEmpty());
        }
    }

    @Nested
    class GetCartByUserIdTest {
        @Test
        @DisplayName("Get cart by user id should return cart")
        void testGetCartByUserId() {
            when(cartJpaDao.getCartByUserId(1L)).thenReturn(Optional.of(cartJpaEntity1));

            CartEntity expectedEntity = CartPersistenceMapper.getInstance()
                    .fromCartJpaEntityToCartEntity(cartJpaEntity1);

            Optional<CartEntity> actualEntity = cartRepositoryImpl.getCartByUserId(1L);

            assertAll(
                    () -> assertTrue(actualEntity.isPresent()),
                    () -> assertEquals(expectedEntity.user().id(), actualEntity.get().user().id())
            );
        }

        @Test
        @DisplayName("Get cart by user id should return empty when not found")
        void testGetCartByUserIdNotFound() {
            when(cartJpaDao.getCartByUserId(999L)).thenReturn(Optional.empty());

            Optional<CartEntity> actualEntity = cartRepositoryImpl.getCartByUserId(999L);

            assertTrue(actualEntity.isEmpty());
        }
    }

    @Nested
    class CreateCartTest {
        @Test
        @DisplayName("Create should save cart")
        void testCreateCart() {
            when(cartJpaDao.createCart(any(CartJpaEntity.class))).thenReturn(cartJpaEntity1);

            CartEntity entityToCreate = CartPersistenceMapper.getInstance()
                    .fromCartJpaEntityToCartEntity(cartJpaEntity1);

            CartEntity result = cartRepositoryImpl.createCart(entityToCreate);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(entityToCreate.id(), result.id()),
                    () -> verify(cartJpaDao).createCart(any(CartJpaEntity.class))
            );
        }
    }

    @Nested
    class UpdateCartTest {
        @Test
        @DisplayName("Update should modify cart")
        void testUpdateCart() {
            when(cartJpaDao.updateCart(any(CartJpaEntity.class))).thenReturn(cartJpaEntity1);

            CartEntity entityToUpdate = CartPersistenceMapper.getInstance()
                    .fromCartJpaEntityToCartEntity(cartJpaEntity1);

            CartEntity result = cartRepositoryImpl.updateCart(entityToUpdate);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(entityToUpdate.id(), result.id()),
                    () -> verify(cartJpaDao).updateCart(any(CartJpaEntity.class))
            );
        }
    }

    @Nested
    class DeleteCartTest {
        @Test
        @DisplayName("Delete should remove cart")
        void testDeleteCart() {
            cartRepositoryImpl.deleteCart(1L);

            verify(cartJpaDao).deleteCart(1L);
        }
    }
}

