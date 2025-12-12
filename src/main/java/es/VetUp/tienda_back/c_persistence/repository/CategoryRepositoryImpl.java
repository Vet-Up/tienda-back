package es.VetUp.tienda_back.c_persistence.repository;

import es.VetUp.tienda_back.b_domain.model.Page;
import es.VetUp.tienda_back.b_domain.repository.CategoryRepository;
import es.VetUp.tienda_back.b_domain.repository.entity.CategoryEntity;
import es.VetUp.tienda_back.b_domain.repository.entity.ProductEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.CategoryJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.CategoryJpaEntity;
import es.VetUp.tienda_back.c_persistence.repository.mapper.CategoryPersistenceMapper;
import es.VetUp.tienda_back.c_persistence.repository.mapper.ProductPersistenceMapper;

import java.util.List;
import java.util.Optional;

public class CategoryRepositoryImpl implements CategoryRepository {
    private final CategoryJpaDao categoryJpaDao;

    public CategoryRepositoryImpl(CategoryJpaDao categoryDao) {
        this.categoryJpaDao = categoryDao;
    }

    @Override
    public CategoryEntity saveCategory(CategoryEntity categoryEntity) {
        CategoryJpaEntity categoryJpaEntity = CategoryPersistenceMapper.getInstance().fromCategoryEntitytoToCategoryJpaEntity(categoryEntity);
        if(categoryEntity.categoryId() == null){
            return CategoryPersistenceMapper.getInstance().fromCategoryJpaEntitytoToCategoryEntity(categoryJpaDao.insert(categoryJpaEntity));
        }
        return CategoryPersistenceMapper.getInstance().fromCategoryJpaEntitytoToCategoryEntity(categoryJpaDao.update(categoryJpaEntity));
    }

    @Override
    public Optional<CategoryEntity> findCategoryById(Long categoryId) {
        return categoryJpaDao.findById((long) categoryId).map(CategoryPersistenceMapper.getInstance()::fromCategoryJpaEntitytoToCategoryEntity);
    }

    @Override
    public CategoryEntity updateCategory(Long categoryId, CategoryEntity categoryEntity) {
        CategoryJpaEntity categoryJpaEntity = CategoryPersistenceMapper.getInstance().fromCategoryEntitytoToCategoryJpaEntity(categoryEntity);
        categoryJpaEntity.setCategoryId((long) categoryId);
        return CategoryPersistenceMapper.getInstance().fromCategoryJpaEntitytoToCategoryEntity(categoryJpaDao.update(categoryJpaEntity));
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryJpaDao.deleteById((long) categoryId);

    }

    @Override
    public List<CategoryEntity> findAllCategories() {
        return categoryJpaDao.findAll().stream().map(CategoryPersistenceMapper.getInstance()::fromCategoryJpaEntitytoToCategoryEntity).toList();
    }
}
