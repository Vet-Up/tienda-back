package es.VetUp.tienda_back.a_presentation.controller;

import es.VetUp.tienda_back.a_presentation.controller.mapper.UserPresentationMapper;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.UserInsertRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.UserUpdateRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.response.UserDetailResponse;
import es.VetUp.tienda_back.b_domain.service.UserService;
import es.VetUp.tienda_back.b_domain.service.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDetailResponse>> findAllUsers() {
        List<UserDto> users = userService.getAllUsersnotAdmin();
        List<UserDetailResponse> userResponses = users.stream().map(UserPresentationMapper.getInstance()::fromUserDtoToUserDetailResponse).toList();
        return new ResponseEntity<>(userResponses, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDetailResponse> getUserById(@PathVariable Long id) {
        UserDto userDto = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserDetailResponse userDetailResponse =
                UserPresentationMapper.getInstance().fromUserDtoToUserDetailResponse(userDto);
        return new ResponseEntity<>(userDetailResponse, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<UserDetailResponse> createUser(@RequestBody UserInsertRequest userInsertRequest) {
        UserDto userDto = UserPresentationMapper.getInstance().fromUserInsertRequestToUserDto(userInsertRequest);
        UserDto createdUser = userService.createUser(userDto);
        UserDetailResponse response = UserPresentationMapper.getInstance().fromUserDtoToUserDetailResponse(createdUser);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDetailResponse> updateUser(@PathVariable("id") Long id, @RequestBody UserUpdateRequest userUpdateRequest) {
        if (!id.equals(userUpdateRequest.id())) {
            throw new IllegalArgumentException("ID in path and request body must match");
        }
        UserDto userDto = UserPresentationMapper.getInstance().fromUserUpdateRequestToUserDto(userUpdateRequest);
        UserDto updatedUser = userService.updateUser(userDto);
        UserDetailResponse response = UserPresentationMapper.getInstance().fromUserDtoToUserDetailResponse(updatedUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }



}
