package es.VetUp.tienda_back.b_domain.repository;

import es.VetUp.tienda_back.b_domain.repository.entity.UserEntity;
import es.VetUp.tienda_back.b_domain.service.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<UserEntity> getAllUsers();
    Optional<UserEntity> getClientById(Long id);
    Optional<UserEntity> getClientByEmail(String email);
    Optional<UserEntity> getClientByUsername(String username);
    UserEntity createClient(UserEntity userEntity);
    UserEntity updateClient(UserEntity userEntity);
    void deleteClient(Long id);
}
