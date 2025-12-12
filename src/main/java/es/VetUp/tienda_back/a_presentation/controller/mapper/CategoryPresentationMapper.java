package es.VetUp.tienda_back.a_presentation.controller.mapper;

import es.VetUp.tienda_back.a_presentation.controller.webModel.request.CategoryInsertRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.CategoryUpdateRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.response.CategoryDetailResponse;
import es.VetUp.tienda_back.a_presentation.controller.webModel.response.CategorySummaryResponse;
import es.VetUp.tienda_back.b_domain.service.dto.CategoryDto;

public class CategoryPresentationMapper {
    public static CategoryPresentationMapper INSTANCE;

    private CategoryPresentationMapper() {
    }

    public static CategoryPresentationMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CategoryPresentationMapper();
        }
        return INSTANCE;
    }

    public CategoryDetailResponse fromCategoryDtoToCategoryDetailResponse(CategoryDto categoryDto) {
        return new CategoryDetailResponse(
                categoryDto.categoryId(),
                categoryDto.name(),
                categoryDto.description()
        );
    }

    public CategorySummaryResponse fromCategoryDtoToCategorySummaryResponse(CategoryDto categoryDto) {
        return new CategorySummaryResponse(
                categoryDto.categoryId(),
                categoryDto.name(),
                categoryDto.description()
        );
    }

    public CategoryDto fromCategoryInsertRequestToCategoryDto(CategoryInsertRequest categoryInsertRequest) {
        return new CategoryDto(
                null,
                categoryInsertRequest.name(),
                categoryInsertRequest.description()
        );
    }

    public CategoryDto fromCategoryUpdateRequestToCategoryDto(CategoryUpdateRequest categoryUpdateRequest) {
        return new CategoryDto(
                categoryUpdateRequest.categoryId(),
                categoryUpdateRequest.name(),
                categoryUpdateRequest.description()
        );
    }

}
