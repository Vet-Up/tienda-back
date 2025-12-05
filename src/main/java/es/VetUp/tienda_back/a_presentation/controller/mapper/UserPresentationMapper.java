package es.VetUp.tienda_back.a_presentation.controller.mapper;

import es.VetUp.tienda_back.a_presentation.controller.webModel.request.UserInsertRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.UserUpdateRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.response.UserDetailResponse;
import es.VetUp.tienda_back.b_domain.mapper.UserMapper;
import es.VetUp.tienda_back.b_domain.service.dto.UserDto;

public class UserPresentationMapper {


    private static UserPresentationMapper INSTANCE;
    private UserPresentationMapper() {
    }
    public static UserPresentationMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserPresentationMapper();
        }
        return INSTANCE;
    }

    public UserDetailResponse fromUserDtoToUserDetailResponse(UserDto userDto) {
        return new UserDetailResponse(
                userDto.id(),
                userDto.name(),
                userDto.username(),
                userDto.email(),
                userDto.address(),
                userDto.isAdmin(),
                userDto.phone(),
                userDto.country(),
                userDto.profilePicture(),
                userDto.birthdate()
        );
    }

    public UserDto fromUserInsertRequestToUserDto(UserInsertRequest userInsertRequest) {
        return new UserDto(
                null,
                userInsertRequest.name(),
                userInsertRequest.username(),
                userInsertRequest.email(),
                userInsertRequest.password(),
                userInsertRequest.address(),
                userInsertRequest.isAdmin(),
                userInsertRequest.phone(),
                userInsertRequest.country(),
                userInsertRequest.profilePicture(),
                userInsertRequest.birthdate()
        );
    }

    public UserDto fromUserUpdateRequestToUserDto(UserUpdateRequest userUpdateRequest) {
        return new UserDto(
                userUpdateRequest.id(),
                userUpdateRequest.name(),
                userUpdateRequest.username(),
                userUpdateRequest.email(),
                userUpdateRequest.password(),
                userUpdateRequest.address(),
                userUpdateRequest.isAdmin(),
                userUpdateRequest.phone(),
                userUpdateRequest.country(),
                userUpdateRequest.profilePicture(),
                userUpdateRequest.birthdate()
        );
    }

}
