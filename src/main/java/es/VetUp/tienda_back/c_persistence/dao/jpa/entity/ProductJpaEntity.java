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
    private Long product_id;
    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 150, message = "Product name must be between 2 and 150 characters")
    private String name;

    @NotBlank(message = "Product description is required")
    @Size(max = 1000, message = "Product description cannot exceed 1000 characters")

    @Column(name = "product_description")
    private String productDescription;

    @Positive(message = "Price must be greater than 0")
    private BigDecimal  price;

    @PositiveOrZero(message = "Discounted price must be zero or positive")
    private BigDecimal discountedPrice;

    @NotBlank(message = "Product picture URL is required")
    private String pictureProduct;

    @NotBlank(message = "Brand is required")
    @Size(min = 2, max = 100, message = "Brand must be between 2 and 100 characters")
    private String brand;

    public ProductJpaEntity() {
    }

    public ProductJpaEntity(Long product_id, String name, String productDescription, BigDecimal  price, BigDecimal  discountedPrice, String pictureProduct, String brand) {
        this.product_id = product_id;
        this.name = name;
        this.productDescription = productDescription;
        this.price = price;
        this.discountedPrice = discountedPrice;
        this.pictureProduct = pictureProduct;
        this.brand = brand;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getProductDescription() { return productDescription; }
    public void setProductDescription(String productDescription) { this.productDescription = productDescription; }

    public BigDecimal  getPrice() { return price; }
    public void setPrice(BigDecimal  price) { this.price = price; }

    public BigDecimal  getDiscountedPrice() { return discountedPrice; }
    public void setDiscountedPrice(BigDecimal discountedPrice) { this.discountedPrice = discountedPrice; }

    public String getPictureProduct() { return pictureProduct; }
    public void setPictureProduct(String pictureProduct) { this.pictureProduct = pictureProduct; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

}
