package es.VetUp.tienda_back.config;

import es.VetUp.tienda_back.b_domain.model.User;
import es.VetUp.tienda_back.b_domain.repository.UserRepository;
import es.VetUp.tienda_back.b_domain.service.impl.UserServiceImpl;
import es.VetUp.tienda_back.c_persistence.dao.jpa.UserJpaDao;
import es.VetUp.tienda_back.c_persistence.dao.jpa.impl.UserJpaDaoImpl;
import es.VetUp.tienda_back.c_persistence.repository.UserRepositoryImpl;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "es.VetUp.tienda_back.c_persistence.dao.jpa")
@EntityScan(basePackages = "es.VetUp.tienda_back.c_persistence.dao.jpa.entity")
public class SpringConfig {

    @Bean
    public UserJpaDao userJpaDao() {
        return new UserJpaDaoImpl();
    }

    @Bean
    public UserRepository userRepository(UserJpaDao userJpaDao) {
        return new UserRepositoryImpl(userJpaDao);
    }

    @Bean
    public UserServiceImpl userService(UserRepository userRepository) {
        return new UserServiceImpl(userRepository);
    }
}
