package es.VetUp.tienda_back.b_domain.service;

import es.VetUp.tienda_back.b_domain.model.Page;
import es.VetUp.tienda_back.b_domain.service.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Page<UserDto> getAllUsers(int page, int size);
    Page<UserDto> searchByEmail(String email, int page, int size);
    Optional<UserDto> getUserById(Long id);
    Optional<UserDto> getUserByEmail(String email);
    UserDto getUserByUsername(String username);
    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto);
    void deleteUser(Long id);
}
