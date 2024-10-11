package com.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "user")
public class User extends EntityBase {
    @Column(name = "first_name")
    public String firstName;

    @Column(name = "last_name")
    public String lastName;

    @Column(name = "email", unique = true)
    public String email;

    @Column(name = "password")
    public String password;

    @ManyToOne
    @JoinColumn(name = "id")
    public Role role;
}
