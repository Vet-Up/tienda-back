package es.VetUp.tienda_back.b_domain.service;

import es.VetUp.tienda_back.b_domain.service.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> getAllUsers();
    Optional<UserDto> getUserById(Long id);
    Optional<UserDto> getUserByEmail(String email);
    UserDto getUserByUsername(String username);
    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto);
    void deleteUser(Long id);
}
