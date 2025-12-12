package es.VetUp.tienda_back.c_persistence.dao.jpa.entity;


import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;


@Entity
@Table(name = "users")
public class UserJpaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String address;
    @Enumerated(EnumType.STRING)
    @Column(name = "is_admin")
    private UserRole isAdmin;
    private Integer phone;
    private String country;
    private String profilePicture;
    private LocalDate birthdate;



    public UserJpaEntity() {
    }
    public UserJpaEntity(Long id, String name, String username, String email, String password, String address, UserRole isAdmin, Integer phone, String country, String profilePicture, LocalDate birthdate) {
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

    public UserRole getIsAdmin() {
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


