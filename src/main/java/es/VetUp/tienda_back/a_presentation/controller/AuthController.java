package es.VetUp.tienda_back.a_presentation.controller;

import es.VetUp.tienda_back.a_presentation.controller.mapper.UserPresentationMapper;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.LoginRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.response.LoginResponse;
import es.VetUp.tienda_back.a_presentation.controller.webModel.response.UserDetailResponse;
import es.VetUp.tienda_back.b_domain.mapper.UserMapper;
import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import es.VetUp.tienda_back.b_domain.service.JwtService;
import es.VetUp.tienda_back.b_domain.service.UserService;
import es.VetUp.tienda_back.b_domain.service.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login/{role}")
    public LoginResponse login(@RequestBody LoginRequest loginRequest, @PathVariable("role") UserRole role) {
        System.out.println("Login attempt for role: " + role);
        UserDto user = userService.getUserByUsername(loginRequest.username());

        if (user == null || !passwordEncoder.matches(loginRequest.password(), user.password())) {
            throw new RuntimeException("Invalid username or password");
        } else if (role == UserRole.ADMIN && user.isAdmin() != UserRole.ADMIN) {
            throw new RuntimeException("User is not an admin");
        }

        String token = jwtService.generateToken(user);
        return new LoginResponse(token);
    }


    @GetMapping("/validate")
    public UserDetailResponse validateToken(@RequestHeader("Authorization") String authHeader) {
        UserDto user = jwtService.getUserFromToken(authHeader.substring(7));
        return UserPresentationMapper.getInstance().fromUserDtoToUserDetailResponse(user);
    }

}
