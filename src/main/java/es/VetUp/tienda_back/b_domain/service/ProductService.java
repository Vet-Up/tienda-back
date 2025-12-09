package es.VetUp.tienda_back.b_domain.service;

import es.VetUp.tienda_back.b_domain.model.Page;
import es.VetUp.tienda_back.b_domain.model.Product;
import es.VetUp.tienda_back.b_domain.service.dto.ProductDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Page<ProductDto> getAllProducts(int page, int size);
    ProductDto getProductById(Long productId);
    Optional<ProductDto> findProductById(Long productId);
    List<ProductDto> getProductByCategory(int categoryId, int page, int size);
    List<ProductDto> getProductByBrand(String brand, int page, int size);
    ProductDto createProduct(ProductDto productDto);
    ProductDto updateProduct(Long productId, ProductDto productDto);
    void deleteProduct(Long productId);
}
