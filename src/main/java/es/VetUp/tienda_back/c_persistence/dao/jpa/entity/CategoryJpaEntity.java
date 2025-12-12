package es.VetUp.tienda_back.c_persistence.dao.jpa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_category")
public class CategoryJpaEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "category_id")
        private Long categoryId;

        @Column(name = "name", nullable = false, length = 100)
        private String name;

        @Column(name = "description", length = 255)
        private String description;

        public CategoryJpaEntity() {
        }

        public CategoryJpaEntity(Long categoryId, String name, String description) {
            this.categoryId = categoryId;
            this.name = name;
            this.description = description;
        }

        public Long getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Long categoryId) {
            this.categoryId = categoryId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

}
