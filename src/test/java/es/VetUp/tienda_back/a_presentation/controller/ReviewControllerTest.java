package es.VetUp.tienda_back.a_presentation.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import es.VetUp.tienda_back.b_domain.service.JwtService;
import es.VetUp.tienda_back.b_domain.service.ReviewService;
import es.VetUp.tienda_back.b_domain.service.dto.ReviewDto;

@WebMvcTest(ReviewController.class)
@AutoConfigureMockMvc(addFilters = false)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReviewService reviewService;

    @MockitoBean
    private JwtService jwtService;

    private ReviewDto reviewDto1;
    private ReviewDto reviewDto2;

    @BeforeEach
    void setUp() {
        LocalDateTime createdAt = LocalDateTime.of(2026, 2, 3, 10, 30);

        reviewDto1 = new ReviewDto(
                1L,
                10L,
                5L,
                "John Doe",
                5,
                "Excellent product!",
                createdAt);

        reviewDto2 = new ReviewDto(
                2L,
                10L,
                6L,
                "Jane Smith",
                4,
                "Good quality",
                createdAt);
    }

    @Nested
    class GetAllReviewsTests {
        @Test
        @DisplayName("GET /api/reviews - Success")
        void testGetAllReviewsSuccess() throws Exception {
            when(reviewService.getAllReviews()).thenReturn(List.of(reviewDto1, reviewDto2));

            mockMvc.perform(get("/api/reviews"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(2))
                    .andExpect(jsonPath("$[0].reviewId").value(1))
                    .andExpect(jsonPath("$[0].userName").value("John Doe"))
                    .andExpect(jsonPath("$[1].reviewId").value(2))
                    .andExpect(jsonPath("$[1].userName").value("Jane Smith"));
        }
    }

    @Nested
    class GetReviewByIdTests {
        @Test
        @DisplayName("GET /api/reviews/{id} - Success")
        void testGetReviewByIdSuccess() throws Exception {
            when(reviewService.getReviewById(1L)).thenReturn(Optional.of(reviewDto1));

            mockMvc.perform(get("/api/reviews/{id}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.reviewId").value(1))
                    .andExpect(jsonPath("$.userName").value("John Doe"))
                    .andExpect(jsonPath("$.rating").value(5))
                    .andExpect(jsonPath("$.comment").value("Excellent product!"));
        }

        @Test
        @DisplayName("GET /api/reviews/{id} - Not Found")
        void testGetReviewByIdNotFound() throws Exception {
            when(reviewService.getReviewById(999L)).thenReturn(Optional.empty());

            mockMvc.perform(get("/api/reviews/{id}", 999L))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    class GetReviewsByProductIdTests {
        @Test
        @DisplayName("GET /api/reviews/product/{productId} - Success")
        void testGetReviewsByProductIdSuccess() throws Exception {
            when(reviewService.getReviewsByProductId(10L, 0, 10)).thenReturn(List.of(reviewDto1, reviewDto2));
            when(reviewService.countReviewsByProductId(10L)).thenReturn(2L);

            mockMvc.perform(get("/api/reviews/product/{productId}", 10L)
                    .param("page", "0")
                    .param("size", "10"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.length()").value(2))
                    .andExpect(jsonPath("$.data[0].productId").value(10))
                    .andExpect(jsonPath("$.data[1].productId").value(10))
                    .andExpect(jsonPath("$.pageNumber").value(0))
                    .andExpect(jsonPath("$.pageSize").value(10))
                    .andExpect(jsonPath("$.totalElements").value(2));
        }
    }

    @Nested
    class GetReviewsByUserIdTests {
        @Test
        @DisplayName("GET /api/reviews/user/{userId} - Success")
        void testGetReviewsByUserIdSuccess() throws Exception {
            when(reviewService.getReviewsByUserId(5L, 0, 10)).thenReturn(List.of(reviewDto1));
            when(reviewService.countReviewsByUserId(5L)).thenReturn(1L);

            mockMvc.perform(get("/api/reviews/user/{userId}", 5L)
                    .param("page", "0")
                    .param("size", "10"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.length()").value(1))
                    .andExpect(jsonPath("$.data[0].userId").value(5))
                    .andExpect(jsonPath("$.data[0].userName").value("John Doe"))
                    .andExpect(jsonPath("$.pageNumber").value(0))
                    .andExpect(jsonPath("$.pageSize").value(10))
                    .andExpect(jsonPath("$.totalElements").value(1));
        }
    }

    @Nested
    class GetReviewByUserAndProductTests {
        @Test
        @DisplayName("GET /api/reviews/product/{productId}/user/{userId} - Success")
        void testGetReviewByUserAndProductSuccess() throws Exception {
            when(reviewService.getReviewByUserAndProduct(5L, 10L)).thenReturn(Optional.of(reviewDto1));

            mockMvc.perform(get("/api/reviews/product/{productId}/user/{userId}", 10L, 5L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.reviewId").value(1))
                    .andExpect(jsonPath("$.userId").value(5))
                    .andExpect(jsonPath("$.productId").value(10))
                    .andExpect(jsonPath("$.rating").value(5));
        }

        @Test
        @DisplayName("GET /api/reviews/product/{productId}/user/{userId} - Not Found")
        void testGetReviewByUserAndProductNotFound() throws Exception {
            when(reviewService.getReviewByUserAndProduct(5L, 10L)).thenReturn(Optional.empty());

            mockMvc.perform(get("/api/reviews/product/{productId}/user/{userId}", 10L, 5L))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    class CreateReviewTests {
        @Test
        @DisplayName("POST /api/reviews - Success")
        void testCreateReviewSuccess() throws Exception {
            ReviewDto createdReviewDto = new ReviewDto(
                    3L,
                    10L,
                    5L,
                    "John Doe",
                    5,
                    "Amazing!",
                    LocalDateTime.now());

            when(reviewService.saveReview(any(ReviewDto.class))).thenReturn(createdReviewDto);

            String reviewInsertRequestJson = """
                    {
                        "productId": 10,
                        "userId": 5,
                        "rating": 5,
                        "comment": "Amazing!"
                    }
                    """;

            mockMvc.perform(post("/api/reviews")
                    .contentType("application/json")
                    .content(reviewInsertRequestJson))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.reviewId").value(3))
                    .andExpect(jsonPath("$.rating").value(5))
                    .andExpect(jsonPath("$.comment").value("Amazing!"));
        }
    }

    @Nested
    class UpdateReviewTests {
        @Test
        @DisplayName("PUT /api/reviews/{id} - Success")
        void testUpdateReviewSuccess() throws Exception {
            ReviewDto updatedReviewDto = new ReviewDto(
                    1L,
                    10L,
                    5L,
                    "John Doe",
                    4,
                    "Updated comment",
                    LocalDateTime.now());

            when(reviewService.saveReview(any(ReviewDto.class))).thenReturn(updatedReviewDto);

            String reviewUpdateRequestJson = """
                    {
                        "reviewId": 1,
                        "productId": 10,
                        "userId": 5,
                        "rating": 4,
                        "comment": "Updated comment"
                    }
                    """;

            mockMvc.perform(put("/api/reviews/{id}", 1L)
                    .contentType("application/json")
                    .content(reviewUpdateRequestJson))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.reviewId").value(1))
                    .andExpect(jsonPath("$.rating").value(4))
                    .andExpect(jsonPath("$.comment").value("Updated comment"));
        }

        @Test
        @DisplayName("PUT /api/reviews/{id} - ID Mismatch")
        void testUpdateReviewIdMismatch() throws Exception {
            String reviewUpdateRequestJson = """
                    {
                        "reviewId": 2,
                        "productId": 10,
                        "userId": 5,
                        "rating": 4,
                        "comment": "Updated comment"
                    }
                    """;

            mockMvc.perform(put("/api/reviews/{id}", 1L)
                    .contentType("application/json")
                    .content(reviewUpdateRequestJson))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").value("ID in path and request body must match"));
        }
    }

    @Nested
    class DeleteReviewTests {
        @Test
        @DisplayName("DELETE /api/reviews/{id} - Success")
        void testDeleteReviewSuccess() throws Exception {
            doNothing().when(reviewService).deleteReview(1L);

            mockMvc.perform(delete("/api/reviews/{id}", 1L))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("DELETE /api/reviews/{id} - Not Found")
        void testDeleteReviewNotFound() throws Exception {
            // El GlobalExceptionHandler convierte ReviewNotFoundException en 404
            doThrow(new es.VetUp.tienda_back.b_domain.exception.ReviewNotFoundException(
                    "Review not found with id: 999"))
                    .when(reviewService).deleteReview(999L);

            mockMvc.perform(delete("/api/reviews/{id}", 999L))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.status").value(404))
                    .andExpect(jsonPath("$.error").value("Review not found"));
        }
    }

    @Nested
    class GetReviewStatsTests {
        @Test
        @DisplayName("GET /api/reviews/product/{productId}/count - Success")
        void testGetReviewCountByProductId() throws Exception {
            when(reviewService.countReviewsByProductId(10L)).thenReturn(15L);

            mockMvc.perform(get("/api/reviews/product/{productId}/count", 10L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").value(15));
        }

        @Test
        @DisplayName("GET /api/reviews/user/{userId}/count - Success")
        void testGetReviewCountByUserId() throws Exception {
            when(reviewService.countReviewsByUserId(5L)).thenReturn(8L);

            mockMvc.perform(get("/api/reviews/user/{userId}/count", 5L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").value(8));
        }
    }
}
