package es.VetUp.tienda_back.b_domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Product {

    private final Long productId;
    private final String name;
    private final String productDescription;
    private final BigDecimal  basePrice;
    private final BigDecimal  discountedPrice;
    private final BigDecimal  price;
    private final String pictureProduct;
    private final String brand;
    private final Long categoryId;


    public Product(Long productId, String name, String productDescription, BigDecimal basePrice, BigDecimal discountedPrice, BigDecimal price, String pictureProduct, String brand, Long categoryId) {
        this.productId = productId;
        this.name = name;
        this.productDescription = productDescription;
        this.basePrice = basePrice;
        this.discountedPrice = discountedPrice;
        this.price = calculateFinalPrice();
        this.pictureProduct = pictureProduct;
        this.brand = brand;
        this.categoryId = categoryId;
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

    public BigDecimal getDiscountedPrice() {
        return discountedPrice;
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

    public BigDecimal calculateFinalPrice() {
        if (basePrice == null) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        if (discountedPrice == null) {
            return basePrice.setScale(2, RoundingMode.HALF_UP);
        }
        BigDecimal discount = basePrice
                .multiply(discountedPrice)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        return basePrice.subtract(discount).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId) && Objects.equals(name, product.name) && Objects.equals(productDescription, product.productDescription) && Objects.equals(basePrice, product.basePrice) && Objects.equals(discountedPrice, product.discountedPrice) && Objects.equals(price, product.price) && Objects.equals(pictureProduct, product.pictureProduct) && Objects.equals(brand, product.brand) && Objects.equals(categoryId, product.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, name, productDescription, basePrice, discountedPrice, price, pictureProduct, brand, categoryId);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", basePrice=" + basePrice +
                ", discountedPrice=" + discountedPrice +
                ", price=" + price +
                ", pictureProduct='" + pictureProduct + '\'' +
                ", brand='" + brand + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }


}
