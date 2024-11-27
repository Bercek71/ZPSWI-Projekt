package com.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "app_user")
public class AppUser extends PanacheEntity {
    @Column(name = "first_name")
    @JsonbProperty("firstName")
    public String firstName;

    @Column(name = "last_name")
    @JsonbProperty("lastName")
    public String lastName;

    @Column(name = "email", unique = true)
    @JsonbProperty("email")
    public String email;

    @Column(name = "password")
    @JsonbProperty("password")
    public String password;

    @Enumerated(EnumType.STRING)
    @JsonbProperty("role")
    public AppUserRole role;

    @ManyToMany(mappedBy = "receptionists")
    List<Hotel> hotels;


    public enum AppUserRole {
        EMPLOYEE, //odklikava platby
        USER,
        MANAGER // CRUD operace nad hotelem
    }
}
