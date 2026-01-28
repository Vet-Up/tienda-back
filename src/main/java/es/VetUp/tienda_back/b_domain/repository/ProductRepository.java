package es.VetUp.tienda_back.b_domain.repository;

import es.VetUp.tienda_back.b_domain.model.Page;
import es.VetUp.tienda_back.b_domain.repository.entity.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Page<ProductEntity> findAllProducts(int page, int size);
    Optional<ProductEntity> findProductById(Long productId);
    List<ProductEntity> getProductByCategory(int categoryId, int page, int size);
    List<ProductEntity> getProductByBrand(String brand, int page, int size);
    ProductEntity saveProduct(ProductEntity productEntity);
    ProductEntity updateProduct(Long productId, ProductEntity productEntity);
    void deleteProduct(Long productId);
    boolean existsByCategoryId(Long categoryId);
    List<ProductEntity> findProductsByName(String name, int page, int size, String sort);
    List<ProductEntity> findAllOrdered(String order, int page, int size);
    Page<ProductEntity> getProductsByPriceRange(double minPrice, double maxPrice, int page, int size, String order);
    long count();
    List<ProductEntity> findByCategoryIds(List<Integer> categoryIds, int page, int size, String order);



}
