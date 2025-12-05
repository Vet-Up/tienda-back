package es.VetUp.tienda_back.b_domain.service.dto;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserDto(
    Long id,
    @NotNull
    String name,
    @NotNull
    String username,
    @NotNull
    String email,
    @NotNull
    String password,
    @NotNull
    String address,
    @NotNull
    Boolean isAdmin,
    @NotNull
    Integer phone,
    @NotNull
    String country,
    String profilePicture,
    @NotNull
    LocalDate birthdate
) {
}
