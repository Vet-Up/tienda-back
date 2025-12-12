package es.VetUp.tienda_back.b_domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private final Long productId;
    private final String name;
    private final String productDescription;
    private final BigDecimal  price;
    private final BigDecimal  discountedPrice;
    private final String pictureProduct;
    private final String brand;
    private final Long categoryId;


    public Product(Long productId, String name, String productDescription, BigDecimal  price, BigDecimal  discountedPrice, String pictureProduct, String brand, Long categoryId) {
        this.productId = productId;
        this.name = name;
        this.productDescription = productDescription;
        this.price = price;
        this.discountedPrice = discountedPrice;
        this.pictureProduct = pictureProduct;
        this.brand = brand;
        this.categoryId = categoryId;
    }

    public Long getProduct_id() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getProduct_description() {
        return productDescription;
    }

    public BigDecimal  getPrice() {
        return price;
    }

    public BigDecimal getDiscountedPrice() {
        return discountedPrice;
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



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId) && Objects.equals(name, product.name) && Objects.equals(productDescription, product.productDescription) && Objects.equals(price, product.price) && Objects.equals(discountedPrice, product.discountedPrice) && Objects.equals(pictureProduct, product.pictureProduct) && Objects.equals(brand, product.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, name, productDescription, price, discountedPrice, pictureProduct, brand);
    }


}


