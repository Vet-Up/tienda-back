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
                productEntity.product_id(),
                productEntity.name(),
                productEntity.product_description(),
                productEntity.price(),
                productEntity.discountedPrice(),
                productEntity.pictureProduct(),
                productEntity.brand()
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
                product.getBrand()
        );
    }

    public Product fromProductDtotoProduct(ProductDto productDto) {
        return new Product(
                productDto.product_id(),
                productDto.name(),
                productDto.product_description(),
                productDto.price(),
                productDto.discountedPrice(),
                productDto.pictureProduct(),
                productDto.brand()
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
                product.getBrand()
        );
    }




}
