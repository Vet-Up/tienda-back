package es.VetUp.tienda_back.b_domain.service.impl;

import es.VetUp.tienda_back.b_domain.exception.BusinessException;
import es.VetUp.tienda_back.b_domain.exception.ResourceNotFoundException;
import es.VetUp.tienda_back.b_domain.mapper.UserMapper;
import es.VetUp.tienda_back.b_domain.model.enums.OrderState;
import es.VetUp.tienda_back.b_domain.repository.UserRepository;
import es.VetUp.tienda_back.b_domain.repository.entity.UserEntity;
import es.VetUp.tienda_back.b_domain.service.OrderService;
import es.VetUp.tienda_back.b_domain.service.UserService;
import es.VetUp.tienda_back.b_domain.service.dto.OrderDto;
import es.VetUp.tienda_back.b_domain.service.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrderService orderService;
    private final PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepository, OrderService orderService, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.orderService = orderService;
    }


    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.getAllUsers()
                .stream()
                .map(UserMapper.getInstance()::fromUserEntityToUser)
                .map(UserMapper.getInstance()::fromUserToUserDto)
                .toList();
    }

    @Override
    public Optional<UserDto> getUserById(Long id) {
      return userRepository.getClientById(id)
              .map(UserMapper.getInstance()::fromUserEntityToUser)
              .map(UserMapper.getInstance()::fromUserToUserDto);
    }

    @Override
    public Optional<UserDto> getUserByEmail(String email) {
        return userRepository.getClientByEmail(email)
                .map(UserMapper.getInstance()::fromUserEntityToUser)
                .map(UserMapper.getInstance()::fromUserToUserDto);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        UserEntity userEntity = userRepository.getClientByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with username " + username + " not found"));

        return UserMapper.getInstance().fromUserToUserDto(
                UserMapper.getInstance().fromUserEntityToUser(userEntity));
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        if (getUserByEmail(userDto.email()).isPresent()) {
            throw new BusinessException(
                    "User with email " + userDto.email() + " already exists"
            );
        }

        String hashedPassword = passwordEncoder.encode(userDto.password());

        UserDto userDtoWithHashedPassword = new UserDto(
                userDto.id(),
                userDto.name(),
                userDto.username(),
                userDto.email(),
                hashedPassword,
                userDto.address(),
                userDto.isAdmin(),
                userDto.phone(),
                userDto.country(),
                userDto.profilePicture(),
                userDto.birthdate()

        );

        UserEntity userEntity = UserMapper.getInstance()
                .fromUserToUserEntity(
                        UserMapper.getInstance()
                                .fromUserDtoToUser(userDtoWithHashedPassword)
                );

        UserEntity createdUserEntity = userRepository.createClient(userEntity);

        UserDto createdUserDto = UserMapper.getInstance()
                .fromUserToUserDto(
                        UserMapper.getInstance()
                                .fromUserEntityToUser(createdUserEntity)
                );

        OrderDto initialOrder = new OrderDto(
                null,
                0,
                BigDecimal.ZERO,
                OrderState.CART,
                createdUserDto
        );

        orderService.createOrder(initialOrder);

        return createdUserDto;
    }



    @Override
    public UserDto updateUser(UserDto userDto) {
        userRepository.getClientById(userDto.id()).orElseThrow(
                () -> new ResourceNotFoundException("User with id " + userDto.id() + " not found")
        );
        userRepository.getClientByEmail(userDto.email())
                .filter(u -> !u.id().equals(userDto.id()))
                .ifPresent(u -> {throw new BusinessException("Another user with email " + userDto.email() + " already exists");});

        // Encriptar la contraseña si se está actualizando
        String hashedPassword = userDto.password();
        if (hashedPassword != null && !hashedPassword.startsWith("$2a$")) {
            hashedPassword = passwordEncoder.encode(hashedPassword);
        }

        UserDto userDtoWithHashedPassword = new UserDto(
                userDto.id(),
                userDto.name(),
                userDto.username(),
                userDto.email(),
                hashedPassword,
                userDto.address(),
                userDto.isAdmin(),
                userDto.phone(),
                userDto.country(),
                userDto.profilePicture(),
                userDto.birthdate()
        );

        UserEntity newUserEntity = UserMapper.getInstance().fromUserToUserEntity(
                UserMapper.getInstance().fromUserDtoToUser(userDtoWithHashedPassword));

        UserEntity updatedEntity = userRepository.updateClient(newUserEntity);

        return UserMapper.getInstance().fromUserToUserDto(
                UserMapper.getInstance().fromUserEntityToUser(updatedEntity));
    }


    @Override
    public void deleteUser(Long id) {
        userRepository.getClientById(id).orElseThrow(
                () -> new ResourceNotFoundException("User with id " + id + " not found")
        );
        userRepository.deleteClient(id);
    }


}
