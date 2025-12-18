package es.VetUp.tienda_back.b_domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.annotation.Target;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CategoryTest {

    @Test
    @DisplayName("Test Category Creation")
    void testCategoryCreation() {
        Long categoryId = 1L;
        String name = "Food";
        String description = "All kinds of pet food";

        Category category = assertDoesNotThrow(() -> new Category(categoryId, name, description));

        assertAll("category",
                () -> assertEquals(categoryId, category.getCategoryId()),
                () -> assertEquals(name,  category.getName()),
                () -> assertEquals(description, category.getDescription()));
    }
}