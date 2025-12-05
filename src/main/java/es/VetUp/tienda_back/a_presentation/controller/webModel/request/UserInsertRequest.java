package es.VetUp.tienda_back.a_presentation.controller.webModel.request;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record UserInsertRequest(
        String name,
        String username,
        String email,
        String password,
        String address,
        Boolean isAdmin,
        Integer phone,
        String country,
        String profilePicture,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate birthdate
) {
}
