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
                productDto.discount(),
                productDto.price(),
                productDto.pictureProduct(),
                productDto.brand(),
                productDto.categoryId(),
                productDto.stock(),
                productDto.averageRating(),
                productDto.reviewsCount()
        );
    }

    public static ProductSummaryResponse fromProductDtoToProductSummaryResponse(ProductDto productDto) {
        return new ProductSummaryResponse(
                productDto.productId(),
                productDto.name(),
                productDto.productDescription(),
                productDto.basePrice(),
                productDto.discount(),
                productDto.price(),
                productDto.pictureProduct(),
                productDto.brand(),
                productDto.categoryId(),
                productDto.stock(),
                productDto.averageRating(),
                productDto.reviewsCount()
        );
    }

    public ProductDto fromProductInsertRequestToProductDto(ProductInsertRequest productInsertRequest) {
        return new ProductDto(
                null,
                productInsertRequest.name(),
                productInsertRequest.productDescription(),
                productInsertRequest.basePrice(),
                productInsertRequest.discount(),
                null,
                productInsertRequest.pictureProduct(),
                productInsertRequest.brand(),
                productInsertRequest.categoryId(),
                productInsertRequest.stock(),
                null,
                null
        );
    }

    public ProductDto fromProductUpdateRequestToProductDto(ProductUpdateRequest productUpdateRequest) {
        return new ProductDto(
                productUpdateRequest.productId(),
                productUpdateRequest.name(),
                productUpdateRequest.productDescription(),
                productUpdateRequest.basePrice(),
                productUpdateRequest.discount(),
                null,
                productUpdateRequest.pictureProduct(),
                productUpdateRequest.brand(),
                productUpdateRequest.categoryId(),
                productUpdateRequest.stock(),
                null,
                null
        );
    }
}
