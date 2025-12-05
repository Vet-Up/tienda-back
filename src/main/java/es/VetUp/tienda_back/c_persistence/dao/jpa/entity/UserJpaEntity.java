package es.VetUp.tienda_back.c_persistence.dao.jpa.entity;


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
    private Boolean isAdmin;
    private Integer phone;
    private String country;
    private String profilePicture;
    private LocalDate birthdate;

//
//    @OneToOne(mappedBy = "user")
//    private CartJpaEntity cart;

    public UserJpaEntity() {
    }
    public UserJpaEntity(Long id, String name, String username, String email, String password, String address, Boolean isAdmin, Integer phone, String country, String profilePicture, LocalDate birthdate) {
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

    public Boolean getIsAdmin() {
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

//    public CartJpaEntity getCart() {
//        return cart;
//    }
//
//    public void setCart(CartJpaEntity cart) {
//        this.cart = cart;
//    }
}


