package es.VetUp.tienda_back.b_domain.repository.entity;


import es.VetUp.tienda_back.b_domain.model.enums.UserRole;

import java.sql.Date;
import java.time.LocalDate;

public record UserEntity(
    Long id,
    String name,
    String username,
    String email,
    String password,
    String address,
    UserRole isAdmin,
    Integer phone,
    String country,
    String profilePicture,
    LocalDate birthdate
) {
}
