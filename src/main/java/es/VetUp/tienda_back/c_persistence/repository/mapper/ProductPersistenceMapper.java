package es.VetUp.tienda_back.c_persistence.repository.mapper;

import es.VetUp.tienda_back.b_domain.repository.entity.ProductEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.ProductJpaEntity;

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
        if(productJpaEntity == null) {
            return null;
        }

        return new ProductEntity(
                productJpaEntity.getProduct_id(),
                productJpaEntity.getName(),
                productJpaEntity.getProductDescription(),
                productJpaEntity.getPrice(),
                productJpaEntity.getDiscountedPrice(),
                productJpaEntity.getPictureProduct(),
                productJpaEntity.getBrand(),
                productJpaEntity.getCategoryId()
        );
    }

    public ProductJpaEntity fromProductEntitytoToProductJpaEntity(ProductEntity productEntity) {
        if(productEntity == null) {
            return null;
        }

        return new ProductJpaEntity(
                productEntity.productId(),
                productEntity.name(),
                productEntity.productDescription(),
                productEntity.price(),
                productEntity.discountedPrice(),
                productEntity.pictureProduct(),
                productEntity.brand(),
                productEntity.categoryId()
        );
    }
}



