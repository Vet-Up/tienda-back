package es.VetUp.tienda_back.a_presentation.controller.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.VetUp.tienda_back.a_presentation.controller.webModel.response.CategoryDetailResponse;
import es.VetUp.tienda_back.b_domain.service.dto.CategoryDto;

class CategoryPresentationMapperTest {

    @Test
    @DisplayName("Test map from CategoryDto to CategoryDetailResponse")
    void testFromCategoryDtoToCategoryDetailResponse() {
        CategoryDto categoryDto = new CategoryDto(
                1L,
                "Categoría 1",
                "Descripción categoría 1");

        CategoryDetailResponse response = CategoryPresentationMapper.getInstance()
                .fromCategoryDtoToCategoryDetailResponse(categoryDto);

        assertEquals(categoryDto.categoryId(), response.categoryId());
        assertEquals(categoryDto.name(), response.name());
        assertEquals(categoryDto.description(), response.description());

    }

    @Test
    @DisplayName("Test map from CategoryDto to CategorySummaryResponse")
    void testFromCategoryDtoToCategorySummaryResponse() {
        CategoryDto categoryDto = new CategoryDto(
                2L,
                "Categoría 2",
                "Descripción categoría 2");

        var response = CategoryPresentationMapper.getInstance()
                .fromCategoryDtoToCategorySummaryResponse(categoryDto);

        assertEquals(categoryDto.categoryId(), response.categoryId());
        assertEquals(categoryDto.name(), response.name());
        assertEquals(categoryDto.description(), response.description());

    }

    @Test
    @DisplayName("Test map from CategoryInsertRequest to CategoryDto")
    void testFromCategoryInsertRequestToCategoryDto() {
        var categoryInsertRequest = new es.VetUp.tienda_back.a_presentation.controller.webModel.request.CategoryInsertRequest(
                "Categoría Insert",
                "Descripción categoría insert"
        );

        var categoryDto = CategoryPresentationMapper.getInstance()
                .fromCategoryInsertRequestToCategoryDto(categoryInsertRequest);

        assertNull(categoryDto.categoryId());
        assertEquals(categoryInsertRequest.name(), categoryDto.name());
        assertEquals(categoryInsertRequest.description(), categoryDto.description());
    }

    @Test
    @DisplayName("Test map from CategoryUpdateRequest to CategoryDto")
    void testFromCategoryUpdateRequestToCategoryDto() {
        var categoryUpdateRequest = new es.VetUp.tienda_back.a_presentation.controller.webModel.request.CategoryUpdateRequest(
                3L,
                "Categoría Update",
                "Descripción categoría update"
        );

        var categoryDto = CategoryPresentationMapper.getInstance()
                .fromCategoryUpdateRequestToCategoryDto(categoryUpdateRequest);

        assertEquals(categoryUpdateRequest.categoryId(), categoryDto.categoryId());
        assertEquals(categoryUpdateRequest.name(), categoryDto.name());
        assertEquals(categoryUpdateRequest.description(), categoryDto.description());
    }
}