package es.VetUp.tienda_back.b_domain.service.impl;

import es.VetUp.tienda_back.b_domain.mapper.ProductMapper;
import es.VetUp.tienda_back.b_domain.model.Page;
import es.VetUp.tienda_back.b_domain.repository.ProductRepository;
import es.VetUp.tienda_back.b_domain.repository.entity.ProductEntity;
import es.VetUp.tienda_back.b_domain.service.ProductService;
import es.VetUp.tienda_back.b_domain.service.dto.ProductDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<ProductDto> getAllProducts(int page, int size) {
        if (page < 1 || size < 1) {
            throw new IllegalArgumentException("Page and size must be greater than 0");
        }
        Page<ProductEntity> productEntityPage = productRepository
                .findAllProducts(page, size);
        List<ProductDto> itemsDto = productEntityPage.data()
                .stream()
                .map(ProductMapper.getInstance()::fromProductEntitytoProduct)
                .map(ProductMapper.getInstance()::fromProducttoProductDto)
                .toList();
        return new Page<>(
                itemsDto,
                productEntityPage.pageNumber(),
                productEntityPage.pageSize(),
                productEntityPage.totalElements()
        );
    }

    @Override
    public ProductDto getProductById(Long productId) {
        return productRepository.findProductById(productId).map(ProductMapper.getInstance()::fromProductEntitytoProduct)
                .map(ProductMapper.getInstance()::fromProducttoProductDto)
                .orElseThrow(() -> new RuntimeException("Product with id " + productId + " not found"));
    }

    @Override
    public Optional<ProductDto> findProductById(Long productId) {
        return productRepository.findProductById(productId)
                .map(ProductMapper.getInstance()::fromProductEntitytoProduct)
                .map(ProductMapper.getInstance()::fromProducttoProductDto);
    }

    @Override
    public List<ProductDto> getProductByCategory(int categoryId, int page, int size) {
        return productRepository.getProductByCategory(categoryId, page, size)
                .stream()
                .map(ProductMapper.getInstance()::fromProductEntitytoProduct)
                .map(ProductMapper.getInstance()::fromProducttoProductDto)
                .toList();
    }

    @Override
    public List<ProductDto> getProductByBrand(String brand, int page, int size) {
        List<ProductDto> result = productRepository.getProductByBrand(brand, page, size)
                .stream()
                .map(ProductMapper.getInstance()::fromProductEntitytoProduct)
                .map(ProductMapper.getInstance()::fromProducttoProductDto)
                .toList();
        if (result.isEmpty()) {
            throw new es.VetUp.tienda_back.b_domain.exception.ResourceNotFoundException("No products found for brand " + brand);
        }
        return result;
    }

    @Override
    @Transactional
        public ProductDto createProduct(ProductDto productDto) {
                if (productDto == null) {
                        throw new IllegalArgumentException("ProductDto cannot be null");
                }
                ProductEntity productEntity = ProductMapper.getInstance().fromProducttoProductEntity(ProductMapper.getInstance().fromProductDtotoProduct(productDto));
                ProductEntity createdProductEntity = productRepository.saveProduct(productEntity);
                return ProductMapper.getInstance().fromProducttoProductDto(
                                ProductMapper.getInstance().fromProductEntitytoProduct(createdProductEntity)
                );
        }

    @Override
    @Transactional
    public ProductDto updateProduct(Long productId, ProductDto productDto) {
        productRepository.findProductById(productDto.productId())
                .orElseThrow(() -> new RuntimeException("Product with id " + productDto.productId() + " does not exist"));

        ProductEntity productEntityToUpdate = ProductMapper.getInstance()
                .fromProducttoProductEntity(
                        ProductMapper.getInstance()
                                .fromProductDtotoProduct(productDto)
                );
        return ProductMapper.getInstance()
                .fromProducttoProductDto(
                        ProductMapper.getInstance()
                                .fromProductEntitytoProduct(
                                        productRepository.updateProduct(productId, productEntityToUpdate)
                                )
                );
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        Optional<ProductDto> existingProductDto = findProductById(productId);
        if (existingProductDto.isEmpty()) {
            throw new RuntimeException("Product with id " + productId + " does not exist");
        }
        productRepository.deleteProduct(productId);
    }
}
