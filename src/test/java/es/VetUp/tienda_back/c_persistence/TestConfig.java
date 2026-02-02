package es.VetUp.tienda_back.c_persistence;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import es.VetUp.tienda_back.c_persistence.dao.jpa.CategoryJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.ProductJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.UserJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.impl.CategoryJpaDaoImpl;
import es.VetUp.tienda_back.c_persistence.dao.jpa.impl.ProductJpaDaoImpl;
import es.VetUp.tienda_back.c_persistence.dao.jpa.impl.UserJpaDaoImpl;

@Configuration
@EnableJpaRepositories(basePackages = "es.VetUp.tienda_back.c_persistence.dao.jpa")
@EntityScan(basePackages = "es.VetUp.tienda_back.c_persistence.dao.jpa.entity")
public class TestConfig {
    
    @Bean
    public CategoryJpaDao categoryJpaDao() {
        return new CategoryJpaDaoImpl();
    }

    @Bean
    public ProductJpaDao productJpaDao() {
        return new ProductJpaDaoImpl();
    }

    @Bean
    public UserJpaDao userJpaDao() {
        return new UserJpaDaoImpl();
    }
    
}
