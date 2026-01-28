package es.VetUp.tienda_back.a_presentation.controller.mapper;

import es.VetUp.tienda_back.b_domain.service.dto.ReviewDto;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.ReviewInsertRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.ReviewUpdateRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.response.ReviewDetailResponse;
import es.VetUp.tienda_back.a_presentation.controller.webModel.response.ReviewSummaryResponse;

public class ReviewPresentationMapper {
    private static ReviewPresentationMapper INSTANCE;

    private ReviewPresentationMapper() {}

    public static ReviewPresentationMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReviewPresentationMapper();
        }
        return INSTANCE;
    }

    public ReviewDto fromInsertRequestToDto(ReviewInsertRequest request) {
        return new ReviewDto(
            null,
            request.productId(),
            request.userId(),
            null,
            request.rating(),
            request.comment(),
            null
        );
    }

    public ReviewDto fromUpdateRequestToDto(ReviewUpdateRequest request) {
        return new ReviewDto(
            request.reviewId(),
            request.productId(),
            request.userId(),
            null,
            request.rating(),
            request.comment(),
            null
        );
    }

    public ReviewDetailResponse fromDtoToDetailResponse(ReviewDto dto) {
        return new ReviewDetailResponse(
            dto.reviewId(),
            dto.productId(),
            dto.userId(),
            dto.userName(),
            dto.rating(),
            dto.comment(),
            dto.createdAt()
        );
    }

    public ReviewSummaryResponse fromDtoToSummaryResponse(ReviewDto dto) {
        return new ReviewSummaryResponse(
            dto.reviewId(),
            dto.productId(),
            dto.userId(),
            dto.userName(),
            dto.rating(),
            dto.comment(),
            dto.createdAt()
        );
    }
}
