package es.VetUp.tienda_back.b_domain.model;

import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    @DisplayName("Test User Creation")
    void testUserCreation() {
        Long id = 1L;
        String name = "John Doe";
        String username = "johndoe";
        String email = "john.doe@example.com";
        String password = "securePassword123";
        String address = "123 Main Street";
        UserRole isAdmin = UserRole.CUSTOMER;
        Integer phone = 123456789;
        String country = "Spain";
        String profilePicture = "profile.jpg";
        LocalDate birthdate = LocalDate.of(1990, 5, 15);

        User user = assertDoesNotThrow(() -> new User(id, name, username, email, password, address,
                isAdmin, phone, country, profilePicture, birthdate));

        assertAll("user",
                () -> assertEquals(id, user.getId()),
                () -> assertEquals(name, user.getName()),
                () -> assertEquals(username, user.getUsername()),
                () -> assertEquals(email, user.getEmail()),
                () -> assertEquals(password, user.getPassword()),
                () -> assertEquals(address, user.getAddress()),
                () -> assertEquals(isAdmin, user.getAdmin()),
                () -> assertEquals(phone, user.getPhone()),
                () -> assertEquals(country, user.getCountry()),
                () -> assertEquals(profilePicture, user.getProfilePicture()),
                () -> assertEquals(birthdate, user.getBirthdate()));
    }
}
