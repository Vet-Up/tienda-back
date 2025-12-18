package es.VetUp.tienda_back.c_persistence.repository.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.VetUp.tienda_back.b_domain.repository.entity.ProductEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.ProductJpaEntity;

class ProductPersistenceMapperTest {
    @Test
    @DisplayName("Test map from ProductJpaEntity to ProductEntity")
    void testFromProductJpaEntitytoToProductEntity() {
        // Arrange
        ProductJpaEntity jpaEntity = new ProductJpaEntity();
        jpaEntity.setProductId(1L);
        jpaEntity.setName("Producto 1");
        jpaEntity.setProductDescription("Descripción producto 1");
        jpaEntity.setPrice(new BigDecimal("19.99"));
        jpaEntity.setDiscountedPrice(new BigDecimal("15.99"));
        jpaEntity.setPictureProduct("imagen1.jpg");
        jpaEntity.setBrand("Marca1");
        jpaEntity.setCategoryId(1L);

        // Act
        ProductEntity entity = ProductPersistenceMapper.getInstance().fromProductJpaEntitytoToProductEntity(jpaEntity);

        // Assert
        assertEquals(jpaEntity.getProductId(), entity.productId());
        assertEquals(jpaEntity.getName(), entity.name());
        assertEquals(jpaEntity.getProductDescription(), entity.productDescription());
        assertEquals(jpaEntity.getPrice(), entity.price());
        assertEquals(jpaEntity.getDiscountedPrice(), entity.discountedPrice());
        assertEquals(jpaEntity.getPictureProduct(), entity.pictureProduct());
        assertEquals(jpaEntity.getBrand(), entity.brand());
        assertEquals(jpaEntity.getCategoryId(), entity.categoryId());
    }

    @Test
    @DisplayName("Test map from ProductEntity to ProductJpaEntity")
    void testFromProductEntitytoToProductJpaEntity() {
        // Arrange
        ProductEntity entity = new ProductEntity(
                2L,
                "Producto 2",
                "Descripción producto 2",
                new BigDecimal("29.99"),
                new BigDecimal("25.99"),
                "imagen2.jpg",
                "Marca2",
                2L);

        // Act
        ProductJpaEntity jpaEntity = ProductPersistenceMapper.getInstance().fromProductEntitytoToProductJpaEntity(entity);

        // Assert
        assertEquals(entity.productId(), jpaEntity.getProductId());
        assertEquals(entity.name(), jpaEntity.getName());
        assertEquals(entity.productDescription(), jpaEntity.getProductDescription());
        assertEquals(entity.price(), jpaEntity.getPrice());
        assertEquals(entity.discountedPrice(), jpaEntity.getDiscountedPrice());
        assertEquals(entity.pictureProduct(), jpaEntity.getPictureProduct());
        assertEquals(entity.brand(), jpaEntity.getBrand());
        assertEquals(entity.categoryId(), jpaEntity.getCategoryId());
    }

}