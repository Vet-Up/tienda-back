package es.VetUp.tienda_back.a_presentation.controller;

import es.VetUp.tienda_back.a_presentation.controller.mapper.ReviewPresentationMapper;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.ReviewInsertRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.ReviewUpdateRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.response.ReviewDetailResponse;
import es.VetUp.tienda_back.a_presentation.controller.webModel.response.ReviewSummaryResponse;
import es.VetUp.tienda_back.b_domain.model.Page;
import es.VetUp.tienda_back.b_domain.service.ReviewService;
import es.VetUp.tienda_back.b_domain.service.dto.ReviewDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewDetailResponse> createReview(@RequestBody ReviewInsertRequest request) {
        ReviewDto dto = ReviewPresentationMapper.getInstance().fromInsertRequestToDto(request);
        ReviewDetailResponse response = ReviewPresentationMapper.getInstance()
                .fromDtoToDetailResponse(reviewService.saveReview(dto));
        return ResponseEntity.ok(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<ReviewDetailResponse> updateReview(
            @PathVariable Long id,
            @RequestBody ReviewUpdateRequest request) {
        ReviewDto dto = ReviewPresentationMapper.getInstance().fromUpdateRequestToDto(request);
        dto = reviewService.saveReview(dto);
        ReviewDetailResponse response = ReviewPresentationMapper.getInstance()
                .fromDtoToDetailResponse(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDetailResponse> getReviewById(@PathVariable Long reviewId) {
        return reviewService.getReviewById(reviewId)
                .map(ReviewPresentationMapper.getInstance()::fromDtoToDetailResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Page<ReviewSummaryResponse>> getReviewsByProduct(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "4") int size) {
        if (page < 1) {
            return ResponseEntity.badRequest().build();
        }
        int realPage = page - 1;
        List<ReviewDto> reviewDtos = reviewService.getReviewsByProductId(productId, realPage, size);
        List<ReviewSummaryResponse> responses = reviewDtos.stream()
                .map(ReviewPresentationMapper.getInstance()::fromDtoToSummaryResponse)
                .collect(Collectors.toList());
        long total = reviewService.countReviewsByProductId(productId);
        Page<ReviewSummaryResponse> result = new Page<>(
                responses, page, size, total);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<ReviewSummaryResponse>> getReviewsByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "4") int size) {
        if (page < 1) {
            return ResponseEntity.badRequest().build();
        }
        int realPage = page - 1;
        List<ReviewDto> reviewDtos = reviewService.getReviewsByUserId(userId, realPage, size);
        List<ReviewSummaryResponse> responses = reviewDtos.stream()
                .map(ReviewPresentationMapper.getInstance()::fromDtoToSummaryResponse)
                .collect(Collectors.toList());
        long total = reviewService.countReviewsByUserId(userId);
        Page<ReviewSummaryResponse> result = new Page<>(
                responses, page, size, total);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/product/{productId}/user/{userId}")
    public ResponseEntity<ReviewDetailResponse> getReviewByUserAndProduct(
            @PathVariable Long productId,
            @PathVariable Long userId) {
        return reviewService.getReviewByUserAndProduct(userId, productId)
                .map(ReviewPresentationMapper.getInstance()::fromDtoToDetailResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }


}
