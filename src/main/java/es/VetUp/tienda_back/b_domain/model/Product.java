package es.VetUp.tienda_back.b_domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Product {

    private final Long productId;
    private final String name;
    private final String productDescription;
    private final BigDecimal basePrice;
    private final BigDecimal discount;
    private final BigDecimal price;
    private final String pictureProduct;
    private final String brand;
    private final Long categoryId;
    private final int stock;
    private final Double averageRating;
    private final Integer reviewsCount;

    public Product(Long productId, String name, String productDescription, BigDecimal basePrice,
            BigDecimal discount, String pictureProduct, String brand, Long categoryId, int stock,
            Double averageRating, Integer reviewsCount) {
        this.productId = productId;
        this.name = name;
        this.productDescription = productDescription;
        this.basePrice = basePrice;
        this.discount = discount;
        this.price = calculateFinalPrice();
        this.pictureProduct = pictureProduct;
        this.brand = brand;
        this.categoryId = categoryId;
        this.stock = stock;
        this.averageRating = averageRating;
        this.reviewsCount = reviewsCount;
    }

    // Constructor antiguo para compatibilidad
    public Product(Long productId, String name, String productDescription, BigDecimal basePrice,
            BigDecimal discount, String pictureProduct, String brand, Long categoryId, int stock) {
        this(productId, name, productDescription, basePrice, discount, pictureProduct, brand, categoryId, stock, null, null);
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getPictureProduct() {
        return pictureProduct;
    }

    public String getBrand() {
        return brand;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public int getStock() {
        return stock;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public Integer getReviewsCount() {
        return reviewsCount;
    }

    public BigDecimal calculateFinalPrice() {
        if (basePrice == null) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        if (discount == null) {
            return basePrice.setScale(2, RoundingMode.HALF_UP);
        }
        BigDecimal discountValue = basePrice
                .multiply(discount)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        return basePrice.subtract(discountValue).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId) && Objects.equals(name, product.name)
                && Objects.equals(productDescription, product.productDescription)
                && Objects.equals(basePrice, product.basePrice)
                && Objects.equals(discount, product.discount) && Objects.equals(price, product.price)
                && Objects.equals(pictureProduct, product.pictureProduct) && Objects.equals(brand, product.brand)
                && Objects.equals(categoryId, product.categoryId) && stock == product.stock
                && Objects.equals(averageRating, product.averageRating)
                && Objects.equals(reviewsCount, product.reviewsCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, name, productDescription, basePrice, discount, price, pictureProduct,
                brand, categoryId, stock, averageRating, reviewsCount);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", basePrice=" + basePrice +
                ", discount=" + discount +
                ", price=" + price +
                ", pictureProduct='" + pictureProduct + '\'' +
                ", brand='" + brand + '\'' +
                ", categoryId=" + categoryId +
                ", stock=" + stock +
                ", averageRating=" + averageRating +
                ", reviewsCount=" + reviewsCount +
                '}';
    }

}
