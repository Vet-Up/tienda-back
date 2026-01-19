package es.VetUp.tienda_back.a_presentation.controller.mapper;

import es.VetUp.tienda_back.a_presentation.controller.webModel.request.ProductInsertRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.ProductUpdateRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.response.ProductDetailResponse;
import es.VetUp.tienda_back.a_presentation.controller.webModel.response.ProductSummaryResponse;
import es.VetUp.tienda_back.b_domain.service.dto.ProductDto;

public class ProductPresentationMapper {
    private static ProductPresentationMapper INSTANCE;

    private ProductPresentationMapper() {
    }

    public static ProductPresentationMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProductPresentationMapper();
        }
        return INSTANCE;
    }

    public ProductDetailResponse fromProductDtoToToProductDetailResponse(ProductDto productDto) {
        return new ProductDetailResponse(
                productDto.productId(),
                productDto.name(),
                productDto.productDescription(),
                productDto.basePrice(),
                productDto.discountedPrice(),
                productDto.price(),
                productDto.pictureProduct(),
                productDto.brand(),
                productDto.categoryId());
    }

    public static ProductSummaryResponse fromProductDtoToProductSummaryResponse(ProductDto productDto) {
        return new ProductSummaryResponse(
                productDto.productId(),
                productDto.name(),
                productDto.productDescription(),
                productDto.basePrice(),
                productDto.discountedPrice(),
                productDto.price(),
                productDto.pictureProduct(),
                productDto.brand(),
                productDto.categoryId());
    }

    public ProductDto fromProductInsertRequestToProductDto(ProductInsertRequest productInsertRequest) {
        return new ProductDto(
                null,
                productInsertRequest.name(),
                productInsertRequest.productDescription(),
                productInsertRequest.basePrice(),
                productInsertRequest.discountedPrice(),
                null,
                productInsertRequest.pictureProduct(),
                productInsertRequest.brand(),
                productInsertRequest.categoryId());
    }

    public ProductDto fromProductUpdateRequestToProductDto(ProductUpdateRequest productUpdateRequest) {
        return new ProductDto(
                productUpdateRequest.productId(),
                productUpdateRequest.name(),
                productUpdateRequest.productDescription(),
                productUpdateRequest.basePrice(),
                productUpdateRequest.discountedPrice(),
                null,
                productUpdateRequest.pictureProduct(),
                productUpdateRequest.brand(),
                productUpdateRequest.categoryId());
    }
}
