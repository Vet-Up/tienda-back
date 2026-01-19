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
                productEntity.discountedPrice(),
                null, // price is calculated in Product
                productEntity.pictureProduct(),
                productEntity.brand(),
                productEntity.categoryId()
        );
    }

    public ProductEntity fromProducttoProductEntity(Product product) {
        return new ProductEntity(
                product.getProductId(),
                product.getName(),
                product.getProductDescription(),
                product.getBasePrice(),
                product.getDiscountedPrice(),
                product.getPictureProduct(),
                product.getBrand(),
                product.getCategoryId()
        );
    }

    public Product fromProductDtotoProduct(ProductDto productDto) {
        return new Product(
                productDto.productId(),
                productDto.name(),
                productDto.productDescription(),
                productDto.basePrice(),
                productDto.discountedPrice(),
                null, // price is calculated in Product
                productDto.pictureProduct(),
                productDto.brand(),
                productDto.categoryId()
        );
    }

    public ProductDto fromProducttoProductDto(Product product) {
        return new ProductDto(
                product.getProductId(),
                product.getName(),
                product.getProductDescription(),
                product.getBasePrice(),
                product.getDiscountedPrice(),
                null, // price is calculated in Product
                product.getPictureProduct(),
                product.getBrand(),
                product.getCategoryId()
        );
    }




}
