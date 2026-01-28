package es.VetUp.tienda_back.c_persistence.repository.mapper;

import es.VetUp.tienda_back.b_domain.repository.entity.ProductEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.ProductJpaEntity;

import java.math.BigDecimal;

public class ProductPersistenceMapper {
    private static ProductPersistenceMapper INSTANCE;

    private ProductPersistenceMapper() {
    }

    public static ProductPersistenceMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProductPersistenceMapper();
        }
        return INSTANCE;
    }

    public ProductEntity fromProductJpaEntitytoToProductEntity(ProductJpaEntity productJpaEntity) {
        if (productJpaEntity == null) {
            return null;
        }

        BigDecimal basePrice = productJpaEntity.getBasePrice();
        BigDecimal price = productJpaEntity.getPrice();
        BigDecimal discount = BigDecimal.ZERO;

        if (basePrice != null && basePrice.compareTo(BigDecimal.ZERO) > 0 && price != null) {
            discount = basePrice.subtract(price)
                    .divide(basePrice, 2, java.math.RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }

        return new ProductEntity(
                productJpaEntity.getProductId(),
                productJpaEntity.getName(),
                productJpaEntity.getProductDescription(),
                basePrice,
                discount,
                productJpaEntity.getPictureProduct(),
                productJpaEntity.getBrand(),
                productJpaEntity.getCategoryId(),
                price,
                productJpaEntity.getStock());
    }

    public ProductJpaEntity fromProductEntitytoToProductJpaEntity(ProductEntity productEntity) {
        if (productEntity == null) {
            return null;
        }

        CategoryJpaEntity categoryJpaEntity = null;
        if (productEntity.categoryId() != null) {
            categoryJpaEntity = new CategoryJpaEntity();
            categoryJpaEntity.setCategoryId(productEntity.categoryId());
        }

        return new ProductJpaEntity(
                productEntity.productId(),
                productEntity.name(),
                productEntity.productDescription(),
                productEntity.basePrice(),
                productEntity.price(),
                productEntity.pictureProduct(),
                productEntity.brand(),
                categoryJpaEntity,
                productEntity.stock());
    }
}
