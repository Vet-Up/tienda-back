package es.VetUp.tienda_back.b_domain.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.VetUp.tienda_back.b_domain.repository.OrderRepository;
import es.VetUp.tienda_back.b_domain.repository.ReviewRepository;
import es.VetUp.tienda_back.b_domain.repository.entity.ReviewEntity;
import es.VetUp.tienda_back.b_domain.service.dto.ReviewDto;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private ReviewServiceImpl reviewServiceImpl;

    @Nested
    class SaveReviewTests {
        @Test
        @DisplayName("saveReview should create and return ReviewDto")
        void testSaveReview() {
            LocalDateTime createdAt = LocalDateTime.now();
            ReviewDto reviewDtoToCreate = new ReviewDto(
                    null,
                    10L,
                    5L,
                    "John Doe",
                    5,
                    "Excellent product!",
                    createdAt);

            ReviewEntity createdReviewEntity = new ReviewEntity(
                    1L,
                    10L,
                    5L,
                    "John Doe",
                    5,
                    "Excellent product!",
                    createdAt);

            when(orderRepository.hasUserPurchasedProduct(5L, 10L)).thenReturn(true);
            when(reviewRepository.findByUserId(5L, 0, Integer.MAX_VALUE)).thenReturn(List.of());
            when(reviewRepository.save(any(ReviewEntity.class))).thenReturn(createdReviewEntity);

            ReviewDto createdReviewDto = reviewServiceImpl.saveReview(reviewDtoToCreate);

            assertAll("result",
                    () -> assertNotNull(createdReviewDto),
                    () -> assertEquals(1L, createdReviewDto.reviewId()),
                    () -> assertEquals(10L, createdReviewDto.productId()),
                    () -> assertEquals(5L, createdReviewDto.userId()),
                    () -> assertEquals("John Doe", createdReviewDto.userName()),
                    () -> assertEquals(5, createdReviewDto.rating()),
                    () -> assertEquals("Excellent product!", createdReviewDto.comment()));
        }

        @Test
        @DisplayName("saveReview should throw exception when reviewDto is null")
        void testSaveReview_NullReviewDto() {
            assertThrows(NullPointerException.class, () -> reviewServiceImpl.saveReview(null));
        }
    }

    @Nested
    class GetReviewByIdTests {
        @Test
        @DisplayName("getReviewById should return ReviewDto when found")
        void testGetReviewById() {
            Long reviewId = 1L;
            LocalDateTime createdAt = LocalDateTime.now();
            ReviewEntity reviewEntity = new ReviewEntity(
                    reviewId,
                    10L,
                    5L,
                    "John Doe",
                    5,
                    "Excellent product!",
                    createdAt);

            when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(reviewEntity));

            Optional<ReviewDto> result = reviewServiceImpl.getReviewById(reviewId);

            assertAll("result",
                    () -> assertTrue(result.isPresent()),
                    () -> assertEquals(reviewId, result.get().reviewId()),
                    () -> assertEquals("John Doe", result.get().userName()),
                    () -> assertEquals(5, result.get().rating()));
        }

        @Test
        @DisplayName("getReviewById should return empty when not found")
        void testGetReviewByIdNotFound() {
            Long reviewId = 999L;
            when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

            Optional<ReviewDto> result = reviewServiceImpl.getReviewById(reviewId);

            assertFalse(result.isPresent());
        }
    }

    @Nested
    class GetReviewsByProductIdTests {
        @Test
        @DisplayName("getReviewsByProductId should return list of reviews")
        void testGetReviewsByProductId() {
            Long productId = 10L;
            LocalDateTime createdAt = LocalDateTime.now();

            ReviewEntity review1 = new ReviewEntity(1L, productId, 5L, "John Doe", 5, "Excellent!", createdAt);
            ReviewEntity review2 = new ReviewEntity(2L, productId, 6L, "Jane Smith", 4, "Good", createdAt);

            when(reviewRepository.findByProductId(productId, 0, 10)).thenReturn(List.of(review1, review2));

            List<ReviewDto> result = reviewServiceImpl.getReviewsByProductId(productId, 0, 10);

            assertEquals(2, result.size());
            assertEquals("John Doe", result.get(0).userName());
            assertEquals("Jane Smith", result.get(1).userName());
        }

        @Test
        @DisplayName("getReviewsByProductId should return empty list when no reviews")
        void testGetReviewsByProductIdEmpty() {
            Long productId = 999L;
            when(reviewRepository.findByProductId(productId, 0, 10)).thenReturn(List.of());

            List<ReviewDto> result = reviewServiceImpl.getReviewsByProductId(productId, 0, 10);

            assertTrue(result.isEmpty());
        }
    }

    @Nested
    class GetReviewsByUserIdTests {
        @Test
        @DisplayName("getReviewsByUserId should return list of reviews")
        void testGetReviewsByUserId() {
            Long userId = 5L;
            LocalDateTime createdAt = LocalDateTime.now();

            ReviewEntity review1 = new ReviewEntity(1L, 10L, userId, "John Doe", 5, "Great!", createdAt);
            ReviewEntity review2 = new ReviewEntity(2L, 11L, userId, "John Doe", 4, "Good", createdAt);

            when(reviewRepository.findByUserId(userId, 0, 10)).thenReturn(List.of(review1, review2));

            List<ReviewDto> result = reviewServiceImpl.getReviewsByUserId(userId, 0, 10);

            assertEquals(2, result.size());
            assertEquals(10L, result.get(0).productId());
            assertEquals(11L, result.get(1).productId());
        }

        @Test
        @DisplayName("getReviewsByUserId should return empty list when no reviews")
        void testGetReviewsByUserIdEmpty() {
            Long userId = 999L;
            when(reviewRepository.findByUserId(userId, 0, 10)).thenReturn(List.of());

            List<ReviewDto> result = reviewServiceImpl.getReviewsByUserId(userId, 0, 10);

            assertTrue(result.isEmpty());
        }
    }

    @Nested
    class DeleteReviewTests {
        @Test
        @DisplayName("deleteReview should delete review when it exists")
        void testDeleteReview() {
            Long reviewId = 1L;
            LocalDateTime createdAt = LocalDateTime.now();
            ReviewEntity reviewEntity = new ReviewEntity(
                    reviewId,
                    10L,
                    5L,
                    "John Doe",
                    5,
                    "Excellent!",
                    createdAt);

            when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(reviewEntity));
            doNothing().when(reviewRepository).deleteById(reviewId);

            assertDoesNotThrow(() -> reviewServiceImpl.deleteReview(reviewId));
        }

        @Test
        @DisplayName("deleteReview should not fail when review not found")
        void testDeleteReviewNotFound() {
            Long reviewId = 999L;
            when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

            // El servicio no lanza excepción, simplemente no hace nada
            assertDoesNotThrow(() -> reviewServiceImpl.deleteReview(reviewId));
        }
    }

    @Nested
    class GetAllReviewsTests {
        @Test
        @DisplayName("getAllReviews should return all reviews")
        void testGetAllReviews() {
            LocalDateTime createdAt = LocalDateTime.now();
            ReviewEntity review1 = new ReviewEntity(1L, 10L, 5L, "John Doe", 5, "Great!", createdAt);
            ReviewEntity review2 = new ReviewEntity(2L, 11L, 6L, "Jane Smith", 4, "Good", createdAt);

            when(reviewRepository.findAll()).thenReturn(List.of(review1, review2));

            List<ReviewDto> result = reviewServiceImpl.getAllReviews();

            assertEquals(2, result.size());
        }

        @Test
        @DisplayName("getAllReviews should return empty list when no reviews")
        void testGetAllReviewsEmpty() {
            when(reviewRepository.findAll()).thenReturn(List.of());

            List<ReviewDto> result = reviewServiceImpl.getAllReviews();

            assertTrue(result.isEmpty());
        }
    }

    @Nested
    class CountReviewsTests {
        @Test
        @DisplayName("countReviewsByProductId should return correct count")
        void testCountReviewsByProductId() {
            Long productId = 10L;
            when(reviewRepository.countByProductId(productId)).thenReturn(5L);

            long count = reviewServiceImpl.countReviewsByProductId(productId);

            assertEquals(5L, count);
        }

        @Test
        @DisplayName("countReviewsByUserId should return correct count")
        void testCountReviewsByUserId() {
            Long userId = 5L;
            when(reviewRepository.countByUserId(userId)).thenReturn(3L);

            long count = reviewServiceImpl.countReviewsByUserId(userId);

            assertEquals(3L, count);
        }

        @Nested
        class GetReviewByUserAndProductTests {
            @Test
            @DisplayName("getReviewByUserAndProduct should return review when it exists")
            void testGetReviewByUserAndProduct() {
                Long userId = 1L;
                Long productId = 10L;
                LocalDateTime createdAt = LocalDateTime.now();
                ReviewEntity reviewEntity = new ReviewEntity(1L, productId, userId, "John Doe", 5, "Great!", createdAt);

                when(reviewRepository.findByUserAndProduct(userId, productId)).thenReturn(Optional.of(reviewEntity));

                Optional<ReviewDto> result = reviewServiceImpl.getReviewByUserAndProduct(userId, productId);

                assertTrue(result.isPresent());
                assertEquals(1L, result.get().reviewId());
            }

            @Test
            @DisplayName("getReviewByUserAndProduct should throw ReviewNotFoundException when not found")
            void testGetReviewByUserAndProductNotFound() {
                Long userId = 1L;
                Long productId = 10L;
                when(reviewRepository.findByUserAndProduct(userId, productId)).thenReturn(Optional.empty());

                assertThrows(es.VetUp.tienda_back.b_domain.exception.ReviewNotFoundException.class,
                        () -> reviewServiceImpl.getReviewByUserAndProduct(userId, productId));
            }
        }
    }
}
