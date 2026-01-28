package es.VetUp.tienda_back.config;

import es.VetUp.tienda_back.b_domain.repository.CategoryRepository;
import es.VetUp.tienda_back.b_domain.repository.ProductRepository;
import es.VetUp.tienda_back.b_domain.repository.ReviewRepository;
import es.VetUp.tienda_back.b_domain.repository.OrderRepository;
import es.VetUp.tienda_back.b_domain.repository.UserRepository;
import es.VetUp.tienda_back.b_domain.service.JwtService;
import es.VetUp.tienda_back.b_domain.service.impl.*;
import es.VetUp.tienda_back.b_domain.service.OrderService;
import es.VetUp.tienda_back.b_domain.service.UserService;
import es.VetUp.tienda_back.c_persistence.dao.jpa.CategoryJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.ProductJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.ReviewJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.OrderJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.UserJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.impl.CategoryJpaDaoImpl;
import es.VetUp.tienda_back.c_persistence.dao.jpa.impl.ProductJpaDaoImpl;
import es.VetUp.tienda_back.c_persistence.dao.jpa.impl.ReviewJpaDaoImpl;
import es.VetUp.tienda_back.c_persistence.dao.jpa.impl.OrderJpaDaoImpl;
import es.VetUp.tienda_back.c_persistence.dao.jpa.impl.UserJpaDaoImpl;
import es.VetUp.tienda_back.c_persistence.repository.CategoryRepositoryImpl;
import es.VetUp.tienda_back.c_persistence.repository.ProductRepositoryImpl;
import es.VetUp.tienda_back.c_persistence.repository.ReviewRepositoryImpl;
import es.VetUp.tienda_back.c_persistence.repository.OrderRepositoryImpl;
import es.VetUp.tienda_back.c_persistence.repository.UserRepositoryImpl;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableJpaRepositories(basePackages = "es.VetUp.tienda_back.c_persistence.dao.jpa")
@EntityScan(basePackages = "es.VetUp.tienda_back.c_persistence.dao.jpa.entity")
public class SpringConfig {

    @Bean
    public JwtService jwtService(UserService userService) {
        return new JwtServiceImpl(userService);
    }

    @Bean
    public UserJpaDao userJpaDao() {
        return new UserJpaDaoImpl();
    }

    @Bean
    public UserRepository userRepository(UserJpaDao userJpaDao) {
        return new UserRepositoryImpl(userJpaDao);
    }

    @Bean
    public UserService userService(UserRepository userRepository, OrderService orderService,
            PasswordEncoder passwordEncoder) {
        return new UserServiceImpl(userRepository, orderService, passwordEncoder);
    }

    @Bean
    public OrderJpaDao orderJpaDao() {
        return new OrderJpaDaoImpl();
    }

    @Bean
    public OrderRepository orderRepository(OrderJpaDao orderJpaDao) {
        return new OrderRepositoryImpl(orderJpaDao);
    }

    @Bean
    public OrderService orderService(OrderRepository orderRepository) {
        return new OrderServiceImpl(orderRepository);
    }

    @Bean
    public ProductJpaDao productJpaDao() {
        return new ProductJpaDaoImpl();
    }

    @Bean
    public ProductRepository productRepository(ProductJpaDao productJpaDao) {
        return new ProductRepositoryImpl(productJpaDao);
    }

    @Bean
    public ProductServiceImpl productService(ProductRepository productRepository,ReviewRepository reviewRepository) {
        return new ProductServiceImpl(productRepository, reviewRepository);
    }

    @Bean
    public CategoryJpaDao categoryJpaDao() {
        return new CategoryJpaDaoImpl();
    }

    @Bean
    public CategoryRepository categoryRepository(CategoryJpaDao categoryJpaDao) {
        return new CategoryRepositoryImpl(categoryJpaDao);
    }

    @Bean
    public CategoryServiceImpl categoryService(CategoryRepository categoryRepository,
            ProductRepository productRepository) {
        return new CategoryServiceImpl(categoryRepository, productRepository);
    }

    @Bean
    public ReviewJpaDao reviewJpaDao() {
        return new ReviewJpaDaoImpl();
    }

    @Bean
    public ReviewRepository reviewRepository(
            ReviewJpaDao reviewJpaDao,
            ProductJpaDao productJpaDao,
            UserJpaDao userJpaDao) {
        return new ReviewRepositoryImpl(reviewJpaDao, productJpaDao, userJpaDao);
    }

    @Bean
    public ReviewServiceImpl reviewService(
            ReviewRepository reviewRepository) {
        return new ReviewServiceImpl(reviewRepository);
    }
}
