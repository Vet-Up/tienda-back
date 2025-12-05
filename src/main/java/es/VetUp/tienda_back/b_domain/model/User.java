package es.VetUp.tienda_back.b_domain.model;

import java.time.LocalDate;

public class User {
    private final Long id;
    private final String name;
    private final String username;
    private final String email;
    private final String password;
    private final String address;
    private final Boolean  isAdmin;
    private final Integer phone;
    private final String country;
    private final String  profilePicture;
    private final LocalDate birthdate;

    public User(Long id, String name, String username, String email, String password, String address, Boolean isAdmin, Integer phone, String country, String profilePicture,     LocalDate birthdate) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.isAdmin = isAdmin;
        this.phone = phone;
        this.country = country;
        this.profilePicture = profilePicture;
        this.birthdate = birthdate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public Integer getPhone() {
        return phone;
    }

    public String getCountry() {
        return country;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

}
