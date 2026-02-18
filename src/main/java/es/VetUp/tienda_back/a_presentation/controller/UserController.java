package es.VetUp.tienda_back.a_presentation.controller;

import es.VetUp.tienda_back.a_presentation.controller.mapper.UserPresentationMapper;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.UserInsertRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.UserUpdateRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.response.UserDetailResponse;
import es.VetUp.tienda_back.b_domain.model.Page;
import es.VetUp.tienda_back.b_domain.service.UserService;
import es.VetUp.tienda_back.b_domain.service.dto.UserDto;
import es.VetUp.tienda_back.config.annotation.RequireAdmin;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequireAdmin
    @GetMapping
    public ResponseEntity<Page<UserDetailResponse>> findAllUsers(@RequestParam(required = false, defaultValue = "1") int page,
                                                                 @RequestParam(required = false, defaultValue = "10") int size) {
        Page<UserDto> userDtoPage = userService.getAllUsers(page, size);

        List<UserDetailResponse> userResponses = userDtoPage.data().stream()
                .map(UserPresentationMapper.getInstance()::fromUserDtoToUserDetailResponse)
                .toList();

        Page<UserDetailResponse> userResponsePage = new Page<>(
                userResponses,
                userDtoPage.pageNumber(),
                userDtoPage.pageSize(),
                userDtoPage.totalElements()
        );
        return new ResponseEntity<>(userResponsePage, HttpStatus.OK);
    }

    @RequireAdmin
    @GetMapping("/search")
    public ResponseEntity<Page<UserDetailResponse>> searchUsersByEmail(
            @RequestParam String email,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email parameter cannot be empty");
        }
        Page<UserDto> userDtoPage = userService.searchByEmail(email.trim(), page, size);

        List<UserDetailResponse> userResponses = userDtoPage.data().stream()
                .map(UserPresentationMapper.getInstance()::fromUserDtoToUserDetailResponse)
                .toList();

        Page<UserDetailResponse> userResponsePage = new Page<>(
                userResponses,
                userDtoPage.pageNumber(),
                userDtoPage.pageSize(),
                userDtoPage.totalElements()
        );
        return new ResponseEntity<>(userResponsePage, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDetailResponse> getUserById(
            @PathVariable Long id,
            @RequestAttribute("userId") Long authenticatedUserId,
            @RequestAttribute("isAdmin") Boolean isAdmin) {

        if (!isAdmin && !id.equals(authenticatedUserId)) {
            throw new RuntimeException("Unauthorized: You can only access your own profile");
        }

        UserDto userDto = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserDetailResponse userDetailResponse =
                UserPresentationMapper.getInstance().fromUserDtoToUserDetailResponse(userDto);
        return new ResponseEntity<>(userDetailResponse, HttpStatus.OK);
    }


    @RequireAdmin
    @GetMapping("/by-email")
    public ResponseEntity<UserDetailResponse> getUserByEmail(@RequestParam String email) {
        UserDto userDto = userService.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserDetailResponse userDetailResponse =
                UserPresentationMapper.getInstance().fromUserDtoToUserDetailResponse(userDto);
        return new ResponseEntity<>(userDetailResponse, HttpStatus.OK);
    }



    @PostMapping
    public ResponseEntity<UserDetailResponse> createUser(@Valid @RequestBody UserInsertRequest userInsertRequest) {
        UserDto userDto = UserPresentationMapper.getInstance().fromUserInsertRequestToUserDto(userInsertRequest);
        UserDto createdUser = userService.createUser(userDto);
        UserDetailResponse response = UserPresentationMapper.getInstance().fromUserDtoToUserDetailResponse(createdUser);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDetailResponse> updateUser(
            @PathVariable("id") Long id,
            @Valid @RequestBody UserUpdateRequest userUpdateRequest,
            @RequestAttribute("userId") Long authenticatedUserId,
            @RequestAttribute("isAdmin") Boolean isAdmin) {

        if (!id.equals(userUpdateRequest.id())) {
            throw new IllegalArgumentException("ID in path and request body must match");
        }

        if (!isAdmin && !id.equals(authenticatedUserId)) {
            throw new RuntimeException("Unauthorized: You can only update your own profile");
        }

        UserDto userDto = UserPresentationMapper.getInstance().fromUserUpdateRequestToUserDto(userUpdateRequest);
        UserDto updatedUser = userService.updateUser(userDto);
        UserDetailResponse response = UserPresentationMapper.getInstance().fromUserDtoToUserDetailResponse(updatedUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequireAdmin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
