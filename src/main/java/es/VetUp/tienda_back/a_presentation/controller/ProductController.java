package es.VetUp.tienda_back.a_presentation.controller;

import es.VetUp.tienda_back.a_presentation.controller.mapper.ProductPresentationMapper;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.ProductInsertRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.ProductUpdateRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.response.ProductDetailResponse;
import es.VetUp.tienda_back.a_presentation.controller.webModel.response.ProductSummaryResponse;
import es.VetUp.tienda_back.b_domain.model.Page;
import es.VetUp.tienda_back.b_domain.service.ProductService;
import es.VetUp.tienda_back.b_domain.service.dto.ProductDto;
import es.VetUp.tienda_back.config.annotation.RequireAdmin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductSummaryResponse>> findAllProducts(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "20") int size) {
        Page<ProductDto> productDtoPage = productService.getAllProducts(page, size);

        List<ProductSummaryResponse> productSummaries = productDtoPage.data().stream()
                .map(ProductPresentationMapper::fromProductDtoToProductSummaryResponse)
                .toList();

        Page<ProductSummaryResponse> productSummaryPage = new Page<>(
                productSummaries,
                productDtoPage.pageNumber(),
                productDtoPage.pageSize(),
                productDtoPage.totalElements());
        return new ResponseEntity<>(productSummaryPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailResponse> getProductById(@PathVariable long id) {
        ProductDto productDto = productService.getProductById(id);
        ProductDetailResponse productDetailResponse = ProductPresentationMapper.getInstance()
                .fromProductDtoToToProductDetailResponse(productDto);
        return new ResponseEntity<>(productDetailResponse, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductSummaryResponse>> getProductsByCategory(@PathVariable int categoryId,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        List<ProductDto> productDtos = productService.getProductByCategory(categoryId, page, size);
        List<ProductSummaryResponse> productSummaries = productDtos.stream()
                .map(ProductPresentationMapper::fromProductDtoToProductSummaryResponse)
                .toList();
        return new ResponseEntity<>(productSummaries, HttpStatus.OK);
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<ProductSummaryResponse>> getProductsByBrand(@PathVariable String brand,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        List<ProductDto> productDtos = productService.getProductByBrand(brand, page, size);
        List<ProductSummaryResponse> productSummaries = productDtos.stream()
                .map(ProductPresentationMapper::fromProductDtoToProductSummaryResponse)
                .toList();
        return new ResponseEntity<>(productSummaries, HttpStatus.OK);
    }

    @RequireAdmin
    @PostMapping
    public ResponseEntity<ProductDetailResponse> createProduct(@RequestBody ProductInsertRequest productInsertRequest) {
        ProductDto productDto = ProductPresentationMapper.getInstance()
                .fromProductInsertRequestToProductDto(productInsertRequest);
        ProductDto createdProductDto = productService.createProduct(productDto);
        return new ResponseEntity<>(
                ProductPresentationMapper.getInstance().fromProductDtoToToProductDetailResponse(createdProductDto),
                HttpStatus.CREATED);
    }

    @RequireAdmin
    @PutMapping("/{id}")
    public ResponseEntity<ProductDetailResponse> updateProduct(@PathVariable("id") long id,
            @RequestBody ProductUpdateRequest productUpdateRequest) {
        ProductDto productDto = ProductPresentationMapper.getInstance()
                .fromProductUpdateRequestToProductDto(productUpdateRequest);
        ProductDto updatedProductDto = productService.updateProduct(id, productDto);
        return new ResponseEntity<>(
                ProductPresentationMapper.getInstance().fromProductDtoToToProductDetailResponse(updatedProductDto),
                HttpStatus.OK);
    }

    @RequireAdmin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ordered")
    public ResponseEntity<Page<ProductSummaryResponse>> getProductsOrdered(
            @RequestParam String order,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "20") int size) {
        Page<ProductDto> productDtoPage = productService.findAllOrdered(order, page, size);
        List<ProductSummaryResponse> productSummaries = productDtoPage.data().stream()
                .map(ProductPresentationMapper::fromProductDtoToProductSummaryResponse)
                .toList();
        Page<ProductSummaryResponse> productSummaryPage = new Page<>(
                productSummaries,
                productDtoPage.pageNumber(),
                productDtoPage.pageSize(),
                productDtoPage.totalElements()
        );
        return new ResponseEntity<>(productSummaryPage, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductSummaryResponse>> searchProductsByName(
            @RequestParam String name,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "20") int size,
            @RequestParam(required = false) String order) {
        int pageIndex = Math.max(page - 1, 0);
        List<ProductDto> productDtos = productService.findProductsByName(name, pageIndex, size, order);
        List<ProductSummaryResponse> productSummaries = productDtos.stream()
                .map(ProductPresentationMapper::fromProductDtoToProductSummaryResponse)
                .toList();
        return new ResponseEntity<>(productSummaries, HttpStatus.OK);
    }

    @GetMapping("/price-range")
    public ResponseEntity<Page<ProductSummaryResponse>> getProductsByPriceRange(
            @RequestParam double minPrice,
            @RequestParam double maxPrice,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "20") int size,
            @RequestParam(required = false) String order) {
        int pageIndex = Math.max(page - 1, 0);
        Page<ProductDto> productDtoPage = productService.getProductsByPriceRange(minPrice, maxPrice, pageIndex, size, order);
        List<ProductSummaryResponse> productSummaries = productDtoPage.data().stream()
                .map(ProductPresentationMapper::fromProductDtoToProductSummaryResponse)
                .toList();
        Page<ProductSummaryResponse> productSummaryPage = new Page<>(
                productSummaries,
                productDtoPage.pageNumber() + 1, // devolver al front la página en base 1
                productDtoPage.pageSize(),
                productDtoPage.totalElements()
        );
        return new ResponseEntity<>(productSummaryPage, HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<Page<ProductSummaryResponse>> getProductsByCategories(
            @RequestParam List<Integer> categoryIds,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "20") int size,
            @RequestParam(required = false) String order) {
        int pageIndex = Math.max(page - 1, 0);
        Page<ProductDto> productDtoPage = productService.getProductsByCategories(categoryIds, pageIndex, size, order);
        List<ProductSummaryResponse> productSummaries = productDtoPage.data().stream()
                .map(ProductPresentationMapper::fromProductDtoToProductSummaryResponse)
                .toList();
        Page<ProductSummaryResponse> productSummaryPage = new Page<>(
                productSummaries,
                productDtoPage.pageNumber() + 1, // devolver al front la página en base 1
                productDtoPage.pageSize(),
                productDtoPage.totalElements());
        return new ResponseEntity<>(productSummaryPage, HttpStatus.OK);
    }

}
