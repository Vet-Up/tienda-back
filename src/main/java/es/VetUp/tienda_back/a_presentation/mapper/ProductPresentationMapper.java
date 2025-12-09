package es.VetUp.tienda_back.a_presentation.mapper;

import es.VetUp.tienda_back.a_presentation.webModel.request.ProductInsertRequest;
import es.VetUp.tienda_back.a_presentation.webModel.request.ProductUpdateRequest;
import es.VetUp.tienda_back.a_presentation.webModel.response.ProductDetailResponse;
import es.VetUp.tienda_back.a_presentation.webModel.response.ProductSummaryResponse;
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
                productDto.product_id(),
                productDto.name(),
                productDto.product_description(),
                productDto.price(),
                productDto.discountedPrice(),
                productDto.pictureProduct(),
                productDto.brand()
        );
    }

    public static ProductSummaryResponse fromProductDtoToProductSummaryResponse(ProductDto productDto) {
        return new ProductSummaryResponse(
                productDto.product_id(),
                productDto.name(),
                productDto.price(),
                productDto.discountedPrice(),
                productDto.pictureProduct(),
                productDto.brand()
        );
    }

    public ProductDto fromProductInsertRequestToProductDto(ProductInsertRequest productInsertRequest) {
        return new ProductDto(
                null,
                productInsertRequest.name(),
                productInsertRequest.product_description(),
                productInsertRequest.price(),
                productInsertRequest.discountedPrice(),
                productInsertRequest.pictureProduct(),
                productInsertRequest.brand()
        );
    }

    public ProductDto fromProductUpdateRequestToProductDto(ProductUpdateRequest productUpdateRequest) {
        return new ProductDto(
                productUpdateRequest.product_id(),
                productUpdateRequest.name(),
                productUpdateRequest.product_description(),
                productUpdateRequest.price(),
                productUpdateRequest.discountedPrice(),
                productUpdateRequest.pictureProduct(),
                productUpdateRequest.brand()
        );
    }
}
