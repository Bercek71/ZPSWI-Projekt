package com.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "street")
public class Street extends EntityBase {
    @Column(name = "name")
    public String name;

    @Column(name = "land_registry_number")
    public int landRegistryNumber;

    @Column(name = "house_number")
    public int houseNumber;

    @ManyToOne
    @JoinColumn(name = "id")
    public City city;
}
