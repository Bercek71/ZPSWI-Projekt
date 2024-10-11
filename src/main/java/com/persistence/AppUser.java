package com.persistence;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "app_user")
public class AppUser extends EntityBase {
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


    public enum AppUserRole {
        ADMIN,
        USER,
        MANAGER
    }
}
