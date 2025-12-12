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
                productEntity.price(),
                productEntity.discountedPrice(),
                productEntity.pictureProduct(),
                productEntity.brand(),
                productEntity.categoryId()
        );
    }

    public ProductEntity fromProducttoProductEntity(Product product) {
        return new ProductEntity(
                product.getProduct_id(),
                product.getName(),
                product.getProduct_description(),
                product.getPrice(),
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
                productDto.price(),
                productDto.discountedPrice(),
                productDto.pictureProduct(),
                productDto.brand(),
                productDto.categoryId()
        );
    }

    public ProductDto fromProducttoProductDto(Product product) {
        return new ProductDto(
                product.getProduct_id(),
                product.getName(),
                product.getProduct_description(),
                product.getPrice(),
                product.getDiscountedPrice(),
                product.getPictureProduct(),
                product.getBrand(),
                product.getCategoryId()
        );
    }




}
