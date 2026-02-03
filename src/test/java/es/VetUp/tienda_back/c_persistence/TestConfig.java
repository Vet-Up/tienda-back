package es.VetUp.tienda_back.c_persistence;

import es.VetUp.tienda_back.c_persistence.dao.jpa.*;
import es.VetUp.tienda_back.c_persistence.dao.jpa.impl.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

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

    @Bean
    public ReviewJpaDao reviewJpaDao() {
        return new ReviewJpaDaoImpl();
    }

    @Bean
    public CartJpaDao cartJpaDao() {
        return new CartJpaDaoImpl();
    }

    @Bean
    public CartItemJpaDao cartItemJpaDao() {
        return new CartItemJpaDaoImpl();
    }

    @Bean
    public OrderJpaDao orderJpaDao() {
        return new OrderJpaDaoImpl();
    }

}
