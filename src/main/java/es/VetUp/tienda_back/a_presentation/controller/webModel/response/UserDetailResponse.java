package es.VetUp.tienda_back.a_presentation.controller.webModel.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import es.VetUp.tienda_back.b_domain.model.enums.UserRole;

import java.time.LocalDate;

public record UserDetailResponse(
        Long id,
        String name,
        String username,
        String email,
        String password,
        String address,
        UserRole isAdmin,
        Number phone,
        String country,
        String profilePicture,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate birthdate
) {
}
