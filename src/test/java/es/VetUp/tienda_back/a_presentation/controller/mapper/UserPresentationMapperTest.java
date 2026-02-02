package es.VetUp.tienda_back.a_presentation.controller.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.VetUp.tienda_back.a_presentation.controller.webModel.request.UserInsertRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.UserUpdateRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.response.UserDetailResponse;
import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import es.VetUp.tienda_back.b_domain.service.dto.UserDto;


class UserPresentationMapperTest {

    @Test
    @DisplayName("Test map from UserDto to UserDetailResponse")
    void testFromUserDtoToUserDetailResponse() {
        UserDto userDto = new UserDto(
                1L,
                "John Doe",
                "johndoe",
                "john@example.com",
                "password123",
                "123 Main St",
                UserRole.CUSTOMER,
                123456789,
                "Spain",
                "profile.jpg",
                LocalDate.of(1990, 5, 15));

        UserDetailResponse response = UserPresentationMapper.getInstance()
                .fromUserDtoToUserDetailResponse(userDto);

        assertEquals(userDto.id(), response.id());
        assertEquals(userDto.name(), response.name());
        assertEquals(userDto.username(), response.username());
        assertEquals(userDto.email(), response.email());
        assertEquals(userDto.password(), response.password());
        assertEquals(userDto.address(), response.address());
        assertEquals(userDto.isAdmin(), response.isAdmin());
        assertEquals(userDto.phone(), response.phone());
        assertEquals(userDto.country(), response.country());
        assertEquals(userDto.profilePicture(), response.profilePicture());
        assertEquals(userDto.birthdate(), response.birthdate());
    }

    @Test
    @DisplayName("Test map from UserInsertRequest to UserDto")
    void testFromUserInsertRequestToUserDto() {
        UserInsertRequest userInsertRequest = new UserInsertRequest(
                "Jane Smith",
                "janesmith",
                "jane@example.com",
                "password456",
                "456 Oak Ave",
                UserRole.ADMIN,
                987654321,
                "USA",
                "profile2.jpg",
                LocalDate.of(1985, 8, 20));

        UserDto userDto = UserPresentationMapper.getInstance()
                .fromUserInsertRequestToUserDto(userInsertRequest);

        assertNull(userDto.id());
        assertEquals(userInsertRequest.name(), userDto.name());
        assertEquals(userInsertRequest.username(), userDto.username());
        assertEquals(userInsertRequest.email(), userDto.email());
        assertEquals(userInsertRequest.password(), userDto.password());
        assertEquals(userInsertRequest.address(), userDto.address());
        assertEquals(userInsertRequest.isAdmin(), userDto.isAdmin());
        assertEquals(userInsertRequest.phone(), userDto.phone());
        assertEquals(userInsertRequest.country(), userDto.country());
        assertEquals(userInsertRequest.profilePicture(), userDto.profilePicture());
        assertEquals(userInsertRequest.birthdate(), userDto.birthdate());
    }

    @Test
    @DisplayName("Test map from UserUpdateRequest to UserDto")
    void testFromUserUpdateRequestToUserDto() {
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest(
                3L,
                "Bob Wilson",
                "bobwilson",
                "bob@example.com",
                "password789",
                "789 Pine Rd",
                UserRole.CUSTOMER,
                555555555,
                "France",
                "profile3.jpg",
                LocalDate.of(1995, 3, 10));

        UserDto userDto = UserPresentationMapper.getInstance()
                .fromUserUpdateRequestToUserDto(userUpdateRequest);

        assertEquals(userUpdateRequest.id(), userDto.id());
        assertEquals(userUpdateRequest.name(), userDto.name());
        assertEquals(userUpdateRequest.username(), userDto.username());
        assertEquals(userUpdateRequest.email(), userDto.email());
        assertEquals(userUpdateRequest.password(), userDto.password());
        assertEquals(userUpdateRequest.address(), userDto.address());
        assertEquals(userUpdateRequest.isAdmin(), userDto.isAdmin());
        assertEquals(userUpdateRequest.phone(), userDto.phone());
        assertEquals(userUpdateRequest.country(), userDto.country());
        assertEquals(userUpdateRequest.profilePicture(), userDto.profilePicture());
        assertEquals(userUpdateRequest.birthdate(), userDto.birthdate());
    }
}
