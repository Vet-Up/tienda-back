package es.VetUp.tienda_back.a_presentation.controller.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.VetUp.tienda_back.a_presentation.controller.webModel.response.ProductDetailResponse;
import es.VetUp.tienda_back.b_domain.service.dto.ProductDto;

class ProductPresentationMapperTest {

        @Test
        @DisplayName("Test map from ProductDto to ProductDetailResponse")
        void testFromProductDtoToToProductDetailResponse() {
                ProductDto productDto = new ProductDto(
                                1L,
                                "Producto 1",
                                "Descripción producto 1",
                                new BigDecimal("19.99"),
                                new BigDecimal("5.00"),
                                new BigDecimal("15.99"),
                                "imagen1.jpg",
                                "Marca1",
                                1L,
                                100,
                                null,
                                null);

                ProductDetailResponse response = ProductPresentationMapper.getInstance()
                                .fromProductDtoToToProductDetailResponse(productDto);

                assertEquals(productDto.productId(), response.productId());
                assertEquals(productDto.name(), response.name());
                assertEquals(productDto.productDescription(), response.productDescription());
                assertEquals(productDto.basePrice(), response.basePrice());
                assertEquals(productDto.discount(), response.discount());
                assertEquals(productDto.price(), response.price());
                assertEquals(productDto.pictureProduct(), response.pictureProduct());
                assertEquals(productDto.brand(), response.brand());
                assertEquals(productDto.categoryId(), response.categoryId());
                assertEquals(productDto.stock(), response.stock());

        }

        @Test
        @DisplayName("Test map from ProductDto to ProductSummaryResponse")
        void testFromProductDtoToProductSummaryResponse() {
                ProductDto productDto = new ProductDto(
                                2L,
                                "Producto 2",
                                "Descripción producto 2",
                                new BigDecimal("29.99"),
                                new BigDecimal("5.00"),
                                new BigDecimal("25.99"),
                                "imagen2.jpg",
                                "Marca2",
                                2L,
                                100,
                                null,
                                null);

                var response = ProductPresentationMapper.fromProductDtoToProductSummaryResponse(productDto);

                assertEquals(productDto.productId(), response.productId());
                assertEquals(productDto.name(), response.name());
                assertEquals(productDto.productDescription(), response.productDescription());
                assertEquals(productDto.basePrice(), response.basePrice());
                assertEquals(productDto.discount(), response.discount());
                assertEquals(productDto.price(), response.price());
                assertEquals(productDto.pictureProduct(), response.pictureProduct());
                assertEquals(productDto.brand(), response.brand());
                assertEquals(productDto.categoryId(), response.categoryId());
                assertEquals(productDto.stock(), response.stock());
        }

        @Test
        @DisplayName("Test map from ProductInsertRequest to ProductDto")
        void testFromProductInsertRequestToProductDto() {
                var productInsertRequest = new es.VetUp.tienda_back.a_presentation.controller.webModel.request.ProductInsertRequest(
                                "Producto 3",
                                "Descripción producto 3",
                                new BigDecimal("39.99"),
                                new BigDecimal("5.00"),
                                "imagen3.jpg",
                                "Marca3",
                                3L,
                                50);

                ProductDto productDto = ProductPresentationMapper.getInstance()
                                .fromProductInsertRequestToProductDto(productInsertRequest);

                assertNull(productDto.productId());
                assertEquals(productInsertRequest.name(), productDto.name());
                assertEquals(productInsertRequest.productDescription(), productDto.productDescription());
                assertEquals(productInsertRequest.basePrice(), productDto.basePrice());
                assertEquals(productInsertRequest.discount(), productDto.discount());
                assertEquals(productInsertRequest.pictureProduct(), productDto.pictureProduct());
                assertEquals(productInsertRequest.brand(), productDto.brand());
                assertEquals(productInsertRequest.categoryId(), productDto.categoryId());
                assertEquals(productInsertRequest.stock(), productDto.stock());
        }

        @Test
        @DisplayName("Test map from ProductUpdateRequest to ProductDto")
        void testFromProductUpdateRequestToProductDto() {
                var productUpdateRequest = new es.VetUp.tienda_back.a_presentation.controller.webModel.request.ProductUpdateRequest(
                                4L,
                                "Producto 4",
                                "Descripción producto 4",
                                new BigDecimal("49.99"),
                                new BigDecimal("5.00"),
                                "imagen4.jpg",
                                "Marca4",
                                4L,
                                75);

                ProductDto productDto = ProductPresentationMapper.getInstance()
                                .fromProductUpdateRequestToProductDto(productUpdateRequest);

                assertEquals(productUpdateRequest.productId(), productDto.productId());
                assertEquals(productUpdateRequest.name(), productDto.name());
                assertEquals(productUpdateRequest.productDescription(), productDto.productDescription());
                assertEquals(productUpdateRequest.basePrice(), productDto.basePrice());
                assertEquals(productUpdateRequest.discount(), productDto.discount());
                assertEquals(productUpdateRequest.pictureProduct(), productDto.pictureProduct());
                assertEquals(productUpdateRequest.brand(), productDto.brand());
                assertEquals(productUpdateRequest.categoryId(), productDto.categoryId());
                assertEquals(productUpdateRequest.stock(), productDto.stock());
        }
}