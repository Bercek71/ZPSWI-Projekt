package com.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "hotel")
public class Hotel extends EntityBase {
    @Column(name = "name")
    public String name;

    @ManyToOne
    @JoinColumn(name = "id")
    public Country country;
}
