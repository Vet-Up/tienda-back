package es.VetUp.tienda_back.c_persistence.dao.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class ProductJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 150, message = "Product name must be between 2 and 150 characters")
    private String name;

    @NotBlank(message = "Product description is required")
    @Size(max = 1000, message = "Product description cannot exceed 1000 characters")
    @Column(name = "product_description")
    private String productDescription;

    @Positive(message = "Price must be greater than 0")
    @Column(name = "price")
    private BigDecimal basePrice;

    @PositiveOrZero(message = "Discounted price must be zero or positive")
    @Column(name = "discounted_price")
    private BigDecimal price;

    @NotBlank(message = "Product picture URL is required")
    private String pictureProduct;

    @NotBlank(message = "Brand is required")
    @Size(min = 2, max = 100, message = "Brand must be between 2 and 100 characters")
    private String brand;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryJpaEntity category;

    @PositiveOrZero(message = "Stock must be zero or positive")
    @Column(name = "stock")
    private Integer stock;


    public ProductJpaEntity() {}

    public ProductJpaEntity(Long productId, String name, String productDescription, BigDecimal basePrice,
                            BigDecimal price, String pictureProduct, String brand,
                            CategoryJpaEntity category, Integer stock) {
        this.productId = productId;
        this.name = name;
        this.productDescription = productDescription;
        this.basePrice = basePrice;
        this.price = price;
        this.pictureProduct = pictureProduct;
        this.brand = brand;
        this.category = category;
        this.stock = stock;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPictureProduct() {
        return pictureProduct;
    }

    public void setPictureProduct(String pictureProduct) {
        this.pictureProduct = pictureProduct;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public CategoryJpaEntity getCategory() { return category; }
    public void setCategory(CategoryJpaEntity category) { this.category = category; }


    public Long getCategoryId() {
        return category != null ? category.getCategoryId() : null;
    }

    public void setCategoryId(Long categoryId) {
        if (this.category == null) {
            this.category = new CategoryJpaEntity();
        }
        this.category.setCategoryId(categoryId);
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
