package com.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "street")
public class Street extends PanacheEntity {
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
    public City city;

    @Transient
    @JsonbProperty("cityId")
    public Long cityId;
}
