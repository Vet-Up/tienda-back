package es.VetUp.tienda_back.a_presentation.controller.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.VetUp.tienda_back.a_presentation.controller.webModel.response.ReviewDetailResponse;
import es.VetUp.tienda_back.b_domain.service.dto.ReviewDto;

import java.time.LocalDateTime;

class ReviewPresentationMapperTest {

    @Test
    @DisplayName("Test map from ReviewDto to ReviewDetailResponse")
    void testFromReviewDtoToReviewDetailResponse() {
        LocalDateTime now = LocalDateTime.now();
        ReviewDto reviewDto = new ReviewDto(
                1L,
                10L,
                5L,
                "John Doe",
                5,
                "Excellent product!",
                now);

        ReviewDetailResponse response = ReviewPresentationMapper.getInstance()
                .fromDtoToDetailResponse(reviewDto);

        assertEquals(reviewDto.reviewId(), response.reviewId());
        assertEquals(reviewDto.productId(), response.productId());
        assertEquals(reviewDto.userId(), response.userId());
        assertEquals(reviewDto.userName(), response.userName());
        assertEquals(reviewDto.rating(), response.rating());
        assertEquals(reviewDto.comment(), response.comment());
        assertEquals(reviewDto.createdAt(), response.createdAt());
    }

    @Test
    @DisplayName("Test map from ReviewDto to ReviewSummaryResponse")
    void testFromReviewDtoToReviewSummaryResponse() {
        LocalDateTime now = LocalDateTime.now();
        ReviewDto reviewDto = new ReviewDto(
                2L,
                11L,
                6L,
                "Jane Smith",
                4,
                "Good quality",
                now);

        var response = ReviewPresentationMapper.getInstance()
                .fromDtoToSummaryResponse(reviewDto);

        assertEquals(reviewDto.reviewId(), response.reviewId());
        assertEquals(reviewDto.productId(), response.productId());
        assertEquals(reviewDto.userId(), response.userId());
        assertEquals(reviewDto.userName(), response.userName());
        assertEquals(reviewDto.rating(), response.rating());
        assertEquals(reviewDto.comment(), response.comment());
        assertEquals(reviewDto.createdAt(), response.createdAt());
    }

    @Test
    @DisplayName("Test map from ReviewInsertRequest to ReviewDto")
    void testFromReviewInsertRequestToReviewDto() {
        var reviewInsertRequest = new es.VetUp.tienda_back.a_presentation.controller.webModel.request.ReviewInsertRequest(
                10L,
                5L,
                5,
                "Great product!"
        );

        var reviewDto = ReviewPresentationMapper.getInstance()
                .fromInsertRequestToDto(reviewInsertRequest);

        assertNull(reviewDto.reviewId());
        assertEquals(reviewInsertRequest.productId(), reviewDto.productId());
        assertEquals(reviewInsertRequest.userId(), reviewDto.userId());
        assertNull(reviewDto.userName());
        assertEquals(reviewInsertRequest.rating(), reviewDto.rating());
        assertEquals(reviewInsertRequest.comment(), reviewDto.comment());
        assertNull(reviewDto.createdAt());
    }

    @Test
    @DisplayName("Test map from ReviewUpdateRequest to ReviewDto")
    void testFromReviewUpdateRequestToReviewDto() {
        var reviewUpdateRequest = new es.VetUp.tienda_back.a_presentation.controller.webModel.request.ReviewUpdateRequest(
                1L,
                10L,
                5L,
                4,
                "Updated comment"
        );

        var reviewDto = ReviewPresentationMapper.getInstance()
                .fromUpdateRequestToDto(reviewUpdateRequest);

        assertEquals(reviewUpdateRequest.reviewId(), reviewDto.reviewId());
        assertEquals(reviewUpdateRequest.productId(), reviewDto.productId());
        assertEquals(reviewUpdateRequest.userId(), reviewDto.userId());
        assertNull(reviewDto.userName());
        assertEquals(reviewUpdateRequest.rating(), reviewDto.rating());
        assertEquals(reviewUpdateRequest.comment(), reviewDto.comment());
        assertNull(reviewDto.createdAt());
    }
}
