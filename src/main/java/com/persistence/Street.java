package com.persistence;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "street")
public class Street extends EntityBase {
    @Column(name = "name")
    @JsonbProperty("name")
    public String name;

    @Column(name = "land_registry_number")
    @JsonbProperty("landRegistryNumber")
    public int landRegistryNumber;

    @Column(name = "house_number")
    @JsonbProperty("houseNumber")
    public int houseNumber;

    @ManyToOne
    @JoinColumn(name = "city_id")
    @JsonbProperty("cityId")
    public City city;
}
