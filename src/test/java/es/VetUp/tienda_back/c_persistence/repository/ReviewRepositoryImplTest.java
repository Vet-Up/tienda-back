package es.VetUp.tienda_back.c_persistence.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.VetUp.tienda_back.b_domain.repository.entity.ReviewEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.ReviewJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.ProductJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.UserJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.ProductJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.ReviewJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.UserJpaEntity;
import es.VetUp.tienda_back.c_persistence.repository.mapper.ReviewPersistenceMapper;

@ExtendWith(MockitoExtension.class)
class ReviewRepositoryImplTest {

    @Mock
    private ReviewJpaDao reviewJpaDao;

    @Mock
    private ProductJpaDao productJpaDao;

    @Mock
    private UserJpaDao userJpaDao;

    @InjectMocks
    private ReviewRepositoryImpl reviewRepositoryImpl;

    ReviewJpaEntity reviewJpaEntity1;
    ReviewJpaEntity reviewJpaEntity2;
    ProductJpaEntity productJpaEntity;
    UserJpaEntity userJpaEntity;

    @BeforeEach
    void setUp() {
        productJpaEntity = new ProductJpaEntity();
        productJpaEntity.setProductId(10L);

        userJpaEntity = new UserJpaEntity(
                5L,
                "John Doe",
                "johndoe",
                "johndoe@example.com",
                "password",
                null,
                null,
                null,
                null,
                null,
                null
        );

        reviewJpaEntity1 = new ReviewJpaEntity();
        reviewJpaEntity1.setReviewId(1L);
        reviewJpaEntity1.setProduct(productJpaEntity);
        reviewJpaEntity1.setUser(userJpaEntity);
        reviewJpaEntity1.setRating(5);
        reviewJpaEntity1.setComment("Excellent product!");
        reviewJpaEntity1.setCreatedAt(LocalDateTime.now());

        reviewJpaEntity2 = new ReviewJpaEntity();
        reviewJpaEntity2.setReviewId(2L);
        reviewJpaEntity2.setProduct(productJpaEntity);
        reviewJpaEntity2.setUser(userJpaEntity);
        reviewJpaEntity2.setRating(4);
        reviewJpaEntity2.setComment("Good quality");
        reviewJpaEntity2.setCreatedAt(LocalDateTime.now());
    }

    @Nested
    class FindAllReviewsTest {
        @Test
        @DisplayName("Find all should return all reviews")
        void testFindAllReviews() {
            List<ReviewJpaEntity> expectedList = List.of(reviewJpaEntity1, reviewJpaEntity2);
            when(reviewJpaDao.findAll()).thenReturn(expectedList);

            List<ReviewEntity> expectedEntities = expectedList.stream()
                    .map(ReviewPersistenceMapper.getInstance()::fromReviewJpaEntityToReviewEntity)
                    .toList();

            List<ReviewEntity> actualEntities = reviewRepositoryImpl.findAll();

            assertAll(
                    () -> assertEquals(expectedEntities.size(), actualEntities.size()),
                    () -> assertEquals(expectedEntities.getFirst().reviewId(), actualEntities.getFirst().reviewId()),
                    () -> assertEquals(expectedEntities.get(1).reviewId(), actualEntities.get(1).reviewId()));
        }

        @Test
        @DisplayName("Find all should return empty list when no reviews")
        void testFindAllReviewsEmpty() {
            when(reviewJpaDao.findAll()).thenReturn(List.of());

            List<ReviewEntity> actualEntities = reviewRepositoryImpl.findAll();

            assertTrue(actualEntities.isEmpty());
        }
    }

    @Nested
    class FindReviewByIdTest {
        @Test
        @DisplayName("Find by id should return review when found")
        void testFindReviewByIdFound() {
            Long reviewId = 1L;
            when(reviewJpaDao.findById(reviewId)).thenReturn(Optional.of(reviewJpaEntity1));

            Optional<ReviewEntity> actual = reviewRepositoryImpl.findById(reviewId);

            ReviewEntity expectedEntity = ReviewPersistenceMapper.getInstance()
                    .fromReviewJpaEntityToReviewEntity(reviewJpaEntity1);

            assertAll(
                    () -> assertTrue(actual.isPresent()),
                    () -> assertEquals(expectedEntity.reviewId(), actual.get().reviewId()));
        }

        @Test
        @DisplayName("Find by id should return empty when not found")
        void testFindReviewByIdNotFound() {
            Long reviewId = 999L;
            when(reviewJpaDao.findById(reviewId)).thenReturn(Optional.empty());

            Optional<ReviewEntity> actual = reviewRepositoryImpl.findById(reviewId);

            assertFalse(actual.isPresent());
        }
    }

    @Nested
    class FindReviewsByProductIdTest {
        @Test
        @DisplayName("Find by product id should return reviews for that product")
        void testFindReviewsByProductId() {
            Long productId = 10L;
            List<ReviewJpaEntity> expectedList = List.of(reviewJpaEntity1, reviewJpaEntity2);
            when(reviewJpaDao.findByProductId(productId, 0, 10)).thenReturn(expectedList);

            List<ReviewEntity> actualEntities = reviewRepositoryImpl.findByProductId(productId, 0, 10);

            assertEquals(2, actualEntities.size());
        }

        @Test
        @DisplayName("Find by product id should return empty list when no reviews")
        void testFindReviewsByProductIdEmpty() {
            Long productId = 999L;
            when(reviewJpaDao.findByProductId(productId, 0, 10)).thenReturn(List.of());

            List<ReviewEntity> actualEntities = reviewRepositoryImpl.findByProductId(productId, 0, 10);

            assertTrue(actualEntities.isEmpty());
        }
    }

    @Nested
    class FindReviewsByUserIdTest {
        @Test
        @DisplayName("Find by user id should return reviews for that user")
        void testFindReviewsByUserId() {
            Long userId = 5L;
            List<ReviewJpaEntity> expectedList = List.of(reviewJpaEntity1, reviewJpaEntity2);
            when(reviewJpaDao.findByUserId(userId, 0, 10)).thenReturn(expectedList);

            List<ReviewEntity> actualEntities = reviewRepositoryImpl.findByUserId(userId, 0, 10);

            assertEquals(2, actualEntities.size());
        }

        @Test
        @DisplayName("Find by user id should return empty list when no reviews")
        void testFindReviewsByUserIdEmpty() {
            Long userId = 999L;
            when(reviewJpaDao.findByUserId(userId, 0, 10)).thenReturn(List.of());

            List<ReviewEntity> actualEntities = reviewRepositoryImpl.findByUserId(userId, 0, 10);

            assertTrue(actualEntities.isEmpty());
        }
    }

    @Nested
    class SaveReviewTest {
        @Test
        @DisplayName("Save with new review should insert it")
        void testSaveReview() {
            ReviewEntity reviewEntityToSave = new ReviewEntity(
                    null,
                    10L,
                    5L,
                    "John Doe",
                    5,
                    "New review",
                    LocalDateTime.now());

            // Mock para productJpaDao y userJpaDao
            when(productJpaDao.findById(10L)).thenReturn(Optional.of(productJpaEntity));
            when(userJpaDao.getClientById(5L)).thenReturn(Optional.of(userJpaEntity));
            when(reviewJpaDao.insert(any(ReviewJpaEntity.class))).thenReturn(reviewJpaEntity1);

            ReviewEntity actual = reviewRepositoryImpl.save(reviewEntityToSave);

            ReviewEntity expected = ReviewPersistenceMapper.getInstance()
                    .fromReviewJpaEntityToReviewEntity(reviewJpaEntity1);

            assertEquals(expected.reviewId(), actual.reviewId());
        }

        @Test
        @DisplayName("Update existing review should update it")
        void testUpdateReview() {
            ReviewEntity reviewEntityToUpdate = new ReviewEntity(
                    1L,
                    10L,
                    5L,
                    "John Doe",
                    4,
                    "Updated review",
                    LocalDateTime.now());

            // Mock para productJpaDao y userJpaDao
            when(productJpaDao.findById(10L)).thenReturn(Optional.of(productJpaEntity));
            when(userJpaDao.getClientById(5L)).thenReturn(Optional.of(userJpaEntity));
            when(reviewJpaDao.update(any(ReviewJpaEntity.class))).thenReturn(reviewJpaEntity1);

            ReviewEntity actual = reviewRepositoryImpl.update(reviewEntityToUpdate);

            ReviewEntity expected = ReviewPersistenceMapper.getInstance()
                    .fromReviewJpaEntityToReviewEntity(reviewJpaEntity1);

            assertEquals(expected.reviewId(), actual.reviewId());
        }
    }

    @Nested
    class DeleteReviewTest {
        @Test
        @DisplayName("Delete review should call dao to delete review by id")
        void testDeleteReview() {
            Long reviewIdToDelete = 1L;

            reviewRepositoryImpl.deleteById(reviewIdToDelete);
        }
    }

    @Nested
    class CountReviewsTest {
        @Test
        @DisplayName("Count by product id should return correct count")
        void testCountReviewsByProductId() {
            Long productId = 10L;
            when(reviewJpaDao.countByProductId(productId)).thenReturn(5L);

            long count = reviewRepositoryImpl.countByProductId(productId);

            assertEquals(5L, count);
        }

        @Test
        @DisplayName("Count by user id should return correct count")
        void testCountReviewsByUserId() {
            Long userId = 5L;
            when(reviewJpaDao.countByUserId(userId)).thenReturn(3L);

            long count = reviewRepositoryImpl.countByUserId(userId);

            assertEquals(3L, count);
        }
    }

    @Nested
    class AverageRatingTest {
        @Test
        @DisplayName("Average rating by product id should return correct average")
        void testAverageRatingByProductId() {
            Long productId = 10L;
            when(reviewJpaDao.averageRatingByProductId(productId)).thenReturn(4.5);

            double average = reviewRepositoryImpl.averageRatingByProductId(productId);

            assertEquals(4.5, average);
        }
    }
}

