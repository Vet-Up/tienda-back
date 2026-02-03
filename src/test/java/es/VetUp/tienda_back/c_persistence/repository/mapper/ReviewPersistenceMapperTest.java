package es.VetUp.tienda_back.c_persistence.repository.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import es.VetUp.tienda_back.b_domain.repository.entity.ReviewEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.ProductJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.ReviewJpaEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.UserJpaEntity;

import java.time.LocalDateTime;

class ReviewPersistenceMapperTest {

    private ProductJpaEntity productJpaEntity;
    private UserJpaEntity userJpaEntity;

    @BeforeEach
    void setUp() {
        productJpaEntity = new ProductJpaEntity();
        productJpaEntity.setProductId(10L);
        productJpaEntity.setName("Product 1");

        userJpaEntity = new UserJpaEntity(
                5L,
                "John Doe",
                "johndoe",
                "john@example.com",
                "password",
                null,
                UserRole.CUSTOMER,
                null,
                null,
                null,
                null
        );
    }

    @Test
    @DisplayName("Test map from ReviewJpaEntity to ReviewEntity")
    void testFromReviewJpaEntityToReviewEntity() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        ReviewJpaEntity jpaEntity = new ReviewJpaEntity();
        jpaEntity.setReviewId(1L);
        jpaEntity.setProduct(productJpaEntity);
        jpaEntity.setUser(userJpaEntity);
        jpaEntity.setRating(5);
        jpaEntity.setComment("Excellent product!");
        jpaEntity.setCreatedAt(now);

        // Act
        ReviewEntity entity = ReviewPersistenceMapper.getInstance()
                .fromReviewJpaEntityToReviewEntity(jpaEntity);

        // Assert
        assertEquals(jpaEntity.getReviewId(), entity.reviewId());
        assertEquals(jpaEntity.getProduct().getProductId(), entity.productId());
        assertEquals(jpaEntity.getUser().getId(), entity.userId());
        assertEquals(jpaEntity.getUser().getName(), entity.userName());
        assertEquals(jpaEntity.getRating(), entity.rating());
        assertEquals(jpaEntity.getComment(), entity.comment());
        assertEquals(jpaEntity.getCreatedAt(), entity.createdAt());
    }

    @Test
    @DisplayName("Test map from ReviewEntity to ReviewJpaEntity")
    void testFromReviewEntityToReviewJpaEntity() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        ReviewEntity entity = new ReviewEntity(
                1L,
                10L,
                5L,
                "John Doe",
                5,
                "Excellent product!",
                now);

        // Act
        ReviewJpaEntity jpaEntity = ReviewPersistenceMapper.getInstance()
                .fromReviewEntityToReviewJpaEntity(entity, productJpaEntity, userJpaEntity);

        // Assert
        assertEquals(entity.reviewId(), jpaEntity.getReviewId());
        assertEquals(productJpaEntity, jpaEntity.getProduct());
        assertEquals(userJpaEntity, jpaEntity.getUser());
        assertEquals(entity.rating(), jpaEntity.getRating());
        assertEquals(entity.comment(), jpaEntity.getComment());
        assertEquals(entity.createdAt(), jpaEntity.getCreatedAt());
    }

}
