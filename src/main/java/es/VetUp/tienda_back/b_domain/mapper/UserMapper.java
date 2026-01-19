package es.VetUp.tienda_back.b_domain.mapper;

import es.VetUp.tienda_back.b_domain.model.User;
import es.VetUp.tienda_back.b_domain.repository.entity.UserEntity;
import es.VetUp.tienda_back.b_domain.service.dto.UserDto;

public class UserMapper {

    private static UserMapper INSTANCE;
    private UserMapper() {
    }

    public static UserMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserMapper();
        }
        return INSTANCE;
    }

    public User fromUserDtoToUser(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        return new User(
            userDto.id(),
            userDto.name(),
            userDto.username(),
            userDto.email(),
            userDto.password(),
            userDto.address(),
            userDto.isAdmin(),
            userDto.phone(),
            userDto.country(),
            userDto.profilePicture(),
            userDto.birthdate()
        );
    }

    public UserDto fromUserToUserDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(
            user.getId(),
            user.getName(),
            user.getUsername(),
            user.getEmail(),
            user.getPassword(),
            user.getAddress(),
            user.getAdmin(),
            user.getPhone(),
            user.getCountry(),
            user.getProfilePicture(),
            user.getBirthdate()
        );
    }

    public User fromUserEntityToUser(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        return new User(
            userEntity.id(),
            userEntity.name(),
            userEntity.username(),
            userEntity.email(),
            userEntity.password(),
            userEntity.address(),
            userEntity.isAdmin(),
            userEntity.phone(),
            userEntity.country(),
            userEntity.profilePicture(),
            userEntity.birthdate()

        );
    }

    public UserEntity fromUserToUserEntity(User user) {
        if (user == null) {
            return null;
        }
        return new UserEntity(
            user.getId(),
            user.getName(),
            user.getUsername(),
            user.getEmail(),
            user.getPassword(),
            user.getAddress(),
            user.getAdmin(),
            user.getPhone(),
            user.getCountry(),
            user.getProfilePicture(),
           user.getBirthdate()
        );
    }
}
