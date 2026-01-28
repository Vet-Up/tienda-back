package es.VetUp.tienda_back.b_domain.mapper;

import es.VetUp.tienda_back.b_domain.model.Product;
import es.VetUp.tienda_back.b_domain.repository.entity.ProductEntity;
import es.VetUp.tienda_back.b_domain.service.dto.ProductDto;

public class ProductMapper {
    private static ProductMapper INSTANCE;

    private ProductMapper() {
    }

    public static ProductMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProductMapper();
        }
        return INSTANCE;
    }

    public Product fromProductEntitytoProduct(ProductEntity productEntity) {
        return new Product(
                productEntity.productId(),
                productEntity.name(),
                productEntity.productDescription(),
                productEntity.basePrice(),
                productEntity.discount(),
                productEntity.pictureProduct(),
                productEntity.brand(),
                productEntity.categoryId(),
                productEntity.stock(),
                null, // averageRating
                null  // reviewsCount
        );
    }

    public ProductEntity fromProducttoProductEntity(Product product) {
        return new ProductEntity(
                product.getProductId(),
                product.getName(),
                product.getProductDescription(),
                product.getBasePrice(),
                product.getDiscount(),
                product.getPictureProduct(),
                product.getBrand(),
                product.getCategoryId(),
                product.getPrice(), 
                product.getStock());
    }

    public Product fromProductDtotoProduct(ProductDto productDto) {
        return new Product(
                productDto.productId(),
                productDto.name(),
                productDto.productDescription(),
                productDto.basePrice(),
                productDto.discount(),
                productDto.pictureProduct(),
                productDto.brand(),
                productDto.categoryId(),
                productDto.stock(),
                productDto.averageRating(),
                productDto.reviewsCount()
        );
    }

    public ProductDto fromProducttoProductDto(Product product) {
        return new ProductDto(
                product.getProductId(),
                product.getName(),
                product.getProductDescription(),
                product.getBasePrice(),
                product.getDiscount(),
                product.getPrice(),
                product.getPictureProduct(),
                product.getBrand(),
                product.getCategoryId(),
                product.getStock(),
                product.getAverageRating(),
                product.getReviewsCount()
        );
    }
}