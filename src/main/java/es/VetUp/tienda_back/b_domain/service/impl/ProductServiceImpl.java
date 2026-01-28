package es.VetUp.tienda_back.b_domain.service.impl;

import es.VetUp.tienda_back.b_domain.mapper.ProductMapper;
import es.VetUp.tienda_back.b_domain.model.Page;
import es.VetUp.tienda_back.b_domain.model.Product;
import es.VetUp.tienda_back.b_domain.repository.ProductRepository;
import es.VetUp.tienda_back.b_domain.repository.ReviewRepository;
import es.VetUp.tienda_back.b_domain.repository.entity.ProductEntity;
import es.VetUp.tienda_back.b_domain.service.ProductService;
import es.VetUp.tienda_back.b_domain.service.dto.ProductDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {
        private final ProductRepository productRepository;
        private final ReviewRepository reviewRepository;

        public ProductServiceImpl(ProductRepository productRepository, ReviewRepository reviewRepository) {
                this.productRepository = productRepository;
                this.reviewRepository = reviewRepository;
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
                                .map(entity -> {
                                    var product = ProductMapper.getInstance().fromProductEntitytoProduct(entity);
                                    Double avg = reviewRepository.averageRatingByProductId(entity.productId());
                                    Integer count = (int) reviewRepository.countByProductId(entity.productId());
                                    Product productWithRatings = new Product(
                                        product.getProductId(),
                                        product.getName(),
                                        product.getProductDescription(),
                                        product.getBasePrice(),
                                        product.getDiscount(),
                                        product.getPictureProduct(),
                                        product.getBrand(),
                                        product.getCategoryId(),
                                        product.getStock(),
                                        avg,
                                        count
                                    );
                                    return ProductMapper.getInstance().fromProducttoProductDto(productWithRatings);
                                })
                                .toList();
                return new Page<>(
                                itemsDto,
                                productEntityPage.pageNumber(),
                                productEntityPage.pageSize(),
                                productEntityPage.totalElements());
        }

        @Override
        public ProductDto getProductById(Long productId) {
                return productRepository.findProductById(productId)
                                .map(entity -> {
                                    var product = ProductMapper.getInstance().fromProductEntitytoProduct(entity);
                                    Double avg = reviewRepository.averageRatingByProductId(entity.productId());
                                    Integer count = (int) reviewRepository.countByProductId(entity.productId());
                                    Product productWithRatings = new Product(
                                        product.getProductId(),
                                        product.getName(),
                                        product.getProductDescription(),
                                        product.getBasePrice(),
                                        product.getDiscount(),
                                        product.getPictureProduct(),
                                        product.getBrand(),
                                        product.getCategoryId(),
                                        product.getStock(),
                                        avg,
                                        count
                                    );
                                    return ProductMapper.getInstance().fromProducttoProductDto(productWithRatings);
                                })
                                .orElseThrow(() -> new RuntimeException("Product with id " + productId + " not found"));
        }

        @Override
        public Optional<ProductDto> findProductById(Long productId) {
                return productRepository.findProductById(productId)
                                .map(entity -> {
                                    var product = ProductMapper.getInstance().fromProductEntitytoProduct(entity);
                                    Double avg = reviewRepository.averageRatingByProductId(entity.productId());
                                    Integer count = (int) reviewRepository.countByProductId(entity.productId());
                                    Product productWithRatings = new Product(
                                        product.getProductId(),
                                        product.getName(),
                                        product.getProductDescription(),
                                        product.getBasePrice(),
                                        product.getDiscount(),
                                        product.getPictureProduct(),
                                        product.getBrand(),
                                        product.getCategoryId(),
                                        product.getStock(),
                                        avg,
                                        count
                                    );
                                    return ProductMapper.getInstance().fromProducttoProductDto(productWithRatings);
                                });
        }

        @Override
        public List<ProductDto> getProductByCategory(int categoryId, int page, int size) {
                return productRepository.getProductByCategory(categoryId, page, size)
                                .stream()
                                .map(entity -> {
                                    var product = ProductMapper.getInstance().fromProductEntitytoProduct(entity);
                                    Double avg = reviewRepository.averageRatingByProductId(entity.productId());
                                    Integer count = (int) reviewRepository.countByProductId(entity.productId());
                                    Product productWithRatings = new Product(
                                        product.getProductId(),
                                        product.getName(),
                                        product.getProductDescription(),
                                        product.getBasePrice(),
                                        product.getDiscount(),
                                        product.getPictureProduct(),
                                        product.getBrand(),
                                        product.getCategoryId(),
                                        product.getStock(),
                                        avg,
                                        count
                                    );
                                    return ProductMapper.getInstance().fromProducttoProductDto(productWithRatings);
                                })
                                .toList();
        }

        @Override
        public List<ProductDto> getProductByBrand(String brand, int page, int size) {
                List<ProductDto> result = productRepository.getProductByBrand(brand, page, size)
                                .stream()
                                .map(entity -> {
                                    var product = ProductMapper.getInstance().fromProductEntitytoProduct(entity);
                                    Double avg = reviewRepository.averageRatingByProductId(entity.productId());
                                    Integer count = (int) reviewRepository.countByProductId(entity.productId());
                                    Product productWithRatings = new Product(
                                        product.getProductId(),
                                        product.getName(),
                                        product.getProductDescription(),
                                        product.getBasePrice(),
                                        product.getDiscount(),
                                        product.getPictureProduct(),
                                        product.getBrand(),
                                        product.getCategoryId(),
                                        product.getStock(),
                                        avg,
                                        count
                                    );
                                    return ProductMapper.getInstance().fromProducttoProductDto(productWithRatings);
                                })
                                .toList();
                if (result.isEmpty()) {
                        throw new es.VetUp.tienda_back.b_domain.exception.ResourceNotFoundException(
                                        "No products found for brand " + brand);
                }
                return result;
        }

        @Override
        @Transactional
        public ProductDto createProduct(ProductDto productDto) {
                if (productDto == null) {
                        throw new IllegalArgumentException("ProductDto cannot be null");
                }
                ProductEntity productEntity = ProductMapper.getInstance().fromProducttoProductEntity(
                                ProductMapper.getInstance().fromProductDtotoProduct(productDto));
                ProductEntity createdProductEntity = productRepository.saveProduct(productEntity);
                return ProductMapper.getInstance().fromProducttoProductDto(
                                ProductMapper.getInstance().fromProductEntitytoProduct(createdProductEntity));
        }

        @Override
        @Transactional
        public ProductDto updateProduct(Long productId, ProductDto productDto) {
                productRepository.findProductById(productDto.productId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Product with id " + productDto.productId() + " does not exist"));

                ProductEntity productEntityToUpdate = ProductMapper.getInstance()
                                .fromProducttoProductEntity(
                                                ProductMapper.getInstance()
                                                                .fromProductDtotoProduct(productDto));
                return ProductMapper.getInstance()
                                .fromProducttoProductDto(
                                                ProductMapper.getInstance()
                                                                .fromProductEntitytoProduct(
                                                                                productRepository.updateProduct(
                                                                                                productId,
                                                                                                productEntityToUpdate)));
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

    @Override
    public List<ProductDto> findProductsByName(String name, int page, int size, String sort) {
        return productRepository.findProductsByName(name, page, size, sort)
                .stream()
                .map(entity -> {
                    var product = ProductMapper.getInstance().fromProductEntitytoProduct(entity);
                    Double avg = reviewRepository.averageRatingByProductId(entity.productId());
                    Integer count = (int) reviewRepository.countByProductId(entity.productId());
                    Product productWithRatings = new Product(
                        product.getProductId(),
                        product.getName(),
                        product.getProductDescription(),
                        product.getBasePrice(),
                        product.getDiscount(),
                        product.getPictureProduct(),
                        product.getBrand(),
                        product.getCategoryId(),
                        product.getStock(),
                        avg,
                        count
                    );
                    return ProductMapper.getInstance().fromProducttoProductDto(productWithRatings);
                })
                .toList();
    }



        @Override
        public Page<ProductDto> findAllOrdered(String order, int page, int size) {
            List<ProductEntity> entities = productRepository.findAllOrdered(order, page, size);
            long totalElements = productRepository.count();
            List<ProductDto> dtos = entities.stream()
                    .map(entity -> {
                        var product = ProductMapper.getInstance().fromProductEntitytoProduct(entity);
                        Double avg = reviewRepository.averageRatingByProductId(entity.productId());
                        Integer count = (int) reviewRepository.countByProductId(entity.productId());
                        Product productWithRatings = new Product(
                            product.getProductId(),
                            product.getName(),
                            product.getProductDescription(),
                            product.getBasePrice(),
                            product.getDiscount(),
                            product.getPictureProduct(),
                            product.getBrand(),
                            product.getCategoryId(),
                            product.getStock(),
                            avg,
                            count
                        );
                        return ProductMapper.getInstance().fromProducttoProductDto(productWithRatings);
                    })
                    .toList();
            return new Page<>(dtos, page, size, totalElements);
        }

    @Override
    public Page<ProductDto> getProductsByPriceRange(double minPrice, double maxPrice, int page, int size, String order) {
        Page<ProductEntity> productEntityPage = productRepository.getProductsByPriceRange(minPrice, maxPrice, page, size, order);
        List<ProductDto> itemsDto = productEntityPage.data()
                .stream()
                .map(entity -> {
                    var product = ProductMapper.getInstance().fromProductEntitytoProduct(entity);
                    Double avg = reviewRepository.averageRatingByProductId(entity.productId());
                    Integer count = (int) reviewRepository.countByProductId(entity.productId());
                    Product productWithRatings = new Product(
                        product.getProductId(),
                        product.getName(),
                        product.getProductDescription(),
                        product.getBasePrice(),
                        product.getDiscount(),
                        product.getPictureProduct(),
                        product.getBrand(),
                        product.getCategoryId(),
                        product.getStock(),
                        avg,
                        count
                    );
                    return ProductMapper.getInstance().fromProducttoProductDto(productWithRatings);
                })
                .toList();
        return new Page<>(
                itemsDto,
                productEntityPage.pageNumber(),
                productEntityPage.pageSize(),
                productEntityPage.totalElements());
    }

    @Override
    public Page<ProductDto> getProductsByCategories(List<Integer> categoryIds, int page, int size, String order) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            throw new IllegalArgumentException("categoryIds no puede ser vacío");
        }
        if (page < 0 || size < 1) {
            throw new IllegalArgumentException("page debe ser >= 0 y size > 0");
        }
        List<ProductEntity> entities = productRepository.findByCategoryIds(categoryIds, page, size, order);
        long totalElements = entities.size(); // Mejorable: contar total real si hay paginación en BD
        List<ProductDto> dtos = entities.stream()
                .map(entity -> {
                    var product = ProductMapper.getInstance().fromProductEntitytoProduct(entity);
                    Double avg = reviewRepository.averageRatingByProductId(entity.productId());
                    Integer count = (int) reviewRepository.countByProductId(entity.productId());
                    Product productWithRatings = new Product(
                        product.getProductId(),
                        product.getName(),
                        product.getProductDescription(),
                        product.getBasePrice(),
                        product.getDiscount(),
                        product.getPictureProduct(),
                        product.getBrand(),
                        product.getCategoryId(),
                        product.getStock(),
                        avg,
                        count
                    );
                    return ProductMapper.getInstance().fromProducttoProductDto(productWithRatings);
                })
                .toList();
        return new Page<>(dtos, page, size, totalElements);
    }
}
