package es.VetUp.tienda_back.c_persistence.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
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

import es.VetUp.tienda_back.b_domain.repository.entity.CartItemEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.CartItemJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CartItemJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CartJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.ProductJpaEntity;
import es.VetUp.tienda_back.c_persistence.repository.mapper.CartItemPersistenceMapper;

@ExtendWith(MockitoExtension.class)
class CartItemRepositoryImplTest {

    @Mock
    private CartItemJpaDao cartItemJpaDao;

    @InjectMocks
    private CartItemRepositoryImpl cartItemRepositoryImpl;

    CartItemJpaEntity cartItemJpaEntity1;
    CartItemJpaEntity cartItemJpaEntity2;
    CartJpaEntity cartJpaEntity;
    ProductJpaEntity productJpaEntity;

    @BeforeEach
    void setUp() {
        cartJpaEntity = new CartJpaEntity();
        cartJpaEntity.setId(1L);

        productJpaEntity = new ProductJpaEntity();
        productJpaEntity.setProductId(10L);
        productJpaEntity.setName("Test Product");
        productJpaEntity.setPrice(BigDecimal.valueOf(99.99));

        cartItemJpaEntity1 = new CartItemJpaEntity();
        cartItemJpaEntity1.setId(1L);
        cartItemJpaEntity1.setCart(cartJpaEntity);
        cartItemJpaEntity1.setProduct(productJpaEntity);
        cartItemJpaEntity1.setQuantity(2);

        cartItemJpaEntity2 = new CartItemJpaEntity();
        cartItemJpaEntity2.setId(2L);
        cartItemJpaEntity2.setCart(cartJpaEntity);
        cartItemJpaEntity2.setProduct(productJpaEntity);
        cartItemJpaEntity2.setQuantity(1);
    }

    @Nested
    class FindAllCartItemsTest {
        @Test
        @DisplayName("Find all should return all cart items")
        void testFindAllCartItems() {
            List<CartItemJpaEntity> expectedList = List.of(cartItemJpaEntity1, cartItemJpaEntity2);
            when(cartItemJpaDao.getAllCartItems()).thenReturn(expectedList);

            List<CartItemEntity> expectedEntities = expectedList.stream()
                    .map(CartItemPersistenceMapper.getInstance()::fromCartItemJpaEntityToCartItemEntity)
                    .toList();

            List<CartItemEntity> actualEntities = cartItemRepositoryImpl.getAllCartItems();

            assertAll(
                    () -> assertEquals(expectedEntities.size(), actualEntities.size()),
                    () -> assertEquals(expectedEntities.getFirst().id(), actualEntities.getFirst().id()),
                    () -> assertEquals(expectedEntities.get(1).id(), actualEntities.get(1).id())
            );
        }
    }

    @Nested
    class FindByIdTest {
        @Test
        @DisplayName("Find by id should return cart item")
        void testFindById() {
            when(cartItemJpaDao.getCartItemById(1L)).thenReturn(Optional.of(cartItemJpaEntity1));

            Optional<CartItemEntity> actualEntity = cartItemRepositoryImpl.getCartItemById(1L);

            CartItemEntity expectedEntity = CartItemPersistenceMapper.getInstance()
                    .fromCartItemJpaEntityToCartItemEntity(cartItemJpaEntity1);

            assertAll(
                    () -> assertEquals(true, actualEntity.isPresent()),
                    () -> assertEquals(expectedEntity, actualEntity.get())
            );
        }

        @Test
        @DisplayName("Find by id should return empty when not found")
        void testFindByIdNotFound() {
            when(cartItemJpaDao.getCartItemById(999L)).thenReturn(Optional.empty());

            Optional<CartItemEntity> actualEntity = cartItemRepositoryImpl.getCartItemById(999L);

            assertTrue(actualEntity.isEmpty());
        }
    }

    @Nested
    class FindByCartIdTest {
        @Test
        @DisplayName("Find by cart id should return cart items")
        void testFindByCartId() {
            List<CartItemJpaEntity> expectedList = List.of(cartItemJpaEntity1, cartItemJpaEntity2);
            when(cartItemJpaDao.getCartItemsByCartId(1L)).thenReturn(expectedList);

            List<CartItemEntity> expectedEntities = expectedList.stream()
                    .map(CartItemPersistenceMapper.getInstance()::fromCartItemJpaEntityToCartItemEntity)
                    .toList();

            List<CartItemEntity> actualEntities = cartItemRepositoryImpl.getCartItemsByCartId(1L);

            assertAll(
                    () -> assertEquals(expectedEntities.size(), actualEntities.size()),
                    () -> assertEquals(expectedEntities.getFirst().cartId(), actualEntities.getFirst().cartId())
            );
        }
    }

    @Nested
    class InsertTest {
        @Test
        @DisplayName("Insert should save cart item")
        void testInsert() {
            when(cartItemJpaDao.createCartItem(any(CartItemJpaEntity.class))).thenReturn(cartItemJpaEntity1);

            CartItemEntity entityToInsert = CartItemPersistenceMapper.getInstance()
                    .fromCartItemJpaEntityToCartItemEntity(cartItemJpaEntity1);

            CartItemEntity result = cartItemRepositoryImpl.createCartItem(entityToInsert);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(entityToInsert.id(), result.id()),
                    () -> verify(cartItemJpaDao).createCartItem(any(CartItemJpaEntity.class))
            );
        }
    }

    @Nested
    class UpdateTest {
        @Test
        @DisplayName("Update should modify cart item")
        void testUpdate() {
            when(cartItemJpaDao.updateCartItem(any(CartItemJpaEntity.class))).thenReturn(cartItemJpaEntity1);

            CartItemEntity entityToUpdate = CartItemPersistenceMapper.getInstance()
                    .fromCartItemJpaEntityToCartItemEntity(cartItemJpaEntity1);

            CartItemEntity result = cartItemRepositoryImpl.updateCartItem(entityToUpdate);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(entityToUpdate.id(), result.id()),
                    () -> verify(cartItemJpaDao).updateCartItem(any(CartItemJpaEntity.class))
            );
        }
    }

    @Nested
    class DeleteByIdTest {
        @Test
        @DisplayName("Delete by id should remove cart item")
        void testDeleteById() {
            cartItemRepositoryImpl.deleteCartItem(1L);

            verify(cartItemJpaDao).deleteCartItem(1L);
        }
    }
}

