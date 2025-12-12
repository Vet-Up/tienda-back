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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrderService orderService;
    public UserServiceImpl(UserRepository userRepository, OrderService orderService) {
        this.userRepository = userRepository;
        this.orderService = orderService;
    }


    @Override
    public List<UserDto> getAllUsersnotAdmin() {
        return userRepository.getAllClientsnotAdmin()
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
    public UserDto createUser(UserDto userDto) {

        if (getUserByEmail(userDto.email()).isPresent()) {
            throw new BusinessException("User with email " + userDto.email() + " already exists");
        }

        UserEntity userEntity = UserMapper.getInstance().fromUserToUserEntity(
                UserMapper.getInstance().fromUserDtoToUser(userDto));
        UserEntity createdUserEntity = userRepository.createClient(userEntity);


        UserDto createdUserDto = UserMapper.getInstance().fromUserToUserDto(
                UserMapper.getInstance().fromUserEntityToUser(createdUserEntity));


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

        UserEntity newUserEntity = UserMapper.getInstance().fromUserToUserEntity(
                UserMapper.getInstance().fromUserDtoToUser(userDto));

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
