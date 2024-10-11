package com.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "city")
public class City extends EntityBase {
    @Column(name = "name")
    public String name;

    @Column(name = "zip_code")
    public int zipCode;

    @ManyToOne
    @JoinColumn(name = "id")
    public Country country;
}
