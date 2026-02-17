package es.VetUp.tienda_back.a_presentation.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import es.VetUp.tienda_back.b_domain.service.ProductService;
import es.VetUp.tienda_back.b_domain.service.JwtService;
import es.VetUp.tienda_back.b_domain.service.dto.ProductDto;
import es.VetUp.tienda_back.b_domain.model.Page;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockitoBean
        private ProductService productService;

        @MockitoBean
        private JwtService jwtService;

        private ProductDto productDto1;
        private ProductDto productDto2;

        @BeforeEach
        void setUp() {
                productDto1 = new ProductDto(
                                1L,
                                "Producto 1",
                                "Descripción producto 1",
                                new BigDecimal("19.99"),
                                new BigDecimal("5.00"),
                                new BigDecimal("15.99"),
                                "imagen1.jpg",
                                "Marca1",
                                1L,
                                100,
                                null,
                                null);

                productDto2 = new ProductDto(
                                2L,
                                "Producto 2",
                                "Descripción producto 2",
                                new BigDecimal("29.99"),
                                new BigDecimal("5.00"),
                                new BigDecimal("25.99"),
                                "imagen2.jpg",
                                "Marca2",
                                2L,
                                100,
                                null,
                                null);
        }

        @Test
        @DisplayName("GET /api/products - Success")
        void testGetAllProductsSuccess() throws Exception {
                Page<ProductDto> productPage = new Page<>(
                                List.of(productDto1, productDto2),
                                1,
                                10,
                                2);

                when(productService.getAllProducts(1, 10)).thenReturn(productPage);

                mockMvc.perform(get("/api/products")
                                .param("page", "1")
                                .param("size", "10"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.data.length()").value(2))
                                .andExpect(jsonPath("$.data[0].name").value("Producto 1"))
                                .andExpect(jsonPath("$.data[1].name").value("Producto 2"))
                                .andExpect(jsonPath("$.pageNumber").value(1))
                                .andExpect(jsonPath("$.pageSize").value(10))
                                .andExpect(jsonPath("$.totalElements").value(2));
        }

        @Test
        @DisplayName("GET /api/products/{id} - Success")
        void testGetProductByIdSuccess() throws Exception {
                when(productService.getProductById(1L)).thenReturn(productDto1);

                mockMvc.perform(get("/api/products/{id}", 1L))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value("Producto 1"))
                                .andExpect(jsonPath("$.productDescription").value("Descripción producto 1"))
                                .andExpect(jsonPath("$.basePrice").value(19.99))
                                .andExpect(jsonPath("$.discount").value(5.00))
                                .andExpect(jsonPath("$.price").value(15.99))
                                .andExpect(jsonPath("$.pictureProduct").value("imagen1.jpg"))
                                .andExpect(jsonPath("$.brand").value("Marca1"))
                                .andExpect(jsonPath("$.categoryId").value(1))
                                .andExpect(jsonPath("$.stock").value(100));
        }

        @Test
        @DisplayName("GET /api/products/category/{categoryId} - Success")
        void testGetProductsByCategorySuccess() throws Exception {
                when(productService.getProductByCategory(1, 1, 10)).thenReturn(List.of(productDto1));

                mockMvc.perform(get("/api/products/category/{categoryId}", 1)
                                .param("page", "1")
                                .param("size", "10"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(1))
                                .andExpect(jsonPath("$[0].name").value("Producto 1"));
        }

        @Test
        @DisplayName("GET /api/products/brand/{brand} - Success")
        void testGetProductsByBrandSuccess() throws Exception {
                when(productService.getProductByBrand("Marca1", 1, 10)).thenReturn(List.of(productDto1));

                mockMvc.perform(get("/api/products/brand/{brand}", "Marca1")
                                .param("page", "1")
                                .param("size", "10"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(1))
                                .andExpect(jsonPath("$[0].name").value("Producto 1"));
        }

        @Test
        @DisplayName("GET /api/products/ordered - Success")
        void testGetProductsOrderedSuccess() throws Exception {
                Page<ProductDto> productPage = new Page<>(
                                List.of(productDto1, productDto2),
                                1,
                                10,
                                2);

                when(productService.findAllOrdered("priceAsc", 1, 10)).thenReturn(productPage);

                mockMvc.perform(get("/api/products/ordered")
                                .param("order", "priceAsc")
                                .param("page", "1")
                                .param("size", "10"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.data.length()").value(2))
                                .andExpect(jsonPath("$.data[0].name").value("Producto 1"));
        }

        @Test
        @DisplayName("GET /api/products/search - Success")
        void testSearchProductsByNameSuccess() throws Exception {
                when(productService.findProductsByName("Producto", 0, 10, null))
                                .thenReturn(List.of(productDto1, productDto2));

                mockMvc.perform(get("/api/products/search")
                                .param("name", "Producto")
                                .param("page", "1")
                                .param("size", "10"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(2))
                                .andExpect(jsonPath("$[0].name").value("Producto 1"));
        }

        @Test
        @DisplayName("GET /api/products/price-range - Success")
        void testGetProductsByPriceRangeSuccess() throws Exception {
                Page<ProductDto> productPage = new Page<>(
                                List.of(productDto1),
                                0, // Service returns 0-based page index
                                10,
                                1);

                when(productService.getProductsByPriceRange(10.0, 20.0, 0, 10, null)).thenReturn(productPage);

                mockMvc.perform(get("/api/products/price-range")
                                .param("minPrice", "10.0")
                                .param("maxPrice", "20.0")
                                .param("page", "1")
                                .param("size", "10"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.data.length()").value(1))
                                .andExpect(jsonPath("$.pageNumber").value(1)) // Controller converts to 1-based
                                .andExpect(jsonPath("$.data[0].name").value("Producto 1"));
        }

        @Test
        @DisplayName("GET /api/products/categories - Success")
        void testGetProductsByCategoriesSuccess() throws Exception {
                Page<ProductDto> productPage = new Page<>(
                                List.of(productDto1),
                                0, // Service returns 0-based page index
                                10,
                                1);

                when(productService.getProductsByCategories(List.of(1, 2), 0, 10, null)).thenReturn(productPage);

                mockMvc.perform(get("/api/products/categories")
                                .param("categoryIds", "1,2")
                                .param("page", "1")
                                .param("size", "10"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.data.length()").value(1))
                                .andExpect(jsonPath("$.pageNumber").value(1)) // Controller converts to 1-based
                                .andExpect(jsonPath("$.data[0].name").value("Producto 1"));
        }

        @Test
        @DisplayName("POST /api/products - Success")
        void testCreateProductSuccess() throws Exception {
                ProductDto createdProductDto = new ProductDto(
                                3L,
                                "Producto 3",
                                "Descripción producto 3",
                                new BigDecimal("39.99"),
                                new BigDecimal("5.00"),
                                new BigDecimal("35.99"),
                                "imagen3.jpg",
                                "Marca3",
                                3L,
                                50,
                                null,
                                null);

                when(productService.createProduct(any(ProductDto.class))).thenReturn(createdProductDto);

                String productInsertRequestJson = """
                                {
                                    "name": "Producto 3",
                                    "productDescription": "Descripción producto 3",
                                    "basePrice": 39.99,
                                    "discount": 5.00,
                                    "pictureProduct": "imagen3.jpg",
                                    "brand": "Marca3",
                                    "categoryId": 3,
                                    "stock": 50
                                }
                                """;

                mockMvc.perform(post("/api/products")
                                .contentType("application/json")
                                .content(productInsertRequestJson))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.productId").value(3))
                                .andExpect(jsonPath("$.name").value("Producto 3"));
        }

        @Test
        @DisplayName("PUT /api/products/{id} - Success")
        void testUpdateProductSuccess() throws Exception {
                ProductDto updatedProductDto = new ProductDto(
                                1L,
                                "Producto 1 Actualizado",
                                "Descripción producto 1 actualizada",
                                new BigDecimal("21.99"),
                                new BigDecimal("5.00"),
                                new BigDecimal("17.99"),
                                "imagen1_updated.jpg",
                                "Marca1",
                                1L,
                                100,
                                null,
                                null);

                when(productService.updateProduct(eq(1L), any(ProductDto.class))).thenReturn(updatedProductDto);

                String productUpdateRequestJson = """
                                {
                                    "productId": 1,
                                    "name": "Producto 1 Actualizado",
                                    "productDescription": "Descripción producto 1 actualizada",
                                    "basePrice": 21.99,
                                    "discount": 5.00,
                                    "pictureProduct": "imagen1_updated.jpg",
                                    "brand": "Marca1",
                                    "categoryId": 1,
                                    "stock": 100
                                }
                                """;

                mockMvc.perform(put("/api/products/{id}", 1L)
                                .contentType("application/json")
                                .content(productUpdateRequestJson))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value("Producto 1 Actualizado"))
                                .andExpect(jsonPath("$.productDescription")
                                                .value("Descripción producto 1 actualizada"));
        }

        @Test
        @DisplayName("DELETE /api/products/{id} - Success")
        void testDeleteProductSuccess() throws Exception {
                mockMvc.perform(delete("/api/products/{id}", 343L))
                                .andExpect(status().isNoContent());
        }
}
