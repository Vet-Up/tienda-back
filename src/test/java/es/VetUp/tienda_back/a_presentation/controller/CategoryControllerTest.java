package es.VetUp.tienda_back.a_presentation.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import es.VetUp.tienda_back.b_domain.service.CategoryService;
import es.VetUp.tienda_back.b_domain.service.JwtService;
import es.VetUp.tienda_back.b_domain.service.dto.CategoryDto;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoryService categoryService;

    @MockitoBean
    private JwtService jwtService;

    private CategoryDto categoryDto1;
    private CategoryDto categoryDto2;

    @BeforeEach
    void setUp() {
        categoryDto1 = new CategoryDto(
                1L,
                "Categoría 1",
                "Descripción categoría 1");

        categoryDto2 = new CategoryDto(
                2L,
                "Categoría 2",
                "Descripción categoría 2");
    }

    @Test
    @DisplayName("GET /api/categories - Success")
    void testGetAllCategoriesSuccess() throws Exception {
        when(categoryService.getAllCategories()).thenReturn(List.of(categoryDto1, categoryDto2));

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].categoryId").value(1))
                .andExpect(jsonPath("$[0].name").value("Categoría 1"))
                .andExpect(jsonPath("$[1].categoryId").value(2))
                .andExpect(jsonPath("$[1].name").value("Categoría 2"));
    }

    @Test
    @DisplayName("GET /api/categories/{id} - Success")
    void testGetCategoryByIdSuccess() throws Exception {
        when(categoryService.getCategoryById(1L)).thenReturn(categoryDto1);

        mockMvc.perform(get("/api/categories/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryId").value(1))
                .andExpect(jsonPath("$.name").value("Categoría 1"));
    }

    @Test
    @DisplayName("POST /api/categories - Success")
    void testCreateCategorySuccess() throws Exception {
        CategoryDto newCategoryDto = new CategoryDto(
                null,
                "Categoría Nueva",
                "Descripción categoría nueva");

        CategoryDto createdCategoryDto = new CategoryDto(
                3L,
                "Categoría Nueva",
                "Descripción categoría nueva");

        when(categoryService.createCategory(newCategoryDto)).thenReturn(createdCategoryDto);

        String categoryInsertRequestJson = """
                {
                    "name": "Categoría Nueva",
                    "description": "Descripción categoría nueva"
                }
                """;

        mockMvc.perform(post("/api/categories")
                .contentType("application/json")
                .content(categoryInsertRequestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.categoryId").value(3))
                .andExpect(jsonPath("$.name").value("Categoría Nueva"));
    }

    @Test
    @DisplayName("PUT /api/categories/{id} - Success")
    void testUpdateCategorySuccess() throws Exception {
        CategoryDto updatedCategoryDto = new CategoryDto(
                1L,
                "Categoría Actualizada",
                "Descripción categoría actualizada");

        when(categoryService.updateCategory(1L, updatedCategoryDto)).thenReturn(updatedCategoryDto);

        String categoryUpdateRequestJson = """
                {
                    "categoryId": 1,
                    "name": "Categoría Actualizada",
                    "description": "Descripción categoría actualizada"
                }
                """;

        mockMvc.perform(put("/api/categories/{id}", 1L)
                .contentType("application/json")
                .content(categoryUpdateRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryId").value(1))
                .andExpect(jsonPath("$.name").value("Categoría Actualizada"));
    }

    @Test
    @DisplayName("DELETE /api/categories/{id} - Success")
    void testDeleteCategorySuccess() throws Exception {
        mockMvc.perform(delete("/api/categories/{id}", 1L))
                .andExpect(status().isNoContent());
    }
    


}