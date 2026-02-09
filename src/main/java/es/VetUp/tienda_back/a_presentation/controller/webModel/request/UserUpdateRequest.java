package es.VetUp.tienda_back.a_presentation.controller.webModel.request;
import com.fasterxml.jackson.annotation.JsonFormat;
import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserUpdateRequest(
        @NotNull(message = "ID cannot be null")
        Long id,

        @NotBlank(message = "Name cannot be empty")
        @Size(min = 1, message = "Name must have at least 1 character")
        String name,

        @NotBlank(message = "Username cannot be empty")
        @Size(min = 1, message = "Username must have at least 1 character")
        String username,

        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Email must be valid")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.(com|es)$",
                 message = "Email must end with .com or .es")
        String email,

        @NotBlank(message = "Password cannot be empty")
        @Size(min = 1, message = "Password must have at least 1 character")
        String password,
        String address,

        @NotNull(message = "User role cannot be null")
        UserRole isAdmin,

        @NotNull(message = "Phone cannot be null")
        @Min(value = 100000000, message = "Phone must have exactly 9 digits")
        @Max(value = 999999999, message = "Phone must have exactly 9 digits")
        Integer phone,

        @NotBlank(message = "Country cannot be empty")
        @Size(min = 1, message = "Country must have at least 1 character")
        String country,

        String profilePicture,

        @NotNull(message = "Birthdate cannot be null")
        @Past(message = "Birthdate must be in the past")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate birthdate
) {
}
