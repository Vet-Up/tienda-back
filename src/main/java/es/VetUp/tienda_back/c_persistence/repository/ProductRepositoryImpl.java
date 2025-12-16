package es.VetUp.tienda_back.c_persistence.repository;

import es.VetUp.tienda_back.b_domain.model.Page;
import es.VetUp.tienda_back.b_domain.repository.ProductRepository;
import es.VetUp.tienda_back.b_domain.repository.entity.ProductEntity;
import es.VetUp.tienda_back.c_persistence.dao.jpa.ProductJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.entity.ProductJpaEntity;
import es.VetUp.tienda_back.c_persistence.repository.mapper.ProductPersistenceMapper;

import java.util.List;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {
    private final ProductJpaDao productJpaDao;

    public ProductRepositoryImpl(ProductJpaDao productDao) {
        this.productJpaDao = productDao;
    }

    @Override
    public Page<ProductEntity> findAllProducts(int page, int size) {
        List<ProductEntity> content = productJpaDao.findAll(page, size).stream().map(ProductPersistenceMapper.getInstance()::fromProductJpaEntitytoToProductEntity).toList();
        long totalElements = productJpaDao.count();
        return new Page<>(content, page, size, totalElements);
    }

    @Override
    public Optional<ProductEntity> findProductById(Long productId) {
        return productJpaDao.findById((long) productId).map(ProductPersistenceMapper.getInstance()::fromProductJpaEntitytoToProductEntity);
    }

    @Override
    public List<ProductEntity> getProductByCategory(int categoryId, int page, int size) {
        return productJpaDao.findByCategoryId(categoryId,page,size).stream().map(ProductPersistenceMapper.getInstance()::fromProductJpaEntitytoToProductEntity).toList();
    }

    @Override
    public List<ProductEntity> getProductByBrand(String brand, int page, int size) {
        return productJpaDao.findByBrandId(brand, page, size).stream().map(ProductPersistenceMapper.getInstance()::fromProductJpaEntitytoToProductEntity).toList();
    }

    @Override
    public ProductEntity saveProduct(ProductEntity productEntity) {
        ProductJpaEntity productJpaEntity = ProductPersistenceMapper.getInstance().fromProductEntitytoToProductJpaEntity(productEntity);
        if(productEntity.productId() == null){
            return ProductPersistenceMapper.getInstance().fromProductJpaEntitytoToProductEntity(productJpaDao.insert(productJpaEntity));
        }
        return ProductPersistenceMapper.getInstance().fromProductJpaEntitytoToProductEntity(productJpaDao.update(productJpaEntity));
    }

    @Override
    public ProductEntity updateProduct(Long productId, ProductEntity productEntity) {
        ProductJpaEntity productJpaEntity = ProductPersistenceMapper.getInstance().fromProductEntitytoToProductJpaEntity(productEntity);
        productJpaEntity.setProductId((long) productId);
        return ProductPersistenceMapper.getInstance().fromProductJpaEntitytoToProductEntity(productJpaDao.update(productJpaEntity));

    }

    @Override
    public void deleteProduct(Long productId) {
        productJpaDao.deleteById((long) productId);

    }

    @Override
    public boolean existsByCategoryId(Long categoryId) {
        return productJpaDao.existsByCategoryId(categoryId);
    }
}
