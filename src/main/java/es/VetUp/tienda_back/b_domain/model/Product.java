package es.VetUp.tienda_back.b_domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private final Long product_id;
    private final String name;
    private final String product_description;
    private final BigDecimal  price;
    private final BigDecimal  discountedPrice;
    private final String pictureProduct;
    private final String brand;


    public Product(Long productId, String name, String productDescription, BigDecimal  price, BigDecimal  discountedPrice, String pictureProduct, String brand) {
        this.product_id = productId;
        this.name = name;
        this.product_description = productDescription;
        this.price = price;
        this.discountedPrice = discountedPrice;
        this.pictureProduct = pictureProduct;
        this.brand = brand;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public String getName() {
        return name;
    }

    public String getProduct_description() {
        return product_description;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(product_id, product.product_id) && Objects.equals(name, product.name) && Objects.equals(product_description, product.product_description) && Objects.equals(price, product.price) && Objects.equals(discountedPrice, product.discountedPrice) && Objects.equals(pictureProduct, product.pictureProduct) && Objects.equals(brand, product.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product_id, name, product_description, price, discountedPrice, pictureProduct, brand);
    }


}


