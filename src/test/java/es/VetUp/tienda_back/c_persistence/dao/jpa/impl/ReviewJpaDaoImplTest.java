package es.VetUp.tienda_back.c_persistence.dao.jpa.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import es.VetUp.tienda_back.c_persistence.TestConfig;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.ProductJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import es.VetUp.tienda_back.c_persistence.dao.jpa.ReviewJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.ReviewJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@DataJpaTest
@Import(TestConfig.class)
class ReviewJpaDaoImplTest {

        @PersistenceContext
        private EntityManager entityManager;

        @Autowired
        private ReviewJpaDao reviewJpaDao;

        private ProductJpaEntity product;
        private UserJpaEntity user;

        @BeforeEach
        void setUp() {
                // No limpiar entre tests, dejar que @DataJpaTest lo haga automáticamente

                // Crear categoría
                CategoryJpaEntity category = new CategoryJpaEntity(null, "Test Category", "Description");
                entityManager.persist(category);

                // Crear producto usando constructor
                product = new ProductJpaEntity(
                                null,
                                "Test Product",
                                "Test Description",
                                BigDecimal.valueOf(100.00), // price
                                BigDecimal.valueOf(90.00), // discountedPrice
                                "test.jpg",
                                "Test Brand",
                                category,
                                10);
                entityManager.persist(product);

                // Crear usuario con constructor - añadir timestamp para unicidad
                long timestamp = System.currentTimeMillis();
                user = new UserJpaEntity(
                                null,
                                "Test User",
                                "testuser_" + timestamp,
                                "test_" + timestamp + "@test.com",
                                "password",
                                "Test Address",
                                UserRole.CUSTOMER,
                                123456789,
                                "España",
                                null,
                                LocalDate.of(1990, 1, 1));
                entityManager.persist(user);

                entityManager.flush();
        }

        @Test
        void testFindAll() {
                // Arrange
                ReviewJpaEntity review = new ReviewJpaEntity(
                                null,
                                product,
                                user,
                                5,
                                "Great product!",
                                LocalDateTime.now());
                entityManager.persist(review);
                entityManager.flush();

                // Act
                List<ReviewJpaEntity> result = reviewJpaDao.findAll();

                // Assert
                assertNotNull(result);
                assertFalse(result.isEmpty());
                assertTrue(result.stream().anyMatch(r -> "Great product!".equals(r.getComment())));
        }

        @Test
        void testFindById() {
                // Arrange
                ReviewJpaEntity review = new ReviewJpaEntity(
                                null,
                                product,
                                user,
                                5,
                                "Great product!",
                                LocalDateTime.now());
                entityManager.persist(review);
                entityManager.flush();

                // Act
                ReviewJpaEntity result = reviewJpaDao.findById(review.getReviewId()).orElse(null);

                // Assert
                assertNotNull(result);
                assertEquals("Great product!", result.getComment());
                assertEquals(5, result.getRating());
        }

        @Test
        void testInsert() {
                // Arrange
                ReviewJpaEntity review = new ReviewJpaEntity(
                                null,
                                product,
                                user,
                                4,
                                "Good product",
                                LocalDateTime.now());

                // Act
                ReviewJpaEntity result = reviewJpaDao.insert(review);

                // Assert
                assertNotNull(result.getReviewId());
                assertEquals("Good product", result.getComment());
                assertEquals(4, result.getRating());
        }

        @Test
        void testUpdate() {
                // Arrange
                ReviewJpaEntity review = new ReviewJpaEntity(
                                null,
                                product,
                                user,
                                3,
                                "Original comment",
                                LocalDateTime.now());
                entityManager.persist(review);
                entityManager.flush();

                // Modificar la review
                review.setComment("Updated comment");
                review.setRating(5);

                // Act
                ReviewJpaEntity result = reviewJpaDao.update(review);

                // Assert
                assertNotNull(result);
                assertEquals("Updated comment", result.getComment());
                assertEquals(5, result.getRating());
        }

        @Test
        void testDeleteById() {
                // Arrange
                ReviewJpaEntity review = new ReviewJpaEntity(
                                null,
                                product,
                                user,
                                5,
                                "Review to delete",
                                LocalDateTime.now());
                entityManager.persist(review);
                entityManager.flush();

                Long reviewIdToDelete = review.getReviewId();

                // Act
                reviewJpaDao.deleteById(reviewIdToDelete);

                // Assert
                ReviewJpaEntity deletedReview = entityManager.find(ReviewJpaEntity.class, reviewIdToDelete);
                assertNull(deletedReview);
        }

        @Test
        void testFindByProductId() {
                // Arrange - crear segundo usuario con timestamp para evitar violación de unique
                // constraint
                long timestamp = System.currentTimeMillis() + 1000; // Añadir offset para evitar colisión
                UserJpaEntity user2 = new UserJpaEntity(
                                null,
                                "Test User 2",
                                "testuser2_" + timestamp,
                                "test2_" + timestamp + "@test.com",
                                "password",
                                "Test Address 2",
                                UserRole.CUSTOMER,
                                987654321,
                                "España",
                                null,
                                LocalDate.of(1992, 1, 1));
                entityManager.persist(user2);

                ReviewJpaEntity review1 = new ReviewJpaEntity(
                                null,
                                product,
                                user,
                                5,
                                "Review 1",
                                LocalDateTime.now());
                ReviewJpaEntity review2 = new ReviewJpaEntity(
                                null,
                                product,
                                user2, // Usar el segundo usuario
                                4,
                                "Review 2",
                                LocalDateTime.now());
                entityManager.persist(review1);
                entityManager.persist(review2);
                entityManager.flush();

                // Act
                List<ReviewJpaEntity> result = reviewJpaDao.findByProductId(product.getProductId(), 0, 10);

                // Assert
                assertNotNull(result);
                assertEquals(2, result.size());
                assertTrue(result.stream().anyMatch(r -> "Review 1".equals(r.getComment())));
                assertTrue(result.stream().anyMatch(r -> "Review 2".equals(r.getComment())));
        }

        @Test
        void testFindByUserId() {
                // Arrange
                ReviewJpaEntity review = new ReviewJpaEntity(
                                null,
                                product,
                                user,
                                5,
                                "User review",
                                LocalDateTime.now());
                entityManager.persist(review);
                entityManager.flush();

                // Act
                List<ReviewJpaEntity> result = reviewJpaDao.findByUserId(user.getId(), 0, 10);

                // Assert
                assertNotNull(result);
                assertFalse(result.isEmpty());
                assertTrue(result.stream().anyMatch(r -> "User review".equals(r.getComment())));
        }

        @Test
        void testCountByProductId() {
                // Arrange - crear segundo usuario con timestamp para evitar violación de unique
                // constraint
                long timestamp = System.currentTimeMillis() + 2000; // Añadir offset para evitar colisión
                UserJpaEntity user2 = new UserJpaEntity(
                                null,
                                "Test User 2",
                                "testuser2_" + timestamp,
                                "test2_" + timestamp + "@test.com",
                                "password",
                                "Test Address 2",
                                UserRole.CUSTOMER,
                                987654321,
                                "España",
                                null,
                                LocalDate.of(1992, 1, 1));
                entityManager.persist(user2);

                ReviewJpaEntity review1 = new ReviewJpaEntity(
                                null,
                                product,
                                user,
                                5,
                                "Review 1",
                                LocalDateTime.now());
                ReviewJpaEntity review2 = new ReviewJpaEntity(
                                null,
                                product,
                                user2, // Usar el segundo usuario
                                4,
                                "Review 2",
                                LocalDateTime.now());
                entityManager.persist(review1);
                entityManager.persist(review2);
                entityManager.flush();

                // Act
                long count = reviewJpaDao.countByProductId(product.getProductId());

                // Assert
                assertEquals(2, count);
        }

        @Test
        void testCountByUserId() {
                // Arrange
                ReviewJpaEntity review = new ReviewJpaEntity(
                                null,
                                product,
                                user,
                                5,
                                "User review",
                                LocalDateTime.now());
                entityManager.persist(review);
                entityManager.flush();

                // Act
                long count = reviewJpaDao.countByUserId(user.getId());

                // Assert
                assertEquals(1, count);
        }

        @Test
        void testFindByUserAndProduct() {
                // Arrange
                ReviewJpaEntity review = new ReviewJpaEntity(null, product, user, 5, "Nice", LocalDateTime.now());
                entityManager.persist(review);
                entityManager.flush();

                // Act
                List<ReviewJpaEntity> result = reviewJpaDao.findByUserAndProduct(user.getId(), product.getProductId());

                // Assert
                assertEquals(1, result.size());
                assertEquals("Nice", result.get(0).getComment());
        }

        @Test
        void testFindAllPaginated() {
                // Arrange
                ReviewJpaEntity review = new ReviewJpaEntity(null, product, user, 5, "Nice", LocalDateTime.now());
                entityManager.persist(review);
                entityManager.flush();

                // Act
                List<ReviewJpaEntity> result = reviewJpaDao.findAll(0, 10);

                // Assert
                assertNotNull(result);
                assertFalse(result.isEmpty());
        }

        @Test
        void testAverageRatingByProductId() {
                // Arrange
                long t = System.currentTimeMillis();
                UserJpaEntity u2 = new UserJpaEntity(null, "U2", "u2" + t, "u2" + t + "@t.com", "p", "a",
                                UserRole.CUSTOMER, 1, "E", null, LocalDate.now());
                entityManager.persist(u2);

                ReviewJpaEntity r1 = new ReviewJpaEntity(null, product, user, 5, "C1", LocalDateTime.now());
                ReviewJpaEntity r2 = new ReviewJpaEntity(null, product, u2, 3, "C2", LocalDateTime.now());
                entityManager.persist(r1);
                entityManager.persist(r2);
                entityManager.flush();

                // Act
                double avg = reviewJpaDao.averageRatingByProductId(product.getProductId());

                // Assert
                assertEquals(4.0, avg);
        }
}
